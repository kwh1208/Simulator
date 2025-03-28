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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.convertRS485AddrASCii;
import static dbps.dbps.Constants.isRS;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class AdditionalFunctionsController {

    public ComboBox<String> displaySpeed;
    public ComboBox<String> blinkCnt;
    public ComboBox<String> offset;
    public ComboBox<Double> fontWidth;
    public ComboBox<Double> fontHeight;
    public ProgressIndicator progressIndicator;
    public AnchorPane additionalFunctionAp;
    public ComboBox<String> pageMsgType;
    public Spinner<Integer> spinnerForBefore;
    public Spinner<Integer> spinnerForAfter;
    AsciiMsgTransceiver asciiMsgTransceiver;
    ResourceBundle bundle;

    HexMsgTransceiver hexMsgTransceiver;
    @FXML
    public void initialize(){
        additionalFunctionAp.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        bundle = ResourceManager.getInstance().getBundle();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();


        SpinnerValueFactory<Integer> valueFactoryForBefore = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);
        SpinnerValueFactory<Integer> valueFactoryForAfter = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);


        spinnerForBefore.setValueFactory(valueFactoryForBefore);
        spinnerForAfter.setValueFactory(valueFactoryForAfter);


        spinnerForBefore.setEditable(true);
        spinnerForAfter.setEditable(true);

        displaySpeed.getItems().add(bundle.getString("notUsed"));
        for (int i = 1; i < 100; i++) {
            if (i==1){
                displaySpeed.getItems().add(bundle.getString("1(fast)"));
            } else if (i==99) {
                displaySpeed.getItems().add(bundle.getString("99(slow)"));
            }
            else displaySpeed.getItems().add(String.valueOf(i));
        }
        displaySpeed.setValue(bundle.getString("notUsed"));

        for (int i = 1; i < 21; i++) {
            if (i==8) blinkCnt.getItems().add("8"+bundle.getString("times")+bundle.getString("default"));
            else blinkCnt.getItems().add(i +bundle.getString("times"));
        }
        blinkCnt.setValue("8"+bundle.getString("times")+bundle.getString("default"));

        for (double i = 1.0; i <= 3.1; i += 0.1) {
            fontWidth.getItems().add(Double.parseDouble(String.format("%.1f", i)));
            fontHeight.getItems().add(Double.parseDouble(String.format("%.1f", i)));
        }
        fontWidth.setValue(1.0);
        fontHeight.setValue(1.0);

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();

        pageMsgType.getItems().add(bundle.getString("individualEffectDisplay"));
        pageMsgType.getItems().add(bundle.getString("simultaneousEffectDisplay"));

        pageMsgType.setValue(bundle.getString("simultaneousEffectDisplay"));
    }



    public void openBGSchedule(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/BGSchedule.fxml", "배경화면 스케쥴", mouseEvent);
    }

    public void openRelay(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/Relay.fxml", "릴레이 신호 출력", mouseEvent);
    }

    public void sendoffSet() {
        String value = offset.getValue().replaceAll("[^0-9]", "");
        String msg = "![0058 "+value+"!]";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"058 "+value+"!]";
        }
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    private void openModal(String fxmlPath, String title, MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource(fxmlPath));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();


        Stage modalStage = new Stage();
        modalStage.setTitle(title);
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
            double modalHeight = modalStage.getHeight();

            // 위치 계산
            double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙
            double modalY = parentY;

            // 위치 설정
            modalStage.setX(modalX);
            modalStage.setY(modalY);
        });


        modalStage.showAndWait();
    }


    public void sendBlinkCnt() {
        int cnt = Integer.parseInt(blinkCnt.getValue().replaceAll("[^0-9]", ""));
        String msg = "![0055 "+String.format("%2d", cnt)+"!]";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"055 "+String.format("%2d", cnt)+"!]";
        }

        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    public void sendDisplaySpeed() {
        int speed;
        if (displaySpeed.getValue().equals(bundle.getString("notUsed"))){
            speed = 0;
        }
        else{
            speed = Integer.parseInt(displaySpeed.getValue().replaceAll("[^0-9]", ""));
        }
        String msg = "![0054 "+String.format("%2d", speed)+"!]";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"054 "+String.format("%2d", speed)+"!]";
        }
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }



    public void sendFontWeight() {
        String sendMsg = "![0056 ";
        if (isRS){
            sendMsg = "!["+convertRS485AddrASCii()+"056 ";
        }
        sendMsg+= (int) (fontWidth.getValue() * 10) +" ";
        sendMsg+= (int) (fontHeight.getValue() * 10) +"!]";
        asciiMsgTransceiver.sendMessages(sendMsg, false, progressIndicator);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void sendPageMsgType() {
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
    }

    @FXML
    public void setting() {
        Integer before = spinnerForBefore.getValue();
        Integer after = spinnerForAfter.getValue();

        String beforeStr = (before < 10) ? " " + before : before.toString();
        String afterStr = (after < 10) ? " " + after : after.toString();

        String msg = "![00B4" + beforeStr + " " + afterStr + "!]";
        if (isRS) {
            msg = "![" + convertRS485AddrASCii() + "0B4" + beforeStr + " " + afterStr + "!]";
        }

        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    @FXML
    public void read() {
        String msg = "![00B50!]";
        if (isRS) {
            msg = "![" + convertRS485AddrASCii() + "0B50!]";
        }
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }
}
