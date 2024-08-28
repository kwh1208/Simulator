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

public class ASCiiMsgController {

    private static final Logger log = LoggerFactory.getLogger(ASCiiMsgController.class);

    @FXML
    private AnchorPane ASCiiMsgAnchorPane;

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

        ASCiiMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/ASCiiMsg.css").toExternalForm());
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
        for (int i = 2; i < 10; i++) {
            TextField textField = new TextField();
            textField.setMaxHeight(45.0);
            textField.setMaxWidth(466.0);
            AnchorPane.setLeftAnchor(textField, 11.0);
            AnchorPane.setTopAnchor(textField, 15.0 + (i - 1) * 55.0);
            AnchorPane.setRightAnchor(textField, 223.0);
            AnchorPane.setBottomAnchor(textField, 543 - (i - 1) * 55.0);
            textField.setId("transmitMsg" + i);
            textField.setText(transmitMsgContents.get(i - 1));
            transmitMsgs.add(textField);

            Button previewButton = new Button("미리보기");
            previewButton.setPrefHeight(45.0);
            previewButton.setPrefWidth(80.0);
            AnchorPane.setLeftAnchor(previewButton, 481.0);
            AnchorPane.setTopAnchor(previewButton, 15.0 + (i - 1) * 55.0);
            AnchorPane.setBottomAnchor(previewButton, 543 - (i - 1) * 55.0);
            AnchorPane.setRightAnchor(previewButton, 139.0);
            previewButton.setId("previewBtn" + i);
            previewButton.setOnMouseClicked(this::preview);
            previewBtns.add(previewButton);

            Button saveButton = new Button("저장");
            saveButton.setPrefHeight(45.0);
            saveButton.setPrefWidth(61.0);
            AnchorPane.setLeftAnchor(saveButton, 565.0);
            AnchorPane.setTopAnchor(saveButton, 15.0 + (i - 1) * 55.0);
            AnchorPane.setRightAnchor(saveButton, 73.5);
            AnchorPane.setBottomAnchor(saveButton, 543 - (i - 1) * 55.0);
            saveButton.setId("msgSaveBtn" + i);
            saveButton.setOnMouseClicked(this::saveMsg);
            msgSaveBtns.add(saveButton);

            Button sendButton = new Button("전송");
            sendButton.setPrefHeight(45.0);
            sendButton.setPrefWidth(61.0);
            AnchorPane.setLeftAnchor(sendButton, 630.0);
            AnchorPane.setTopAnchor(sendButton, 15.0 + (i - 1) * 55.0);
            AnchorPane.setBottomAnchor(sendButton, 543 - (i - 1) * 55.0);
            AnchorPane.setRightAnchor(sendButton, 8.0);
            sendButton.setId("msgSendBtn" + i);
            sendButton.setOnMouseClicked(this::sendMsg);
            msgSendBtns.add(sendButton);

            ASCiiMsgAnchorPane.getChildren().addAll(textField, previewButton, saveButton, sendButton);
        }
    }
}