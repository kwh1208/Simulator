package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import dbps.dbps.service.MQTTUIService;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MQTTManager {

    static MQTTManager instance = null;
    LogService logService;
    MQTTUIService mqttUIService;

    public static MQTTManager getInstance(){
        if (instance == null){
            instance = new MQTTManager();
        }
        return instance;
    }
    @Setter
    private String brokerIp;
    @Setter
    private String brokerPort;
    @Setter
    private String username;
    @Setter
    private String password;
    private MqttClient client;

    List<String> subscribedTopics = new ArrayList<>();



    private MQTTManager() {
        logService = LogService.getLogService();
        mqttUIService = MQTTUIService.getMqttUIService();
    }

    public void connect(){
        String brokerUrl = "tcp://" + brokerIp + ":" + brokerPort;

        try {
            client = new MqttClient(brokerUrl, MqttClient.generateClientId(), new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            if (username != null){
                options.setUserName(username);
            }
            if (password != null){
                options.setPassword(password.toCharArray());
            }
            client.connect(options);

        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

//    public void publishGet(Map<String, Integer> moid) throws JsonProcessingException {
//        try {
//            if (client == null || !client.isConnected()) {
//                System.out.println("Client is not connected. Connect first.");
//                connect();
//            }
//
//            subscribe("");
//            // 메시지 생성
//            MqttMessage message = new MqttMessage(makeGetMsg(moid).getBytes());
//            message.setQos(0);
//
//            // 메시지 발행
//            System.out.println("Publishing message: " + message + " to topic: " + "");
//            client.publish("", message);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void newBrokerInfo(String MAC, String apiUrl, String brokerIp, String brokerPort, String clientId, String username, String password) {
//        try {
//            if (client == null || !client.isConnected()) {
//                System.out.println("Client is not connected. Connect first.");
//                connect();
//            }
//
//            // JSON 메시지 생성
//            ObjectMapper objectMapper = new ObjectMapper();
//            BrokerInfo brokerInfo = new BrokerInfo(MAC, apiUrl, brokerIp, Integer.parseInt(brokerPort), clientId, username, password);
//            String jsonMessage = objectMapper.writeValueAsString(brokerInfo);
//
//            // MQTT 메시지 생성
//            MqttMessage message = new MqttMessage(jsonMessage.getBytes());
//            message.setQos(0);
//
//            // 메시지 발행
//            System.out.println("Publishing message: " + jsonMessage + " to topic: " + "");
//            client.publish("", message);
//
//            // 구독 설정
//            subscribe("");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String makeGetMsg(Map<String, Integer> moid) throws JsonProcessingException {
//        // MQTT 메시지 객체 생성
//        MQTTMsgGet mqttDomain = MQTTMsgGet.builder()
//                .MSG_TYPE("GET")
//                .MSG_VER(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
//                .MSG_ID(String.valueOf(Instant.now().getEpochSecond()))
//                .MOID(moid) // MOID를 동적으로 추가
//                .build();
//
//        // JSON 직렬화
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(mqttDomain);
//    }
//
//    public void publishSet(Map<String, Object> moid) throws JsonProcessingException {
//        try {
//            if (client == null || !client.isConnected()) {
//                System.out.println("Client is not connected. Connect first.");
//                connect();
//            }
//
//            subscribe("");
//            // 메시지 생성
//            String jsonMessage = makeSetMsg(moid); // JSON 메시지 생성
//            MqttMessage message = new MqttMessage(jsonMessage.getBytes());
//            message.setQos(0);
//
//            // 메시지 발행
//            System.out.println("Publishing message: " + jsonMessage + " to topic: " + "");
//            client.publish("", message);
//            System.out.println("Message published!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String makeSetMsg(Map<String, Object> moid) throws JsonProcessingException {
//        // MQTT 메시지 객체 생성
//        MQTTMsgSet mqttDomain = dbps.dbps.MQTTMsgSet.builder()
//                .MSG_TYPE("SET")
//                .MSG_VER(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
//                .MSG_ID(String.valueOf(Instant.now().getEpochSecond()))
//                .MOID(moid)
//                .build();
//
//        // JSON 직렬화
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(mqttDomain);
//    }

    private void chkConnect(){
        if (client == null || !client.isConnected()) {
            connect();
        }
    }

    public void sendSetMsg(String payload) {
        chkConnect();

        try {
            // 메시지 전송
            MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
            message.setQos(0);
            client.publish("/set", message);

            return;

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public String sendReadMsg(String payload) {
        chkConnect();

        try {
            // 메시지 전송
            MqttMessage message = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
            message.setQos(0);
            client.publish("/sch", message);


            // 응답을 기다림
            return receiveReadMsg();

        } catch (MqttException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // 응답을 기다리는 subscribe (동기 반환)
    private String receiveReadMsg() {
        CompletableFuture<String> future = new CompletableFuture<>();

        try {
            client.subscribe("/sch_r", (receivedTopic, message) -> {
                String receivedMessage = new String(message.getPayload(), StandardCharsets.UTF_8);

                future.complete(receivedMessage);
            });

            // 최대 5초 동안 응답을 기다림
            return future.get(5, TimeUnit.SECONDS);

        } catch (MqttException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } catch (TimeoutException e) {
            return "Error: Timeout waiting for response";
        } catch (InterruptedException | ExecutionException e) {
            return "Error: " + e.getMessage();
        }
    }

    public void setMSG() {

    }
}