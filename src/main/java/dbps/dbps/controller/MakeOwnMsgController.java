package dbps.dbps.controller;

import dbps.dbps.Simulator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dbps.dbps.controller.ASCiiMsgController.transmitMsgs;
import static dbps.dbps.service.ASCiiMsgService.makeMsgWindow;
import static java.lang.Integer.parseInt;


public class MakeOwnMsgController {
    private static final Logger log = LoggerFactory.getLogger(MakeOwnMsgController.class);
    @FXML
    AnchorPane makeOwnMsgAnchorPane;

    @FXML
    ChoiceBox<String> displayControl;

    @FXML
    ChoiceBox<String> displayMethod;

    @FXML
    ChoiceBox<String> charCodes;

    @FXML
    ChoiceBox<String> fontSize;

    @FXML
    ChoiceBox<String> fontGroup;

    @FXML
    ChoiceBox<String> effectIn;

    @FXML
    ChoiceBox<String> inDirection;

    @FXML
    ChoiceBox<String> effectOut;

    @FXML
    ChoiceBox<String> outDirection;

    @FXML
    ChoiceBox<String> sub;

    @FXML
    ChoiceBox<String> effectSpeed;

    @FXML
    ChoiceBox<String> effectTime;

    @FXML
    ChoiceBox<String> xStart;

    @FXML
    ChoiceBox<String> yStart;

    @FXML
    ChoiceBox<String> xEnd;

    @FXML
    ChoiceBox<String> yEnd;

    @FXML
    ChoiceBox<String> bgImg;

    @FXML
    ChoiceBox<String> fontColor;

    @FXML
    ChoiceBox<String> fontBgColor;

    @FXML
    TextField msgPreview;

