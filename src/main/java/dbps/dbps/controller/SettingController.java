package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static dbps.dbps.Constants.*;

public class SettingController {

    @FXML
    public ProgressIndicator commonProgressIndicator;

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;
    LogService logService;
    SettingService settingService;

    @FXML
    public ChoiceBox<String> displayBright;

    @FXML
    public ChoiceBox<String> pageMsgType;

    @FXML
    public void initialize(){
        settingService = SettingService.getInstance(commonProgressIndicator);
        logService = LogService.getLogService();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
    }

    @FXML
    public void communicationSettingClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/communicationSetting.fxml"));
            fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
            Parent root = fxmlLoader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("통신 설정");

            modalStage.initModality(Modality.APPLICATION_MODAL);

            Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            modalStage.initOwner(parentStage);

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.setResizable(false);

            modalStage.setOnShown(event -> {
                // 부모 창 위치와 크기 가져오기
                double parentX = parentStage.getX();
                double parentY = parentStage.getY();
                double parentWidth = parentStage.getWidth();

                // 모달 창 크기 계산
                double modalWidth = modalStage.getWidth();

                // 위치 계산
                double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙
                double modalY = parentY;

                // 위치 설정
                modalStage.setX(modalX);
                modalStage.setY(modalY);
            });

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // 사용자에게 오류 메시지 표시 (선택 사항)
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw); // 스택 트레이스를 StringWriter에 출력
            String stackTrace = sw.toString(); // 전체 스택 트레이스를 문자열로 변환

            logService.errorLog("오류 발생:\n" + stackTrace);
            showAlert("오류", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void sendDisplayBright() {
        if (IS_ASCII){
            String msg = "![0050";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"050";
            }
            switch (displayBright.getValue()){
                case "100%(기본)": msg += "99"; break;
                case "75%": msg += "75"; break;
                case "50%": msg += "50"; break;
                case "25%": msg += "25"; break;
                case "5%": msg += "05"; break;
            }
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
            

        } else{
            String msg = "10 02 00 00 02 44 ";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 02 44 ";
            }
            switch (displayBright.getValue()){
                case "100%(기본)": msg += "64"; break;
                case "75%": msg += "48"; break;
                case "50%": msg += "32"; break;
                case "25%": msg += "19"; break;
                case "5%": msg += "05"; break;
            }
            msg += " 10 03";
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    public void sendPageMsgType() {
        if (IS_ASCII){
            //![0062N!] : 동시, ![0062Y!] : 개별
            String msg = "![0062";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"062";
            }
            if (pageMsgType.getValue().contains("동시")){
                msg += "N";
            } else{
                msg += "Y";
            }
            msg += "!]";
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
            

        } else {
            String msg;
            if (pageMsgType.getValue().contains("동시")){
                msg = "21 5B 30 30 36 32 4E 21 5D";
            }else{
                msg = "21 5B 30 30 36 32 59 21 5D";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }
}
