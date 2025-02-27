package dbps.dbps.service.connectManager;

import dbps.dbps.service.ConfigService;
import dbps.dbps.service.LogService;
import dbps.dbps.service.MQTTUIService;
import javafx.concurrent.Task;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static dbps.dbps.Constants.bytesToHex;

public class MQTTManager {

    static MQTTManager instance = null;
    LogService logService;
    MQTTUIService mqttUIService;
    ConfigService configService;

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

    String sendTopic = "/msg";
    String receiveTopic = "/msg_r";



    private MQTTManager() {
        logService = LogService.getLogService();
        mqttUIService = MQTTUIService.getMqttUIService();
        configService = ConfigService.getInstance();
    }

    public void connect(){
        logService.updateInfoLog("MQTT 브로커 서버에 연결 시도중입니다.");
        if (brokerIp == null || brokerIp.isEmpty()){
            brokerIp = configService.getProperty("mqtt_IP");
        }

        if (brokerPort == null || brokerPort.isEmpty()){
            brokerPort = configService.getProperty("mqtt_Port");
        }

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
            logService.updateInfoLog("MQTT 브로커 서버 연결에 성공했습니다.");
        } catch (MqttException e) {
            logService.updateInfoLog("MQTT 브로커 서버 연결에 실패했습니다.");
            throw new RuntimeException(e);
        }
    }

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

    public Task<String> sendMsg(String payload) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                chkConnect();
                try {
                    MqttMessage message = new MqttMessage(payload.getBytes(Charset.forName("MS949")));
                    message.setQos(0);
                    client.publish(sendTopic, message);
                    logService.updateInfoLog("전송 메세지 : " + payload);

                    String result = receivedMsg();
                    logService.updateInfoLog("받은 메세지 : " + result);
                    result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    return result;

                } catch (MqttException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }
        };
    }

    public Task<String> sendByteMsg(byte[] payload) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                chkConnect();
                try {
                    MqttMessage message = new MqttMessage(payload);
                    message.setQos(0);
                    client.publish(sendTopic, message);

                    logService.updateInfoLog("전송 메세지 : " + new String(payload, Charset.forName("MS949")));

                    String result = receivedMsg();
                    result = result.substring(result.indexOf(":\"")+2, result.indexOf("\"}"));
                    byte[] bytes = Base64.getDecoder().decode(result);
                    result = bytesToHex(bytes, bytes.length);
                    logService.updateInfoLog("받은 메세지 : "+result);
                    return result;
                } catch (MqttException e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }
        };
    }

    public void sendByteMsgNoLog(byte[] payload) {
        chkConnect();
        try {
            MqttMessage message = new MqttMessage(payload);
            message.setQos(0);
            client.publish(sendTopic, message);
            String result = receivedMsg();
            result = result.substring(result.indexOf(":\"")+2, result.indexOf("\"}"));
            byte[] bytes = Base64.getDecoder().decode(result);
            bytesToHex(bytes, bytes.length);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendByteMsgShortLog(byte[] payload) {
        chkConnect();
        try {
            MqttMessage message = new MqttMessage(payload);
            message.setQos(0);

            client.publish(sendTopic, message);
            String result = receivedMsg();
            result = result.substring(result.indexOf(":\"")+2, result.indexOf("\"}"));
            byte[] bytes = Base64.getDecoder().decode(result);
            bytesToHex(bytes, bytes.length);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private String receivedMsg() {
        CompletableFuture<String> future = new CompletableFuture<>();

        try {
            client.subscribe(receiveTopic, (receivedTopic, message) -> {
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
}