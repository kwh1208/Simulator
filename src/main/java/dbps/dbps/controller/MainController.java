package dbps.dbps.controller;


import dbps.dbps.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainController {
    MainService mainService;

    @FXML
    public TabPane mainTab;
    @FXML
    public Tab messageTab;

    @FXML
    public Tab setting;

    @FXML
    public void initialize() {
        mainService = MainService.getInstance();
        mainService.setMessageTab(messageTab);
        mainService.showASCiiMsgTab();
    }


}
