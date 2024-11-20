package dbps.dbps.controller;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.AsciiMsgTransceiver;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.OPEN_PORT_NAME;

public class DabitNetController {
    @FXML
    public ChoiceBox<String> networkSelection;
    public RadioButton isSerial;
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

    AsciiMsgTransceiver asciiMsgTransceiver;
    List<DB300Info> db300InfoList;

    @FXML
    public void initialize() {
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        db300InfoList = new ArrayList<>();
        if (!serialPortChoiceBox.getItems().isEmpty()) {
            serialPortChoiceBox.setValue(serialPortChoiceBox.getItems().get(0));
        }

        serialPortChoiceBox.showingProperty().addListener((observableValue, oldValue, newValue) -> getSerialPortList());
    }


    @FXML
    public void search() throws ExecutionException, InterruptedException, IOException {
        dbList.getItems().clear();
        if (isSerial.isSelected()) {//시리얼
            OPEN_PORT_NAME = serialPortChoiceBox.getValue();

            String result = asciiMsgTransceiver.sendMessages("++SET++![SEARCHING DIBD  B\r\n!]", false);
            get300Info(result);

            DB300Info now = db300InfoList.get(0);
            System.out.println(now.toString());

        } else {//udp

        }
        //데이터 UI에 반영
    }

    @FXML
    public void set(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {
        DB300Info newDB300 = new DB300Info();
        newDB300.setMacAddress(dbList.getSelectionModel().getSelectedItem());
        newDB300.setClientIP(InetAddress.getByName(clientIPTF.getText()));
        newDB300.setClientPort(String.format("%05d", Integer.parseInt(clientPortTF.getText())));
        newDB300.setClientGateway(InetAddress.getByName(clientGatewayTF.getText()));
        newDB300.setClientSubnetMask(InetAddress.getByName(clientSubnetMaskTF.getText()));
        newDB300.setServerIP(InetAddress.getByName(serverIPTF.getText()));
        newDB300.setServerPort(serverPortTF.getText());
        newDB300.setIpStatic(staticRadio.isSelected());
        newDB300.setServerMode(!isClient.isSelected());
        newDB300.setName(boardNameTF.getText());
        newDB300.setWifiSSID(wifiSSID.getText());
        newDB300.setWifiSSID(wifiPW.getText());
        newDB300.setStation(wifiStation.isSelected());
        if (isSerial.isSelected()) {
            String sendMsg = "++SET++![SETT  "+newDB300.getMacAddress()+"  "+newDB300.clientIP+"  "+newDB300.clientSubnetMask+"  "+newDB300.clientGateway+"  "+newDB300.clientPort+"  "+(newDB300.ipStatic?"30  ":"31  ")+newDB300.serverIP+" "+newDB300.serverPort+"  "+newDB300.isServerMode()+"  "+newDB300.name+"필요한만큼 빈공간"+"000"+"필요한만큼 빈공간"+wifiSSID+"빈공간"+wifiPW+"빈공간"+wifiStation+"!]";
            asciiMsgTransceiver.sendMessages(sendMsg, false);
        }
        else{

        }
    }

    @FXML
    public void read(MouseEvent mouseEvent) throws ExecutionException, InterruptedException, IOException {
        if (isSerial.isSelected()) {
            String result = asciiMsgTransceiver.sendMessages("++SET++![INFO_R  " + dbList.getSelectionModel().getSelectedItem() + "\r\n!]", false);
        }
        else {

        }
    }

    @FXML
    public void reset(MouseEvent mouseEvent) {
        if (isSerial.isSelected()) {
            asciiMsgTransceiver.sendMessages("++SET++![RESET  "+dbList.getSelectionModel().getSelectedItem()+"\r\n!]", false);
        }
    }


    private void get300Info(String responseData) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(responseData));
        String line;
        int idx = 0;
        DB300Info readDB300Info = new DB300Info();
        while ((line = reader.readLine()) != null) {
            idx++;
            line = line.trim();
            if (line.equals("DIBD")) {
                idx = 0;
                readDB300Info = new DB300Info();
                continue;
            }
            switch (idx) {
                case 1:
                    readDB300Info.setMacAddress(line);
                    break;
                case 2:
                    readDB300Info.setClientIP(InetAddress.getByName(line));
                    break;
                case 3:
                    readDB300Info.setClientSubnetMask(InetAddress.getByName(line));
                    break;
                case 4:
                    readDB300Info.setClientGateway(InetAddress.getByName(line));
                    break;
                case 5:
                    readDB300Info.setClientPort(line);
                    break;
                case 6:
                    readDB300Info.setIpStatic(line.equals("30"));
                    break;
                case 7:
                    readDB300Info.setServerIP(InetAddress.getByName(line));
                    break;
                case 8:
                    readDB300Info.setServerPort(line);
                    break;
                case 9:
                    readDB300Info.setServerMode(line.equals("31"));
                    break;
                case 10:
                    readDB300Info.setName(line);
                    break;
                case 13:
                    readDB300Info.setWifiSSID(line);
                    break;
                case 14:
                    readDB300Info.setWifiPW(line);
                    break;
                case 15:
                    readDB300Info.setStation(line.equals("30"));
                    break;
                default:
                    break;
            }db300InfoList.add(readDB300Info);
        }
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



    @Getter
    @Setter
    @ToString
    private class DB300Info {
        String macAddress;
        InetAddress clientIP;
        String clientPort;
        InetAddress clientSubnetMask;
        InetAddress clientGateway;

        InetAddress  serverIP;
        String serverPort;
        boolean ipStatic;//true = 고정, false 변동
        boolean serverMode;//true = 서버 , false 클라이언트
        String name;
        String wifiSSID;
        String wifiPW;
        boolean isStation; //true = sta(30), false = ap(31)
    }
}