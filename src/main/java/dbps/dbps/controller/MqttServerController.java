package dbps.dbps.controller;

import dbps.dbps.service.connectManager.MQTTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MqttServerController {
    public TextField IP;
    public TextField password;
    public TextField userName;
    public TextField port;
    MQTTManager mqttManager;
    @FXML
    public void initialize(){
        mqttManager = MQTTManager.getInstance();
    }

    public void set() {
        mqttManager.setBrokerIp(IP.getText());
        mqttManager.setBrokerPort(port.getText());
        if (!userName.getText().isBlank()){
            mqttManager.setUsername(userName.getText());
        }
        if (!password.getText().isBlank()){
            mqttManager.setPassword(password.getText());
        }

        mqttManager.connect();
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
