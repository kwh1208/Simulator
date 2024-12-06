package dbps.dbps.service;


import dbps.dbps.Simulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ASCiiMsgService {

    private static final String FILE_NAME = "messages.txt";
    public static Stage makeMsgWindow;

    private static ASCiiMsgService instance = null;

    private final LogService logService;
    ConfigService configService;

    private ASCiiMsgService() {
        logService = LogService.getLogService();
        configService = ConfigService.getInstance();
    }

    public static ASCiiMsgService getInstance() {
        if (instance == null) {
            instance = new ASCiiMsgService();
        }
        return instance;
    }

    //메세지 txt 파일에 저장
    public void saveMessages(List<String> msgList){
        for (int i = 0; i < msgList.size(); i++) {
            configService.setProperty("ASCMsg"+(i+1), msgList.get(i));
        }
    }

    //메세지 초기화 확인
    public void resetMsg() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            for (int i = 1; i < 10; i++) {
                if (i==1){
                    configService.setProperty("ASCMsg"+i, "![000Hello world!]");
                } else if (i==2) {
                    configService.setProperty("ASCMsg"+i, "![000/C1Hello /C2World!]");
                } else if (i == 3) {
                    configService.setProperty("ASCMsg"+i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
                }
                else configService.setProperty("ASCMsg"+i, "");
            }
        }
    }

    /**
     * 리팩토링
     */

    //메세지 불러오기
    public List<String> loadMessages() {
        List<String> messages = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            String value = configService.getProperty("ASCMsg" + i);
            messages.add(value);
        }

        return messages;
    }

    //메세지 만들기 창 띄우기
    public void makeOwnMsg() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/asciiDefaultSetting.fxml"));
            AnchorPane root = fxmlLoader.load();

            makeMsgWindow = new Stage();
            makeMsgWindow.setTitle("메세지 만들기");

            Scene scene = new Scene(root, 550, 550);
            makeMsgWindow.setScene(scene);
            makeMsgWindow.setResizable(false);

            makeMsgWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
