package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.MsgService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MsgController {

    private static final Logger log = LoggerFactory.getLogger(MsgController.class);

    @FXML
    private AnchorPane msgAnchorPane;

    @FXML
    private TextField transmitMsg1;

    @FXML
    private Button msgSaveBtn1;

    @FXML
    private Button msgSendBtn1;

    @FXML
    private Button previewBtn1;

    public static List<TextField> transmitMsgs = new ArrayList<>();
    private List<Button> msgSaveBtns = new ArrayList<>();
    private List<Button> msgSendBtns = new ArrayList<>();
    private List<Button> previewBtns = new ArrayList<>();
    private List<String> transmitMsgContents;

    @FXML
    public void initialize() {
        transmitMsgContents = MsgService.loadMessages();

        transmitMsgs.add(transmitMsg1);
        msgSaveBtns.add(msgSaveBtn1);
        msgSendBtns.add(msgSendBtn1);
        previewBtns.add(previewBtn1);
        transmitMsg1.setText(transmitMsgContents.get(0));

        makeMsgContainer();

        msgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/msg.css").toExternalForm());
    }


    /**
     * 실제 작동하는 기능들
     */

    //text파일에 저장
    @FXML
    public void saveMsg(Event event) {
        Button clickedBtn = (Button) event.getSource();
        int num = Integer.parseInt(clickedBtn.getId().substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);

        String inputText = targetTextField.getText();

        MsgService.saveMessages(num, inputText);
    }

    //기기에 메세지 전송
    @FXML
    public void sendMsg(Event event) {
        Button clickedBtn = (Button) event.getSource();
        int num = Integer.parseInt(clickedBtn.getId().substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);

        MsgService.sendMessages(num, targetTextField.getText());
    }

    @FXML
    public void resetMsg() {
        MsgService.resetMsg(transmitMsgContents, transmitMsgs);
    }

    @FXML
    public void makeOwnMsg(){
        MsgService.makeOwnMsg();
    }

    @FXML
    public void preview(MouseEvent event) {
        MsgService.preview(event, transmitMsgs);
    }


    /**
     * 리팩토링용
     * 처음 시작할때 textField와 버튼을 만들어주는 메소드
     */


    private void makeMsgContainer() {
        for (int i = 2; i < 12; i++) {
            TextField textField = new TextField();
            textField.setPrefHeight(35.0);
            textField.setPrefWidth(374.0);
            textField.setLayoutX(11.0);
            textField.setLayoutY(15.0 + (i - 1) * 45.0);
            textField.setId("transmitMsg" + i);
            textField.setText(transmitMsgContents.get(i - 1));
            transmitMsgs.add(textField);

            Button previewButton = new Button("미리보기");
            previewButton.setPrefHeight(35.0);
            previewButton.setPrefWidth(72.0);
            previewButton.setLayoutX(389.0);
            previewButton.setLayoutY(15.0 + (i - 1) * 45.0);
            previewButton.setId("previewBtn" + i);
            previewButton.setOnMouseClicked(this::preview);
            previewBtns.add(previewButton);

            Button saveButton = new Button("저장");
            saveButton.setPrefHeight(35.0);
            saveButton.setPrefWidth(61.0);
            saveButton.setLayoutX(465.0);
            saveButton.setLayoutY(15.0 + (i - 1) * 45.0);
            saveButton.setId("msgSaveBtn" + i);
            saveButton.setOnMouseClicked(this::saveMsg);
            msgSaveBtns.add(saveButton);

            Button sendButton = new Button("전송");
            sendButton.setPrefHeight(35.0);
            sendButton.setPrefWidth(61.0);
            sendButton.setLayoutX(530.0);
            sendButton.setLayoutY(15.0 + (i - 1) * 45.0);
            sendButton.setId("msgSendBtn" + i);
            sendButton.setOnMouseClicked(this::sendMsg);
            msgSendBtns.add(sendButton);

            msgAnchorPane.getChildren().addAll(textField, previewButton, saveButton, sendButton);
        }
    }
}