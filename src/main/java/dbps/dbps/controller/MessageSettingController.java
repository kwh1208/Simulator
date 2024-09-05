package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

import static dbps.dbps.Constants.isAscii;

public class MessageSettingController {

    HexMsgTransceiver hexMsgTransceiver = HexMsgTransceiver.getInstance();
    
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

    @FXML
    public ChoiceBox<String> msgInitialize;

    @FXML
    public ChoiceBox<String> pageMsgCnt;

    @FXML
    public Pane msPane;

    @FXML
    public void initialize() {
        msPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/messageSetting.css").toExternalForm());

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedCount = Integer.parseInt(newValue.replace("개", ""));
            msgInitialize.getItems().clear();
            msgInitialize.getItems().add("전체");

            for (int i = 1; i <= selectedCount; i++) {
                msgInitialize.getItems().add(i+"개");
            }

            msgInitialize.setValue("전체");
        });
    }

    public void sendMsgInitialize() {
        if (isAscii){
            // ![006103!]
            String msg = "![0061";
            if (msgInitialize.getValue().equals("전체")){
                msg += "99";
            }
            else{
                msg += String.format("%02d", Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", ""))-1);
            }
            msg += "!]";

            asciiMsgTransceiver.sendMessages(msg);

        } else {
            //10 02 00 00 02 4B 00 10 03
            String msg = "10 02 00 00 02 4B ";
            if (msgInitialize.getValue().equals("전체")){
                msg += "80";
            }
            else{
                msg += String.format("%02d", Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", ""))-1);
            }
            msg += " 10 03";

            hexMsgTransceiver.sendMessages(msg);
        }
    }

    public void sendPageCnt() {
        if (isAscii){ //아스키 코드라면
//            ![006003!]
            String msg = "![0060";
            msg += String.format("%02d", Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", "")));
            msg += "!]";
            String receiveMsg = asciiMsgTransceiver.sendMessages(msg);



        } else {
            String msg = "10 02 00 00 02 4C ";
            msg += Integer.toHexString(Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", ""))).toUpperCase();
            msg += " 10 03";

            hexMsgTransceiver.sendMessages(msg);
        }
    }
}
