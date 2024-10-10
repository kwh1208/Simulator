package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static dbps.dbps.Constants.SIZE_COLUMN;
import static dbps.dbps.Constants.SIZE_ROW;

public class ASCiiMsgController {

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

    ASCiiMsgService msgService = ASCiiMsgService.getInstance();
    AsciiMsgTransceiver asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
    ConfigService configService = ConfigService.getInstance();
    public static List<TextField> transmitMsgs = new ArrayList<>();
    private List<Button> msgSaveBtns = new ArrayList<>();
    private List<Button> msgSendBtns = new ArrayList<>();
    private List<Button> previewBtns = new ArrayList<>();
    private List<String> transmitMsgContents;

    private final KeyCode[] KONAMI_CODE = {
            KeyCode.UP, KeyCode.UP, KeyCode.DOWN, KeyCode.DOWN,
            KeyCode.LEFT, KeyCode.RIGHT, KeyCode.LEFT, KeyCode.RIGHT,
            KeyCode.B, KeyCode.A
    };

    // 사용자가 입력한 키를 저장할 큐
    private Queue<KeyCode> inputQueue = new LinkedList<>();

    @FXML
    public void initialize() {
        transmitMsgContents = msgService.loadMessages();

        transmitMsgs.add(transmitMsg1);
        msgSaveBtns.add(msgSaveBtn1);
        msgSendBtns.add(msgSendBtn1);
        previewBtns.add(previewBtn1);
        transmitMsg1.setText(transmitMsgContents.get(0));

        makeMsgContainer();

        ASCiiMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/ASCiiMsg.css").toExternalForm());

        transmitMsg1.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            inputQueue.add(keyCode); // 입력된 키를 큐에 추가

            // 큐의 크기가 정해진 시퀀스의 길이를 초과하면 가장 오래된 입력 제거
            if (inputQueue.size() > KONAMI_CODE.length) {
                inputQueue.poll();
            }

            // 현재 입력된 키 시퀀스가 정해진 시퀀스와 일치하는지 확인
            if (checkSequence()) {
                //숨겨진 기능!
                inputQueue.clear(); // 일치 시 입력 기록 초기화
            }
        });

    }

    private boolean checkSequence() {
        if (inputQueue.size() != KONAMI_CODE.length) return false;

        int i = 0;
        for (KeyCode code : inputQueue) {
            if (code != KONAMI_CODE[i++]) {
                return false;
            }
        }
        return true;
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

        msgService.saveMessages(num, inputText);
    }

    //기기에 메세지 전송
    public void sendMsg(Event event) {
        Button clickedBtn = (Button) event.getSource();
        int num = Integer.parseInt(clickedBtn.getId().substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);

        asciiMsgTransceiver.sendMessages(targetTextField.getText());
    }

    @FXML
    public void resetMsg() {
        msgService.resetMsg(transmitMsgContents, transmitMsgs);
    }

    @FXML
    public void setDefault(){
        msgService.makeOwnMsg();
    }

    @FXML
    public void preview(MouseEvent event) {
        Button clickedBtn = (Button) event.getSource();
        int num = Integer.parseInt(clickedBtn.getId().substring(10));
        TextField targetTextField = transmitMsgs.get(num - 1);
        //구현 계획
        //기본표시 설정 창에서 먼저 가져오고 추가된 값 있으면 기존값에서 갈아끼움.

        String textSize = "16";
        String fontGroup = "1";
        String effectIn = "정지효과";
        String effectInDirection = "효과방향";
        String effectOut = "정지효과";
        String effectOutDirection = "효과방향";
        String effectTime = "15";
        String xStart = "00";
        String xEnd = "00";
        String yStart = "00";
        String yEnd = "00";
        String textColor = "Yellow";
        String bgColor = "Black";


        //화면설정 크기에서 가져와서 크기 만들고.

        //그렇게 메세지 완성된거 기준으로 미리보기 띄우면 됨.

        //한픽셀 = 10

        msgService.preview(event, transmitMsgs);
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