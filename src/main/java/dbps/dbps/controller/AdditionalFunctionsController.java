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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;

public class AdditionalFunctionsController {

    public ComboBox<String> displaySpeed;
    public ComboBox<String> blinkCnt;
    public ChoiceBox<String> fillColor;
    public ChoiceBox<String> offset;
    public ChoiceBox<Double> fontWidth;
    public ChoiceBox<Double> fontHeight;
    public ProgressIndicator progressIndicator;
    public AnchorPane additionalFunctionAp;
    AsciiMsgTransceiver asciiMsgTransceiver;
    ResourceBundle bundle;

    HexMsgTransceiver hexMsgTransceiver;
    @FXML
    public void initialize(){
        additionalFunctionAp.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        bundle = ResourceManager.getInstance().getBundle();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();

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

        addItems();
    }

    private void addItems() {
        fillColor.getItems().addAll(
                bundle.getString("black"),
                bundle.getString("red"),
                bundle.getString("green"),
                bundle.getString("yellow"),
                bundle.getString("blue"),
                bundle.getString("pink"),
                bundle.getString("cyan"),
                bundle.getString("white")
        );
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

    public void sendFillColor() throws InterruptedException {
        String value = fillColor.getValue();
        if (IS_ASCII){
            String msg = "![0070"+getColorCode(value)+"!]";
            if (isRS){
                msg = "!["+convertRS485AddrASCii()+"070"+getColorCode(value)+"!]";
            }
            asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
        }
        else {
            hexMsgTransceiver.sendMessages("10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 02 45 00 10 03", progressIndicator);

            String msg = "10 02 00 00 06 42 08 "+getColorCodeHex(value)+"00 00 00 10 03";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 06 42 08 "+getColorCodeHex(value)+"00 00 00 10 03";
            }
            hexMsgTransceiver.sendMessages(msg, progressIndicator);
        }
    }

    private String getColorCode(String value) {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("검은색", "0");
        colorMap.put("빨간색", "1");
        colorMap.put("초록색", "2");
        colorMap.put("노란색", "3");
        colorMap.put("파란색", "4");
        colorMap.put("분홍색", "5");
        colorMap.put("청록색", "6");
        colorMap.put("흰색", "7");
        colorMap.put("보라색", "8");
        colorMap.put("하늘색", "9");

        return colorMap.getOrDefault(value, "0"); // 기본값을 검은색("0")으로 설정
    }

    private String getColorCodeHex(String value) {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("검은색", "00 ");
        colorMap.put("빨간색", "07 ");
        colorMap.put("초록색", "38 ");
        colorMap.put("노란색", "3F ");
        colorMap.put("파란색", "C0 ");
        colorMap.put("분홍색", "C7 ");
        colorMap.put("청록색", "F8 ");
        colorMap.put("흰색", "FF "); // 기본값을 흰색으로 지정

        return colorMap.getOrDefault(value, "FF ");
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

    public void fontName(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/fontName.fxml", "폰트 이름 설정", mouseEvent);
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
}
