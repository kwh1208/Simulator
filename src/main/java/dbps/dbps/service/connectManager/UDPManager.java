package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import lombok.Getter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

import static dbps.dbps.Constants.*;

public class UDPManager {
    //IP, PORT에 정보 넣는 시점은 버튼 누를때 controller에서 파라미터로 넣을 예정
    @Getter
    private String IP;
    @Getter
    private int PORT;

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

    public String sendASCMsg(String msg) {
        if (socket == null) {
            logService.warningLog("UDP 소켓이 열려있지 않습니다.");
            connect(IP, PORT);
        }

        DatagramPacket receivePacket;
        try{
            InetAddress serverAddr = InetAddress.getByName(IP);
            DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(Charset.forName("EUC-KR")), msg.getBytes().length, serverAddr, PORT);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        }catch (IOException e){
            //에러 처리
            logService.errorLog(msg+"전송에 실패했습니다.");
        }
        return "에러코드";
    }

    public String sendHEXMsg(String msg){
        if (socket == null) {
            logService.warningLog("UDP 소켓이 열려있지 않습니다.");
            connect(IP, PORT);
        }

        DatagramPacket receivePacket;
        try{
            InetAddress serverAddr = InetAddress.getByName(IP);
            byte[] sendData = hexStringToByteArray(msg);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddr, PORT);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            return new String(receivePacket.getData(), 0, receivePacket.getLength());
        }catch (IOException e){
            //에러 처리
        }
        return null;
    }

    //접속하기
    public void connect(String IP, int PORT){
        logService.updateInfoLog("UDP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;
        DatagramPacket receivePacket;
        try {
            InetAddress serverAddr = InetAddress.getByName(IP);
            DatagramPacket sendPacket = new DatagramPacket(CONNECT_START, CONNECT_START.length, serverAddr, PORT);
            socket.setSoTimeout(responseLatency);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String receivedMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            logService.updateInfoLog("수신 메세지: " + receivedMsg);
            if (receivedMsg.equals("연결성공코드")) {
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
