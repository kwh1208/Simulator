package dbps.dbps.controller;

import dbps.dbps.Simulator;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static dbps.dbps.Constants.openModal;

public class AdvancedSettingController {


    @FXML
    Pane ASAP;

    @FXML
    public void initialize(){
        ASAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/advancedSetting.css").toExternalForm());
    }


    // 폰트 설정 모달창
    @FXML
    public void fontSetting(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/fontSetting.fxml", "폰트 설정", mouseEvent);
    }

    @FXML
    public void communicationSettingClicked(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/communicationSetting.fxml", "통신 설정", mouseEvent);
    }

    //표출신호 창 열기
    @FXML
    public void transferSignalSetting(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/displaySignalSetting.fxml", "표출신호 설정", mouseEvent);
    }
    //보드기능 설정 창 열기
    @FXML
    public void boardSetting(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/boardSettings.fxml", "보드 기능 설정", mouseEvent);
    }

    //펌웨어 모달창 열기
    @FXML
    public void firmwareInfo(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/firmwareUpgrade.fxml", "펌웨어 정보", mouseEvent);
    }



    /**
     * @param fxmlPath open 모달창 fxml 경로
     * @param title 모달창 이름
     * 모달창 open
     */


}
