package dbps.dbps.controller;


import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
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

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import static dbps.dbps.Constants.*;

public class CommunicationSettingController {


    public Button shutConnect;
    SerialPortManager serialPortManager;
    TCPManager tcpManager;

    HexMsgTransceiver hexMsgTransceiver;
    UDPManager udpManager;
    ConfigService configService;
    LogService logService;
    ServerTCPManager serverTCPManager;

    @FXML
    private AnchorPane communicationSettingAP;

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
    private ChoiceBox<String> serverIPAddress;

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
        serverTCPManager = ServerTCPManager.getInstance();
        logService = LogService.getLogService();


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
                break;
            case "serverTCP":
                communicationGroup.selectToggle(serverTCPRadioBtn);  // Server TCP 버튼 선택
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(true);
                UDPRadioToggle(false);
                break;
            case "UDP":
                communicationGroup.selectToggle(UDPRadioBtn);  // UDP 버튼 선택
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(true);
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
                CONNECT_TYPE = "serial";
                configService.setProperty("connectType", "serial");
                connect.setText("포트열기");
                shutConnect.setText("포트닫기");
            } else if (selectedRadioButton.equals(clientTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(true);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                CONNECT_TYPE = "clientTCP";
                configService.setProperty("connectType", "clientTCP");
            } else if (selectedRadioButton.equals(serverTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(true);
                UDPRadioToggle(false);
                CONNECT_TYPE = "serverTCP";
            } else  {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(true);
                CONNECT_TYPE = "UDP";
            }
        });

        getSerialPortList();

        if (!serialPortComboBox.getItems().isEmpty()) {
            serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));
        }

        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            serialPortComboBox.setValue(newValue);
            configService.setProperty("openPortName", newValue);
        });
        serialPortComboBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());

        RS485ChkBox.selectedProperty().addListener((observableValue, oldValue, newValue) ->
                {RS485ChoiceBox.setVisible(newValue);
                    if (newValue){
                        isRS = true;
                    }
                    else {
                        isRS = false;
                    }
                }
        );

        //응답시간 변경
        delayTime.selectionModelProperty().addListener((observableValue, oldValue, newValue) -> RESPONSE_LATENCY = Integer.parseInt(delayTime.getValue()));

        communicationSettingAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/communicationSetting.css").toExternalForm());

        if (isRS){
            RS485ChkBox.setSelected(true);
        }

        getServerIP();
    }

    //사용가능한 포트 가져오기
    private void getSerialPortList() {
        List<String> portNames = Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .sorted(Comparator.comparingInt(this::extractPortNumber))
                .toList();

        serialPortComboBox.getItems().setAll(portNames);
        serialPortComboBox.getSelectionModel().selectFirst();
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
        int port = Integer.parseInt(serverIPPort.getText());

        serverTCPManager.connect(port);
        serverTCPPort = port;
        configService.setProperty("serverTCPPort", String.valueOf(port));

        logService.updateInfoLog("Port :"+port+"가 열렸습니다.");
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


        logService.updateInfoLog("IP :"+IPAddress+" Port :"+port+"가 열렸습니다.");
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
    public void openDabitNet(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/dabitNet.fxml"));
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("DabitNet");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    //블루투스 열기
    public void openBluetooth(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/blueTooth.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
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
    public void controllerConnect() throws IOException {
        //시리얼 일때
        if (communicationGroup.getSelectedToggle().equals(serialRadioBtn)){
            if (RS485ChkBox.isSelected()){
                CONNECT_TYPE = "rs485";
                serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));
                RS485_ADDR_NUM = Integer.parseInt(RS485ChoiceBox.getValue().replaceAll("[^0-9]", ""));
                String msg = "10 02 "+convertRS485AddrASCii()+" 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                hexMsgTransceiver.sendMessages(msg);
            } else {
                CONNECT_TYPE = "serial";
                serialPortManager.openPort(serialPortComboBox.getValue(), Integer.parseInt(serialSpeedChoiceBox.getValue()));
                hexMsgTransceiver.sendMessages("10 02 00 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03");
            }
        } else if (communicationGroup.getSelectedToggle().equals(clientTCPRadioBtn)) {
            CONNECT_TYPE = "clientTCP";
            connectClientTCP();
            tcpManager.connect(tcpManager.getIP(), tcpManager.getPORT());
        } else if (communicationGroup.getSelectedToggle().equals(serverTCPRadioBtn)) {
            CONNECT_TYPE = "serverTCP";
            connectServerTCP();
            serverTCPManager.sendMsgAndGetMsgByte(CONNECT_START);
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

    private void toggleComponents(boolean enable, Control... controls) {
        for (Control control : controls) {
            control.setDisable(!enable);
        }
    }

    private void serialRadioToggle(boolean isSerial) {
        toggleComponents(isSerial, serialPortComboBox, serialSpeedChoiceBox, RS485ChkBox, findSpeedBtn, openDeviceManagerBtn);
    }

    private void clientTCPRadioToggle(boolean isClient) {
        toggleComponents(isClient, clientIPAddress, clientIPPort);
    }

    private void serverTCPRadioToggle(boolean isServer) {
        toggleComponents(isServer, serverIPAddress, serverIPPort);
    }

    private void UDPRadioToggle(boolean isUDP) {
        toggleComponents(isUDP, UDPIPAddress, UDPIPPort);
    }

    private void getServerIP() {
        // 기존 항목을 비우고 새로 추가할 IP 주소 리스트 가져오기
        ObservableList<String> ipAddresses = serverIPAddress.getItems();
        ipAddresses.clear();  // 리스트 초기화

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface network = interfaces.nextElement();

                // 네트워크 인터페이스가 활성화되고, 루프백이 아닌 경우에만 확인
                if (network.isUp() && !network.isLoopback()) {
                    Enumeration<InetAddress> addresses = network.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();

                        // IPv4 주소만 추가 (IPv6 제외)
                        if (!address.isLoopbackAddress() && address instanceof java.net.Inet4Address) {
                            String IPAddress = address.getHostAddress();
                            ipAddresses.add(IPAddress); // IP 주소를 리스트에 추가
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // 첫 번째 IP 주소 선택 (선택사항)
        if (!ipAddresses.isEmpty()) {
            serverIPAddress.getSelectionModel().select(0);
        }
    }

    public void openMqtt(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/mqtt.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("Mqtt 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }
}
