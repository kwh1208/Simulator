package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static dbps.dbps.Constants.*;

public class UDPManager {
    //IP, PORT에 정보 넣는 시점은 버튼 누를때 controller에서 파라미터로 넣을 예정
    @Getter
    @Setter
    private String IP;

    @Getter
    @Setter
    private int PORT;



    DatagramSocket socket = null;

    private static UDPManager udpManager = null;
    private static LogService logService;

    private UDPManager() {
        logService = LogService.getLogService();
        setIP(UDP_IP);
        setPORT(UDP_PORT);
    }

    public static UDPManager getUDPManager() {
        if (udpManager == null) {
            udpManager = new UDPManager();
        }
        return udpManager;
    }

    public Task<String> sendASCMsg(String msg, boolean utf8){
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                if (socket == null||socket.isClosed()) {
                    connect(IP, PORT);
                }
                DatagramPacket receivePacket;
                try{
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    byte[] sendByte = msg.getBytes(Charset.forName("EUC-KR"));

                    if (utf8) sendByte = msg.getBytes(StandardCharsets.UTF_8);
                    else if (ascUTF16) sendByte = msg.getBytes(StandardCharsets.UTF_16BE);
                    DatagramPacket sendPacket = new DatagramPacket(sendByte, sendByte.length, serverAddr, PORT);
                    logService.updateInfoLog("전송 메세지 :"+msg);
                    socket.send(sendPacket);
                    int totalBytesRead = 0;
                    byte[] receiveBuffer = new byte[1024];
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    while (true) {
                        try {
                            // 패킷 수신
                            socket.receive(receivePacket);
                            int bytesRead = receivePacket.getLength(); // 수신된 바이트 수
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;

                                // 데이터 처리 로직
                                if (dataReceivedIsComplete(receiveBuffer, totalBytesRead)) {
                                    break; // 수신 완료 조건 만족 시 루프 종료
                                }
                            }
                        } catch (java.net.SocketTimeoutException e) {
                            logService.errorLog("데이터 수신에 실패했습니다. 연결상태를 확인해주세요.");
                            throw e;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }

                    String result = new String(receiveBuffer, 0, totalBytesRead);
                    logService.updateInfoLog("받은 메세지 :"+result);
                    return result;
                }catch (IOException e){
                    //에러 처리
                    logService.errorLog(msg+"전송에 실패했습니다.");
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg){
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null||socket.isClosed()) {
                    connect(IP, PORT);
                }
                DatagramPacket receivePacket;
                try {
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    logService.updateInfoLog("전송 메세지 :"+bytesToHex(msg, msg.length));
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
                    socket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    int totalBytesRead = 0;
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    while (true) {
                        try {
                            socket.receive(receivePacket);
                            int bytesRead = receivePacket.getLength();
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;
                                // 데이터 처리 로직
                                if (dataReceivedIsCompleteHex(receiveBuffer, totalBytesRead)) {
                                    break; // 수신 완료 조건 만족 시 루프 종료
                                }
                            }
                        } catch (SocketTimeoutException e) {
                            logService.errorLog("데이터 수신에 실패했습니다. 연결상태를 확인해주세요.");
                            throw new RuntimeException();
                        }
                    }
                    String result = bytesToHex(receivePacket.getData(), receivePacket.getLength());
                    logService.updateInfoLog("받은 메세지 :"+result);
                    return result;
                } catch (IOException e) {
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> sendMsgAndGetMsgByteNoLog(byte[] msg){
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null||socket.isClosed()) {
                    connect(IP, PORT);
                }
                DatagramPacket receivePacket;
                try {
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
                    socket.send(sendPacket);

                    byte[] receiveBuffer = new byte[1024];
                    int totalBytesRead = 0;
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    while (true) {
                        try {
                            socket.receive(receivePacket);
                            int bytesRead = receivePacket.getLength();
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;
                                // 데이터 처리 로직
                                if (dataReceivedIsCompleteHex(receiveBuffer, totalBytesRead)) {
                                    break; // 수신 완료 조건 만족 시 루프 종료
                                }
                            }
                        } catch (SocketTimeoutException e) {
                            logService.errorLog("데이터 수신에 실패했습니다. 연결상태를 확인해주세요.");
                            throw new RuntimeException();
                        }
                    }
                    return bytesToHex(receivePacket.getData(), receivePacket.getLength());
                } catch (IOException e) {
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> send300MsgAndGetMsgByte(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws IOException {
                if (socket == null||socket.isClosed()) {
                    connect300(); // 소켓 연결
                }

                DatagramPacket receivePacket;
                List<String> receivedMessages = new ArrayList<>();
                try {
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
                    socket.send(sendPacket); // 메시지 전송
                    byte[] receiveBuffer = new byte[1024];

                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    while (true) {
                        try {
                            socket.receive(receivePacket);
                            int bytesRead = receivePacket.getLength();
                            if (bytesRead > 0) {
                                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                                receivedMessages.add(message); // 리스트에 메시지 추가
                            }
                        } catch (SocketTimeoutException e) {
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    // 받은 메시지를 합쳐서 반환
                    String result = String.join("", receivedMessages);
                    return result;

                } catch (IOException e) {
                    throw e;
                } finally {
                    disconnect(); // 자원 정리
                }
            }
        };
    }

    public void connect300(){
        this.IP = "255.255.255.255";
        this.PORT = 5108;
        try {
            if (socket == null) {
                socket = new DatagramSocket(5109);
            }
            socket.setBroadcast(true);
            socket.setSoTimeout(RESPONSE_LATENCY*1000);
        } catch (SocketException e) {
            e.printStackTrace();
            logService.errorLog("IP: " + IP + ", PORT: " + PORT+"열기에 실패했습니다.");
        }
    }

    //접속하기
    public void connect(String IP, int PORT){
        logService.updateInfoLog("UDP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;
        try {
            if (socket == null) {
                socket = new DatagramSocket(5109);
            }
            socket.setBroadcast(true);
            socket.setSoTimeout(RESPONSE_LATENCY*1000);
        } catch (SocketException e) {
            logService.errorLog("IP: " + IP + ", PORT: " + PORT+"열기에 실패했습니다.");
        }
    }

    //접속끊기
    public void disconnect() {
        socket.close();
        socket = null;
        logService.updateInfoLog("UDP 서버를 종료합니다. IP: " + IP + ", PORT: " + PORT);
    }
}
