package dbps.dbps.service.connectManager;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.LogService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static dbps.dbps.controller.CommunicationSettingController.openPortName;
import static dbps.dbps.controller.CommunicationSettingController.selectedTime;

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
        port.openPort();
        serialPortMap.put(portName, port);
        logService.updateInfoLog(portName + " 포트가 열렸습니다.");
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

    public boolean isPortOpen(String portName) {
        SerialPort port = serialPortMap.get(portName);
        return port != null && port.isOpen();
    }

    public String sendMsgAndGetMsg(String message){
        String portName = openPortName;
        int timeoutSec = selectedTime;
        SerialPort port = serialPortMap.get(portName);

        if (port == null || !isPortOpen(portName)) {
            logService.updateInfoLog(portName+" 포트가 열려 있지 않습니다.");
            return null;
        }
        timeoutSec*=1000;
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            byte[] dataToSend;
            dataToSend = message.getBytes();

            logService.updateInfoLog("전송 메세지 = " + message);

            outputStream.write(dataToSend);
            outputStream.flush();

            long startTime = System.currentTimeMillis();

            StringBuilder response = new StringBuilder();
            byte[] buffer = new byte[1024];
            int numBytesRead;
            while ((System.currentTimeMillis() - startTime) < timeoutSec) {
                //로딩 애니메이션
                if (inputStream.available() > 0) {
                    numBytesRead = inputStream.read(buffer);
                    response.append(new String(buffer, 0, numBytesRead));
                }
                Thread.sleep(1000); // 짧은 대기 시간
            }

            logService.updateInfoLog("메시지 전송 성공 메세지 = "+ response);
            return response.toString();
        }catch (Exception e){
            logService.errorLog("메시지 전송 중 오류 발생");
            return null;
        }
    }


    public String sendMsgAndGetMsgHex(String msg){
        String portName = openPortName;
        int timeoutSec = selectedTime;
        SerialPort port = serialPortMap.get(portName);
        timeoutSec*=1000;
        try {
            OutputStream outputStream = port.getOutputStream();
            InputStream inputStream = port.getInputStream();

            byte[] dataToSend;
            dataToSend = hexStringToByteArray(msg);

            logService.updateInfoLog("전송 메세지 ="+ msg);

            outputStream.write(dataToSend);
            outputStream.flush();

            long startTime = System.currentTimeMillis();

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int numBytesRead;
            while ((System.currentTimeMillis() - startTime) < timeoutSec) {
                //로딩용 애니메이션
                if (inputStream.available() > 0) {
                    numBytesRead = inputStream.read(buffer);
                    responseStream.write(buffer, 0, numBytesRead);
                }
                Thread.sleep(1000); // 짧은 대기 시간
            }

            String hexResponse = bytesToHex(responseStream.toByteArray());
            logService.updateInfoLog("메시지 전송 성공 메세지 = "+ hexResponse);
            return hexResponse;
        }catch (Exception e){
            logService.errorLog("메시지 전송 중 오류 발생");
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

    public int findSpeed() {
        int[] baudRates = {2400, 4800, 9600, 19200, 38400, 57600, 115200, 230400, 460800, 921600};

        for (int baudRate : baudRates) {
            try {
                openPort(openPortName, baudRate);
                SerialPort port = serialPortMap.get(openPortName);
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

                if (!response.isBlank()) {
                    closePort(openPortName);
                    logService.updateInfoLog("통신 속도 찾기 성공");
                    logService.updateInfoLog(openPortName+"의 적정 통신 속도는 "+ baudRate + "입니다.");
                    return baudRate;
                }
            } catch (IOException | InterruptedException e) {
                logService.errorLog("Error with baud rate {}: {}"+ baudRate+ e.getMessage());
            } finally {
                closePort(openPortName);
            }
        }
        return 0;
    }

}
