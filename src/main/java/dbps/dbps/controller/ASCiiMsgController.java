package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ASCiiMsgController {

    @FXML
    public Button msgSaveBtn;

    @FXML
    private AnchorPane ASCiiMsgAnchorPane;
    @FXML
    ProgressIndicator progressIndicator;
    ResourceBundle bundle;

    @FXML
    private RadioButton utf_8;
    @FXML
    public RadioButton utf_16;
    public RadioButton euc_kr;

    ASCiiMsgService msgService;
    AsciiMsgTransceiver asciiMsgTransceiver;
    ConfigService configService;

    private List<TextField> transmitMsgs;
    private List<String> transmitMsgContents;

    ToggleGroup msgType = new ToggleGroup();


    @FXML
    public void initialize() {
        Platform.runLater(() -> progressIndicator.toFront());
        bundle= ResourceManager.getInstance().getBundle();

        msgService = ASCiiMsgService.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        transmitMsgContents = msgService.loadMessages();
        configService = ConfigService.getInstance();

        transmitMsgs = new ArrayList<>();

        makeMsgContainer();

        euc_kr.setToggleGroup(msgType);
        utf_8.setToggleGroup(msgType);
        utf_16.setToggleGroup(msgType);

        ASCiiMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/ASCiiMsg.css").toExternalForm());
        ASCiiMsgAnchorPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        euc_kr.setSelected(true);
    }


    public void saveMsg() {
        List<String> msgList = new ArrayList<>();
        for (int i = 1; i < transmitMsgs.size(); i++) {
            msgList.add(transmitMsgs.get(i-1).getText());
        }

        msgService.saveMessages(msgList);
    }

    //기기에 메세지 전송
    public void sendMsg(MouseEvent event) {
        Button clickedBtn = (Button) event.getSource();
        int num = Integer.parseInt(clickedBtn.getId().substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);
        asciiMsgTransceiver.sendMessages(targetTextField.getText(), utf_8.isSelected(), utf_16.isSelected(), progressIndicator);

        saveMsg();
    }

    private void makeMsgContainer() {
        // transmitMsgContents 리스트에 최소 10개의 문자열이 있다고 가정합니다.
        for (int i = 1; i <= 10; i++) {
            TextField textField = new TextField();
            textField.setLayoutX(14);
            textField.setLayoutY(41 + (i - 1) * 40); // 첫 번째: 41, 두 번째: 81, ...
            textField.setPrefWidth(339);
            textField.setPrefHeight(28);
            textField.setId("transmitMsg" + i);
            textField.setText(transmitMsgContents.get(i - 1));
            transmitMsgs.add(textField);

            // Button 생성 및 설정
            Button sendButton = new Button(bundle.getString("sendButton"));
            sendButton.setLayoutX(367);
            sendButton.setLayoutY(41 + (i - 1) * 40); // TextField와 같은 Y값
            sendButton.setPrefWidth(59);
            sendButton.setPrefHeight(28);
            sendButton.setId("msgSendBtn" + i);
            sendButton.setOnMouseClicked(this::sendMsg);

            ASCiiMsgAnchorPane.getChildren().addAll(textField, sendButton);
        }
    }


    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}