package dbps.dbps.service.connectManager;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.LogService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public SerialPort openPortNoLog(String portName, int baudRate){
        if (serialPortMap.containsKey(portName)&&isPortOpen(portName)){
            serialPortMap.get(portName).closePort();
            serialPortMap.remove(portName);
        }

        SerialPort port = SerialPort.getCommPort(portName);
        port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, RESPONSE_LATENCY *1000, 0);
        port.openPort();

        return port;
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

    public String sendMsgAndGetMsg(String message){
        String portName = OPEN_PORT_NAME;
        SerialPort port = serialPortMap.get(portName);

        if (port == null || !isPortOpen(portName)) {
            openPort(portName, SERIAL_BAUDRATE);
            port = serialPortMap.get(portName);
        }
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            byte[] dataToSend = message.getBytes();
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

    private boolean dataReceivedIsComplete(byte[] buffer, int length) {
        return length > 0 && buffer[length - 1]==(byte) ']' && buffer[length - 2]==(byte) '!';
    }


    public String sendMsgAndGetMsgHex(String msg) {
        String portName = OPEN_PORT_NAME;
        SerialPort port = serialPortMap.get(portName);

        if (port == null || !isPortOpen(portName)) {
            openPort(portName, SERIAL_BAUDRATE);
            port = serialPortMap.get(portName);
        }

        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();
            byte[] dataToSend = hexStringToByteArray(msg);
            outputStream.write(dataToSend);
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

    public int findSpeed() {
        int[] baudRates = {2400, 4800, 9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};

        for (int baudRate : baudRates) {
            try {
                SerialPort port = openPortNoLog(OPEN_PORT_NAME, baudRate);
                OutputStream outputStream = port.getOutputStream();
                InputStream inputStream = port.getInputStream();
                if (!port.isOpen()){
                    logService.warningLog("포트를 열 수 없습니다.");
                }

                String msg = "10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                outputStream.write(hexStringToByteArray(msg));
                outputStream.flush();
                Thread.sleep(100);
                byte[] buffer = new byte[1024];
                int numRead = inputStream.read(buffer, 0, buffer.length);
                String response = bytesToHex(buffer, numRead);
                if (!response.isBlank()) {
                    logService.updateInfoLog("통신 속도 찾기 성공");
                    logService.updateInfoLog(OPEN_PORT_NAME +"의 적정 통신 속도는 "+ baudRate + "입니다.");
                    return baudRate;
                }
                closePortNoLog(OPEN_PORT_NAME);
            } catch (IOException | InterruptedException e) {
            }
        }
        return 0;
    }
}