package dbps.dbps.controller;



import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.SerialPortManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;


import static dbps.dbps.Constants.*;

public class CommunicationSettingController {


    SerialPortManager serialPortManager;

    HexMsgTransceiver hexMsgTransceiver;

    @FXML
    private ProgressIndicator loadingSpinner;

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

    //초기화
    @FXML
    private void initialize() {
        serialPortManager = SerialPortManager.getManager();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();


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
        serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));


        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            serialPortComboBox.setValue(newValue);
        });
        serialPortComboBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());


        RS485ChkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> RS485ChoiceBox.setVisible(newValue));

        //응답시간 변경
        delayTime.selectionModelProperty().addListener((observableValue, oldValue, newValue) -> responseLatency = Integer.parseInt(delayTime.getValue()));
    }

    //사용가능한 포트 가져오기
    private void getSerialPortList() {
        SerialPort[] ports = SerialPort.getCommPorts();
        ObservableList<String> items = serialPortComboBox.getItems();

        String currentSelection = serialPortComboBox.getValue(); // 현재 선택된 값 저장


        List<String> portNames = Arrays.stream(ports)
                .map(SerialPort::getSystemPortName)
                .sorted() // 알파벳순 정렬 (COM 포트 번호 기준으로 자동 정렬됨)
                .toList();

        items.clear();
        // 정렬된 포트 이름을 items에 추가
        items.addAll(portNames);

        // ComboBox에 정렬된 목록 설정
        serialPortComboBox.setItems(items);

        // 기존에 선택한 값이 여전히 리스트에 있다면, 다시 선택해줍니다.
        if (currentSelection != null && items.contains(currentSelection)) {
            serialPortComboBox.setValue(currentSelection);
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
    public void findCommunicationSpeed() {
        // UI 업데이트를 JavaFX 애플리케이션 스레드에서 즉시 실행
        Platform.runLater(() -> loadingSpinner.setVisible(true));

        int speed = serialPortManager.findSpeed(); // 속도 찾기

        serialBaudRate = speed;

        serialSpeedChoiceBox.setValue(String.valueOf(speed));

        Platform.runLater(() -> loadingSpinner.setVisible(false));
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

    //다빛넷 열기
    public void openDabitNet(){
    }

    //블루투스 열기
    public void openBluetooth() {
    }

    //컨트롤러 연결하고 확인신호 보내기
    @FXML
    public void controllerConnect(){
        //시리얼 일때
        CONNECT_TYPE = "serial";
        serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));
        String receiveMsg = hexMsgTransceiver.sendMessages("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03");
        if (!receiveMsg.split(" ")[5].equals("6A")){
            CONNECT_TYPE = "none";
        }
    }

    public void communicationSettingClose(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
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
