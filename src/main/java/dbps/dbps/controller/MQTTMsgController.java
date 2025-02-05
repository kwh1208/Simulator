package dbps.dbps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbps.dbps.Simulator;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.*;

public class MQTTMsgController {
    public AnchorPane mqttMsgAP;
    public RadioButton realTimeMsg;
    public RadioButton pageMsg;
    public Label pageCntLabel;
    public ChoiceBox<String> pageMsgCnt;
    public RadioButton section1;
    public RadioButton section2;
    public RadioButton section4;
    public RadioButton section3;
    public ChoiceBox<String> displayControl;
    public ChoiceBox<String> displayMethod;
    public ChoiceBox<String> effectIn;
    public ChoiceBox<String> inDirection;
    public ChoiceBox<String> effectOut;
    public ChoiceBox<String> outDirection;
    public ChoiceBox<String> bgImg;
    public Pane mqttMsgPane;
    public ChoiceBox<String> xStart;
    public ChoiceBox<String> yStart;
    public ChoiceBox<String> xEnd;
    public ChoiceBox<String> yEnd;
    public ChoiceBox<String> effectTime;
    public ChoiceBox<String> effectSpeed;
    MQTTManager mqttManager;
    ResourceBundle bundle;

    ToggleGroup msgTypeGroup = new ToggleGroup();

    ToggleGroup sectionGroup = new ToggleGroup();

    private List<TextField> textFields;
    private List<ChoiceBox<String>> textColorChoiceBoxes;
    private List<ChoiceBox<String>> bgColorChoiceBoxes;
    private List<ChoiceBox<String>> fontChoiceBoxes;
    private List<ChoiceBox<String>> sizeChoiceBoxes;

    Map<String, Integer> colorMap;


    @FXML
    public void initialize(){
        mqttMsgAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/mqttMsg.css").toExternalForm());
        mqttManager=MQTTManager.getInstance();
        bundle= ResourceManager.getInstance().getBundle();
        realTimeMsg.setToggleGroup(msgTypeGroup);
        pageMsg.setToggleGroup(msgTypeGroup);
        textFields = new ArrayList<>();
        textColorChoiceBoxes = new ArrayList<>();
        bgColorChoiceBoxes = new ArrayList<>();
        fontChoiceBoxes = new ArrayList<>();
        sizeChoiceBoxes = new ArrayList<>();

        makeUI();

        section1.setToggleGroup(sectionGroup);
        section2.setToggleGroup(sectionGroup);
        section3.setToggleGroup(sectionGroup);
        section4.setToggleGroup(sectionGroup);

        effectIn.valueProperty().addListener((observable, oldValue, newValue)->{
            selectEffect(newValue, inDirection);
        });

        effectOut.valueProperty().addListener((observable, oldValue, newValue)->{
            selectEffect(newValue, outDirection);
        });

        bgImg.getItems().add(bundle.getString("notUsed"));
        for (int i = 1; i < 11; i++) {
            bgImg.getItems().add(String.valueOf(i));
        }

        for (int i = 0; i <= 4; i++) {
            xStart.getItems().add(i * 25 + "%");
            xEnd.getItems().add(i * 25 + "%");
            yStart.getItems().add(i * 25 + "%");
            yEnd.getItems().add(i * 25 + "%");
        }

        colorMap = Map.ofEntries(
                Map.entry(bundle.getString("black"), 0),
                Map.entry(bundle.getString("red"), 1),
                Map.entry(bundle.getString("green"), 2),
                Map.entry(bundle.getString("yellow"), 3),
                Map.entry(bundle.getString("blue"), 4),
                Map.entry(bundle.getString("pink"), 5),
                Map.entry(bundle.getString("skyblue"), 6),
                Map.entry(bundle.getString("white"), 7)
        );
    }

