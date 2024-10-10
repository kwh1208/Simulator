package dbps.dbps.controller;


import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import static dbps.dbps.Constants.IS_ASCII;

public class BasicSettingController {
    @FXML
    public Pane basicPane;

    MainService mainService;
    ConfigService configService;
    @FXML
    public ChoiceBox<String> protocolFormat;
    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
        mainService = MainService.getInstance();
        if (IS_ASCII){
            protocolFormat.setValue("아스키 프로토콜");
        } else {
            protocolFormat.setValue("헥사 프로토콜");
        }
        protocolFormat.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("아스키 프로토콜")) {
                mainService.showASCiiMsgTab();
                IS_ASCII = true;
                configService.setProperty("IS_ASCII", "true");
            } else if (newValue.equals("헥사 프로토콜")) {
                mainService.showHEXMsgTab();
                IS_ASCII = false;
                configService.setProperty("IS_ASCII", "false");
            }
        });

    }
}
