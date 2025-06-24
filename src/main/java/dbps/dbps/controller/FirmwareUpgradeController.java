package dbps.dbps.controller;

import dbps.dbps.Constants;
import dbps.dbps.service.*;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;
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
    ResourceBundle bundle;

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
        bundle = ResourceManager.getInstance().getBundle();

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        logService = LogService.getLogService();
        firmwareService = FirmwareService.getFirmwareService();
        firmwareInformation.setCache(true);
        firmwareInformation.setCacheHint(CacheHint.SPEED);
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
        progressStage.setTitle("Firmware Upload Progress");

        progressBar = new ProgressBar(0);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.setPrefWidth(250);

        progressLabel = new Label("Preparing Firmware Upload...");
        progressLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        cancelButton = new Button("Cancel");


        HBox buttonBox = new HBox(new Region(), cancelButton);
        HBox.setHgrow(buttonBox.getChildren().get(0), Priority.ALWAYS);
        buttonBox.setSpacing(10);

        VBox vbox = new VBox(15, progressLabel, progressBar, buttonBox);
        vbox.setStyle("-fx-padding: 20px;");
        Scene progressScene = new Scene(vbox, 300, 150);
        progressStage.setScene(progressScene);

        vbox.setStyle("-fx-padding: 20px;");
        cancelButton.setStyle(
                "-fx-border-radius: 10;" +
                        "-fx-padding: 5 10 5 10;" +
                        "-fx-background-radius: 10;"
        );
        cancelButton.setOnMousePressed(e -> {
            cancelButton.setStyle(
                    "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;"
            );
        });

        cancelButton.setOnMouseEntered(e -> {
            cancelButton.setStyle(
                    "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" // 🔹 손가락 커서로 변경
            );
        });

// 🔹 마우스를 벗어나면 원래 스타일로 복구
        cancelButton.setOnMouseExited(e -> {
            cancelButton.setStyle(
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: default;" // 기본 커서로 변경
            );
        });

// 버튼에서 손을 뗄 때 원래 스타일로 복구
        cancelButton.setOnMouseReleased(e -> {
            cancelButton.setStyle(
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0, 1, 1);"
            );
        });
        cancelButton.setOnAction(e -> {
            if (firmwareUploadTask != null) {
                cancel = true;
                firmwareUploadTask.cancel();
                progressBar.setProgress(0);
                closeWindowAfterDelay(progressStage, 1000);
                cancel = false;
            }
        });
        progressLabel.setStyle(
                        "-fx-padding: 5; " +
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


        firmwareUpgradeAP.setOnKeyPressed(new Constants.EscapeKeyEventHandler());
    }


    public void read() throws ExecutionException, InterruptedException {
        if (IS_ASCII) {
            String msg = "![0081!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "081!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, firmwareProgressIndicator);
        } else {
            String msg = "10 02 00 00 02 6F F1 10 03";
            if (isRS) {
                msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 02 6F F1 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, firmwareProgressIndicator);
        }
    }

    public void open(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Select");
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
        if (fileName.contains("502")) {
            firmwareFileInformation.setText(fileName);
        } else if (fileName.contains("400")) {
            int startOffset = 0x50;   // 읽기 시작 위치
            int endOffset   = 0x6B;   // 읽기 끝 위치
            int length      = endOffset - startOffset + 1;

            try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
                raf.seek(startOffset);
                byte[] buffer = new byte[length];
                raf.read(buffer);
                String data = new String(buffer, StandardCharsets.US_ASCII);

                System.out.println("data = " + data);

                firmwareFileInformation.setText(data.toString());

            } catch (IOException e) {

            }
        } else {
            try {
                assert selectedFile != null;
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
                        result = new String(buffer, 1, length, "MS949");
                        result = result.replaceAll("!]", "");
                    }
                }
            } catch (IOException ignored) {

            }

            // UI에 표시
            firmwareFileInformation.setText("<" + hexToDecimal + ">" + result);
        }
    }

    public Task<Void> firmwareUploadTask;

    public void send() {
        if (firmwareInformation.getText().isEmpty()) {
            logService.warningLog(bundle.getString("readFirmwareFirst"));
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
            logService.errorLog(bundle.getString("errorDIBD"));
            return;
        }
        if (index2 == -1) {
            index2 = firmwareFileInformationText.lastIndexOf("DB");
            result2 = firmwareFileInformationText.substring(index2 + "DB".length(), index2 + "DB".length() + 4);
        } else if (index2 + "DIBD".length() + 4 <= firmwareFileInformationText.length()) {
            result2 = firmwareFileInformationText.substring(index2 + "DIBD".length(), index2 + "DIBD".length() + 4);
        } else {
            logService.errorLog(bundle.getString("errorDIBD"));
            return;
        }

        if (!result1.equals(result2)) {
            logService.errorLog(bundle.getString("errorFirmwareMismatch"));
            return;
        }

        uploadFirmwarePath = fileLocation.getText();

        if (!Files.exists(Path.of(uploadFirmwarePath))) {
            logService.errorLog(bundle.getString("errorFileNotFound"));
            return;
        }

        // 새로운 창 생성
        progressStage.show();

        // 펌웨어 업로드 Task 실행
        firmwareUploadTask = firmwareService.firmwareUpload(progressBar, progressLabel);

        firmwareUploadTask.setOnRunning(e -> {
            progressLabel.setText("Uploading Firmware...");
            progressBar.setProgress(-1); // 애니메이션 상태
        });

        firmwareUploadTask.setOnSucceeded(e -> {
            progressLabel.setText("Firmware upload Completed!");
            progressBar.setProgress(1.0);
            closeWindowAfterDelay(progressStage, 2000); // 2초 후 창 닫기
        });

        firmwareUploadTask.setOnFailed(e -> {
            progressLabel.setText("Firmware upload Failed!");
            progressBar.setProgress(0);
            closeWindowAfterDelay(progressStage, 2000); // 실패 시 2초 후 창 닫기
        });

        firmwareUploadTask.setOnCancelled(e -> {
            progressLabel.setText("Firmware upload Canceled!");
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

    private void end() {
        tcpManager.disconnectNoLog();
        serverTCPManager.disconnectNoLog();
        udpManager.disconnectNoLog();
        serialPortmanager.closePortNoLog(OPEN_PORT_NAME);
    }


    public void close(MouseEvent mouseEvent) {
        ((Stage) (((Node) mouseEvent.getSource()).getScene().getWindow())).close();
        end();
    }
}
