package dbps.dbps.controller;

import dbps.dbps.service.connectManager.MQTTManager;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileController {

    private File selectedFile;

    @FXML
    private void handleOpen(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("파일 선택");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("모든 파일", "*.*");
        fileChooser.getExtensionFilters().add(extFilter);

        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            showAlert("파일 선택", "선택된 파일: " + selectedFile.getName());
        }
    }

    @FXML
    private void handleSend() {
        if (selectedFile == null) {
            showAlert("오류", "전송할 파일이 선택되지 않았습니다. 먼저 파일을 선택하세요.");
            return;
        }
        // MQTTManager 인스턴스 호출 후 파일 전송 Task 생성
        MQTTManager mqttManager = MQTTManager.getInstance();
        Task<String> sendTask = mqttManager.sendFileMsg(selectedFile);
        sendTask.setOnSucceeded(e -> {
            String result = sendTask.getValue();
            showAlert("전송 결과", result);
        });
        sendTask.setOnFailed(e -> showAlert("오류", "파일 전송 중 오류가 발생하였습니다."));
        new Thread(sendTask).start();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleReceive() {
        MQTTManager mqttManager = MQTTManager.getInstance();
        String saveDir = "C:\\Users\\HP\\Desktop\\mqttFile";
        Task<String> receiveTask = new Task<>() {
            @Override
            protected String call() {
                return mqttManager.subscribeAndSaveFile(saveDir);
            }
        };
        receiveTask.setOnSucceeded(e -> {
            String result = receiveTask.getValue();
            showAlert("수신 결과", result);
        });
        receiveTask.setOnFailed(e -> showAlert("오류", "파일 수신 중 오류가 발생하였습니다."));
        new Thread(receiveTask).start();
    }
}