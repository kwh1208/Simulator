package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static dbps.dbps.Constants.IS_ASCII;

public class SettingController {

    HexMsgTransceiver hexMsgTransceiver = HexMsgTransceiver.getInstance();
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance(); 
    @FXML
    public ChoiceBox<String> displayBright;

    @FXML
    public ChoiceBox<String> pageMsgType;

    @FXML
    public void initialize(){
    }


    public void communicationSettingClicked(MouseEvent mouseEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/communicationSetting.fxml"));
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("통신 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    public void sendDisplayBright() {
//        ![005099!]
        if (IS_ASCII){
            String msg = "![0050";
            switch (displayBright.getValue()){
                case "100%(기본)": msg += "99"; break;
                case "75%": msg += "75"; break;
                case "50%": msg += "50"; break;
                case "25%": msg += "25"; break;
                case "5%": msg += "05"; break;
            }
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg);
            

        } else{
            String msg = "10 02 00 00 02 44 ";
            switch (displayBright.getValue()){
                case "100%(기본)": msg += "64"; break;
                case "75%": msg += "48"; break;
                case "50%": msg += "32"; break;
                case "25%": msg += "19"; break;
                case "5%": msg += "05"; break;
            }
            msg += " 10 03";
            hexMsgTransceiver.sendMessages(msg);
        }
    }

    public void sendPageMsgType() {
        if (IS_ASCII){
            //![0062N!] : 동시, ![0062Y!] : 개별
            String msg = "![0062";
            if (pageMsgType.getValue().contains("동시")){
                msg += "N";
            } else{
                msg += "Y";
            }
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg);
            

        } else {
            String msg;
            if (pageMsgType.getValue().contains("동시")){
                msg = "21 5B 30 30 36 32 4E 21 5D";
            }else{
                msg = "21 5B 30 30 36 32 59 21 5D";
            }
            hexMsgTransceiver.sendMessages(msg);
        }
    }
}
