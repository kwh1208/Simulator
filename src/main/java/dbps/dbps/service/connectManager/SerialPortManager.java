package dbps.dbps.service.connectManager;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import com.fazecast.jSerialComm.SerialPortTimeoutException;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.DabitNetService;
import dbps.dbps.service.LogService;
import dbps.dbps.service.ResourceManager;
import javafx.concurrent.Task;

import java.io.*;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dbps.dbps.Constants.*;

public class SerialPortManager {
    public static final Map<String, SerialPort> serialPortMap = new HashMap<>();
    private static SerialPortManager instance = null;
    private final LogService logService;
    ConfigService configService;
    ResourceBundle bundle;
    private static final Object portLock = new Object();
    private final BlockingQueue<Task<?>> taskQueue = new LinkedBlockingQueue<>();
    DabitNetService dabitNetService;

    private SerialPortManager() {
        logService = LogService.getLogService();
        configService = ConfigService.getInstance();
        dabitNetService = DabitNetService.getInstance();
        bundle = ResourceManager.getInstance().getBundle();
    }

    public static SerialPortManager getManager() {
        if (instance == null) {
            instance = new SerialPortManager();
        }
        return instance;
    }

    public void openPort(String portName, int baudRate) {
        synchronized (portLock) {
            if (serialPortMap.containsKey(portName) && isPortOpen(portName)) {
                return;
            }
            SerialPort port = SerialPort.getCommPort(portName);
            port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, RESPONSE_LATENCY * 1000, 0);

            if (!port.openPort()) {
                logService.errorLog(portName + bundle.getString("portCantOpen"));
                return;
            }
            logService.updateInfoLog(portName + bundle.getString("portOpen"));

            serialPortMap.put(portName, port);
        }
    }

    public void openPortNoLog(String portName, int baudRate) {
        synchronized (portLock) {
            if (serialPortMap.containsKey(portName) && isPortOpen(portName)) {
                return;
            }
            SerialPort port = SerialPort.getCommPort(portName);
            port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, RESPONSE_LATENCY * 1000, 0);
//            port.setFlowControl(SerialPort.FLOW_CONTROL_RTS_ENABLED | SerialPort.FLOW_CONTROL_CTS_ENABLED);


            if (!port.openPort()) {
                logService.errorLog(portName + bundle.getString("portCantOpen"));
            }

            serialPortMap.put(portName, port);
        }
    }


    public void closePort(String portName) {
        if (KEEP_OPEN) {
            return;
        }
        synchronized (portLock) {
            SerialPort port = serialPortMap.get(portName);
            if (port != null && port.isOpen()) {
                port.closePort();
                logService.updateInfoLog(portName + bundle.getString("portClose"));
            }
            serialPortMap.remove(portName);
        }
    }

    public void closePortNoLog(String portName) {
        if (KEEP_OPEN) {
            return;
        }
        synchronized (portLock) {
            SerialPort port = serialPortMap.get(portName);
            if (port != null && port.isOpen()) {
                port.closePort();
            }
            serialPortMap.remove(portName);
        }
    }

    public boolean isPortOpen(String portName) {
        SerialPort port = serialPortMap.get(portName);
        return port != null && port.isOpen();
    }

    public Task<String> sendMsgAndGetMsg(String msg, boolean utf8) {
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                String portName = OPEN_PORT_NAME;
                synchronized (portLock) {
                    if (!isPortOpen(portName)) {
                        openPort(portName, SERIAL_BAUDRATE);
                    }
                    SerialPort port = serialPortMap.get(portName);
                    if (port == null) {
                        throw new IllegalStateException("포트를 열 수 없습니다: " + portName);
                    }

                    if (!isBT && port.getPortDescription().toLowerCase().contains("bluetooth")) {
                        logService.warningLog(bundle.getString("bluetoothPort"));
                        closePort(portName);
                        throw new RuntimeException();
                    }

                    logService.updateInfoLog(bundle.getString("sendMsg") + msg);

                    try (InputStream inputStream = new BufferedInputStream(port.getInputStream());
                         OutputStream outputStream = new BufferedOutputStream(port.getOutputStream())) {
                        inputStream.skip(inputStream.available());
                        byte[] dataToSend = msg.getBytes(utf8 ? StandardCharsets.UTF_8 : Charset.forName("MS949"));
                        outputStream.write(dataToSend);
                        outputStream.flush();

                        if (msg.startsWith("++SET++![BT SETT  ")) {
                            return null;
                        }

                        byte[] buffer = new byte[1024];
                        int totalBytesRead = 0;
                        long startTime = System.currentTimeMillis();
                        long overallTimeout = RESPONSE_LATENCY * 1000L; // 예: RESPONSE_LATENCY가 초 단위라면 밀리초로 변환

                        while (true) {
                            // 전체 대기 시간이 초과되었는지 체크
                            if (System.currentTimeMillis() - startTime > overallTimeout) {
                                logService.errorLog(bundle.getString("connectionFail"));
                                throw new SocketTimeoutException("Overall timeout reached");
                            }
                            int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);

                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;

                                if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }

                        String result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));

                        if (result.contains("TX") && result.contains("![") && result.contains("!]")) {
                            int indexTX = result.indexOf("TX");
                            result = result.substring(indexTX);
                            result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                        }
                        logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                        return result;
                    } catch (SerialPortTimeoutException | SerialPortIOException e) {
                        logService.errorLog(bundle.getString("connectionFail"));
                        throw e;
                    } catch (Exception e) {
                        logService.errorLog(bundle.getString("Error") + e.getMessage());
                        throw e;
                    } finally {
                        closePort(portName); // 작업 후 포트 닫기
                    }
                }
            }
        };
        taskQueue.add(task);
        return task;
    }

    public Task<String> sendMsgAndGetMsg(String msg, boolean utf8, boolean utf16) {
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                String portName = OPEN_PORT_NAME;
                synchronized (portLock) {
                    if (!isPortOpen(portName)) {
                        openPort(portName, SERIAL_BAUDRATE);
                    }
                    SerialPort port = serialPortMap.get(portName);
                    if (port == null) {
                        throw new IllegalStateException("포트를 열 수 없습니다: " + portName);
                    }

                    if (!isBT && port.getPortDescription().toLowerCase().contains("bluetooth")) {
                        logService.warningLog(bundle.getString("bluetoothPort"));
                        closePort(portName);
                        throw new RuntimeException();
                    }

                    try (InputStream inputStream = new BufferedInputStream(port.getInputStream());
                         OutputStream outputStream = new BufferedOutputStream(port.getOutputStream())) {
                        inputStream.skip(inputStream.available());
                        byte[] dataToSend = msg.getBytes(utf8 ? StandardCharsets.UTF_8 : Charset.forName("MS949"));
                        if (utf16) dataToSend = createPacket(msg);
                        outputStream.write(dataToSend);
                        outputStream.flush();

                        if (utf8) {
                            logService.updateInfoLog(bundle.getString("sendMsg") + formatLogForUTF8(msg));
                        } else if (utf16) {
                            logService.updateInfoLog(bundle.getString("sendMsg") + formatLogForUTF16(msg));
                        } else logService.updateInfoLog(bundle.getString("sendMsg") + msg);

                        byte[] buffer = new byte[1024];
                        int totalBytesRead = 0;
                        long startTime = System.currentTimeMillis();
                        long overallTimeout = RESPONSE_LATENCY * 1000L; // 예: RESPONSE_LATENCY가 초 단위라면 밀리초로 변환

                        while (true) {
                            // 전체 대기 시간이 초과되었는지 체크
                            if (System.currentTimeMillis() - startTime > overallTimeout) {
                                logService.errorLog(bundle.getString("connectionFail"));
                                throw new SocketTimeoutException("Overall timeout reached");
                            }
                            int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);

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
                        if (result.contains("TX") && result.contains("![") && result.contains("!]")) {
                            int indexTX = result.indexOf("TX");
                            result = result.substring(indexTX);
                            result = result.substring(result.indexOf("!["), result.indexOf("!]") + 2);
                        }
                        if (result.contains("RX")) {

                            result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));

                            String startMarker = "!["; // "10 02"
                            String endMarker = "!]";   // "10 03"
                            int startIndex = result.indexOf(startMarker);
                            int endIndex = result.lastIndexOf(endMarker);

                            result = result.substring(startIndex, endIndex + endMarker.length());
                            result = result.toUpperCase();
                        }
                        logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                        return result;
                    } catch (SerialPortTimeoutException | SerialPortIOException e) {
                        logService.errorLog(bundle.getString("connectionFail"));
                        throw e;
                    } catch (Exception e) {
                        logService.errorLog(bundle.getString("Error"));
                        throw e;
                    } finally {
                        closePort(portName); // 작업 후 포트 닫기
                    }
                }
            }
        };
        taskQueue.add(task);
        return task;
    }

    private int extractNumberAfterTXBeforeByteHex(String input) {
        // "TX" 뒤의 "byte" 앞 숫자를 찾는 정규식
        Pattern pattern = Pattern.compile("TX.*?(\\d+)\\s*byte");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String number = matcher.group(1); // 첫 번째 그룹에서 숫자 추출
            return Integer.parseInt(number); // 숫자를 Integer로 변환하여 반환
        }

        return -1;
    }

    public Task<String> sendMsgAndGetMsgByte(byte[] msg) {
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {
                String portName = OPEN_PORT_NAME;
                synchronized (portLock) {
                    if (!isPortOpen(portName)) {
                        openPort(portName, SERIAL_BAUDRATE);
                    }
                    SerialPort port = serialPortMap.get(portName);
                    if (port == null) {
                        throw new IllegalStateException("포트를 열 수 없습니다: " + portName);
                    }


                    try (OutputStream outputStream = new BufferedOutputStream(port.getOutputStream());
                         InputStream inputStream = new BufferedInputStream(port.getInputStream())) {
                        outputStream.write(msg);
                        outputStream.flush();
                        logService.updateInfoLog(bundle.getString("sendMsg") + bytesToHex(msg, msg.length));

                        byte[] buffer = new byte[1024];
                        int totalBytesRead = 0;
                        long startTime = System.currentTimeMillis();
                        long overallTimeout = RESPONSE_LATENCY * 1000L; // 예: RESPONSE_LATENCY가 초 단위라면 밀리초로 변환

                        while (true) {
                            // 전체 대기 시간이 초과되었는지 체크
                            if (System.currentTimeMillis() - startTime > overallTimeout) {
                                logService.errorLog(bundle.getString("connectionFail"));
                                throw new SocketTimeoutException("Overall timeout reached");
                            }
                            try {
                                int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                                if (bytesRead > 0) {
                                    totalBytesRead += bytesRead;

                                    // 데이터가 모두 수신되었는지 확인
                                    if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                        break;
                                    }
                                } else {
                                    break; // 스트림 종료
                                }
                            } catch (SocketTimeoutException e) {
                                logService.errorLog(bundle.getString("connectionFail"));
                                throw e;
                            }
                        }

                        String result = bytesToHex(buffer, totalBytesRead);

                        if (result.contains("54 58 28")) {
                            result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));
                            int tmp = extractNumberAfterTXBeforeByteHex(result);
                            if (tmp > 0 && 14 + String.valueOf(tmp).length() + result.indexOf("TX(") + tmp <= buffer.length) {
                                result = new String(buffer, 15 + String.valueOf(tmp).length() + result.indexOf("54 58 28"), tmp * 3, Charset.forName("MS949"));
                            } else {
                                throw new IllegalArgumentException("유효하지 않은 offset 또는 tmp 값입니다.");
                            }
                        }
                        if (result.contains("52 58 28")) {

                            result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));

                            String startMarker = "10 02"; // "10 02"
                            String endMarker = "10 03";   // "10 03"
                            int startIndex = result.indexOf(startMarker);
                            int endIndex = result.lastIndexOf(endMarker);

                            result = result.substring(startIndex, endIndex + endMarker.length());
                            result = result.toUpperCase();
                        }
                        logService.updateInfoLog(bundle.getString("receivedMsg") + result);
                        return result;
                    } catch (SerialPortTimeoutException | SerialPortIOException e) {
                        logService.errorLog(bundle.getString("connectionFail"));
                        throw e;
                    } catch (Exception e) {
                        throw e;
                    } finally {
                        closePort(portName); // 작업 후 포트 닫기
                    }
                }
            }
        };

        taskQueue.add(task); // 작업을 큐에 추가
        return task;
    }


    public void sendMsgAndGetMsgByteNoLog(byte[] msg) throws IOException {
        String portName = OPEN_PORT_NAME;
        SerialPort port = serialPortMap.get(portName);

        if (port == null || !isPortOpen(portName)) {
            openPortNoLog(portName, SERIAL_BAUDRATE);
            port = serialPortMap.get(portName);
        }
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            outputStream.write(msg);

            // 읽기용 버퍼 초기화
            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            long startWait = System.currentTimeMillis();
            long timeout = 150;

            while ((System.currentTimeMillis() - startWait) < timeout) {
                if (inputStream.available() > 0) {
                    int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);

                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                        if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                            break;
                        }
                    }
                }
            }
            bytesToHex(buffer, totalBytesRead);
        } catch (Exception e) {
            logService.errorLog(bundle.getString("connectionFail"));
            throw e;
        }
    }

    public void sendMsgAndGetMsgByteShortLog(byte[] msg) throws IOException {
        String portName = OPEN_PORT_NAME;
        SerialPort port = serialPortMap.get(portName);

        if (port == null || !isPortOpen(portName)) {
            openPortNoLog(portName, SERIAL_BAUDRATE);
            port = serialPortMap.get(portName);
        }
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            String log;
            if (msg.length>=32){
                log = bytesToHex(msg, 32);
                log += " ~ 10 03";
            }
            else {
                log = bytesToHex(msg, msg.length);
            }
            logService.updateInfoLog(log);

            outputStream.write(msg);
            // 읽기용 버퍼 초기화
            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            long startWait = System.currentTimeMillis();
            long timeout = 150;

            while ((System.currentTimeMillis() - startWait) < timeout) {
                if (inputStream.available() > 0) {
                    int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);

                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                        if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                            break;
                        }
                    }
                }
            }
            if (totalBytesRead == 0){
                throw new RuntimeException();
            }
            bytesToHex(buffer, totalBytesRead);
        } catch (Exception e) {
            logService.errorLog(bundle.getString("connectionFail"));
            throw e;
        }
    }


    public Task<Integer> findSpeedTask() {
        return new Task<>() {
            @Override
            protected Integer call() throws Exception {
                int[] baudRates = {9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};

                for (int baudRate : baudRates) {
                    try {
                        closePortNoLog(OPEN_PORT_NAME);
                        openPortNoLog(OPEN_PORT_NAME, baudRate);
                        SerialPort port = serialPortMap.get(OPEN_PORT_NAME);
                        OutputStream outputStream = port.getOutputStream();
                        InputStream inputStream = port.getInputStream();

                        if (!port.isOpen()) {
                            logService.warningLog(bundle.getString("portCantOpen"));
                            continue;
                        }

                        logService.updateInfoLog(bundle.getString("responseSpeed1") + baudRate + bundle.getString("responseSpeed2"));
                        String msg = "10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                        if (isRS) {
                            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                        }
                        outputStream.write(hexStringToByteArray(msg));
                        outputStream.flush();

                        byte[] buffer = new byte[1024];
                        int totalBytesRead = 0;
                        while (true) {
                            int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                            if (bytesRead > 0) {
                                totalBytesRead += bytesRead;
                                if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }

                        String response = bytesToHex(buffer, totalBytesRead);
                        if (!response.isBlank()) {
                            logService.updateInfoLog(OPEN_PORT_NAME + bundle.getString("rightSpeed1") + baudRate + bundle.getString("rightSpeed2"));
                            return baudRate;
                        }

                        closePortNoLog(OPEN_PORT_NAME);
                    } catch (IOException e) {
                        logService.warningLog(bundle.getString("timeout"));
                    }
                }

                logService.warningLog(bundle.getString("noSpeed"));
                return 0;
            }
        };
    }

    public Task<String> send300MsgAndGetMsg(String msg, String portNum, int baudRate) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                SerialPort port = SerialPort.getCommPort(portNum);
                port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, RESPONSE_LATENCY * 1000, RESPONSE_LATENCY * 1000);
                port.openPort();
                byte[] buffer = new byte[230];
                int totalBytesRead = 0;
                try {
                    // 데이터 전송
                    byte[] dataToSend = msg.getBytes(Charset.forName("MS949"));
                    OutputStream outputStream = new BufferedOutputStream(port.getOutputStream());
                    outputStream.write(dataToSend);
                    outputStream.flush();

                    // 데이터 수신
                    InputStream inputStream = new BufferedInputStream(port.getInputStream());


                    while (totalBytesRead < 230) { // 212바이트가 채워질 때까지 읽기
                        int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;
                        } else {
                            break; // 더 이상 읽을 데이터가 없을 경우
                        }
                    }

                    if (totalBytesRead <= 230) {
                        // 212바이트를 읽었으면 결과 출력
                        String result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));
                        if (result.contains("TX")) {
                            result = result.substring(0, result.indexOf("TX") + 2); // "TX" 포함하여 잘라냄
                        }
                        dabitNetService.updateUI(result);
                        return result;
                    } else {
                        throw new IOException("212 바이트를 읽는 데 실패했습니다. 총 읽은 바이트: " + totalBytesRead);
                    }
                } catch (SerialPortTimeoutException e) {
                    String result = new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));
                    if (result.contains("TX")) {
                        result = result.substring(0, result.indexOf("TX") + 2); // "TX" 포함하여 잘라냄
                    }
                    String[] lines = result.split("\r?\n"); // 윈도우(\r\n)와 유닉스(\n) 모두 대응 가능
                    int lineCount = lines.length;
                    if (lineCount >= 12) {
                        dabitNetService.updateUI(result);
                    }
                    return new String(buffer, 0, totalBytesRead, Charset.forName("MS949"));
                } catch (Exception e) {
                    logService.errorLog(bundle.getString("connectionFail"));
                    throw e;
                } finally {
                    port.closePort();
                }
            }
        };
    }


    public Task<Void> send300ByteMsg(byte[] sendByte, String portNum, int baudRate) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SerialPort port = SerialPort.getCommPort(portNum);
                port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, RESPONSE_LATENCY * 1000, RESPONSE_LATENCY * 1000);
                port.openPort();

                OutputStream outputStream = new BufferedOutputStream(port.getOutputStream());
                outputStream.write(sendByte);
                outputStream.flush();

                port.closePort();

                return null;
            }
        };
    }
}