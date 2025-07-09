package dbps.dbps.service.connectManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.LogService;
import dbps.dbps.service.ResourceManager;
import javafx.concurrent.Task;
import lombok.Setter;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dbps.dbps.Constants.*;

public class MQTTManager {

    private static MQTTManager instance = null;
    private final LogService logService;
    private final ConfigService configService;
    private final ResourceBundle bundle;

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

    private Mqtt3BlockingClient client;

    String sendTopic = "/db_msg";
    String receiveTopic = "/db_msg_r";

    private MQTTManager() {
        logService = LogService.getLogService();
        configService = ConfigService.getInstance();
        bundle = ResourceManager.getInstance().getBundle();
    }// MQTT 브로커에 연결
    public void connect() {
        logService.updateInfoLog("MQTT 브로커 서버에 연결 시도중입니다.");
        try {
            if (brokerIp == null || brokerIp.isEmpty()) {
                brokerIp = configService.getProperty("mqtt_IP");
            }
            if (brokerPort == null || brokerPort.isEmpty()) {
                brokerPort = configService.getProperty("mqtt_Port");
            }

            int port = Integer.parseInt(brokerPort);
            try {
                // HiveMQ client 빌더 사용 (MQTT 5 Blocking Client)
                MqttClientBuilder defaultBuilder = MqttClient.builder()
                        .serverHost(brokerIp)
                        .serverPort(port);

                if (username != null && !username.isEmpty()) {
                    // MQTT 3 빌더로 변환
                    Mqtt3ClientBuilder mqtt3Builder = defaultBuilder.useMqttVersion3();
                    mqtt3Builder = mqtt3Builder.simpleAuth()
                            .username(username)
                            .password(password != null ? password.getBytes(StandardCharsets.UTF_8) : null)
                            .applySimpleAuth();

                    client = mqtt3Builder.buildBlocking();
                } else {
                    client = defaultBuilder.useMqttVersion3().buildBlocking();
                }

                client.connect();
                logService.updateInfoLog("MQTT 브로커 서버 연결에 성공했습니다.");
                subscribeInitialTopics();
            } catch (Exception e) {
                logService.updateInfoLog("MQTT 브로커 서버 연결에 실패했습니다.");
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            if (e.getMessage().contains("NOT_AUTHORIZED")){
                logService.updateInfoLog("아이디와 비밀번호를 확인해주세요.");
            }
        }
    }

    private void subscribeInitialTopics() {
        try {
            client.toAsync().subscribeWith()
                    .topicFilter("/db_sch_r")
                    .send();

            client.toAsync().subscribeWith()
                    .topicFilter("/db_msg_r")
                    .send();

        } catch (Exception e) {

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
                    .topic("/db_set")
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
                    .topic("/db_sch")
                    .payload(payload.getBytes(StandardCharsets.UTF_8))
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();
            return receiveReadMsg();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    // 응답 대기 (최대 5초)
    private String receiveReadMsg() {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            client.toAsync().subscribeWith()
                    .topicFilter("/db_sch_r")
                    .callback(publish -> {
                        String payload = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
                        future.complete(payload);
                    })
                    .send();

            return future.get(RESPONSE_LATENCY, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return "Error: Timeout waiting for response";
        } catch (InterruptedException | ExecutionException e) {
            return "Error";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
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
                    if (result.contains("Error")){
                        logService.errorLog(bundle.getString("Error"));
                        return null;
                    }
                    logService.updateInfoLog("받은 메세지 : " + result);
                    result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error";
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
                    String b64 = Base64.getEncoder().encodeToString(payload);

                    String json = "{\"db_hex\":\"" + b64 + "\"}";

                    client.publishWith()
                            .topic(sendTopic)
                            .payload(json.getBytes(Charset.forName("MS949")))
                            .qos(MqttQos.AT_MOST_ONCE)
                            .send();

                    logService.updateInfoLog("전송 메세지 : " + json);

                    String result = receivedMsg();
                    if (result.contains("Error")){
                        logService.errorLog(bundle.getString("Error"));
                        return "Error";
                    }
                    logService.updateInfoLog("받은 메세지 : " + result);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error";
                }
            }
        };
    }

    public void sendByteMsgNoLog(byte[] payload) {
        chkConnect();
        try {
            String b64 = Base64.getEncoder().encodeToString(payload);

            // 2) JSON으로 감싸기
            String json = "{\"db_hex\":\"" + b64 + "\"}";

            if (Thread.currentThread().isInterrupted()) {
                throw new RuntimeException();
            }

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

    public void sendByteMsgShortLog(byte[] payload) throws InterruptedIOException {
        chkConnect();
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException("전송이 취소되었습니다.");
        }
        try {
            String b64 = Base64.getEncoder().encodeToString(payload);

            String json = "{\"db_hex\":\"" + b64 + "\"}";

            if (cancel) {
                throw new InterruptedIOException("전송이 취소되었습니다.");
            }

            client.publishWith()
                    .topic(sendTopic)
                    .payload(json.getBytes(Charset.forName("MS949")))
                    .qos(MqttQos.AT_MOST_ONCE)
                    .send();

            if (cancel) {
                throw new InterruptedIOException("전송이 취소되었습니다.");
            }

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
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            // 1) 구독 요청 + 콜백 등록
            client.toAsync().subscribeWith()
                    .topicFilter(receiveTopic)
                    .callback(publish -> {
                        String msg = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
                        try {
                            // 2) { 로 시작하면, RX 뒤에 있는 ![ ... !] 프레임만 추출
                            if (msg.startsWith("{") && msg.contains("![")) {
                                if (msg.contains("RX") && msg.contains("![") && msg.contains("!]")) {
                                    int indexTX = msg.indexOf("TX");
                                    msg = msg.substring(indexTX);
                                    msg = msg.substring(msg.indexOf("!["), msg.indexOf("!]")+2);
                                }
                                int start = msg.indexOf("![");
                                int end = msg.indexOf("!]");
                                if (start != -1 && end != -1 && end > start) {
                                    future.complete(msg.substring(start, end + 2));
                                }
                            } else {
                                ObjectMapper mapper = new ObjectMapper();
                                JsonNode root = mapper.readTree(msg);
                                msg = root.get("db_hex").asText();

                                byte[] decodedBytes = Base64.getDecoder().decode(msg);

                                msg = bytesToHex(decodedBytes, decodedBytes.length);

                                if (!msg.startsWith("10")){
                                    StringBuilder result = new StringBuilder();
                                    String[] hexArray = msg.split(" ");
                                    for (String hex : hexArray) {
                                        int byteVal = Integer.parseInt(hex, 16);
                                        result.append((char) byteVal);
                                    }

                                    msg = result.toString();
                                    msg = msg.toUpperCase();

                                    if (msg.contains(">DIBD")){
                                        int start = result.indexOf("<");
                                        int end = result.indexOf("port:");

                                        if (start != -1 && end != -1) {
                                            end += 10;
                                            msg = result.substring(start, end);
                                        }
                                    }

                                    if (msg.contains("52 58 28")) {
                                        System.out.println(111);

                                        String startMarker = "10 02";
                                        String endMarker = "10 03";
                                        int startIndex = msg.indexOf(startMarker);
                                        int endIndex = msg.indexOf(endMarker);

                                        msg = msg.substring(startIndex, endIndex + endMarker.length());
                                        System.out.println("result1 = " + msg);
                                    }

                                        int txIndex = result.indexOf("TX(");


                                        if (txIndex != -1) {
                                            // "10 02" 이후부터 "10 03"까지 탐색
                                            int start = result.indexOf("10 02", txIndex);
                                            int end = result.indexOf("10 03", start);

                                            if (start != -1 && end != -1) {
                                                end += "10 03".length(); // "10 03"까지 포함
                                                msg = msg.substring(start, end);
                                            }}
                                }


                                // 3) 프레임 마커 정의
                                String startMarker = "10 02";
                                String endMarker = "10 03";

                                // 4) 시작/끝 인덱스 찾기
                                int startIdx = msg.indexOf(startMarker);
                                int endIdx = msg.indexOf(endMarker);

                                System.out.println("msg = " + msg);

                                // 5) 잘라내기
                                if (startIdx != -1 && endIdx != -1 && endIdx + endMarker.length() <= msg.length()) {
                                    future.complete(msg.substring(startIdx, endIdx + endMarker.length()));
                                } else {
                                    future.complete(msg);
                                }
                            }
                        } catch (Exception ex) {
                            future.completeExceptionally(ex);
                        }
                    })
                    .send();

            // 2) 메시지 대기
            return future.get(5, TimeUnit.SECONDS);

        } catch (TimeoutException e) {

            return "Error";
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return "Error";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private int extractNumberAfterTXBeforeByteHex(String input) {
        // "TX" 뒤의 "byte" 앞 숫자를 찾는 정규식
        Pattern pattern = Pattern.compile("TX.*?(\\d+)\\s*byte");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String number = matcher.group(1); // 첫 번째 그룹에서 숫자 추출
            return Integer.parseInt(number); // 숫자를 Integer로 변환하여 반환
        }

        return -1;
    }


    public void disconnect() {
        if (client != null && client.getState().isConnected()) {
            try {
                // 블로킹 클라이언트의 경우 간단히 disconnect() 호출
                client.disconnect();
                logService.updateInfoLog("MQTT 브로커 서버 연결을 해제했습니다.");
            } catch (Exception e) {
                logService.updateInfoLog("MQTT 브로커 연결 해제 중 오류 발생");
            } finally {
                client = null;
            }
        } else {
            logService.updateInfoLog("MQTT 브로커 연결이 되어 있지 않아 해제할 필요가 없습니다.");
        }
    }

}
