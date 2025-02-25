package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.BoardInfoReadService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BoardInfoReadController {
    public TextField brightness;
    public TextField horizontal;
    public TextField vertical;
    public TextField array;
    public TextField firmware;
    public TextField cpu;
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
    }


    public void readBrightness(MouseEvent mouseEvent) {

    }

    public void readDisplaySize(MouseEvent mouseEvent) {
    }

    public void readCPU(MouseEvent mouseEvent) {
    }
}
