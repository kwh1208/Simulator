package dbps.dbps.controller;


import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
        MainService.setMessageTab(messageTab);
        MainService.setSettingTab(setting);
        mainTab.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == messageTab) {
                mainService.showHEXMsgTab();
            } else if (newTab == setting) {
                mainService.changeSetTab();
            }
        });

        // 초기 탭(예, 메시지 탭)을 미리 로드
        mainService.showHEXMsgTab();
    }
}
