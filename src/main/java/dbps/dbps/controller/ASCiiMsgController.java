package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.PreviewService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.*;


public class ASCiiMsgController {

    @FXML
    public Button msgSaveBtn;
    @FXML
    private AnchorPane ASCiiMsgAnchorPane;

    ASCiiMsgService msgService;
    AsciiMsgTransceiver asciiMsgTransceiver;
    PreviewService previewService;
    ConfigService configService;

    public static List<TextField> transmitMsgs = new ArrayList<>();
    private List<Button> msgSendBtns = new ArrayList<>();
    private List<String> transmitMsgContents;

    @FXML
    public void initialize() {
        msgService = ASCiiMsgService.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        transmitMsgContents = msgService.loadMessages();
        previewService = PreviewService.getInstance();
        configService = ConfigService.getInstance();

        makeMsgContainer();

        ASCiiMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/ASCiiMsg.css").toExternalForm());
    }

    //text파일에 저장
    @FXML
    public void saveMsg() {
        List<String> msgList = new ArrayList<>();
        for (int i = 1; i < transmitMsgs.size(); i++) {
            msgList.add(transmitMsgs.get(i-1).getText());
        }

        msgService.saveMessages(msgList);
    }

    //기기에 메세지 전송
    public void sendMsg(Event event) {
        Button clickedBtn = (Button) event.getSource();
        int num = Integer.parseInt(clickedBtn.getId().substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);

        asciiMsgTransceiver.sendMessages(targetTextField.getText());
    }

    //메세지 초기화
    @FXML
    public void resetMsg() {
        msgService.resetMsg();
        for (int i = 0; i < transmitMsgs.size(); i++) {
            transmitMsgs.get(i).setText(configService.getProperty("ASCMsg"+(i+1)));
        }
    }

    //기본 속성 설정 창
    @FXML
    public void setDefault(){
        msgService.makeOwnMsg();
    }

    //미리보기
//    @FXML
//    public void preview(MouseEvent event) {
//        Button clickedBtn = (Button) event.getSource();
//        int num = Integer.parseInt(clickedBtn.getId().substring(10));
//        TextField targetTextField = transmitMsgs.get(num - 1);
//        //구현 계획
//        //기본표시 설정 창에서 먼저 가져오고 추가된 값 있으면 기존값에서 갈아끼움.
//
//        HashMap<String, String> textEffect = new HashMap<>();
//        textEffect.put("textSize", "16");
//        textEffect.put("fontGroup", "1");
//        textEffect.put("effectIn", "정지효과");
//        textEffect.put("effectInDirection", "효과방향");
//        textEffect.put("effectOut", "정지효과");
//        textEffect.put("effectOutDirection", "효과방향");
//        textEffect.put("effectTime", "15");
//        textEffect.put("xStart", "00");
//        textEffect.put("yStart", "00");
//        textEffect.put("xEnd", "00");
//        textEffect.put("yEnd", "00");
//        textEffect.put("textColor", "yellow");
//        textEffect.put("bgColor", "black");
//
//
//
//        //화면설정 크기에서 가져와서 크기 만들고.
//
//        //그렇게 메세지 완성된거 기준으로 미리보기 띄우면 됨.
//
//        //한픽셀 = 10
//
////        String fullText = targetTextField.getText().substring(5, targetTextField.getText().length()-2);
////        String[] split = fullText.split("/");
////        String text = "";
////        String[] deco = new String[split.length];
////        for (int i = 0; i < split.length; i++) {
////            if (split[i].startsWith("/")){
////
////            }
////        }
//
//
//        previewService.preview(targetTextField.getText(), textEffect);
//    }


    /**
     * 리팩토링용
     * 시작시 textField와 버튼을 만들어주는 메소드
     */
    private void makeMsgContainer() {
        for (int i = 1; i < 10; i++) {
            TextField textField = new TextField();
            textField.setMaxHeight(45.0);
            textField.setMaxWidth(565.0);
            AnchorPane.setLeftAnchor(textField, 11.0);
            AnchorPane.setTopAnchor(textField, 15.0 + (i - 1) * 55.0);
            AnchorPane.setRightAnchor(textField, 91.0);
            AnchorPane.setBottomAnchor(textField, 543 - (i - 1) * 55.0);
            textField.setId("transmitMsg" + i);
            textField.setText(transmitMsgContents.get(i - 1));
            transmitMsgs.add(textField);

            Button sendButton = new Button("전송");
            sendButton.setPrefHeight(45.0);
            sendButton.setPrefWidth(61.0);
            AnchorPane.setLeftAnchor(sendButton, 620.0);
            AnchorPane.setTopAnchor(sendButton, 15.0 + (i - 1) * 55.0);
            AnchorPane.setBottomAnchor(sendButton, 543 - (i - 1) * 55.0);
            AnchorPane.setRightAnchor(sendButton, 8.0);
            sendButton.setId("msgSendBtn" + i);
            sendButton.setOnMouseClicked(this::sendMsg);
            msgSendBtns.add(sendButton);

            ASCiiMsgAnchorPane.getChildren().addAll(textField,  sendButton);
        }
    }
}