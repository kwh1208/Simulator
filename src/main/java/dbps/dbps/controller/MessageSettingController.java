package dbps.dbps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import org.json.JSONObject;

import java.awt.*;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class MessageSettingController {

    HexMsgTransceiver hexMsgTransceiver = HexMsgTransceiver.getInstance();
    
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
    MQTTManager mqttManager;

    @FXML
    public ChoiceBox<String> msgInitialize;

    @FXML
    public ChoiceBox<String> pageMsgCnt;

    @FXML
    public Pane msPane;

    @FXML
    public void initialize() {
        mqttManager=MQTTManager.getInstance();
        msPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/messageSetting.css").toExternalForm());

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedCount = Integer.parseInt(newValue.replace("개", ""));
            msgInitialize.getItems().clear();
            msgInitialize.getItems().add("전체");

            for (int i = 1; i <= selectedCount; i++) {
                msgInitialize.getItems().add("page "+i);
            }

            msgInitialize.setValue("전체");
        });
    }

    public void sendMsgInitialize() {
        if (ROAD){
            long msgId = System.currentTimeMillis();

            JSONObject moid = new JSONObject();
            moid.put("2.RTE058.3.5", Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", "")));
            moid.put("2.RTE058.3.6", 1);

            JSONObject setRequest = new JSONObject();
            setRequest.put("MSG_TYPE", "SET");
            setRequest.put("MSG_VER", 20241028);
            setRequest.put("MSG_ID", msgId);
            setRequest.put("MOID", moid);

            Task<String> stringTask = mqttManager.sendRoadMsg(setRequest.toString());

            new Thread(stringTask).start();

            return;
        }


        if (IS_ASCII){
            String msg = "![0061";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"061";
            }
            if (msgInitialize.getValue().equals("전체")){
                msg += "99";
            }
            else{
                msg += String.format("%02d", Integer.parseInt(msgInitialize.getValue().replaceAll("[^0-9]", ""))-1);
            }
            msg += "!]";

            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);

        } else {
            String msg = "10 02 00 00 02 4B ";
            if (isRS){
                msg = "10 02 "+String.format("%02X", RS485_ADDR_NUM)+" 00 02 4B ";
            }
            if (msgInitialize.getValue().equals("전체")){
                msg += "80";
            }
            else{
                msg += String.format("%02d", Integer.parseInt(msgInitialize.getValue().replaceAll("[^0-9]", ""))-1);
            }
            msg += " 10 03";

            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    public void sendPageCnt() {
       if (ROAD){
           long msgId = System.currentTimeMillis();

           JSONObject moid = new JSONObject();
           moid.put("2.RTE058.3.5", Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", "")));
           moid.put("2.RTE058.3.6", 0);

           JSONObject setRequest = new JSONObject();
           setRequest.put("MSG_TYPE", "SET");
           setRequest.put("MSG_VER", 20241028);
           setRequest.put("MSG_ID", msgId);
           setRequest.put("MOID", moid);

           Task<String> stringTask = mqttManager.sendRoadMsg(setRequest.toString());

           new Thread(stringTask).start();

           return;
       }

        if (IS_ASCII){ //아스키 코드라면
//            ![006003!]
            String msg = "![0060";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"060";
            }
            msg += String.format("%02d", Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", "")));
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);

        } else {
            String msg = "10 02 00 00 02 4C ";
            if (isRS){
                msg = "10 02 "+String.format("%02X", RS485_ADDR_NUM)+" 00 02 4C ";
            }
            msg += Integer.toHexString(Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", ""))).toUpperCase();
            msg += " 10 03";

            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }
}
