package dbps.dbps.service.connectManager;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortIOException;
import com.fazecast.jSerialComm.SerialPortTimeoutException;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.LogService;
import javafx.concurrent.Task;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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
    private static final Object portLock = new Object();
    private final BlockingQueue<Task<?>> taskQueue = new LinkedBlockingQueue<>();

    private SerialPortManager() {
        logService = LogService.getLogService();
        configService = ConfigService.getInstance();
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
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, RESPONSE_LATENCY * 1000);

            if (!port.openPort()) {
                logService.errorLog(portName + " 포트를 열 수 없습니다.");
            }

            serialPortMap.put(portName, port);
            logService.updateInfoLog(portName + " 포트가 열렸습니다.");
        }
    }

    public void openPortNoLog(String portName, int baudRate) {
        synchronized (portLock) {
            if (serialPortMap.containsKey(portName) && isPortOpen(portName)) {
                return;
            }
            SerialPort port = SerialPort.getCommPort(portName);
            port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, RESPONSE_LATENCY * 1000);

            if (!port.openPort()) {
                throw new IllegalStateException(portName + " 포트를 열 수 없습니다.");
            }

            serialPortMap.put(portName, port);
        }
    }


    public void closePort(String portName) {
        synchronized (portLock) {
            SerialPort port = serialPortMap.get(portName);
            if (port != null && port.isOpen()) {
                port.closePort();
                logService.updateInfoLog(portName + " 포트가 닫혔습니다.");
            }
            serialPortMap.remove(portName);
        }
    }

    public void closePortNoLog(String portName) {
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

                    logService.updateInfoLog("전송 메세지: " + msg);

                    try (InputStream inputStream = new BufferedInputStream(port.getInputStream());
                         OutputStream outputStream = new BufferedOutputStream(port.getOutputStream())) {
                        inputStream.skip(inputStream.available());
                        byte[] dataToSend = msg.getBytes(utf8 ? StandardCharsets.UTF_8 : Charset.forName("EUC-KR"));
                        outputStream.write(dataToSend);
                        outputStream.flush();

                        byte[] buffer = new byte[1024];
                        int totalBytesRead = 0;
                        while (true) {
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

                        String result = new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                        if (result.contains("TX") && result.contains("![") && result.contains("!]")) {
                            int tmp = extractNumberAfterTXBeforeByte(result);
                            if (tmp > 0 && 14 + String.valueOf(tmp).length() + result.indexOf("TX(") + tmp <= buffer.length) {
                                result = new String(buffer, 14 + String.valueOf(tmp).length() + result.indexOf("TX("), tmp, Charset.forName("EUC-KR"));
                            } else {
                                throw new IllegalArgumentException("유효하지 않은 offset 또는 tmp 값입니다.");
                            }
                        }
                        logService.updateInfoLog("받은 메세지: " + result);
                        return result;

                    } catch (SerialPortTimeoutException | SerialPortIOException e) {
                        logService.errorLog("통신에 실패했습니다. 연결상태를 확인해주세요.");
                        throw e;
                    } catch (Exception e) {
                        logService.errorLog("기타 예외 발생: " + e.getMessage());
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


    private int extractNumberAfterTXBeforeByte(String input) {
        // "TX" 뒤의 "byte" 앞 숫자를 찾는 정규식
        Pattern pattern = Pattern.compile("TX.*?(\\d+)\\s*byte");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String number = matcher.group(1); // 첫 번째 그룹에서 숫자 추출
            return Integer.parseInt(number); // 숫자를 Integer로 변환하여 반환
        }

        return -1;
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

                    logService.updateInfoLog("전송 메세지: " + bytesToHex(msg, msg.length));

                    try (OutputStream outputStream = new BufferedOutputStream(port.getOutputStream());
                         InputStream inputStream = new BufferedInputStream(port.getInputStream())) {
                        inputStream.skip(inputStream.available());
                        outputStream.write(msg);
                        outputStream.flush();

                        byte[] buffer = new byte[1024];
                        int totalBytesRead = 0;

                        while (true) {
                            int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
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
                        logService.updateInfoLog("받은 메세지: " + result);
                        return result;

                    } catch (SerialPortTimeoutException | SerialPortIOException e) {
                        logService.errorLog("통신에 실패했습니다. 연결상태를 확인해주세요.");
                        throw e;
                    } catch (Exception e) {
                        logService.errorLog("기타 예외 발생: " + e.getMessage());
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


    public Task<String> sendMsgAndGetMsgByteNoLog(byte[] msg) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
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
                    outputStream.flush();

                    // 읽기용 버퍼 초기화
                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;

                    // 반복적으로 읽어 남아있는 데이터를 모두 수신
                    while (true) {
                        int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;

                            // 데이터가 모두 수신되었는지 확인
                            if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                break;
                            }
                        } else {
                            // 타임아웃 발생 시 루프 종료
                            break;
                        }
                    }

                    // 수신된 모든 데이터를 Hex로 변환하여 반환
                    return bytesToHex(buffer, totalBytesRead);
                } catch (Exception e) {
                    logService.errorLog("에러가 발생했습니다: " + e.getMessage());
                    throw e;
                } finally {
                    closePortNoLog(portName);
                }
            }
        };
    }

    public Task<Integer> findSpeedTask = new Task<>() {
        @Override
        protected Integer call() throws Exception {
            int[] baudRates = {9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};

            serialPortMap.get(OPEN_PORT_NAME).closePort();
            for (int baudRate : baudRates) {
                try {
                    openPortNoLog(OPEN_PORT_NAME, baudRate);
                    Thread.sleep(300);
                    SerialPort port = serialPortMap.get(OPEN_PORT_NAME);
                    OutputStream outputStream = port.getOutputStream();
                    InputStream inputStream = port.getInputStream();

                    if (!port.isOpen()) {
                        // 포트를 열 수 없을 때 로그 업데이트
                        logService.warningLog("포트를 열 수 없습니다.");
                        continue;
                    }

                    // 포트가 열렸을 때 로그 업데이트
                    logService.updateInfoLog("현재 속도 " + baudRate + "에서 응답을 대기 중...");

                    String msg = "10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                    if (isRS) {
                        msg = "10 02 " + String.format("02X ", RS485_ADDR_NUM) + "00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                    }
                    outputStream.write(hexStringToByteArray(msg));
                    outputStream.flush();

                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;
                    while (true) {
                        int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;

                            // 데이터가 모두 수신되었는지 확인
                            if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                                break;
                            }
                        } else {
                            // 타임아웃 발생 시 루프 종료
                            break;
                        }
                    }

                    String response = bytesToHex(buffer, totalBytesRead);

                    if (!response.isBlank()) {
                        // 통신 속도 찾기 성공 로그 업데이트
                        logService.updateInfoLog(OPEN_PORT_NAME + "의 적정 통신 속도는 " + baudRate + "입니다.");
                        return baudRate;
                    }

                    closePortNoLog(OPEN_PORT_NAME);
                } catch (IOException | InterruptedException e) {
                    // 예외 발생 시 로그 업데이트
                    logService.warningLog("예외 발생: " + e.getMessage());
                    throw e;
                }
            }

            // 통신 속도를 찾지 못한 경우 경고 로그 업데이트
            logService.warningLog("적정 통신 속도를 찾지 못했습니다.");
            return 0;
        }
    };

    public Task<String> send300MsgAndGetMsg(String msg, String portNum, int baudRate) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                SerialPort port = SerialPort.getCommPort(portNum);
                port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, RESPONSE_LATENCY * 1000, RESPONSE_LATENCY * 1000);
                port.openPort();
                try {
                    byte[] dataToSend = msg.getBytes(Charset.forName("EUC-KR"));
                    OutputStream outputStream = new BufferedOutputStream(port.getOutputStream());
                    outputStream.write(dataToSend);
                    outputStream.flush();

                    InputStream inputStream = new BufferedInputStream(port.getInputStream());
                    byte[] buffer = new byte[1024];
                    int totalBytesRead = 0;

                    while (true) {
                        int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                        if (bytesRead > 0) {
                            totalBytesRead += bytesRead;

                            // 데이터가 모두 수신되었는지 확인
                            if (dataReceivedIsComplete(buffer, totalBytesRead)) {
                                break;
                            }
                        } else {
                            // 타임아웃 발생 시 루프 종료
                            break;
                        }
                    }
                    String result = new String(buffer, 0, totalBytesRead, Charset.forName("EUC-KR"));
                    return result;
                } catch (Exception e) {
                    logService.errorLog("에러가 발생했습니다: " + e.getMessage());
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

                BufferedOutputStream output = new BufferedOutputStream(port.getOutputStream());
                output.write(sendByte);
                output.flush();

                port.closePort();

                return null;
            }
        };
    }
}