package dbps.dbps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MQTTMsgController {
    public AnchorPane mqttMsgAP;
    @FXML
    private RadioButton realTimeMsg;
    @FXML
    private RadioButton pageMsg;
    @FXML
    private Label pageCntLabel;
    @FXML
    private ChoiceBox<String> pageMsgCnt;
    @FXML
    private RadioButton section0;
    @FXML
    private RadioButton section1;
    @FXML
    private RadioButton section2;
    @FXML
    private ChoiceBox<String> displayControl;
    @FXML
    private ChoiceBox<String> displayMethod;
    @FXML
    private ChoiceBox<String> charCodes;
    @FXML
    private ChoiceBox<String> fontSize;
    @FXML
    private ChoiceBox<String> fontGroup;
    @FXML
    private ChoiceBox<String> effectIn;
    @FXML
    private ChoiceBox<String> inDirection;
    @FXML
    private ChoiceBox<String> effectOut;
    @FXML
    private ChoiceBox<String> outDirection;
    @FXML
    private ChoiceBox<String> effectSpeed;
    @FXML
    private ChoiceBox<String> effectTime;
    @FXML
    private ChoiceBox<String> xStart;
    @FXML
    private ChoiceBox<String> yStart;
    @FXML
    private ChoiceBox<String> xEnd;
    @FXML
    private ChoiceBox<String> yEnd;
    @FXML
    private ChoiceBox<String> bgImg;
    @FXML
    private TextField textColor;
    @FXML
    private TextField bgColor;
    @FXML
    private TextField msg;
    MQTTManager mqttManager;
    ResourceBundle bundle;

    ToggleGroup msgTypeGroup = new ToggleGroup();

    ToggleGroup sectionGroup = new ToggleGroup();
    ConfigService configService;

    Map<String, Integer> colorMap;


    @FXML
    public void initialize() {
        mqttMsgAP.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/hexMessage.css")).toExternalForm());
        configService = ConfigService.getInstance();
        bundle= ResourceManager.getInstance().getBundle();
        mqttManager = MQTTManager.getInstance();
        mqttManager.setMSG();

        realTimeMsg.setToggleGroup(msgTypeGroup);
        pageMsg.setToggleGroup(msgTypeGroup);

        section0.setToggleGroup(sectionGroup);
        section1.setToggleGroup(sectionGroup);
        section2.setToggleGroup(sectionGroup);

        realTimeMsg.setSelected(true);
        if (configService.getProperty("isMQTTRealTime").equals("0")){
            realTimeMsg.setSelected(true);
        }
        else{
            pageMsg.setSelected(true);
            pageMsgCnt.setVisible(true);
            pageCntLabel.setVisible(true);
            pageMsgCnt.setValue(configService.getProperty("isMQTTRealTime"));
        }
        section0.setSelected(true);

        msgTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                if (selectedRadioButton.getId().equals("realTimeMsg")) {
                    pageMsgCnt.setVisible(false);
                    pageCntLabel.setVisible(false);
                } else {
                    pageMsgCnt.setVisible(true);
                    pageCntLabel.setVisible(true);
                }
                doMsgSettings();
            }
        });

        for (int i = 1; i < 11; i++) {
            bgImg.getItems().add(Integer.toString(i));
        }

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> doMsgSettings());

        sectionGroup.selectedToggleProperty().addListener((observable, oldValue, newValue)-> doMsgSettings());

        effectOut.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateOutDirections(newValue));
        effectIn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateInDirections(newValue));

        doMsgSettings();

        setXY();
    }

    public void save() {
        String msgNum = getMsgNum();
        configService.setProperty("isMQTTRealTime", "0");
        if (pageMsg.isSelected()) {
            configService.setProperty("isMQTTRealTime", pageMsgCnt.getValue());
        }
        configService.setProperty("MQTTdisplayControl"+msgNum, displayControl.getValue());
        configService.setProperty("MQTTdisplayMethod"+msgNum, displayMethod.getValue());
        configService.setProperty("MQTTcharCode"+msgNum, charCodes.getValue());
        configService.setProperty("MQTTfontSize"+msgNum, fontSize.getValue());
        configService.setProperty("MQTTfontGroup"+msgNum, fontGroup.getValue());
        configService.setProperty("MQTTeffectIn"+msgNum, effectIn.getValue());
        configService.setProperty("MQTTeffectInDirection"+msgNum, inDirection.getValue());
        configService.setProperty("MQTTeffectOut"+msgNum, effectOut.getValue());
        configService.setProperty("MQTTeffectOutDirection"+msgNum, outDirection.getValue());
        configService.setProperty("MQTTeffectSpeed"+msgNum, effectSpeed.getValue());
        configService.setProperty("MQTTeffectTime"+msgNum, effectTime.getValue());
        configService.setProperty("MQTTxStart"+msgNum, xStart.getValue());
        configService.setProperty("MQTTyStart"+msgNum, yStart.getValue());
        configService.setProperty("MQTTxEnd"+msgNum, xEnd.getValue());
        configService.setProperty("MQTTyEnd"+msgNum, yEnd.getValue());
        configService.setProperty("MQTTbgImg"+msgNum, bgImg.getValue());
        configService.setProperty("MQTTtextColor"+msgNum, textColor.getText());
        configService.setProperty("MQTTbgColor"+msgNum, bgColor.getText());
        configService.setProperty("MQTTtext"+msgNum, msg.getText());
    }

    public void send() {
        String msg = makeMQTTMsg();

        save();
    }

    public void reset() {
        String msgNum = getMsgNum();
        configService.setProperty("MQTTdisplayControl"+msgNum, "ON");
        configService.setProperty("MQTTdisplayMethod"+msgNum, "Clear");
        configService.setProperty("MQTTcharCode"+msgNum, "한글 조합형");
        configService.setProperty("MQTTfontSize"+msgNum, "16(Standard)");
        configService.setProperty("MQTTfontGroup"+msgNum, "폰트그룹1");
        configService.setProperty("MQTTeffectIn"+msgNum, "정지효과");
        configService.setProperty("MQTTeffectInDirection"+msgNum, "방향없음");
        configService.setProperty("MQTTeffectOut"+msgNum, "사용안함");
        configService.setProperty("MQTTeffectOutDirection"+msgNum, "사용안함");
        configService.setProperty("MQTTeffectSpeed"+msgNum, "5");
        configService.setProperty("MQTTeffectTime"+msgNum, "2초");
        configService.setProperty("MQTTxStart"+msgNum, "0");
        configService.setProperty("MQTTxEnd"+msgNum, "0");
        configService.setProperty("MQTTyStart"+msgNum, "0");
        configService.setProperty("MQTTyEnd"+msgNum, "0");
        configService.setProperty("MQTTbgImg"+msgNum, "사용안함");
        configService.setProperty("MQTTtextColor"+msgNum, "1");
        configService.setProperty("MQTTbgColor"+msgNum, "0");

        doMsgSettings();
    }

    private String makeMQTTMsg() {
        String msgType = ((RadioButton) msgTypeGroup.getSelectedToggle()).getText();

        if (msgType.equals("realTimeMsg")) {
            try {
                makeRealTimeMsg();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            makePageMsg();
        }

        String pageMsgCntValue = pageMsgCnt.getValue();
        String section = ((RadioButton) sectionGroup.getSelectedToggle()).getText();
        String displayControlValue = displayControl.getValue();
        String displayMethodValue = displayMethod.getValue();
        String charCodesValue = charCodes.getValue();
        String fontSizeValue = fontSize.getValue();
        String fontGroupValue = fontGroup.getValue();
        String effectInValue = effectIn.getValue();
        String inDirectionValue = inDirection.getValue();
        String effectOutValue = effectOut.getValue();
        String outDirectionValue = outDirection.getValue();
        String effectSpeedValue = effectSpeed.getValue();
        String effectTimeValue = effectTime.getValue();
        String xStartValue = xStart.getValue();
        String yStartValue = yStart.getValue();
        String xEndValue = xEnd.getValue();
        String yEndValue = yEnd.getValue();
        String bgImgValue = bgImg.getValue();
        String textColorValue = textColor.getText();
        String bgColorValue = bgColor.getText();
        String text = msg.getText();

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

//        pageMsgCntValue = 1
//        section = 0
//        displayControlValue = ON
//        displayMethodValue = Clear
//        charCodesValue = 한글 조합형
//                fontSizeValue = 16(Standard)
//                fontGroupValue = 폰트그룹1
//        effectInValue = 정지효과
//        inDirectionValue = 방향없음
//        effectOutValue = 사용안함
//        outDirectionValue = 사용안함
//        effectSpeedValue = 5
//        effectTimeValue = 2초
//                xStartValue = 0
//        yStartValue = 0
//        xEndValue = 0
//        yEndValue = 0
//        bgImgValue = 사용안함
//        textColorValue = 1
//        bgColorValue = 0
//        text = 1234


        return null;
    }

    private String makeRealTimeMsg() throws JsonProcessingException {
        String common = "\"2.RTE058.6.1."+((RadioButton) sectionGroup.getSelectedToggle()).getText()+".";


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 최상위 JSON 객체 (순서 보장)
        Map<String, Object> jsonMessage = new LinkedHashMap<>();
        jsonMessage.put("MSG_TYPE", "TEXT");
        jsonMessage.put("MSG_VER", Integer.parseInt(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))));
        jsonMessage.put("MSG_ID", System.currentTimeMillis() / 1000);

        // MOID 객체 생성 (순서 보장)
        Map<String, Object> moid = new LinkedHashMap<>();
        moid.put("2.RTE058.6.1.1.1", Arrays.asList(displayMethod.getValue().equals("Clear") ? 1 : 0, "On".equals(displayMethod.getValue()) ? 99 : "Off".equals(displayMethod.getValue()) ? 0 : Integer.parseInt(displayMethod.getValue())));
        moid.put("2.RTE058.6.1.1.2", Arrays.asList(30, 10, 15, 3));
        moid.put("2.RTE058.6.1.1.3", Arrays.asList(Integer.parseInt(xStart.getValue()), Integer.parseInt(xEnd.getValue()), Integer.parseInt(yStart.getValue()), Integer.parseInt(yEnd.getValue())));
        moid.put("2.RTE058.6.1.1.4", bgImg.getValue().equals("사용안함") ? 0 : Integer.parseInt(bgImg.getValue()));

        // MOID 내부 리스트도 순서 보장
        List<Object> customList = new ArrayList<>();
        customList.add(Arrays.asList("안전", 2, 0, 0));  // 첫 번째 배열
        customList.add(Arrays.asList("확인", 4));        // 두 번째 배열
        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열

        moid.put("2.RTE058.6.1.1.5", customList);

        // 최종 JSON 메시지에 MOID 추가
        jsonMessage.put("MOID", moid);

        // JSON 문자열 변환 (순서 보장)
        return objectMapper.writeValueAsString(jsonMessage);
    }

    private void makePageMsg() {
    }




    private void setXY() {
        xStart.getItems().clear();
        xEnd.getItems().clear();
        yStart.getItems().clear();
        yEnd.getItems().clear();

        int xLimit = Integer.parseInt(configService.getProperty("displayColumnSize"));
        int yLimit = Integer.parseInt(configService.getProperty("displayRowSize"));

        for (int i = 0; i <= 4*xLimit; i++) {
            xStart.getItems().add(String.valueOf(4*i));
            xEnd.getItems().add(String.valueOf(4*i));
        }
        for (int i = 0; i <= 4*yLimit; i++) {
            yStart.getItems().add(String.valueOf(4*i));
            yEnd.getItems().add(String.valueOf(4*i));
        }

        xStart.setValue("0");
        yStart.setValue("0");
        xEnd.setValue("0");
        yEnd.setValue("0");
    }

    private void selectEffect(String effect, ChoiceBox<String> directionBox) {
        //배경.색상깜빡이기 작동되나 확인.
        switch (effect) {
            case "정지효과" ->
                    directionBox.setItems(FXCollections.observableArrayList("방향없음", "밝아지기", "어두워지기", "수평 반사", "수직 반사"));
            case "전체효과" -> directionBox.setItems(FXCollections.observableArrayList("무작위효과"));
            case "이동하기", "닦아내기", "블라인드" ->
                    directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위쪽", "아래"));
            case "커튼효과" -> directionBox.setItems(FXCollections.observableArrayList("수평밖으로", "수평안으로", "수직밖으로", "수직안으로"));
            case "확대효과" -> directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위쪽", "아래쪽", "오른쪽아래로"));
            case "회전효과" -> directionBox.setItems(FXCollections.observableArrayList("시계반대1", "시계방향1", "시계반대2", "시계방향2"));
            case "배경깜박이기" ->
                    directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "노란색", "흰색", "전체(순차적)"));
            case "색상깜박이기" ->
                    directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "노란색", "흰색", "전체(순차적)", "전체(동시에)"));
            case "3D 효과" -> directionBox.setItems(FXCollections.observableArrayList("왼쪽"));
            case "효과없음" -> directionBox.setDisable(true);
        }


        directionBox.getSelectionModel().selectFirst();
    }

    private void updateInDirections(String effect) {
        selectEffect(effect, inDirection);
    }

    private void updateOutDirections(String effect) {
        selectEffect(effect, outDirection);
    }

    //TODO : configService에 mqtt 메세지용 구분해서 하나 만들기.
    private void doMsgSettings() {
        String msgNum = getMsgNum();
        displayControl.setValue(configService.getProperty("MQTTdisplayControl"+msgNum));
        displayMethod.setValue(configService.getProperty("MQTTdisplayMethod"+msgNum));
        charCodes.setValue(configService.getProperty("MQTTcharCode"+msgNum));
        fontSize.setValue(configService.getProperty("MQTTfontSize"+msgNum));
        fontGroup.setValue(configService.getProperty("MQTTfontGroup"+msgNum));
        effectIn.setValue(configService.getProperty("MQTTeffectIn"+msgNum));
        inDirection.setValue(configService.getProperty("MQTTeffectInDirection"+msgNum));
        effectOut.setValue(configService.getProperty("MQTTeffectOut"+msgNum));
        outDirection.setValue(configService.getProperty("MQTTeffectOutDirection"+msgNum));
        effectSpeed.setValue(configService.getProperty("MQTTeffectSpeed"+msgNum));
        effectTime.setValue(configService.getProperty("MQTTeffectTime"+msgNum));
        xStart.setValue(configService.getProperty("MQTTxStart"+msgNum));
        yStart.setValue(configService.getProperty("MQTTyStart"+msgNum));
        xEnd.setValue(configService.getProperty("MQTTxEnd"+msgNum));
        yEnd.setValue(configService.getProperty("MQTTyEnd"+msgNum));
        bgImg.setValue(configService.getProperty("MQTTbgImg"+msgNum));
        textColor.setText(configService.getProperty("MQTTtextColor"+msgNum));
        bgColor.setText(configService.getProperty("MQTTbgColor"+msgNum));
        msg.setText(configService.getProperty("MQTTtext"+msgNum));
    }

    private String getMsgNum() {
        String i = "0";
        if (!realTimeMsg.isSelected()) {
            i= pageMsgCnt.getValue();
        }
        String j;
        if (section0.isSelected()){
            j="0";
        } else if (section1.isSelected()) {
            j="1";
        } else {
            j="2";
        }
        return i+j;
    }
}
