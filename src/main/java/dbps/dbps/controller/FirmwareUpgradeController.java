package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.FirmwareService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;

public class FirmwareUpgradeController {

    @FXML
    public TextArea firmwareInformation;

    @FXML
    public TextArea firmwareFileInformation;

    @FXML
    public TextField fileLocation;

    @FXML
    public AnchorPane firmwareUpgradeAP;

    @FXML
    public ProgressBar firmwareProgressIndicator;

    @FXML
    public Label firmwareProgressLabel;

    AsciiMsgTransceiver asciiMsgTransceiver;
    HexMsgTransceiver hexMsgTransceiver;
    LogService logService;
    FirmwareService firmwareService;

    TCPManager tcpManager;
    UDPManager udpManager;
    ServerTCPManager serverTCPManager;
    SerialPortManager serialPortmanager;

    Stage progressStage;
    ProgressBar progressBar;
    Label progressLabel;
    Button cancelButton;

    @FXML
    public void initialize() {
        firmwareUpgradeAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/firmware.css").toExternalForm());
        firmwareInformation.setEditable(false);
        firmwareFileInformation.setEditable(false);

        tcpManager = TCPManager.getManager();
        udpManager = UDPManager.getUDPManager();
        serverTCPManager = ServerTCPManager.getInstance();
        serialPortmanager = SerialPortManager.getManager();

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        logService = LogService.getLogService();
        firmwareService = FirmwareService.getFirmwareService();
        FirmwareService.setFirmwareInformation(firmwareInformation);

        fileLocation.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                fileLocation.positionCaret(fileLocation.getText().length()); // 텍스트 끝으로 캐럿 이동
            });
        });

        fileLocation.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                fileLocation.positionCaret(newValue.length()); // 텍스트 끝으로 캐럿 이동
            });
        });

        progressStage = new Stage();
        progressStage.initModality(Modality.APPLICATION_MODAL); // 부모 창을 블로킹
        progressStage.setTitle("펌웨어 업로드 진행 상태");

        progressBar = new ProgressBar(0);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.setPrefWidth(250);

        progressLabel = new Label("펌웨어 업로드 준비 중...");
        progressLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        cancelButton = new Button("취소");


        HBox buttonBox = new HBox(new Region(), cancelButton);
        HBox.setHgrow(buttonBox.getChildren().get(0), Priority.ALWAYS);
        buttonBox.setSpacing(10);

        VBox vbox = new VBox(15, progressLabel, progressBar, buttonBox);
        vbox.setStyle("-fx-padding: 20px;");
        Scene progressScene = new Scene(vbox, 300, 150);
        progressStage.setScene(progressScene);

        vbox.setStyle("-fx-padding: 20px; -fx-background-color: #333333;");
        cancelButton.setStyle(
                "" +
                        "-fx-background-color: linear-gradient(#444444, #222222);" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #4A4A4A;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 5 10 5 10;" +
                        "-fx-background-radius: 10;"
        );
        cancelButton.setOnMousePressed(e -> {
            cancelButton.setStyle(
                    "-fx-background-color: linear-gradient(#222222, #000000);" + // 눌린 효과 (어두운 색)
                            "-fx-text-fill: white;" +
                            "-fx-border-color: orange;" + // 눌렀을 때 테두리 주황색
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: innershadow(gaussian, rgba(255, 165, 0, 0.8), 5, 0, 0, 0);" // 안쪽 그림자 효과 추가
            );
        });

        cancelButton.setOnMouseEntered(e -> {
            cancelButton.setStyle(
                    "-fx-background-color: linear-gradient(#444444, #222222);" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: orange;" + // 🔹 테두리 주황색 변경
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.8), 5, 0, 1, 1);" + // 밝은 그림자 효과
                            "-fx-cursor: hand;" // 🔹 손가락 커서로 변경
            );
        });

// 🔹 마우스를 벗어나면 원래 스타일로 복구
        cancelButton.setOnMouseExited(e -> {
            cancelButton.setStyle(
                    "-fx-background-color: linear-gradient(#444444, #222222);" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: #4A4A4A;" + // 원래 테두리 색상으로 복귀
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 1, 1);" + // 기본 그림자 효과
                            "-fx-cursor: default;" // 기본 커서로 변경
            );
        });

