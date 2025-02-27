package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MqttServerController {
    public TextField IP;
    public TextField password;
    public TextField userName;
    public TextField port;
    public AnchorPane mqttServer;
    MQTTManager mqttManager;
    ConfigService configService;
    @FXML
    public void initialize(){
        mqttManager = MQTTManager.getInstance();
        configService = ConfigService.getInstance();
        mqttServer.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());
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

        configService.setProperty("mqtt_IP", IP.getText());
        configService.setProperty("mqtt_Port", port.getText());
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
