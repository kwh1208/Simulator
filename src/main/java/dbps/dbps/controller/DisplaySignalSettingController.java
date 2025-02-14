package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import static dbps.dbps.Constants.*;
import static dbps.dbps.controller.DisplayListController.SELECTED_SIGNAL;
import static dbps.dbps.service.DisplaySignal.SignalMap_ASC;
import static dbps.dbps.service.DisplaySignal.SignalMap_HEX;

public class DisplaySignalSettingController {

    @FXML
    public TextArea memo;
    public ProgressIndicator progressIndicator;

    @FXML
    private AnchorPane displaySignalAP;

    @FXML
    private ListView<String> signalList;

    @FXML
    private ChoiceBox<String> colorScan;

    @FXML
    private ChoiceBox<String> scanOrder;

    @FXML
    private Spinner<Integer> spinnerForSec;

    @FXML
    private Spinner<Integer> spinnerForBefore;

    @FXML
    private Spinner<Integer> spinnerForAfter;

    @FXML
    private Button autoTransfer;

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;

    DisplaySignal displaySignal;

    ConfigService configService;

    public static Timeline timeline;

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactoryForSec = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 3);
        SpinnerValueFactory<Integer> valueFactoryForBefore = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);
        SpinnerValueFactory<Integer> valueFactoryForAfter = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);

        spinnerForSec.setValueFactory(valueFactoryForSec);
        spinnerForBefore.setValueFactory(valueFactoryForBefore);
        spinnerForAfter.setValueFactory(valueFactoryForAfter);

        spinnerForSec.setEditable(true);
        spinnerForBefore.setEditable(true);
        spinnerForAfter.setEditable(true);

        signalList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("08D-P64D1S21")){
                scanOrder.getItems().clear();
                scanOrder.getItems().addAll("138 IC", "L800", "NO IC");
                scanOrder.setValue("138 IC");
                scanOrder.setDisable(false);
            }else if (newValue.equals("04D-P32D2S61")){
                scanOrder.getItems().clear();
                scanOrder.getItems().addAll("138 IC", "595 IC", "SUM2017TD IC");
                scanOrder.getStyleClass().add("wide-choice-box");
                scanOrder.setValue("138 IC");
                scanOrder.setDisable(false);
            }
            else {
                scanOrder.setValue("138 IC");
                scanOrder.setDisable(true);
            }
            memo.setText(configService.getDisplayProperty(signalList.getFocusModel().getFocusedItem()));
        });

        signalList.setOnMouseClicked(event -> handleDoubleClick(event, signalList));

        signalList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String selectedItem = signalList.getSelectionModel().getSelectedItem(); // 선택된 항목 가져오기
                if (selectedItem != null) {
                    signalTransfer();
                }
            }
        });


        displaySignalAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/displaySignal.css").toExternalForm());
        displaySignal = DisplaySignal.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        configService = ConfigService.getInstance();
    }

    private void handleDoubleClick(MouseEvent event, ListView<String> listView) {
        if (event.getClickCount() == 2) { // 더블클릭 감지
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                signalTransfer();
            }
        }
    }

    //현재창 닫기
    @FXML
    void closeWindow() {
        Stage stage = (Stage) spinnerForSec.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void signalTransfer() {
        if (IS_ASCII||signalList.getSelectionModel().getSelectedItem().equals("08D-P64D1S71")){
        String selectedSignal = signalList.getSelectionModel().getSelectedItem();
        String signalProtocol = makePerfectProtocol(selectedSignal);
        String transferProtocol;
        if (isRS){
            transferProtocol = "!["+convertRS485AddrASCii()+signalProtocol+"!]";
        }else transferProtocol = "![0"+signalProtocol+"!]";
        asciiMsgTransceiver.sendMessages(transferProtocol, false, progressIndicator);
        }
        else {
            String selectedSignal = signalList.getSelectionModel().getSelectedItem();
            String signalProtocol = makePerfectProtocolHEX(selectedSignal);
            hexMsgTransceiver.sendMessages(signalProtocol, progressIndicator);
        }
    }

    private String makePerfectProtocolHEX(String selectedSignal) {
        String result = "10 02 00 ";
        if (isRS){
            result = "10 02 " + String.format("%02X ", RS485_ADDR_NUM);
        }
        result += SignalMap_HEX.get(selectedSignal);
        switch (colorScan.getValue()){
            case "RGB":
                result = result + " 01";
                break;
            case "RBG":
                result = result + " 02";
                break;
            case "GRB":
                result = result + " 03";
                break;
            case "GBR":
                result = result + " 04";
                break;
            case "BRG":
                result = result + " 05";
                break;
            case "BGR":
                result = result + " 06";
                break;
            case "NC1":
                result = result + " 07";
                break;
        }
        if (selectedSignal.equals("08D-P64D1S21")) {
            switch (scanOrder.getValue()){
                case "138 IC":
                    result = result + " 01";
                    break;
                case "595 IC":
                    result = result + " 05";
                    break;
                case "SUM2017TD IC":
                    result = result + " 06";
                    break;
            }
        } else if (selectedSignal.equals("04D-P32D2S61")) {
            switch (scanOrder.getValue()){
                case "138 IC":
                    result = result + " 01";
                    break;
                case " L800":
                    result = result + " 02";
                    break;
                case "NO IC":
                    result = result + " 03";
                    break;
            }
        } else {
            result = result + " 01";
        }
        result+=" 10 03";
        return result;
    }

    private String makePerfectProtocol(String selectedSignal) {
        String result = SignalMap_ASC.get(selectedSignal);
        switch (colorScan.getValue()){
            case "RGB":
                result = result + "1";
                break;
            case "RBG":
                result = result + "2";
                break;
            case "GRB":
                result = result + "3";
                break;
            case "GBR":
                result = result + "4";
                break;
            case "BRG":
                result = result + "5";
                break;
            case "BGR":
                result = result + "6";
                break;
            case "NC1":
                result = result + "7";
                break;
        }
        if (selectedSignal.equals("08D-P64D1S21")) {
            switch (scanOrder.getValue()){
                case "138 IC":
                    result = result + "1";
                    break;
                case "595 IC":
                    result = result + "5";
                    break;
                case "SUM2017TD IC":
                    result = result + "6";
                    break;
            }
        } else if (selectedSignal.equals("04D-P32D2S61")) {
            switch (scanOrder.getValue()){
                case "138 IC":
                    result = result + "1";
                    break;
                case " L800":
                    result = result + "2";
                    break;
                case "NO IC":
                    result = result + "3";
                    break;
            }
        } else {
            result = result + "1";
        }
        return result;
    }

    @FXML
    public void autoTransfer() {
        if(autoTransfer.getText().equals("해제")){
            timeline.stop(); // Timeline 중지
            timeline = null; // 객체 초기화
            autoTransfer.setText("자동전송"); // 버튼 텍스트를 원래대로 변경
            return; // 함수 종료
        }
        int signalCount = signalList.getItems().size();
        Integer time = spinnerForSec.getValue(); // 지연 시간을 가져옴 (초 단위)
        int originalTime = RESPONSE_LATENCY;
        RESPONSE_LATENCY = time;
        int startIdx = signalList.getSelectionModel().getSelectedIndex();
        timeline = new Timeline();
        timeline.setCycleCount(signalCount); // 각 신호에 대해 반복

        long ctime = System.currentTimeMillis();
        for (int i = startIdx; i < signalCount; i++) {
            int index = i; // 람다식 내부에서 사용될 인덱스
            KeyFrame keyFrame = new KeyFrame(Duration.seconds((i - startIdx) * time), event -> {
                // 신호를 선택하여 UI에 반영
                signalList.getSelectionModel().select(index);
                // signalTransfer() 호출
                signalTransfer();
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        // 작업이 끝나면 원래의 대기 시간을 복구
        timeline.setOnFinished(event -> {
            RESPONSE_LATENCY = originalTime;
            autoTransfer.setText("자동 전송"); // 모든 작업이 끝나면 버튼 텍스트를 원래대로 변경
            timeline = null; // 타임라인 초기화
        });

        autoTransfer.setText("해제"); // 자동 전송 시작 시 버튼 텍스트 변경
        timeline.play(); // 타임라인 시작
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
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"0B50!]";
        }
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    public void save() {
        configService.setDisplayProperties(signalList.getFocusModel().getFocusedItem(), memo.getText());
    }

    public void search(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/displayList.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        DisplayListController controller = fxmlLoader.getController();

        Stage modalStage = new Stage();
        modalStage.setTitle("통신 설정");
        modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.png")));

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.setOnHiding(event -> {
            int targetIndex = signalList.getItems().indexOf(SELECTED_SIGNAL);
            signalList.getSelectionModel().select(targetIndex);
        });

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
    }
}