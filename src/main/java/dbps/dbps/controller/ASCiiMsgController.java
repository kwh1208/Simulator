package dbps.dbps.controller;

import dbps.dbps.Constants;
import dbps.dbps.Simulator;
import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.UIConstants;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class ASCiiMsgController {

    public RadioButton utf8;
    public RadioButton utf16;
    public RadioButton eucKr;
    public AnchorPane asciiMsgAnchorPane;
    @FXML
    private ProgressIndicator progressIndicator;

    // 서비스 필드
    private ASCiiMsgService msgService;
    private AsciiMsgTransceiver asciiMsgTransceiver;
    private ResourceBundle bundle;

    // UI 관련 필드
    private List<TextField> transmitMsgs;
    private List<String> transmitMsgContents;
    private final ToggleGroup msgType = new ToggleGroup();

    @FXML
    public void initialize() {
        Platform.runLater(() -> progressIndicator.toFront());
        
        initializeServices();
        initializeUIComponents();
        setupEventListeners();
    }

    /**
     * 서비스 초기화
     */
    private void initializeServices() {
        bundle = ResourceManager.getInstance().getBundle();
        msgService = ASCiiMsgService.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        
        transmitMsgContents = msgService.loadMessages();
        transmitMsgs = new ArrayList<>();
    }

    /**
     * UI 컴포넌트 초기화
     */
    private void initializeUIComponents() {
        createMessageContainers();
        
        asciiMsgAnchorPane.getStylesheets().add(
            Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/ASCiiMsg.css")).toExternalForm()
        );
        asciiMsgAnchorPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }

    /**
     * 이벤트 리스너 설정
     */
    private void setupEventListeners() {
        eucKr.setToggleGroup(msgType);
        utf8.setToggleGroup(msgType);
        utf16.setToggleGroup(msgType);
        
        eucKr.setSelected(true);
        
        asciiMsgAnchorPane.setOnKeyPressed(new Constants.EscapeKeyEventHandler());
    }

    /**
     * 메시지 컨테이너 생성
     */
    private void createMessageContainers() {
        for (int i = 1; i <= UIConstants.MAX_MESSAGES; i++) {
            addMessageRow(i);
        }
    }

    /**
     * 단일 메시지 행 추가
     * 
     * @param index 행 인덱스
     */
    private void addMessageRow(int index) {
        TextField textField = createTextField(index);
        Button sendButton = createSendButton(index);
        
        asciiMsgAnchorPane.getChildren().addAll(textField, sendButton);
        transmitMsgs.add(textField);
    }

    /**
     * 텍스트 필드 생성
     * 
     * @param index 텍스트필드 인덱스
     * @return 생성된, 설정된 TextField
     */
    private TextField createTextField(int index) {
        TextField textField = new TextField();
        int rowIndex = index - 1; // 0부터 시작하는 인덱스로 변환
        
        textField.setLayoutX(UIConstants.TEXT_FIELD_MARGIN_LEFT);
        textField.setLayoutY(UIConstants.TEXT_FIELD_MARGIN_TOP + 
                             rowIndex * UIConstants.TEXT_FIELD_VERTICAL_SPACING);
        textField.setPrefWidth(UIConstants.TEXT_FIELD_WIDTH);
        textField.setPrefHeight(UIConstants.TEXT_FIELD_HEIGHT);
        textField.setId("transmitMsg" + index);
        
        // 메시지 내용 설정 (빈 문자열 방지)
        String content = (rowIndex < transmitMsgContents.size() && transmitMsgContents.get(rowIndex) != null) 
                           ? transmitMsgContents.get(rowIndex) : "";
        textField.setText(content);
        
        return textField;
    }

    /**
     * 전송 버튼 생성
     * 
     * @param index 버튼 인덱스
     * @return 생성된, 설정된 Button
     */
    private Button createSendButton(int index) {
        Button sendButton = new Button(bundle.getString("sendButton"));
        int rowIndex = index - 1; // 0부터 시작하는 인덱스로 변환
        
        sendButton.setLayoutX(UIConstants.BUTTON_MARGIN_LEFT);
        sendButton.setLayoutY(UIConstants.TEXT_FIELD_MARGIN_TOP + 
                              rowIndex * UIConstants.TEXT_FIELD_VERTICAL_SPACING);
        sendButton.setPrefWidth(UIConstants.BUTTON_WIDTH);
        sendButton.setPrefHeight(UIConstants.BUTTON_HEIGHT);
        sendButton.setId("msgSendBtn" + index);
        sendButton.setOnMouseClicked(this::sendMsg);
        
        return sendButton;
    }

    /**
     * 메시지 저장 메서드
     */
    @FXML
    public void saveMsg() {
        List<String> msgList = new ArrayList<>();
        
        for (TextField textField : transmitMsgs) {
            msgList.add(textField.getText());
        }
        
        msgService.saveMessages(msgList);
    }

    /**
     * 기기에 메시지 전송
     * 
     * @param event 마우스 이벤트
     */
    @FXML
    public void sendMsg(MouseEvent event) {
        if (!(event.getSource() instanceof Button clickedBtn)) {
            return;
        }

        int num = extractButtonNumber(clickedBtn.getId());
        
        if (num > 0 && num <= transmitMsgs.size()) {
            TextField targetTextField = transmitMsgs.get(num - 1);
            sendMessageToDevice(targetTextField.getText());
            saveMsg();
        }
    }

    /**
     * 버튼 ID에서 숫자 부분 추출
     * 
     * @param buttonId 버튼 ID
     * @return 추출된 숫자
     */
    private int extractButtonNumber(String buttonId) {
        try {
            return Integer.parseInt(buttonId.substring(10));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return -1;
        }
    }

    /**
     * 실제 메시지 전송 로직
     * 
     * @param message 전송할 메시지
     */
    private void sendMessageToDevice(String message) {
        boolean isUtf8Selected = utf8.isSelected();
        boolean isUtf16Selected = utf16.isSelected();
        
        asciiMsgTransceiver.sendMessages(
            message, 
            isUtf8Selected, 
            isUtf16Selected, 
            progressIndicator
        );
    }

    /**
     * 화면 닫기
     * 
     * @param mouseEvent 마우스 이벤트
     */
    @FXML
    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}