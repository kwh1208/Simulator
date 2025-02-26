package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;
import static java.lang.Integer.parseInt;

public class HEXMessageController {

    public ProgressIndicator progressIndicator;
    public RadioButton hexChkBox;
    public RadioButton ascChkBox;
    HexMsgTransceiver hexMsgTransceiver;
    HexMsgService hexMsgService;
    AsciiMsgTransceiver ascMsgTransceiver;

    @FXML
    private AnchorPane HEXMsgAP;

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
    private TextField msgPreview;

    ConfigService configService;

    ToggleGroup msgTypeGroup = new ToggleGroup();

    ToggleGroup sectionGroup = new ToggleGroup();

    ToggleGroup msgType = new ToggleGroup();

    ResourceBundle bundle;

    @FXML
    private void initialize() {
        configService = ConfigService.getInstance();
        HEXMsgAP.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/hexMessage.css")).toExternalForm());
        bundle= ResourceManager.getInstance().getBundle();
        hexMsgService = HexMsgService.getInstance();
        hexMsgService.setPageMsgCnt(pageMsgCnt);
        hexMsgService.setXStart(xStart);
        hexMsgService.setYStart(yStart);
        hexMsgService.setXEnd(xEnd);
        hexMsgService.setYEnd(yEnd);

        hexChkBox.setToggleGroup(msgType);
        ascChkBox.setToggleGroup(msgType);

        ascChkBox.setSelected(true);
        ascMsgTransceiver = AsciiMsgTransceiver.getInstance();


        realTimeMsg.setToggleGroup(msgTypeGroup);
        pageMsg.setToggleGroup(msgTypeGroup);

        section0.setToggleGroup(sectionGroup);
        section1.setToggleGroup(sectionGroup);
        section2.setToggleGroup(sectionGroup);

        realTimeMsg.setSelected(true);
        if (configService.getProperty("isHexRealTime").equals("0")){
            realTimeMsg.setSelected(true);
        }
        else{
            pageMsg.setSelected(true);
            pageMsgCnt.setVisible(true);
            pageCntLabel.setVisible(true);
            pageMsgCnt.setValue(configService.getProperty("isHexRealTime"));
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

        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        doMsgSettings();

        saveConfig();

        setXY();
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

    private void doMsgSettings() {
        String msgNum = getMsgNum();

        displayControl.setValue(configService.getProperty("displayControl"+msgNum));
        displayMethod.setValue(configService.getProperty("displayMethod"+msgNum));
        charCodes.setValue(configService.getProperty("charCode"+msgNum));
        fontSize.setValue(configService.getProperty("fontSize"+msgNum));
        fontGroup.setValue(configService.getProperty("fontGroup"+msgNum));
        effectIn.setValue(configService.getProperty("effectIn"+msgNum));
        inDirection.setValue(configService.getProperty("effectInDirection"+msgNum));
        effectOut.setValue(configService.getProperty("effectOut"+msgNum));
        outDirection.setValue(configService.getProperty("effectOutDirection"+msgNum));
        effectSpeed.setValue(configService.getProperty("effectSpeed"+msgNum));
        effectTime.setValue(configService.getProperty("effectTime"+msgNum));
        xStart.setValue(configService.getProperty("xStart"+msgNum));
        yStart.setValue(configService.getProperty("yStart"+msgNum));
        xEnd.setValue(configService.getProperty("xEnd"+msgNum));
        yEnd.setValue(configService.getProperty("yEnd"+msgNum));
        bgImg.setValue(configService.getProperty("bgImg"+msgNum));
        textColor.setText(configService.getProperty("textColor"+msgNum));
        bgColor.setText(configService.getProperty("bgColor"+msgNum));
        msgPreview.setText(configService.getProperty("text"+msgNum));
    }

    public void save() {
        String msgNum = getMsgNum();
        configService.setProperty("isHexRealTime", "0");
        if (pageMsg.isSelected()) {
            configService.setProperty("isHexRealTime", pageMsgCnt.getValue());
        }
        configService.setProperty("displayControl"+msgNum, displayControl.getValue());
        configService.setProperty("displayMethod"+msgNum, displayMethod.getValue());
        configService.setProperty("charCode"+msgNum, charCodes.getValue());
        configService.setProperty("fontSize"+msgNum, fontSize.getValue());
        configService.setProperty("fontGroup"+msgNum, fontGroup.getValue());
        configService.setProperty("effectIn"+msgNum, effectIn.getValue());
        configService.setProperty("effectInDirection"+msgNum, inDirection.getValue());
        configService.setProperty("effectOut"+msgNum, effectOut.getValue());
        configService.setProperty("effectOutDirection"+msgNum, outDirection.getValue());
        configService.setProperty("effectSpeed"+msgNum, effectSpeed.getValue());
        configService.setProperty("effectTime"+msgNum, effectTime.getValue());
        configService.setProperty("xStart"+msgNum, xStart.getValue());
        configService.setProperty("yStart"+msgNum, yStart.getValue());
        configService.setProperty("xEnd"+msgNum, xEnd.getValue());
        configService.setProperty("yEnd"+msgNum, yEnd.getValue());
        configService.setProperty("bgImg"+msgNum, bgImg.getValue());
        configService.setProperty("textColor"+msgNum, textColor.getText());
        configService.setProperty("bgColor"+msgNum, bgColor.getText());
        configService.setProperty("text"+msgNum, msgPreview.getText());
    }

    public void send() {
        if (hexChkBox.isSelected()){
            String msg = makeHexMsg();

            hexMsgTransceiver.sendMessages(msg, progressIndicator);

        }
        else {
            String sendMsg = makeASCMsg();
            ascMsgTransceiver.sendMessages(sendMsg, false, progressIndicator);
        }
        save();
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
        String text = msgPreview.getText();


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
            case "On":
                msg.append("63 ");
                break;
            default:
                msg.append(displayControlValue).append(" ");
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

    public void reset() {
        String msgNum = getMsgNum();
        configService.setProperty("displayControl"+msgNum, "On");
        configService.setProperty("displayMethod"+msgNum, "Clear");
        configService.setProperty("charCode"+msgNum, "한글 조합형");
        configService.setProperty("fontSize"+msgNum, "16(Standard)");
        configService.setProperty("fontGroup"+msgNum, "폰트그룹1");
        configService.setProperty("effectIn"+msgNum, "정지효과");
        configService.setProperty("effectInDirection"+msgNum, "방향없음");
        configService.setProperty("effectOut"+msgNum, "사용안함");
        configService.setProperty("effectOutDirection"+msgNum, "사용안함");
        configService.setProperty("effectSpeed"+msgNum, "5");
        configService.setProperty("effectTime"+msgNum, "2초");
        configService.setProperty("xStart"+msgNum, "0");
        configService.setProperty("xEnd"+msgNum, "0");
        configService.setProperty("yStart"+msgNum, "0");
        configService.setProperty("yEnd"+msgNum, "0");
        configService.setProperty("bgImg"+msgNum, "사용안함");
        configService.setProperty("textColor"+msgNum, "1");
        configService.setProperty("bgColor"+msgNum, "0");

        doMsgSettings();
    }

    public void preview(MouseEvent mouseEvent) {
    }

    private void saveConfig() {
        displayControl.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("displayControl"+getMsgNum(), newValue));

        displayMethod.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("displayMethod"+getMsgNum(), newValue)
        );
        charCodes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("charCodes"+getMsgNum(), newValue)
        );
        fontSize.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("fontSize"+getMsgNum(), newValue)
        );
        fontGroup.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("fontGroup"+getMsgNum(), newValue)
        );
        effectIn.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectIn"+getMsgNum(), newValue)
        );
        effectOut.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectOut"+getMsgNum(), newValue)
        );
        outDirection.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("outDirection"+getMsgNum(), newValue)
        );
        effectSpeed.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectSpeed"+getMsgNum(), newValue)
        );
        effectTime.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectTime"+getMsgNum(), newValue)
        );
        xStart.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue!=null) configService.setProperty("xStart"+getMsgNum(), newValue);
                }
        );
        yStart.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue!=null) configService.setProperty("yStart"+getMsgNum(), newValue);
                }
        );
        xEnd.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue!=null) configService.setProperty("xEnd"+getMsgNum(), newValue);
                }
        );
        yEnd.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue!=null) configService.setProperty("yEnd"+getMsgNum(), newValue);
                }
        );
        bgImg.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("bgImg"+getMsgNum(), newValue)
        );
    }

    //Todo 나중에 asc랑 합치면서 사용할 함수
    private String makeASCMsg() {
        StringBuilder sendMsg = new StringBuilder("![00");
        if (realTimeMsg.isSelected()) sendMsg.append("0/P00");
        else sendMsg.append("1/P").append(String.format("%02d", Integer.parseInt(pageMsgCnt.getValue())-1));

        sendMsg.append(String.format("%02d", Integer.parseInt(((RadioButton) sectionGroup.getSelectedToggle()).getText())));

        sendMsg.append("/D").append(setDText(displayControl.getValue(), displayMethod.getValue()));
        sendMsg.append("/F").append(setFText(charCodes.getValue(), fontSize.getValue()));
        sendMsg.append("/E").append(setEText(effectIn.getValue(), inDirection.getValue()));
        sendMsg.append(setEText(effectOut.getValue(), outDirection.getValue()));
        sendMsg.append("/S").append(setSText(effectSpeed.getValue(), effectTime.getValue()));
        sendMsg.append("/X").append(String.format("%02d", parseInt(xStart.getValue()) / 4)).append(String.format("%02d", parseInt(xEnd.getValue()) / 4));
        sendMsg.append("/Y").append(String.format("%02d", parseInt(yStart.getValue()) / 4)).append(String.format("%02d", parseInt(yEnd.getValue()) / 4));
        sendMsg.append("/B").append(bgImg.getValue().equals("사용안함") ? "000" : String.format("%03d", parseInt(bgImg.getValue())));
        sendMsg.append("/T").append(Integer.parseInt(fontGroup.getValue().replaceAll("[^\\d]", "")) - 1);

        int length = msgPreview.getText().length();
        String text = msgPreview.getText();
        String fgColors = textColor.getText();
        String bgColors = bgColor.getText();

        int currentColor = fgColors.charAt(0) - '0';
        int currentBg = bgColors.charAt(0) - '0';

        sendMsg.append("/C").append(currentColor).append("/G").append(currentBg);

        for (int i = 0; i < length; i++) {
            int nextColor = (i < fgColors.length()) ? fgColors.charAt(i) - '0' : currentColor;
            int nextBg = (i < bgColors.length()) ? bgColors.charAt(i) - '0' : currentBg;

            if (i > 0 && (nextColor != currentColor || nextBg != currentBg)) {
                // 색상이 변경되면 새로운 C/G 추가
                sendMsg.append("/C").append(nextColor).append("/G").append(nextBg);
                currentColor = nextColor;
                currentBg = nextBg;
            }

            // 문자 추가
            sendMsg.append(text.charAt(i));
        }

        sendMsg.append("!]");

        return sendMsg.toString();

    }

    private String setDText(String value1, String value2) {
        String result = "";
        switch (value1) {
            case "Off":
                result += "00";
                break;
            case "On":
                result += "99";
                break;
            default:
                int num = Integer.parseInt(value1);
                if (num >= 1 && num <= 9) {
                    result += String.format("0%d", num);
                } else if (num >= 10 && num <= 90 && num % 10 == 0) {
                    result += String.format("%d", num / 10 + 9);
                }
                break;
        }
        if (value2.equals("Normal")) {
            result += "00";
        } else result += "01";
        return result;
    }

    private String setFText(String value1, String value2) {
        String result = "";
        if (value1.equals(bundle.getString("CombinationType"))) {
            result += "00";
        } else {
            result += "01";
        }

        if (value2.equals("16(Standard)")) {
            result += "03";
        } else if (value2.equals("14")) {
            result += "01";
        } else {
            result += String.format("%02d", (Integer.parseInt(value2) -4) / 4);
        }

        return result;
    }

    private String setEText(String value1, String value2) {
        if (value1.equals(bundle.getString("noEffect"))) {
            return "00";
        } else if (value1.equals(bundle.getString("staticEffect"))) {
            if (value2.equals(bundle.getString("noDirection"))) {
                return "01";
            } else if (value2.equals(bundle.getString("brighten"))) {
                return "02";
            } else if (value2.equals(bundle.getString("darken"))) {
                return "03";
            } else if (value2.equals(bundle.getString("horizontalReflection"))) {
                return "04";
            } else if (value2.equals(bundle.getString("verticalReflection"))) {
                return "05";
            }
        } else if (value1.equals(bundle.getString("move"))) {
            if (value2.equals(bundle.getString("left"))) {
                return "06";
            } else if (value2.equals(bundle.getString("right"))) {
                return "07";
            } else if (value2.equals(bundle.getString("up"))) {
                return "08";
            } else if (value2.equals(bundle.getString("down"))) {
                return "09";
            }

        } else if (value1.equals(bundle.getString("wipe"))) {
            if (value2.equals(bundle.getString("left"))) {
                return "12";
            } else if (value2.equals(bundle.getString("right"))) {
                return "13";
            } else if (value2.equals(bundle.getString("up"))) {
                return "14";
            } else if (value2.equals(bundle.getString("down"))) {
                return "15";
            }

        } else if (value1.equals(bundle.getString("blind"))) {
            if (value2.equals(bundle.getString("left"))) {
                return "18";
            } else if (value2.equals(bundle.getString("right"))) {
                return "19";
            } else if (value2.equals(bundle.getString("up"))) {
                return "20";
            } else if (value2.equals(bundle.getString("down"))) {
                return "21";
            }

        } else if (value1.equals(bundle.getString("curtainEffect"))) {
            if (value2.equals(bundle.getString("horizontalOutward"))) {
                return "24";
            } else if (value2.equals(bundle.getString("horizontalInward"))) {
                return "25";
            } else if (value2.equals(bundle.getString("verticalOutward"))) {
                return "26";
            } else if (value2.equals(bundle.getString("verticalInward"))) {
                return "27";
            }

        } else if (value1.equals(bundle.getString("zoomEffect"))) {
            if (value2.equals(bundle.getString("left"))) {
                return "35";
            } else if (value2.equals(bundle.getString("right"))) {
                return "36";
            } else if (value2.equals(bundle.getString("up"))) {
                return "37";
            } else if (value2.equals(bundle.getString("down"))) {
                return "38";
            } else if (value2.equals(bundle.getString("Bottom-Right"))) {
                return "39";
            }
            //네오시스코리아 자석거치대 주문을 못해서 추가 주문원함

        } else if (value1.equals(bundle.getString("rotateEffect"))) {
            if (value2.equals(bundle.getString("counterclockwise1"))) {
                return "40";
            } else if (value2.equals(bundle.getString("clockwise1"))) {
                return "41";
            } else if (value2.equals(bundle.getString("counterclockwise2"))) {
                return "42";
            } else if (value2.equals(bundle.getString("clockwise2"))) {
                return "43";
            }

        } else if (value1.equals(bundle.getString("backgroundFlash"))) {
            if (value2.equals(bundle.getString("red"))) {
                return "44";
            } else if (value2.equals(bundle.getString("green"))) {
                return "45";
            } else if (value2.equals(bundle.getString("blue"))) {
                return "46";
            } else if (value2.equals(bundle.getString("white"))) {
                return "47";
            } else if (value2.equals(bundle.getString("allSequential"))) {
                return "48";
            }
        } else if (value1.equals(bundle.getString("textFlash"))) {
            if (value2.equals(bundle.getString("red"))) {
                return "49";
            } else if (value2.equals(bundle.getString("green"))) {
                return "50";
            } else if (value2.equals(bundle.getString("blue"))) {
                return "51";
            } else if (value2.equals(bundle.getString("white"))) {
                return "52";
            } else if (value2.equals(bundle.getString("allSequential"))) {
                return "53";
            } else if (value2.equals(bundle.getString("allSimultaneous"))) {
                return "55";
            }
        } else if (value1.equals(bundle.getString("randomEffect"))) return "122";
        return "54";//3D 효과, 왼쪽
    }

    private String setSText(String value1, String value2) {
        String result = "";
        result += String.format("%02d", Integer.parseInt(value1.replaceAll("[^\\d]", "")));


        if (value2.contains(bundle.getString("sec"))) {
            result += String.format("%02d", (int) (Double.parseDouble(value2.replaceAll("[^\\d.]", "")) * 2));
        } else {
            if (value2.equals(bundle.getString("2min"))) {
                result += "90";
            } else if (value2.equals(bundle.getString("3min"))) {
                result += "91";
            } else if (value2.equals(bundle.getString("5min"))) {
                result += "92";
            } else if (value2.equals(bundle.getString("10min"))) {
                result += "93";
            } else if (value2.equals(bundle.getString("30min"))) {
                result += "94";
            } else if (value2.equals(bundle.getString("1hr"))) {
                result += "95";
            } else if (value2.equals(bundle.getString("3hr"))) {
                result += "96";
            } else if (value2.equals(bundle.getString("5hr"))) {
                result += "97";
            } else if (value2.equals(bundle.getString("9hr"))) {
                result += "98";
            }
        }
        return result;
    }
}
