package dbps.dbps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbps.dbps.Simulator;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.MQTTUIService;
import dbps.dbps.service.connectManager.MQTTManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.CONNECT_TYPE;
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
    public RadioButton UDPRadioBtn;
    public RadioButton TCPRadioBtn;
    ToggleGroup communicationGroup;

    HexMsgTransceiver hexTransceiver;
    MQTTManager mqttManager;
    MQTTUIService mqttUIService;
    UDPManager udpManager;

    @FXML
    public void initialize() {
        hexTransceiver = HexMsgTransceiver.getInstance();
        mqttManager = MQTTManager.getInstance();
        mqttUIService = MQTTUIService.getMqttUIService();
        udpManager = UDPManager.getUDPManager();

        mqttAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());
        mqttUIService.setMQTTControllerUI(mqttMac, name, API, brokerIP, brokerPort, userName, password);

        communicationGroup = new ToggleGroup();
        UDPRadioBtn.setToggleGroup(communicationGroup);
        TCPRadioBtn.setToggleGroup(communicationGroup);
        TCPRadioBtn.setSelected(true);
    }

    public void read() {
        if (UDPRadioBtn.isSelected()) {
            Task<String> stringTask = udpManager.sendASCMsg("/sch{\"name\":\"DB300\"}", false);
            Thread thread = new Thread(stringTask);
            thread.start();
            stringTask.setOnSucceeded(e -> {
                try {
                    System.out.println(stringTask.get());
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
            });

            return;
        }
        CONNECT_TYPE = "mqtt";
        String result = mqttManager.sendReadMsg("{\"name\":\"DB300\"}");
        mqttUIService.changeUIRead(result);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void set() throws JsonProcessingException {
        if (TCPRadioBtn.isSelected()) {
            DeviceInfo deviceInfo = new DeviceInfo(name.getText(),
                    mqttMac.getText(),
                    API.getText(),
                    brokerIP.getText(),
                    Integer.parseInt(brokerPort.getText()),
                    userName.getText(),
                    password.getText());
            mqttManager.sendSetMsg(new ObjectMapper().writeValueAsString(deviceInfo));
        } else {
            try {
                DeviceInfo deviceInfo = new DeviceInfo(name.getText(),
                        mqttMac.getText(),
                        API.getText(),
                        brokerIP.getText(),
                        Integer.parseInt(brokerPort.getText()),
                        userName.getText(),
                        password.getText()
                );

                // 메시지 구조 : 토픽과 payload를 포함하는 Map (여기서는 topic을 "/set"으로 지정)
                Map<String, Object> udpMsg = new HashMap<>();
                udpMsg.put("payload", deviceInfo);

                // JSON 변환: Jackson ObjectMapper를 사용하여 문자열로 변환합니다.
                ObjectMapper mapper = new ObjectMapper();
                String jsonPayload = mapper.writeValueAsString(udpMsg);

                // 변환된 JSON 문자열을 바이트 배열로 변환하여 UDP 전송 메서드 호출
                new Thread(udpManager.sendMQTTMsgAndGetMsgByte(jsonPayload.getBytes(StandardCharsets.UTF_8), Integer.parseInt(brokerPort.getText()))).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void mqttServer(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/mqttServer.fxml", "MQTT 서버 설정", mouseEvent);
    }

    @Setter
    @Getter
    public static class DeviceInfo {
        private String dev_name;
        private String dev_mac;
        private String api_url;
        private int api_delay;
        private int uart_comm;
        private String broker_ip;
        private int broker_port;
        private String broker_user;
        private String broker_pass;

        public DeviceInfo(String dev_name, String dev_mac, String api_url, String broker_ip, int broker_port, String broker_user, String broker_pass) {
            this.dev_name = dev_name;
            this.dev_mac = dev_mac;
            this.api_url = api_url;
            this.api_delay = 10;
            this.uart_comm = 1;
            this.broker_ip = broker_ip;
            this.broker_port = broker_port;
            this.broker_user = broker_user;
            this.broker_pass = broker_pass;
        }
    }

}
