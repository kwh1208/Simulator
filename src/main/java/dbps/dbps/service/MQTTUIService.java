package dbps.dbps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.TextField;

public class MQTTUIService {
    private static MQTTUIService mqttUIService;

    private MQTTUIService() {
    }

    public static MQTTUIService getMqttUIService() {
        if (mqttUIService == null) {
            mqttUIService = new MQTTUIService();
        }
        return mqttUIService;
    }

    public TextField mqttMac;
    public TextField name;
    public TextField API;
    public TextField brokerIP;
    public TextField brokerPort;
    public TextField userName;
    public TextField password;


    public void setMQTTControllerUI(TextField mqttMac, TextField name, TextField api, TextField IP, TextField port, TextField userName, TextField password){
        this.mqttMac = mqttMac;
        this.name = name;
        this.API = api;
        this.brokerIP = IP;
        this.brokerPort = port;
        this.userName = userName;
        this.password = password;
    }
    //175.197.68.72 > 112.220.234.180:1883,9152,55675

    public void changeUIRead(String result){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);

            String devMac = jsonNode.get("dev_mac").asText();
            String devName = jsonNode.get("dev_name").asText();
            String API_tmp = jsonNode.get("api_url").asText();
            String brokerIp = jsonNode.get("broker_ip").asText();
            String brokerPort_tmp = jsonNode.get("broker_port").asText();
            String userName_tmp = jsonNode.get("broker_user").asText();
            String password_tmp = jsonNode.get("broker_pass").asText();

            Platform.runLater(()->{
                mqttMac.setText(devMac);
                name.setText(devName);
                API.setText(API_tmp);
                brokerIP.setText(brokerIp);
                brokerPort.setText(brokerPort_tmp);
                userName.setText(userName_tmp);
                password.setText(password_tmp);
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
