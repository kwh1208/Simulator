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
        HEXMsgAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/hexMessage.css").toExternalForm());

        realTimeMsg.setToggleGroup(msgTypeGroup);
        pageMsg.setToggleGroup(msgTypeGroup);

        section0.setToggleGroup(sectionGroup);
        section1.setToggleGroup(sectionGroup);
        section2.setToggleGroup(sectionGroup);

        realTimeMsg.setSelected(true);
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

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            doMsgSettings();
        });

        sectionGroup.selectedToggleProperty().addListener((observable, oldValue, newValue)->{
            doMsgSettings();
        });

        effectOut.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateOutDirections(newValue));
        effectIn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateInDirections(newValue));

        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        doMsgSettings();
    }

    private void doMsgSettings() {
        int sectionNum = 0;
        int pageMsgNum = pageMsgCnt.getSelectionModel().getSelectedIndex()+1;
        if (section1.isSelected()) {
            sectionNum = 1;
        } else if (section2.isSelected()){
            sectionNum = 2;
        }
        if (realTimeMsg.isSelected()) {
            pageMsgNum = 0;
        }
        displayControl.setValue(configService.getProperty("displayControl"+pageMsgNum+sectionNum));
        displayMethod.setValue(configService.getProperty("displayMethod"+pageMsgNum+sectionNum));
        charCodes.setValue(configService.getProperty("charCode"+pageMsgNum+sectionNum));
        fontSize.setValue(configService.getProperty("fontSize"+pageMsgNum+sectionNum));
        fontGroup.setValue(configService.getProperty("fontGroup"+pageMsgNum+sectionNum));
        effectIn.setValue(configService.getProperty("effectIn"+pageMsgNum+sectionNum));
        inDirection.setValue(configService.getProperty("effectInDirection"+pageMsgNum+sectionNum));
        effectOut.setValue(configService.getProperty("effectOut"+pageMsgNum+sectionNum));
        outDirection.setValue(configService.getProperty("effectOutDirection"+pageMsgNum+sectionNum));
        effectSpeed.setValue(configService.getProperty("effectSpeed"+pageMsgNum+sectionNum));
        effectTime.setValue(configService.getProperty("effectTime"+pageMsgNum+sectionNum));
        xStart.setValue(configService.getProperty("xStart"+pageMsgNum+sectionNum));
        yStart.setValue(configService.getProperty("yStart"+pageMsgNum+sectionNum));
        xEnd.setValue(configService.getProperty("xEnd"+pageMsgNum+sectionNum));
        yEnd.setValue(configService.getProperty("yEnd"+pageMsgNum+sectionNum));
        bgImg.setValue(configService.getProperty("bgImg"+pageMsgNum+sectionNum));
        textColor.setText(configService.getProperty("textColor"+pageMsgNum+sectionNum));
        bgColor.setText(configService.getProperty("bgColor"+pageMsgNum+sectionNum));
        msgPreview.setText(configService.getProperty("text"+pageMsgNum+sectionNum));
    }

    public void save(MouseEvent mouseEvent) {
        int sectionNum = 0;
        int pageMsgNum = pageMsgCnt.getSelectionModel().getSelectedIndex();
        if (section1.isSelected()) {
            sectionNum = 1;
        } else if (section2.isSelected()){
            sectionNum = 2;
        }
        if (realTimeMsg.isSelected()) {
            pageMsgNum = 0;
        }

        configService.setProperty("displayControl"+pageMsgNum+sectionNum, displayControl.getValue());
        configService.setProperty("displayMethod"+pageMsgNum+sectionNum, displayMethod.getValue());
        configService.setProperty("charCode"+pageMsgNum+sectionNum, charCodes.getValue());
        configService.setProperty("fontSize"+pageMsgNum+sectionNum, fontSize.getValue());
        configService.setProperty("fontGroup"+pageMsgNum+sectionNum, fontGroup.getValue());
        configService.setProperty("effectIn"+pageMsgNum+sectionNum, effectIn.getValue());
        configService.setProperty("effectInDirection"+pageMsgNum+sectionNum, inDirection.getValue());
        configService.setProperty("effectOut"+pageMsgNum+sectionNum, effectOut.getValue());
        configService.setProperty("effectOutDirection"+pageMsgNum+sectionNum, outDirection.getValue());
        configService.setProperty("effectSpeed"+pageMsgNum+sectionNum, effectSpeed.getValue());
        configService.setProperty("effectTime"+pageMsgNum+sectionNum, effectTime.getValue());
        configService.setProperty("xStart"+pageMsgNum+sectionNum, xStart.getValue());
        configService.setProperty("yStart"+pageMsgNum+sectionNum, yStart.getValue());
        configService.setProperty("xEnd"+pageMsgNum+sectionNum, xEnd.getValue());
        configService.setProperty("yEnd"+pageMsgNum+sectionNum, yEnd.getValue());
        configService.setProperty("bgImg"+pageMsgNum+sectionNum, bgImg.getValue());
        configService.setProperty("textColor"+pageMsgNum+sectionNum, textColor.getText());
        configService.setProperty("bgColor"+pageMsgNum+sectionNum, bgColor.getText());
        configService.setProperty("text"+pageMsgNum+sectionNum, msgPreview.getText());
    }

    public void send() {
        String msg = makeHexMsg();

        hexMsgTransceiver.sendMessages(msg);
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


        String msg = "10 02 ";

        //rs485인 경우 변경
        if (!CONNECT_TYPE.equals("RS485")) {
            msg += "00 ";
        } else {
            //rs485면 주소
            msg += "01 ";
        }

        //msg 길이
        byte[] textBytes;
        try {
            if (charCodesValue.equals("KS완성형 한글코드"))
                textBytes = text.getBytes("KSC5601");
            else textBytes = text.getBytes(StandardCharsets.UTF_16BE);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        msg+="00 "+String.format("%02x", (textBytes.length*2)+17);
        msg+=" 94 ";

        //실시간메세지
        if (msgType.equals("실시간 메세지")) {
            msg += "00 ";
        } else {
            msg+=Integer.toHexString(Integer.parseInt(pageMsgCntValue))+" ";
        }

        //섹션번호
        msg+= "0"+Integer.parseInt(section)+" ";

        //표시제어
        switch (displayControlValue) {
            case "Off":
                msg += "00 ";
                break;
            case "ON":
                msg += "63 ";
                break;
        }

        //10 02 00 00 39 94 00 00 63 00 00 03 18 00 00 05 00 00 00 00 00 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 BE C8 B3 E7 BE C8 B3 E7 BE C8 B3 E7 BE C8 B3 E7 BE C8 B3 E7 10 03
        //10 02 00 00 39 94 00 00 63 00 00 03 18 00 00 05 02 00 00 00 00 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 01 00 BE C8 B3 E7 BE C8 B3 E7 BE C8 B3 E7 BE C8 B3 E7 BE C8 B3 E7 10 0
        //표시방법
        msg+= displayMethodValue.equals("Normal") ? "00 ":"01 ";

        //문자코드
        msg+= charCodesValue.equals("KS완성형 한글코드") ? "00 ":"01 ";

        //폰트크기
        fontSizeValue = fontSizeValue.replaceAll("[^0-9]", "");
        msg+="0"+((Integer.parseInt(fontSizeValue)/4)-1)+" ";

        //입장효과
        msg+=makeEffect(effectInValue, inDirectionValue);

        //퇴장효과
        msg+=makeEffect(effectOutValue, outDirectionValue);

        //예비
        msg+="00 ";

        //효과속도**
        msg+=String.format("%02x", Integer.parseInt(effectSpeedValue.replaceAll("[^0-9]", "")))+" ";

        //유지시간
        msg+=makeEffectTime(effectTimeValue);

        //x축 시작**
        msg+=String.format("%02d", (Integer.parseInt(xStartValue)/4))+" ";

        //y축 시작
        msg+=String.format("%02d", (Integer.parseInt(yStartValue)/4))+" ";

        //x축 끝
        msg+=String.format("%02d", (Integer.parseInt(xEndValue)/4))+" ";

        //y축 끝
        msg+=String.format("%02d", (Integer.parseInt(yEndValue)/4))+" ";

        //배경이미지
        msg+=bgImgValue.equals("사용안함") ? "00 ": String.format("%02d ", Integer.parseInt(bgImgValue));
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

            int add = 0;
            switch (fontGroupValue){
                case "FontGroup1"->{
                    add = 8;
                }
                case "FontGroup2"->{
                    add = 128;
                }
                case "FontGroup3"->{
                    add = 136;
                }
                default -> {
                    add = 0;
                }
            }

            int tmpValue = Integer.parseInt(tmp, 16);
            String resultHex = "";

            resultHex = String.format("%02X ", tmpValue + add);
            if (String.valueOf(text.charAt(i)).getBytes(Charset.forName("EUC-KR")).length!=1){
                resultHex += String.format("%02X ", 0);

            }

            msg+=resultHex;
        }



        msg+=bytesToHex(textBytes, textBytes.length);

        msg += "10 03";

        return msg;
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
            case "효과없음"->{
                return "00 ";
            }
            case "사용안함"->{
                return "00 ";
            }
            case "정지효과"-> {
                if (direction.equals("방향없음")) {
                    return "01 ";
                } else if (direction.equals("밝아지기")){
                    return "02 ";
                } else if (direction.equals("어두워지기")){
                    return "03 ";
                } else if (direction.equals("수평반사")){
                    return "04 ";
                } else {
                    return "05 ";
                }
            }
            case "이동하기"->{
                if (direction.equals("왼쪽")){
                    return "06 ";
                } else if (direction.equals("오른쪽")){
                    return "07 ";
                } else if (direction.equals("위쪽")){
                    return "08 ";
                } else {
                    return "09 ";
                }
            }
            case "닦아내기"->{
                if (direction.equals("왼쪽")){
                    return "0C ";
                } else if (direction.equals("오른쪽")){
                    return "0D ";
                } else if (direction.equals("위쪽")){
                    return "0E ";
                } else {
                    return "0F ";
                }
            }
            case "커튼효과"->{
                if (direction.equals("수평밖으로")){
                    return "18 ";
                } else if (direction.equals("수평안으로")){
                    return "19 ";
                } else if (direction.equals("수직밖으로")){
                    return "1A ";
                } else {
                    return "1B ";
                }
            } case "확대효과"->{
                if (direction.equals("왼쪽")){
                    return "23 ";
                } else if (direction.equals("오른쪽")){
                    return "24 ";
                } else if (direction.equals("위쪽")){
                    return "25 ";
                } else if (direction.equals("아래쪽")){
                    return "26 ";
                } else {
                    return "27 ";
                }
            } case "회전효과"->{
                if (direction.equals("시계반대1")){
                    return "28 ";
                } else if (direction.equals("시계방향1")){
                    return "29 ";
                } else if (direction.equals("시계반대2")){
                    return "2A ";
                } else {
                    return "2B ";
                }
            } case "배경깜박이기"->{
                if (direction.equals("빨강")){
                    return "31 ";
                } else if (direction.equals("초록")){
                    return "32 ";
                } else if (direction.equals("파랑")){
                    return "33 ";
                } else if (direction.equals("흰색")){
                    return "34 ";
                } else if (direction.equals("모든색상(순차적)")){
                    return "35 ";
                } else {
                    return "36 ";
                }
            } case "3D 효과"->{
                return "37 ";
            }default -> {//무작위
                return "7A ";
            }
        }
    }

    private void selectEffect(String effect, ChoiceBox<String> directionBox) {
        if (effect.equals("정지효과")) {
            directionBox.setItems(FXCollections.observableArrayList("방향없음", "밝아지기", "어두워지기", "수평 반사", "수직 반사"));
        } else if (effect.equals("전체효과")) {
            directionBox.setItems(FXCollections.observableArrayList("무작위효과"));
        } else if (effect.equals("이동하기") || effect.equals("닦아내기") || effect.equals("블라인드")) {
            directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위", "아래"));
        } else if (effect.equals("커튼효과")) {
            directionBox.setItems(FXCollections.observableArrayList("수평밖으로", "수평안으로", "수직밖으로", "수직안으로"));
        } else if (effect.equals("확대효과")) {
            directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위쪽", "아래쪽", "오른쪽아래로"));
        } else if (effect.equals("회전효과")) {
            directionBox.setItems(FXCollections.observableArrayList("시계반대1", "시계방향1", "시계반대2", "시계방향2"));
        } else if (effect.equals("배경깜빡이기")) {
            directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "파란색", "흰색", "전체(순차적)"));
        } else if (effect.equals("색상깜박이기")) {
            directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "파란색", "흰색", "전체(순차적)", "전체(동시에)"));
        } else if (effect.equals("3D 효과")) {
            directionBox.setItems(FXCollections.observableArrayList("왼쪽"));
        } else if (effect.equals("효과없음")){
            directionBox.setDisable(true);
        }

        directionBox.getSelectionModel().selectFirst(); // 첫 번째 항목 선택
    }

    private void updateInDirections(String effect) {
        selectEffect(effect, inDirection);
    }

    private void updateOutDirections(String effect) {
        selectEffect(effect, outDirection);
    }

    public void reset(MouseEvent mouseEvent) {
    }

    public void preview(MouseEvent mouseEvent) {
    }
}
