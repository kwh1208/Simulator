package dbps.dbps.service;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class SerialPortManager {
    private static final Logger log = LoggerFactory.getLogger(SerialPortManager.class);
    private final Map<String, SerialPort> serialPortMap = new HashMap<>();

    private static SerialPortManager instance = null;

    public static synchronized SerialPortManager getInstance() {
        if (instance == null) {
            instance = new SerialPortManager();
        }
        return instance;
    }

    private SerialPortManager() {
    }



    public void openPort(String portName, int baudRate) throws IOException {
        SerialPort port = SerialPort.getCommPort(portName);
        port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0);
        if (!port.openPort()) {
            throw new IOException("Failed to open port " + portName);
        }
        serialPortMap.put(portName, port);
        log.info("포트가 열렸습니다.");
    }



    public void closePort(String portName){
        if (serialPortMap.containsKey(portName)){
            SerialPort serialPort = serialPortMap.get(portName);
            if (serialPort.isOpen()){
                serialPort.closePort();
                log.info("포트가 닫혔습니다.");
            } else {
                log.info("포트가 이미 닫혀있습니다.");
            }
        } else {
            log.info("해당 포트가 존재하지 않습니다.");
        }
    }

    public boolean isPortOpen(String portName) {
        SerialPort port = serialPortMap.get(portName);
        return port != null && port.isOpen();
    }

    public String sendMsgAndGetMsg(String portName, String message, int timeoutSec){
        SerialPort port = serialPortMap.get(portName);
        if (port == null || !port.isOpen()) {
            log.info("포트 {}가 열려 있지 않습니다.", portName);
            return null;
        }
        timeoutSec*=1000;
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            byte[] dataToSend;
            dataToSend = message.getBytes();

            log.info("전송 메세지 = {}", dataToSend);

            outputStream.write(dataToSend);
            outputStream.flush();

            long startTime = System.currentTimeMillis();

            StringBuilder response = new StringBuilder();
            byte[] buffer = new byte[1024];
            int numBytesRead;
            while ((System.currentTimeMillis() - startTime) < timeoutSec) {
                log.info("응답 대기 중...");
                if (inputStream.available() > 0) {
                    numBytesRead = inputStream.read(buffer);
                    response.append(new String(buffer, 0, numBytesRead));
                }
                Thread.sleep(1000); // 짧은 대기 시간
            }

            log.info("메시지 전송 성공 메세지 = {}", response.toString());
            return response.toString();
        }catch (Exception e){
            log.error("메시지 전송 중 오류 발생", e);
            return null;
        }
    }


    public String sendMsgAndGetMsgHex(String portName, String msg, int timeoutSec){
        SerialPort port = serialPortMap.get(portName);
        if (port == null || !port.isOpen()) {
            log.info("포트 {}가 열려 있지 않습니다.", portName);
            return null;
        }
        timeoutSec*=1000;
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            byte[] dataToSend;
            dataToSend = hexStringToByteArray("!["+msg+"!]");

            log.info("전송 메세지 = {}", dataToSend);

            outputStream.write(dataToSend);
            outputStream.flush();

            long startTime = System.currentTimeMillis();

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int numBytesRead;
            while ((System.currentTimeMillis() - startTime) < timeoutSec) {
                log.info("응답 대기 중...");
                if (inputStream.available() > 0) {
                    numBytesRead = inputStream.read(buffer);
                    responseStream.write(buffer, 0, numBytesRead);
                }
                Thread.sleep(1000); // 짧은 대기 시간
            }

            String hexResponse = bytesToHex(responseStream.toByteArray());
            log.info("메시지 전송 성공 메세지 = {}", hexResponse);
            return hexResponse;
        }catch (Exception e){
            log.error("메시지 전송 중 오류 발생", e);
            return null;
        }

    }


    public byte[] hexStringToByteArray(String hex) {
        String[] hexPairs = hex.split(" ");
        byte[] bytes = new byte[hexPairs.length];
        for (int i = 0; i < hexPairs.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexPairs[i], 16);
        }
        return bytes;
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    public int findSpeed(String portName) {
        int[] baudRates = {2400, 4800, 9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};

        for (int baudRate : baudRates) {
            try {
                openPort(portName, baudRate);
                SerialPort port = serialPortMap.get(portName);
                OutputStream outputStream = port.getOutputStream();
                String msg = "10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                byte[] dataToSend = hexStringToByteArray(msg);
                outputStream.write(dataToSend);
                outputStream.flush();

                InputStream inputStream = port.getInputStream();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                int numBytesRead;
                long startTime = System.currentTimeMillis();

                // 데이터 읽기
                while (System.currentTimeMillis() - startTime < 1000) { // 1초 타임아웃
                    if (inputStream.available() > 0) {
                        numBytesRead = inputStream.read(buffer);
                        responseStream.write(buffer, 0, numBytesRead);
                        break; // 데이터가 읽히면 루프 종료
                    }
                    Thread.sleep(100); // 짧은 대기 시간
                }

                String response = bytesToHex(responseStream.toByteArray());
                log.info("Response at baud rate {}: {}", baudRate, response);

                if (!response.isBlank()) {
                    closePort(portName);
                    return baudRate;
                }
            } catch (IOException | InterruptedException e) {
                log.error("Error with baud rate {}: {}", baudRate, e.getMessage());
            } finally {
                closePort(portName);
            }
        }
        return 0; // 적정 보드레이트를 찾지 못한 경우
    }

}
