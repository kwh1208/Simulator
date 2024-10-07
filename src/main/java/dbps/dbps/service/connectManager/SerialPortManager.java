package dbps.dbps.service.connectManager;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.LogService;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static dbps.dbps.Constants.*;

public class SerialPortManager {
    public static final Map<String, SerialPort> serialPortMap = new HashMap<>();
    private static SerialPortManager instance = null;
    private final LogService logService;

    private SerialPortManager() {
        logService = LogService.getLogService();
    }

    public static SerialPortManager getManager() {
        if (instance == null) {
            instance = new SerialPortManager();
        }
        return instance;
    }

    public void openPort(String portName, int baudRate){
        if (serialPortMap.containsKey(portName)&&isPortOpen(portName)){
            logService.updateInfoLog(portName + " 포트가 열려있습니다.");
            return;
        }

        SerialPort port = SerialPort.getCommPort(portName);
        port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        port.openPort();
        serialPortMap.put(portName, port);
        logService.updateInfoLog(portName + " 포트가 열렸습니다.");
    }

    public void openPortNoLog(String portName, int baudRate){
        if (serialPortMap.containsKey(portName)&&isPortOpen(portName)){
            return;
        }

        SerialPort port = SerialPort.getCommPort(portName);
        port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        port.openPort();
        serialPortMap.put(portName, port);
    }



    public void closePort(String portName){
        if (serialPortMap.containsKey(portName)){
            SerialPort serialPort = serialPortMap.get(portName);
            if (serialPort.isOpen()){
                serialPort.closePort();
                logService.updateInfoLog(portName + " 포트가 닫혔습니다.");
            } else {
                logService.updateInfoLog(portName + " 포트가 이미 닫혀 있습니다.");
            }
        } else {
            logService.updateInfoLog(portName+" 포트가 존재하지 않습니다.");
        }
    }
    //

    public void closePortNoLog(String portName){
        if (serialPortMap.containsKey(portName)){
            SerialPort serialPort = serialPortMap.get(portName);
            if (serialPort.isOpen()){
                serialPort.closePort();
            }
        }
    }

    public boolean isPortOpen(String portName) {
        SerialPort port = serialPortMap.get(portName);
        return port != null && port.isOpen();
    }
    public Task<String> sendMsgAndGetMsg(String msg){
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                String portName = OPEN_PORT_NAME;
                SerialPort port = serialPortMap.get(portName);

                if (port == null || !isPortOpen(portName)) {
                    openPort(portName, SERIAL_BAUDRATE);
                    port = serialPortMap.get(portName);
                }
                try {
                    OutputStream outputStream = port.getOutputStream();
                    InputStream inputStream = port.getInputStream();

                    byte[] dataToSend = msg.getBytes();
                    outputStream.write(dataToSend);
                    outputStream.flush();

                    Thread.sleep(300);
                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;
                    long timeout = RESPONSE_LATENCY * 1000;
                    long startTime = System.currentTimeMillis();

                    while (System.currentTimeMillis() - startTime < timeout) {
                        if (inputStream.available() > 0) {
                            int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;
                                // 타임아웃 시간 갱신 (데이터 수신이 있으면 타이머 리셋)
                                startTime = System.currentTimeMillis();

                                if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                                    break; // 데이터를 다 받았으면 루프 종료
                                }

                            }
                        } else {
                            // 짧은 대기 시간 후 다시 읽기 시도 (바쁜 대기 방지)
                            Thread.sleep(50);
                        }
                    }

