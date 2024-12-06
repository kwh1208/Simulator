package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static dbps.dbps.Constants.*;

public class TCPManager {

    @Setter
    @Getter
    private String IP;

    @Setter
    @Getter
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
    public Task<String> sendASCMsg(String msg, boolean utf8){
        return new Task<>() {

            @Override
            protected String call() throws Exception {
                if (socket.isClosed()) {
                    connect(IP, PORT);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    byte[] sendBytes = msg.getBytes(Charset.forName("EUC-KR"));
                    if (utf8) sendBytes = msg.getBytes(StandardCharsets.UTF_8);
                    else if (ascUTF16) {
                        sendBytes = msg.getBytes(StandardCharsets.UTF_16BE);
                    }

                    output.write(sendBytes);
                    output.flush();

                    long startTime = System.currentTimeMillis();
                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;

                    while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000L) {
                        if (input.available() > 0) {
                            int bytesRead = input.read(buffer);

                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;

                                startTime = System.currentTimeMillis();

                                if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                                    break;
                                }
                            }
                        } else {
                            Thread.sleep(50);
                        }
                    }
                    return new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                } catch (IOException | InterruptedException e) {
                    e.getMessage();

                    logService.errorLog(msg + " 전송에 실패했습니다.");

                    return "에러발생";
                }finally {
                    socket.close();
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


    //접속하기
    public void connect(String IP, int PORT){

        logService.updateInfoLog("TCP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);

        this.IP = IP;
        this.PORT = PORT;

        try {
            socket = new Socket(IP, PORT);

            socket.setSoTimeout(0);

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

        logService.updateInfoLog("TCP 서버 연결이 종료되었습니다. IP: " + IP + ", PORT: " + PORT);

    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg){
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket.isClosed()) {
                    connect(IP, PORT);
                }

                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();

                    output.write(msg);
                    output.flush();

                    long startTime = System.currentTimeMillis();
                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;

                    while (System.currentTimeMillis() - startTime < RESPONSE_LATENCY * 1000L) {
                        if (input.available() > 0) {
                            int bytesRead = input.read(buffer);
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;

                                startTime = System.currentTimeMillis();

                                if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                    break;
                                }
                            }
                        } else {
                            Thread.sleep(50);
                        }
                    }
                    return bytesToHex(buffer, totalBytesRead);
                } catch (IOException | InterruptedException e) {
                    logService.errorLog(msg + "전송에 실패했습니다.");
                    return null;
                } finally {
                    disconnect();
                }
            }
        };
    }
}