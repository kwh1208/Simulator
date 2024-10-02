package dbps.dbps.controller;

import dbps.dbps.service.FontService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FontSettingController {
    FontService fontService = FontService.getInstance();
    @FXML
    public ChoiceBox<String> fontGroup2fontSelected1;
    @FXML
    public TextField fontGroup2fontPath1;
    @FXML
    public ChoiceBox<String> fontGroup2fontSelected2;
    @FXML
    public TextField fontGroup2fontPath2;
    @FXML
    public ChoiceBox<String> fontGroup2fontSelected3;
    @FXML
    public TextField fontGroup2fontPath3;
    @FXML
    public ChoiceBox<String> fontGroup3fontSelected1;
    @FXML
    public TextField fontGroup3fontPath1;
    @FXML
    public ChoiceBox<String> fontGroup3fontSelected2;
    @FXML
    public TextField fontGroup3fontPath2;
    @FXML
    public ChoiceBox<String> fontGroup3fontSelected3;
    @FXML
    public TextField fontGroup3fontPath3;
    @FXML
    public TextField fontGroup4fontPath1;
    @FXML
    public TextField fontGroup4fontPath2;
    @FXML
    public TextField fontGroup4fontPath3;
    @FXML
    public ChoiceBox<String> fontGroup4fontSelected1;
    @FXML
    public ChoiceBox<String> fontGroup4fontSelected2;
    @FXML
    public ChoiceBox<String> fontGroup4fontSelected3;
    @FXML
    public ChoiceBox<String> fontGroup2fontsize;
    @FXML
    public ChoiceBox<String> fontGroup3fontsize;
    @FXML
    public ChoiceBox<String> fontGroup4fontsize;
    @FXML
    ChoiceBox<String> fontGroup1fontSelected3;
    @FXML
    TextField fontGroup1fontPath3;
    @FXML
    TextField fontGroup1fontPath2;
    @FXML
    ChoiceBox<String> fontGroup1fontSelected2;
    @FXML
    TextField fontGroup1fontPath1;

    @FXML
    CheckBox fontGroup1ChkBox;

    @FXML
    CheckBox fontGroup2ChkBox;

    @FXML
    CheckBox fontGroup3ChkBox;

    @FXML
    CheckBox fontGroup4ChkBox;

    @FXML
    AnchorPane fontSettingAnchorPane;

    //초기화(하위 폰트그룹이랑 그룹화)
    @FXML
    public void initialize() {
        fontGroup2ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ableAllNodesInPane((Pane) fontGroup2ChkBox.getParent());
                fontGroup3ChkBox.setDisable(false);
            } else {
                fontGroup3ChkBox.setSelected(false);
                fontGroup4ChkBox.setSelected(false);
                disableAllNodesInPane((Pane) fontGroup2ChkBox.getParent());
                disableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
                disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
                fontGroup2ChkBox.setDisable(false);
            }
        });

        fontGroup3ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
                fontGroup4ChkBox.setDisable(false);
            } else {
                fontGroup4ChkBox.setSelected(false);
                disableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
                disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
                fontGroup3ChkBox.setDisable(false);
            }
        });

        fontGroup4ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
            } else {
                disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
                fontGroup4ChkBox.setDisable(false);
            }
        });

        fontSettingAnchorPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/fontSetting.css").toExternalForm());
    }


    //폰트선택창 띄우기
    @FXML
    public void groupFindFont(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("폰트 선택");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("폰트 파일", "*.fnt"),
                new FileChooser.ExtensionFilter("모든 파일", "*.*")
        );

        Stage stage = (Stage) fontGroup1ChkBox.getScene().getWindow();
        File selectedFont = fileChooser.showOpenDialog(stage);

        Button clickedBtn = (Button) event.getSource();
        String groupNum = String.valueOf(clickedBtn.getId().charAt(9));
        String BtnNum = String.valueOf(clickedBtn.getId().charAt(17));

        if (selectedFont != null) {
            String textFieldId = "fontGroup" + groupNum + "fontPath" + BtnNum;
            TextField fontPath = (TextField) fontSettingAnchorPane.lookup("#" + textFieldId);
            fontPath.setText(selectedFont.getAbsolutePath());
        }
    }

    //체크박스 클릭시 폰트설정 비활성화/활성화
    private void disableAllNodesInPane(Pane pane){
        for (Node node : pane.getChildren()) {
            node.setDisable(true);

            if (node instanceof Pane) {
                disableAllNodesInPane((Pane) node);
            }
        }
    }

    private void ableAllNodesInPane(Pane pane){
        for (Node node : pane.getChildren()) {
            node.setDisable(false);
            if (node instanceof Pane) {
                ableAllNodesInPane((Pane) node);
            }
        }
    }

    public void send() throws InterruptedException {
        /**
         * 일단 전광판 끔
         * 그다음에 폰트 그룹 갯수 확인
         * 차례로 폰트 보냄.
         * 다시 전광판 킴
         */
        String[] fontGroup1Path = new String[3];
        String[] fontGroup2Path = null;
        String[] fontGroup3Path = null;
        String[] fontGroup4Path = null;
        String[] fontSize = new String[4];
        //첫번째 그룹
        fontGroup1Path[0] = fontGroup1fontPath1.getText();
        if (!fontGroup1fontSelected2.getValue().equals("사용안함")){
            fontGroup1Path[1] = fontGroup1fontPath2.getText();
        }
        if (!fontGroup1fontSelected3.getValue().equals("사용안함")){
            fontGroup1Path[2] = fontGroup1fontPath3.getText();
        }
        fontSize[0] = "8x16/16x16";


        //두번째 그룹
        if (fontGroup2ChkBox.isSelected()) {
            fontGroup2Path = new String[3];
            if (!fontGroup2fontSelected1.getValue().equals("사용안함")){
                fontGroup2Path[0] = fontGroup2fontPath1.getText();
            }
            if (!fontGroup2fontSelected2.getValue().equals("사용안함")){
                fontGroup1Path[1] = fontGroup2fontPath2.getText();
            }
            if (!fontGroup2fontSelected3.getValue().equals("사용안함")){
                fontGroup1Path[2] = fontGroup2fontPath3.getText();
            }
            fontSize[1] = fontGroup2fontsize.getValue();
        }

        //세번째 그룹
        if (fontGroup3ChkBox.isSelected()){
            fontGroup3Path = new String[3];
            if (!fontGroup3fontSelected1.getValue().equals("사용안함")){
                fontGroup3Path[0] = fontGroup3fontPath1.getText();
            }
            if (!fontGroup3fontSelected2.getValue().equals("사용안함")){
                fontGroup3Path[1] = fontGroup3fontPath2.getText();
            }
            if (!fontGroup3fontSelected3.getValue().equals("사용안함")){
                fontGroup3Path[2] = fontGroup3fontPath3.getText();
            }
            fontSize[2] = fontGroup3fontsize.getValue();
        }

        //네번째 그룹
        if (fontGroup4ChkBox.isSelected()){
            fontGroup4Path = new String[3];
            if (!fontGroup4fontSelected1.getValue().equals("사용안함")){
                fontGroup4Path[0] = fontGroup4fontPath1.getText();
            }
            if (!fontGroup3fontSelected2.getValue().equals("사용안함")){
                fontGroup4Path[1] = fontGroup4fontPath2.getText();
            }
            if (!fontGroup4fontSelected3.getValue().equals("사용안함")){
                fontGroup4Path[2] = fontGroup4fontPath3.getText();
            }
            fontSize[3] = fontGroup4fontsize.getValue();
        }

        fontService.sendFont(fontGroup1Path, fontGroup2Path, fontGroup3Path, fontGroup4Path, fontSize);

    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}