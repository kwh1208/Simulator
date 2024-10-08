package dbps.dbps.service;

import dbps.dbps.service.connectManager.*;
import javafx.concurrent.Task;


import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;

public class HexMsgTransceiver {

    private static HexMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;

    private HexMsgTransceiver() {
        serialPortManager = SerialPortManager.getManager();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
    }

    public static HexMsgTransceiver getInstance() {
        if (instance == null) {
            instance = new HexMsgTransceiver();
        }
        return instance;
    }

    public String sendByteMessages(byte[] msg) {
        String receivedMsg = "";

        //로그 출력
        logService.updateInfoLog("전송 메세지: " + bytesToHex(msg, msg.length));

        switch (CONNECT_TYPE) {
            case "serial", "bluetooth", "rs485" -> {
                try {
                    // Task 객체를 생성하여 비동기 작업 실행
                    Task<String> sendTask = serialPortManager.sendMsgAndGetMsgByte(msg);

                    // 새로운 스레드에서 Task를 실행
                    Thread taskThread = new Thread(sendTask);
                    taskThread.start();

                    // Task의 완료를 기다리고 결과를 동기적으로 가져오기
                    receivedMsg = sendTask.get(); // get() 메서드는 Task 완료까지 대기함
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "UDP" -> //udp로 메세지 전송
            {
                try {
                    Task<String> sendTask = udpManager.sendMsgAndGetMsgByte(msg);
                    Thread taskThread = new Thread(sendTask);
                    taskThread.start();

                    receivedMsg = sendTask.get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "TCP" -> //tcp로 메세지 전송
            {
                try {
                    Task<String> sendTask = tcpManager.sendMsgAndGetMsgByte(msg);
                    Thread taskThread = new Thread(sendTask);
                    taskThread.start();

                    receivedMsg = sendTask.get();
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //시간 바꾸거나, 펌웨어 같은 일부 특수한 경우에 반환값 사용.
        return msgReceive(receivedMsg, msg);
    }

    public String sendMessages(String msg) {
        return sendByteMessages(hexStringToByteArray(msg));
    }

    private String msgReceive(String receiveMsg, byte[] msg) {
        if (receiveMsg==null) {
            return null;
        }
        String[] splitMsg = receiveMsg.split(" ");
        if (splitMsg[5].equals("94")){
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

    public String chkSpecificCmdCode(String receiveMsg, byte[] msg) {
        String[] splitMsg = receiveMsg.split(" ");
        String command = splitMsg[5];
        String status = splitMsg[6];

        switch (command) {
            case "40" -> handleScreenSizeSetting(splitMsg, msg);
            case "66" -> handleTimeRead(receiveMsg, splitMsg);
            case "6F" -> {
                return receiveMsg;
            }

            default -> handleDefaultCommands(status, receiveMsg, splitMsg);
        }
        return receiveMsg;
    }


    private void handleScreenSizeSetting(String[] splitMsg, byte[] msg) {
        if (!splitMsg[7].equals(String.format("%02X", msg[7])) || !splitMsg[8].equals(String.format("%02X", msg[8]))) {
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

    private void handleDefaultCommands(String status, String receiveMsg, String[] splitMsg) {
        // 단순 상태 코드 확인 및 로그 출력
        if (status.equals("00")) {
            logService.updateInfoLog("받은 메세지 : " + receiveMsg); // 받은 메세지 출력
        } else {
            chkErrorCode(receiveMsg, splitMsg);
        }
    }

    private void chkErrorCode(String receiveMsg, String[] splitMsg) {
        switch (splitMsg[6]){
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
