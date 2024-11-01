package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import static dbps.dbps.Constants.*;

public class UnderTheLineRightController {
    @FXML
    public ComboBox<String> BGImgSelection;

    @FXML
    public ChoiceBox<String> fillColor;

    @FXML
    public VBox rightVbox;

    @FXML
    public ComboBox<String> relayBox1;

    @FXML
    public ComboBox<String> relayBox2;

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;


    @FXML
    public void initialize(){
        BGImgSelection.getItems().add("사용안함");
        for (int i = 1; i < 256; i++) {
            BGImgSelection.getItems().add(String.valueOf(i));
        }

        BGImgSelection.setVisibleRowCount(10);

        rightVbox.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineRight.css").toExternalForm());

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    public void sendRelaySignal() {
        String msg = "![0022";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"022";
        }
        msg+=makeRelayMsg(relayBox1.getValue());
        msg+=makeRelayMsg(relayBox2.getValue());
        msg+="!]";

        asciiMsgTransceiver.sendMessages(msg);
    }

    private String makeRelayMsg(String value) {

        if (value.equals("None")) {
            return "61696";
        } else if (value.equals("On")) {
            return "61440";
        } else if (value.equals("Off")) {
            return "00000";
        } else
            return String.format("%05d", Integer.parseInt(value));
    }

    public void sendBGImgSelection(MouseEvent mouseEvent) {
        String value = BGImgSelection.getValue();
        if (IS_ASCII){
            String result = "";
            if (value.equals("사용안함"))
            {
                result = "000";
            }
            else result = String.format("%03d", Integer.parseInt(value));
            {
                String msg = "![0020"+result+"!]";
                if (isRS){
                    msg = "!["+convertRS485AddrASCii()+"020"+result+"!]";
                }
                asciiMsgTransceiver.sendMessages(msg);
            }
        }
        else {
            int result = 0;
            if (!value.equals("사용안함")){
                result = Integer.parseInt(value);
            }
            String msg = "10 02 00 00 02 4F "+String.format("%02X ", result)+"10 03";
            if (isRS){
                msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 02 4F "+String.format("%02X ", result)+"10 03";
            }
            hexMsgTransceiver.sendMessages(msg);
        }
    }

    public void sendFillColor() throws InterruptedException {
        String value = fillColor.getValue();
        if (IS_ASCII){
            String result = "";
            if (value.equals("검은색")){
                result = "0";
            }else if (value.equals("빨간색")){
                result = "1";
            } else if (value.equals("초록색")){
                result = "2";
            } else if (value.equals("노란색")){
                result = "3";
            } else if (value.equals("파란색")){
                result = "4";
            } else if (value.equals("분홍색")){
                result = "5";
            } else if (value.equals("청록색")){
                result = "6";
            } else result = "7";
            String msg = "![0070"+result+"!]";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"070"+result+"!]";
            }
            asciiMsgTransceiver.sendMessages(msg);
        }
        else {
            String msg = "10 02 00 00 02 45 00 10 03";
            if (isRS){
                msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 02 45 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg);

            Thread.sleep(500);

            String result = "";
            if (value.equals("검은색")){
                result = "00 ";
            }else if (value.equals("빨간색")){
                result = "07 ";
            } else if (value.equals("초록색")){
                result = "38 ";
            } else if (value.equals("노란색")){
                result = "3F ";
            } else if (value.equals("파란색")){
                result = "C0 ";
            } else if (value.equals("분홍색")){
                result = "C7 ";
            } else if (value.equals("청록색")){
                result = "F8 ";
            } else result = "FF ";
            msg = "10 02 00 00 06 42 08 "+result+"00 00 00 10 03";
            if (isRS){
                msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 06 42 08 "+result+"00 00 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg);
        }
    }
}
