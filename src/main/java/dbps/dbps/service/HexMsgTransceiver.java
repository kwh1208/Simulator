package dbps.dbps.service;

import dbps.dbps.service.connectManager.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.*;
import static dbps.dbps.controller.FontNameController.getFontName;

public class HexMsgTransceiver {

    private static HexMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final ServerTCPManager serverTCPManager;
    private final MQTTManager mqttManager;
    private final UnderTheLineLeftService underTheLineLeftService;
    private final SizeOfDisplayBoardService sizeOfDisplayBoardService;
    private final HexMsgService hexMsgService;
    private final FontNameService fontNameService;
    private final ResourceBundle bundle;

    private static final int MIN_YEAR = 0, MAX_YEAR = 99;
    private static final int MIN_MONTH = 1, MAX_MONTH = 12;
    private static final int MIN_DAY = 1, MAX_DAY = 31;
    private static final int MIN_DAY_OF_WEEK = 0, MAX_DAY_OF_WEEK = 6;
    private static final int MIN_HOUR = 0, MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0, MAX_MINUTE = 59;
    private static final int MIN_SECOND = 0, MAX_SECOND = 59;


    private HexMsgTransceiver() {
        serialPortManager = SerialPortManager.getManager();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
        serverTCPManager = ServerTCPManager.getInstance();
        underTheLineLeftService = UnderTheLineLeftService.getInstance();
        sizeOfDisplayBoardService = SizeOfDisplayBoardService.getInstance();
        hexMsgService=HexMsgService.getInstance();
        fontNameService = FontNameService.getInstance();
        bundle=ResourceManager.getInstance().getBundle();
        mqttManager = MQTTManager.getInstance();
    }

    public static HexMsgTransceiver getInstance() {
        if (instance == null) {
            instance = new HexMsgTransceiver();
        }
        return instance;
    }

    public CompletableFuture<String> sendByteMessages(byte[] msg, ProgressIndicator progressIndicator) {
        CompletableFuture<String> resultFuture = new CompletableFuture<>();
        Task<String> sendTask = switch (CONNECT_TYPE) {
            case "serial", "bluetooth", "rs485" -> serialPortManager.sendMsgAndGetMsgByte(msg);
            case "UDP" -> udpManager.sendMsgAndGetMsgByte(msg);
            case "clientTCP" -> tcpManager.sendMsgAndGetMsgByte(msg);
            case "serverTCP" -> serverTCPManager.sendMsgAndGetMsgByte(msg);
            case "mqtt" -> mqttManager.sendByteMsg(msg);
            default -> throw new IllegalStateException("Unexpected value: " + CONNECT_TYPE);
        };


        if (sendTask != null) {
            sendTask.setOnSucceeded(event -> {
                String receivedMsg = sendTask.getValue();
                msgReceive(receivedMsg, msg);
                resultFuture.complete(receivedMsg); // 성공 시 CompletableFuture에 결과 전달

                Platform.runLater(() -> {
                    if (progressIndicator != null) {
                        progressIndicator.setVisible(false);
                    }
                });
            });

            sendTask.setOnFailed(event -> {
                Throwable exception = sendTask.getException();
                resultFuture.completeExceptionally(exception); // 실패 시 예외 전달

                Platform.runLater(() -> {
                    if (progressIndicator != null) {
                        progressIndicator.setVisible(false);
                    }
                });
            });

            new Thread(sendTask).start(); // 비동기로 실행
        } else {
            resultFuture.completeExceptionally(new IllegalStateException("Task is null."));
        }
        return resultFuture;
    }