                    return new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                }catch (Exception e){
                    logService.errorLog("에러가 발생했습니다: "+e.getMessage());
                    return null;
                }
            }
        };
    }

    private boolean dataReceivedIsComplete(byte[] buffer, int length) {
        return length > 0 && buffer[length - 1]==(byte) ']' && buffer[length - 2]==(byte) '!';
    }
    public Task<String> sendMsgAndGetMsgByte(byte[] msg) {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                String portName = OPEN_PORT_NAME;
                SerialPort port = serialPortMap.get(portName);

                if (port == null || !isPortOpen(portName)) {
                    openPort(portName, SERIAL_BAUDRATE);
                    port = serialPortMap.get(portName);
                }

                try {
                    OutputStream outputStream = port.getOutputStream();
                    InputStream inputStream = port.getInputStream();
                    outputStream.write(msg);
                    outputStream.flush();

                    // 읽기용 버퍼 초기화
                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;

                    // 데이터 수신을 기다리는 최대 시간 (예: 1000 밀리초)
                    long timeout = RESPONSE_LATENCY * 1000;
                    long startTime = System.currentTimeMillis();

                    // 반복적으로 읽어 남아있는 데이터를 모두 수신
                    while (System.currentTimeMillis() - startTime < timeout) {
                        if (inputStream.available() > 0) {
                            int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;
                                // 타임아웃 시간 갱신 (데이터 수신이 있으면 타이머 리셋)
                                startTime = System.currentTimeMillis();

                                if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                    break; // 데이터를 다 받았으면 루프 종료
                                }
                            }
                        } else {
                            // 짧은 대기 시간 후 다시 읽기 시도 (바쁜 대기 방지)
                            Thread.sleep(50);
                        }
                    }

                    // 수신된 모든 데이터를 Hex로 변환하여 반환
                    return bytesToHex(buffer, totalBytesRead);
                } catch (Exception e) {
                    logService.errorLog("에러가 발생했습니다: " + e.getMessage());
                    return null;
                }
            }
        };
    };

    public String sendMsgAndGetMsgBytetmp(byte[] msg){
        String portName = OPEN_PORT_NAME;
        SerialPort port = serialPortMap.get(portName);

        if (port == null || !isPortOpen(portName)) {
            openPort(portName, SERIAL_BAUDRATE);
            port = serialPortMap.get(portName);
        }

        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();
            outputStream.write(msg);
            outputStream.flush();

            // 읽기용 버퍼 초기화
            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            // 데이터 수신을 기다리는 최대 시간 (예: 1000 밀리초)
            long timeout = RESPONSE_LATENCY * 1000;
            long startTime = System.currentTimeMillis();

            // 반복적으로 읽어 남아있는 데이터를 모두 수신
            while (System.currentTimeMillis() - startTime < timeout) {
                if (inputStream.available() > 0) {
                    int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                        // 타임아웃 시간 갱신 (데이터 수신이 있으면 타이머 리셋)
                        startTime = System.currentTimeMillis();

                        if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                            break; // 데이터를 다 받았으면 루프 종료
                        }
                    }
                } else {
                    // 짧은 대기 시간 후 다시 읽기 시도 (바쁜 대기 방지)
                    Thread.sleep(50);
                }
            }

            // 수신된 모든 데이터를 Hex로 변환하여 반환
            return bytesToHex(buffer, totalBytesRead);
        } catch (Exception e) {
            logService.errorLog("에러가 발생했습니다: " + e.getMessage());
            return null;
        }
    }

    private boolean dataReceivedIsCompleteHex(byte[] buffer, int length) {
        return length >= 2 && buffer[length - 2] == (byte) 0x10 && buffer[length - 1] == (byte) 0x03;
    }

    public Task<Integer> findSpeedTask = new Task<>() {
        @Override
        protected Integer call() throws Exception {
            int[] baudRates = {9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};

            serialPortMap.get(OPEN_PORT_NAME).closePort();
            for (int baudRate : baudRates) {
                try {
                    openPortNoLog(OPEN_PORT_NAME, baudRate);
                    Thread.sleep(1000);  // 포트가 열릴 시간을 기다림
                    SerialPort port = serialPortMap.get(OPEN_PORT_NAME);
                    OutputStream outputStream = port.getOutputStream();
                    InputStream inputStream = port.getInputStream();

                    if (!port.isOpen()) {
                        // 포트를 열 수 없을 때 로그 업데이트
                        Platform.runLater(() -> logService.warningLog("포트를 열 수 없습니다."));
                        continue;
                    }

                    // 포트가 열렸을 때 로그 업데이트
                    Platform.runLater(() -> logService.updateInfoLog("현재 속도 " + baudRate + "에서 응답을 대기 중..."));

                    String msg = "10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                    outputStream.write(hexStringToByteArray(msg));
                    outputStream.flush();

                    byte[] buffer = new byte[1024];
                    int numRead =  0;
                    long timeout = RESPONSE_LATENCY * 1000;
                    long startTime = System.currentTimeMillis();

                    // 응답 대기
                    while (System.currentTimeMillis() - startTime < timeout) {
                        if (inputStream.available() > 0) {
                            int bytesRead = inputStream.read(buffer, numRead, buffer.length - numRead);
                            if (bytesRead > 0) {
                                numRead += bytesRead;
                                startTime = System.currentTimeMillis();
                                if (dataReceivedIsComplete(buffer, numRead)) {
                                    break;
                                }
                            }
                        } else {
                            Thread.sleep(50);  // 짧은 대기 시간
                        }
                    }

                    String response = bytesToHex(buffer, numRead);

                    if (!response.isBlank()) {
                        // 통신 속도 찾기 성공 로그 업데이트
                        Platform.runLater(() -> logService.updateInfoLog(OPEN_PORT_NAME + "의 적정 통신 속도는 " + baudRate + "입니다."));
                        return baudRate;
                    }

                    closePortNoLog(OPEN_PORT_NAME);
                } catch (IOException | InterruptedException e) {
                    // 예외 발생 시 로그 업데이트
                    Platform.runLater(() -> logService.warningLog("예외 발생: " + e.getMessage()));
                }
            }

            // 통신 속도를 찾지 못한 경우 경고 로그 업데이트
            Platform.runLater(() -> logService.warningLog("적정 통신 속도를 찾지 못했습니다."));
            return 0;
        }
    };
}