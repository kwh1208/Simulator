package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static dbps.dbps.Constants.*;

public class ServerTCPManager {
    Socket socket;
    LogService logService;
    static ServerTCPManager instance;
    String serverIp;

    private ServerTCPManager() {
        logService = LogService.getLogService();
    }

    public static ServerTCPManager getInstance() {
        if (instance == null) {
            instance = new ServerTCPManager();
        }
        return instance;
    }

    public void connect(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(RESPONSE_LATENCY * 1000);
            logService.updateInfoLog("서버 소켓이 열렸습니다. 클라이언트 연결 대기 중...");
            socket = serverSocket.accept();
        } catch (SocketTimeoutException e) {
            logService.errorLog("클라이언트 연결 시간 초과");
        } catch (IOException e) {
            logService.errorLog("서버 소켓 오류: " + e.getMessage());
        }
    }

    public void disconnect() {
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
                    connect(serverTCPPort);
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
                        String response = bytesToHex(buffer, totalBytesRead);

                        if (response.isEmpty()) {
                            throw new IOException("서버 응답이 비어 있습니다.");
                        }

                        return response;
                    } else {
                        throw new IOException("서버에서 응답이 없습니다.");
                    }
                } catch (IOException e) {
                    logService.errorLog(msg + " 전송에 실패했습니다: " + e.getMessage());
                    throw new IOException("메시지 전송 실패", e);
                } finally {
                    disconnect();
                }
            }
        };
    }

    public Task<String> sendMsgAndGetMsgByteNoLog(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect(serverTCPPort);
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
                        String response = bytesToHex(buffer, totalBytesRead);

                        if (response.isEmpty()) {
                            throw new IOException("서버 응답이 비어 있습니다.");
                        }

                        return response;
                    } else {
                        throw new IOException("서버에서 응답이 없습니다.");
                    }
                } catch (IOException e) {
                    logService.errorLog(msg + " 전송에 실패했습니다: " + e.getMessage());
                    throw new IOException("메시지 전송 실패", e);
                } finally {
                    disconnect();
                }
            }
        };
    }



    public Task<String> sendASCMsg(String msg, boolean utf8){
        return new Task<>() {

            @Override
            protected String call() throws Exception {
                if (socket == null) {
                    connect(serverTCPPort);
                }
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    byte[] sendData = msg.getBytes(Charset.forName("EUC-KR"));
                    if (utf8) sendData = msg.getBytes(StandardCharsets.UTF_8);
                    else if (ascUTF16) {
                        sendData = msg.getBytes(StandardCharsets.UTF_16BE);
                    }
                    logService.updateInfoLog("전송 데이터 :"+msg);
                    output.write(sendData);
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
                    String result = new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                    logService.updateInfoLog("받은 데이터 :"+result);
                    return result;
                } catch (IOException | InterruptedException e) {
                    e.getMessage();

                    logService.errorLog(msg + " 전송에 실패했습니다.");
                    throw e;
                }finally {
                    disconnect();
                }
            }
        };
    }

}