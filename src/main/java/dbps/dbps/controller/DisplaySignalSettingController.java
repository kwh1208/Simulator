package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.DisplaySignal;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static dbps.dbps.Constants.IS_ASCII;
import static dbps.dbps.Constants.RESPONSE_LATENCY;
import static dbps.dbps.service.DisplaySignal.SignalMap_ASC;
import static dbps.dbps.service.DisplaySignal.SignalMap_HEX;

public class DisplaySignalSettingController {

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

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;

    DisplaySignal displaySignal;

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
        });

        displaySignalAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/displaySignal.css").toExternalForm());
        displaySignal = DisplaySignal.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    //현재창 닫기
    @FXML
    void closeWindow() {
        Stage stage = (Stage) spinnerForSec.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void signalTransfer() {
        if (IS_ASCII){
        String selectedSignal = signalList.getSelectionModel().getSelectedItem();
        String signalProtocol = makePerfectProtocol(selectedSignal);
        String transferProtocol = "!["+signalProtocol+"!]";
        asciiMsgTransceiver.sendMessages(transferProtocol);
        }
        else {
            String selectedSignal = signalList.getSelectionModel().getSelectedItem();
            String signalProtocol = makePerfectProtocolHEX(selectedSignal);
            hexMsgTransceiver.sendMessages(signalProtocol);
        }
    }

    private String makePerfectProtocolHEX(String selectedSignal) {
        String result = SignalMap_HEX.get(selectedSignal);
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
        Integer time = spinnerForSec.getValue();
        int originalTime = RESPONSE_LATENCY;
        if (IS_ASCII){
            RESPONSE_LATENCY = time;
            signalList.getItems().forEach(signal -> {
                String signalProtocol = SignalMap_ASC.get(signal);
                signalList.getSelectionModel().select(signal);
                String transferProtocol = "!["+makePerfectProtocol(signalProtocol)+"!]";
                asciiMsgTransceiver.sendMessages(transferProtocol);
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        else {
            RESPONSE_LATENCY = time;
            signalList.getItems().forEach(signal -> {
                String signalProtocol = SignalMap_HEX.get(signal);
                signalList.getSelectionModel().select(signal);
                String transferProtocol = makePerfectProtocolHEX(signalProtocol);
                hexMsgTransceiver.sendMessages(transferProtocol);
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        RESPONSE_LATENCY = originalTime;
    }

    @FXML
    public void setting() {
        Integer before = spinnerForBefore.getValue();
        Integer after = spinnerForAfter.getValue();

        String msg = "![00B4"+before+" "+after+"!]";

        asciiMsgTransceiver.sendMessages(msg);
    }

    @FXML
    public void read() {
        asciiMsgTransceiver.sendMessages("![00B50!]");
    }

    public void save(MouseEvent mouseEvent) {
        //현재내용 저장.
    }
}