// 버튼에서 손을 뗄 때 원래 스타일로 복구
        cancelButton.setOnMouseReleased(e -> {
            cancelButton.setStyle(
                    "-fx-background-color: linear-gradient(#444444, #222222);" +
                            "-fx-text-fill: white;" +
                            "-fx-border-color: #4A4A4A;" +
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 1, 1);"
            );
        });
        cancelButton.setOnAction(e->{
            if (firmwareUploadTask != null){
                firmwareUploadTask.cancel();
                progressBar.setProgress(0);
                closeWindowAfterDelay(progressStage, 1000);
            }
        });
        progressLabel.setStyle(
                " " +
                        "-fx-text-fill: white; " +
                        "-fx-background-color: #222222; " +
                        "-fx-padding: 5; " +
                        "-fx-border-color: #4A4A4A; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        );

        Platform.runLater(() -> {
            Stage parentStage = (Stage) firmwareUpgradeAP.getScene().getWindow();

            double parentX = parentStage.getX();
            double parentY = parentStage.getY();
            double parentWidth = parentStage.getWidth();
            double parentHeight = parentStage.getHeight();

            // 진행 창 위치 설정 (세로는 부모와 동일, 가로는 절반 위치)
            progressStage.setX(parentX + parentWidth / 2 - 150); // 300px 창 기준 중앙 정렬
            progressStage.setY(parentY + (parentHeight / 2) - 75); // 150px 창 기준 중앙 정렬
        });
    }


    public void read() throws ExecutionException, InterruptedException {
        if (IS_ASCII){
            String msg = "![0081!]";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"081!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, firmwareProgressIndicator);
        }
        else {
            String msg = "10 02 00 00 02 6F F1 10 03";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+ "00 02 6F F1 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, firmwareProgressIndicator);
        }
    }

    public void open(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("펌웨어 파일 선택");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("펌웨어 파일", "*.bin"),
                new FileChooser.ExtensionFilter("모든 파일", "*.*")
        );
        File defaultDir = new File(System.getProperty("user.dir") + File.separator + "Firmware");
        if (defaultDir.exists() && defaultDir.isDirectory()) {
            fileChooser.setInitialDirectory(defaultDir);
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        Stage stage = (Stage) (((Node) mouseEvent.getSource()).getScene().getWindow());
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            fileLocation.setText(selectedFile.getAbsolutePath());
        }

        String result = "";
        String hexToDecimal = "";
        String filePath = fileLocation.getText();
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        if (fileName.contains("502")){
            firmwareFileInformation.setText(fileName);
        } else {
        try (RandomAccessFile file = new RandomAccessFile(selectedFile.getAbsolutePath(), "r")) {
            int startByte = 0;
            int length = 0;
            if (!selectedFile.getName().contains("502")) {
                startByte = 516;
                length = 38;
            } else {
                startByte = 15796;
                length = 38;
            }

            // 앞 한 글자를 추가로 읽기 위해 startByte를 1 줄임
            int extendedStartByte = startByte - 1;

            // 파일의 해당 위치로 이동
            file.seek(extendedStartByte);

            // 읽을 바이트 배열 생성 (기존 길이 + 앞 한 글자)
            byte[] buffer = new byte[length + 1];
            int bytesRead = file.read(buffer);

            if (bytesRead == length + 1) {
                // 앞 한 글자 (바이트) 읽어서 16진수 변환 후 10진수 변환
                int extraByte = buffer[0] & 0xFF;  // 부호 없는 값으로 변환
                hexToDecimal = String.valueOf(extraByte);  // 10진수 문자열로 변환

                // 기존 데이터 부분을 읽기 (1바이트 이후부터)
                result = new String(buffer, 1, length, "MS949"); // ASCII 호환 인코딩


            }
        } catch (IOException e) {

        }

        // UI에 표시
        firmwareFileInformation.setText("<"+hexToDecimal+">"+result);}
    }

    public Task<Void> firmwareUploadTask;

    public void send() {
        if (firmwareInformation.getText().isEmpty()) {
            logService.warningLog("컨트롤러의 펌웨어 버전을 먼저 읽어주세요.");
            return;
        }

        String firmwareInformationText = firmwareInformation.getText();
        String firmwareFileInformationText = firmwareFileInformation.getText();
        int index1 = firmwareInformationText.lastIndexOf("DIBD");
        int index2 = firmwareFileInformationText.lastIndexOf("DIBD");

        String result1;
        String result2;
        if (index1 != -1 && index1 + "DIBD".length() + 4 <= firmwareInformationText.length()) {
            result1 = firmwareInformationText.substring(index1 + "DIBD".length(), index1 + "DIBD".length() + 4);
        } else {
            logService.errorLog("DIBD를 찾을 수 없거나 4자리를 가져올 수 없습니다.");
            return;
        }
        if (index2 == -1) {
            index2 = firmwareFileInformationText.lastIndexOf("DB");
            result2 = firmwareFileInformationText.substring(index2 + "DB".length(), index2 + "DB".length() + 4);
        } else if (index2 != -1 && index2 + "DIBD".length() + 4 <= firmwareFileInformationText.length()) {
            result2 = firmwareFileInformationText.substring(index2 + "DIBD".length(), index2 + "DIBD".length() + 4);
        } else {
            logService.errorLog("DIBD를 찾을 수 없거나 4자리를 가져올 수 없습니다.");
            return;
        }

        if (!result1.equals(result2)) {
            logService.errorLog("업로드할 수 없습니다. 컨트롤러의 펌웨어와 동일한 펌웨어를 업로드해주세요.");
            return;
        }

        if (!Files.exists(Path.of(uploadFirmwarePath))) {
            logService.errorLog("파일을 찾을 수 없습니다.");
            return;
        }

        uploadFirmwarePath = fileLocation.getText();

        // 새로운 창 생성
        progressStage.show();

        // 펌웨어 업로드 Task 실행
        Task<Void> firmwareUploadTask = firmwareService.firmwareUpload(progressBar, progressLabel);

        firmwareUploadTask.setOnRunning(e -> {
            progressLabel.setText("펌웨어 업로드 중...");
            progressBar.setProgress(-1); // 애니메이션 상태
        });

        cancelButton.setOnAction(e -> {
            if (firmwareUploadTask != null) {
                firmwareUploadTask.cancel();
                progressLabel.setText("업로드가 취소되었습니다.");
                closeWindowAfterDelay(progressStage, 2000); // 2초 후 창 닫기
            }
        });

        firmwareUploadTask.setOnSucceeded(e -> {
            progressLabel.setText("펌웨어 업로드 완료!");
            progressBar.setProgress(1.0);
            closeWindowAfterDelay(progressStage, 2000); // 2초 후 창 닫기
        });

        firmwareUploadTask.setOnFailed(e -> {
            progressLabel.setText("펌웨어 업로드 실패!");
            progressBar.setProgress(0);
            closeWindowAfterDelay(progressStage, 2000); // 실패 시 2초 후 창 닫기
        });

        firmwareUploadTask.setOnCancelled(e -> {
            progressLabel.setText("업로드가 취소되었습니다.");
            progressBar.setProgress(0);
            closeWindowAfterDelay(progressStage, 2000); // 취소 시 2초 후 창 닫기
        });

        new Thread(firmwareUploadTask).start();
    }

    private void closeWindowAfterDelay(Stage stage, int delayMillis) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Platform.runLater(stage::close);
        }).start();

        end();
    }

    private void end(){
        tcpManager.disconnectNoLog();
        serverTCPManager.disconnectNoLog();
        udpManager.disconnectNoLog();
        serialPortmanager.closePortNoLog(OPEN_PORT_NAME);
    }


    public void close(MouseEvent mouseEvent) {
        ((Stage)(((Node) mouseEvent.getSource()).getScene().getWindow())).close();
        end();
    }
}
