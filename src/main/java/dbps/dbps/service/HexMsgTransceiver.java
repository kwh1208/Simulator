package dbps.dbps.service;

import dbps.dbps.service.connectManager.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static dbps.dbps.Constants.CONNECT_TYPE;

public class HexMsgTransceiver {

    private static HexMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final RS485Manager rs485Manager;
    private final WiFiManager wiFiManager;

    private HexMsgTransceiver() {
        serialPortManager = SerialPortManager.getManager();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
        rs485Manager = RS485Manager.getInstance();
        wiFiManager = WiFiManager.getInstance();
    }

    public static HexMsgTransceiver getInstance() {
        if (instance == null) {
            instance = new HexMsgTransceiver();
        }
        return instance;
    }

    public String sendMessages(String msg) {
        String receivedMsg = "";

        //로그 출력
        logService.updateInfoLog("전송 메세지: " + msg);

        switch (CONNECT_TYPE) {
            case "serial", "bluetooth" -> //시리얼 및 블루투스
                    receivedMsg = serialPortManager.sendMsgAndGetMsgHex(msg);
            case "udp" -> //udp로 메세지 전송
                    receivedMsg = udpManager.sendMsgAndGetMsgHex(msg);
            case "tcp" -> //tcp로 메세지 전송
                    receivedMsg = tcpManager.sendMsgAndGetMsgHex(msg);
            case "rs485" ->  //rs485로 메세지 전송
                    receivedMsg = rs485Manager.sendMsgAndGetMsgHex(msg);
            case "WiFi" -> //WiFi로 메세지 전송
                    receivedMsg = wiFiManager.sendMsgAndGetMsgHex(msg);
        }

        //시간 바꾸거나, 펌웨어 같은 일부 특수한 경우에 반환값 사용.
        return msgReceive(receivedMsg, msg);
    }

    private String msgReceive(String receiveMsg, String msg) {
        if (receiveMsg==null) {
            return null;
        }
        String[] splitMsg = receiveMsg.split(" ");
        if (splitMsg[5].equals("94")){
            //일반 메세지
            chkErrorCode(receiveMsg, splitMsg);
        }
        if (splitMsg[5].equals("6A")){
            //특수 메세지
            for (int i = 6; i < 16; i++) {
                if (splitMsg[i].equals("3"+i)){
                    logService.errorLog("커맨드가 없습니다.");
                    return null;
                }
            }
            logService.updateInfoLog("연결되었습니다.");
            logService.updateInfoLog("받은 메세지 : " + receiveMsg);
        }


        return chkSpecificCmdCode(receiveMsg, msg);
    }

    public String chkSpecificCmdCode(String receiveMsg, String msg) {
        String[] splitMsg = receiveMsg.split(" ");
        String command = splitMsg[5];
        String status = splitMsg[6];

        switch (command) {
            case "40" -> handleScreenSizeSetting(splitMsg, msg);
            case "66" -> handleTimeRead(receiveMsg, splitMsg);
            default -> handleDefaultCommands(command, status, receiveMsg, splitMsg);
        }
        return receiveMsg;
    }

    private void handleScreenSizeSetting(String[] splitMsg, String msg) {
        if (!splitMsg[7].equals(msg.split(" ")[7]) || !splitMsg[8].equals(msg.split(" ")[8])) {
            logService.warningLog("화면 크기 설정에 실패했습니다.");
            logService.warningLog(splitMsg[7] + "단, " + splitMsg[8] + "열까지만 가능합니다.");
        } else {
            logService.updateInfoLog("받은 메세지 : " + msg);
            logService.updateInfoLog("화면 크기 설정에 성공했습니다.");
        }
    }

    private void handleTimeRead(String receiveMsg, String[] splitMsg) {
        if (!splitMsg[6].equals("10") && !splitMsg[6].equals("20") && !splitMsg[6].equals("40") && !splitMsg[6].equals("80")) {
            StringBuilder time = new StringBuilder();
            for (int i = 6; i < 12; i++) { // 수정된 범위
                time.append(splitMsg[i]);
            }

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyMMddHHmmss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yy년 MM월 dd일 E요일 HH시 mm분 ss초", Locale.KOREAN);

            try {
                Date date = inputFormat.parse(time.toString());
                String formattedTime = outputFormat.format(date);
                logService.updateInfoLog("받은 메세지 : " + receiveMsg);
                logService.updateInfoLog("컨트롤러 시간은 " + formattedTime + "입니다.");
            } catch (Exception e) {
                logService.warningLog("시간 변환에 실패했습니다: " + e.getMessage());
            }
        } else {
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    private void handleDefaultCommands(String command, String status, String receiveMsg, String[] splitMsg) {
        // 단순 상태 코드 확인 및 로그 출력
        if (status.equals("00")) {
            logService.updateInfoLog(getSuccessMessage(command));
        } else {
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    private String getSuccessMessage(String command) {
        return switch (command) {
            case "4C" -> "페이지 메세지 개수 등록에 성공했습니다.";
            case "4B" -> "페이지 메모리 전체 삭제에 성공했습니다.";
            case "4A" -> "하드 리셋에 성공했습니다.";
            case "41" -> "화면 on/off에 성공했습니다.";
            case "4F" -> "배경이미지 호출에 성공했습니다.";
            case "44" -> "화면 밝기 조절에 성공했습니다.";
            case "47" -> "시간 동기화에 성공했습니다.";
            case "42" -> "화면 단일색으로 채우기에 성공했습니다.";
            default -> "작업에 성공했습니다.";
        };
    }

    private void chkErrorCode(String receiveMsg, String[] splitMsg) {
        switch (splitMsg[6]){
            case "00" ->
                    logService.updateInfoLog("받은 메세지 : " + receiveMsg);
            case "10" ->
                    logService.errorLog("커맨드가 없습니다. " + receiveMsg);
            case "20" ->
                    logService.warningLog("No Function(커맨드 비활성) " + receiveMsg);
            case "40" ->
                    logService.updateInfoLog("데이터가 허용 범위를 벗어났습니다. " + receiveMsg);
            case "80" ->
                    logService.updateInfoLog("알 수 없는 에러가 발생했습니다." + receiveMsg);
        }
    }
}
