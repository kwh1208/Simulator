package dbps.dbps.controller;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.DabitNetService;
import dbps.dbps.service.LogService;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DabitNetController {

    @FXML
    public ChoiceBox<String> networkSelection;
    public RadioButton isSerial;
    public RadioButton isUDP;
    public ComboBox<String> serialPortComboBox;
    public ListView<String> dbList;
    public TextField clientIPTF;
    public TextField clientPortTF;
    public TextField clientGatewayTF;
    public TextField clientSubnetMaskTF;
    public TextField serverIPTF;
    public TextField serverPortTF;
    public RadioButton staticRadio;
    public CheckBox isClient;
    public TextField boardNameTF;
    public RadioButton wifiStation;
    public TextField wifiSSID;
    public TextField wifiPW;
    public ChoiceBox<String> debugging;
    public ChoiceBox<String> connectPort;
    public ChoiceBox<Integer> baudRate;
    public TextField ascFirst;
    public TextField ascSecond;
    public TextField hexFirst;
    public TextField hexSecond;
    public TextField timeOut;
    public ChoiceBox<String> baudRateChoiceBox;
    public Label versionInfo;
    public Label DBCommunication;
    public Label AP;

    public AnchorPane dabitNetAP;
    public TextField keepAlive;
    public RadioButton DHCPRadio;
    public RadioButton wifiAP;
    public Button searchBtn;
    public ProgressBar dbNetProgressBar;
    public Tab wifiTab;
    public Tab networkTab;
    public Tab commTab;
    public Tab db300Tab;


    ToggleGroup connectionToggleGroup = new ToggleGroup();
    ToggleGroup IPToggleGroup = new ToggleGroup();
    ToggleGroup wifiToggleGroup = new ToggleGroup();

    SerialPortManager serialPortManager;
    UDPManager udpManager;
    Map<String, DB300IPPort> db300InfoList;
    LogService logService;
    ConfigService configService;
    DabitNetService dabitNetService;

    private CommunicationSettingController communicationSettingController;


    public void setMainController(CommunicationSettingController mainController) {
        this.communicationSettingController = mainController;
    }

    @FXML
    public void initialize() {
        serialPortManager = SerialPortManager.getManager();
        udpManager = UDPManager.getUDPManager();
        db300InfoList = new HashMap<>();
        configService = ConfigService.getInstance();
        logService = LogService.getLogService();
        dabitNetService = DabitNetService.getInstance();
        if (!serialPortComboBox.getItems().isEmpty()) {
            serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));
        }
        dabitNetService.setDb300InfoList(db300InfoList);
        dabitNetService.setDbList(dbList);
        dabitNetService.setSearchBtn(searchBtn);

        getSerialPortList();


        if (!serialPortComboBox.getItems().isEmpty()) {
            serialPortComboBox.setValue(serialPortComboBox.getItems().get(0));
        }

        serialPortComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            serialPortComboBox.setValue(newValue);
        });

        serialPortComboBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());

        serialPortComboBox.setValue(configService.getProperty("openPortName"));

        dbList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null){
                clearUI();
                DB300IPPort selectedItem = db300InfoList.get(dbList.getSelectionModel().getSelectedItem());
                changeUI(selectedItem);
            }
        });



        isClient.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue){
                serverIPTF.setDisable(false);
                serverPortTF.setDisable(false);
            }
            else {
                serverIPTF.setDisable(true);
                serverPortTF.setDisable(true);
            }
                }
        );

        dabitNetAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/dabitNet.css").toExternalForm());

        isUDP.setToggleGroup(connectionToggleGroup);
        isSerial.setToggleGroup(connectionToggleGroup);

        staticRadio.setToggleGroup(IPToggleGroup);
        DHCPRadio.setToggleGroup(IPToggleGroup);

        wifiStation.setToggleGroup(wifiToggleGroup);
        wifiAP.setToggleGroup(wifiToggleGroup);


        wifiToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle)->{
            if (newToggle == null) {
                return; // 선택된 토글이 없으면 아무것도 하지 않음
            }

            if (newToggle.equals(wifiStation)) {
                wifiSSID.setLayoutX(110.0);
                wifiSSID.setPrefWidth(176);
                AP.setText("");
            } else if (newToggle.equals(wifiAP)) {
                wifiSSID.setLayoutX(143.0);
                wifiSSID.setPrefWidth(146);
                AP.setText("AP-");
            }
        });

    }

    private void clearUI() {
        clientIPTF.setText("");
        serverIPTF.setText("");
        clientPortTF.setText("");
        serverPortTF.setText("");
        clientGatewayTF.setText("");
        clientSubnetMaskTF.setText("");
        isClient.setSelected(false);
        IPToggleGroup.selectToggle(null);
        wifiToggleGroup.selectToggle(null);
        boardNameTF.setText("");
        wifiSSID.setText("");
        wifiPW.setText("");
        debugging.setValue("");
        connectPort.setValue("");
        baudRate.setValue(null);
        ascFirst.setText("");
        ascSecond.setText("");
        hexFirst.setText("");
        hexSecond.setText("");
        timeOut.setText("");
        versionInfo.setText("");
        DBCommunication.setText("");
        keepAlive.setText("");
    }

    private boolean isSearching = false;

    @FXML
    public void search() throws ExecutionException, InterruptedException, IOException {
        System.out.println(UDPManager.socketList.size());
        if (isSearching) {
            logService.errorLog("검색이 이미 진행 중입니다.");
            return;
        }
        isSearching = true;

        dbNetProgressBar.setVisible(true);

        Platform.runLater(() -> {
            clearUI();
            dbList.getItems().clear();
            db300InfoList.clear();
        });

        logService.updateInfoLog("검색을 시작합니다.");

        Task<Void> backgroundTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Task<String> sendTask;
                if (isSerial.isSelected()) { // ✅ 시리얼 통신 Task 실행
                    sendTask = serialPortManager.send300MsgAndGetMsg(
                            "++SET++![SEARCHING DIBD  B\r\n!]",
                            serialPortComboBox.getValue(),
                            Integer.parseInt(baudRateChoiceBox.getValue())
                    );
                } else {
                    if (networkSelection.getValue().equals("All")) {
                        udpManager.connect300All();
                        sendTask = udpManager.send300MsgAndGetMsgByte("SEARCHING DIBD  B\r\n".getBytes());
                    } else {
                        int port = networkSelection.getValue().equals("Ethernet") ? 5108 : 5107;
                        if (port == 5108) {
                            udpManager.connect300Ethernet(5108); // ✅ UI 스레드에서 실행하지 않음
                        } else {
                            udpManager.connect300Wifi(port);
                        }
                        sendTask = udpManager.send300MsgAndGetMsgByte("SEARCHING DIBD  B\r\n".getBytes());
                    }
                }


                sendTask.setOnSucceeded(event -> {
                    Platform.runLater(() -> {
                        searchBtn.setDisable(false);
                        dbNetProgressBar.setVisible(false);
                        logService.updateInfoLog("검색이 완료되었습니다.");
                        isSearching = false;
                        udpManager.disconnectNoLog();
                        if (dbList.getSelectionModel().getSelectedItem()==null){
                            dbList.getSelectionModel().select(0);
                        }
                    });
                });

                sendTask.setOnFailed(event -> {
                    Platform.runLater(() -> {
                        searchBtn.setDisable(false);
                        dbNetProgressBar.setVisible(false);
                        logService.updateInfoLog("검색이 실패했습니다.");
                        isSearching = false;
                        udpManager.disconnectNoLog();
                    });
                });
                new Thread(sendTask).start();


                return null;
            }
        };

        dbNetProgressBar.progressProperty().bind(backgroundTask.progressProperty());
        thread = new Thread(backgroundTask);
        thread.setDaemon(true);

        thread.start();
    }

    Thread thread;



    @FXML
    public void set() throws IOException {
        Platform.runLater(()->{
            dbNetProgressBar.setVisible(true);
        });

        DB300IPPort newDB300 = new DB300IPPort();
        newDB300.setMacAddress(dbList.getSelectionModel().getSelectedItem());
        newDB300.setClientIP(newDB300.formatInetAddress(InetAddress.getByName(clientIPTF.getText())));
        newDB300.setClientPort(String.format("%05d", Integer.parseInt(clientPortTF.getText())));
        newDB300.setClientGateway(newDB300.formatInetAddress(InetAddress.getByName(clientGatewayTF.getText())));
        newDB300.setClientSubnetMask(newDB300.formatInetAddress(InetAddress.getByName(clientSubnetMaskTF.getText())));
        newDB300.setServerIP(newDB300.formatInetAddress(InetAddress.getByName(serverIPTF.getText())));
        newDB300.setServerPort(String.format("%05d", Integer.parseInt(serverPortTF.getText())));
        newDB300.setIpStatic(staticRadio.isSelected());
        newDB300.setServerMode(!isClient.isSelected());
        newDB300.setName(boardNameTF.getText());
        newDB300.setWifiSSID(wifiSSID.getText().replaceAll("AP-", ""));
        newDB300.setWifiPW(wifiPW.getText());
        newDB300.setHeartbeat(keepAlive.getText());
        newDB300.setStation(wifiStation.isSelected());
        byte[] sendByte;

        if (isSerial.isSelected()) {

            sendByte = getBytesSerial(newDB300);

            Task<Void> set = serialPortManager.send300ByteMsg(sendByte, serialPortComboBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            set.setOnSucceeded(event->{
                Platform.runLater(() -> {
                    dbNetProgressBar.setVisible(false);
                    logService.updateInfoLog("설정이 완료되었습니다.");
                    udpManager.disconnectNoLog();
                });
            });

            set.setOnFailed(event->{
                dbNetProgressBar.setVisible(false);
                udpManager.disconnectNoLog();
            });

            new Thread(set).start();
        } else {
            sendByte = getBytesUDP(newDB300);

            Task<String> set = udpManager.send300MsgAndGetMsgByte(sendByte);

            set.setOnSucceeded(event->{
                Platform.runLater(() -> {
                    dbNetProgressBar.setVisible(false);
                    logService.updateInfoLog("설정이 완료되었습니다.");
                });
            });

            set.setOnFailed(event->{
                Platform.runLater(() -> {
                    dbNetProgressBar.setVisible(false);
                });
            });

            new Thread(set).start();
        }
    }

    private byte[] getBytesUDP(DB300IPPort newDB300) {
        byte[] sendByte = new byte[222];

        Arrays.fill(sendByte, (byte) 0x20);
        int destPos = 0;

        byte[] tmp = "SETT".getBytes();
        System.arraycopy(tmp, 0, sendByte, 0, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getMacAddress().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientIP().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientSubnetMask().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientGateway().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientPort().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = new byte[]{0x33, (byte) (newDB300.ipStatic ? 0x30 : 0x31)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getServerIP().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getServerPort().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = new byte[]{0x33, (byte) (newDB300.serverMode ? 0x31 : 0x30)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.name.getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = keepAlive.getText().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 37;

        if (newDB300.getWifiSSID() != null) tmp = newDB300.getWifiSSID().getBytes();
        else tmp = new byte[]{0x20};
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        if (newDB300.getWifiPW() != null) tmp = newDB300.getWifiPW().getBytes();
        else tmp = new byte[]{0x20};
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = new byte[]{0x33, (byte) (newDB300.isStation() ? 0x30 : 0x31)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        sendByte[220] = 0x0D;
        sendByte[221] = 0x0A;

        return sendByte;
    }

    private static byte[] getBytesSerial(DB300IPPort newDB300) {
        byte[] sendByte = new byte[233];
        Arrays.fill(sendByte, (byte) 0x20);
        int destPos = 0;

        byte[] tmp = "++SET++![SETT".getBytes();
        System.arraycopy(tmp, 0, sendByte, 0, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getMacAddress().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientIP().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientSubnetMask().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientGateway().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getClientPort().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = new byte[]{0x33, (byte) (newDB300.ipStatic ? 0x30 : 0x31)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getServerIP().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.getServerPort().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = new byte[]{0x33, (byte) (newDB300.serverMode ? 0x31 : 0x30)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        destPos += tmp.length + 2;

        tmp = newDB300.name.getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = newDB300.heartbeat.getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 37;

        tmp = newDB300.getWifiSSID().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = newDB300.getWifiPW().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = new byte[]{0x33, (byte) (newDB300.isStation() ? 0x30 : 0x31)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        sendByte[229] = 0x0D;
        sendByte[230] = 0x0A;
        sendByte[231] = 0x21;
        sendByte[232] = 0x5D;
        return sendByte;
    }

    @FXML
    public void add(MouseEvent mouseEvent) {
        if (isClient.isSelected()){
            communicationSettingController.addIPAndPort(serverIPTF.getText(), serverPortTF.getText(), isClient.isSelected());
        }
        else{
            communicationSettingController.addIPAndPort(clientIPTF.getText(), clientPortTF.getText(), isClient.isSelected());
        }
        if (thread.isAlive()){
            thread.interrupt();
        }
        udpManager.disconnectNoLog();

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reboot() {
        udpManager.disconnectNoLog();
        if (isSerial.isSelected()) {
            Task<String> reboot = serialPortManager.send300MsgAndGetMsg("++SET++![RESET  " + dbList.getSelectionModel().getSelectedItem() + "\r\n!]", serialPortComboBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            new Thread(reboot);
        } else {
            Task<String> reboot = udpManager.send300MsgAndGetMsgByte(("RESET  " + dbList.getSelectionModel().getSelectedItem() + "\r\n").getBytes());

            new Thread(reboot).start();
        }
    }


    @FXML
    public void close(MouseEvent mouseEvent) {
        if (thread!=null&&thread.isAlive()){
            thread.interrupt();
        }
        System.out.println("DabitNetController.close");
        udpManager.disconnectNoLog();
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setDefault(MouseEvent mouseEvent) {
        clientIPTF.setText("192.168.0.201");
        clientGatewayTF.setText("192.168.0.1");
        clientPortTF.setText("5000");
        clientSubnetMaskTF.setText("255.255.255.0");
    }


    @FXML
    public void read() throws ExecutionException, InterruptedException, IOException {
        Platform.runLater(()->{
            dbNetProgressBar.setVisible(true);
        });
        if (isSerial.isSelected()) {
            Task<String> read = serialPortManager.send300MsgAndGetMsg("++SET++![INFO_R  " + dbList.getSelectionModel().getSelectedItem() + "\r\n!]", serialPortComboBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            Thread readTask = new Thread(read);
            readTask.start();

            read.setOnSucceeded(event ->{
                try {
                    get300Info(read.getValue());
                    dbNetProgressBar.setVisible(false);
                    logService.updateInfoLog("정보 읽기를 완료했습니다.");
                    udpManager.disconnectNoLog();
                } catch (IOException e) {
                    dbNetProgressBar.setVisible(false);
                    udpManager.disconnectNoLog();
                }
            });

            read.setOnFailed(event ->{
                dbNetProgressBar.setVisible(false);
                udpManager.disconnectNoLog();
            });


        } else {
            System.out.println(111




            );
            Task<String> read = udpManager.send300MsgAndGetMsgByte(("INFO_R  " + dbList.getSelectionModel().getSelectedItem() + "\r\n").getBytes());

            Thread readTask = new Thread(read);
            readTask.start();

            read.setOnSucceeded(event ->{
                try {
                    get300Info(read.getValue());
                    dbNetProgressBar.setVisible(false);
                    logService.updateInfoLog("정보 읽기를 완료했습니다.");
                    udpManager.disconnectNoLog();
                } catch (IOException e) {
                    dbNetProgressBar.setVisible(false);
                    udpManager.disconnectNoLog();
                }
            });
        }
    }

    public void write() throws UnsupportedEncodingException {
        udpManager.disconnectNoLog();
        Platform.runLater(()->{
            dbNetProgressBar.setVisible(true);
        });
        if (isSerial.isSelected()) {
            String msg = "++SET++![INFO_W " + dbList.getSelectionModel().getSelectedItem() + "  "
                    + debugging.getItems().indexOf(debugging.getValue())
                    + connectPort.getItems().indexOf(connectPort.getValue())
                    + baudRate.getItems().indexOf(baudRate.getValue())
                    + ascFirst.getText() + ascSecond.getText()
                    + hexFirst.getText() + hexSecond.getText()
                    + String.format("%03d", Integer.parseInt(timeOut.getText())) + "      \r\n!]";

            Task<Void> write = serialPortManager.send300ByteMsg(msg.getBytes("MS949"),
                    serialPortComboBox.getValue(),
                    Integer.parseInt(baudRateChoiceBox.getValue()));

            new Thread(write).start();
        } else {
            Task<String> write = udpManager.send300MsgAndGetMsgByte(("INFO_W  "
                    + dbList.getSelectionModel().getSelectedItem() + "  "
                    + debugging.getItems().indexOf(debugging.getValue())
                    + connectPort.getItems().indexOf(connectPort.getValue())
                    + baudRate.getItems().indexOf(baudRate.getValue())
                    + ascFirst.getText()
                    + ascSecond.getText()
                    + hexFirst.getText()
                    + hexSecond.getText()
                    + String.format("%03d", Integer.parseInt(timeOut.getText()))
                    +"      "
                    + "\r\n!]").getBytes());

            new Thread(write).start();
        }
        Platform.runLater(()->{
            dbNetProgressBar.setVisible(false);
        });
        logService.updateInfoLog("정보 쓰기를 완료했습니다.");
    }


    private void get300Info(String responseData) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(responseData));
        String line;
        int idx = 0;
        DB300Info readDB300Info = new DB300Info();
        while ((line = br.readLine()) != null) {
            idx++;
            line = line.trim();
            if (line.equals("INFO_R")) {
                idx = 0;
                readDB300Info = new DB300Info();
                continue;
            }
            switch (idx) {
                case 1:
                    readDB300Info.setMacAddress(line);
                    break;
                case 2:
                    debugging.setValue(debugging.getItems().get(line.charAt(0) - '0'));
                    connectPort.setValue(connectPort.getItems().get(line.charAt(1) - '0'));
                    baudRate.setValue(baudRate.getItems().get(line.charAt(2) - '0'));
                    ascFirst.setText(String.valueOf(line.charAt(3)));
                    ascSecond.setText(String.valueOf(line.charAt(4)));
                    hexFirst.setText(line.substring(5, 7));
                    hexSecond.setText(line.substring(7, 9));
                    timeOut.setText(String.valueOf(Integer.parseInt(line.substring(9))));
                    break;
                case 3:
                    String[] split = line.split("(?=V)");
                    readDB300Info.setVersion(split[1]);
                    versionInfo.setText(readDB300Info.getVersion());
                    readDB300Info.setCommunication(split[0]);
                    String communication = readDB300Info.getCommunication();
                    if (communication.contains("E")) {
                        DBCommunication.setText("Ethernet");
                    }
                    if (communication.contains("W")) {
                        DBCommunication.setText("WiFi");
                    }
                    if (communication.contains("B")) {
                        DBCommunication.setText(DBCommunication.getText() + " & Bluetooth");
                    }
            }
        }
    }

    private void getSerialPortList() {
        String selectedValue = serialPortComboBox.getValue();
        List<String> portNames = Arrays.stream(SerialPort.getCommPorts())
                .map(SerialPort::getSystemPortName)
                .sorted(Comparator.comparingInt(this::extractPortNumber))
                .toList();

        serialPortComboBox.getItems().setAll(portNames);
        // 기존 선택값 복원
        if (selectedValue != null && portNames.contains(selectedValue)) {
            serialPortComboBox.setValue(selectedValue);
        } else {
            // 기존 선택값이 없거나 리스트에 없으면 첫 번째 항목 선택
            serialPortComboBox.getSelectionModel().selectFirst();
        }
    }

    private int extractPortNumber(String portName) {
        return Integer.parseInt(portName.replaceAll("[^0-9]", ""));
    }



    //UI 변경하기
    private void changeUI(DB300IPPort db300IPPort){
        clientIPTF.setText(db300IPPort.formatIPAddress(db300IPPort.getClientIP()));
        clientGatewayTF.setText(db300IPPort.formatIPAddress(db300IPPort.getClientGateway()));
        clientPortTF.setText(String.valueOf(Integer.parseInt(db300IPPort.getClientPort())));
        clientSubnetMaskTF.setText(db300IPPort.formatIPAddress(db300IPPort.getClientSubnetMask()));
        serverIPTF.setText(db300IPPort.formatIPAddress(db300IPPort.getServerIP()));
        serverPortTF.setText(String.valueOf(Integer.parseInt(db300IPPort.getServerPort())));
        boardNameTF.setText(db300IPPort.getName());
        if(db300IPPort.isStation){
            wifiSSID.setText(db300IPPort.getWifiSSID());
        }
        else if(db300IPPort.wifiPW != null) wifiSSID.setText(db300IPPort.getWifiSSID());

        wifiPW.setText(db300IPPort.getWifiPW());
        if(db300IPPort.isIpStatic()){
            staticRadio.setSelected(true);
        }
        else{
            DHCPRadio.setSelected(true);
        }
        isClient.setSelected(db300IPPort.isServerMode());
        if(db300IPPort.isStation){
            wifiStation.setSelected(true);
        }
        else if(db300IPPort.wifiPW != null) wifiAP.setSelected(true);

        keepAlive.setText(db300IPPort.heartbeat);

        if (!isClient.isSelected()) {
            serverIPTF.setDisable(true);
            serverPortTF.setDisable(true);
        } else {
            serverIPTF.setDisable(false);
            serverPortTF.setDisable(false);
        }

        if (db300IPPort.wifiPW==null){
            wifiTab.setDisable(true);
        }
        else {
            wifiTab.setDisable(false);
        }
    }

    @Setter
    @Getter
    @ToString
    public static class DB300IPPort {
        String macAddress;
        String clientIP;
        String clientPort;
        String clientSubnetMask;
        String clientGateway;
        String serverIP;
        String serverPort;
        boolean ipStatic;//true = 고정, false 변동
        boolean serverMode;//true = 서버 , false 클라이언트
        String name;
        String wifiSSID;
        String wifiPW;
        String heartbeat;
        boolean isStation; //true = sta(30), false = ap(31)
        DB300Info db300Info;


        private String formatInetAddress(InetAddress address) {

            byte[] bytes = address.getAddress();

            StringBuilder formattedIP = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int value = bytes[i] & 0xFF;

                formattedIP.append(String.format("%03d", value));
                if (i < bytes.length - 1) {
                    formattedIP.append(".");
                }
            }

            return formattedIP.toString();
        }

        public String formatIPAddress(String ip) {
            // 점(.)으로 IP 주소를 나눔
            String[] segments = ip.split("\\.");
            StringBuilder formattedIP = new StringBuilder();

            // 각 세그먼트의 앞쪽 0을 제거하고 다시 합침
            for (int i = 0; i < segments.length; i++) {
                formattedIP.append(Integer.parseInt(segments[i])); // 앞쪽 0 제거
                if (i < segments.length - 1) {
                    formattedIP.append("."); // 마지막 세그먼트를 제외하고 점(.) 추가
                }
            }

            return formattedIP.toString();
        }
    }

    @Setter
    @Getter
    public class DB300Info {
        String communication;
        String macAddress;
        boolean debugging;
        boolean connectedTTL;
        Integer baudRate;
        String asc_1;
        String asc_2;
        String hex_1;
        String hex_2;
        String timeout;
        String version;

    }
}