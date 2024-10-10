//package dbps.dbps.service.connectManager;
//
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
//public class MQTTManager {
//    public static void main(String[] args) {
//        String broker = "tcp://broker.hivemq.com:1883"; // MQTT 브로커 주소
//        String clientId = "JavaSampleClient";           // 클라이언트 ID
//        String topic = "test/topic";                    // 테스트용 토픽
//        String content = "Hello from Java!";            // 전송할 메시지
//        int qos = 2;                                    // QoS: 0, 1, 2 중 하나 선택
//
//        try {
//            // MQTT 클라이언트 생성
//            MqttClient client = new MqttClient(broker, clientId);
//
//            // 연결 옵션 설정
//            MqttConnectOptions connOpts = new MqttConnectOptions();
//            connOpts.setCleanSession(true); // 클린 세션 사용
//
//            // 콜백 설정 (메시지 수신, 전달 완료, 연결 중단 처리)
//            client.setCallback(new MqttCallback() {
//                @Override
//                public void connectionLost(Throwable cause) {
//                    System.out.println("Connection lost! " + cause.getMessage());
//                }
//
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    System.out.println("Message arrived on topic: " + topic + " -> " + new String(message.getPayload()));
//                }
//
//                @Override
//                public void deliveryComplete(IMqttDeliveryToken token) {
//                    System.out.println("Delivery complete: " + token.isComplete());
//                }
//            });
//
//            // 브로커 연결
//            System.out.println("Connecting to broker: " + broker);
//            client.connect(connOpts);
//            System.out.println("Connected");
//
//            // 메시지 발행
//            System.out.println("Publishing message: " + content);
//            MqttMessage message = new MqttMessage(content.getBytes());
//            message.setQos(qos); // QoS 설정
//            client.publish(topic, message);
//            System.out.println("Message published");
//
//            // 토픽 구독 설정
//            client.subscribe(topic);
//            System.out.println("Subscribed to topic: " + topic);
//
//            // 메시지 발행 및 구독 테스트 (10초간 대기 후 종료)
//            Thread.sleep(10000);
//
//            // 연결 종료
//            client.disconnect();
//            System.out.println("Disconnected");
//        } catch (MqttException me) {
//            System.out.println("reason " + me.getReasonCode());
//            System.out.println("msg " + me.getMessage());
//            System.out.println("loc " + me.getLocalizedMessage());
//            System.out.println("cause " + me.getCause());
//            System.out.println("excep " + me);
//            me.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}