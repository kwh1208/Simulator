package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

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

    public Task<String> sendASCMsg(String msg){
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    logService.warningLog("UDP 소켓이 열려있지 않습니다.");
                    connect(IP, PORT);
                }

                DatagramPacket receivePacket;
                try{
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(Charset.forName("EUC-KR")), msg.getBytes().length, serverAddr, PORT);
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

    public Task<String> sendMsgAndGetMsgByte(byte[] msg){
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    logService.warningLog("UDP 소켓이 열려있지 않습니다.");
                    connect(IP, PORT);
                }

                DatagramPacket receivePacket;
                try{
                    InetAddress serverAddr = InetAddress.getByName(IP);
                    DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, serverAddr, PORT);
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
                    System.out.println(receivePacket.getData());
                    return bytesToHex(receivePacket.getData(), receivePacket.getLength());
                }catch (IOException | InterruptedException e){
                    //에러 처리
                }
                return null;
            }
        };
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
        }
    }

    //접속끊기
    public void disconnect() {
        socket.close();
        socket = null;
        logService.updateInfoLog("UDP 서버 연결이 종료되었습니다. IP: " + IP + ", PORT: " + PORT);
    }
}
