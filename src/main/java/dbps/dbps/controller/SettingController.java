package dbps.dbps.controller;


import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.*;

public class SettingController {

    @FXML
    public ProgressIndicator commonProgressIndicator;
    public ComboBox<String> BGImgSelection;
    public ChoiceBox<String> fillColor;
    public ChoiceBox<String> displayBright;

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;
    LogService logService;
    SettingService settingService;
    ResourceBundle bundle;

    @FXML
    public TextField timeBoard;
    UnderTheLineLeftService underTheLineLeftService;

    @FXML
    public void initialize() {
        bundle = ResourceManager.getInstance().getBundle();
        settingService = SettingService.getInstance(commonProgressIndicator);
        logService = LogService.getLogService();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        underTheLineLeftService = UnderTheLineLeftService.getInstance();
        underTheLineLeftService.setTimeBoard(timeBoard);

        boolean isKorean = bundle.getLocale().getLanguage().equals("ko");

        // 날짜 및 시간 포맷 설정 (언어에 따라 변경)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd (E) HH:mm:ss",
                isKorean ? Locale.KOREAN : Locale.ENGLISH);

        // 현재 시간 가져오기 및 포맷 적용
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(formatter);

        // 한글 요일 변환 (영어에서 한국어로 변환할 필요가 있을 경우)
        if (isKorean) {
            Map<String, String> dayMap = new HashMap<>();
            dayMap.put("Mon", "월");
            dayMap.put("Tue", "화");
            dayMap.put("Wed", "수");
            dayMap.put("Thu", "목");
            dayMap.put("Fri", "금");
            dayMap.put("Sat", "토");
            dayMap.put("Sun", "일");

            for (Map.Entry<String, String> entry : dayMap.entrySet()) {
                formattedTime = formattedTime.replace(entry.getKey(), entry.getValue());
            }
        }

        // 화면에 표시
        timeBoard.setText(formattedTime);

        BGImgSelection.getItems().add(bundle.getString("notUsed"));
        for (int i = 1; i < 256; i++) {
            BGImgSelection.getItems().add(String.valueOf(i));
        }

        BGImgSelection.setVisibleRowCount(10);
        BGImgSelection.setValue(bundle.getString("notUsed"));

        addItems();
    }

    private void addItems() {
        fillColor.getItems().addAll(
                bundle.getString("black"),
                bundle.getString("red"),
                bundle.getString("green"),
                bundle.getString("yellow"),
                bundle.getString("blue"),
                bundle.getString("pink"),
                bundle.getString("cyan"),
                bundle.getString("white")
        );
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

    public void synchronizeTime() {
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

    public void sendBGImgSelection() {
        String value = BGImgSelection.getValue();
        if (IS_ASCII){
            String result = "";
            if (value.equals(bundle.getString("notUsed")))
            {
                result = "000";
            }
            else result = String.format("%03d", Integer.parseInt(value));
            {
                String msg = "![0020"+result+"!]";
                if (isRS){
                    msg = "!["+convertRS485AddrASCii()+"020"+result+"!]";
                }
                asciiMsgTransceiver.sendMessages(msg,false, commonProgressIndicator);
            }
        }
        else {
            int result = 0;
            if (!value.equals(bundle.getString("notUsed"))){
                result = Integer.parseInt(value);
            }
            String msg = "10 02 00 00 02 4F "+String.format("%02X ", result)+"10 03";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 02 4F "+String.format("%02X ", result)+"10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    public void sendFillColor() throws InterruptedException {
        String value = fillColor.getValue();
        if (IS_ASCII){
            String msg = "![0070"+getColorCode(value)+"!]";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"070"+getColorCode(value)+"!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
        }
        else {
            hexMsgTransceiver.sendMessages("10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 02 45 00 10 03", commonProgressIndicator);

            String msg = "10 02 00 00 06 42 08 "+getColorCodeHex(value)+"00 00 00 10 03";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 06 42 08 "+getColorCodeHex(value)+"00 00 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    private String getColorCode(String value) {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("검은색", "0");
        colorMap.put("빨간색", "1");
        colorMap.put("초록색", "2");
        colorMap.put("노란색", "3");
        colorMap.put("파란색", "4");
        colorMap.put("분홍색", "5");
        colorMap.put("청록색", "6");
        colorMap.put("흰색", "7");
        colorMap.put("보라색", "8");
        colorMap.put("하늘색", "9");

        return colorMap.getOrDefault(value, "0"); // 기본값을 검은색("0")으로 설정
    }

    private String getColorCodeHex(String value) {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("검은색", "00 ");
        colorMap.put("빨간색", "07 ");
        colorMap.put("초록색", "38 ");
        colorMap.put("노란색", "3F ");
        colorMap.put("파란색", "C0 ");
        colorMap.put("분홍색", "C7 ");
        colorMap.put("청록색", "F8 ");
        colorMap.put("흰색", "FF "); // 기본값을 흰색으로 지정

        return colorMap.getOrDefault(value, "FF ");
    }

    public void sendDisplayBright() {
        if (IS_ASCII){
            String msg = "![0050";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"050";
            }
            switch (displayBright.getValue()){
                case "100%": msg += "99"; break;
                case "75%": msg += "75"; break;
                case "50%": msg += "50"; break;
                case "25%": msg += "25"; break;
                case "5%": msg += "05"; break;
            }
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);


        } else{
            String msg = "10 02 00 00 02 44 ";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 02 44 ";
            }
            switch (displayBright.getValue()){
                case "100%": msg += "64"; break;
                case "75%": msg += "48"; break;
                case "50%": msg += "32"; break;
                case "25%": msg += "19"; break;
                case "5%": msg += "05"; break;
            }
            msg += " 10 03";
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

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
}
