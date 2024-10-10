package dbps.dbps.controller;


import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import static dbps.dbps.Constants.IS_ASCII;

public class MainController {
    MainService mainService;
    ConfigService configService;

    @FXML
    public TabPane mainTab;
    @FXML
    public Tab messageTab;

    @FXML
    public Tab setting;

    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
        mainService = MainService.getInstance();
        mainService.setMessageTab(messageTab);
        if (IS_ASCII){
            mainService.showASCiiMsgTab();
        }else {
            mainService.showHEXMsgTab();
        }
    }


}
