package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class UnderTheLineRightController {
    @FXML
    public ComboBox<String> BGImgSelection;

    @FXML
    public VBox rightVbox;

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;
    ResourceBundle bundle;


    @FXML
    public void initialize(){
        bundle=ResourceManager.getInstance().getBundle();



        rightVbox.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineRight.css").toExternalForm());

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }



}
