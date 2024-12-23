package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.FirmwareService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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

    @FXML
    public void initialize() {
        firmwareUpgradeAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/firmware.css").toExternalForm());
        firmwareInformation.setEditable(false);
        firmwareFileInformation.setEditable(false);

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        logService = LogService.getLogService();
        firmwareService = FirmwareService.getFirmwareService();
        firmwareService.setFirmwareInformation(firmwareInformation);
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
            String version = hexMsgTransceiver.sendMessages(msg, firmwareProgressIndicator);
            String[] version_split = version.split(" ");
            StringBuilder result = new StringBuilder();

            for (int i = 7; i < version_split.length; i++) {
                result.append((char) Integer.parseInt(version_split[i], 16));
            }
            firmwareInformation.setText(result.toString());
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

        //파일 정보읽어서 firmwareFileInformation에 업데이트
        firmwareFileInformation.setText(selectedFile.getAbsolutePath());
    }

    public void send() {
        if (firmwareInformation.getText().isEmpty()){
            logService.warningLog("컨트롤러의 펌웨어 버전을 먼저 읽어주세요.");
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
        if (index2==-1){
            index2 = firmwareFileInformationText.lastIndexOf("DB");
            result2 = firmwareFileInformationText.substring(index2 +"DB".length(), index2 + "DB".length() + 4);
        }
        else if (index2 != -1 && index2 + "DIBD".length() + 4 <= firmwareFileInformationText.length()) {
            result2 = firmwareFileInformationText.substring(index2 + "DIBD".length(), index2 + "DIBD".length() + 4);
        } else {
            logService.errorLog("DIBD를 찾을 수 없거나 4자리를 가져올 수 없습니다.");
            return;
        }

        if (!result1.equals(result2)){
            System.out.println("result1 = " + result1);
            System.out.println("result2 = " + result2);
            logService.errorLog("업로드할 수 없습니다. 컨트롤러의 펌웨어와 동일한 펌웨어를 업로드해주세요.");
            return;
        }

        if (!Files.exists(Path.of(uploadFirmwarePath))){
            logService.errorLog("파일을 찾을 수 없습니다.");
            return;
        }

        uploadFirmwarePath = firmwareFileInformationText;
        Task<Void> firmwareUpload = firmwareService.firmwareUpload(firmwareProgressIndicator, firmwareProgressLabel);
        firmwareProgressIndicator.setVisible(false);
        firmwareUpload.setOnRunning(e -> {
            // Task가 시작될 때 로딩 애니메이션 표시
            firmwareProgressIndicator.setVisible(true);
            firmwareProgressLabel.setVisible(true);
        });

        firmwareUpload.setOnSucceeded(e -> {
            // Task가 성공적으로 끝났을 때 로딩 애니메이션 숨김
            firmwareProgressIndicator.setVisible(false);
            firmwareProgressLabel.setVisible(false);
        });

        firmwareUpload.setOnFailed(e -> {
            // Task가 실패했을 때 로딩 애니메이션 숨김
            firmwareProgressIndicator.setVisible(false);
            firmwareProgressLabel.setVisible(false);
        });

        firmwareUpload.setOnCancelled(e -> {
            // Task가 취소됐을 때 로딩 애니메이션 숨김
            firmwareProgressIndicator.setVisible(false);
            firmwareProgressLabel.setVisible(false);
        });

        new Thread(firmwareUpload).start();
    }

    public void close(MouseEvent mouseEvent) {
        ((Stage)(((Node) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
