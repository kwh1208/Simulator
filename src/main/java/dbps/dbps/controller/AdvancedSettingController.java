package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class AdvancedSettingController {

    HexMsgTransceiver hexMsgTransceiver;
    AsciiMsgTransceiver asciiMsgTransceiver;


    @FXML
    Pane ASAP;

    @FXML
    public void initialize(){
        ASAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/advancedSetting.css").toExternalForm());

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
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
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/displaySignalSetting.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("표출신호 설정");
        modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.jpg")));
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
            double parentHeight = parentStage.getHeight();

            // 모달 창 크기 계산
            double modalWidth = modalStage.getWidth();

            // 위치 계산
            double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙
            double modalY = parentY;

            // 위치 설정
            modalStage.setX(modalX);
            modalStage.setY(modalY);
        });

        modalStage.setOnCloseRequest(e->{
            if (DisplaySignalSettingController.timeline !=null){
                DisplaySignalSettingController.timeline.stop();
            }
        });

        modalStage.showAndWait();
    }

    //보드기능 설정 창 열기
    @FXML
    public void boardSetting(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/boardSettings.fxml", "보드 기능 설정", mouseEvent);
    }

    public void pageMsg(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/messageSetting.fxml", "페이지메세지 설정", mouseEvent);
    }

    //펌웨어 모달창 열기
    @FXML
    public void firmwareInfo(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/firmwareUpgrade.fxml", "펌웨어 정보", mouseEvent);
    }

    public void openAdditionalFunction(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/additionalFunctions.fxml", "추가 기능", mouseEvent);
    }

    public void resetController() {
        if (IS_ASCII) {
            String msg = "![0041!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "041!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
        } else {
            String msg = "10 02 00 00 02 89 00 10 03";
            if (isRS) {
                msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 02 89 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    /**
     * 누리콘
     */

    public void hardReset() {
        if (IS_ASCII) {
            String msg = "![0042!]";
            if (isRS) {
                msg = "![" + convertRS485AddrASCii() + "042!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, commonProgressIndicator);
        }
        //비트수, 세로, 가로크기 가져와서 같이 보내줘야함.
        else {
            String msg = "10 02 00 00 04 4A 0" + BITS_PER_PIXEL + " 0" + SIZE_ROW + " 0" + SIZE_COLUMN + " 10 03";
            if (isRS) {
                msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 04 4A 0" + BITS_PER_PIXEL + " 0" + SIZE_ROW + " 0" + SIZE_COLUMN + " 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }

    public void docs() {
        try {
            Desktop.getDesktop().browse(new URI("https://publish.obsidian.md/dabitdocs"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void ASPhoto() {
        try {
            Desktop.getDesktop().browse(new URI("https://forms.gle/zkt5ALsQKKZhbQnx9"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
