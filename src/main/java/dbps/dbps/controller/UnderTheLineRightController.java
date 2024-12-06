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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static dbps.dbps.Constants.*;

public class UnderTheLineRightController {
    @FXML
    public ComboBox<String> BGImgSelection;

    @FXML
    public ChoiceBox<String> fillColor;

    @FXML
    public VBox rightVbox;
    public ComboBox<String> displaySpeed;
    public ComboBox<String> blinkCnt;
    public ChoiceBox<String> offset;


    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;


    @FXML
    public void initialize(){
        BGImgSelection.getItems().add("사용안함");
        for (int i = 1; i < 256; i++) {
            BGImgSelection.getItems().add(String.valueOf(i));
        }

        displaySpeed.getItems().add("사용안함");
        for (int i = 1; i < 100; i++) {
            if (i==1){
                displaySpeed.getItems().add("1(빠름)");
            } else if (i==99) {
                displaySpeed.getItems().add("99(빠름)");
            }
            else displaySpeed.getItems().add(String.valueOf(i));
        }

        for (int i = 1; i < 21; i++) {
            if (i==8) blinkCnt.getItems().add("8회(기본값)");
            else blinkCnt.getItems().add(i + "회");
        }

        BGImgSelection.setVisibleRowCount(10);

        rightVbox.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineRight.css").toExternalForm());

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    public void openRelay(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/Relay.fxml", "릴레이 신호 출력", mouseEvent);
    }

    public void openBGSchedule(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/BGSchedule.fxml", "배경화면 스케쥴", mouseEvent);
    }

    public void sendBGImgSelection(MouseEvent mouseEvent) {
        String value = BGImgSelection.getValue();
        if (IS_ASCII){
            String result = "";
            if (value.equals("사용안함"))
            {
                result = "000";
            }
            else result = String.format("%03d", Integer.parseInt(value));
            {
                String msg = "![0020"+result+"!]";
                if (isRS){
                    msg = "!["+convertRS485AddrASCii()+"020"+result+"!]";
                }
                asciiMsgTransceiver.sendMessages(msg,false);
            }
        }
        else {
            int result = 0;
            if (!value.equals("사용안함")){
                result = Integer.parseInt(value);
            }
            String msg = "10 02 00 00 02 4F "+String.format("%02X ", result)+"10 03";
            if (isRS){
                msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 02 4F "+String.format("%02X ", result)+"10 03";
            }
            hexMsgTransceiver.sendMessages(msg);
        }
    }

    public void sendFillColor() throws InterruptedException {
        String value = fillColor.getValue();
        if (IS_ASCII){
            String result = "";
            if (value.equals("검은색")){
                result = "0";
            }else if (value.equals("빨간색")){
                result = "1";
            } else if (value.equals("초록색")){
                result = "2";
            } else if (value.equals("노란색")){
                result = "3";
            } else if (value.equals("파란색")){
                result = "4";
            } else if (value.equals("분홍색")){
                result = "5";
            } else if (value.equals("청록색")){
                result = "6";
            } else result = "7";
            String msg = "![0070"+result+"!]";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"070"+result+"!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false);
        }
        else {
            String msg = "10 02 00 00 02 45 00 10 03";
            if (isRS){
                msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 02 45 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg);

            Thread.sleep(500);

            String result = "";
            if (value.equals("검은색")){
                result = "00 ";
            }else if (value.equals("빨간색")){
                result = "07 ";
            } else if (value.equals("초록색")){
                result = "38 ";
            } else if (value.equals("노란색")){
                result = "3F ";
            } else if (value.equals("파란색")){
                result = "C0 ";
            } else if (value.equals("분홍색")){
                result = "C7 ";
            } else if (value.equals("청록색")){
                result = "F8 ";
            } else result = "FF ";
            msg = "10 02 00 00 06 42 08 "+result+"00 00 00 10 03";
            if (isRS){
                msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 06 42 08 "+result+"00 00 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg);
        }
    }

    private void openModal(String fxmlPath, String title, MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource(fxmlPath));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle(title);
        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    public void sendDisplaySpeed() {
        int speed;
        if (displaySpeed.getValue().equals("사용안함")){
            speed = 0;
        }
        else{
            speed = Integer.parseInt(displaySpeed.getValue().replaceAll("[^0-9]", ""));
        }

        asciiMsgTransceiver.sendMessages("![0054 "+String.format("%2d", speed)+"!]", false);
    }


    public void sendBlinkCnt() {
        int cnt = Integer.parseInt(blinkCnt.getValue().replaceAll("[^0-9]", ""));

        asciiMsgTransceiver.sendMessages("![0055 "+String.format("%2d", cnt)+"!]", false);
    }

    public void sendoffSet() {
        String value = offset.getValue().replaceAll("[^0-9]", "");
        asciiMsgTransceiver.sendMessages("![0058 "+value+"!]", false);
    }
}
