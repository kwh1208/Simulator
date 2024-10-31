package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.Charset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    public String MQTT_CLIENT = "";
    public String MQTT_TOPIC = "";
    public int MQTT_Qos = 0;
    public String MQTT_ID = "";
    public String MQTT_PWD = "";


    public void setMQTTInfo(String broker, String client, String topic, int qos, String id, String pwd){
        MQTT_BROKER = broker;
        MQTT_CLIENT = client;
        MQTT_TOPIC = topic;
        MQTT_Qos = qos;
        MQTT_ID = id;
        MQTT_PWD = pwd;
    }


    public static void main(String[] args) {
        String broker = "tcp://broker.hivemq.com:1883"; // MQTT 브로커 주소
        String clientId = "JavaSampleClient";           // 클라이언트 ID
        String topic = "test/topic";                    // 테스트용 토픽
        String content = "Hello from Java!";            // 전송할 메시지
        int qos = 2;                                    // QoS: 0, 1, 2 중 하나 선택
        //변경가능한 부분, qos, 토픽, 브로커주소, 클라이언트 ID, 사용자 인증정보(ID/PWD)

        try {
            // MQTT 클라이언트 생성
            MqttClient client = new MqttClient(broker, clientId);

            // 연결 옵션 설정
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true); // 클린 세션 사용

            // 콜백 설정 (메시지 수신, 전달 완료, 연결 중단 처리)
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost! " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("Message arrived on topic: " + topic + " -> " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Delivery complete: " + token.isComplete());
                }
            });

            // 브로커 연결
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected");

            // 메시지 발행
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos); // QoS 설정
            client.publish(topic, message);
            System.out.println("Message published");

            // 토픽 구독 설정
            client.subscribe(topic);
            System.out.println("Subscribed to topic: " + topic);

            // 메시지 발행 및 구독 테스트 (10초간 대기 후 종료)
            Thread.sleep(10000);

            // 연결 종료
            client.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                MqttClient client = new MqttClient(MQTT_BROKER, MQTT_CLIENT);

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true); // 클린 세션 사용
                connOpts.setUserName(MQTT_ID);
                connOpts.setPassword(MQTT_PWD.toCharArray());

                CompletableFuture<String> futureResponse = new CompletableFuture<>();

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        System.out.println("Connection lost! " + cause.getMessage());
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) {
                        System.out.println("Message arrived on topic: " + topic + " -> " + new String(message.getPayload()));

                        // 여기에 응답을 처리하는 로직 추가
                        futureResponse.complete(new String(message.getPayload()));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        System.out.println("Delivery complete: " + token.isComplete());
                    }
                });

                client.connect(connOpts);
                MqttMessage message = new MqttMessage(msg);
                message.setQos(MQTT_Qos);
                client.publish(MQTT_TOPIC, message);

                client.subscribe("MQTT_TOPIC_RESPONSE");

                try {
                    // 5초 동안 응답 대기 후 반환, 타임아웃 시 TimeoutException 발생
                    return futureResponse.get(5, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    client.disconnect();
                    return "Timeout: No response received";
                } finally {
                    client.disconnect(); // 연결 종료
                }
            }
        };
    }

    public Task<String> sendASCMsg(String msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                MqttClient client = new MqttClient(MQTT_BROKER, MQTT_CLIENT);

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true); // 클린 세션 사용
                connOpts.setUserName(MQTT_ID);
                connOpts.setPassword(MQTT_PWD.toCharArray());

                CompletableFuture<String> futureResponse = new CompletableFuture<>();

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        System.out.println("Connection lost! " + cause.getMessage());
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) {
                        System.out.println("Message arrived on topic: " + topic + " -> " + new String(message.getPayload()));

                        // 여기에 응답을 처리하는 로직 추가
                        futureResponse.complete(new String(message.getPayload()));
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        System.out.println("Delivery complete: " + token.isComplete());
                    }
                });

                client.connect(connOpts);
                MqttMessage message = new MqttMessage(msg.getBytes(Charset.forName("EUC-KR")));
                message.setQos(MQTT_Qos);
                client.publish(MQTT_TOPIC, message);

                client.subscribe("MQTT_TOPIC_RESPONSE");

                try {
                    // 5초 동안 응답 대기 후 반환, 타임아웃 시 TimeoutException 발생
                    return futureResponse.get(5, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    client.disconnect();
                    return "Timeout: No response received";
                } finally {
                    client.disconnect(); // 연결 종료
                }
            }
        };
    }
}