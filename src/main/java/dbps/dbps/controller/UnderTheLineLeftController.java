package dbps.dbps.controller;


import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.TCPManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static dbps.dbps.Constants.*;

public class UnderTheLineLeftController {

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;
    SerialPortManager serialPortManager;
    TCPManager tcpManager;

    @FXML
    public Pane leftPane;

    @FXML
    public TextField timeBoard;

    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd (E) HH:mm:ss");
        String formattedTime = now.format(formatter);

        timeBoard.setText(formattedTime);
        serialPortManager = SerialPortManager.getManager();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        tcpManager = TCPManager.getManager();
    }
    @FXML
    public void sendDisplayOn() {
        if (IS_ASCII){
            asciiMsgTransceiver.sendMessages("![00211!]");
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 41 01 10 03");
    }
    @FXML
    public void sendDisplayOff() {
        if (IS_ASCII){
            asciiMsgTransceiver.sendMessages("![00210!]");
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 02 41 00 10 03");


    }


    public void getControllerTime() {
        if (IS_ASCII){
            String time = asciiMsgTransceiver.sendMessages("![0031!]");
            timeBoard.setText(time);
            return;
        }
        hexMsgTransceiver.sendMessages("10 02 00 00 01 66 10 03");

        //년 = splitMsg[6] 월 = splitMsg[7] 일 = splitMsg[8] 요일 = splitMsg[9] 시간 = splitMsg[10] 분 = splitMsg[11] 초 = splitMsg[12]


    }

    public void synchronizeTime() {
        if (IS_ASCII){
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

    public void resetController() throws InterruptedException {
        if (IS_ASCII){
            asciiMsgTransceiver.sendMessages("![0041!]");
        }
        else hexMsgTransceiver.sendMessages("10 02 00 00 02 89 00 10 03");

        Thread.sleep(4500);
        if (CONNECT_TYPE.equals("serial")) {
            serialPortManager.closePort(OPEN_PORT_NAME);
            serialPortManager.openPort(OPEN_PORT_NAME, SERIAL_BAUDRATE);
        }
        if (CONNECT_TYPE.equals("clientTCP")) {
            tcpManager.disconnect();
            tcpManager.connect(CLIENT_TCP_IP, CLIENT_TCP_PORT);
        }
    }

    public void hardReset() throws InterruptedException {
        if (IS_ASCII){
            asciiMsgTransceiver.sendMessages("![0042!]");
        }
        //비트수, 세로, 가로크기 가져와서 같이 보내줘야함.
        else {
            String msg = "10 02 00 00 04 4A 0"+BITS_PER_PIXEL+" 0"+SIZE_ROW+" 0"+SIZE_COLUMN+ " 10 03";
            hexMsgTransceiver.sendMessages(msg);
        }
        Thread.sleep(4500);
        if (CONNECT_TYPE.equals("serial")) {
            serialPortManager.closePort(OPEN_PORT_NAME);
            serialPortManager.openPort(OPEN_PORT_NAME, SERIAL_BAUDRATE);
        }
        if (CONNECT_TYPE.equals("clientTCP")) {
            tcpManager.disconnect();
            tcpManager.connect(CLIENT_TCP_IP, CLIENT_TCP_PORT);
        }
    }
}
