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
    public void connect(String IP, int PORT){
        logService.updateInfoLog("TCP 서버에 연결합니다. IP: " + IP + ", PORT: " + PORT);
        this.IP = IP;
        this.PORT = PORT;

        try {
            socket = new Socket(IP, PORT);

            socket.setSoTimeout(RESPONSE_LATENCY*1000);

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
                if (socket==null||socket.isClosed()){
                    connect(IP, PORT);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    input.skip(input.available());

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
                    if (result.contains("54 58 28")) {
                        result = new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                        int tmp = extractNumberAfterTXBeforeByteHex(result);
                        if (tmp > 0 && 14 + String.valueOf(tmp).length() + result.indexOf("TX(") + tmp <= buffer.length) {
                            result = new String(buffer, 15 + String.valueOf(tmp).length() + result.indexOf("54 58 28"), tmp * 3, Charset.forName("EUC-KR"));
                        } else {
                            throw new IllegalArgumentException("유효하지 않은 offset 또는 tmp 값입니다.");
                        }
                    }
                    return result;
                } catch (IOException e) {
                    logService.errorLog(msg + "전송에 실패했습니다.");
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    private int extractNumberAfterTXBeforeByteHex(String input) {
        // "TX" 뒤의 "byte" 앞 숫자를 찾는 정규식
        Pattern pattern = Pattern.compile("TX.*?(\\d+)\\s*byte");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String number = matcher.group(1); // 첫 번째 그룹에서 숫자 추출
            return Integer.parseInt(number); // 숫자를 Integer로 변환하여 반환
        }

        return -1; // "TX" 뒤 "byte" 앞 숫자가 없을 경우 -1 반환
    }
}