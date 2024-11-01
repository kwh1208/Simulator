package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static dbps.dbps.Constants.*;

public class MqttController {

    @FXML
    public AnchorPane mqttAP;
    @FXML
    public TextField mqttBroker;
    @FXML
    public TextField mqttClientId;
    @FXML
    public TextField mqttTopic;
    @FXML
    public ChoiceBox<String> mqttQos;
    @FXML
    public TextField mqttId;
    @FXML
    public TextField mqttPwd;

    HexMsgTransceiver hexTransceiver;
    MQTTManager mqttManager;

    /**
     * public static String MQTT_BROKER = "";
     *     public static String MQTT_CLIENT = "";
     *     public static String MQTT_TOPIC = "";
     *     public static String MQTT_Qos = "";
     *     public static String MQTT_ID = "";
     *     public static String MQTT_PWD = "";
     */

    @FXML
    public void initialize(){
        hexTransceiver=HexMsgTransceiver.getInstance();
        mqttManager = MQTTManager.getInstance();

        mqttAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/mqtt.css").toExternalForm());
    }


    public void connect() {
        mqttManager.setMQTTInfo(mqttBroker.getText(), mqttClientId.getText(), mqttTopic.getText(), Integer.parseInt(mqttQos.getValue()), mqttId.getText(), mqttPwd.getText());

        CONNECT_TYPE="mqtt";

        hexTransceiver.sendByteMessages(CONNECT_START);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void read(MouseEvent mouseEvent) {
    }

    public void set(MouseEvent mouseEvent) {
    }
}
