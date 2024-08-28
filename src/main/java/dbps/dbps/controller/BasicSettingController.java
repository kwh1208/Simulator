package dbps.dbps.controller;

import dbps.dbps.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class BasicSettingController {
    MainService mainService;
    @FXML
    public ChoiceBox<String> protocolFormat;
    @FXML
    public void initialize() {
        mainService = MainService.getInstance();
        protocolFormat.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("아스키 프로토콜")) {
                mainService.showASCiiMsgTab();
            } else if (newValue.equals("헥사 프로토콜")) {
                mainService.showHEXMsgTab();
            }
        });
    }
}
