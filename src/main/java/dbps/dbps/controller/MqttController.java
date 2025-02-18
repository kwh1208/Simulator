package dbps.dbps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbps.dbps.Simulator;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.MQTTUIService;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static dbps.dbps.Constants.openModal;

public class MqttController {

    @FXML
    public AnchorPane mqttAP;
    @FXML
    public TextField mqttMac;
    @FXML
    public TextField name;
    @FXML
    public TextField API;
    @FXML
    public TextField brokerIP;
    @FXML
    public TextField userName;
    @FXML
    public ProgressIndicator progressIndicator;
    @FXML
    public TextField brokerPort;
    @FXML
    public TextField password;


    HexMsgTransceiver hexTransceiver;
    MQTTManager mqttManager;
    MQTTUIService mqttUIService;

    @FXML
    public void initialize(){
        hexTransceiver=HexMsgTransceiver.getInstance();
        mqttManager = MQTTManager.getInstance();
        mqttUIService = MQTTUIService.getMqttUIService();

        mqttAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/mqtt.css").toExternalForm());
        mqttUIService.setMQTTControllerUI(mqttMac, name, API, brokerIP, brokerPort, userName, password);
    }

    public void read() {
        String result = mqttManager.sendReadMsg("{\"name\":\"DB300\"}");
        mqttUIService.changeUIRead(result);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void set() throws JsonProcessingException {
        DeviceInfo deviceInfo = new DeviceInfo(mqttMac.getText(),
                API.getText(),
                brokerIP.getText(),
                Integer.parseInt(brokerPort.getText()),
                userName.getText(),
                password.getText());
        mqttManager.sendSetMsg(new ObjectMapper().writeValueAsString(deviceInfo));
    }

    public void mqttServer(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/mqttServer.fxml", "MQTT 서버 설정", mouseEvent);
    }

    @Setter
    @Getter
    public static class DeviceInfo {
        private String dev_mac;
        private String api_url;
        private String broker_ip;
        private int broker_port;
        private String broker_user;
        private String broker_pass;

        public DeviceInfo(String dev_mac, String api_url, String broker_ip, int broker_port, String broker_user, String broker_pass) {
            this.dev_mac = dev_mac;
            this.api_url = api_url;
            this.broker_ip = broker_ip;
            this.broker_port = broker_port;
            this.broker_user = broker_user;
            this.broker_pass = broker_pass;
        }
    }

}