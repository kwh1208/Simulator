package dbps.dbps.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.UnderTheLineLeftService;
import dbps.dbps.service.connectManager.MQTTManager;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.TCPManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class UnderTheLineLeftController {

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;
    SerialPortManager serialPortManager;
    TCPManager tcpManager;
    UnderTheLineLeftService underTheLineLeftService;
    MQTTManager mqttManager;

    @FXML
    public Pane leftPane;

    @FXML
    public TextField timeBoard;

    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());

        mqttManager = MQTTManager.getInstance();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd (E) HH:mm:ss");
        String formattedTime = now.format(formatter);

        timeBoard.setText(formattedTime);
        serialPortManager = SerialPortManager.getManager();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        tcpManager = TCPManager.getManager();
        underTheLineLeftService = UnderTheLineLeftService.getInstance();
        underTheLineLeftService.setTimeBoard(timeBoard);

    }

    @FXML
    public void sendDisplayOn() {
        if (IS_ASCII) {
            String msg = "![00211!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "0211!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
            return;
        }
        String msg = "10 02 00 00 02 41 01 10 03";
        if (isRS) {
            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 02 41 01 10 03";
        }
        hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
    }

    @FXML
    public void sendDisplayOff() {
        if (IS_ASCII) {
            String msg = "![00210!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "0210!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
            return;
        }
        String msg = "10 02 00 00 02 41 00 10 03";
        if (isRS) {
            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 02 41 00 10 03";
        }
        hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
    }


    public void getControllerTime(){
        if (IS_ASCII) {
            String msg;
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "031!]";
            } else {
                msg = "![0031!]";
            }
            CompletableFuture.supplyAsync(() -> String.valueOf(asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator)));

            return;
        }
        String msg = "10 02 00 00 01 66 10 03";
        if (isRS) {
            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 01 66 10 03";
        }
        hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
    }

    public void synchronizeTime() throws JsonProcessingException {
        if (IS_ASCII) {
            String msg = "![0030";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "030!]";
            }
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
            msg += time.replace(day, dayInt);
            msg += "!]";

            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);

            return;
        }
        String msg = "10 02 00 00 08 47 ";
        if (isRS) {
            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 08 47 ";
        }
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
        msg += time.replace(day, dayStr);
        msg += "10 03";

        hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);

    }

    public void resetController() throws InterruptedException {
        if (IS_ASCII) {
            String msg = "![0041!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "041!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
        } else {
            String msg = "10 02 00 00 02 89 00 10 03";
            if (isRS) {
                msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 02 89 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    /**
     * 누리콘
     */

    public void hardReset() {
        if (IS_ASCII) {
            String msg = "![0042!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "042!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
        }
        //비트수, 세로, 가로크기 가져와서 같이 보내줘야함.
        else {
            String msg = "10 02 00 00 04 4A 0" + BITS_PER_PIXEL + " 0" + SIZE_ROW + " 0" + SIZE_COLUMN + " 10 03";
            if (isRS) {
                msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 04 4A 0" + BITS_PER_PIXEL + " 0" + SIZE_ROW + " 0" + SIZE_COLUMN + " 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }
}
