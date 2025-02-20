package dbps.dbps.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.UnderTheLineLeftService;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.TCPManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class UnderTheLineLeftController {

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;
    SerialPortManager serialPortManager;
    TCPManager tcpManager;
    UnderTheLineLeftService underTheLineLeftService;


    @FXML
    public Pane leftPane;

    @FXML
    public TextField timeBoard;

    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd (E) HH:mm:ss", Locale.ENGLISH);
        String formattedTime = now.format(formatter);

        // 요일을 한글로 변환하는 매핑
        Map<String, String> dayMap = new HashMap<>();
        dayMap.put("Mon", "월");
        dayMap.put("Tue", "화");
        dayMap.put("Wed", "수");
        dayMap.put("Thu", "목");
        dayMap.put("Fri", "금");
        dayMap.put("Sat", "토");
        dayMap.put("Sun", "일");

        // 영어 요일을 한글로 변환
        for (Map.Entry<String, String> entry : dayMap.entrySet()) {
            formattedTime = formattedTime.replace(entry.getKey(), entry.getValue());
        }
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
                msg = "![" + convertRS485AddrASCii() + "030";
            }

            // ✅ 현재 날짜 가져오기
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss", Locale.KOREAN);
            String time = formatter.format(calendar.getTime());

            // ✅ 요일 숫자로 변환
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            char dayInt = switch (dayOfWeek) {
                case Calendar.MONDAY -> '1';
                case Calendar.TUESDAY -> '2';
                case Calendar.WEDNESDAY -> '3';
                case Calendar.THURSDAY -> '4';
                case Calendar.FRIDAY -> '5';
                case Calendar.SATURDAY -> '6';
                case Calendar.SUNDAY -> '0';
                default -> 'X'; // 에러 방지
            };

            msg += time.substring(0, 6) + dayInt + time.substring(6);
            msg += "!]";

            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
            return;
        }

        String msg = "10 02 00 00 08 47 ";
        if (isRS) {
            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 08 47 ";
        }

        // ✅ 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yy MM dd HH mm ss", Locale.KOREAN);
        String time = formatter.format(calendar.getTime());

        // ✅ 요일 변환 (2자리)
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayStr = switch (dayOfWeek) {
            case Calendar.MONDAY -> "01 ";
            case Calendar.TUESDAY -> "02 ";
            case Calendar.WEDNESDAY -> "03 ";
            case Calendar.THURSDAY -> "04 ";
            case Calendar.FRIDAY -> "05 ";
            case Calendar.SATURDAY -> "06 ";
            case Calendar.SUNDAY -> "00 ";
            default -> "99"; // 에러 방지
        };

        msg += time.substring(0,9) + dayStr + time.substring(9) + " 10 03";

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
