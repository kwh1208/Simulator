package dbps.dbps.controller;

import dbps.dbps.service.MsgMaker;
import dbps.dbps.service.SerialPortManager;
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
    SerialPortManager serialPortManager = SerialPortManager.getInstance();

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
        String signalProtocol = SignalMap.get(selectedSignal);
        String transferProtocol = msgMaker.makeMsgASCii(signalProtocol);
        serialPortManager.sendMsgAndGetMsg(transferProtocol);
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
