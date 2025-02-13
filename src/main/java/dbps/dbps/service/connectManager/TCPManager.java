package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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
                if (socket==null||socket.isClosed()){
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
                    input.skip(input.available());
                    logService.updateInfoLog("전송 메세지: "+msg);
                    output.write(sendBytes);
                    output.flush();


                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;

                    while (true) {
                        int bytesRead = input.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;
                            if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                                break;
                            }
                        } else {
                            break; // 타임아웃
                        }
                    }
                    String result = new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                    if (result.contains("RX") && result.contains("![") && result.contains("!]")) {
                        int indexTX = result.indexOf("TX");
                        result = result.substring(indexTX);
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    if (result.contains("init_rtcTimeDate Start")){
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    logService.updateInfoLog("받은 메세지: " + result);
                    return result;
                } catch (IOException e) {
                    e.getMessage();

                    logService.errorLog(msg + " 전송에 실패했습니다.");
                    throw e;
                }finally {
                    socket.close();
                }
            }
        };
    }

    //접속하기
    public void connect(String IP, int PORT) {
        logService.updateInfoLog("TCP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;
        try {
            // ✅ 소켓을 먼저 생성하고, 명확한 타임아웃 설정
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, PORT), RESPONSE_LATENCY*1000);
            socket.setSoTimeout(RESPONSE_LATENCY * 1000);
        } catch (IOException e) {
            logService.errorLog("TCP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
        }
    }

    public void connectNoLog(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
        try {
            // ✅ 소켓을 먼저 생성하고, 명확한 타임아웃 설정
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, PORT), RESPONSE_LATENCY*1000);
            socket.setSoTimeout(RESPONSE_LATENCY * 1000);
        } catch (IOException e) {
            logService.errorLog("TCP 서버 연결에 실패했습니다. IP: " + IP + ", PORT: " + PORT);
        }
    }


    //접속끊기
    public void disconnect(){
        if (socket==null)
            return;
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.getStackTrace();
        }

        logService.updateInfoLog("TCP 서버 연결이 종료되었습니다. IP: " + IP + ", PORT: " + PORT);
    }

    public void disconnectNoLog(){
        if (socket==null)
            return;
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg){
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket==null||socket.isClosed()){
                    connect(IP, PORT);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    input.skip(input.available());

                    logService.updateInfoLog("전송 메세지 :"+bytesToHex(msg, msg.length));
                    output.write(msg);
                    output.flush();


                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;
                    while (true) {
                        int bytesRead = input.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;

                            // 데이터가 모두 수신되었는지 확인
                            if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                break;
                            }
                        } else {
                            break; // 타임아웃
                        }
                    }

                    String result = bytesToHex(buffer, totalBytesRead);
                    if (result.contains("52 58 28")) {
                            result = result.substring(result.indexOf("10 02"));
                    }
                    logService.updateInfoLog("받은 메세지: " + result);
                    return result;
                } catch (IOException e) {
                    logService.errorLog("전송에 실패했습니다.");
                    e.printStackTrace();
                    throw e;
                }finally {
                    disconnect();
                }
            }
        };
    }

    public void sendMsgAndGetMsgByteNoLog(byte[] msg) throws IOException {
        if (socket == null || socket.isClosed()) {
            connectNoLog(IP, PORT);
        }
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            input.skip(input.available()); // 기존에 남아있는 데이터 제거

            output.write(msg);
            output.flush();

            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            while (true) {
                int bytesRead = input.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                if (bytesRead > 0) {
                    totalBytesRead += bytesRead;

                    // 데이터가 모두 수신되었는지 확인
                    if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                        break;
                    }
                } else {
                    break; // 타임아웃
                }
            }

            String result = bytesToHex(buffer, totalBytesRead);
            if (result.contains("52 58 28")) {
                result = result.substring(result.indexOf("10 02"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}