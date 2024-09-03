package dbps.dbps.service;

import dbps.dbps.service.connectManager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static dbps.dbps.Constants.CONNECT_TYPE;

public class AsciiMsgTransceiver {

    private static final Logger log = LoggerFactory.getLogger(AsciiMsgTransceiver.class);
    private static AsciiMsgTransceiver instance = null;

    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final RS485Manager rs485Manager;
    private final WiFiManager wiFiManager;


    private AsciiMsgTransceiver() {
        serialPortManager = SerialPortManager.getManager();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
        rs485Manager = RS485Manager.getInstance();
        wiFiManager = WiFiManager.getInstance();
    }

    public static AsciiMsgTransceiver getInstance() {
        if (instance == null) {
            instance = new AsciiMsgTransceiver();
        }
        return instance;
    }


    public String sendMessages(String msg) {
        //현재 serial, bluetooth, udp, tcp, rs485, WiFi 중에 어떤 것인지 파악.
        //열려있는 곳으로 메세지 전송
        String receivedMsg = "";

        //로그 출력
        logService.updateInfoLog("전송 메세지: " + msg);

        switch (CONNECT_TYPE) {
            case "serial", "bluetooth" -> //시리얼 및 블루투스
                    receivedMsg = serialPortManager.sendMsgAndGetMsg(msg);
            case "udp" -> //udp로 메세지 전송
                    receivedMsg = udpManager.sendASCMsg(msg);
            case "tcp" -> //tcp로 메세지 전송
                    receivedMsg = tcpManager.sendASCMsg(msg);
            case "rs485" ->  //rs485로 메세지 전송
                    receivedMsg = rs485Manager.sendMsg(msg);
            case "WiFi" -> //WiFi로 메세지 전송
                    receivedMsg = wiFiManager.sendMsg(msg);
        }

        //시간 바꾸거나, 펌웨어 같은 일부 특수한 경우에 반환값 사용.
        return msgReceive(receivedMsg, msg);
    }


    private String msgReceive(String receiveMsg, String msg) {
        //실시간 메세지, 페이지 메세지
        if (receiveMsg.charAt(4) == 0 || receiveMsg.charAt(4) == 1) {
            if (receiveMsg.charAt(5) == 0) {//정상 처리
                logService.updateInfoLog("받은 메세지 : " + receiveMsg);
                return receiveMsg;
            }
            if (receiveMsg.charAt(5) == 'F') {//오류 발생
                logService.warningLog("오류가 발생했습니다.");
                logService.warningLog("받은 메세지 : " + receiveMsg);
                return receiveMsg;
            }

        }
        return chkSpecificCmdCode(msg, receiveMsg);
    }

    private String chkSpecificCmdCode(String msg, String receiveMsg) {
        String command = receiveMsg.substring(4, 6);
        char status = receiveMsg.charAt(6);

        if (status == 0) { // 정상 처리
            logService.updateInfoLog("받은 메세지 : " + receiveMsg);
            if (command.equals("83")) { // 맥주소 읽기
                logService.updateInfoLog("맥주소 : " + receiveMsg.substring(8, 20));
            }
            if (command.equals("31")) {
                //시간 바꿈.
                String time = receiveMsg.substring(6, 20);

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyMMddHHmmss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yy년 MM월 dd일 E요일 HH시 mm분 ss초");

                try {
                    // 문자열을 Date로 파싱
                    Date date = inputFormat.parse(time);

                    // 원하는 출력 형식으로 변환
                    String formattedTime = outputFormat.format(date);
                    logService.updateInfoLog("컨트롤러 시간은 " + formattedTime+"입니다.");
                } catch (Exception e){

                }

                return time;
            }
            if (command.equals("40")){
                // ![004002060!]
                int row = Integer.parseInt(receiveMsg.substring(6, 8));
                int column = Integer.parseInt(receiveMsg.substring(8, 10));

                if (row!=Integer.parseInt(msg.substring(6,8))||column!=Integer.parseInt(msg.substring(8,10))){
                    logService.warningLog("화면 크기 설정에 실패했습니다.");
                    logService.warningLog(row+"단, "+column+"열까지만 가능합니다.");
                }
            }
            return receiveMsg;
        } else if (status == 'F') { // 오류 발생
            errorLog(command, receiveMsg);
        } else {
            logService.warningLog("알 수 없는 상태 코드입니다. 전송 패킷을 확인해주세요.");
            logService.warningLog("받은 메세지 : " + receiveMsg);
        }

        return null;
    }

    private void errorLog(String command, String msg) {
        String errorMsg = switch (command) {
            case "20" -> "배경이미지 표출에 실패했습니다.";
            case "21" -> "화면 끄기/켜기에 실패했습니다.";
            case "22" -> "외부 신호 출력에 실패했습니다.";
            case "30" -> "시간 동기화에 실패했습니다.";
            case "31" -> "컨트롤러 시간 읽기에 실패했습니다.";
            case "41" -> "컨트롤러 리셋에 실패했습니다.";
            case "42" -> "하드 리셋에 실패했습니다.";
            case "50" -> "밝기 조절에 실패했습니다.";
            case "54" -> "표출 속도 변경에 실패했습니다.";
            case "56" -> "배경이미지 표출 목록 선택에 실패했습니다.";
            case "60" -> "페이지 메세지 개수 설정에 실패했습니다.";
            case "61" -> "페이지 메세지 삭제에 실패했습니다.";
            case "62" -> "실시간 메세지 삭제에 실패했습니다.";
            case "70" -> "화면 단색 채우기에 실패했습니다.";
            case "81" -> "펌웨어 정보 읽기에 실패했습니다.";
            case "82" -> "맥주소 설정에 실패했습니다.";
            case "85" -> "하트비트 세팅에 실패했습니다.";
            default -> "알 수 없는 명령어입니다. 전송 패킷을 확인해주세요.";
        };
        logService.warningLog(errorMsg);
        logService.warningLog("받은 메세지 : " + msg);
    }
}
