package dbps.dbps.controller;

import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;


import static dbps.dbps.Constants.IS_ASCII;

public class BasicSettingController {
    @FXML
    public Pane basicPane;
    public ChoiceBox<String> programLanguage;


    MainService mainService;
    ConfigService configService;

    @FXML
    public ChoiceBox<String> protocolFormat;


    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
        //초기설정에 따라서 메세지 탭 변경
        mainService = MainService.getInstance();
        if (IS_ASCII){
            protocolFormat.setValue("아스키 프로토콜");
        } else {
            protocolFormat.setValue("헥사 프로토콜");
        }



        //드롭다운 감지해서 탭 변경
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

        programLanguage.setValue(configService.getProperty("PROGRAM_LANGUAGE"));

        programLanguage.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("한국어")){
                programLanguage.setValue("한국어");
                configService.setProperty("PROGRAM_LANGUAGE", "한국어");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("알림");
                alert.setHeaderText(null);
                alert.setContentText("언어를 변경하기 위해서 프로그램을 다시 시작해주세요.");
                alert.showAndWait();
            } else if (newValue.equals("English")) {
                programLanguage.setValue("English");
                configService.setProperty("PROGRAM_LANGUAGE", "english");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("alert");
                alert.setHeaderText(null);
                alert.setContentText("Please restart the program to apply the language change.");
                alert.showAndWait();
            }
        });

    }
}
