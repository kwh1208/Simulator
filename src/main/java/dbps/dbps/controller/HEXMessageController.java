package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static dbps.dbps.Constants.*;

public class HEXMessageController {

    HexMsgTransceiver hexMsgTransceiver;

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

    @FXML
    private void initialize() {
        configService = ConfigService.getInstance();
        HEXMsgAP.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/hexMessage.css")).toExternalForm());



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
            xStart.getItems().add(String.valueOf(i));
            xEnd.getItems().add(String.valueOf(i));
        }
        for (int i = 0; i <= 4*yLimit; i++) {
            yStart.getItems().add(String.valueOf(i));
            yEnd.getItems().add(String.valueOf(i));
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
        configService.setProperty("isHexRealTime", pageMsgCnt.getValue());
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
        String msg = makeHexMsg();

        hexMsgTransceiver.sendMessages(msg);
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
            if (charCodesValue.equals("한글 조합형"))
                textBytes = text.getBytes("KSC5601");
            else textBytes = text.getBytes(StandardCharsets.UTF_16BE);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        msg.append("00 ").append(String.format("%02x", (textBytes.length * 2) + 17));
        msg.append(" 94 ");

        //실시간메세지
        if (msgType.equals("실시간 메세지")) {
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
        msg.append(charCodesValue.equals("한글 조합형") ? "00 " : "01 ");

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
        msg.append(String.format("%02d", (Integer.parseInt(xStartValue) / 4))).append(" ");

        //y축 시작
        msg.append(String.format("%02d", (Integer.parseInt(yStartValue) / 4))).append(" ");

        //x축 끝
        msg.append(String.format("%02d", (Integer.parseInt(xEndValue) / 4))).append(" ");

        //y축 끝
        msg.append(String.format("%02d", (Integer.parseInt(yEndValue) / 4))).append(" ");

        //배경이미지
        msg.append(bgImgValue.equals("사용안함") ? "00 " : String.format("%02d ", Integer.parseInt(bgImgValue)));

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
                case "FontGroup1"-> add = 8;
                case "FontGroup2"-> add = 128;
                case "FontGroup3"-> add = 136;
                default -> add = 0;
            }

            int tmpValue = Integer.parseInt(tmp, 16);
            String resultHex;

            resultHex = String.format("%02X ", tmpValue + add);
            if (String.valueOf(text.charAt(i)).getBytes(Charset.forName("EUC-KR")).length!=1){
                resultHex += String.format("%02X ", 0);
            }

            msg.append(resultHex);
            if (charCodes.getValue().equals("UTF16유니코드")&&String.valueOf(text.charAt(i)).getBytes(Charset.forName("EUC-KR")).length==1){
                msg.append(String.format("%02X ", 0));
            }
        }



        msg.append(bytesToHex(textBytes, textBytes.length));

        msg.append("10 03");

        return msg.toString();
    }

    private String makeEffectTime(String effectTimeValue) {
        if (effectTimeValue.contains("초")) {
            return String.format("%02x ", Integer.parseInt(effectTimeValue.replaceAll("[^0-9]", "")));
        }
        switch (effectTimeValue){
            case "2분"->{
                return "5A ";
            }
            case "3분"->{
                return "5B ";
            }
            case "5분"->{
                return "5C ";
            }
            case "10분"->{
                return "5D ";
            }
            case "30분"->{
                return "5E ";
            }
            case "1시간"-> {
                return "5F ";
            }
            case "3시간"-> {
                return "60 ";
            }
            case "5시간"-> {
                return "61 ";
            }
            case "9시간"-> {
                return "62 ";
            }
            default -> {
                return "00 ";
            }
        }
    }

    private String makeEffect(String effect, String direction){
        switch (effect){
            case "효과없음", "사용안함" ->{
                return "00 ";
            }
            case "정지효과"-> {
                return switch (direction) {
                    case "방향없음" -> "01 ";
                    case "밝아지기" -> "02 ";
                    case "어두워지기" -> "03 ";
                    case "수평반사" -> "04 ";
                    default -> "05 ";
                };
            }
            case "이동하기"->{
                return switch (direction) {
                    case "왼쪽" -> "06 ";
                    case "오른쪽" -> "07 ";
                    case "위쪽" -> "08 ";
                    default -> "09 ";
                };
            }
            case "닦아내기"->{
                return switch (direction) {
                    case "왼쪽" -> "0C ";
                    case "오른쪽" -> "0D ";
                    case "위쪽" -> "0E ";
                    default -> "0F ";
                };
            }
            case "커튼효과"->{
                return switch (direction) {
                    case "수평밖으로" -> "18 ";
                    case "수평안으로" -> "19 ";
                    case "수직밖으로" -> "1A ";
                    default -> "1B ";
                };
            } case "확대효과"->{
                return switch (direction) {
                    case "왼쪽" -> "23 ";
                    case "오른쪽" -> "24 ";
                    case "위쪽" -> "25 ";
                    case "아래쪽" -> "26 ";
                    default -> "27 ";
                };
            } case "회전효과"->{
                return switch (direction) {
                    case "시계반대1" -> "28 ";
                    case "시계방향1" -> "29 ";
                    case "시계반대2" -> "2A ";
                    default -> "2B ";
                };
            } case "배경깜빡이기"->{
                return switch (direction) {
                    case "빨강" -> "31 ";
                    case "초록" -> "32 ";
                    case "파랑" -> "33 ";
                    case "흰색" -> "34 ";
                    case "모든색상(순차적)" -> "35 ";
                    default -> "36 ";
                };
            } case "3D 효과"->{
                return "37 ";
            }default -> {//무작위
                return "7A ";
            }
        }
    }

    private void selectEffect(String effect, ChoiceBox<String> directionBox) {
        switch (effect) {
            case "정지효과" ->
                    directionBox.setItems(FXCollections.observableArrayList("방향없음", "밝아지기", "어두워지기", "수평 반사", "수직 반사"));
            case "전체효과" -> directionBox.setItems(FXCollections.observableArrayList("무작위효과"));
            case "이동하기", "닦아내기", "블라인드" ->
                    directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위", "아래"));
            case "커튼효과" -> directionBox.setItems(FXCollections.observableArrayList("수평밖으로", "수평안으로", "수직밖으로", "수직안으로"));
            case "확대효과" -> directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위쪽", "아래쪽", "오른쪽아래로"));
            case "회전효과" -> directionBox.setItems(FXCollections.observableArrayList("시계반대1", "시계방향1", "시계반대2", "시계방향2"));
            case "배경깜빡이기" ->
                    directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "파란색", "흰색", "전체(순차적)"));
            case "색상깜빡이기" ->
                    directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "파란색", "흰색", "전체(순차적)", "전체(동시에)"));
            case "3D 효과" -> directionBox.setItems(FXCollections.observableArrayList("왼쪽"));
            case "효과없음" -> directionBox.setDisable(true);
        }


        directionBox.getSelectionModel().selectFirst(); // 첫 번째 항목 선택
    }

    private void updateInDirections(String effect) {
        selectEffect(effect, inDirection);
    }

    private void updateOutDirections(String effect) {
        selectEffect(effect, outDirection);
    }

    public void reset() {
        String msgNum = getMsgNum();
        configService.setProperty("displayControl"+msgNum, "ON");
        configService.setProperty("displayMethod"+msgNum, "Normal");
        configService.setProperty("charCode"+msgNum, "한글 조합형");
        configService.setProperty("fontSize"+msgNum, "16(Standard)");
        configService.setProperty("fontGroup"+msgNum, "FontGroup0");
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
}
