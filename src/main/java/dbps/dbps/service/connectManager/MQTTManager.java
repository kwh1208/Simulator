package dbps.dbps.service.connectManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbps.dbps.BrokerInfo;
import dbps.dbps.MQTTMsgGet;
import dbps.dbps.MQTTMsgSet;
import dbps.dbps.service.LogService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class MQTTManager {

    static MQTTManager instance = null;
    LogService logService;

    public static MQTTManager getInstance(){
        if (instance == null){
            instance = new MQTTManager();
        }
        return instance;
    }

    private String brokerIp;
    private String brokerPort;
    private String clientId;
    private String username;
    private String password;
    private MqttClient client;
    private String topic;
    private String topicR;

    private MQTTManager() {
        logService = LogService.getLogService();
    }

    public void connect(){
        String brokerUrl = "tcp://" + brokerIp + ":" + brokerPort;

        try {
            client = new MqttClient(brokerUrl, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            client.connect(options);


        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void publishGet(List<String> moid) throws JsonProcessingException {
        try {
            if (client == null || !client.isConnected()) {
                System.out.println("Client is not connected. Connect first.");
                connect();
            }

            subscribe();
            // 메시지 생성
            MqttMessage message = new MqttMessage(makeGetMsg(moid).getBytes());
            message.setQos(0);

            // 메시지 발행
            System.out.println("Publishing message: " + message + " to topic: " + topic);
            client.publish(topic, message);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newBrokerInfo(String MAC, String apiUrl, String brokerIp, String brokerPort, String clientId, String username, String password) {
        try {
            if (client == null || !client.isConnected()) {
                System.out.println("Client is not connected. Connect first.");
                connect();
            }

            // JSON 메시지 생성
            ObjectMapper objectMapper = new ObjectMapper();
            BrokerInfo brokerInfo = new BrokerInfo(MAC, apiUrl, brokerIp, Integer.parseInt(brokerPort), clientId, username, password);
            String jsonMessage = objectMapper.writeValueAsString(brokerInfo);

            // MQTT 메시지 생성
            MqttMessage message = new MqttMessage(jsonMessage.getBytes());
            message.setQos(0);

            // 메시지 발행
            System.out.println("Publishing message: " + jsonMessage + " to topic: " + topic);
            client.publish(topic, message);

            // 구독 설정
            subscribe();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makeGetMsg(List<String> moid) throws JsonProcessingException {
        // MQTT 메시지 객체 생성
        MQTTMsgGet mqttDomain = MQTTMsgGet.builder()
                .MSG_TYPE("GET")
                .MSG_VER(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .MSG_ID(String.valueOf(Instant.now().getEpochSecond()))
                .MOID(moid) // MOID를 동적으로 추가
                .build();

        // JSON 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mqttDomain);
    }

    public void publishSet(Map<String, Object> moid) throws JsonProcessingException {
        try {
            if (client == null || !client.isConnected()) {
                System.out.println("Client is not connected. Connect first.");
                connect();
            }

            subscribe();
            // 메시지 생성
            String jsonMessage = makeSetMsg(moid); // JSON 메시지 생성
            MqttMessage message = new MqttMessage(jsonMessage.getBytes());
            message.setQos(0);

            // 메시지 발행
            System.out.println("Publishing message: " + jsonMessage + " to topic: " + topic);
            client.publish(topic, message);
            System.out.println("Message published!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String makeSetMsg(Map<String, Object> moid) throws JsonProcessingException {
        // MQTT 메시지 객체 생성
        MQTTMsgSet mqttDomain = dbps.dbps.MQTTMsgSet.builder()
                .MSG_TYPE("SET")
                .MSG_VER(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .MSG_ID(String.valueOf(Instant.now().getEpochSecond()))
                .MOID(moid)
                .build();

        // JSON 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mqttDomain);
    }


    public void subscribe() {
        try {
            if (client == null || !client.isConnected()) {
                System.out.println("Client is not connected. Connect first.");
                return;
            }

            // 토픽 구독
            System.out.println("Subscribing to topicR: " + topicR);
            client.subscribe(topicR, (receivedTopic, message) -> {
                // 메시지 처리
                System.out.println("Received message from topicR: " + receivedTopic);
                logService.updateInfoLog(new String(message.getPayload()));
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}