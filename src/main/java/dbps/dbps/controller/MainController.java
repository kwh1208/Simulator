package dbps.dbps.controller;


import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

    public void handleHelpAction() {
        String url = "http://www.dabitsol.com/files/DabitSimulator_UserManual_v9.0.pdf"; // 여기에 원하는 URL 입력

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported. Cannot open the URL.");
        }
    }
}
