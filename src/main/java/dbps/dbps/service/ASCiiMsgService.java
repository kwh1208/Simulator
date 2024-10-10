package dbps.dbps.service;


import dbps.dbps.Simulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static dbps.dbps.Constants.SIZE_COLUMN;
import static dbps.dbps.Constants.SIZE_ROW;


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
    public void saveMessages(int num, String msg){
        configService.setProperty("ASCMsg"+num, msg);
    }

    //메세지 초기화 확인
    public void resetMsg(List<String> transmitMsgContents, List<TextField> transmitMsgs) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            for (int i = 1; i < 10; i++) {
                if (i==1){
                    configService.setProperty("ASCMsg"+i, "![000Hello world!]");
                } else if (i==2) {
                    configService.setProperty("ASCMsg"+i, "![000/P000/C1Hello!]");
                } else if (i == 3) {
                    configService.setProperty("ASCMsg"+i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
                }
                configService.setProperty("ASCMsg"+i, "");
            }
        }
    }

    /**
     *
     * @param event
     * @param transmitMsgs
     */

    public void preview(MouseEvent event, List<TextField> transmitMsgs) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        int num = Integer.parseInt(buttonId.substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);
        String inputText = targetTextField.getText();


        //메세지 미리보기
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/preview.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);  // 모달창 설정
        stage.setTitle("Preview Window");

        // Scene 생성 및 크기 지정
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), SIZE_ROW*16*4, SIZE_COLUMN*16*4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);

        // 모달 창 표시
        stage.showAndWait();

    }

    //


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
