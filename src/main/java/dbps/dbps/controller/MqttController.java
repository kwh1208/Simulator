package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MqttController {

    @FXML
    public AnchorPane mqttAP;
    @FXML
    public TextField mqttMac;
    @FXML
    public TextField API;
    @FXML
    public TextField brokerIP;
    @FXML
    public TextField clientId;
    @FXML
    public TextField userName;
    public ProgressIndicator progressIndicator;
    public TextField brokerPort;
    public TextField password;

    HexMsgTransceiver hexTransceiver;
    MQTTManager mqttManager;

    @FXML
    public void initialize(){
        hexTransceiver=HexMsgTransceiver.getInstance();
        mqttManager = MQTTManager.getInstance();

        mqttAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/mqtt.css").toExternalForm());
    }


    public void connect() {
        mqttManager.connect();
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void read(MouseEvent mouseEvent) {
        mqttManager.read();
    }

    public void set(MouseEvent mouseEvent) {
        mqttManager.newBrokerInfo(mqttMac.getText(), API.getText(), brokerIP.getText(), brokerPort.getText(), clientId.getText(), userName.getText(), password.getText());
    }
}