package dbps.dbps.controller;

import dbps.dbps.Simulator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dbps.dbps.controller.MsgController.transmitMsgs;
import static dbps.dbps.service.MsgService.makeMsgWindow;


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

        makeOwnMsgAnchorPane.getStylesheets().add(Simulator.class.getResource("/dabit/dabit_simulator/css/makeOwnMsg.css").toExternalForm());
    }

    public void settingConfirm() {
        String newMsg = getSettings();

        msgPreview.setText(newMsg);
    }

    public void preview() {
        String text = msgPreview.getText();
        log.info("makeOwnMsgController preview : " + text);
    }

    public void reset(MouseEvent mouseEvent) {
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
        log.info("makeOwnMsgController confirm 호출");
        log.info("text={}", text);
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
            directionBox.setItems(FXCollections.observableArrayList("왼쪽", "오른쪽", "위", "아래", "오른쪽아래로"));
        } else if (effect.equals("회전효과")) {
            directionBox.setItems(FXCollections.observableArrayList("시계방향1", "반시계방향1", "시계방향2", "반시계방향2"));
        } else if (effect.equals("배경깜박이기")) {
            directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "파란색", "흰색", "전체(순차적)"));
        } else if (effect.equals("색상깜박이기")) {
            directionBox.setItems(FXCollections.observableArrayList("빨간색", "초록색", "파란색", "흰색", "전체(순차적)", "전체(동시에)"));
        } else if (effect.equals("3D 효과")) {
            directionBox.setItems(FXCollections.observableArrayList("왼쪽"));
        }

        directionBox.getSelectionModel().selectFirst(); // 첫 번째 항목 선택
    }

    /**
     * 미완성
     */

    private String getSettings() {
        displayControl.getValue();
        displayMethod.getValue();
        charCodes.getValue();
        fontSize.getValue();
        fontGroup.getValue();
        effectIn.getValue();
        inDirection.getValue();
        effectOut.getValue();
        outDirection.getValue();
        effectSpeed.getValue();
        effectTime.getValue();
        xStart.getValue();
        yStart.getValue();
        xEnd.getValue();
        yEnd.getValue();
        bgImg.getValue();
        fontColor.getValue();
        fontBgColor.getValue();

        //설정긁어와서 코드 약속모양대로 적용
        return "설정적용완료";
    }
}
