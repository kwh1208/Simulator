package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import dbps.dbps.service.ResourceManager;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dbps.dbps.Constants.*;

public class ServerTCPManager {
    Socket socket;
    LogService logService;
    ResourceBundle bundle;
    static ServerTCPManager instance;

    private ServerTCPManager() {
        logService = LogService.getLogService();
        bundle = ResourceManager.getInstance().getBundle();
    }

    public static ServerTCPManager getInstance() {
        if (instance == null) {
            instance = new ServerTCPManager();
        }
        return instance;
    }

    public void connect(String host, int port) {
        try {
            hostIP = host;
            serverTCPPort = port;
            InetAddress bindAddr = InetAddress.getByName(host);
            try (ServerSocket serverSocket = new ServerSocket(port, 50, bindAddr)) {
                serverSocket.setSoTimeout(RESPONSE_LATENCY * 1000);
                logService.updateInfoLog(bundle.getString("serverSocketOpen") + host + ":" + port + bundle.getString("clientWaiting"));
                socket = serverSocket.accept();
            }
        } catch (SocketTimeoutException e) {
            logService.errorLog(bundle.getString("clientConnectionTimeout"));
        } catch (IOException e) {
            logService.errorLog(bundle.getString("serverSocketError") + e.getMessage());
        }
    }

    public void disconnect() {
        if (KEEP_OPEN) {
            return;
        }
        try {
            socket.close();
            socket = null;
            logService.updateInfoLog(bundle.getString("serverSocketClosed"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnectNoLog() {
        if (KEEP_OPEN) {
            return;
        }
        if (socket == null) {
            return;
        }

        try {
            socket.close();
            socket = null;
            logService.updateInfoLog(bundle.getString("serverSocketClosed"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect(hostIP, serverTCPPort);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();

                    logService.updateInfoLog(bundle.getString("sendMsg") + bytesToHex(msg, msg.length));

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
                    logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                    return result;
                } catch (IOException e) {
                    logService.errorLog(bundle.getString("connectionFail"));
                    e.printStackTrace();
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public void sendMsgAndGetMsgByteNoLog(byte[] msg) throws IOException {
        if (socket == null) {
            connect(hostIP, serverTCPPort);
        }
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

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
            logService.errorLog(bundle.getString("connectionFail"));
            e.printStackTrace();
            throw e;
        } finally {
            disconnect();
        }
    }

    public void sendMsgAndGetMsgByteShortLog(byte[] msg) throws IOException {
        if (socket == null) {
            connect(hostIP, serverTCPPort);
        }

        try {
            socket.setSoTimeout(RESPONSE_LATENCY * 1000); // 시간 초과 설정
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            output.write(msg);
            output.flush();

            String log;
            if (msg.length>=32){
                log = bytesToHex(msg, 32);
                log += " ~ 10 03";
            }
            else {
                log = bytesToHex(msg, msg.length);
            }
            logService.updateInfoLog(log);

            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;
            int retryCount = 0;
            final int maxRetries = 3;
            boolean success = false;
            while (!success && retryCount < maxRetries) {
                try {
                    int bytesRead = input.read(buffer);
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                        if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                            success = true;
                            break;
                        }
                    } else {
                        retryCount++;
                        Thread.sleep(1000);
                        logService.warningLog(
                                MessageFormat.format(bundle.getString("packetTransmissionRetry"), retryCount)
                        );
                        if (retryCount >= maxRetries) {
                            throw new RuntimeException();
                        }
                    } } catch (SocketTimeoutException e) {
                    retryCount++;
                    Thread.sleep(1000);
                    logService.warningLog(
                            MessageFormat.format(bundle.getString("packetTransmissionRetry"), retryCount)
                    );
                    if (retryCount >= maxRetries) {
                        throw new RuntimeException();
                    }
                }
            }
            // 재시도 횟수를 초과하면 예외 처리
            if (!success) {
                logService.warningLog(bundle.getString("packetTransmissionFailedAfterRetries"));
                throw new RuntimeException();
            }

            String result = bytesToHex(buffer, totalBytesRead);
            if (result.contains("52 58 28")) {
                Pattern pattern = Pattern.compile("10 02(.*?)10 03");
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    result = matcher.group(0); // 전체 매칭된 부분 추출
                }
            }
            // 로깅 후 여기서 결과를 사용할 수 있지만, 반환값이 없는 void 함수임.
        } catch (IOException e) {
            disconnectNoLog();
            throw e;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public Task<String> sendASCMsg(String msg, boolean utf8) {
        return new Task<>() {

            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect(hostIP, serverTCPPort);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    byte[] sendData = msg.getBytes(Charset.forName("MS949"));
                    if (utf8) sendData = msg.getBytes(StandardCharsets.UTF_8);
                    logService.updateInfoLog(bundle.getString("sendMsg") + msg);
                    output.write(sendData);
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
                        result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                    }
                    if (result.contains("init_rtcTimeDate Start")) {
                        result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                    }
                    logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                    return result;
                } catch (IOException e) {
                    e.getMessage();
                    logService.errorLog(bundle.getString("connectionFail"));
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> sendASCMsg(String msg, boolean utf8, boolean utf16) {
        return new Task<>() {

            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect(hostIP, serverTCPPort);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    byte[] sendData = msg.getBytes(Charset.forName("MS949"));
                    if (utf8) sendData = msg.getBytes(StandardCharsets.UTF_8);
                    if (utf16) sendData = createPacket(msg);
                    if (utf8){
                        logService.updateInfoLog(bundle.getString("sendMsg") + formatLogForUTF8(msg));
                    } else if (utf16) {
                        logService.updateInfoLog(bundle.getString("sendMsg") + formatLogForUTF16(msg));
                    }
                    else logService.updateInfoLog(bundle.getString("sendMsg") + msg);
                    output.write(sendData);
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
                        result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                    }
                    if (result.contains("init_rtcTimeDate Start")) {
                        result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                    }
                    logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                    return result;
                } catch (IOException e) {
                    e.getMessage();
                    logService.errorLog(bundle.getString("connectionFail"));
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }
}