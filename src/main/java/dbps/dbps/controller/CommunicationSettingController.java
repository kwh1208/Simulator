package dbps.dbps.controller;



import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.SerialPortManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static dbps.dbps.controller.SettingController.communicationSettingWindow;

public class CommunicationSettingController {

    private static final Logger log = LoggerFactory.getLogger(CommunicationSettingController.class);

    SerialPortManager serialPortManager = SerialPortManager.getInstance();
    /**
     * 시리얼
     */
    @FXML
    private RadioButton serialRadioBtn;

    @FXML
    private ComboBox<String> serialPortComboBox;

    @FXML
    private ChoiceBox<String> serialSpeedChoiceBox;

    @FXML
    private CheckBox RS485ChkBox;

    @FXML
    private Button findSpeedBtn;

    @FXML
    private Button openDeviceManagerBtn;

    /**
     * 클라이언트 TCP/IP
     */
    @FXML
    private RadioButton clientTCPRadioBtn;

    @FXML
    private TextField ClientIPAddress;

    @FXML
    private TextField ClientIPPort;

    /**
     * 서버 TCP/IP
     */

    @FXML
    private RadioButton serverTCPRadioBtn;

    @FXML
    private TextField serverIPAddress;

    @FXML
    private TextField serverIPPort;

    /**
     *  UDP
     */

    @FXML
    private RadioButton UDPRadioBtn;

    @FXML
    private TextField UDPIPAddress;

    @FXML
    private TextField UDPIPPort;

    @FXML
    private void initialize() {
        ToggleGroup communicationGroup = new ToggleGroup();
        serialRadioBtn.setToggleGroup(communicationGroup);
        clientTCPRadioBtn.setToggleGroup(communicationGroup);
        serverTCPRadioBtn.setToggleGroup(communicationGroup);
        UDPRadioBtn.setToggleGroup(communicationGroup);

        communicationGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            if (selectedRadioButton.equals(serialRadioBtn)) {
                serialRadioToggle(true);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
            } else if (selectedRadioButton.equals(clientTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(true);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
            } else if (selectedRadioButton.equals(serverTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(true);
                UDPRadioToggle(false);
            } else  {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(true);
            }

        });

        serialRadioBtn.setSelected(true);

        getSerialPortList();


        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> closePort(oldValue));
    }

    private void getSerialPortList() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            serialPortComboBox.getItems().add(port.getSystemPortName());
        }
    }

    public void openDeviceManager() {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "devmgmt.msc");
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void findCommunicationSpeed() throws IOException {
        log.info("통신 속도 찾기");

        int speed = serialPortManager.findSpeed(serialPortComboBox.getValue());

        serialSpeedChoiceBox.setValue(String.valueOf(speed));
    }

    @FXML
    public void openSerialPort() throws IOException {
        openPort(serialPortComboBox.getValue());
    }

    @FXML
    public void closeSerialPort() {
        closePort(serialPortComboBox.getValue());
    }


    public void openPort(String portName) throws IOException {
        serialPortManager.openPort(portName, Integer.parseInt(serialSpeedChoiceBox.getValue()));
    }

    public void closePort(String portName) {
        serialPortManager.closePort(portName);
    }

    public void openDabitNet(){
        serialPortManager.sendMsgAndGetMsg(serialPortComboBox.getValue(), "![0052B11111!]", 3);

    }

    public void openBluetooth() {
    }

    @FXML
    public void controllerConnect() throws IOException {
        serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));

        try {
            serialPortManager.sendMsgAndGetMsgHex(serialPortComboBox.getValue(), "10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03", 1);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        log.info("컨트롤러가 연결되었습니다.");
    }

    public void communicationSettingClose() {
        communicationSettingWindow.close();
    }


    /**
     * 리팩토링용
     */

    private void UDPRadioToggle(boolean isUDP) {
        if (isUDP) {
            UDPIPAddress.setDisable(false);
            UDPIPPort.setDisable(false);
        } else {
            UDPIPAddress.setDisable(true);
            UDPIPPort.setDisable(true);
        }

    }

    private void serverTCPRadioToggle(boolean isServer) {
        if (isServer) {
            serverIPAddress.setDisable(false);
            serverIPPort.setDisable(false);
        } else {
            serverIPAddress.setDisable(true);
            serverIPPort.setDisable(true);
        }
    }

    private void clientTCPRadioToggle(boolean isClient) {
        if (isClient) {
            ClientIPAddress.setDisable(false);
            ClientIPPort.setDisable(false);
        } else {
            ClientIPAddress.setDisable(true);
            ClientIPPort.setDisable(true);
        }
    }

    private void serialRadioToggle(boolean isSerial) {
        if (isSerial) {
            serialPortComboBox.setDisable(false);
            serialSpeedChoiceBox.setDisable(false);
            RS485ChkBox.setDisable(false);
            findSpeedBtn.setDisable(false);
            openDeviceManagerBtn.setDisable(false);
        } else {
            serialPortComboBox.setDisable(true);
            serialSpeedChoiceBox.setDisable(true);
            RS485ChkBox.setDisable(true);
            findSpeedBtn.setDisable(true);
            openDeviceManagerBtn.setDisable(true);
        }
    }
}
