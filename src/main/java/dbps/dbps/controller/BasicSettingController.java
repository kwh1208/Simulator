package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.MainService;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

import java.util.Objects;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.IS_ASCII;
import static dbps.dbps.controller.HEXMessageController.isAsc;

public class BasicSettingController {
    @FXML
    public Pane basicPane;
    public ComboBox<String> programLanguage;
    public RadioButton hexRadioBtn;
    public RadioButton ascRadioBtn;
    ToggleGroup protocolType = new ToggleGroup();


    MainService mainService;
    ConfigService configService;


    ResourceBundle bundle;


    @FXML
    public void initialize() {
        bundle= ResourceManager.getInstance().getBundle();
        configService = ConfigService.getInstance();
        //초기설정에 따라서 메세지 탭 변경
        mainService = MainService.getInstance();
        hexRadioBtn.setToggleGroup(protocolType);
        ascRadioBtn.setToggleGroup(protocolType);

        if (IS_ASCII){
            ascRadioBtn.setSelected(true);
            isAsc.set(true);
        } else {
            hexRadioBtn.setSelected(true);
            isAsc.set(false);
        }

        isAsc.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                ascRadioBtn.setSelected(true);
            } else {
                hexRadioBtn.setSelected(true);
            }
        });

        protocolType.selectedToggleProperty().addListener((observable, oldValue, newValue)->{
            if (newValue.equals(hexRadioBtn)){
                IS_ASCII=false;
                isAsc.set(false);
            }
            else {
                IS_ASCII=true;
                isAsc.set(true);
            }
            configService.setProperty("IS_ASCII", String.valueOf(IS_ASCII));
        });

        basicPane.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/communicationSetting.css")).toExternalForm());

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
                configService.setProperty("PROGRAM_LANGUAGE", "English");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("alert");
                alert.setHeaderText(null);
                alert.setContentText("Please restart the program to apply the language change.");
                alert.showAndWait();
            }
        });

    }
}