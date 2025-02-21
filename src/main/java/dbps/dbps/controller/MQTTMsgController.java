package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;

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

    public void send() throws UnsupportedEncodingException {
        String msg = makeMQTTMsg();
        String result = mqttManager.sendMsg(msg);

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
        String msg = makeHexMsg();

        byte[] sendByte = hexStringToByteArray(msg);

        String sendMsg = "{\"db_hex\":\""+ Base64.getEncoder().encodeToString(sendByte)+"\"}";

        sendMsg = "{\"db_hex\":\"EAIAAAJBABAD\"}";

        System.out.println(sendMsg);

        mqttManager.sendMsg(sendMsg);

        return null;
    }

    private String makeHexMsg(){
        String msgType = ((RadioButton) msgTypeGroup.getSelectedToggle()).getText();
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


        StringBuilder msg = new StringBuilder("10 02 ");


        //rs485인 경우 변경
        if (!isRS) {
            msg.append("00 ");
        } else {
            //rs485면 주소
            msg.append(String.format("%02X ", RS485_ADDR_NUM));
        }

        //msg 길이
        byte[] textBytes;
        try {
            if (charCodesValue.equals(bundle.getString("CombinationType")))
                textBytes = text.getBytes("MS949");
            else textBytes = text.getBytes(StandardCharsets.UTF_16BE);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        msg.append("00 ").append(String.format("%02x", (textBytes.length * 2) + 17));
        msg.append(" 94 ");

        //실시간메세지
        if (msgType.equals(bundle.getString("realTimeMsg"))) {
            msg.append("00 ");
        } else {
            msg.append(Integer.toHexString(Integer.parseInt(pageMsgCntValue))).append(" ");
        }

        //섹션번호
        msg.append("0").append(Integer.parseInt(section)).append(" ");

        //표시제어
        switch (displayControlValue) {
            case "Off":
                msg.append("00 ");
                break;
            case "ON":
                msg.append("63 ");
                break;
        }

        //표시방법
        msg.append(displayMethodValue.equals("Normal") ? "00 " : "01 ");

        //문자코드
        msg.append(charCodesValue.equals(bundle.getString("CombinationType")) ? "00 " : "01 ");

        //폰트크기
        fontSizeValue = fontSizeValue.replaceAll("[^0-9]", "");
        if (fontSizeValue.equals("14")){
            msg.append("01 ");
        }
        else msg.append("0").append((Integer.parseInt(fontSizeValue) / 4) - 1).append(" ");

        //입장효과
        msg.append(makeEffect(effectInValue, inDirectionValue));

        //퇴장효과
        msg.append(makeEffect(effectOutValue, outDirectionValue));

        //예비
        msg.append("00 ");

        //효과속도**
        msg.append(String.format("%02x", Integer.parseInt(effectSpeedValue.replaceAll("[^0-9]", "")))).append(" ");

        //유지시간
        msg.append(makeEffectTime(effectTimeValue));

        //x축 시작**
        msg.append(String.format("%02X", (Integer.parseInt(xStartValue) / 4))).append(" ");

        //y축 시작
        msg.append(String.format("%02X", (Integer.parseInt(yStartValue) / 4))).append(" ");

        //x축 끝
        msg.append(String.format("%02X", (Integer.parseInt(xEndValue) / 4))).append(" ");

        //y축 끝
        msg.append(String.format("%02X", (Integer.parseInt(yEndValue) / 4))).append(" ");

        //배경이미지
        msg.append(bgImgValue.equals(bundle.getString("notUsed")) ? "00 " : String.format("%02d ", Integer.parseInt(bgImgValue)));

        //글자
        for (int i = 0; i < text.length(); i++) {
            String tmp = "";
            if (bgColorValue.length()>i){
                tmp+=String.valueOf(bgColorValue.charAt(i));
            }else {
                tmp+=String.valueOf(bgColorValue.charAt(bgColorValue.length()-1));
            }

            if (textColorValue.length()>i) {
                tmp += String.valueOf(textColorValue.charAt(i));
            } else{
                tmp += String.valueOf(textColorValue.charAt(textColorValue.length()-1));
            }

            int add;
            switch (fontGroupValue){
                case "폰트그룹1"-> add = 0;
                case "폰트그룹2"-> add = 8;
                case "폰트그룹3"-> add = 128;
                default -> add = 136;
            }

            int tmpValue = Integer.parseInt(tmp, 16);
            String resultHex;

            resultHex = String.format("%02X ", tmpValue + add);
            if (String.valueOf(text.charAt(i)).getBytes(Charset.forName("MS949")).length!=1){
                resultHex += String.format("%02X ", 0);
            }

            msg.append(resultHex);
            if (charCodes.getValue().equals(bundle.getString("UTF16"))&&String.valueOf(text.charAt(i)).getBytes(Charset.forName("MS949")).length==1){
                msg.append(String.format("%02X ", 0));
            }
        }



        msg.append(bytesToHex(textBytes, textBytes.length));

        msg.append("10 03");

        return msg.toString();
    }

    private String makeEffect(String effect, String direction){
        if (effect.equals(bundle.getString("noEffect")) || effect.equals(bundle.getString("notUsed"))) {
            return "00 ";
        } else if (effect.equals(bundle.getString("staticEffect"))) {
            if (direction.equals(bundle.getString("noDirection"))) {
                return "01 ";
            } else if (direction.equals(bundle.getString("brighten"))) {
                return "02 ";
            } else if (direction.equals(bundle.getString("darken"))) {
                return "03 ";
            } else if (direction.equals(bundle.getString("horizontalReflection"))) {
                return "04 ";
            } else {
                return "05 ";
            }
        } else if (effect.equals(bundle.getString("move"))) {
            if (direction.equals(bundle.getString("left"))) {
                return "06 ";
            } else if (direction.equals(bundle.getString("right"))) {
                return "07 ";
            } else if (direction.equals(bundle.getString("up"))) {
                return "08 ";
            } else {
                return "09 ";
            }
        } else if (effect.equals(bundle.getString("wipe"))) {
            if (direction.equals(bundle.getString("left"))) {
                return "0C ";
            } else if (direction.equals(bundle.getString("right"))) {
                return "0D ";
            } else if (direction.equals(bundle.getString("up"))) {
                return "0E ";
            } else {
                return "0F ";
            }
        } else if(effect.equals(bundle.getString("blind"))){
            if (direction.equals(bundle.getString("left"))) {
                return "12 ";
            } else if (direction.equals(bundle.getString("right"))) {
                return "13 ";
            } else if (direction.equals(bundle.getString("up"))) {
                return "14 ";
            } else {
                return "15 ";
            }
        }
        else if (effect.equals(bundle.getString("curtainEffect"))) {
            if (direction.equals(bundle.getString("horizontalOutward"))) {
                return "18 ";
            } else if (direction.equals(bundle.getString("horizontalInward"))) {
                return "19 ";
            } else if (direction.equals(bundle.getString("verticalOutward"))) {
                return "1A ";
            } else {
                return "1B ";
            }
        } else if (effect.equals(bundle.getString("zoomEffect"))) {
            if (direction.equals(bundle.getString("left"))) {
                return "23 ";
            } else if (direction.equals(bundle.getString("right"))) {
                return "24 ";
            } else if (direction.equals(bundle.getString("up"))) {
                return "25 ";
            } else if (direction.equals(bundle.getString("down"))) {
                return "26 ";
            } else {
                return "27 ";
            }
        } else if (effect.equals(bundle.getString("rotateEffect"))) {
            if (direction.equals(bundle.getString("counterclockwise1"))) {
                return "29 ";
            } else if (direction.equals(bundle.getString("clockwise1"))) {
                return "28 ";
            } else if (direction.equals(bundle.getString("counterclockwise2"))) {
                return "2B ";
            } else {
                return "2A ";
            }
        } else if (effect.equals(bundle.getString("backgroundFlash"))) {
            if (direction.equals(bundle.getString("red"))) {
                return "2C ";
            } else if (direction.equals(bundle.getString("green"))) {
                return "2D ";
            } else if (direction.equals(bundle.getString("blue"))) {
                return "2E ";
            } else if (direction.equals(bundle.getString("white"))) {
                return "2F ";
            } else {
                return "30 ";
            }
        } else if (effect.equals(bundle.getString("textFlash"))){
            if (direction.equals(bundle.getString("red"))) {
                return "31 ";
            } else if (direction.equals(bundle.getString("green"))) {
                return "32 ";
            } else if (direction.equals(bundle.getString("blue"))) {
                return "33 ";
            } else if (direction.equals(bundle.getString("white"))) {
                return "34 ";
            } else if (direction.equals(bundle.getString("allSequential"))) {
                return "35 ";
            } else {
                return "37 ";
            }
        }
        else if (effect.equals(bundle.getString("3DEffect"))) {
            return "36 ";
        } else {
            return "7A ";
        }

    }

    private String makeEffectTime(String effectTimeValue) {
        if (effectTimeValue.contains(bundle.getString("sec"))) {
            return String.format("%02x ", Integer.parseInt(effectTimeValue.replaceAll("[^0-9]", "")));
        }
        if (effectTimeValue.equals(bundle.getString("2min"))) {
            return "5A ";
        } else if (effectTimeValue.equals(bundle.getString("3min"))) {
            return "5B ";
        } else if (effectTimeValue.equals(bundle.getString("5min"))) {
            return "5C ";
        } else if (effectTimeValue.equals(bundle.getString("10min"))) {
            return "5D ";
        } else if (effectTimeValue.equals(bundle.getString("30min"))) {
            return "5E ";
        } else if (effectTimeValue.equals(bundle.getString("1hr"))) {
            return "5F ";
        } else if (effectTimeValue.equals(bundle.getString("3hr"))) {
            return "60 ";
        } else if (effectTimeValue.equals(bundle.getString("5hr"))) {
            return "61 ";
        } else if (effectTimeValue.equals(bundle.getString("9hr"))) {
            return "62 ";
        } else {
            return "00 ";
        }

    }




    //Todo 도로교통공단 버전
//    String common = "\"2.RTE058.6.1."+((RadioButton) sectionGroup.getSelectedToggle()).getText()+".";
//
//    ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//    // 최상위 JSON 객체 (순서 보장)
//    Map<String, Object> jsonMessage = new LinkedHashMap<>();
//        jsonMessage.put("MSG_TYPE", "TEXT");
//        jsonMessage.put("MSG_VER", Integer.parseInt(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))));
//        jsonMessage.put("MSG_ID", System.currentTimeMillis() / 1000);
//
//
//    // MOID 객체 생성 (순서 보장)
//    Map<String, Object> moid = new LinkedHashMap<>();
//        moid.put(common+"1", Arrays.asList(displayMethod.getValue().equals("Clear") ? 1 : 0, "On".equals(displayControl.getValue()) ? 99 : "Off".equals(displayControl.getValue()) ? 0 : Integer.parseInt(displayControl.getValue())));
//        moid.put(common+"2", Arrays.asList(30, 10, 15, 3));
//        moid.put(common+"3", Arrays.asList(Integer.parseInt(xStart.getValue()), Integer.parseInt(xEnd.getValue()), Integer.parseInt(yStart.getValue()), Integer.parseInt(yEnd.getValue())));
//        moid.put(common+"4", bgImg.getValue().equals("사용안함") ? 0 : Integer.parseInt(bgImg.getValue()));
//
//    // MOID 내부 리스트도 순서 보장
//    List<Object> customList = new ArrayList<>();
//        customList.add(Arrays.asList("안전", 2, 0, 0));  // 첫 번째 배열
//        customList.add(Arrays.asList("확인", 4));        // 두 번째 배열
//        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열
//        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열
//        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열
//        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열
//        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열
//        customList.add(Collections.singletonList("부탁드립니다")); // 세 번째 배열
//
//        moid.put(common+"5", customList);
//
//    // 최종 JSON 메시지에 MOID 추가
//        jsonMessage.put("MOID", moid);




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
