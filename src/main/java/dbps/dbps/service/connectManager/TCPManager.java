package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import dbps.dbps.service.ResourceManager;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    ResourceBundle bundle;

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
                    byte[] sendBytes = msg.getBytes(Charset.forName("MS949"));

                    input.skip(input.available());
                    logService.updateInfoLog(bundle.getString("sendMsg") + msg);
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
                    String result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));
                    if (result.contains("RX") && result.contains("![") && result.contains("!]")) {
                        int indexTX = result.indexOf("TX");
                        result = result.substring(indexTX);
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    if (result.contains("init_rtcTimeDate Start")){
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    logService.errorLog(bundle.getString("connectionFail"));
                    throw e;
                }finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> sendASCMsg(String msg, boolean utf8, boolean utf16){
        return new Task<>() {

            @Override
            protected String call() throws Exception {
                if (socket==null||socket.isClosed()){
                    connect(IP, PORT);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    byte[] sendBytes = msg.getBytes(Charset.forName("MS949"));
                    if (utf8) sendBytes = msg.getBytes(StandardCharsets.UTF_8);
                    if (utf16) sendBytes = createPacket(msg);
                    input.skip(input.available());
                    if (utf8){
                        logService.updateInfoLog(bundle.getString("sendMsg") + formatLogForUTF8(msg));
                    } else if (utf16) {
                        logService.updateInfoLog(bundle.getString("sendMsg") + formatLogForUTF16(msg));
                    }
                    else logService.updateInfoLog(bundle.getString("sendMsg") + msg);

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
                    String result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));
                    if (result.contains("RX") && result.contains("![") && result.contains("!]")) {
                        int indexTX = result.indexOf("TX");
                        result = result.substring(indexTX);
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    if (result.contains("init_rtcTimeDate Start")){
                        result = result.substring(result.indexOf("!["), result.indexOf("!]")+2);
                    }
                    logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                    logService.errorLog(bundle.getString("connectionFail"));
                    throw e;
                }finally {
                    disconnect();
                }
            }
        };
    }


    //접속하기
    public void connect(String IP, int PORT) {
        if (bundle==null){
            bundle = ResourceManager.getInstance().getBundle();
        }
        logService.updateInfoLog(MessageFormat.format(bundle.getString("tcpServerConnect"), IP, PORT));
        this.IP = IP;
        this.PORT = PORT;
        try {
            // ✅ 소켓을 먼저 생성하고, 명확한 타임아웃 설정
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, PORT), RESPONSE_LATENCY*1000);
            socket.setSoTimeout(RESPONSE_LATENCY * 1000);
        } catch (IOException e) {
            logService.errorLog(MessageFormat.format(bundle.getString("tcpServerConnectionFailed"), IP, PORT));
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
            logService.errorLog(MessageFormat.format(bundle.getString("tcpServerConnectionFailed"), IP, PORT));
        }
    }


    //접속끊기
    public void disconnect(){
        if (KEEP_OPEN){
            return;
        }
        if (socket==null)
            return;
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.getStackTrace();
        }

        logService.updateInfoLog(MessageFormat.format(bundle.getString("tcpServerConnectionClosed"), IP, PORT));
    }

    public void disconnectNoLog(){
        if (KEEP_OPEN){
            return;
        }
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

                    logService.updateInfoLog(bundle.getString("sendMsg")+bytesToHex(msg, msg.length));
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
                        Pattern pattern = Pattern.compile("10 02(.*?)10 03");
                        Matcher matcher = pattern.matcher(result);

                        if (matcher.find()) {
                            result = matcher.group(0); // 전체 매칭된 부분을 추출
                        }
                    }
                    logService.updateInfoLog(bundle.getString("receivedMsg")+ result);
                    return result;
                } catch (IOException e) {
                    logService.errorLog(bundle.getString("connectionFail"));
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
                Pattern pattern = Pattern.compile("10 02(.*?)10 03");
                Matcher matcher = pattern.matcher(result);

                if (matcher.find()) {
                    result = matcher.group(0); // 전체 매칭된 부분을 추출
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void sendMsgAndGetMsgByteShortLog(byte[] msg) throws IOException {
        if (socket == null || socket.isClosed()) {
            connectNoLog(IP, PORT);
        }
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            input.skip(input.available()); // 기존에 남아있는 데이터 제거

            output.write(msg);
            output.flush();

            String log = bytesToHex(msg, 32);
            log+=" ~ 10 03";
            logService.updateInfoLog(log);

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
                    throw new RuntimeException();
                }
            }

            String result = bytesToHex(buffer, totalBytesRead);
            if (result.contains("52 58 28")) {
                Pattern pattern = Pattern.compile("10 02(.*?)10 03");
                Matcher matcher = pattern.matcher(result);

                if (matcher.find()) {
                    result = matcher.group(0); // 전체 매칭된 부분을 추출
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}