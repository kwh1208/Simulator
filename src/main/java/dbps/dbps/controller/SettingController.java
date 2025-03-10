package dbps.dbps.controller;


import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;

import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;

public class SettingController {

    @FXML
    public ProgressIndicator commonProgressIndicator;

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;
    LogService logService;
    SettingService settingService;
    ResourceBundle bundle;

    @FXML
    public ChoiceBox<String> displayBright;

    @FXML
    public ChoiceBox<String> pageMsgType;

    @FXML
    public void initialize(){
        bundle=ResourceManager.getInstance().getBundle();
        settingService = SettingService.getInstance(commonProgressIndicator);
        logService = LogService.getLogService();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        pageMsgType.getItems().add(bundle.getString("individualEffectDisplay"));
        pageMsgType.getItems().add(bundle.getString("simultaneousEffectDisplay"));

        pageMsgType.setValue(bundle.getString("simultaneousEffectDisplay"));
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

    public void sendPageMsgType() {
            //![0062N!] : 동시, ![0062Y!] : 개별
            String msg = "![0062";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"062";
            }
            if (pageMsgType.getValue().equals(bundle.getString("simultaneousEffectDisplay"))){
                msg += "N";
            } else{
                msg += "Y";
            }
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
    }
}
