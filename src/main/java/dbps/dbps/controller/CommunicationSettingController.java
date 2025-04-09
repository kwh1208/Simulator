package dbps.dbps.controller;


import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.Constants;
import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

import static dbps.dbps.Constants.*;

public class CommunicationSettingController {

    @FXML
    public Button shutConnect;
    public Button keepOpenBtn;
    public TextField pingTextField;
    public Button pingTestBtn;
    SerialPortManager serialPortManager;
    TCPManager tcpManager;

    HexMsgTransceiver hexMsgTransceiver;
    UDPManager udpManager;
    ConfigService configService;
    LogService logService;
    ServerTCPManager serverTCPManager;
    ResourceBundle bundle;

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
    private ComboBox<String> serialSpeedComboBox;

    @FXML
    private CheckBox RS485ChkBox;

    @FXML
    private ComboBox<String> RS485ComboBox;

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
    private ComboBox<String> serverIPAddress;

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
    private ComboBox<String> delayTime;

    @FXML
    private Button connect;

    @FXML
    private ProgressIndicator progressIndicator;

    public void showLoading() {
        Platform.runLater(() -> progressIndicator.setVisible(true));
    }

    public void hideLoading() {
        Platform.runLater(() -> progressIndicator.setVisible(false));
    }

    ToggleGroup communicationGroup;

