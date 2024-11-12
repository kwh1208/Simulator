package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MQTTManager {

    static MQTTManager instance = null;
    LogService logService;

    public static MQTTManager getInstance(){
        if (instance == null){
            instance = new MQTTManager();
        }
        return instance;
    }

    private MQTTManager() {
        logService = LogService.getLogService();
    }

    public String MQTT_BROKER = "";
    public String MQTT_TOPIC_SEND = "";
    public String MQTT_TOPIC_RECEIVED = "";
    public String MQTT_ID = "";
    public String MQTT_PWD = "";



    public void setMQTTInfo(String broker, String topic,String topic_r, String id, String pwd){
        MQTT_BROKER = broker;
        MQTT_TOPIC_SEND = topic;
        MQTT_ID = id;
        MQTT_PWD = pwd;
        MQTT_TOPIC_RECEIVED = topic_r;
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                MqttClient client = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true); // 클린 세션 사용
                connOpts.setUserName(MQTT_ID);
                connOpts.setPassword(MQTT_PWD.toCharArray());

                client.connect(connOpts);
                MqttMessage message = new MqttMessage(msg);
                message.setQos(0);
                client.publish(MQTT_TOPIC_SEND, message);

                CountDownLatch latch = new CountDownLatch(1);
                final StringBuilder response = new StringBuilder();

                // 응답 토픽 구독
                client.subscribe(MQTT_TOPIC_RECEIVED, (topic, responseMessage) -> {
                    // 메시지 수신 시 처리
                    response.append(new String(responseMessage.getPayload(), Charset.forName("EUC-KR")));
                    latch.countDown(); // 메시지 수신 시 카운트다운
                });

                // 타임아웃 시간 설정 (예: 5초)
                boolean receivedInTime = latch.await(5, TimeUnit.SECONDS);
                client.disconnect(); // 연결 종료

                if (receivedInTime) {
                    return response.toString(); // 응답을 받은 경우 반환
                } else {
                    throw new Exception("Timeout: No response received within the specified time.");
                }
            }
        };
    }

    public Task<String> sendASCMsg(String msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                MqttClient client = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true); // 클린 세션 사용
                connOpts.setUserName(MQTT_ID);
                connOpts.setPassword(MQTT_PWD.toCharArray());

                client.connect(connOpts);
                MqttMessage message = new MqttMessage(msg.getBytes(Charset.forName("EUC-KR")));
                message.setQos(0);
                client.publish(MQTT_TOPIC_SEND, message);

                CountDownLatch latch = new CountDownLatch(1);
                final StringBuilder response = new StringBuilder();

                // 응답 토픽 구독
                client.subscribe(MQTT_TOPIC_RECEIVED, (topic, responseMessage) -> {
                    // 메시지 수신 시 처리
                    response.append(new String(responseMessage.getPayload(), Charset.forName("EUC-KR")));
                    latch.countDown(); // 메시지 수신 시 카운트다운
                });

                // 타임아웃 시간 설정 (예: 5초)
                boolean receivedInTime = latch.await(5, TimeUnit.SECONDS);
                client.disconnect(); // 연결 종료

                if (receivedInTime) {
                    return response.toString(); // 응답을 받은 경우 반환
                } else {
                    throw new Exception("Timeout: No response received within the specified time.");
                }
            }
        };
    }

    public Task<String> sendJSONMsg(String msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                MqttClient client = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true); // 클린 세션 사용
                connOpts.setUserName(MQTT_ID);
                connOpts.setPassword(MQTT_PWD.toCharArray());

                //제이슨 메세지 만들기.

                client.connect(connOpts);
                MqttMessage message = new MqttMessage(msg.getBytes(Charset.forName("EUC-KR")));
                message.setQos(0);
                client.publish(MQTT_TOPIC_SEND, message);

                CountDownLatch latch = new CountDownLatch(1);
                final StringBuilder response = new StringBuilder();

                // 응답 토픽 구독
                client.subscribe(MQTT_TOPIC_RECEIVED, (topic, responseMessage) -> {
                    // 메시지 수신 시 처리
                    response.append(new String(responseMessage.getPayload(), Charset.forName("EUC-KR")));
                    latch.countDown(); // 메시지 수신 시 카운트다운
                });

                // 타임아웃 시간 설정 (예: 5초)
                boolean receivedInTime = latch.await(5, TimeUnit.SECONDS);
                client.disconnect(); // 연결 종료

                if (receivedInTime) {
                    return response.toString(); // 응답을 받은 경우 반환
                } else {
                    throw new Exception("Timeout: No response received within the specified time.");
                }
            }
        };
    }


}