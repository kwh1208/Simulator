package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

import static dbps.dbps.Constants.*;

public class TCPManager {

    @Getter
    @Setter
    private String IP;

    @Getter
    @Setter
    private int PORT;

    Socket socket = null;

    private static TCPManager tcpManager = null;
    private static LogService logService;

    private TCPManager() {
        logService = LogService.getLogService();
    }

    public static TCPManager getManager() {
        if (tcpManager == null) {
            tcpManager = new TCPManager();
        }
        return tcpManager;
    }

    public String sendASCMsg(String msg) {
        if (socket == null) {
            logService.warningLog("TCP 소켓이 열려있지 않습니다.");
            connect(IP, PORT);
        }

        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            output.write(msg.getBytes(Charset.forName("EUC-KR")));
            output.flush();

            long startTime = System.currentTimeMillis();
            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000) {
                if (input.available() > 0) {
                int bytesRead = input.read(buffer);
                if (bytesRead > 0) {
                    totalBytesRead += bytesRead;

                    startTime = System.currentTimeMillis();

                    if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                        break;
                    }
                }} else {
                    Thread.sleep(50);
                }
            }
            return new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
        } catch (IOException | InterruptedException e){
            e.getMessage();
            logService.errorLog(msg+"전송에 실패했습니다.");
            return "에러발생";
        }
    }

    private boolean dataReceivedIsComplete(byte[] buffer, int length) {
        return length > 0 && buffer[length - 1]==(byte) ']' && buffer[length - 2]==(byte) '!';
    }


    //접속하기
    public void connect(String IP, int PORT){
        logService.updateInfoLog("TCP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;

        try {
            socket = new Socket(IP, PORT);

            socket.setSoTimeout(RESPONSE_LATENCY *1000);

            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            output.write(CONNECT_START);
            output.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);

            if (bytesRead == -1) {
                logService.errorLog("서버로부터 데이터를 읽지 못했습니다.");
                return;
            }

            // 받은 데이터 문자열로 변환
            String response = bytesToHex(buffer, bytesRead);

            if (response.equals("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03 ")){
                logService.updateInfoLog("TCP 서버에 연결되었습니다. IP : " + IP + ", PORT: " + PORT);
            } else {
                logService.errorLog("TCP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
            }


        }catch (IOException e){
            logService.errorLog("TCP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
        }
    }


    //접속끊기
    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        socket = null;
        logService.updateInfoLog("TCP 서버 연결이 종료되었습니다. IP: " + IP + ", PORT: " + PORT);
    }

    public String sendMsgAndGetMsgHex(String msg) {
        if (socket == null) {
            logService.warningLog("TCP 소켓이 열려있지 않습니다.");
            connect(IP, PORT);
        }

        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            output.write(hexStringToByteArray(msg));
            output.flush();

            long startTime = System.currentTimeMillis();
            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000) {
                if (input.available() > 0) {
                    int bytesRead = input.read(buffer);
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;

                        startTime = System.currentTimeMillis();

                        if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                            break;
                        }
                    }} else {
                    Thread.sleep(50);
                }
            }
            return bytesToHex(buffer, totalBytesRead);
        } catch (IOException | InterruptedException e){
            logService.errorLog(msg+"전송에 실패했습니다.");
            return null;
        }
    }
}

