package dbps.dbps.service.connectManager;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import lombok.Setter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static dbps.dbps.Constants.RESPONSE_LATENCY;
import static dbps.dbps.Constants.bytesToHex;

public class MQTTManager {

    private static MQTTManager instance = null;
    private final LogService logService;
    private final ConfigService configService;

    public static MQTTManager getInstance() {
        if (instance == null) {
            instance = new MQTTManager();
        }
        return instance;
    }

    // 설정값들
    @Setter
    private String brokerIp;
    @Setter
    private String brokerPort;
    @Setter
    private String username;
    @Setter
    private String password;

    private Mqtt5BlockingClient client;

    String sendTopic = "/msg";
    String receiveTopic = "/msg_r";

    private MQTTManager() {
        logService = LogService.getLogService();
        configService = ConfigService.getInstance();
    }// MQTT 브로커에 연결
    public void connect() {
        logService.updateInfoLog("MQTT 브로커 서버에 연결 시도중입니다.");
        if (brokerIp == null || brokerIp.isEmpty()) {
            brokerIp = configService.getProperty("mqtt_IP");
        }
        if (brokerPort == null || brokerPort.isEmpty()) {
            brokerPort = configService.getProperty("mqtt_Port");
        }

        int port = Integer.parseInt(brokerPort);
        try {
            // HiveMQ client 빌더 사용 (MQTT 5 Blocking Client)
            MqttClientBuilder builder = MqttClient.builder()
                    .serverHost(brokerIp)
                    .serverPort(port);

            if (username != null && !username.isEmpty()) {
                builder = (MqttClientBuilder) builder.useMqttVersion3().simpleAuth()
                        .username(username)
                        .password(password != null ? password.getBytes(StandardCharsets.UTF_8) : null);
            }

            client = builder.useMqttVersion5().buildBlocking();
            client.connect();
            logService.updateInfoLog("MQTT 브로커 서버 연결에 성공했습니다.");
        } catch (Exception e) {
            logService.updateInfoLog("MQTT 브로커 서버 연결에 실패했습니다.");
            throw new RuntimeException(e);
        }
    }

    // 연결 상태 확인 후 연결
    private void chkConnect() {
        if (client == null || !client.getState().isConnected()) {
            connect();
        }
    }

    // 단순 메시지 전송 (/set 토픽)
    public void sendSetMsg(String payload) {
        chkConnect();
        try {
            client.publishWith()
                    .topic("/set")
                    .payload(payload.getBytes(StandardCharsets.UTF_8))
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 요청 메시지 전송 후 응답 읽기 (/sch 토픽에 발행, /sch_r 토픽으로 응답)
    public String sendReadMsg(String payload) {
        chkConnect();
        try {
            client.publishWith()
                    .topic("/sch")
                    .payload(payload.getBytes(StandardCharsets.UTF_8))
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();
            return receiveReadMsg();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // 응답 대기 (최대 5초)
    private String receiveReadMsg() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            client.subscribeWith()
                    .topicFilter("/sch_r")
                    .send();
            Optional<Mqtt5Publish> optionalPublish = client.publishes(MqttGlobalPublishFilter.SUBSCRIBED)
                    .receive(RESPONSE_LATENCY, TimeUnit.SECONDS);
            if (optionalPublish.isEmpty()) {
                return "Error: Timeout waiting for response";
            }
            Mqtt5Publish publish = optionalPublish.get();
            return new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
        } catch (InterruptedException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // 비동기 Task로 메시지 전송 및 응답 처리 (/msg, /msg_r 사용)
    public Task<String> sendMsg(String payload) {
        return new Task<>() {
            @Override
            protected String call() {
                chkConnect();
                try {
                    String json = "{\"db_asc\":\"" + payload + "\"}";

                    client.publishWith()
                            .topic(sendTopic)
                            .payload(json.getBytes(Charset.forName("MS949")))
                            .qos(MqttQos.AT_MOST_ONCE)
                            .send();
                    logService.updateInfoLog("전송 메세지 : " + payload);

                    String result = receivedMsg();
                    logService.updateInfoLog("받은 메세지 : " + result);
                    result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }
        };
    }

    public Task<String> sendByteMsg(byte[] payload) {
        return new Task<>() {
            @Override
            protected String call() {
                chkConnect();
                try {
                    String hexString = bytesToHex(payload, payload.length);
                    String json = "{\"db_asc\":\"" + hexString + "\"}";

                    client.publishWith()
                            .topic(sendTopic)
                            .payload(json.getBytes(Charset.forName("MS949")))
                            .qos(MqttQos.AT_MOST_ONCE)
                            .send();

                    logService.updateInfoLog("전송 메세지 : " + hexString);

                    String result = receivedMsg();
                    result = result.substring(result.indexOf(":\"") + 2, result.indexOf("\"}"));
                    byte[] bytes = Base64.getDecoder().decode(result);
                    result = bytesToHex(bytes, bytes.length);
                    logService.updateInfoLog("받은 메세지 : " + result);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error: " + e.getMessage();
                }
            }
        };
    }

    public void sendByteMsgNoLog(byte[] payload) {
        chkConnect();
        try {
            String hexString = bytesToHex(payload, payload.length);
            String json = "{\"db_asc\":\"" + hexString + "\"}";

            client.publishWith()
                    .topic(sendTopic)
                    .payload(json.getBytes(Charset.forName("MS949")))
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();
            String result = receivedMsg();
            result = result.substring(result.indexOf(":\"") + 2, result.indexOf("\"}"));
            byte[] bytes = Base64.getDecoder().decode(result);
            bytesToHex(bytes, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendByteMsgShortLog(byte[] payload) {
        chkConnect();
        try {
            String hexString = bytesToHex(payload, payload.length);
            String json = "{\"db_asc\":\"" + hexString + "\"}";

            client.publishWith()
                    .topic(sendTopic)
                    .payload(json.getBytes(Charset.forName("MS949")))
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();
            String result = receivedMsg();
            result = result.substring(result.indexOf(":\"") + 2, result.indexOf("\"}"));
            byte[] bytes = Base64.getDecoder().decode(result);
            bytesToHex(bytes, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 응답 메시지 수신 (최대 5초 대기)
    private String receivedMsg() {
        try {
            // 구독 요청: receiveTopic에 대해 구독을 요청합니다.
            client.subscribeWith()
                    .topicFilter(receiveTopic)
                    .send();

            Optional<Mqtt5Publish> optionalPublish = client.publishes(MqttGlobalPublishFilter.SUBSCRIBED)
                    .receive(5, TimeUnit.SECONDS);
            if (optionalPublish.isEmpty()) {
                return "Error: Timeout waiting for response";
            }
            Mqtt5Publish publish = optionalPublish.get();
            return new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
        } catch (InterruptedException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}