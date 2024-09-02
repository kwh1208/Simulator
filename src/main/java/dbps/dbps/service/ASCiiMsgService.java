package dbps.dbps.service;


import dbps.dbps.Simulator;
import dbps.dbps.service.connectManager.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static dbps.dbps.Constants.CONNECT_TYPE;


public class ASCiiMsgService {

    private static final String FILE_NAME = "messages.txt";
    public static Stage makeMsgWindow;

    //싱글톤
    private static ASCiiMsgService instance = null;

    //DI용
    private final SerialPortManager serialPortManager;
    private final LogService logService;
    private final UDPManager udpManager;
    private final TCPManager tcpManager;
    private final RS485Manager rs485Manager;
    private final WiFiManager wiFiManager;

    private ASCiiMsgService() {
        serialPortManager = SerialPortManager.getManager();
        logService = LogService.getLogService();
        udpManager = UDPManager.getUDPManager();
        tcpManager = TCPManager.getManager();
        rs485Manager = RS485Manager.getInstance();
        wiFiManager = WiFiManager.getInstance();
    }

    public static ASCiiMsgService getInstance() {
        if (instance == null) {
            instance = new ASCiiMsgService();
        }
        return instance;
    }

    //메세지 txt 파일에 저장
    public void saveMessages(int num, String msg){
        List<String> msgs = loadMessages();

        msgs.set(num - 1, msg);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String line : msgs) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            //에러처리
        }
    }

    //메세지 초기화 확인
    public void resetMsg(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            doReset(transmitMsgContents, transmitMsgs);
            logService.updateInfoLog("메세지가 초기화 되었습니다.");
        }
    }

    /**
     *
     * @param event
     * @param transmitMsgs
     */

    public void preview(MouseEvent event, List<TextField> transmitMsgs){
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        int num = Integer.parseInt(buttonId.substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);
        String inputText = targetTextField.getText();

        //메세지 미리보기
    }

    public String sendMessages(String text) {
        //현재 serial, bluetooth, udp, tcp, rs485, WiFi 중에 어떤 것인지 파악.
        //열려있는 곳으로 메세지 전송
        String receivedMsg = "";

        //로그 출력
        logService.updateInfoLog("전송 메세지: " + text);

        switch (CONNECT_TYPE) {
            case "serial", "bluetooth" -> {
                //시리얼 및 블루투스
                receivedMsg = serialPortManager.sendMsgAndGetMsg(text);
            }
            case "udp" ->
                //udp로 메세지 전송
                    receivedMsg = udpManager.sendASCMsg(text);
            case "tcp" ->
                //tcp로 메세지 전송
                    receivedMsg = tcpManager.sendASCMsg(text);
            case "rs485" ->
                //rs485로 메세지 전송
                    receivedMsg = rs485Manager.sendMsg(text);
            case "WiFi" ->
                //WiFi로 메세지 전송
                    receivedMsg = wiFiManager.sendMsg(text);
        }
        //응답메세지 로그출력
        logService.updateInfoLog("수신 메세지: " + receivedMsg);

        //응답메세지 검사후 문제 발생시 text 검사.
        if (receivedMsg.equals("에러프로토콜!")){
            //text 검사하는 로직
            String cause = "에러프로토콜";

            //검사 결과 및 응답메세지 경고메세지/주의 메세지로 출력
            logService.errorLog(cause);
        }

        return receivedMsg;
    }

    //


    /**
     * 리팩토링
     */

    public void setDefaultMessages(List<String> messages) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String message : messages) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            //에러처리
        }
    }

    //메세지 불러오기
    public List<String> loadMessages() {
        List<String> messages = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            messages.add("![000Hello world!]");
            messages.add("![000/C1Hello /C2World!]");
            messages.add("![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
            for (int i = 4; i <= 9; i++) {
                messages.add("");
            }
            setDefaultMessages(messages);
            return messages;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            //에러처리
        }

        return messages;
    }

    //저장한 메세지 모두 지우고 초기화.
    private void doReset(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        //메세지 지워
        try {
            Files.delete(Paths.get("messages.txt"));
        } catch (IOException e) {
            e.getStackTrace();
        }

        //새로 만들어
        List<String> messages = loadMessages();

        //textField에 메세지 넣어
        for (int i = 0; i < messages.size(); i++) {
            transmitMsgContents.set(i, messages.get(i));
            transmitMsgs.get(i).setText(messages.get(i));
        }
    }

    //메세지 만들기 창 띄우기
    public void makeOwnMsg() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/makeOwnMsg.fxml"));
            AnchorPane root = fxmlLoader.load();

            makeMsgWindow = new Stage();
            makeMsgWindow.setTitle("메세지 만들기");

            Scene scene = new Scene(root, 550, 600);
            makeMsgWindow.setScene(scene);
            makeMsgWindow.setResizable(false);

            makeMsgWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
