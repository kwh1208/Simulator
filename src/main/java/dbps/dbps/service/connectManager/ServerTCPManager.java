package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dbps.dbps.Constants.*;

public class ServerTCPManager {
    Socket socket;
    LogService logService;
    static ServerTCPManager instance;

    private ServerTCPManager() {
        logService = LogService.getLogService();
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
                logService.updateInfoLog("서버 소켓이 " + host + ":" + port + " 에서 열렸습니다. 클라이언트 연결 대기 중...");
                socket = serverSocket.accept();
            }
        } catch (SocketTimeoutException e) {
            logService.errorLog("클라이언트 연결 시간 초과");
        } catch (IOException e) {
            logService.errorLog("서버 소켓 오류: " + e.getMessage());
        }
    }

    public void disconnect() {
        if (KEEP_OPEN) {
            return;
        }
        try {
            socket.close();
            socket = null;
            logService.updateInfoLog("서버 소켓이 닫혔습니다.");
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
            logService.updateInfoLog("서버 소켓이 닫혔습니다.");
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
                    socket.setSoTimeout(RESPONSE_LATENCY * 1000); // 시간 초과 설정
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();

                    logService.updateInfoLog("전송 메세지 : " + bytesToHex(msg, msg.length));

                    output.write(msg);
                    output.flush();

                    byte[] buffer = new byte[1024];
                    int totalBytesRead = input.read(buffer);

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
                    logService.updateInfoLog("받은 메세지: " + result);
                    return result;
                } catch (IOException e) {
                    logService.errorLog("전송에 실패했습니다.");
                    e.printStackTrace();
                    throw e;
                } finally {
                    disconnect();
                }
            }
        };
    }

    public String sendMsgAndGetMsgByteNoLog(byte[] msg) throws IOException {
        if (socket == null) {
            connect(hostIP, serverTCPPort);
        }

        try {
            socket.setSoTimeout(RESPONSE_LATENCY * 1000); // 시간 초과 설정
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            output.write(msg);
            output.flush();

            byte[] buffer = new byte[1024];
            int totalBytesRead = input.read(buffer);

            if (totalBytesRead > 0) {
                String result = bytesToHex(buffer, totalBytesRead);
                if (result.contains("52 58 28")) {
                    result = result.substring(result.indexOf("10 02"));
                }
                if (result.isEmpty()) {
                    throw new IOException("서버 응답이 비어 있습니다.");
                }

                return result;
            } else {
                throw new IOException("서버에서 응답이 없습니다.");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            disconnect();
        }
    }

    public String sendMsgAndGetMsgByteShortLog(byte[] msg) throws IOException {
        if (socket == null) {
            connect(hostIP, serverTCPPort);
        }

        try {
            socket.setSoTimeout(RESPONSE_LATENCY * 1000); // 시간 초과 설정
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            output.write(msg);
            output.flush();

            String log = bytesToHex(msg, 32);
            log += " ~ 10 03";
            logService.updateInfoLog(log);

            byte[] buffer = new byte[1024];
            int totalBytesRead = input.read(buffer);

            if (totalBytesRead > 0) {
                String result = bytesToHex(buffer, totalBytesRead);
                if (result.contains("52 58 28")) {
                    result = result.substring(result.indexOf("10 02"));
                }
                if (result.isEmpty()) {
                    throw new IOException("서버 응답이 비어 있습니다.");
                }

                return result;
            } else {
                throw new IOException("서버에서 응답이 없습니다.");
            }
        } catch (IOException e) {
            throw e;
        } finally {
            disconnect();
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
                    else if (ascUTF16) {
                        sendData = msg.getBytes(StandardCharsets.UTF_16BE);
                    }
                    logService.updateInfoLog("전송 데이터 :" + msg);
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
                    logService.updateInfoLog("받은 메세지: " + result);
                    return result;
                } catch (IOException e) {
                    e.getMessage();

                    logService.errorLog(msg + " 전송에 실패했습니다.");
                    throw e;
                } finally {
                    socket.close();
                }
            }
        };
    }
}