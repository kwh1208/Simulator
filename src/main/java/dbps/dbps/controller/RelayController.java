package dbps.dbps.controller;

import dbps.dbps.Constants;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static dbps.dbps.Constants.convertRS485AddrASCii;
import static dbps.dbps.Constants.isRS;

public class RelayController {
    @FXML
    public ComboBox<String> relayBox1;

    @FXML
    public ComboBox<String> relayBox2;

    public AnchorPane relayAP;
    public ProgressIndicator progressIndicator;

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;

    @FXML
    public void initialize(){
        relayAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/relay.css").toExternalForm());

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();

        relayAP.setOnKeyPressed(new Constants.EscapeKeyEventHandler());
    }


    public void sendRelaySignal() {
        String msg = "![0022";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"022";
        }
        msg+=makeRelayMsg(relayBox1.getValue());
        msg+=makeRelayMsg(relayBox2.getValue());
        msg+="!]";

        asciiMsgTransceiver.sendMessages(msg,false, progressIndicator);
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

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
