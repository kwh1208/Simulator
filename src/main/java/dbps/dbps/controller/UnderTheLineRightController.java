package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
    }

    public void sendFillColor(MouseEvent mouseEvent) {

    }
}
