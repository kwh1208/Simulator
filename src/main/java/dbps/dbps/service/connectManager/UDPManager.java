package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static dbps.dbps.Constants.*;

public class UDPManager {
    //IP, PORT에 정보 넣는 시점은 버튼 누를때 controller에서 파라미터로 넣을 예정

    private String IP;

    private int PORT;

    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    DatagramSocket socket = null;

    private static UDPManager udpManager = null;
    private static LogService logService;

    private UDPManager() {
        logService = LogService.getLogService();
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
                if (socket == null) {
                    connect(IP, PORT);
                }

                DatagramPacket receivePacket;
                try{
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(Charset.forName("EUC-KR")), msg.getBytes().length, serverAddr, PORT);
                    if (utf8) sendPacket = new DatagramPacket(msg.getBytes(StandardCharsets.UTF_8), msg.getBytes(StandardCharsets.UTF_8).length, serverAddr, PORT);
                    else if (ascUTF16) {
                        sendPacket = new DatagramPacket(msg.getBytes(StandardCharsets.UTF_16BE), msg.getBytes(StandardCharsets.UTF_16BE).length, serverAddr, PORT);
                    }
                    socket.send(sendPacket);

                    long startTime = System.currentTimeMillis();
                    byte[] receiveBuffer = new byte[1024];
                    int totalBytesRead = 0;
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                    while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000){
                        socket.receive(receivePacket);
                        int bytesRead = receivePacket.getLength();  // 수신된 바이트 수
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;
                            startTime = System.currentTimeMillis();  // 수신 시간 갱신

                            // 데이터 처리 로직
                            if (dataReceivedIsComplete(receiveBuffer, totalBytesRead)) {
                                break;
                            }
                        }
                        Thread.sleep(50);
                    }
                    return new String(receivePacket.getData(), 0, receivePacket.getLength());
                }catch (IOException | InterruptedException e){
                    //에러 처리
                    logService.errorLog(msg+"전송에 실패했습니다.");
                    return "에러코드";
                }
            }
        };
    }

    private boolean dataReceivedIsComplete(byte[] buffer, int length) {
        return length > 0 && buffer[length - 1]==(byte) ']' && buffer[length - 2]==(byte) '!';
    }

    private boolean dataReceivedIsCompleteHex(byte[] buffer, int length) {
        return length >= 2 && buffer[length - 2] == (byte) 0x10 && buffer[length - 1] == (byte) 0x03;
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg){
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect(IP, PORT);
                }

                DatagramPacket receivePacket;
                try {
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
                    socket.send(sendPacket);

                    long startTime = System.currentTimeMillis();
                    byte[] receiveBuffer = new byte[1024];
                    int totalBytesRead = 0;
                    receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                    while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000) {
                        socket.receive(receivePacket);
                        int bytesRead = receivePacket.getLength();// 수신된 바이트 수
                        System.out.println("bytesRead = " + bytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;
                            startTime = System.currentTimeMillis();  // 수신 시간 갱신
                            // 데이터 처리 로직
                            if (dataReceivedIsCompleteHex(receiveBuffer, totalBytesRead)) {
                                break;
                            }
                        }
                        Thread.sleep(50);
                    }
                    String result = bytesToHex(receivePacket.getData(), receivePacket.getLength());
                    System.out.println("result = " + result);
                    return bytesToHex(receivePacket.getData(), receivePacket.getLength());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
                return null;
            }
        };
    }

    public Task<String> send300MsgAndGetMsgByte(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect300(); // 소켓 연결
                }

                DatagramPacket receivePacket;
                List<String> receivedMessages = new ArrayList<>();
                try {
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
                    socket.send(sendPacket); // 메시지 전송

                    long startTime = System.currentTimeMillis();
                    byte[] receiveBuffer = new byte[1024];

                    while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000) {
                        try {
                            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                            socket.receive(receivePacket); // 메시지 수신

                            int bytesRead = receivePacket.getLength(); // 수신된 바이트 수
                            if (bytesRead > 0) {
                                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                                receivedMessages.add(message); // 리스트에 메시지 추가

                                startTime = System.currentTimeMillis(); // 수신 시간 갱신
                            }
                        } catch (SocketTimeoutException e) {
                            break;
                        }
                        Thread.sleep(50); // 짧은 대기
                    }

                    // 받은 메시지를 합쳐서 반환
                    String result = String.join("", receivedMessages); // 리스트의 메시지를 연결
                    return result;

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    disconnect(); // 자원 정리
                }
                return null; // 예외 발생 시 null 반환
            }
        };
    }

    public void connect300() throws SocketException {
        this.IP = "255.255.255.255";
        this.PORT = 5108;
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        socket.setSoTimeout(RESPONSE_LATENCY*1000);
    }

    //접속하기
    public void connect(String IP, int PORT){
        logService.updateInfoLog("UDP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;
        DatagramPacket receivePacket;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(RESPONSE_LATENCY*1000);
            InetAddress serverAddr = InetAddress.getByName(IP);
            DatagramPacket sendPacket = new DatagramPacket(CONNECT_START, CONNECT_START.length, serverAddr, PORT);
            socket.send(sendPacket);
            logService.updateInfoLog("전송 메세지: 10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03");
            byte[] receiveBuffer = new byte[1024];
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            byte[] receiveData = receivePacket.getData();

            String receivedMsg = bytesToHex(receiveData, receivePacket.getLength());
            logService.updateInfoLog("수신 메세지: " + receivedMsg);
            if (receivedMsg.equals("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03 ")) {
                logService.updateInfoLog("UDP 서버에 연결되었습니다. IP: " + IP + ", PORT: " + PORT);
            } else {
                logService.errorLog("UDP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
            }
        }catch (IOException e){
            //에러처리
            logService.errorLog("UDP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
        } finally {
            disconnect();
        }
    }

    //접속끊기
    public void disconnect() {
        socket.close();
        socket = null;
    }
}