    public void sendMqttMsg() throws JsonProcessingException {
        Map<String, Object> moidDetail = new HashMap<>();
        moidDetail.put("1", List.of((displayMethod.getValue()).equals("Clear") ? 1 : 0,
                ((displayControl.getValue()).equals("On") ? 99 : Integer.parseInt(displayControl.getValue()))));

        moidDetail.put("2", List.of(getEffect(effectIn.getValue(), inDirection.getValue()), getEffect(effectOut.getValue(), outDirection.getValue()), Integer.parseInt((effectSpeed.getValue().replaceAll("[^0-9]", ""))), Integer.parseInt((effectTime.getValue().replaceAll("초", "")))));

        moidDetail.put("3", List.of(Integer.parseInt((xStart.getValue().replaceAll("%", ""))), Integer.parseInt((xEnd.getValue().replaceAll("%", ""))), Integer.parseInt((yStart.getValue().replaceAll("%", ""))), Integer.parseInt((yEnd.getValue().replaceAll("%", "")))));

        moidDetail.put("4", (bgImg.getValue()).equals(bundle.getString("notUsed")) ? 0 : Integer.parseInt(bgImg.getValue()));

        List<List<Object>> msg = new ArrayList<>();

        for (int i = 0; i < textFields.size(); i++) {
            if (textFields.get(i).getText()==null) {
                continue;
            }
            List<Object> tmp = new ArrayList<>();
            tmp.add(textFields.get(i).getText());
            if(textColorChoiceBoxes.get(i).getValue().equals("default")) {
                msg.add(tmp);
                continue;
            }
            else {
                tmp.add(colorMap.get(textColorChoiceBoxes.get(i).getValue()));
            }

            if(bgColorChoiceBoxes.get(i).getValue().equals("default")) {
                msg.add(tmp);
                continue;
            }
            else {
                tmp.add(colorMap.get(bgColorChoiceBoxes.get(i).getValue()));
            }

            if(fontChoiceBoxes.get(i).getValue().equals("default")) {
                msg.add(tmp);
                continue;
            }

            else {
                tmp.add(Character.getNumericValue(fontChoiceBoxes.get(i).getValue().charAt(fontChoiceBoxes.get(i).getValue().length() - 1)));
            }

            if (sizeChoiceBoxes.get(i).getValue().equals("default")) {
                msg.add(tmp);
            }
            else {
                tmp.add(Integer.parseInt(sizeChoiceBoxes.get(i).getValue()));
                msg.add(tmp);
            }
        }

        moidDetail.put("5", msg);

        Map<String, Object> moidMap = new HashMap<>();
        moidMap.put(getMsgKey(), moidDetail);

        mqttManager.publishSet(moidMap);
    }

    private String getMsgKey() {
        String tmp = "2.RTE058.2.";
        if (realTimeMsg.isSelected()) {
            tmp+="0.";
        }
        else tmp+=pageMsgCnt.getValue()+".";

        tmp+=((RadioButton) sectionGroup.getSelectedToggle()).getText();

        return tmp;
    }