    //초기화
    @FXML
    private void initialize() {
        communicationSettingAP.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode()== KeyCode.F10){
                FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/asCheck.fxml"));
                fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Stage modalStage = new Stage();
                modalStage.setTitle("테스트중");
                modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.jpg")));
                modalStage.initModality(Modality.APPLICATION_MODAL);

                Stage parentStage = (Stage) communicationSettingAP.getScene().getWindow();
                modalStage.initOwner(parentStage);

                Scene scene = new Scene(root);
                modalStage.setScene(scene);
                modalStage.setResizable(false);
                modalStage.show();
            }
        });

        bundle=ResourceManager.getInstance().getBundle();
        serialPortManager = SerialPortManager.getManager();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        tcpManager = TCPManager.getManager();
        udpManager = UDPManager.getUDPManager();
        configService = ConfigService.getInstance();
        serverTCPManager = ServerTCPManager.getInstance();
        logService = LogService.getLogService();

        //토글버튼 그룹화
        communicationGroup = new ToggleGroup();
        serialRadioBtn.setToggleGroup(communicationGroup);
        clientTCPRadioBtn.setToggleGroup(communicationGroup);
        serverTCPRadioBtn.setToggleGroup(communicationGroup);
        UDPRadioBtn.setToggleGroup(communicationGroup);
        serverIPPort.setText(configService.getProperty("serverTCPPort"));

        communicationSettingAP.setOnKeyPressed(new Constants.EscapeKeyEventHandler());

        switch (CONNECT_TYPE) {
            case "serial":
                communicationGroup.selectToggle(serialRadioBtn);  // Serial 버튼 선택
                serialRadioToggle(true);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                connect.setText(bundle.getString("openPort"));
                shutConnect.setText(bundle.getString("closePort"));
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
            case "rs485":
                communicationGroup.selectToggle(serialRadioBtn);
                serialRadioToggle(true);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                connect.setText(bundle.getString("openPort"));
                shutConnect.setText(bundle.getString("closePort"));
                RS485ChkBox.setSelected(true);
                RS485ComboBox.setVisible(true);
                RS485ComboBox.setValue("Dabit "+String.format("%02d", RS485_ADDR_NUM));
                isRS=true;
                break;
            default:
                communicationGroup.selectToggle(null);
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                break;
        }

        UDPIPAddress.setText(configService.getProperty("UDPAddr"));
        UDPIPPort.setText(configService.getProperty("UDPPort"));

        communicationGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton selectedRadioButton = (RadioButton) newValue;
            if (selectedRadioButton.equals(serialRadioBtn)) {
                serialRadioToggle(true);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                CONNECT_TYPE = "serial";
                configService.setProperty("connectType", "serial");
                connect.setText(bundle.getString("openPort"));
                shutConnect.setText(bundle.getString("closePort"));
            } else if (selectedRadioButton.equals(clientTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(true);
                serverTCPRadioToggle(false);
                UDPRadioToggle(false);
                CONNECT_TYPE = "clientTCP";
                configService.setProperty("connectType", "clientTCP");
                RS485ChkBox.setSelected(false);
                RS485ComboBox.setVisible(false);
            } else if (selectedRadioButton.equals(serverTCPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(true);
                UDPRadioToggle(false);
                RS485ChkBox.setSelected(false);
                RS485ComboBox.setVisible(false);
                configService.setProperty("connectType", "serverTCP");
                CONNECT_TYPE = "serverTCP";
            } else if (selectedRadioButton.equals(UDPRadioBtn)) {
                serialRadioToggle(false);
                clientTCPRadioToggle(false);
                serverTCPRadioToggle(false);
                UDPRadioToggle(true);
                RS485ChkBox.setSelected(false);
                RS485ComboBox.setVisible(false);
                CONNECT_TYPE = "UDP";
                configService.setProperty("connectType", "UDP");
            }
        });

        for (int i = 0; i < 32; i++) {
            RS485ComboBox.getItems().add("Addr "+i);
        }
        RS485ComboBox.setValue("Addr "+RS485_ADDR_NUM);

        if (!serialPortComboBox.getItems().isEmpty()) {
            serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));
        }

        serialPortComboBox.setValue(configService.getProperty("openPortName"));

        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            serialPortComboBox.setValue(newValue);
            OPEN_PORT_NAME = serialPortComboBox.getValue();
            configService.setProperty("openPortName", newValue);
        });

        serialPortComboBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());

        serialSpeedComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            SERIAL_BAUDRATE = Integer.parseInt(serialSpeedComboBox.getValue());
            configService.setProperty("serialSpeed", newValue);
        }));

        RS485ChkBox.selectedProperty().addListener((observableValue, oldValue, newValue) ->
                {
                    RS485ComboBox.setVisible(newValue);
                    isRS = newValue;
                    if (newValue) CONNECT_TYPE = "rs485";
                }
        );

        RS485ComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            RS485_ADDR_NUM = Integer.parseInt(newValue.replaceAll("[^0-9]", ""));
            configService.setProperty("RS485_ADDR_NUM", String.valueOf(RS485_ADDR_NUM));
        }));

        clientIPAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            tcpManager.setIP(newValue);
            configService.setProperty("clientTCPAddr", newValue);
        });

        clientIPPort.textProperty().addListener((observable, oldValue, newValue) -> {
            int port = Integer.parseInt(newValue);
            tcpManager.setPORT(port);
            configService.setProperty("clientTCPPort", String.valueOf(port));
        });

        serverIPAddress.valueProperty().addListener((observable, oldValue, newValue)->{
            hostIP = newValue;
            configService.setProperty("serverTCPAddr", hostIP);
        });

        serverIPPort.textProperty().addListener((observable, oldValue, newValue)->{
            serverTCPPort = Integer.parseInt(serverIPPort.getText());
            configService.setProperty("serverTCPPort", String.valueOf(serverTCPPort));
        });

        UDPIPAddress.textProperty().addListener((observable, oldValue, newValue)->{
            udpManager.setIP(newValue);
            configService.setProperty("UDPAddr", newValue);
        });

        UDPIPPort.textProperty().addListener(((observable, oldValue, newValue) -> {
            int port = Integer.parseInt(UDPIPPort.getText());
            udpManager.setPORT(port);
            configService.setProperty("UDPPort", String.valueOf(port));
        }));

        //응답시간 변경
        delayTime.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                RESPONSE_LATENCY = Integer.parseInt(newValue);
                configService.setProperty("RESPONSE_LATENCY", String.valueOf(RESPONSE_LATENCY));
            }
        });

        delayTime.setValue(configService.getProperty("RESPONSE_LATENCY"));

        communicationSettingAP.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/communicationSetting.css")).toExternalForm());

        if (isRS){
            RS485ChkBox.setSelected(true);
        }

        getServerIP();

        clientIPAddress.setText(configService.getProperty("clientTCPAddr"));
        clientIPPort.setText(configService.getProperty("clientTCPPort"));

    }

    //사용가능한 포트 가져오기
    private void getSerialPortList() {
        String selectedValue = configService.getProperty("openPortName");
        List<String> portNames = Arrays.stream(SerialPort.getCommPorts())
                .filter(port -> !port.getPortDescription().toLowerCase().contains("bluetooth"))
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
        Task<Integer> findSpeedTask = serialPortManager.findSpeedTask();

        findSpeedTask.setOnSucceeded(event -> {
            Integer speed = findSpeedTask.getValue();
            if (speed != null && speed > 0) {
                SERIAL_BAUDRATE = speed;
                serialSpeedComboBox.setValue(String.valueOf(speed));
                configService.setProperty("serialSpeed", String.valueOf(speed));
            }
        });

        new Thread(findSpeedTask).start();
    }

    //포트열기, 접속하기


    private void connectServerTCP() {
        hostIP = serverIPAddress.getValue();
        serverTCPPort = Integer.parseInt(serverIPPort.getText());

        new Thread(() -> serverTCPManager.connect(hostIP, serverTCPPort)).start();

        configService.setProperty("serverTCPPort", String.valueOf(serverTCPPort));
    }



    private void connectClientTCP() {
        String IPAddress = clientIPAddress.getText();
        int port = Integer.parseInt(clientIPPort.getText());
        tcpManager.setIP(IPAddress);
        tcpManager.setPORT(port);
        configService.setProperty("clientTCPAddr", IPAddress);
        configService.setProperty("clientTCPPort", String.valueOf(port));
        tcpManager.connect(IPAddress, port);

        TCP_IP = IPAddress;
        TCP_PORT = port;
    }

    private void changeConnectType(){
        if (communicationGroup.getSelectedToggle().equals(serialRadioBtn)) {
            CONNECT_TYPE="serial";
            if (RS485ChkBox.isSelected()) {
                CONNECT_TYPE="rs485";
            }
        }
        else if (communicationGroup.getSelectedToggle().equals(clientTCPRadioBtn))
            CONNECT_TYPE="clientTCP";
        else if (communicationGroup.getSelectedToggle().equals(serverTCPRadioBtn))
            CONNECT_TYPE="serverTCP";
        else if (communicationGroup.getSelectedToggle().equals(UDPRadioBtn)){
            CONNECT_TYPE="UDP";
        }
    }

    @FXML
    public void closeSerialPort() {
        changeConnectType();
        if (communicationGroup.getSelectedToggle().equals(serialRadioBtn)) {
            closePort(serialPortComboBox.getValue());
        }
        else if (communicationGroup.getSelectedToggle().equals(clientTCPRadioBtn))
            tcpManager.disconnect();
        else if (communicationGroup.getSelectedToggle().equals(serverTCPRadioBtn))
            serverTCPManager.disconnect();
        else if (communicationGroup.getSelectedToggle().equals(UDPRadioBtn)){
            udpManager.disconnect();
        }
    }

    @FXML
    public void openSerialPort(){
        changeConnectType();
        if (communicationGroup.getSelectedToggle().equals(serialRadioBtn)) {
            openPort(serialPortComboBox.getValue());
        }
        else if (communicationGroup.getSelectedToggle().equals(clientTCPRadioBtn))
            connectClientTCP();
        else if (communicationGroup.getSelectedToggle().equals(serverTCPRadioBtn))
            connectServerTCP();
        else if (communicationGroup.getSelectedToggle().equals(UDPRadioBtn)){
            udpManager.connect(UDPIPAddress.getText(), Integer.parseInt(UDPIPPort.getText()));
        }
    }


    //포트열기
    public void openPort(String portName){
        serialPortManager.openPort(portName, Integer.parseInt(serialSpeedComboBox.getValue()));
        OPEN_PORT_NAME = portName;
    }

    //포트닫기
    public void closePort(String portName) {
        serialPortManager.closePort(portName);
    }

    //다빛넷 열기
    public void openDabitNet(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/dabitNet.fxml"));
        Parent root = fxmlLoader.load();

        DabitNetController dabitNetController = fxmlLoader.getController();
        dabitNetController.setMainController(this);

        Stage modalStage = new Stage();
        modalStage.setTitle("dbNet");
        modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.jpg")));

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.setOnShown(event -> {
            // 부모 창 위치와 크기 가져오기
            double parentX = parentStage.getX();
            double parentY = parentStage.getY();
            double parentWidth = parentStage.getWidth();

            // 모달 창 크기 계산
            double modalWidth = modalStage.getWidth();

            // 위치 계산
            double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙

            // 위치 설정
            modalStage.setX(modalX);
            modalStage.setY(parentY);
        });

        modalStage.showAndWait();
    }

    public void addIPAndPort(String ip, String port, boolean selected){
        if (selected){
            serverIPPort.setText(port);
            serverTCPRadioBtn.setSelected(true);
            UDPIPPort.setText(port);
        }
        else {
            clientIPAddress.setText(ip);
            clientIPPort.setText(port);
            clientTCPRadioBtn.setSelected(true);
        }

        UDPIPAddress.setText(ip);
    }

    //블루투스 열기
    public void openBluetooth(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/blueTooth.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("블루투스 설정");
        modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.jpg")));

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.setOnShown(event -> {
            // 부모 창 위치와 크기 가져오기
            double parentX = parentStage.getX();
            double parentY = parentStage.getY();
            double parentWidth = parentStage.getWidth();

            // 모달 창 크기 계산
            double modalWidth = modalStage.getWidth();

            // 위치 계산
            double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙

            // 위치 설정
            modalStage.setX(modalX);
            modalStage.setY(parentY);
        });

        modalStage.showAndWait();
    }

    //컨트롤러 연결하고 확인신호 보내기
    @FXML
    public void controllerConnect() {
        changeConnectType();
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> showLoading()); // 로딩 애니메이션 시작
                try {
                    // 시리얼일 때
                    if (communicationGroup.getSelectedToggle().equals(serialRadioBtn) && RS485ChkBox.isSelected()) {
                        String msg = "10 02 " + convertRS485AddrASCii() + " 00 0B 6A 30 31 32 33 34 35 36 37 38 39 10 03";
                        hexMsgTransceiver.sendMessages(msg, progressIndicator);
                    }

                    hexMsgTransceiver.sendByteMessages(CONNECT_START, progressIndicator);
                }
                 finally {
                    Platform.runLater(() -> hideLoading()); // 작업 완료 후 로딩 애니메이션 종료
                }
                return null;
            }
        };

        // 비동기 실행
        new Thread(task).start();
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
        toggleComponents(isSerial, serialPortComboBox, serialSpeedComboBox, RS485ChkBox, findSpeedBtn, openDeviceManagerBtn);
    }

    private void clientTCPRadioToggle(boolean isClient) {
        toggleComponents(isClient, clientIPAddress, clientIPPort, pingTextField, pingTestBtn);
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
        } catch (SocketException ignored) {

        }

        // 첫 번째 IP 주소 선택 (선택사항)
        if (!ipAddresses.isEmpty()) {
            serverIPAddress.getSelectionModel().select(0);
        }
    }


    public void keepOpen() {
        KEEP_OPEN = !KEEP_OPEN;
        if (KEEP_OPEN) {
            logService.updateInfoLog(bundle.getString("portAlwaysOpen"));
            keepOpenBtn.setText("유지 해제");
            openSerialPort();
        } else {
            logService.updateInfoLog(bundle.getString("portOpenClose"));
            keepOpenBtn.setText("포트 유지");
            closeSerialPort();
        }
    }

    public void pingTest() {
        pingTextField.setText("");
        logService.updateInfoLog("핑 테스트를 시작합니다. 4개의 핑을 보냅니다. 잠시만 기다려주세요.");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String ip = clientIPAddress.getText();
                if(ip == null || ip.isEmpty()){
                    updateMessage("IP 주소를 입력하세요.");
                    return null;
                }
                InetAddress address = InetAddress.getByName(ip);
                int successCnt = 0;
                long totalTime = 0;
                for (int i = 1; i <= 4; i++) {
                    long startTime = System.currentTimeMillis();
                    boolean reachable = address.isReachable(RESPONSE_LATENCY * 1000);
                    long endTime = System.currentTimeMillis();
                    long rtt = endTime - startTime;

                    if (reachable) {
                        logService.updateInfoLog((i)+"번째 패킷 응답 시간 : " +rtt+"ms");
                        successCnt++;
                        totalTime += rtt;
                    } else {
                        logService.updateInfoLog((i)+"번째 패킷 손실되었습니다.");
                    }
                }
                double avgTime = (double) totalTime / successCnt;
                String result = String.format("%.1fms (%d/4)", avgTime, successCnt);
                Platform.runLater(() -> {pingTextField.setText(result);logService.updateInfoLog("핑 테스트가 완료되었습니다.");});
                return null;
            }
        };

        new Thread(task).start();
    }
}
