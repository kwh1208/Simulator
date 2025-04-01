package dbps.dbps.controller;

import com.fazecast.jSerialComm.SerialPort;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

import static dbps.dbps.Constants.*;

public class ASCheckController {
    AsciiMsgTransceiver asciiMsgTransceiver;
    HexMsgTransceiver hexMsgTransceiver;
    ConfigService configService;
    LogService logService;

    public void initialize() {
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        configService = ConfigService.getInstance();
        logService = LogService.getLogService();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    private int extractPortNumber(String portName) {
        return Integer.parseInt(portName.replaceAll("[^0-9]", ""));
    }

    public void copyController() {
        asciiMsgTransceiver.sendMessages("![0081!]", false, null)
                .thenAccept(result -> {
                    inputData(result, true);
                })
                .exceptionally(ex ->{
                    inputData(null, false);
                    return null;
                });
    }

    private void inputData(String firmware, boolean result){
        Map<String, Object> infoMap = new LinkedHashMap<>();
        infoMap.put("connect type", CONNECT_TYPE);
        if (CONNECT_TYPE.equals("serial")){
            infoMap.put("serial baud rate", SERIAL_BAUDRATE);
            infoMap.put("port num", OPEN_PORT_NAME);
            List<String> portNames = Arrays.stream(SerialPort.getCommPorts())
                    .filter(port -> !port.getPortDescription().toLowerCase().contains("bluetooth"))
                    .map(SerialPort::getSystemPortName)
                    .sorted(Comparator.comparingInt(this::extractPortNumber))
                    .toList();
            infoMap.put("possible Port", portNames);
        }
        if (isRS){
            infoMap.put("RS485", "Yes");
            infoMap.put("RS485-Addr", RS485_ADDR_NUM);
        }
        if (CONNECT_TYPE.equals("clientTCP")){
            infoMap.put("TCP IP", TCP_IP);
            infoMap.put("TCP Port", TCP_PORT);
        }
        if (CONNECT_TYPE.equals("serverTCP")){
            infoMap.put("server TCP IP", hostIP);
            infoMap.put("server TCP Port", serverTCPPort);
        }
        if (CONNECT_TYPE.equals("UDP")){
            infoMap.put("UDP IP", UDP_IP);
            infoMap.put("UDP Port", UDP_PORT);
        }
        infoMap.put("ascii", IS_ASCII);
        infoMap.put("column size", SIZE_COLUMN);
        infoMap.put("row size", SIZE_ROW);
        if (result) {
            infoMap.put("Firmware version", firmware);
        }
        else {
            infoMap.put("", "통신불가로 확인안됨.");
        }
        infoMap.put("arrange", howToArrange);
        infoMap.put("bits per pixel", BITS_PER_PIXEL);
        infoMap.put("display signal", configService.getProperty("displaySignal"));
        infoMap.put("display Signal color", configService.getProperty(configService.getProperty("displaySignal") + "-color"));
        infoMap.put("log", logService.getLast10Lines());

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : infoMap.entrySet()) {
            sb.append(entry.getKey())
                    .append(" : ")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }
        String copyText = sb.toString();

        Platform.runLater(() -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(copyText);
            clipboard.setContent(content);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("복사 완료");
            alert.setHeaderText(null);
            alert.setContentText("정보가 클립보드에 복사되었습니다.");
            alert.show();
        });

    }

    public void openAS() {
        try {
            Desktop.getDesktop().browse(new URI("https://forms.gle/zkt5ALsQKKZhbQnx9"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connectionTest() {
        hexMsgTransceiver.sendByteMessages(CONNECT_START, null);
    }

    public void goDocs() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://publish.obsidian.md/dabitdocs"));
    }

    public void goFAQ() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://publish.obsidian.md/dabitdocs"));
    }
}