    public void close() {
        switch (CONNECT_TYPE){
            case "serial", "bluetooth", "rs485" -> {
                try {
                    // Task 객체를 생성하여 비동기 작업 실행
                    serialPortManager.closePort(OPEN_PORT_NAME);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "UDP" -> //udp로 메시지 전송
            {
                try {
                    udpManager.disconnect();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "clientTCP" -> //tcp로 메시지 전송
            {
                try {
                    tcpManager.disconnect();
                } catch (Exception e) {

                    throw new RuntimeException(e);
                }
            }
            case "serverTCP" ->{
                try {
                    serverTCPManager.disconnect();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }



    public void sendByteMessagesNoLog(byte[] msg) {
        switch (CONNECT_TYPE) {
            case "serial", "bluetooth", "rs485" -> {
                try {
                    // Task 객체를 생성하여 비동기 작업 실행
                    serialPortManager.sendMsgAndGetMsgByteNoLog(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            case "UDP" -> //udp로 메시지 전송
            {
                try {
                    udpManager.sendMsgAndGetMsgByteNoLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "clientTCP" -> //tcp로 메시지 전송
            {
                try {
                    tcpManager.sendMsgAndGetMsgByteNoLog(msg);
                } catch (Exception e) {

                    throw new RuntimeException(e);
                }
            }
            case "serverTCP" ->{
                try {
                    serverTCPManager.sendMsgAndGetMsgByteNoLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "mqtt" ->{
                try {
                    mqttManager.sendByteMsgNoLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void sendByteMessagesShortLog(byte[] msg) throws IOException {
        switch (CONNECT_TYPE) {
            case "serial", "bluetooth", "rs485" -> {
                try {
                    // Task 객체를 생성하여 비동기 작업 실행
                    serialPortManager.sendMsgAndGetMsgByteShortLog(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            case "UDP" -> //udp로 메시지 전송
            {
                try {
                    udpManager.sendMsgAndGetMsgByteShortLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "clientTCP" -> //tcp로 메시지 전송
            {
                try {
                    tcpManager.sendMsgAndGetMsgByteShortLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "serverTCP" ->{
                try {
                    serverTCPManager.sendMsgAndGetMsgByteShortLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } case "mqtt" ->{
                try {
                    mqttManager.sendByteMsgShortLog(msg);
                } catch (InterruptedIOException e) {
                    throw new IOException(e);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void sendMessages(String msg, ProgressIndicator progressIndicator) {
        byte[] bytes = hexStringToByteArray(msg);
        sendByteMessages(bytes, progressIndicator);
    }

    private void msgReceive(String receiveMsg, byte[] msg) {
        receiveMsg = receiveMsg.toUpperCase();
        if (receiveMsg.isEmpty()) {
            return;
        }
        if (receiveMsg.contains(">DIBD")){
            updateFirmwareUIHEX(receiveMsg);
        }
        if (receiveMsg.startsWith("{") && receiveMsg.endsWith("}")) {

        }
        if (receiveMsg.contains("30 31 32 33 34 35 36 37 38 39")){
            logService.updateInfoLog(bundle.getString("connectionSuccess"));
        }
        String[] splitMsg = receiveMsg.split(" ");

        if (splitMsg[5].equals("6A")|| splitMsg[5].equals("6a")) {
            for (int i = 6; i < 16; i++) {
                if (!splitMsg[i].equals("3" + (i-6))) {
//                    logService.errorLog(bundle.getString("unknownStatusCode"));
                    logService.warningLog(bundle.getString("receivePacketError"));
                    return;
                }
            }
            return;
        }
        chkSpecificCmdCode(receiveMsg, msg);
    }

    public void chkSpecificCmdCode(String receiveMsg, byte[] msg) {
        String[] splitMsg = receiveMsg.split(" ");
        String length = splitMsg[4];
        String command = splitMsg[5];
        String status = splitMsg[6];
        if ((splitMsg.length-7)!=Integer.parseInt(length, 16)){
            System.out.println(111);
            logService.warningLog(bundle.getString("receivePacketError"));
            return;
        }

        switch (command) {
            case "40" -> {
                String tmp = bytesToHex(msg, msg.length);
                System.out.println("tmp = " + tmp);
                System.out.println("receiveMsg = " + receiveMsg);
                if (receiveMsg.replace(" ", "").equals(tmp.replace(" ", ""))) {
                    handleScreenSizeSetting(splitMsg, msg);
                    return;
                }
                if (!Objects.equals(length, "04")){
                    logService.warningLog(bundle.getString("receivePacketError"));
                    return;
                }
                handleScreenSizeSetting(splitMsg, msg);
            }
            case "66" -> handleTimeRead(receiveMsg, splitMsg);
            case "6F" -> {
                //Todo
                updateFirmwareUI(splitMsg);
            }
            case "4C" -> {
                if (splitMsg.length>9){
                    logService.warningLog(bundle.getString("receivePacketError"));
                    return;
                }
                hexMsgService.setUI(msg[6] & 0xFF);
            }

            case "48" ->{
                StringBuilder result = new StringBuilder();

                if (receiveMsg.length()<226&&!status.equals("00")){
                    logService.warningLog(bundle.getString("receivePacketError"));
                }

                for (int i = 7; i <= 224; i++) {
                    result.append(splitMsg[i]).append(" ");
                }

                String[] fontNames = null;
                try {
                    fontNames = getFontName(hexStringToByteArray(result.toString()));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

                fontNameService.getGroup1font1().setText(fontNames[0]);
                fontNameService.getGroup1font2().setText(fontNames[1]);
                fontNameService.getGroup1font3().setText(fontNames[2]);
                fontNameService.getGroup2font1().setText(fontNames[3]);
                fontNameService.getGroup2font2().setText(fontNames[4]);
                fontNameService.getGroup2font3().setText(fontNames[5]);
            }

            default -> handleDefaultCommands(status, receiveMsg, splitMsg);
        }
    }

    private void updateFirmwareUI(String[] splitMsg) {
        // splitMsg의 7번째부터 끝에서 3번째까지 추출
        StringBuilder hexStringBuilder = new StringBuilder();
        for (int i = 7; i < splitMsg.length - 3; i++) { // 인덱스 6부터 끝에서 3번째 전까지
            hexStringBuilder.append(splitMsg[i]);
        }

        // 헥사 문자열을 ASCII로 변환
        String hexString = hexStringBuilder.toString();
        StringBuilder asciiStringBuilder = new StringBuilder();
        for (int i = 0; i < hexString.length(); i += 2) {
            String hexChar = hexString.substring(i, i + 2); // 2글자씩 잘라서
            char asciiChar = (char) Integer.parseInt(hexChar, 16); // 16진수 -> 10진수 -> 문자
            asciiStringBuilder.append(asciiChar);
        }

        // ASCII 문자열 생성
        String asciiString = asciiStringBuilder.toString();

        // firmwareService에 전달
        FirmwareService.firmwareInformation.setText(asciiString);
    }

    private void updateFirmwareUIHEX(String msg) {
        // firmwareService에 전달
        FirmwareService.firmwareInformation.setText(msg);
    }

    //Todo 로그 수정
    private void handleScreenSizeSetting(String[] splitMsg, byte[] msg) {
        if (!splitMsg[7].equals(String.format("%02X", msg[7])) || !splitMsg[8].equals(String.format("%02X", msg[8]))) {
            logService.warningLog(bundle.getString("displaySizeSettingFailed"));
            logService.warningLog(MessageFormat.format(bundle.getString("displaySizeLimit"), Integer.parseInt(splitMsg[7], 16), Integer.parseInt(splitMsg[8], 16)));
        } else {
            logService.updateInfoLog(bundle.getString("displaySizeSettingSuccess"));
        }
        sizeOfDisplayBoardService.setDisplaySize(Integer.parseInt(splitMsg[7], 16), Integer.parseInt(splitMsg[8], 16));
    }

    Map<String, String> dayMap = new HashMap<>();

    private void handleTimeRead(String receiveMsg, String[] splitMsg) {

        processTimeString(receiveMsg.substring(18, 38));
        if (!splitMsg[6].equals("10") && !splitMsg[6].equals("20") && !splitMsg[6].equals("40") && !splitMsg[6].equals("80")) {
            StringBuilder time = new StringBuilder();

            // 현재 언어 설정 확인
            boolean isKorean = bundle.getLocale().getLanguage().equals("ko");
            //10 02 00 00 08 66 00 01 01 00 00 06 03 10 03
            //10 02 00 00 08 66 00 01 01 00 00 09 27 10 03

            // 요일 변환을 위한 매핑
            if (dayMap.isEmpty()) {
                if (isKorean) {
                    dayMap.put("01", "월"); // 일요일
                    dayMap.put("02", "화"); // 월요일
                    dayMap.put("03", "수"); // 화요일
                    dayMap.put("04", "목"); // 수요일
                    dayMap.put("05", "금"); // 목요일
                    dayMap.put("06", "토"); // 금요일
                    dayMap.put("00", "일"); // 토요일
                } else {
                    dayMap.put("01", "Mon"); // Sunday
                    dayMap.put("02", "Tue"); // Monday
                    dayMap.put("03", "Wed"); // Tuesday
                    dayMap.put("04", "Thu"); // Wednesday
                    dayMap.put("05", "Fri"); // Thursday
                    dayMap.put("06", "Sat"); // Friday
                    dayMap.put("00", "Sun"); // Saturday
                }
            }

            String dayPart = splitMsg[9];
            String dayOfWeek = dayMap.getOrDefault(dayPart, "?");

            // 날짜 및 시간 포맷 구성
            time.append(splitMsg[6]).append("-").append(splitMsg[7]).append("-").append(splitMsg[8])
                    .append(" (").append(dayOfWeek).append(") ")
                    .append(splitMsg[10]).append(":").append(splitMsg[11]).append(":").append(splitMsg[12]);

            try {
                underTheLineLeftService.setTime(time.toString());
                logService.updateInfoLog(MessageFormat.format(bundle.getString("controllerTimeInfo"), time));
            } catch (Exception e) {
                logService.warningLog(bundle.getString("controllerTimeReadFailed"));
            }
        } else {
            logService.warningLog(bundle.getString("receivePacketError"));
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    public void processTimeString(String timeStr){
        if (timeStr == null || timeStr.trim().isEmpty()) {
            logService.warningLog(bundle.getString("receivePacketError"));
            return;
        }

        // 공백 기준으로 분리 (토큰의 개수는 7개여야 함)
        String[] tokens = timeStr.trim().split("\\s+");
        if (tokens.length != 7) {
            logService.warningLog(bundle.getString("receivePacketError"));
            return;
        }

        try {
            // 숫자들은 보통 16진수(hex)로 표현되므로, 만약 16진수 형태라면 radix 16로 변환합니다.
            // 십진수라면 Integer.parseInt(token) 으로 변경하면 됩니다.
            int year = Integer.parseInt(tokens[0]);
            int month = Integer.parseInt(tokens[1]);
            int day = Integer.parseInt(tokens[2]);
            int dayOfWeek = Integer.parseInt(tokens[3]);
            int hour = Integer.parseInt(tokens[4]);
            int minute = Integer.parseInt(tokens[5]);
            int second = Integer.parseInt(tokens[6]);

            // 각 필드의 값이 범위 내에 있는지 검증
            if (year < MIN_YEAR || year > MAX_YEAR) {
                logService.warningLog(bundle.getString("receivePacketError"));
                return;
            }
            if (month < MIN_MONTH || month > MAX_MONTH) {
                logService.warningLog(bundle.getString("receivePacketError"));
                return;
            }
            if (day < MIN_DAY || day > MAX_DAY) {
                logService.warningLog(bundle.getString("receivePacketError"));
                return;
            }
            if (dayOfWeek < MIN_DAY_OF_WEEK || dayOfWeek > MAX_DAY_OF_WEEK) {
                logService.warningLog(bundle.getString("receivePacketError"));
                return;
            }
            if (hour < MIN_HOUR || hour > MAX_HOUR) {
                logService.warningLog(bundle.getString("receivePacketError"));
                return;
            }
            if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
                logService.warningLog(bundle.getString("receivePacketError"));
                return;
            }
            if (second < MIN_SECOND || second > MAX_SECOND) {

                logService.warningLog(bundle.getString("receivePacketError"));
            }

        } catch (NumberFormatException e) {
            logService.warningLog(bundle.getString("receivePacketError"));
        }
    }

    private void handleDefaultCommands(String status, String receiveMsg, String[] splitMsg) {
        if (splitMsg.length>9){

            logService.warningLog(bundle.getString("receivePacketError"));
        }
        if (!status.equals("00")) {
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    private void chkErrorCode(String receiveMsg, String[] splitMsg) {
        switch (splitMsg[6]) {
            case "10" -> logService.errorLog(bundle.getString("noCommand") );
            case "20" -> logService.warningLog(bundle.getString("noFunction") );
            case "40" -> logService.warningLog(bundle.getString("dataOutOfRange"));
            case "80" -> logService.errorLog(bundle.getString("unknownError") );
        }
    }
}
