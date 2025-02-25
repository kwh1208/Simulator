package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.BoardInfoReadService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static dbps.dbps.Constants.convertRS485AddrASCii;
import static dbps.dbps.Constants.isRS;

public class BoardInfoReadController {
    public TextField brightness;
    public TextField horizontal;
    public TextField vertical;
    public TextField array;
    public TextField firmware;
    public TextField cpu;
    public AnchorPane brAp;
    AsciiMsgTransceiver asciiMsgTransceiver;
    BoardInfoReadService boardInfoReadService;
    @FXML
    public void initialize(){
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        boardInfoReadService = BoardInfoReadService.getInstance();

        boardInfoReadService.setBrightness(brightness);
        boardInfoReadService.setHorizontal(horizontal);
        boardInfoReadService.setVertical(vertical);
        boardInfoReadService.setArray(array);
        boardInfoReadService.setFirmware(firmware);
        boardInfoReadService.setCpu(cpu);

        brAp.getStylesheets().add(getClass().getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());
    }


    public void readBrightness() {
        String sendMsg;
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"051!]";
        }
        else {
            sendMsg = "![0051!]";
        }
        asciiMsgTransceiver.sendMessages(sendMsg, false, null);
    }

    public void readDisplaySize() {
        String sendMsg;
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"043!]";
        }
        else {
            sendMsg = "![0043!]";
        }
        asciiMsgTransceiver.sendMessages(sendMsg, false, null);
    }

    public void readCPU() {
        String sendMsg;
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"097!]";
        }
        else {
            sendMsg = "![0097!]";
        }
        asciiMsgTransceiver.sendMessages(sendMsg, false, null);
    }


    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
