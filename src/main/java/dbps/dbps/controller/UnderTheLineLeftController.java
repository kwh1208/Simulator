package dbps.dbps.controller;

import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgService;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;

import static dbps.dbps.Constants.isAscii;

public class UnderTheLineLeftController {

    ASCiiMsgService ascMsgService = ASCiiMsgService.getInstance();
    HexMsgTransceiver hexMsgTransceiver = HexMsgTransceiver.getInstance();
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

    @FXML
    public Pane leftPane;

    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());
    }
    @FXML
    public void sendDisplayOn() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![00211!]");
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 41 01 10 03");
    }
    @FXML
    public void sendDisplayOff() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![00210!]");
            
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 41 00 10 03");


    }


    public void getControllerTime() {
        if (isAscii){
            asciiMsgTransceiver.sendMessages("![0031!]");
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 01 66 10 03");

        //년 = splitMsg[6] 월 = splitMsg[7] 일 = splitMsg[8] 요일 = splitMsg[9] 시간 = splitMsg[10] 분 = splitMsg[11] 초 = splitMsg[12]

    }

    public void synchronizeTime() {
//        ![00302409021172312!]
        if (isAscii){
            String msg = "![0030";
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMdddHHmmss");
            msg+=formatter.format(System.currentTimeMillis());
            msg += "!]";

            asciiMsgTransceiver.sendMessages(msg);
            
            return;
        }
        String msg = "10 02 00 00 08 47 ";
        SimpleDateFormat formatter = new SimpleDateFormat("yy MM dd d HH mm ss ");
        msg+=formatter.format(System.currentTimeMillis());
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
