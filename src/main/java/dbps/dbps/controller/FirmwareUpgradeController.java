package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.FirmwareService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static dbps.dbps.Constants.IS_ASCII;
import static dbps.dbps.Constants.uploadFirmwarePath;

public class FirmwareUpgradeController {

    @FXML
    public TextArea firmwareInformation;

    @FXML
    public TextArea firmwareFileInformation;

    @FXML
    public TextField fileLocation;

    @FXML
    public AnchorPane firmwareUpgradeAP;

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
    }


    public void read() {
        if (IS_ASCII){
            String version = asciiMsgTransceiver.sendMessages("![0081!]");
            firmwareInformation.setText(version.substring(6, version.length()-2));
        }
        else {
            String version = hexMsgTransceiver.sendMessages("10 02 00 00 02 6F F1 10 03");
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
            logService.warningLog("컨트롤러의 버전을 먼저 확인해주세요.");
        }
        String firmwareInformationText = firmwareInformation.getText();
        String firmwareFileInformationText = firmwareFileInformation.getText();
        int index1 = firmwareInformationText.indexOf("DIBD");
        int index2 = firmwareFileInformationText.indexOf("DIBD");

        String result1;
        String result2;
        if (index1 != -1 && index1 + "DIBD".length() + 4 <= firmwareInformationText.length()) {
            result1 = firmwareInformationText.substring(index1 + "DIBD".length(), index1 + "DIBD".length() + 4);
        } else {
            logService.errorLog("DIBD를 찾을 수 없거나 4자리를 가져올 수 없습니다.");
            return;
        }

        if (index2 != -1 && index2 + "DIBD".length() + 4 <= firmwareFileInformationText.length()) {
            result2 = firmwareFileInformationText.substring(index2 + "DIBD".length(), index2 + "DIBD".length() + 4);
        } else {
            logService.errorLog("DIBD를 찾을 수 없거나 4자리를 가져올 수 없습니다.");
            return;
        }

        if (!result1.equals(result2)){
            logService.errorLog("업로드할 수 없습니다. 컨트롤러의 펌웨어와 동일한 펌웨어를 업로드해주세요.");
            return;
        }

        if (!Files.exists(Path.of(uploadFirmwarePath))){
            logService.errorLog("파일을 찾을 수 없습니다.");
            return;
        }

        uploadFirmwarePath = firmwareFileInformationText;
        Task<Void> firmwareUpload = firmwareService.firmwareUpload;

        new Thread(firmwareUpload).start();
    }

    public void close(MouseEvent mouseEvent) {
        ((Stage)(((Node) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
