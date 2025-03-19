package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

import static dbps.dbps.Constants.*;

public class HEXMessageController {

    public ProgressIndicator progressIndicator;
    public RadioButton hexRadioBtn;
    public RadioButton ascRadioBtn;
    public Pane hexPane;
    public Pane ascPane;
    public TextFlow preview;
    public TextField sendMsgAsc;
    public Label textColorLabel;
    public ComboBox<ComboItem> textColorASC;
    public Label bgColorLabel;
    public ComboBox<ComboItem> bgColorASC;
    public Tooltip previewTooltip;
    public ImageView arrow;
    public Label previewLabel;
    public Button defaultBtn;
    public Pane msgPropertyPane;
    HexMsgTransceiver hexMsgTransceiver;
    HexMsgService hexMsgService;
    AsciiMsgTransceiver asciiMsgTransceiver;
    ToggleGroup protocolType = new ToggleGroup();

    public static final BooleanProperty isAsc = new SimpleBooleanProperty(IS_ASCII);

    @FXML
    private AnchorPane HEXMsgAP;

    @FXML
    private RadioButton realTimeMsg;

    @FXML
    private RadioButton pageMsg;

    @FXML
    private Label pageCntLabel;

    @FXML
    public ComboBox<String> pageMsgCnt;

    @FXML
    private RadioButton section0;

    @FXML
    private RadioButton section1;

    @FXML
    private RadioButton section2;

    @FXML
    private ComboBox<ComboItem> displayControl;

    @FXML
    private ComboBox<ComboItem> displayMethod;

    @FXML
    private ComboBox<ComboItem> charCodes;

    @FXML
    private ComboBox<ComboItem> fontSize;

    @FXML
    private ComboBox<ComboItem> fontGroup;

    @FXML
    private ComboBox<ComboItem> effectIn;

    @FXML
    private ComboBox<ComboItem> inDirection;


    @FXML
    private ComboBox<ComboItem> effectOut;

    @FXML
    private ComboBox<ComboItem> outDirection;


    @FXML
    private ComboBox<ComboItem> effectSpeed;

    @FXML
    private ComboBox<ComboItem> effectTime;

    @FXML
    private ComboBox<ComboItem> xStart;

    @FXML
    private ComboBox<ComboItem> yStart;

    @FXML
    private ComboBox<ComboItem> xEnd;

    @FXML
    private ComboBox<ComboItem> yEnd;

    @FXML
    private ComboBox<ComboItem> bgImg;

    @FXML
    private TextField textColor;

    @FXML
    private TextField bgColor;

    @FXML
    private TextField sendMsg;

    ConfigService configService;

    ToggleGroup msgTypeGroup = new ToggleGroup();

    ToggleGroup sectionGroup = new ToggleGroup();

    ResourceBundle bundle;

    Map<String, Integer> COLOR_MAP;

