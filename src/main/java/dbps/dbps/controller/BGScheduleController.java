package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

public class BGScheduleController {

    public ProgressIndicator progressIndicator;
    @FXML
    private AnchorPane BGScheduleAP;
    @FXML
    private Pane tagPane; // 태그가 표시될 Pane
    @FXML
    private Pane checkboxPane; // 체크박스 리스트 Pane

    private final Map<CheckBox, Label> tagMap = new HashMap<>(); // 체크박스-태그 매핑
    private final int TAGS_PER_ROW = 3; // 한 줄에 최대 태그 개수
    private final double TAG_WIDTH = 110; // 태그의 너비
    private final double TAG_HEIGHT = 30; // 태그의 높이
    private final double TAG_PADDING = 10; // 태그 간 간격
    AsciiMsgTransceiver asciiMsgTransceiver;
    ResourceBundle bundle;


    public void initialize() {
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        bundle= ResourceManager.getInstance().getBundle();
        BGScheduleAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/BGSchedule.css").toExternalForm());
        // 동적으로 체크박스 추가
        for (int i = 1; i <= 64; i++) {
            CheckBox checkBox = new CheckBox("Img " + i);
            checkBox.setLayoutX((i - 1) % 4 * 86 + 5); // 가로로 4개 배치
            checkBox.setLayoutY((i - 1) / 4 * 30 + 10); // 세로 간격 30
            checkboxPane.getChildren().add(checkBox);

            // 체크박스 선택 이벤트
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (tagMap.size() >= 10) {
                        // 10개 이상 선택 시 경고 메시지
                        showAlert();
                        checkBox.setSelected(false);
                    } else {
                        addTag(checkBox);
                    }
                } else {
                    removeTag(checkBox);
                }
            });
        }
    }

    // 태그 추가
    private void addTag(CheckBox checkBox) {
        Label tag = new Label(checkBox.getText());

        tag.setPrefWidth(TAG_WIDTH);
        tag.setPrefHeight(TAG_HEIGHT);
        tag.setOnMouseClicked(event -> checkBox.setSelected(false)); // 태그 클릭 시 체크 해제
        tagPane.getChildren().add(tag);

        // Map에 추가
        tagMap.put(checkBox, tag);

        // 태그 정렬 및 재배치
        sortTags();
        repositionTags();
    }

    // 태그 제거
    private void removeTag(CheckBox checkBox) {
        Label tag = tagMap.get(checkBox);
        if (tag != null) {
            tagPane.getChildren().remove(tag);
            tagMap.remove(checkBox);

            // 태그 정렬 및 재배치
            sortTags();
            repositionTags();
        }
    }

    // 태그를 번호순으로 정렬
    private void sortTags() {
        List<Map.Entry<CheckBox, Label>> sortedEntries = new ArrayList<>(tagMap.entrySet());
        sortedEntries.sort(Comparator.comparingInt(entry -> Integer.parseInt(entry.getKey().getText().replaceAll("[^0-9]", ""))));

        // 기존 태그를 정렬된 순서대로 다시 배치
        tagPane.getChildren().clear();
        for (Map.Entry<CheckBox, Label> entry : sortedEntries) {
            tagPane.getChildren().add(entry.getValue());
        }
    }

    // 태그를 재배치
    private void repositionTags() {
        double x = TAG_PADDING;
        double y = TAG_PADDING;
        int count = 0; // 한 줄에 배치된 태그 개수

        for (javafx.scene.Node node : tagPane.getChildren()) {
            node.setLayoutX(x);
            node.setLayoutY(y);

            count++;
            if (count == TAGS_PER_ROW) { // 한 줄이 가득 찼으면
                count = 0;
                x = TAG_PADDING;
                y += TAG_HEIGHT + TAG_PADDING; // 다음 줄로 이동
            } else {
                x += TAG_WIDTH + TAG_PADDING; // 다음 태그로 이동
            }
        }
    }

    // 경고 메시지 표시
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(bundle.getString("alert"));
        alert.setHeaderText(bundle.getString("tagLimit"));
        alert.setContentText(bundle.getString("tagLimitDes"));
        alert.showAndWait();
    }

    public void send() {
        String sendMsg = "![0020";
        for (CheckBox value : tagMap.keySet()) {
            sendMsg += String.format("%03d", Integer.parseInt(value.getText().replaceAll("[^0-9]", "")))+" ";
        }
        sendMsg = sendMsg.trim();
        sendMsg+="!]";
        asciiMsgTransceiver.sendMessages(sendMsg, false, progressIndicator);
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
