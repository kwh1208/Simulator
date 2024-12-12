package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static dbps.dbps.Constants.CONNECT_START;
import static dbps.dbps.Constants.CONNECT_TYPE;

public class MqttController {

    @FXML
    public AnchorPane mqttAP;
    @FXML
    public TextField mqttBroker;
    @FXML
    public TextField mqttTopic;
    @FXML
    public TextField mqttTopic_R;
    @FXML
    public TextField mqttId;
    @FXML
    public TextField mqttPwd;
    public ProgressIndicator progressIndicator;

    HexMsgTransceiver hexTransceiver;
    MQTTManager mqttManager;

    @FXML
    public void initialize(){
        hexTransceiver=HexMsgTransceiver.getInstance();
        mqttManager = MQTTManager.getInstance();

        mqttAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/mqtt.css").toExternalForm());
    }


    public void connect() {
        mqttManager.setMQTTInfo(mqttBroker.getText(), mqttTopic.getText(), mqttTopic_R.getText() ,mqttId.getText(), mqttPwd.getText());

        CONNECT_TYPE="mqtt";

        hexTransceiver.sendByteMessages(CONNECT_START, progressIndicator);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void read(MouseEvent mouseEvent) {
        System.out.println(222);
        mqttManager.test();
        Task<String> send = mqttManager.sendMsgAndGetMsgByte(CONNECT_START);
        new Thread(send).start();
    }

    public void set(MouseEvent mouseEvent) {
    }
}
