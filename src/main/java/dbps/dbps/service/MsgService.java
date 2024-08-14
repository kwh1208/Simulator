package dbps.dbps.service;


import dbps.dbps.Simulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MsgService {

    private static final String FILE_NAME = "messages.txt";
    private static final Logger log = LoggerFactory.getLogger(MsgService.class);
    public static Stage makeMsgWindow;

    public static void saveMessages(int num, String msg){
        List<String> msgs = loadMessages();

        msgs.set(num - 1, msg);
        log.info("MsgService saveMessages 호출");
        log.info("msgs={} " , msgs);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String line : msgs) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetMsg(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        log.info("MsgService resetMsg 호출");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            doReset(transmitMsgContents, transmitMsgs);
            log.info("메세지 초기화 완료");
        }
    }

    public static void preview(MouseEvent event, List<TextField> transmitMsgs){
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        int num = Integer.parseInt(buttonId.substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);
        String inputText = targetTextField.getText();

        log.info("MsgService preview 호출");
        log.info("inputText={} " , inputText);
    }

    public static void sendMessages(int num, String text) {
        log.info("MsgService sendMessages 호출");
        log.info("text={}", text);
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

    public static void setDisplaySettings() {
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
