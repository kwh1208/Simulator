package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class MessageSettingController {

    HexMsgTransceiver hexMsgTransceiver = HexMsgTransceiver.getInstance();
    
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
    ResourceBundle bundle;
    ConfigService configService;

    @FXML
    public ChoiceBox<String> msgInitialize;

    @FXML
    public ChoiceBox<String> pageMsgCnt;

    @FXML
    public Pane msPane;

    private ChoiceBox<String> pageMsgChoiceBox;

    @FXML
    public void initialize() {
        msPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/messageSetting.css").toExternalForm());
        bundle= ResourceManager.getInstance().getBundle();

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedCount = Integer.parseInt(newValue.replace("개", ""));
            msgInitialize.getItems().clear();
            msgInitialize.getItems().add(bundle.getString("All"));

            for (int i = 1; i <= selectedCount; i++) {
                msgInitialize.getItems().add("page "+i);
            }

            msgInitialize.setValue(bundle.getString("All"));
        });
        msgInitialize.getItems().add(bundle.getString("All"));
        msgInitialize.setValue(bundle.getString("All"));
        configService=ConfigService.getInstance();
    }

    public void sendMsgInitialize() {
        if (IS_ASCII){
            String msg = "![0061";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"061";
            }
            if (msgInitialize.getValue().equals(bundle.getString("All"))){
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
            if (msgInitialize.getValue().equals(bundle.getString("All"))){
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

        PageMsgCnt= Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", ""));
        configService.setProperty("pageMsgCnt", String.valueOf(PageMsgCnt));

        HexMsgService.getInstance().setUI(PageMsgCnt);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
