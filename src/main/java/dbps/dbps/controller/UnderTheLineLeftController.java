package dbps.dbps.controller;


import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;

import static dbps.dbps.Constants.isAscii;

public class UnderTheLineLeftController {

    HexMsgTransceiver hexMsgTransceiver = HexMsgTransceiver.getInstance();
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

    @FXML
    public Pane leftPane;

    @FXML
    public TextField timeBoard;

    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());
    }
    @FXML
    public void sendDisplayOn() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![00211!]");
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 41 01 10 03");
    }
    @FXML
    public void sendDisplayOff() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![00210!]");
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 41 00 10 03");


    }


    public void getControllerTime() {
        if (isAscii){
            String time = asciiMsgTransceiver.sendMessages("![0031!]");
            timeBoard.setText(time);
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 01 66 10 03");

        //년 = splitMsg[6] 월 = splitMsg[7] 일 = splitMsg[8] 요일 = splitMsg[9] 시간 = splitMsg[10] 분 = splitMsg[11] 초 = splitMsg[12]


    }

    public void synchronizeTime() {
        if (isAscii){
            String msg = "![0030";
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddEHHmmss");
            String time = formatter.format(System.currentTimeMillis());
            char day = time.charAt(6);
            char dayInt = switch (day) {
                case '월' -> '1';
                case '화' -> '2';
                case '수' -> '3';
                case '목' -> '4';
                case '금' -> '5';
                case '토' -> '6';
                case '일' -> '0';
                default -> day;
            };
            msg+=time.replace(day, dayInt);
            msg += "!]";

            asciiMsgTransceiver.sendMessages(msg);
            
            return;
        }
        String msg = "10 02 00 00 08 47 ";
        SimpleDateFormat formatter = new SimpleDateFormat("yy MM dd E HH mm ss ");
        String time = formatter.format(System.currentTimeMillis());
        String day = String.valueOf(time.charAt(9));
        String dayStr = switch (day) {
            case "월" -> "01";
            case "화" -> "02";
            case "수" -> "03";
            case "목" -> "04";
            case "금" -> "05";
            case "토" -> "06";
            case "일" -> "00";
            default -> "에러";
        };
        msg+=time.replace(day, dayStr);
        msg += "10 03";

        hexMsgTransceiver.sendMessages(msg);

    }

    public void resetController() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![0041!]");
            
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 89 00 10 03");
    }

    public void hardReset() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![0042!]");
            
            return;
        }
            //비트수, 세로, 가로크기 가져와서 같이 보내줘야함.
//            hexMsgTransceiver.sendMessages("10 02 00 00 04 4A 03 04 06 10 03");
    }
}
