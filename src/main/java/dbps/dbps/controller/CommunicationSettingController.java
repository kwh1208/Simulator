package dbps.dbps.controller;



import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


import static dbps.dbps.Constants.*;

public class CommunicationSettingController {


    public Button shutConnect;
    SerialPortManager serialPortManager;
    TCPManager tcpManager;

    HexMsgTransceiver hexMsgTransceiver;
    UDPManager udpManager;
    ConfigService configService;

    @FXML
    private AnchorPane communicationSettingAP;

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
    private TextField clientIPAddress;

    @FXML
    private TextField clientIPPort;

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

    @FXML
    private Button connect;

    ToggleGroup communicationGroup;

    //초기화
    @FXML
    private void initialize() {
        serialPortManager = SerialPortManager.getManager();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        tcpManager = TCPManager.getManager();
        udpManager = UDPManager.getUDPManager();
        configService = ConfigService.getInstance();


        //delayTime 변경하면 delayTime 값 변경

        //토글버튼 그룹화
        communicationGroup = new ToggleGroup();
        serialRadioBtn.setToggleGroup(communicationGroup);
        clientTCPRadioBtn.setToggleGroup(communicationGroup);
        serverTCPRadioBtn.setToggleGroup(communicationGroup);
        UDPRadioBtn.setToggleGroup(communicationGroup);

        switch (CONNECT_TYPE) {
            case "serial":
                communicationGroup.selectToggle(serialRadioBtn);  // Serial 버튼 선택
                serialRadioToggle(true);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                connect.setText("포트열기");
                shutConnect.setText("포트닫기");
                break;
            case "clientTCP":
                communicationGroup.selectToggle(clientTCPRadioBtn);  // Client TCP 버튼 선택
                serialRadioToggle(false);
                clientTCPRadioToggle(true);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                connect.setText("접속하기");
                shutConnect.setText("접속끊기");
                break;
            case "serverTCP":
                communicationGroup.selectToggle(serverTCPRadioBtn);  // Server TCP 버튼 선택
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(true);
                UDPRadioToggle(false);
                connect.setText("접속하기");
                shutConnect.setText("접속끊기");
                break;
            case "UDP":
                communicationGroup.selectToggle(UDPRadioBtn);  // UDP 버튼 선택
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(true);
                connect.setText("접속하기");
                shutConnect.setText("접속끊기");
                break;
            default:
                // 예외 상황: 아무 것도 선택하지 않음
                communicationGroup.selectToggle(null);
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                break;
        }

        communicationGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            if (selectedRadioButton.equals(serialRadioBtn)) {
                serialRadioToggle(true);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                connect.setText("포트열기");
                shutConnect.setText("포트닫기");
            } else if (selectedRadioButton.equals(clientTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(true);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                connect.setText("접속하기");
                shutConnect.setText("접속끊기");
            } else if (selectedRadioButton.equals(serverTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(true);
                UDPRadioToggle(false);
                connect.setText("접속하기");
                shutConnect.setText("접속끊기");
            } else  {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(true);
                connect.setText("접속하기");
                shutConnect.setText("접속끊기");
            }

        });



        getSerialPortList();

        serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));

        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            serialPortComboBox.setValue(newValue);
            configService.setProperty("openPortName", newValue);
        });
        serialPortComboBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());

        RS485ChkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> RS485ChoiceBox.setVisible(newValue));

        //응답시간 변경
        delayTime.selectionModelProperty().addListener((observableValue, oldValue, newValue) -> RESPONSE_LATENCY = Integer.parseInt(delayTime.getValue()));

        communicationSettingAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/communicationSetting.css").toExternalForm());

        if (isRS){
            RS485ChkBox.setSelected(true);
        }
    }

    //사용가능한 포트 가져오기
    private void getSerialPortList() {
        SerialPort[] ports = SerialPort.getCommPorts();
        ObservableList<String> items = serialPortComboBox.getItems();

        String currentSelection = serialPortComboBox.getValue(); // 현재 선택된 값 저장


        List<String> portNames = Arrays.stream(ports)
                .map(SerialPort::getSystemPortName)
                .sorted(Comparator.comparingInt(this::extractPortNumber)) // 포트 번호 기준으로 정렬
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

    // COM 포트에서 숫자 부분만 추출하여 정수로 반환
    private int extractPortNumber(String portName) {
        return Integer.parseInt(portName.replaceAll("[^0-9]", ""));
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
        Task<Integer> findSpeedTask = serialPortManager.findSpeedTask;

        findSpeedTask.setOnSucceeded(event -> {
            // Task가 성공적으로 완료된 후 값을 가져옴
            Integer speed = findSpeedTask.getValue(); // Task의 결과 값 (통신 속도)

            if (speed != null && speed > 0) {
                SERIAL_BAUDRATE = speed; // 속도 값 저장

                // ChoiceBox UI를 업데이트
                serialSpeedChoiceBox.setValue(String.valueOf(speed));
                configService.setProperty("serialSpeed", String.valueOf(speed));
            }
        });

        new Thread(findSpeedTask).start();
    }

    //포트열기, 접속하기
    @FXML
    public void openSerialPort(){
        if (communicationGroup.getSelectedToggle().equals(serialRadioBtn)) {
            openPort(serialPortComboBox.getValue());
        }
        else if (communicationGroup.getSelectedToggle().equals(clientTCPRadioBtn))
            connectClientTCP();
        else if (communicationGroup.getSelectedToggle().equals(serverTCPRadioBtn))
            connectServerTCP();
        else
            connectUDP();
    }

    private void connectServerTCP() {
        String IPAddress = serverIPAddress.getText();
        int port = Integer.parseInt(serverIPPort.getText());

        tcpManager.setIP(IPAddress);
        tcpManager.setPORT(port);
        configService.setProperty("serverTCPAddr", IPAddress);
        configService.setProperty("serverTCPPort", String.valueOf(port));

        TCP_IP = IPAddress;
        TCP_PORT = port;
    }

    private void connectClientTCP() {
        String IPAddress = clientIPAddress.getText();
        int port = Integer.parseInt(clientIPPort.getText());

        tcpManager.setIP(IPAddress);
        tcpManager.setPORT(port);
        configService.setProperty("clientTCPAddr", IPAddress);
        configService.setProperty("clientTCPPort", String.valueOf(port));

        TCP_IP = IPAddress;
        TCP_PORT = port;
    }

    private void connectUDP(){
        String IPAddress = UDPIPAddress.getText();
        int port = Integer.parseInt(UDPIPPort.getText());

        udpManager.setIP(IPAddress);
        udpManager.setPORT(port);
        configService.setProperty("UDPAddr", IPAddress);
        configService.setProperty("UDPPort", String.valueOf(port));

        UDP_IP = IPAddress;
        UDP_PORT = port;
    }

    @FXML
    public void closeSerialPort() {
        closePort(serialPortComboBox.getValue());
    }


    //포트열기
    public void openPort(String portName){
        serialPortManager.openPort(portName, Integer.parseInt(serialSpeedChoiceBox.getValue()));
        OPEN_PORT_NAME = portName;
    }

    //포트닫기
    public void closePort(String portName) {
        serialPortManager.closePort(portName);
        OPEN_PORT_NAME = null;
    }

    //다빛넷 열기
    public void openDabitNet() throws IOException {
        String relativePath = "./src/main/resources/dbps/dbps/DabitNet_S.exe";

        // 절대 경로로 변환 후 정리
        String absolutePath = new File(relativePath).getCanonicalPath();

        // 실행할 명령어 정의
        String command = "runas /user:Administrator \"" + absolutePath + "\"";
        System.out.println("command = " + command);

        // Runtime 실행
        Runtime.getRuntime().exec(command);
    }

    //블루투스 열기
    public void openBluetooth(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/blueTooth.fxml"));
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("블루투스 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    //컨트롤러 연결하고 확인신호 보내기
    @FXML
    public void controllerConnect(){
        //시리얼 일때
        if (communicationGroup.getSelectedToggle().equals(serialRadioBtn)){
            if (RS485ChkBox.isSelected()){
                CONNECT_TYPE = "rs485";
                serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));
                RS485_ADDR_NUM = RS485ChoiceBox.getValue().replaceAll("[^0-9]", "");
                String msg = "10 02 "+String.format("%02x", RS485_ADDR_NUM)+" 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                String receivedMsg = hexMsgTransceiver.sendMessages(msg);
                if (!receivedMsg.split(" ")[5].equals("6A")){
                    CONNECT_TYPE = "none";
                }
            } else {
                CONNECT_TYPE = "serial";
                serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));
                String receiveMsg = hexMsgTransceiver.sendMessages("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03");
                if (!receiveMsg.split(" ")[5].equals("6A")){
                    CONNECT_TYPE = "none";
                }
            }
        } else if (communicationGroup.getSelectedToggle().equals(clientTCPRadioBtn)) {
            CONNECT_TYPE = "TCP";
            connectClientTCP();
            tcpManager.connect(tcpManager.getIP(), tcpManager.getPORT());
        } else if (communicationGroup.getSelectedToggle().equals(serverTCPRadioBtn)) {
            CONNECT_TYPE = "TCP";
            connectServerTCP();
            tcpManager.connect(tcpManager.getIP(), tcpManager.getPORT());
        } else {
            CONNECT_TYPE = "UDP";
            connectUDP();
            udpManager.connect(udpManager.getIP(), udpManager.getPORT());
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
            clientIPAddress.setDisable(false);
            clientIPPort.setDisable(false);
        } else {
            clientIPAddress.setDisable(true);
            clientIPPort.setDisable(true);
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
