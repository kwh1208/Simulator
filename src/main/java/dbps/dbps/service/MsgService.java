package dbps.dbps.service;


import dbps.dbps.Simulator;
import dbps.dbps.controller.LogController;
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

import static dbps.dbps.service.LogService.logService;


public class MsgService {

    private static final String FILE_NAME = "messages.txt";
    public static Stage makeMsgWindow;

    //메세지 txt 파일에 저장
    public static void saveMessages(int num, String msg){
        List<String> msgs = loadMessages();

        msgs.set(num - 1, msg);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String line : msgs) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //메세지 초기화 확인
    public static void resetMsg(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            doReset(transmitMsgContents, transmitMsgs);
            logService.updateInfoLog("메세지가 초기화 되었습니다.");
        }
    }

    public static void preview(MouseEvent event, List<TextField> transmitMsgs){
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        int num = Integer.parseInt(buttonId.substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);
        String inputText = targetTextField.getText();

        //메세지 미리보기
    }

    public static void sendMessages(int num, String text) {
    }


    /**
     * 리팩토링
     */

    public static void setDefaultMessages(List<String> messages) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String message : messages) {
                writer.write(message);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadMessages() {
        List<String> messages = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            messages.add("![000Hello world!]");
            messages.add("![000/C1Hello /C2World!]");
            messages.add("![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
            for (int i = 4; i <= 11; i++) {
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
            e.printStackTrace();
        }

        return messages;
    }


    private static void doReset(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        try {
            Files.delete(Paths.get("messages.txt"));
        } catch (IOException e) {
            e.getStackTrace();
        }

        List<String> messages = loadMessages();

        for (int i = 0; i < messages.size(); i++) {
            transmitMsgContents.set(i, messages.get(i));
            transmitMsgs.get(i).setText(messages.get(i));
        }
    }

    public static void makeOwnMsg() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("makeOwnMsg.fxml"));
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
