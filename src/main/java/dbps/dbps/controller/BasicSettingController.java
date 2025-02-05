package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

import static dbps.dbps.Constants.IS_ASCII;

public class BasicSettingController {
    @FXML
    public Pane basicPane;
    public ComboBox<String> programLanguage;


    MainService mainService;
    ConfigService configService;

    @FXML
    public ComboBox<String> protocolFormat;

    ResourceBundle bundle;


    @FXML
    public void initialize() {
        bundle= ResourceManager.getInstance().getBundle();
        configService = ConfigService.getInstance();
        //초기설정에 따라서 메세지 탭 변경
        mainService = MainService.getInstance();

        protocolFormat.getItems().addAll(
                bundle.getString("ASCiiProtocol"),
                bundle.getString("HexProtocol")
        );

        basicPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/communicationSetting.css").toExternalForm());

        if (IS_ASCII){
            protocolFormat.setValue(bundle.getString("ASCiiProtocol"));
        } else {
            protocolFormat.setValue(bundle.getString("HexProtocol"));
        }

        //드롭다운 감지해서 탭 변경
        protocolFormat.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(bundle.getString("ASCiiProtocol"))) {
                mainService.showASCiiMsgTab();
                IS_ASCII = true;
                configService.setProperty("IS_ASCII", "true");
            } else if (newValue.equals(bundle.getString("HexProtocol"))) {
                mainService.showHEXMsgTab();
                IS_ASCII = false;
                configService.setProperty("IS_ASCII", "false");
            }
            mainService.changeSetTab();
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