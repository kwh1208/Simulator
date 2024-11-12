package dbps.dbps.service.connectManager;

import dbps.dbps.service.LogService;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static dbps.dbps.Constants.*;

public class ServerTCPManager {
    Socket socket;
    LogService logService;
    static ServerTCPManager instance;

    private ServerTCPManager() {
    }

    public static ServerTCPManager getInstance() {
        if (instance == null) {
            instance = new ServerTCPManager();
        }
        return instance;
    }

    public void connect(int Port) {
        try (ServerSocket serverSocket = new ServerSocket(Port)) {
            System.out.println("Server is listening on port " + Port);

            socket = serverSocket.accept();

        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            socket.close();
            socket = null;
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

                                if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                                    break;
                                }
                            }
                        } else {
                            Thread.sleep(50);
                        }
                    }
                    return bytesToHex(buffer, totalBytesRead);
                } catch (IOException | InterruptedException e) {
                    e.getMessage();

                    logService.errorLog(msg + " 전송에 실패했습니다.");

                    return "에러발생";
                }finally {
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
                    return new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                } catch (IOException | InterruptedException e) {
                    e.getMessage();

                    logService.errorLog(msg + " 전송에 실패했습니다.");

                    return "에러발생";
                }finally {
                    disconnect();
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

}