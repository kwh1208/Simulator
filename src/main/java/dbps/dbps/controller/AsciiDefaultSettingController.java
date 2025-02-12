package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ASCiiDefaultSettingService;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ResourceManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;
import static java.lang.Integer.parseInt;


public class AsciiDefaultSettingController {
    public ProgressIndicator progressIndicator;
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
    ComboBox<String> effectTime;

    @FXML
    ChoiceBox<String> xStart;

    @FXML
    ChoiceBox<String> yStart;

    @FXML
    ChoiceBox<String> xEnd;

    @FXML
    ChoiceBox<String> yEnd;

    @FXML
    ComboBox<String> bgImg;

    @FXML
    ChoiceBox<String> fontColor;

    @FXML
    ChoiceBox<String> fontBgColor;

    @FXML
    TextField defaultSetting;

    AsciiMsgTransceiver asciiMsgTransceiver;
    ASCiiDefaultSettingService asCiiDefaultSettingService;
    ResourceBundle bundle;


    @FXML
    public void initialize() {
        bundle = ResourceManager.getInstance().getBundle();
        // 선택 항목 리스너 설정
        effectOut.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateOutDirections(newValue));
        effectIn.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateInDirections(newValue));


        for (int i = 1; i < 256; i++) {
            bgImg.getItems().add(String.valueOf(i));
        }

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        asCiiDefaultSettingService = ASCiiDefaultSettingService.getInstance();
        makeOwnMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/makeOwnMsg.css").toExternalForm());
        setService();

        addItems();
    }

    private void addItems() {
        charCodes.getItems().addAll(
                bundle.getString("CombinationType"),
                bundle.getString("UTF16")
        );

        effectIn.getItems().addAll(
                bundle.getString("noEffect"),
                bundle.getString("staticEffect"),
                bundle.getString("randomEffect"),
                bundle.getString("move"),
                bundle.getString("wipe"),
                bundle.getString("blind"),
                bundle.getString("curtainEffect"),
                bundle.getString("zoomEffect"),
                bundle.getString("rotateEffect"),
                bundle.getString("backgroundFlash"),
                bundle.getString("textFlash"),
                bundle.getString("3DEffect")
        );

        effectOut.getItems().addAll(
                bundle.getString("noEffect"),
                bundle.getString("staticEffect"),
                bundle.getString("randomEffect"),
                bundle.getString("move"),
                bundle.getString("wipe"),
                bundle.getString("blind"),
                bundle.getString("curtainEffect"),
                bundle.getString("zoomEffect"),
                bundle.getString("rotateEffect"),
                bundle.getString("backgroundFlash"),
                bundle.getString("textFlash"),
                bundle.getString("3DEffect")
        );

        inDirection.getItems().addAll(
                bundle.getString("noDirection"),
                bundle.getString("brighten"),
                bundle.getString("darken"),
                bundle.getString("horizontalReflection"),
                bundle.getString("verticalReflection")
        );

        outDirection.getItems().addAll(
                bundle.getString("noDirection"),
                bundle.getString("brighten"),
                bundle.getString("darken"),
                bundle.getString("horizontalReflection"),
                bundle.getString("verticalReflection")
        );

        effectTime.getItems().addAll(
                0+bundle.getString("sec"),
                1+bundle.getString("sec"),
                2+bundle.getString("sec"),
                4+bundle.getString("sec"),
                5+bundle.getString("sec"),
                7.5+bundle.getString("sec"),
                10+bundle.getString("sec"),
                15+bundle.getString("sec"),
                20+bundle.getString("sec"),
                30+bundle.getString("sec"),
                40+bundle.getString("sec"),
                2+bundle.getString("min"),
                3+bundle.getString("min"),
                5+bundle.getString("min"),
                10+bundle.getString("min"),
                30+bundle.getString("min"),
                1+bundle.getString("hr"),
                3+bundle.getString("hr"),
                5+bundle.getString("hr"),
                9+bundle.getString("hr")
        );
        effectTime.setValue(2+bundle.getString("sec"));
        xStart.getItems().add("0");
        xEnd.getItems().add("0");
        for (int i = 1; i <= 4*SIZE_COLUMN; i++) {
            xStart.getItems().add(String.valueOf(4*i));
            xEnd.getItems().add(String.valueOf(4*i));
        }

        yStart.getItems().add("0");
        yEnd.getItems().add("0");
        for (int i = 1; i <= 4*SIZE_ROW; i++) {
            yStart.getItems().add(String.valueOf(4*i));
            yEnd.getItems().add(String.valueOf(4*i));
        }

        for (int i = 1; i < 11; i++) {
            bgImg.getItems().add(String.valueOf(i));
        }
        bgImg.getItems().add("사용안함");

        fontColor.getItems().addAll(
                bundle.getString("black"),
                bundle.getString("red"),
                bundle.getString("green"),
                bundle.getString("yellow"),
                bundle.getString("blue"),
                bundle.getString("pink"),
                bundle.getString("cyan"),
                bundle.getString("white")
        );

        fontBgColor.getItems().addAll(
                bundle.getString("black"),
                bundle.getString("red"),
                bundle.getString("green"),
                bundle.getString("yellow"),
                bundle.getString("blue"),
                bundle.getString("pink"),
                bundle.getString("cyan"),
                bundle.getString("white")
        );
    }

    private void setService() {
        asCiiDefaultSettingService.setDefaultSetting(defaultSetting);
        asCiiDefaultSettingService.setDisplayControl(displayControl);
        asCiiDefaultSettingService.setDisplayMethod(displayMethod);
        asCiiDefaultSettingService.setCharCodes(charCodes);
        asCiiDefaultSettingService.setFontSize(fontSize);
        asCiiDefaultSettingService.setFontGroup(fontGroup);
        asCiiDefaultSettingService.setEffectIn(effectIn);
        asCiiDefaultSettingService.setInDirection(inDirection);
        asCiiDefaultSettingService.setEffectOut(effectOut);
        asCiiDefaultSettingService.setOutDirection(outDirection);
        asCiiDefaultSettingService.setSub(sub);
        asCiiDefaultSettingService.setEffectSpeed(effectSpeed);
        asCiiDefaultSettingService.setEffectTime(effectTime);
        asCiiDefaultSettingService.setXStart(xStart);
        asCiiDefaultSettingService.setYStart(yStart);
        asCiiDefaultSettingService.setXEnd(xEnd);
        asCiiDefaultSettingService.setYEnd(yEnd);
        asCiiDefaultSettingService.setBgImg(bgImg);
        asCiiDefaultSettingService.setFontColor(fontColor);
        asCiiDefaultSettingService.setFontBgColor(fontBgColor);
        asCiiDefaultSettingService.setFontColor(fontColor);
        asCiiDefaultSettingService.setFontBgColor(fontBgColor);
    }

    //확인버튼
    @FXML
    public void settingConfirm() {
        String newMsg = getSettings();

        defaultSetting.setText(newMsg);
    }

    //미리보기
    //완성필요
    @FXML
    public void send() {
        String text = defaultSetting.getText();
        asciiMsgTransceiver.sendMessages(text, false, progressIndicator);
    }

    //선택지 초기상태로
    @FXML
    public void reset() {
        // 모든 항목의 value 값 초기화
        displayControl.setValue("On");
        displayMethod.setValue("Clear");
        charCodes.setValue(bundle.getString("CombinationType"));
        fontSize.setValue("16(Standard)");
        fontGroup.setValue("fontGroup1");
        effectIn.setValue(bundle.getString("staticEffect"));
        inDirection.setValue(bundle.getString("noDirection"));
        effectOut.setValue(bundle.getString("staticEffect"));
        outDirection.setValue(bundle.getString("noDirection"));
        sub.setValue(bundle.getString("notUsed"));
        effectSpeed.setValue("15");
        effectTime.setValue(2+bundle.getString("sec"));
        xStart.setValue("0");
        yStart.setValue("0");
        xEnd.setValue("0");
        yEnd.setValue("0");
        bgImg.setValue(bundle.getString("notUsed"));
        fontColor.setValue(bundle.getString("yellow"));
        fontBgColor.setValue(bundle.getString("black"));
        String msg = "![0032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]";
        if (isRS) {
            msg = "![" + convertRS485AddrASCii() + "032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]";
        }
        defaultSetting.setText(msg);
    }


    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
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
        if (effect.equals(bundle.getString("noEffect"))) {
            directionBox.setDisable(true);
        } else {
            directionBox.setDisable(false);
        }

        if (effect.equals(bundle.getString("staticEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("noDirection"),
                    bundle.getString("brighten"),
                    bundle.getString("darken"),
                    bundle.getString("horizontalReflection"),
                    bundle.getString("verticalReflection")));
        } else if (effect.equals(bundle.getString("randomEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(bundle.getString("randomEffect")));
        } else if (effect.equals(bundle.getString("move")) || effect.equals(bundle.getString("wipe")) || effect.equals(bundle.getString("blind"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("left"),
                    bundle.getString("right"),
                    bundle.getString("up"),
                    bundle.getString("down")
            ));
        } else if (effect.equals(bundle.getString("curtainEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("horizontalOutward"),
                    bundle.getString("horizontalInward"),
                    bundle.getString("verticalOutward"),
                    bundle.getString("verticalInward")
            ));
        } else if (effect.equals(bundle.getString("zoomEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("left"),
                    bundle.getString("right"),
                    bundle.getString("up"),
                    bundle.getString("down"),
                    bundle.getString("Bottom-Right")
            ));
        } else if (effect.equals(bundle.getString("rotateEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("counterclockwise1"),
                    bundle.getString("clockwise1"),
                    bundle.getString("counterclockwise2"),
                    bundle.getString("clockwise2")
            ));
        } else if (effect.equals(bundle.getString("backgroundFlash"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("red"),
                    bundle.getString("green"),
                    bundle.getString("blue"),
                    bundle.getString("white"),
                    bundle.getString("allSequential")
            ));
        } else if (effect.equals(bundle.getString("textFlash"))) {
            directionBox.setItems(FXCollections.observableArrayList(
                    bundle.getString("red"),
                    bundle.getString("green"),
                    bundle.getString("blue"),
                    bundle.getString("white"),
                    bundle.getString("allSequential"),
                    bundle.getString("allSimultaneous")
            ));
        } else if (effect.equals(bundle.getString("3DEffect"))) {
            directionBox.setItems(FXCollections.observableArrayList("왼쪽"));
        }

        directionBox.getSelectionModel().selectFirst(); // 첫 번째 항목 선택
    }

    private String getSettings() {
        String text = "![0032";
        if (isRS) {
            text = "![" + convertRS485AddrASCii() + "032";
        }
        text += "/P0000";
        text += "/D" + setDText(displayControl.getValue(), displayMethod.getValue());
        text += "/F" + setFText(charCodes.getValue(), fontSize.getValue());
        text += "/E" + setEText(effectIn.getValue(), inDirection.getValue());
        text += setEText(effectOut.getValue(), outDirection.getValue());
        text += "/S" + setSText(effectSpeed.getValue(), effectTime.getValue());
        text += "/X" + String.format("%02d", parseInt(xStart.getValue()) / 4) + String.format("%02d", parseInt(xEnd.getValue()) / 4);
        text += "/Y" + String.format("%02d", parseInt(yStart.getValue()) / 4) + String.format("%02d", parseInt(yEnd.getValue()) / 4);
        text += "/B" + (bgImg.getValue().equals("사용안함") ? "000" : String.format("%03d", parseInt(bgImg.getValue())));
        text += "/C" + setColorText(fontColor.getValue());
        text += "/G" + setColorText(fontBgColor.getValue());
        text += "/T" + (Integer.parseInt(fontGroup.getValue().replaceAll("[^\\d]", ""))-1) + "!]";

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
            result += String.format("%02d", (Integer.parseInt(value2) - 12) / 4);
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
        result += value1.replaceAll("[^\\d]", "");


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

    private String setColorText(String value) {
        if (value.equals(bundle.getString("black"))) {
            return "0";
        } else if (value.equals(bundle.getString("red"))) {
            return "1";
        } else if (value.equals(bundle.getString("green"))) {
            return "2";
        } else if (value.equals(bundle.getString("blue"))) {
            return "3";
        } else if (value.equals(bundle.getString("yellow"))) {
            return "4";
        } else if (value.equals(bundle.getString("purple"))) {
            return "5";
        } else if (value.equals(bundle.getString("skyblue"))) {
            return "6";
        } else if (value.equals(bundle.getString("white"))) {
            return "7";
        } else return "0";
    }

    public void read() throws ExecutionException, InterruptedException {
        String msg = "![00330!]";
        if (isRS) {
            msg = "![" + convertRS485AddrASCii() + "0330!]";
        }
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }
}