    @FXML
    private void initialize() {
        configService = ConfigService.getInstance();
        HEXMsgAP.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/hexMessage.css")).toExternalForm());
        bundle = ResourceManager.getInstance().getBundle();
        hexMsgService = HexMsgService.getInstance();
        hexMsgService.setPageMsgCnt(pageMsgCnt);
        hexMsgService.setXStart(xStart);
        hexMsgService.setYStart(yStart);
        hexMsgService.setXEnd(xEnd);
        hexMsgService.setYEnd(yEnd);
        hexRadioBtn.setToggleGroup(protocolType);
        ascRadioBtn.setToggleGroup(protocolType);

        if (IS_ASCII){
            ascRadioBtn.setSelected(true);
            toggleVisible(false);
        }
        else {
            hexRadioBtn.setSelected(true);
            toggleVisible(true);
        }


        isAsc.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                ascRadioBtn.setSelected(true);
            } else {
                hexRadioBtn.setSelected(true);
            }
        });

        protocolType.selectedToggleProperty().addListener((observable, oldValue, newValue)->{
            if (newValue.equals(hexRadioBtn)){
                IS_ASCII=false;
                isAsc.set(false);
            }
            else {
                IS_ASCII=true;
                isAsc.set(true);
            }
            configService.setProperty("IS_ASCII", String.valueOf(IS_ASCII));
            toggleVisible(newValue.equals(hexRadioBtn));
        });

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        realTimeMsg.setToggleGroup(msgTypeGroup);
        pageMsg.setToggleGroup(msgTypeGroup);

        section0.setToggleGroup(sectionGroup);
        section1.setToggleGroup(sectionGroup);
        section2.setToggleGroup(sectionGroup);

        realTimeMsg.setSelected(true);
        if (configService.getProperty("isHexRealTime").equals("0")) {
            realTimeMsg.setSelected(true);
        } else {
            pageMsg.setSelected(true);
            pageMsgCnt.setVisible(true);
            pageCntLabel.setVisible(true);
            pageMsgCnt.setValue(configService.getProperty("isHexRealTime"));
        }
        section0.setSelected(true);

        if (configService.getProperty("textAsc"+getMsgNum())!=null){
            sendMsgAsc.setText(configService.getProperty("textAsc"+getMsgNum()));
        }

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

        bgImg.getItems().add(new ComboItem("notUsed", bundle.getString("notUsed")));
        for (int i = 1; i < 11; i++) {
            bgImg.getItems().add(new ComboItem(String.valueOf(i), MessageFormat.format(bundle.getString("Img"), i)));
        }

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> doMsgSettings());

        sectionGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> doMsgSettings());

        effectOut.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateOutDirections(newValue.displayText()));
        effectIn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateInDirections(newValue.displayText()));

        hexMsgTransceiver = HexMsgTransceiver.getInstance();

        setXY();
        doMsgSettings();
        saveConfig();
        setUI();
        COLOR_MAP = new HashMap<>();

        COLOR_MAP.put("black", 0);
        COLOR_MAP.put("red", 1);
        COLOR_MAP.put("green", 2);
        COLOR_MAP.put("yellow", 3);
        COLOR_MAP.put("blue", 4);
        COLOR_MAP.put("pink", 5);
        COLOR_MAP.put("cyan", 6);
        COLOR_MAP.put("white", 7);

        packetBinding();
    }

    private void packetBinding() {
        // 기존 미리보기 초기화
        updatePreviewBackground();

        msgTypeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> scheduleUpdatePreviewBackground());
        pageMsgCnt.valueProperty().addListener((obs, oldVal, newVal) -> scheduleUpdatePreviewBackground());
        sectionGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> scheduleUpdatePreviewBackground());
        for (ComboBox<ComboItem> comboItemComboBox : Arrays.asList(displayControl, displayMethod, charCodes, fontSize, fontGroup, effectIn, inDirection, effectOut, outDirection, effectSpeed, effectTime, xStart, yStart, xEnd, yEnd, bgImg, textColorASC, bgColorASC)) {
            comboItemComboBox.valueProperty().addListener((obs, oldVal, newVal) -> scheduleUpdatePreviewBackground());
        }
    }

    private final PauseTransition updateDelay = new PauseTransition(Duration.millis(100));

    private void scheduleUpdatePreviewBackground() {
        // 변경이 발생할 때마다 PauseTransition을 재시작함
        updateDelay.setOnFinished(e -> updatePreviewBackground());
        updateDelay.playFromStart();
    }

    private void updatePreviewBackground() {
        List<String> newSegments = getPreviewSegments();
        preview.getChildren().clear();

        for (int i = 0; i < newSegments.size(); i++) {
            String seg = newSegments.get(i);

            Label labelNode = new Label(seg);
            labelNode.setTextFill(Color.WHITE);

            // 기본 배경을 투명으로 설정
            ObjectProperty<Color> bgColorProperty = new SimpleObjectProperty<>(Color.TRANSPARENT);
            labelNode.backgroundProperty().bind(Bindings.createObjectBinding(() ->
                            new Background(new BackgroundFill(bgColorProperty.get(), new CornerRadii(3), Insets.EMPTY)),
                    bgColorProperty));

            // 변경된 부분에 애니메이션 적용
            if (previousSegments != null && previousSegments.size() > i && !previousSegments.get(i).equals(seg)) {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(bgColorProperty, Color.DARKORANGE)),
                        new KeyFrame(Duration.seconds(4), new KeyValue(bgColorProperty, Color.TRANSPARENT))
                );
                timeline.play();
            }

            preview.getChildren().add(labelNode);
        }

        previousSegments = newSegments;
    }


    private List<String> getPreviewSegments() {
        List<String> segments = new ArrayList<>();
        String msg;

        // 0. 시작 부분
        if (isRS) {
            msg = "![" + convertRS485AddrASCii() + "0";
        } else {
            msg = "![00";
        }
        segments.add(msg);

        // 1. 실시간 메시지 여부에 따른 메시지 타입
        if (realTimeMsg.isSelected()) {
            msg = "0/P00";
        } else {
            msg = "1/P" + String.format("%02d", Integer.parseInt(pageMsgCnt.getValue()) - 1);
        }
        msg += String.format("%02d", Integer.parseInt(((RadioButton) sectionGroup.getSelectedToggle()).getText()));
        segments.add(msg);

        // 3. /D + setDText(...)
        msg = "/D" + setDText(displayControl.getValue().key(), displayMethod.getValue().key());
        segments.add(msg);

        // 4. /F + setFText(...)
        msg = "/F" + setFText(charCodes.getValue().key(), fontSize.getValue().key());
        segments.add(msg);

        // 5. /E + setEText(effectIn, inDirection)
        msg = "/E" + setEText(effectIn.getValue().displayText(), inDirection.getValue().displayText());
        msg += setEText(effectOut.getValue().displayText(), outDirection.getValue().displayText());
        segments.add(msg);

        // 7. /S + setSText(...)
        msg = "/S" + setSText(effectSpeed.getValue().displayText(), effectTime.getValue().displayText());
        segments.add(msg);

        // 8. /X + xStart/xEnd 값 (4로 나눈 후 2자리 포맷)
        msg = "/X" + String.format("%02d", Integer.parseInt(xStart.getValue().key()) / 4)
                + String.format("%02d", Integer.parseInt(xEnd.getValue().key()) / 4);
        segments.add(msg);

        // 9. /Y + yStart/yEnd 값 (4로 나눈 후 2자리 포맷)
        msg = "/Y" + String.format("%02d", Integer.parseInt(yStart.getValue().key()) / 4)
                + String.format("%02d", Integer.parseInt(yEnd.getValue().key()) / 4);
        segments.add(msg);

        // 10. /B + bgImg 값 (특정 조건에 따라 "000" 또는 포맷된 값)
        msg = "/B" + (bgImg.getValue().key().equals("notUsed")
                ? "000"
                : String.format("%03d", Integer.parseInt(bgImg.getValue().key())));
        segments.add(msg);

        // 11. /C + fgColors, /G + bgColors
        String fgColors = textColorASC.getValue().key();
        msg = "/C" + COLOR_MAP.get(fgColors);
        segments.add(msg);

        String bgColors = bgColorASC.getValue().key();
        msg = "/G" + COLOR_MAP.get(bgColors);
        segments.add(msg);

        // 12. /T + fontGroup 값 (숫자만 추출 후 -1)
        msg = "/T" + (Integer.parseInt(fontGroup.getValue().displayText().replaceAll("\\D", "")) - 1);
        segments.add(msg);

        // 13. 닫는 부분
        msg = "!]";
        segments.add(msg);

        return segments;
    }


    List<String> previousSegments;


    public void toggleVisible(boolean visible){
        if (visible){
            hexPane.setVisible(true);
            ascPane.setVisible(false);
            textColorLabel.setDisable(true);
            textColorASC.setDisable(true);
            bgColorLabel.setDisable(true);
            bgColorASC.setDisable(true);
            previewLabel.setVisible(false);
            defaultBtn.setVisible(false);
            preview.setVisible(false);
            msgPropertyPane.setPrefHeight(400);
        }
        else {
            hexPane.setVisible(false);
            ascPane.setVisible(true);
            textColorLabel.setDisable(false);
            textColorASC.setDisable(false);
            bgColorLabel.setDisable(false);
            bgColorASC.setDisable(false);
            previewLabel.setVisible(true);
            defaultBtn.setVisible(true);
            preview.setVisible(true);
            msgPropertyPane.setPrefHeight(450);
        }

        charCodes.getItems().clear();
        charCodes.getItems().addAll(
                new ComboItem("CombinationType", bundle.getString("CombinationType")),
                new ComboItem("UTF16", bundle.getString("UTF16")));
        if (ascRadioBtn.isSelected()){
            charCodes.getItems().addAll(
                    new ComboItem("UTF8Com", bundle.getString("UTF8Com")),
                    new ComboItem("UTF8UNI", bundle.getString("UTF8UNI"))
            );
        }
        charCodes.setValue(new ComboItem(configService.getProperty("charCode"+getMsgNum()), bundle.getString(configService.getProperty("charCode"+getMsgNum()))));
    }

    private void setUI() {
        displayControl.getItems().clear();
        displayControl.getItems().addAll(
                new ComboItem("On", bundle.getString("On")),
                new ComboItem("Off", bundle.getString("Off")),
                new ComboItem("1", "1"+bundle.getString("times")),
                new ComboItem("2", "2"+bundle.getString("times")),
                new ComboItem("3", "3"+bundle.getString("times")),
                new ComboItem("4", "4"+bundle.getString("times")),
                new ComboItem("5", "5"+bundle.getString("times")),
                new ComboItem("6", "6"+bundle.getString("times")),
                new ComboItem("7", "7"+bundle.getString("times")),
                new ComboItem("8", "8"+bundle.getString("times")),
                new ComboItem("9", "9"+bundle.getString("times")),
                new ComboItem("10", "10"+bundle.getString("times")),
                new ComboItem("20", "20"+bundle.getString("times")),
                new ComboItem("30", "30"+bundle.getString("times")),
                new ComboItem("40", "40"+bundle.getString("times")),
                new ComboItem("50", "50"+bundle.getString("times")),
                new ComboItem("60", "60"+bundle.getString("times")),
                new ComboItem("70", "70"+bundle.getString("times")),
                new ComboItem("80", "80"+bundle.getString("times")),
                new ComboItem("90", "90"+bundle.getString("times"))
        );
        displayControl.setValue(new ComboItem(configService.getProperty("displayControl"+getMsgNum()), bundle.getString(configService.getProperty("displayControl"+getMsgNum()))));

        displayMethod.getItems().clear();
        displayMethod.getItems().addAll(
                new ComboItem("Clear", bundle.getString("Clear")),
                new ComboItem("Normal", bundle.getString("Normal"))
        );
        displayMethod.setValue(new ComboItem(configService.getProperty("displayMethod"+getMsgNum()), bundle.getString(configService.getProperty("displayMethod"+getMsgNum()))));

        charCodes.getItems().clear();
        charCodes.getItems().addAll(
                new ComboItem("CombinationType", bundle.getString("CombinationType")),
                new ComboItem("UTF16", bundle.getString("UTF16")));
        if (ascRadioBtn.isSelected()){
            charCodes.getItems().addAll(
                    new ComboItem("UTF8Com", bundle.getString("UTF8Com")),
                    new ComboItem("UTF8UNI", bundle.getString("UTF8UNI"))
            );
        }
        charCodes.setValue(new ComboItem(configService.getProperty("charCode"+getMsgNum()), bundle.getString(configService.getProperty("charCode"+getMsgNum()))));

        fontSize.getItems().clear();
        fontSize.getItems().add(new ComboItem("12", "12픽셀"));
        fontSize.getItems().add(new ComboItem("14", "14픽셀"));
        fontSize.getItems().add(new ComboItem("16", "16픽셀(기본)"));
        for (int i = 1; i < 10; i++) {
            fontSize.getItems().add(new ComboItem(String.valueOf(4*(4+i)), 4*(4+i)+bundle.getString("pixel")));
        }

        fontGroup.getItems().clear();
        fontGroup.getItems().addAll(
                new ComboItem("fontGroup1", bundle.getString("fontGroup1")),
                new ComboItem("fontGroup2", bundle.getString("fontGroup2")),
                new ComboItem("fontGroup3", bundle.getString("fontGroup3")),
                new ComboItem("fontGroup4", bundle.getString("fontGroup4")));

        effectIn.getItems().addAll(
                new ComboItem("staticEffect", bundle.getString("staticEffect")),
                new ComboItem("move", bundle.getString("move")),
                new ComboItem("wipe", bundle.getString("wipe")),
                new ComboItem("blind", bundle.getString("blind")),
                new ComboItem("curtainEffect", bundle.getString("curtainEffect")),
                new ComboItem("zoomEffect", bundle.getString("zoomEffect")),
                new ComboItem("rotateEffect", bundle.getString("rotateEffect")),
                new ComboItem("backgroundFlash", bundle.getString("backgroundFlash")),
                new ComboItem("textFlash", bundle.getString("textFlash")),
                new ComboItem("3DEffect", bundle.getString("3DEffect"))
        );
        selectEffect(effectIn.getValue().displayText(), inDirection);

        effectOut.getItems().addAll(
                new ComboItem("noEffect", bundle.getString("noEffect")),
                new ComboItem("staticEffect", bundle.getString("staticEffect")),
                new ComboItem("move", bundle.getString("move")),
                new ComboItem("wipe", bundle.getString("wipe")),
                new ComboItem("blind", bundle.getString("blind")),
                new ComboItem("curtainEffect", bundle.getString("curtainEffect")),
                new ComboItem("zoomEffect", bundle.getString("zoomEffect")),
                new ComboItem("rotateEffect", bundle.getString("rotateEffect")),
                new ComboItem("backgroundFlash", bundle.getString("backgroundFlash")),
                new ComboItem("textFlash", bundle.getString("textFlash")),
                new ComboItem("3DEffect", bundle.getString("3DEffect"))
        );
        selectEffect(effectOut.getValue().displayText(), outDirection);

        effectSpeed.getItems().add(new ComboItem("slowest", bundle.getString("slowest")));
        for (int i = 5; i <= 95; i += 5) {
            effectSpeed.getItems().add(new ComboItem(String.valueOf(i), String.valueOf(i)));
        }
        effectSpeed.getItems().add(new ComboItem("fastest", bundle.getString("fastest")));
// 초 단위
        effectTime.getItems().add(new ComboItem("0sec", "0" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("1sec", "1" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("2sec", "2" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("4sec", "4" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("5sec", "5" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("7.5sec", "7.5" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("10sec", "10" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("15sec", "15" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("20sec", "20" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("30sec", "30" + bundle.getString("sec")));
        effectTime.getItems().add(new ComboItem("40sec", "40" + bundle.getString("sec")));

// 분 단위
        effectTime.getItems().add(new ComboItem("2min", "2" + bundle.getString("min")));
        effectTime.getItems().add(new ComboItem("3min", "3" + bundle.getString("min")));
        effectTime.getItems().add(new ComboItem("5min", "5" + bundle.getString("min")));
        effectTime.getItems().add(new ComboItem("10min", "10" + bundle.getString("min")));
        effectTime.getItems().add(new ComboItem("30min", "30" + bundle.getString("min")));

// 시간 단위
        effectTime.getItems().add(new ComboItem("1hr", "1" + bundle.getString("hr")));
        effectTime.getItems().add(new ComboItem("3hr", "3" + bundle.getString("hr")));
        effectTime.getItems().add(new ComboItem("5hr", "5" + bundle.getString("hr")));
        effectTime.getItems().add(new ComboItem("9hr", "9" + bundle.getString("hr")));

// 기본 선택
        effectTime.setValue(new ComboItem("2sec", "2" + bundle.getString("sec")));

        textColorASC.getItems().addAll(
                new ComboItem("black", bundle.getString("black")),
                new ComboItem("red", bundle.getString("red")),
                new ComboItem("green", bundle.getString("green")),
                new ComboItem("yellow", bundle.getString("yellow")),
                new ComboItem("blue", bundle.getString("blue")),
                new ComboItem("pink", bundle.getString("pink")),
                new ComboItem("cyan", bundle.getString("cyan")),
                new ComboItem("white", bundle.getString("white")));

        textColorASC.setValue(new ComboItem(configService.getProperty("textColorASC"+getMsgNum()), bundle.getString(configService.getProperty("textColorASC"+getMsgNum()))));

        bgColorASC.getItems().addAll(
                new ComboItem("black", bundle.getString("black")),
                new ComboItem("red", bundle.getString("red")),
                new ComboItem("green", bundle.getString("green")),
                new ComboItem("yellow", bundle.getString("yellow")),
                new ComboItem("blue", bundle.getString("blue")),
                new ComboItem("pink", bundle.getString("pink")),
                new ComboItem("cyan", bundle.getString("cyan")),
                new ComboItem("white", bundle.getString("white")));
        bgColorASC.setValue(new ComboItem(configService.getProperty("bgColorASC"+getMsgNum()), bundle.getString(configService.getProperty("bgColorASC"+getMsgNum()))));
    }

    private void setXY() {
        xStart.getItems().clear();
        xEnd.getItems().clear();
        yStart.getItems().clear();
        yEnd.getItems().clear();

        int xLimit = Integer.parseInt(configService.getProperty("displayColumnSize"));
        int yLimit = Integer.parseInt(configService.getProperty("displayRowSize"));

        for (int i = 0; i <= 4 * xLimit; i++) {
            xStart.getItems().add(new ComboItem(String.valueOf(4 * i), 4 * i +bundle.getString("pixel")));
            xEnd.getItems().add(new ComboItem(String.valueOf(4 * i), 4 * i +bundle.getString("pixel")));
        }
        for (int i = 0; i <= 4 * yLimit; i++) {
            yStart.getItems().add(new ComboItem(String.valueOf(4 * i), 4 * i +bundle.getString("pixel")));
            yEnd.getItems().add(new ComboItem(String.valueOf(4 * i), 4 * i +bundle.getString("pixel")));
        }

        xStart.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
        yStart.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
        xEnd.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
        yEnd.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
    }


    private String getMsgNum() {
        String i = "0";
        if (!realTimeMsg.isSelected()) {
            i = pageMsgCnt.getValue();
        }
        String j;
        if (section0.isSelected()) {
            j = "0";
        } else if (section1.isSelected()) {
            j = "1";
        } else {
            j = "2";
        }
        return i + j;
    }

    private void doMsgSettings() {
        String msgNum = getMsgNum();

        displayControl.setValue(new ComboItem(configService.getProperty("displayControl" + msgNum), bundle.getString(configService.getProperty("displayControl" + msgNum))));
        displayMethod.setValue(new ComboItem(configService.getProperty("displayMethod" + msgNum), bundle.getString(configService.getProperty("displayMethod" + msgNum))));
        charCodes.setValue(new ComboItem(configService.getProperty("charCode" + msgNum), bundle.getString(configService.getProperty("charCode" + msgNum))));
        fontSize.setValue(new ComboItem(configService.getProperty("fontSize" + msgNum), configService.getProperty("fontSize" + msgNum)+bundle.getString("pixel")));
        fontGroup.setValue(new ComboItem(configService.getProperty("fontGroup" + msgNum), bundle.getString(configService.getProperty("fontGroup" + msgNum))));
        effectIn.setValue(new ComboItem(configService.getProperty("effectIn" + msgNum), bundle.getString(configService.getProperty("effectIn" + msgNum))));
        inDirection.setValue(new ComboItem(configService.getProperty("effectInDirection" + msgNum), bundle.getString(configService.getProperty("effectInDirection" + msgNum))));
        effectOut.setValue(new ComboItem(configService.getProperty("effectOut" + msgNum), bundle.getString(configService.getProperty("effectOut" + msgNum))));
        outDirection.setValue(new ComboItem(configService.getProperty("effectOutDirection" + msgNum), bundle.getString(configService.getProperty("effectOutDirection" + msgNum))));
        String speedKey = configService.getProperty("effectSpeed" + msgNum);
        String displayText;
        if (isNumeric(speedKey)) {
            displayText = speedKey;
        } else {
            displayText = bundle.getString(speedKey);
        }
        effectSpeed.setValue(new ComboItem(speedKey, displayText));
        String effectTimeKey = configService.getProperty("effectTime" + msgNum); // 예: "2min"
// 숫자 부분과 단위 부분을 분리
        String numberPart = effectTimeKey.replaceAll("[^0-9.]", "");  // "2"
        String unitPart = effectTimeKey.replaceAll("[0-9.]", "");      // "min"
// 번들에서 단위 번역을 가져옴 (예: bundle.getString("min") → "분")
        String translatedUnit = bundle.getString(unitPart);
        displayText = numberPart + translatedUnit; // "2분"

        effectTime.setValue(new ComboItem(effectTimeKey, displayText));
        xStart.setValue(new ComboItem(configService.getProperty("xStart" + msgNum), configService.getProperty("xStart" + msgNum)+bundle.getString("pixel")));
        yStart.setValue(new ComboItem(configService.getProperty("yStart" + msgNum), configService.getProperty("yStart" + msgNum)+bundle.getString("pixel")));
        xEnd.setValue(new ComboItem(configService.getProperty("xEnd" + msgNum), configService.getProperty("xEnd" + msgNum)+bundle.getString("pixel")));
        yEnd.setValue(new ComboItem(configService.getProperty("yEnd" + msgNum), configService.getProperty("yEnd" + msgNum)+bundle.getString("pixel")));
        bgImg.setValue(new ComboItem(
                configService.getProperty("bgImg" + msgNum),
                configService.getProperty("bgImg" + msgNum).equals("notUsed") ? bundle.getString(configService.getProperty("bgImg" + msgNum)) : configService.getProperty("bgImg" + msgNum)
        ));
        textColor.setText(configService.getProperty("textColor" + msgNum));
        bgColor.setText(configService.getProperty("bgColor" + msgNum));
        sendMsg.setText(configService.getProperty("text" + msgNum));
        textColorASC.setValue(new ComboItem(configService.getProperty("textColorASC" + msgNum), bundle.getString(configService.getProperty("textColorASC" + msgNum))));
        bgColorASC.setValue(new ComboItem(configService.getProperty("bgColorASC"+msgNum), bundle.getString(configService.getProperty("bgColorASC"+msgNum))));
    }

    public void send() {
        if (IS_ASCII) {
            String msg = sendMsgAsc.getText();
            if (msg.contains("/F01")||msg.contains("/f01")){
                asciiMsgTransceiver.sendMessages(msg, false, true, progressIndicator);
                return;
            }
            if (msg.contains("/F02")||msg.contains("/f02")){
                asciiMsgTransceiver.sendMessages(msg, true,false, progressIndicator);
            }
            else asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
        } else {
            String msg = makeHexMsg();
            hexMsgTransceiver.sendMessages(msg, progressIndicator);
        }
    }

    private String makeHexMsg() {
        try {
            String msgType = ((RadioButton) msgTypeGroup.getSelectedToggle()).getText();
            String pageMsgCntValue = pageMsgCnt.getValue();
            String section = ((RadioButton) sectionGroup.getSelectedToggle()).getText();
            String displayControlValue = displayControl.getValue().key();
            String displayMethodValue = displayMethod.getValue().key();
            String charCodesValue = charCodes.getValue().displayText();
            String fontSizeValue = fontSize.getValue().displayText();
            String fontGroupValue = fontGroup.getValue().displayText();
            String effectInValue = effectIn.getValue().displayText();
            String inDirectionValue = inDirection.getValue().displayText();
            String effectOutValue = effectOut.getValue().displayText();
            String outDirectionValue = outDirection.getValue().displayText();
            String effectSpeedValue = effectSpeed.getValue().displayText();
            String effectTimeValue = effectTime.getValue().displayText();
            String xStartValue = xStart.getValue().key();
            String yStartValue = yStart.getValue().key();
            String xEndValue = xEnd.getValue().key();
            String yEndValue = yEnd.getValue().key();
            String bgImgValue = bgImg.getValue().key();
            String textColorValue = textColor.getText();
            String bgColorValue = bgColor.getText();
            String text = sendMsg.getText();


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
            if (fontSizeValue.equals("14")) {
                msg.append("01 ");
            } else msg.append("0").append((Integer.parseInt(fontSizeValue) / 4) - 1).append(" ");

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
            msg.append(bgImgValue.equals("notUsed") ? "00 " : String.format("%02d ", Integer.parseInt(bgImgValue)));

            //글자
            for (int i = 0; i < text.length(); i++) {
                String tmp = "";
                if (bgColorValue.length() > i) {
                    tmp += String.valueOf(bgColorValue.charAt(i));
                } else {
                    tmp += String.valueOf(bgColorValue.charAt(bgColorValue.length() - 1));
                }

                if (textColorValue.length() > i) {
                    tmp += String.valueOf(textColorValue.charAt(i));
                } else {
                    tmp += String.valueOf(textColorValue.charAt(textColorValue.length() - 1));
                }

                int add = 0;
                if (fontGroupValue.equals(bundle.getString("fontGroup1"))) {
                    add += 0;
                } else if (fontGroupValue.equals(bundle.getString("fontGroup2"))) {
                    add += 8;
                } else if (fontGroupValue.equals(bundle.getString("fontGroup3"))) {
                    add += 128;
                } else if (fontGroupValue.equals(bundle.getString("fontGroup4"))) {
                    add += 136;
                }


                int tmpValue = Integer.parseInt(tmp, 16);
                String resultHex;

                resultHex = String.format("%02X ", tmpValue + add);
                if (charCodes.getValue().displayText().equals(bundle.getString("UTF16")) || String.valueOf(text.charAt(i)).getBytes(Charset.forName("MS949")).length != 1) {
                    resultHex += String.format("%02X ", 0);
                }

                msg.append(resultHex);
            }


            msg.append(bytesToHex(textBytes, textBytes.length));

            msg.append("10 03");

            return msg.toString();
        } catch (Exception ignored) {

        }
        return null;
    }

    private String makeEffectTime(String effectTimeValue) {
        if (effectTimeValue.contains(bundle.getString("sec"))) {
            return String.format("%02x ", Integer.parseInt(effectTimeValue.replaceAll("[^0-9]", "")) * 2);
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

    private String makeEffect(String effect, String direction) {
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
        } else if (effect.equals(bundle.getString("blind"))) {
            if (direction.equals(bundle.getString("left"))) {
                return "12 ";
            } else if (direction.equals(bundle.getString("right"))) {
                return "13 ";
            } else if (direction.equals(bundle.getString("up"))) {
                return "14 ";
            } else {
                return "15 ";
            }
        } else if (effect.equals(bundle.getString("curtainEffect"))) {
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
            } else if (direction.equals(bundle.getString("yellow"))) {
                return "2E ";
            } else if (direction.equals(bundle.getString("white"))) {
                return "2F ";
            } else {
                return "30 ";
            }
        } else if (effect.equals(bundle.getString("textFlash"))) {
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
        } else if (effect.equals(bundle.getString("3DEffect"))) {
            return "36 ";
        } else {
            return "7A ";
        }

    }

    private void selectEffect(String effect, ComboBox<ComboItem> directionBox) {
        directionBox.setDisable(false);
        if (effect.equals(bundle.getString("staticEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("noDirection", bundle.getString("noDirection")),
                    new ComboItem("brighten", bundle.getString("brighten")),
                    new ComboItem("darken", bundle.getString("darken")),
                    new ComboItem("horizontalReflection", bundle.getString("horizontalReflection")),
                    new ComboItem("verticalReflection", bundle.getString("verticalReflection"))
            ));
        } else if (effect.equals(bundle.getString("randomEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("randomEffect", bundle.getString("randomEffect"))
            ));
        } else if (effect.equals(bundle.getString("move")) || effect.equals(bundle.getString("wipe")) || effect.equals(bundle.getString("blind"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("left", bundle.getString("left")),
                    new ComboItem("right", bundle.getString("right")),
                    new ComboItem("up", bundle.getString("up")),
                    new ComboItem("down", bundle.getString("down"))
            ));
        } else if (effect.equals(bundle.getString("curtainEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("horizontalOutward", bundle.getString("horizontalOutward")),
                    new ComboItem("horizontalInward", bundle.getString("horizontalInward")),
                    new ComboItem("verticalOutward", bundle.getString("verticalOutward")),
                    new ComboItem("verticalInward", bundle.getString("verticalInward"))
            ));
        } else if (effect.equals(bundle.getString("zoomEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("left", bundle.getString("left")),
                    new ComboItem("right", bundle.getString("right")),
                    new ComboItem("up", bundle.getString("up")),
                    new ComboItem("down", bundle.getString("down"))
            ));
        } else if (effect.equals(bundle.getString("rotateEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("counterclockwise1", bundle.getString("counterclockwise1")),
                    new ComboItem("clockwise1", bundle.getString("clockwise1")),
                    new ComboItem("counterclockwise2", bundle.getString("counterclockwise2")),
                    new ComboItem("clockwise2", bundle.getString("clockwise2"))
            ));
        } else if (effect.equals(bundle.getString("backgroundFlash"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("red", bundle.getString("red")),
                    new ComboItem("green", bundle.getString("green")),
                    new ComboItem("yellow", bundle.getString("yellow")),
                    new ComboItem("white", bundle.getString("white")),
                    new ComboItem("allSequential", bundle.getString("allSequential"))
            ));
        } else if (effect.equals(bundle.getString("textFlash"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("red", bundle.getString("red")),
                    new ComboItem("green", bundle.getString("green")),
                    new ComboItem("yellow", bundle.getString("yellow")),
                    new ComboItem("white", bundle.getString("white")),
                    new ComboItem("allSequential", bundle.getString("allSequential")),
                    new ComboItem("allSimultaneous", bundle.getString("allSimultaneous"))
            ));
        } else if (effect.equals(bundle.getString("3DEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    new ComboItem("left", bundle.getString("left"))
            ));
        } else if (effect.equals(bundle.getString("noEffect"))) {
            directionBox.setDisable(true);
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
        configService.setProperty("displayControl" + msgNum, "On");
        configService.setProperty("displayMethod" + msgNum, "Clear");
        configService.setProperty("charCode" + msgNum, "CombinationType");
        configService.setProperty("fontSize" + msgNum, "16(Standard)");
        configService.setProperty("fontGroup" + msgNum, "fontGroup1");
        configService.setProperty("effectIn" + msgNum, "staticEffect");
        configService.setProperty("effectInDirection" + msgNum, "noDirection");
        configService.setProperty("effectOut" + msgNum, "staticEffect");
        configService.setProperty("effectOutDirection" + msgNum, "noDirection");
        configService.setProperty("effectSpeed" + msgNum, "5");
        configService.setProperty("effectTime" + msgNum, "2sec");
        configService.setProperty("xStart" + msgNum, "0");
        configService.setProperty("xEnd" + msgNum, "0");
        configService.setProperty("yStart" + msgNum, "0");
        configService.setProperty("yEnd" + msgNum, "0");
        configService.setProperty("bgImg" + msgNum, "notUsed");
        configService.setProperty("textColor" + msgNum, "1");
        configService.setProperty("bgColor" + msgNum, "0");
        configService.setProperty("textColorASC"+msgNum, "red");
        configService.setProperty("black", "bgColorASC"+msgNum);

        doMsgSettings();
    }

    private void saveConfig() {
        displayControl.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("displayControl" + getMsgNum(), newValue.key()));

        displayMethod.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("displayMethod" + getMsgNum(), newValue.key())
        );
        charCodes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("charCodes" + getMsgNum(), newValue.key())
        );
        fontSize.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("fontSize" + getMsgNum(), newValue.key())
        );
        fontGroup.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("fontGroup" + getMsgNum(), newValue.key())
        );
        effectIn.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectIn" + getMsgNum(), newValue.key())
        );
        inDirection.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectInDirection" + getMsgNum(), newValue.key())
        );
        effectOut.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectOut" + getMsgNum(), newValue.key())
        );
        outDirection.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectOutDirection" + getMsgNum(), newValue.key())
        );
        effectSpeed.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectSpeed" + getMsgNum(), newValue.key())
        );
        effectTime.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("effectTime" + getMsgNum(), newValue.key())
        );
        xStart.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) configService.setProperty("xStart" + getMsgNum(), newValue.key());
                }
        );
        yStart.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) configService.setProperty("yStart" + getMsgNum(), newValue.key());
                }
        );
        xEnd.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) configService.setProperty("xEnd" + getMsgNum(), newValue.key());
                }
        );
        yEnd.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) configService.setProperty("yEnd" + getMsgNum(), newValue.key());
                }
        );
        bgImg.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> configService.setProperty("bgImg" + getMsgNum(), newValue.key())
        );

        sendMsg.textProperty().addListener((observable, oldValue, newValue)-> configService.setProperty("text"+getMsgNum(), newValue));

        sendMsgAsc.textProperty().addListener((observable, oldValue, newValue)-> configService.setProperty("textAsc"+getMsgNum(), newValue));
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
        switch (value1) {
            case "CombinationType" -> result += "00";
            case "UTF16" -> result += "01";
            case "UTF8Com" -> result += "02";
            case "UTF8UNI" -> result += "03";
        }

        if (value2.equals("16(Standard)")) {
            result += "03";
        } else if (value2.equals("14")) {
            result += "01";
        } else {
            result += String.format("%02d", (Integer.parseInt(value2) - 4) / 4);
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
            } else if (value2.equals(bundle.getString("yellow"))) {
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
            } else if (value2.equals(bundle.getString("yellow"))) {
                return "51";
            } else if (value2.equals(bundle.getString("white"))) {
                return "52";
            } else if (value2.equals(bundle.getString("allSequential"))) {
                return "53";
            } else if (value2.equals(bundle.getString("allSimultaneous"))) {
                return "55";
            }
        } else if (value1.equals(bundle.getString("randomEffect"))) return "57";
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

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    private String getTextFromParent(Parent parent) {
        StringBuilder sb = new StringBuilder();
        for (Node child : parent.getChildrenUnmodifiable()) {
            if (child instanceof Text) {
                sb.append(((Text) child).getText());
            } else if (child instanceof Parent) {
                sb.append(getTextFromParent((Parent) child));
            }
        }
        return sb.toString();
    }

    public void copy() {
        sendMsgAsc.setText(getPreviewText());
    }

    public void sendDefault() {
        asciiMsgTransceiver.sendMessages(getPreviewText().replace("![000", "![0032"), false, progressIndicator);
    }

    private String getPreviewText() {
        StringBuilder sb = new StringBuilder();
        for (Node node : preview.getChildren()) {
            if (node instanceof Text) {
                sb.append(((Text) node).getText());
            } else if (node instanceof Parent) {  // 예: StackPane처럼 Text가 자식으로 포함된 경우
                sb.append(getTextFromParent((Parent) node));
            }
        }
        return sb.toString();
    }

    public void openMulti(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/ASCiiMessage.fxml", "ASCii 프로토콜 전송", mouseEvent);
    }
}
