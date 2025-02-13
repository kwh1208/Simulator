package dbps.dbps.controller;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.Simulator;
import dbps.dbps.service.BTService;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.connectManager.BTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.OPEN_PORT_NAME;
import static dbps.dbps.Constants.isBT;

public class BlueToothController {

    public TextField ble_id;
    public TextField ble_password;
    public AnchorPane bluetoothAP;
    public ProgressIndicator progressIndicator;
    public ComboBox<String> serialPortComboBox;
    BTManager btManager;
    BTService btService;
    ConfigService configService;
    @FXML
    public void initialize(){
        configService = ConfigService.getInstance();
        btManager = BTManager.getInstance();
        btService = BTService.getInstance();

        btService.setBle_id(ble_id);
        btService.setBle_password(ble_password);

        btManager.setProgressIndicator(progressIndicator);

        bluetoothAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/bluetooth.css").toExternalForm());

        serialPortComboBox.setValue(configService.getProperty("openPortName"));

        if (!serialPortComboBox.getItems().isEmpty()) {
            serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));
        }

        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            serialPortComboBox.setValue(newValue);
            System.out.println("newValue = " + newValue);
            System.out.println(newValue.getClass());
            OPEN_PORT_NAME=newValue;
            configService.setProperty("openPortName", newValue);
        });

        serialPortComboBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());
    }

    private void getSerialPortList() {
        String selectedValue = configService.getProperty("openPortName");
        List<String> portNames = Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .sorted(Comparator.comparingInt(this::extractPortNumber))
                .toList();

        serialPortComboBox.getItems().setAll(portNames);
        // 기존 선택값 복원
        if (selectedValue != null && portNames.contains(selectedValue)) {
            serialPortComboBox.setValue(selectedValue);
        } else {
            serialPortComboBox.getSelectionModel().selectFirst();
        }
    }

    private int extractPortNumber(String portName) {
        return Integer.parseInt(portName.replaceAll("[^0-9]", ""));
    }

    //블루투스 검색
    public void search() {
        isBT = true;
        btManager.search();
    }

    //블루투스 이름 및 비밀번호 설정
    public void set( ){
        //++SET++![BT SETT  31  name  password!]
        btManager.set(ble_id.getText(), ble_password.getText());
    }

    //블루투스 통신 시작
    public void begin( ) throws ExecutionException, InterruptedException {
        //++SET++![BT password             BEGIN!]
        btManager.begin(ble_password.getText());
    }

    //블루투스 통신 종료
    public void end( ) {
        //++SET++![BT password             END!]
        btManager.end(ble_password.getText());
    }

    //창종료
    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}