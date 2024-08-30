package dbps.dbps.controller;

import dbps.dbps.service.MsgMaker;
import dbps.dbps.service.connectManager.SerialPortManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static dbps.dbps.controller.CommunicationSettingController.selectedTime;
import static dbps.dbps.service.displaySignal.SignalMap;

public class DisplaySignalSettingController {
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
    private Button settingBtn;

    @FXML
    private Button readBtn;

    MsgMaker msgMaker = MsgMaker.getInstance();
    SerialPortManager serialPortManager = SerialPortManager.getManager();

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
                scanOrder.setDisable(false);
            }else if (newValue.equals("04D-P32D2S61")){
                scanOrder.getItems().clear();
                scanOrder.getItems().addAll("138 IC", "595 IC", "SUM2017TD IC");
                scanOrder.setDisable(false);
            }
        });
    }

    //현재창 닫기
    @FXML
    void closeWindow() {
        Stage stage = (Stage) spinnerForSec.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void signalTransfer() {
        String selectedSignal = signalList.getSelectionModel().getSelectedItem();
        String signalProtocol = makePerfectProtocol(selectedSignal);
        String transferProtocol = msgMaker.makeMsgASCii(signalProtocol);
        serialPortManager.sendMsgAndGetMsg(transferProtocol);
    }

    private String makePerfectProtocol(String selectedSignal) {
        String result = SignalMap.get(selectedSignal);
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
                    result = result + "2";
                    break;
                case "SUM2017TD IC":
                    result = result + "3";
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
        int originalTime = selectedTime;
        selectedTime = time;


        signalList.getItems().forEach(signal -> {
            String signalProtocol = SignalMap.get(signal);
            signalList.getSelectionModel().select(signal);
            String transferProtocol = msgMaker.makeMsgASCii(signalProtocol);
            serialPortManager.sendMsgAndGetMsg(transferProtocol);
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        selectedTime = originalTime;
    }

    @FXML
    public void setting(MouseEvent mouseEvent) {

    }

    @FXML
    public void read(MouseEvent mouseEvent) {

    }
}