    @FXML
    public void initialize() {
        // 선택 항목 리스너 설정
        effectOut.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateOutDirections(newValue));
        effectIn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateInDirections(newValue));

        for (int i = 1; i < 256; i++) {
            bgImg.getItems().add(String.valueOf(i));
        }

        makeOwnMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/makeOwnMsg.css").toExternalForm());
    }

    //확인버튼
    @FXML
    public void settingConfirm() {
        String newMsg = getSettings();

        msgPreview.setText(newMsg);
    }

    //미리보기
    //완성필요
    @FXML
    public void preview() {
        String text = msgPreview.getText();
    }

    //선택지 초기상태로
    @FXML
    public void reset() {
        // 모든 항목의 value 값 초기화
        displayControl.setValue("On");
        displayMethod.setValue("Clear");
        charCodes.setValue("KS완성형 한글코드");
        fontSize.setValue("16(Standard)");
        fontGroup.setValue("fontGroup0");
        effectIn.setValue("정지효과");
        inDirection.setValue("방향없음");
        effectOut.setValue("정지효과");
        outDirection.setValue("방향없음");
        sub.setValue("사용안함");
        effectSpeed.setValue("15");
        effectTime.setValue("2");
        xStart.setValue("0");
        yStart.setValue("0");
        xEnd.setValue("0");
        yEnd.setValue("0");
        bgImg.setValue("사용안함");
        fontColor.setValue("노란색");
        fontBgColor.setValue("검정색");
        msgPreview.setText("![0032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]");
    }


    public void confirm() {
        String text = msgPreview.getText();
        //텍스트를 전체화면으로 넘겨주기.
        for (TextField transmitMsg : transmitMsgs) {
            if (transmitMsg.getText().isBlank() ||transmitMsg.getText().isEmpty()) {
                transmitMsg.setText(text);
                makeMsgWindow.close();
                return;
            }
        }
        transmitMsgs.get(transmitMsgs.size() - 1).setText(text);
        makeMsgWindow.close();
    }

    //효과에따라서 부수효과 바뀜
    private void updateInDirections(String effect) {
        selectEffect(effect, inDirection);
    }

    private void updateOutDirections(String effect) {
        selectEffect(effect, outDirection);
    }


    /**
     * 리팩토링
     */

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

    /**
     * 미완성
     */

    private String getSettings() {
        String text = "![000";
        text += "/D"+setDText(displayControl.getValue(), displayMethod.getValue());
        text += "/F"+setFText(charCodes.getValue(), fontSize.getValue());
        text += "/E"+setEText(effectIn.getValue(),inDirection.getValue());
        text += setEText(effectOut.getValue(), outDirection.getValue());
        text += "/S"+setSText(effectSpeed.getValue(), effectTime.getValue());
        text += "/X"+String.format("%02d", parseInt(xStart.getValue())/4)+String.format("%02d", parseInt(xEnd.getValue())/4);
        text += "/Y"+String.format("%02d", parseInt(yStart.getValue())/4)+String.format("%02d", parseInt(yEnd.getValue())/4);
        text += "/B"+(bgImg.getValue().equals("사용안함") ? "000" : String.format("%03d", parseInt(bgImg.getValue())));
        text += "/C"+ setColorText(fontColor.getValue());
        text += "/G"+ setColorText(fontBgColor.getValue());
        text += "/T"+fontGroup.getValue().replaceAll("[^\\d]", "")+"!]";

        return text;
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
        if (value2.equals("Normal")){
            result+="00";
        }
        else result+="01";
        return result;
    }

    private String setFText(String value1, String value2) {
        String result = "";
        if (value1.equals("KS완성형 한글코드")) {
            result += "00";
        } else {
            result += "01";
        }

        if (value2.equals("16(Standard)")) {
            result += "03";
        } else {
            result += String.format("%02d", (Integer.parseInt(value2) - 12) / 4);
        }

        return result;
    }

    private String setEText(String value1,String value2) {
        if (value1.equals("효과없음")){
            return "00";
        } else if (value1.equals("정지효과")) {
            switch (value2){
                case "방향없음":
                    return "01";
                case "밝아지기":
                    return "02";
                case "어두워지기":
                    return "03";
                case "수평 반사":
                    return "04";
                case "수직 반사":
                    return "05";
            }
        } else if (value1.equals("이동하기")) {
            switch (value2){
                case "왼쪽":
                    return "06";
                case "오른쪽":
                    return "07";
                case "위":
                    return "08";
                case "아래":
                    return "09";
            }
        } else if (value1.equals("닦아내기")) {
            switch (value2){
                case "왼쪽":
                    return "12";
                case "오른쪽":
                    return "13";
                case "위":
                    return "14";
                case "아래":
                    return "15";
            }
        } else if (value1.equals("블라인드")) {
            switch (value2){
                case "왼쪽":
                    return "18";
                case "오른쪽":
                    return "19";
                case "위":
                    return "20";
                case "아래":
                    return "21";
            }
        } else if (value1.equals("커튼효과")) {
            switch (value2){
                case "수평밖으로":
                    return "24";
                case "수평안으로":
                    return "25";
                case "수직밖으로":
                    return "26";
                case "수직안으로":
                    return "27";
            }
        } else if (value1.equals("확대효과")) {
            switch (value2){
                case "왼쪽":
                    return "35";
                case "오른쪽":
                    return "36";
                case "위쪽":
                    return "37";
                case "아래쪽":
                    return "38";
                case "오른쪽아래로":
                    return "39";
            }
        } else if (value1.equals("회전효과")) {
            switch (value2){
                case "시계반대1":
                    return "40";
                case "시계방향1":
                    return "41";
                case "시계반대2":
                    return "42";
                case "시계방향2":
                    return "43";
            }
        } else if (value1.equals("배경깜빡이기")) {
            switch (value2){
                case "빨간색":
                    return "44";
                case "초록색":
                    return "45";
                case "파란색":
                    return "46";
                case "흰색":
                    return "47";
                case "전체(순차적)":
                    return "48";
            }
        } else if (value1.equals("색상깜박이기")) {
            switch (value2){
                case "빨간색":
                    return "49";
                case "초록색":
                    return "50";
                case "파란색":
                    return "51";
                case "흰색":
                    return "52";
                case "전체(순차적)":
                    return "53";
                case "전체(동시에)":
                    return "55";
            }
        }
        return "54";//3D 효과, 왼쪽

    }
    private String setSText(String value1, String value2) {
        String result = "";
        result += value1.replaceAll("[^\\d]", "");


        if (value2.contains("초")) {
            result += String.format("%02d", (int)(Double.parseDouble(value2.replaceAll("[^\\d]", ""))*2));
        } else {
            if (value2.equals("2분")){
                result+="90";
            } else if (value2.equals("3분")) {
                result+="91";
            } else if (value2.equals("5분")) {
                result+="92";
            } else if (value2.equals("10분")) {
                result+="93";
            } else if (value2.equals("30분")) {
                result+="94";
            } else if (value2.equals("1시간")) {
                result+="95";
            } else if (value2.equals("3시간")) {
                result+="96";
            } else if (value2.equals("5시간")) {
                result+="97";
            } else if (value2.equals("9시간")) {
                result+="98";
            }
        }
        return result;
    }

    private String setColorText(String value) {
        switch (value) {
            case "검정색":
                return "0";
            case "빨간색":
                return "1";
            case "초록색":
                return "2";
            case "노란색":
                return "3";
            case "파란색":
                return "4";
            case "보라색":
                return "5";
            case "하늘색":
                return "6";
            case "흰색":
                return "7";
        }
        return "0";
    }

}
