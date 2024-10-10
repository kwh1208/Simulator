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

        fontGroup1fontPath1.setText(System.getProperty("user.dir") + File.separator + "Font");
        fontGroup1fontPath2.setText(System.getProperty("user.dir") + File.separator + "Font");
        fontGroup1fontPath3.setText(System.getProperty("user.dir") + File.separator + "Font");

        fontGroup2fontPath1.setText(System.getProperty("user.dir") + File.separator + "Font");
        fontGroup2fontPath2.setText(System.getProperty("user.dir") + File.separator + "Font");
        fontGroup2fontPath3.setText(System.getProperty("user.dir") + File.separator + "Font");
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

        // 클릭된 버튼에서 그룹 번호와 버튼 번호 추출
        Button clickedBtn = (Button) event.getSource();
        String groupNum = String.valueOf(clickedBtn.getId().charAt(9));
        String btnNum = String.valueOf(clickedBtn.getId().charAt(17));

        // TextField ID를 구성하여 해당 TextField 찾기
        String textFieldId = "fontGroup" + groupNum + "fontPath" + btnNum;
        TextField fontPath = (TextField) fontSettingAnchorPane.lookup("#" + textFieldId);

        // TextField에서 가져온 경로가 유효한지 확인
        File initialDir = new File(fontPath.getText());
        if (initialDir.exists() && initialDir.isDirectory()) {
            // 경로가 존재하고 디렉터리인 경우에만 초기 디렉터리 설정
            fileChooser.setInitialDirectory(initialDir);
        } else {
            // 기본 경로 설정: 실행 파일이 있는 디렉터리를 기본 경로로 설정
            File defaultDir = new File(System.getProperty("user.dir") + File.separator + "Font");
            if (defaultDir.exists() && defaultDir.isDirectory()) {
                fileChooser.setInitialDirectory(defaultDir);
            } else {
                // 기본 폴더도 존재하지 않으면 user.dir을 설정
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            }
        }

        // FileChooser 열기
        Stage stage = (Stage) fontGroup1ChkBox.getScene().getWindow();
        File selectedFont = fileChooser.showOpenDialog(stage);

        // 선택된 폰트 경로를 TextField에 설정
        if (selectedFont != null) {
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
        String[] fontGroup1Path = new String[3];
        String[] fontType = new String[12];
        String[] fontGroup2Path = null;
        String[] fontGroup3Path = null;
        String[] fontGroup4Path = null;
        String[] fontSize = new String[4];
        //첫번째 그룹
        fontGroup1Path[0] = fontGroup1fontPath1.getText();
        fontType[0] = "영어";
        if (!fontGroup1fontSelected2.getValue().equals("사용안함")){
            fontGroup1Path[1] = fontGroup1fontPath2.getText();
            fontType[1] = fontGroup1fontSelected2.getValue();
        }
        if (!fontGroup1fontSelected3.getValue().equals("사용안함")){
            fontGroup1Path[2] = fontGroup1fontPath3.getText();
            fontType[2] = fontGroup1fontSelected3.getValue();
        }
        fontSize[0] = "8x16/16x16";


        //두번째 그룹
        if (fontGroup2ChkBox.isSelected()) {
            fontGroup2Path = new String[3];
            if (!fontGroup2fontSelected1.getValue().equals("사용안함")){
                fontGroup2Path[0] = fontGroup2fontPath1.getText();
                fontType[3] = fontGroup2fontSelected1.getValue();
            }
            if (!fontGroup2fontSelected2.getValue().equals("사용안함")){
                fontGroup1Path[1] = fontGroup2fontPath2.getText();
                fontType[4] = fontGroup2fontSelected2.getValue();
            }
            if (!fontGroup2fontSelected3.getValue().equals("사용안함")){
                fontGroup1Path[2] = fontGroup2fontPath3.getText();
                fontType[5] = fontGroup2fontSelected3.getValue();
            }
            fontSize[1] = fontGroup2fontsize.getValue();
        }

        //세번째 그룹
        if (fontGroup3ChkBox.isSelected()){
            fontGroup3Path = new String[3];
            if (!fontGroup3fontSelected1.getValue().equals("사용안함")){
                fontGroup3Path[0] = fontGroup3fontPath1.getText();
                fontType[6] = fontGroup3fontSelected1.getValue();
            }
            if (!fontGroup3fontSelected2.getValue().equals("사용안함")){
                fontGroup3Path[1] = fontGroup3fontPath2.getText();
                fontType[7] = fontGroup3fontSelected2.getValue();
            }
            if (!fontGroup3fontSelected3.getValue().equals("사용안함")){
                fontGroup3Path[2] = fontGroup3fontPath3.getText();
                fontType[8] = fontGroup3fontSelected3.getValue();
            }
            fontSize[2] = fontGroup3fontsize.getValue();
        }

        //네번째 그룹
        if (fontGroup4ChkBox.isSelected()){
            fontGroup4Path = new String[3];
            if (!fontGroup4fontSelected1.getValue().equals("사용안함")){
                fontGroup4Path[0] = fontGroup4fontPath1.getText();
                fontType[9] = fontGroup4fontSelected1.getValue();
            }
            if (!fontGroup3fontSelected2.getValue().equals("사용안함")){
                fontGroup4Path[1] = fontGroup4fontPath2.getText();
                fontType[10] = fontGroup4fontSelected2.getValue();
            }
            if (!fontGroup4fontSelected3.getValue().equals("사용안함")){
                fontGroup4Path[2] = fontGroup4fontPath3.getText();
                fontType[11] = fontGroup4fontSelected3.getValue();
            }
            fontSize[3] = fontGroup4fontsize.getValue();
        }

        fontService.sendFont(fontGroup1Path, fontGroup2Path, fontGroup3Path,  fontGroup4Path, fontSize, fontType);

    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}