    private void makeUI(){
        int numberOfRows = 8; // 총 10행
        double baseY = 27.0;   // 첫 번째 행의 Y 위치
        double rowHeight = 32.0; // 각 행 간격

        for (int i = 1; i <= numberOfRows; i++) {
            double yOffset = baseY + (i - 1) * rowHeight;

            // TextField 생성
            TextField textField = new TextField();
            textField.setLayoutX(271.0);
            textField.setLayoutY(yOffset);
            textField.setPrefHeight(21.0);
            textField.setPrefWidth(352.0);
            textField.setId("mqttMsgText" + i); // ID 설정

            // ChoiceBox 생성 (TextColor)
            ChoiceBox<String> textColorChoiceBox = new ChoiceBox<>();
            textColorChoiceBox.setLayoutX(-2.0);
            textColorChoiceBox.setLayoutY(yOffset);
            textColorChoiceBox.setPrefHeight(21.0);
            textColorChoiceBox.setPrefWidth(55.0);
            textColorChoiceBox.setId("mqttMsgTextColor" + i); // ID 설정
            textColorChoiceBox.getItems().addAll(
                    "default",
                    bundle.getString("black"),
                    bundle.getString("red"),
                    bundle.getString("green"),
                    bundle.getString("yellow"),
                    bundle.getString("blue"),
                    bundle.getString("pink"),
                    bundle.getString("skyblue"),
                    bundle.getString("white")
                    );
            textColorChoiceBox.setValue("default");

            // ChoiceBox 생성 (BgColor)
            ChoiceBox<String> bgColorChoiceBox = new ChoiceBox<>();
            bgColorChoiceBox.setLayoutX(70.0);
            bgColorChoiceBox.setLayoutY(yOffset);
            bgColorChoiceBox.setPrefHeight(21.0);
            bgColorChoiceBox.setPrefWidth(55.0);
            bgColorChoiceBox.setId("mqttMsgBgColor" + i); // ID 설정
            bgColorChoiceBox.getItems().addAll(
                    "default",
                    bundle.getString("black"),
                    bundle.getString("red"),
                    bundle.getString("green"),
                    bundle.getString("yellow"),
                    bundle.getString("blue"),
                    bundle.getString("pink"),
                    bundle.getString("skyblue"),
                    bundle.getString("white")
            );
            bgColorChoiceBox.setValue("default");

            // ChoiceBox 생성 (Font)
            ChoiceBox<String> fontChoiceBox = new ChoiceBox<>();
            fontChoiceBox.setLayoutX(139.0);
            fontChoiceBox.setLayoutY(yOffset);
            fontChoiceBox.setPrefHeight(21.0);
            fontChoiceBox.setPrefWidth(55.0);
            fontChoiceBox.setId("mqttMsgFont" + i); // ID 설정
            fontChoiceBox.getItems().addAll(
                    "default",
                    bundle.getString("fontGroup1"),
                    bundle.getString("fontGroup2"),
                    bundle.getString("fontGroup3"),
                    bundle.getString("fontGroup4")
            );
            fontChoiceBox.setValue("default");

            // ChoiceBox 생성 (Size)
            ChoiceBox<String> sizeChoiceBox = new ChoiceBox<>();
            sizeChoiceBox.setLayoutX(208.0);
            sizeChoiceBox.setLayoutY(yOffset);
            sizeChoiceBox.setPrefHeight(21.0);
            sizeChoiceBox.setPrefWidth(55.0);
            sizeChoiceBox.setId("mqttMsgSize" + i); // ID 설정
            sizeChoiceBox.getItems().add("default");
            for (int j = 16; j <=64; j+=4) {
                sizeChoiceBox.getItems().add(String.valueOf(j));
            }
            sizeChoiceBox.setValue("default");

            mqttMsgPane.getChildren().addAll(textField, textColorChoiceBox, bgColorChoiceBox, fontChoiceBox, sizeChoiceBox);
            textFields.add(textField);
            textColorChoiceBoxes.add(textColorChoiceBox);
            bgColorChoiceBoxes.add(bgColorChoiceBox);
            fontChoiceBoxes.add(fontChoiceBox);
            sizeChoiceBoxes.add(sizeChoiceBox);
        }
    }

    private void selectEffect(String effect, ChoiceBox<String> directionBox) {
        //배경.색상깜빡이기 작동되나 확인.
        switch (effect) {
            case "정지효과" ->
                    directionBox.setItems(FXCollections.observableArrayList("방향없음"));
            case "이동하기" ->
                    directionBox.setItems(FXCollections.observableArrayList("왼쪽"));
            case "색상깜빡이기" ->
                    directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "노란색", "전체"));
            case "점멸이동" ->
                    directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "노란색", "전체"));
        }


        directionBox.getSelectionModel().selectFirst();
    }

    private int getEffect(String effect, String direction){
        if (effect.equals("정지")){
            return 10;
        }

        if (effect.equals("좌로 이동")){
            return 20;
        }

        if (effect.equals("색상깜빡이기")){
            switch (direction){
                case "빨간색": return 31;
                case "초록색": return 32;
                case "노란색": return 33;
                case "전체": return 30;
            }
        }

        if (effect.equals("점멸이동")){
            switch (direction){
                case "빨간색": return 41;
                case "초록색": return 42;
                case "노란색": return 43;
                case "전체": return 40;
            }
        }

        return 10;
    }
}
