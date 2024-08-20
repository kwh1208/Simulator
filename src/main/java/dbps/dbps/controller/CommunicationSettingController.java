package dbps.dbps.controller;



import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.LogService;
import dbps.dbps.service.SerialPortManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;


import static dbps.dbps.controller.SettingController.communicationSettingWindow;
import static dbps.dbps.service.LogService.logService;

public class CommunicationSettingController {

    SerialPortManager serialPortManager = SerialPortManager.getInstance();

    public static String openPortName = null;

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
    private ChoiceBox<String> RS485ChoiceBox;

    @FXML
    private Button findSpeedBtn;

    @FXML
    private Button openDeviceManagerBtn;

    @FXML
    private CheckBox BLEChkBox;

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
    private ChoiceBox<String> delayTime;

    public static int selectedTime;

    //초기화
    @FXML
    private void initialize() {
        //delayTime 변경하면 delayTime 값 변경

        //토글버튼 그룹화
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


        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> changePort(oldValue, newValue));

        RS485ChkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                RS485ChoiceBox.setVisible(true);
            } else {
                RS485ChoiceBox.setVisible(false);
            }
        });
    }

    //사용가능한 포트 가져오기
    private void getSerialPortList() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            serialPortComboBox.getItems().add(port.getSystemPortName());
        }
    }

    //장치관리자 열기
    public void openDeviceManager() {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "devmgmt.msc");
            pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //통신속도 찾기
    public void findCommunicationSpeed(){
        int speed = serialPortManager.findSpeed();

        serialSpeedChoiceBox.setValue(String.valueOf(speed));
    }

    @FXML
    public void openSerialPort(){
        openPort(serialPortComboBox.getValue());
    }

    @FXML
    public void closeSerialPort() {
        closePort(serialPortComboBox.getValue());
    }


    //포트열기
    public void openPort(String portName){
        serialPortManager.openPort(portName, Integer.parseInt(serialSpeedChoiceBox.getValue()));
        openPortName = portName;
    }

    //포트닫기
    public void closePort(String portName) {
        serialPortManager.closePort(portName);
        openPortName = null;
    }

    //comboBox 변화 감지 함수
    public void changePort(String oldPort, String newPort){
        closePort(oldPort);
        openPort(newPort);
    }

    //다빛넷 열기
    public void openDabitNet(){
    }

    //블루투스 열기
    public void openBluetooth() {
    }

    //컨트롤러 연결하고 확인신호 보내기
    @FXML
    public void controllerConnect(){
        serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));

        //시리얼 일때
        String response = serialPortManager.sendMsgAndGetMsgHex("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03");
        if (!response.isEmpty()){
            logService.updateInfoLog(response);
        }

        //블루투스일때
        if(BLEChkBox.isSelected()){
            String response_BLE = serialPortManager.sendMsgAndGetMsg("대충 블루투스일때 보내는 메세지");
            logService.updateInfoLog(response_BLE);
        }
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
