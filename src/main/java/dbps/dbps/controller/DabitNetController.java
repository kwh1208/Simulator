package dbps.dbps.controller;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.Simulator;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DabitNetController {
    @FXML
    public ChoiceBox<String> networkSelection;
    public RadioButton isSerial;
    public RadioButton isUDP;
    public ChoiceBox<String> serialPortChoiceBox;
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
    public TextField versionInfo;
    public TextField DBCommunication;

    public AnchorPane dabitNetAP;
    public TextField keepAlive;


    SerialPortManager serialPortManager;
    UDPManager udpManager;
    Map<String, DB300IPPort> db300InfoList;

    private CommunicationSettingController communicationSettingController;


    public void setMainController(CommunicationSettingController mainController) {
        this.communicationSettingController = mainController;
    }

    @FXML
    public void initialize() {
        serialPortManager = SerialPortManager.getManager();
        udpManager = UDPManager.getUDPManager();
        db300InfoList = new HashMap<>();
        if (!serialPortChoiceBox.getItems().isEmpty()) {
            serialPortChoiceBox.setValue(serialPortChoiceBox.getItems().get(0));
        }

        getSerialPortList();

        serialPortChoiceBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());

        dbList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            DB300IPPort selectedItem = db300InfoList.get(dbList.getSelectionModel().getSelectedItem());
            changeUI(selectedItem);
        });

        dabitNetAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/dabitNet.css").toExternalForm());
    }


    @FXML
    public void search() throws ExecutionException, InterruptedException, IOException {
        dbList.getItems().clear();
        db300InfoList.clear();
        if (isSerial.isSelected()) {//시리얼
            Task<String> search = serialPortManager.send300MsgAndGetMsg("++SET++![SEARCHING DIBD  B\r\n!]", serialPortChoiceBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            Thread searchTask = new Thread(search);
            searchTask.start();

            String result = search.get();
            get300IPPort(result);
        } else {
            udpManager.connect300();
            Task<String> search = udpManager.send300MsgAndGetMsgByte("SEARCHING DIBD  B\r\n".getBytes());
            Thread searchTask = new Thread(search);
            searchTask.start();
            String result = search.get();
            //한빛전자 - 모듈 구매 문의 2*6 자석 포함 10개

            get300IPPort(result);
        }
        for (Map.Entry<String, DB300IPPort> db300Infos : db300InfoList.entrySet()) {
            DB300IPPort db300IPPort = db300Infos.getValue();
            dbList.getItems().add(db300IPPort.getMacAddress());
        }
    }

    @FXML
    public void set() throws ExecutionException, InterruptedException, IOException {
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
        newDB300.setWifiSSID(wifiSSID.getText());
        newDB300.setWifiPW(wifiPW.getText());
        newDB300.setHeartbeat(keepAlive.getText());
        newDB300.setStation(wifiStation.isSelected());
        byte[] sendByte;

        if (isSerial.isSelected()) {

            sendByte = getBytesSerial(newDB300);

            Task<Void> set = serialPortManager.send300ByteMsg(sendByte, serialPortChoiceBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            new Thread(set).start();
        } else {
            sendByte = getBytesUDP(newDB300);

            Task<String> set = udpManager.send300MsgAndGetMsgByte(sendByte);

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

        tmp = newDB300.getWifiSSID().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        if (newDB300.getWifiPW()!=null) tmp = newDB300.getWifiPW().getBytes();
        else tmp = new byte[]{0x20};
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = new byte[]{0x33, (byte) (newDB300.isStation() ? 0x31 : 0x30)};
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

        tmp = String.format("%3d", newDB300.heartbeat).getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 37;

        tmp = newDB300.getWifiSSID().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = newDB300.getWifiPW().getBytes();
        System.arraycopy(tmp, 0, sendByte, destPos, Math.min(tmp.length, 20));
        destPos += 22;

        tmp = new byte[]{0x33, (byte) (newDB300.isStation() ? 0x31 : 0x30)};
        System.arraycopy(tmp, 0, sendByte, destPos, tmp.length);
        sendByte[229] = 0x0D;
        sendByte[230] = 0x0A;
        sendByte[231] = 0x21;
        sendByte[232] = 0x5D;
        return sendByte;
    }

    @FXML
    public void add(MouseEvent mouseEvent) {
        communicationSettingController.addIPAndPort(clientIPTF.getText(), clientPortTF.getText());

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reboot(MouseEvent mouseEvent) {
        if (isSerial.isSelected()) {
            Task<String> reboot = serialPortManager.send300MsgAndGetMsg("++SET++![RESET  " + dbList.getSelectionModel().getSelectedItem() + "\r\n!]", serialPortChoiceBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            new Thread(reboot).start();
        }
        else {
            Task<String> reboot = udpManager.send300MsgAndGetMsgByte(("RESET  " + dbList.getSelectionModel().getSelectedItem() + "\r\n").getBytes());

            new Thread(reboot).start();
        }
    }


    @FXML
    public void close(MouseEvent mouseEvent) {
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
    public void read(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {
        if (isSerial.isSelected()) {
            Task<String> read = serialPortManager.send300MsgAndGetMsg("++SET++![INFO_R  " + dbList.getSelectionModel().getSelectedItem() + "\r\n!]", serialPortChoiceBox.getValue(), Integer.parseInt(baudRateChoiceBox.getValue()));

            Thread readTask = new Thread(read);
            readTask.start();

            get300Info(read.get());
        } else {
            Task<String> read = udpManager.send300MsgAndGetMsgByte(("INFO_R  " + dbList.getSelectionModel().getSelectedItem() + "\r\n").getBytes());

            Thread readTask = new Thread(read);
            readTask.start();

            get300Info(read.get());
        }
    }

    /**
     * INFO_R
     * 54-FF-82-80-14-57
     * 014!]1003010
     * WB V2.0.5 2024-09-16
     */

    /**
     * ++SET++![INFO_W  54-FF-82-80-14-57  014!]1003010
     * !]
     */

    public void write(MouseEvent mouseEvent) {
        if (isSerial.isSelected()) {
            Task<String> write = serialPortManager.send300MsgAndGetMsg("++SET++![INFO_W  "
                            + dbList.getSelectionModel().getSelectedItem() + "  "
                            + debugging.getItems().indexOf(debugging.getValue())
                            + connectPort.getItems().indexOf(connectPort.getValue())
                            + baudRate.getItems().indexOf(baudRate.getValue())
                            + ascFirst.getText()
                            + ascSecond.getText()
                            + hexFirst.getText()
                            + hexSecond.getText()
                            + String.format("%03d", Integer.parseInt(timeOut.getText()))
                            + "\r\n!]",
                            serialPortChoiceBox.getValue(),
                    Integer.parseInt(baudRateChoiceBox.getValue()));

            new Thread(write).start();
        }
        else {
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
                    + "\r\n!]").getBytes());

            new Thread(write).start();
        }
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


    private void get300IPPort(String responseData) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(responseData));
        String line;
        int idx = 0;
        DB300IPPort readDB300IPPort = null;
        while ((line = reader.readLine()) != null) {
            idx++;
            line = line.trim();
            if (line.equals("DIBD")) {
                idx = 0;
                if (readDB300IPPort!=null){
                    db300InfoList.put(readDB300IPPort.getMacAddress(), readDB300IPPort);
                }
                readDB300IPPort = new DB300IPPort();
                continue;
            }
            switch (idx) {
                case 1:
                    readDB300IPPort.setMacAddress(line);
                    break;
                case 2:
                    readDB300IPPort.setClientIP(line);
                    break;
                case 3:
                    readDB300IPPort.setClientSubnetMask(line);
                    break;
                case 4:
                    readDB300IPPort.setClientGateway(line);
                    break;
                case 5:
                    readDB300IPPort.setClientPort(line);
                    break;
                case 6:
                    readDB300IPPort.setIpStatic(line.equals("30"));
                    break;
                case 7:
                    readDB300IPPort.setServerIP(line);
                    break;
                case 8:
                    readDB300IPPort.setServerPort(line);
                    break;
                case 9:
                    readDB300IPPort.setServerMode(line.equals("31"));
                    break;
                case 10:
                    readDB300IPPort.setName(line);
                    break;
                case 11:
                    readDB300IPPort.setHeartbeat(line);
                case 13:
                    readDB300IPPort.setWifiSSID(line);
                    break;
                case 14:
                    readDB300IPPort.setWifiPW(line);
                    break;
                case 15:
                    readDB300IPPort.setStation(line.equals("30"));
                    break;
                default:
                    break;
            }
        }db300InfoList.put(readDB300IPPort.getMacAddress(), readDB300IPPort);
    }


    //입력받을거.
    //이더넷 -> wifi 5107, 이더넷 -> 이더넷 5108


    private void getSerialPortList() {
        SerialPort[] ports = SerialPort.getCommPorts();
        ObservableList<String> items = serialPortChoiceBox.getItems();

        String currentSelection = serialPortChoiceBox.getValue(); // 현재 선택된 값 저장


        List<String> portNames = Arrays.stream(ports)
                .map(SerialPort::getSystemPortName)
                .sorted(Comparator.comparingInt(this::extractPortNumber)) // 포트 번호 기준으로 정렬
                .toList();

        items.clear();
        // 정렬된 포트 이름을 items에 추가
        items.addAll(portNames);

        // ComboBox에 정렬된 목록 설정
        serialPortChoiceBox.setItems(items);

        // 기존에 선택한 값이 여전히 리스트에 있다면, 다시 선택해줍니다.
        if (currentSelection != null && items.contains(currentSelection)) {
            serialPortChoiceBox.setValue(currentSelection);
        }
    }

    private int extractPortNumber(String portName) {
        return Integer.parseInt(portName.replaceAll("[^0-9]", ""));
    }


    //UI 변경하기
    private void changeUI(DB300IPPort db300IPPort) {
        clientIPTF.setText(db300IPPort.getClientIP());
        clientGatewayTF.setText(db300IPPort.getClientGateway());
        clientPortTF.setText(db300IPPort.getClientPort());
        clientSubnetMaskTF.setText(db300IPPort.getClientSubnetMask());
        serverIPTF.setText(db300IPPort.getServerIP());
        serverPortTF.setText(db300IPPort.getServerPort());
        boardNameTF.setText(db300IPPort.getName());
        wifiSSID.setText(db300IPPort.getWifiSSID());
        wifiPW.setText(db300IPPort.getWifiPW());
        staticRadio.setSelected(db300IPPort.isIpStatic());
        isClient.setSelected(!db300IPPort.isServerMode());
        wifiStation.setSelected(db300IPPort.isStation());
        keepAlive.setText(db300IPPort.heartbeat);
    }


    public class DB300IPPort {
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

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        // Getter and Setter for clientIP
        public String getClientIP() {
            return clientIP;
        }

        public void setClientIP(String clientIP) {
            this.clientIP = clientIP;
        }

        // Getter and Setter for clientPort
        public String getClientPort() {
            return clientPort;
        }

        public void setClientPort(String clientPort) {
            this.clientPort = clientPort;
        }

        // Getter and Setter for clientSubnetMask
        public String getClientSubnetMask() {
            return clientSubnetMask;
        }

        public void setClientSubnetMask(String clientSubnetMask) {
            this.clientSubnetMask = clientSubnetMask;
        }

        // Getter and Setter for clientGateway
        public String getClientGateway() {
            return clientGateway;
        }

        public void setClientGateway(String clientGateway) {
            this.clientGateway = clientGateway;
        }

        // Getter and Setter for serverIP
        public String getServerIP() {
            return serverIP;
        }

        public void setServerIP(String serverIP) {
            this.serverIP = serverIP;
        }

        // Getter and Setter for serverPort
        public String getServerPort() {
            return serverPort;
        }

        public void setServerPort(String serverPort) {
            this.serverPort = serverPort;
        }

        // Getter and Setter for ipStatic
        public boolean isIpStatic() {
            return ipStatic;
        }

        public void setIpStatic(boolean ipStatic) {
            this.ipStatic = ipStatic;
        }

        // Getter and Setter for serverMode
        public boolean isServerMode() {
            return serverMode;
        }

        public void setServerMode(boolean serverMode) {
            this.serverMode = serverMode;
        }

        // Getter and Setter for name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Getter and Setter for wifiSSID
        public String getWifiSSID() {
            return wifiSSID;
        }

        public void setWifiSSID(String wifiSSID) {
            this.wifiSSID = wifiSSID;
        }

        // Getter and Setter for wifiPW
        public String getWifiPW() {
            return wifiPW;
        }

        public void setWifiPW(String wifiPW) {
            this.wifiPW = wifiPW;
        }

        // Getter and Setter for heartbeat
        public String getHeartbeat() {
            return heartbeat;
        }

        public void setHeartbeat(String heartbeat) {
            this.heartbeat = heartbeat;
        }

        // Getter and Setter for isStation
        public boolean isStation() {
            return isStation;
        }

        public void setStation(boolean isStation) {
            this.isStation = isStation;
        }


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
    }

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

        public String getCommunication() {
            return communication;
        }

        public void setCommunication(String communication) {
            this.communication = communication;
        }

        // Getter and Setter for macAddress
        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        // Getter and Setter for debugging
        public boolean isDebugging() {
            return debugging;
        }

        public void setDebugging(boolean debugging) {
            this.debugging = debugging;
        }

        // Getter and Setter for connectedTTL
        public boolean isConnectedTTL() {
            return connectedTTL;
        }

        public void setConnectedTTL(boolean connectedTTL) {
            this.connectedTTL = connectedTTL;
        }

        // Getter and Setter for baudRate
        public Integer getBaudRate() {
            return baudRate;
        }

        public void setBaudRate(Integer baudRate) {
            this.baudRate = baudRate;
        }

        // Getter and Setter for asc_1
        public String getAsc_1() {
            return asc_1;
        }

        public void setAsc_1(String asc_1) {
            this.asc_1 = asc_1;
        }

        // Getter and Setter for asc_2
        public String getAsc_2() {
            return asc_2;
        }

        public void setAsc_2(String asc_2) {
            this.asc_2 = asc_2;
        }

        // Getter and Setter for hex_1
        public String getHex_1() {
            return hex_1;
        }

        public void setHex_1(String hex_1) {
            this.hex_1 = hex_1;
        }

        // Getter and Setter for hex_2
        public String getHex_2() {
            return hex_2;
        }

        public void setHex_2(String hex_2) {
            this.hex_2 = hex_2;
        }

        // Getter and Setter for timeout
        public String getTimeout() {
            return timeout;
        }

        public void setTimeout(String timeout) {
            this.timeout = timeout;
        }

        // Getter and Setter for version
        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}