package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

    public void openAdditionalFunction(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/additionalFunctions.fxml", "추가 기능", mouseEvent);
    }

    public void resetController() throws InterruptedException {
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
