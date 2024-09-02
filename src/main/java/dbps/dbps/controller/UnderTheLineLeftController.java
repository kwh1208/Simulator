package dbps.dbps.controller;

import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.HexMsgService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;

import static dbps.dbps.Constants.isAscii;

public class UnderTheLineLeftController {

    ASCiiMsgService ascMsgService = ASCiiMsgService.getInstance();
    HexMsgService hexMsgService = HexMsgService.getInstance();

    @FXML
    public Pane leftPane;

    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());
    }
    @FXML
    public void sendDisplayOn() {
        if (isAscii){
            String receiveMsg = ascMsgService.sendMessages("![00211!]");
            if (receiveMsg.contains("F")){
                System.out.println("화면 켜기 실패");
            }
        } else{
            String receiveMsg = hexMsgService.sendMessages("10 02 00 00 02 41 01 10 03");
            String[] splitMsg = receiveMsg.split(" ");
            if (!splitMsg[6].equals("00")){
                System.out.println("화면 켜기 실패");
            }
        }
    }
    @FXML
    public void sendDisplayOff() {
        if (isAscii){
            String receiveMsg = ascMsgService.sendMessages("![00210!]");
            if (receiveMsg.contains("F")){
                System.out.println("화면 끄기 실패");
            }
        } else{
            String receiveMsg = hexMsgService.sendMessages("10 02 00 00 02 41 00 10 03");
            String[] splitMsg = receiveMsg.split(" ");
            if (!splitMsg[6].equals("00")){
                System.out.println("화면 끄기 실패");
            }
        }
    }


    public void getControllerTime() {
        if (isAscii){
            String receiveMsg = ascMsgService.sendMessages("![0031!]");
            //년 = receiveMsg.substring(5, 7) 월 = receiveMsg.subString(7, 9) 일 = receiveMsg.subString(9, 11) 요일 = receiveMsg.subString(11, 12) 시간 = receiveMsg.subString(12, 14) 분 = receiveMsg.subString(14, 16) 초 = receiveMsg.subString(16, 18)
        } else {
            String receiveMsg = hexMsgService.sendMessages("10 02 00 00 01 66 10 03");
            String[] splitMsg = receiveMsg.split(" ");
            //년 = splitMsg[6] 월 = splitMsg[7] 일 = splitMsg[8] 요일 = splitMsg[9] 시간 = splitMsg[10] 분 = splitMsg[11] 초 = splitMsg[12]
        }
    }

    public void synchronizeTime() {
//        ![00302409021172312!]
        if (isAscii){
            String msg = "![0030";
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMdddHHmmss");
            msg+=formatter.format(System.currentTimeMillis());
            msg += "!]";

            String receiveMsg = ascMsgService.sendMessages(msg);
            if (receiveMsg.contains("F")){
                System.out.println("시간 동기화 실패");
            }
        } else {
            String msg = "10 02 00 00 08 47 ";
            SimpleDateFormat formatter = new SimpleDateFormat("yy MM dd d HH mm ss ");
            msg+=formatter.format(System.currentTimeMillis());
            msg += "10 03";

            String receiveMsg = hexMsgService.sendMessages(msg);
            String[] splitMsg = receiveMsg.split(" ");
            if (!splitMsg[6].equals("00")){
                System.out.println("시간 동기화 실패");
            }
        }
    }

    public void resetController() {
        if (isAscii){
            String receiveMsg = ascMsgService.sendMessages("![0041!]");
            if (receiveMsg.contains("F")){
                System.out.println("컨트롤러 리셋 실패");
            }
        } else {
            String receiveMsg = hexMsgService.sendMessages("10 02 00 00 02 89 00 10 03");
            String[] splitMsg = receiveMsg.split(" ");
            if (!splitMsg[6].equals("00")){
                System.out.println("컨트롤러 리셋 실패");
            }
        }
    }

    public void hardReset() {
        if (isAscii){
            String receiveMsg = ascMsgService.sendMessages("![0042!]");
            if (receiveMsg.contains("F")){
                System.out.println("하드 리셋 실패");
            }
        } else {
            //비트수, 세로, 가로크기 가져와서 같이 보내줘야함.
//            String receiveMsg = hexMsgService.sendMessages("10 02 00 00 04 4A 03 04 06 10 03");
//            String[] splitMsg = receiveMsg.split(" ");
//            if (!splitMsg[6].equals("00")){
//                System.out.println("하드 리셋 실패");
//            }
        }
    }
}
