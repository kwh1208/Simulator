package dbps.dbps.service;

import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.CONNECT_TYPE;
import static dbps.dbps.Constants.hexStringToByteArray;
import static dbps.dbps.controller.FontNameController.getFontName;

public class HexMsgTransceiver {

    private static HexMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final ServerTCPManager serverTCPManager;
    private final UnderTheLineLeftService underTheLineLeftService;
    private final SizeOfDisplayBoardService sizeOfDisplayBoardService;
    private final HexMsgService hexMsgService;
    private final FontNameService fontNameService;


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



    public String sendByteMessagesNoLog(byte[] msg) {
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
            case "UDP" -> //udp로 메세지 전송
            {
                try {
                    udpManager.sendMsgAndGetMsgByteNoLog(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "clientTCP" -> //tcp로 메세지 전송
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
        }
        return null;
    }

    public String sendMessages(String msg, ProgressIndicator progressIndicator) {
        return String.valueOf(sendByteMessages(hexStringToByteArray(msg), progressIndicator));
    }

    private void msgReceive(String receiveMsg, byte[] msg) {
        if (receiveMsg.isEmpty()) {
            return;
        }
        if (receiveMsg.equals("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03 ")){
            logService.updateInfoLog("성공적으로 연결되었습니다.");
        }
        String[] splitMsg = receiveMsg.split(" ");
        if (splitMsg[5].equals("94")) {
            chkErrorCode(receiveMsg, splitMsg);
        }
        if (splitMsg[5].equals("6A")) {
            //특수 메세지
            for (int i = 6; i < 16; i++) {
                if (splitMsg[i].equals("3" + i)) {
                    logService.errorLog("커맨드가 없습니다.");
                    return;
                }
            }
        }
        chkSpecificCmdCode(receiveMsg, msg);
    }

    public void chkSpecificCmdCode(String receiveMsg, byte[] msg) {
        String[] splitMsg = receiveMsg.split(" ");
        String command = splitMsg[5];
        String status = splitMsg[6];

        switch (command) {
            case "40" -> {
                handleScreenSizeSetting(splitMsg, msg);
            }
            case "66" -> handleTimeRead(receiveMsg, splitMsg);
            case "6F" -> {
                updateFirmwareUI(splitMsg);
            }
            case "4C" -> {
                hexMsgService.setUI((msg[6]& 0xFF));
            }

            case "48" ->{
                StringBuilder result = new StringBuilder();

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


    private void handleScreenSizeSetting(String[] splitMsg, byte[] msg) {
        if (!splitMsg[7].equals(String.format("%02X", msg[7])) || !splitMsg[8].equals(String.format("%02X", msg[8]))) {
            logService.warningLog("화면 크기 설정에 실패했습니다.");
            logService.warningLog(Integer.parseInt(splitMsg[7]) + "단, " + Integer.parseInt(splitMsg[8]) + "열까지만 가능합니다.");
        } else {
            logService.updateInfoLog("화면 크기 설정에 성공했습니다.");
        }
        sizeOfDisplayBoardService.setDisplaySize(Integer.parseInt(splitMsg[7], 16), Integer.parseInt(splitMsg[8], 16));
    }

    Map<String, String> dayMap = new HashMap<>();

    private void handleTimeRead(String receiveMsg, String[] splitMsg) {
        if (!splitMsg[6].equals("10") && !splitMsg[6].equals("20") && !splitMsg[6].equals("40") && !splitMsg[6].equals("80")) {
            StringBuilder time = new StringBuilder();
            for (int i = 6; i < 13; i++) {
                time.append(splitMsg[i]);
            }

            if (dayMap.isEmpty()){
                dayMap.put("01", "월"); // 일요일
                dayMap.put("02", "화"); // 월요일
                dayMap.put("03", "수"); // 화요일
                dayMap.put("04", "목"); // 수요일
                dayMap.put("05", "금"); // 목요일
                dayMap.put("06", "토"); // 금요일
                dayMap.put("07", "일"); // 토요일
            }


            String datePart = time.substring(0, 6); // yyMMdd
            String dayPart = time.substring(6, 8); // 요일 (숫자로 표현된 요일)
            String timePart = time.substring(8);   // HHmmss

            // 요일 변환
            String dayOfWeek = dayMap.getOrDefault(dayPart, "?");

            // 날짜 변환
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyMMddHHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

            try {
                Date date = inputFormat.parse(datePart + timePart);
                String formattedDate = outputFormat.format(date);
                String result = formattedDate.replace(" ", " (" + dayOfWeek + ") ");
                underTheLineLeftService.setTime(result);
                logService.updateInfoLog("컨트롤러 시간은 " + result + "입니다.");
            } catch (Exception e) {
                logService.warningLog("시간 변환에 실패했습니다: " + e.getMessage());
            }
        } else {
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    private void handleDefaultCommands(String status, String receiveMsg, String[] splitMsg) {
        // 단순 상태 코드 확인 및 로그 출력
        if (status.equals("00")) {

        } else {
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    private void chkErrorCode(String receiveMsg, String[] splitMsg) {
        switch (splitMsg[6]) {
            case "10" -> logService.errorLog("커맨드가 없습니다. " + receiveMsg);
            case "20" -> logService.warningLog("No Function(커맨드 비활성) " + receiveMsg);
            case "40" -> logService.updateInfoLog("데이터가 허용 범위를 벗어났습니다. " + receiveMsg);
            case "80" -> logService.updateInfoLog("알 수 없는 에러가 발생했습니다." + receiveMsg);
        }
    }
}
