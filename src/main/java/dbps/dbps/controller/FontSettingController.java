package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.FontService;
import dbps.dbps.service.ResourceManager;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class FontSettingController {
    public Label fontProgressLabel;
    public ProgressBar fontProgressBar;
    public Label fontCapacity;
    public ChoiceBox<String> fontGroup1fontSelected1;
    ResourceBundle bundle = ResourceManager.getInstance().getBundle();

    ConfigService configService;
    FontService fontService = FontService.getInstance();
    @FXML
    public ChoiceBox<String> fontGroup2fontSelected1;
    @FXML
    public TextArea fontGroup2fontPath1;
    @FXML
    public ChoiceBox<String> fontGroup2fontSelected2;
    @FXML
    public TextArea fontGroup2fontPath2;
    @FXML
    public ChoiceBox<String> fontGroup2fontSelected3;
    @FXML
    public TextArea fontGroup2fontPath3;
    @FXML
    public ChoiceBox<String> fontGroup3fontSelected1;
    @FXML
    public TextArea fontGroup3fontPath1;
    @FXML
    public ChoiceBox<String> fontGroup3fontSelected2;
    @FXML
    public TextArea fontGroup3fontPath2;
    @FXML
    public ChoiceBox<String> fontGroup3fontSelected3;
    @FXML
    public TextArea fontGroup3fontPath3;
    @FXML
    public TextArea fontGroup4fontPath1;
    @FXML
    public TextArea fontGroup4fontPath2;
    @FXML
    public TextArea fontGroup4fontPath3;
    @FXML
    public ChoiceBox<String> fontGroup4fontSelected1;
    @FXML
    public ChoiceBox<String> fontGroup4fontSelected2;
    @FXML
    public ChoiceBox<String> fontGroup4fontSelected3;
    @FXML
    ChoiceBox<String> fontGroup1fontSelected3;
    @FXML
    TextArea fontGroup1fontPath3;
    @FXML
    TextArea fontGroup1fontPath2;
    @FXML
    ChoiceBox<String> fontGroup1fontSelected2;
    @FXML
    TextArea fontGroup1fontPath1;

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

    AsciiMsgTransceiver asciiMsgTransceiver;
    //초기화(하위 폰트그룹이랑 그룹화)
    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        fontGroup1ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            fontGroup1ChkBox.setSelected(true);
        });

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
            updateFontSize();
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
            updateFontSize();
        });

        fontGroup4ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                ableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
            } else {
                disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
                fontGroup4ChkBox.setDisable(false);
            }
            updateFontSize();
        });

        fontSettingAnchorPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/fontSetting.css").toExternalForm());

        String defaultPath = System.getProperty("user.dir") + File.separator + "Font";
        fontGroup1fontPath1.setText(configService.getProperty("fontGroup1FontPath1") != null
                ? configService.getProperty("fontGroup1FontPath1")
                : defaultPath);
        fontGroup1fontPath2.setText(configService.getProperty("fontGroup1FontPath2") != null
                ? configService.getProperty("fontGroup1FontPath2")
                : defaultPath);
        fontGroup1fontPath3.setText(configService.getProperty("fontGroup1FontPath3") != null
                ? configService.getProperty("fontGroup1FontPath3")
                : defaultPath);

        fontGroup2fontPath1.setText(configService.getProperty("fontGroup2FontPath1") != null
                ? configService.getProperty("fontGroup2FontPath1")
                : defaultPath);
        fontGroup2fontPath2.setText(configService.getProperty("fontGroup2FontPath2") != null
                ? configService.getProperty("fontGroup2FontPath2")
                : defaultPath);
        fontGroup2fontPath3.setText(configService.getProperty("fontGroup2FontPath3") != null
                ? configService.getProperty("fontGroup2FontPath3")
                : defaultPath);

        fontGroup3fontPath1.setText(configService.getProperty("fontGroup3FontPath1") != null
                ? configService.getProperty("fontGroup3FontPath1")
                : defaultPath);
        fontGroup3fontPath2.setText(configService.getProperty("fontGroup3FontPath2") != null
                ? configService.getProperty("fontGroup3FontPath2")
                : defaultPath);
        fontGroup3fontPath3.setText(configService.getProperty("fontGroup3FontPath3") != null
                ? configService.getProperty("fontGroup3FontPath3")
                : defaultPath);

        fontGroup4fontPath1.setText(configService.getProperty("fontGroup4FontPath1") != null
                ? configService.getProperty("fontGroup4FontPath1")
                : defaultPath);
        fontGroup4fontPath2.setText(configService.getProperty("fontGroup4FontPath2") != null
                ? configService.getProperty("fontGroup4FontPath2")
                : defaultPath);
        fontGroup4fontPath3.setText(configService.getProperty("fontGroup4FontPath3") != null
                ? configService.getProperty("fontGroup4FontPath3")
                : defaultPath);

        moveCursorRight(fontGroup1fontPath1);
        moveCursorRight(fontGroup1fontPath2);
        moveCursorRight(fontGroup1fontPath3);
        moveCursorRight(fontGroup2fontPath1);
        moveCursorRight(fontGroup2fontPath2);
        moveCursorRight(fontGroup2fontPath3);
        moveCursorRight(fontGroup3fontPath1);
        moveCursorRight(fontGroup3fontPath2);
        moveCursorRight(fontGroup3fontPath3);
        moveCursorRight(fontGroup4fontPath1);
        moveCursorRight(fontGroup4fontPath2);
        moveCursorRight(fontGroup4fontPath3);

        fontGroup1fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup1FontType2", newValue);
            updateFontSize();
        });
        fontGroup1fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup1FontType3", newValue);
            updateFontSize();
        });
        fontGroup2fontSelected1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup2FontType1", newValue);
            updateFontSize();
        });
        fontGroup2fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup2FontType2", newValue);
            updateFontSize();
        });
        fontGroup2fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup2FontType3", newValue);
            updateFontSize();
        });
        fontGroup3fontSelected1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup3FontType1", newValue);
            updateFontSize();
        });
        fontGroup3fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup3FontType2", newValue);
            updateFontSize();
        });
        fontGroup3fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup3FontType3", newValue);
            updateFontSize();
        });
        fontGroup4fontSelected1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup4FontType1", newValue);
            updateFontSize();
        });
        fontGroup4fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup4FontType2", newValue);
            updateFontSize();
        });
        fontGroup4fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup4FontType3", newValue);
            updateFontSize();
        });

        addItem();
    }

    private void addItem() {
        fontGroup1fontSelected1.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("english")
        );

        fontGroup1fontSelected2.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("UNI-KR"),
                bundle.getString("UNI-JP"),
                bundle.getString("UNI-CN"),
                bundle.getString("CombinationType"),
                bundle.getString("UNI-all")
        );

        fontGroup1fontSelected3.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("userFont")
        );

        fontGroup2fontSelected1.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("english")
        );

        fontGroup2fontSelected2.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("UNI-KR"),
                bundle.getString("UNI-JP"),
                bundle.getString("UNI-CN"),
                bundle.getString("CombinationType"),
                bundle.getString("UNI-all")
        );

        fontGroup2fontSelected3.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("userFont")
        );

        fontGroup3fontSelected1.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("english")
        );

        fontGroup3fontSelected2.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("UNI-KR"),
                bundle.getString("UNI-JP"),
                bundle.getString("UNI-CN"),
                bundle.getString("CombinationType"),
                bundle.getString("UNI-all")
        );

        fontGroup3fontSelected3.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("userFont")
        );

        fontGroup4fontSelected1.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("english")
        );

        fontGroup4fontSelected2.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("UNI-KR"),
                bundle.getString("UNI-JP"),
                bundle.getString("UNI-CN"),
                bundle.getString("CombinationType"),
                bundle.getString("UNI-all")
        );

        fontGroup4fontSelected3.getItems().addAll(
                bundle.getString("notUsed"),
                bundle.getString("userFont")
        );
    }

    private void moveCursorRight(TextArea textArea) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            moveCaretToEnd(textArea);
        });

        // 포커스를 얻거나 잃을 때마다 커서를 오른쪽 끝으로 이동
        textArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            moveCaretToEnd(textArea);
        });

        // 초기 커서 위치 설정 (텍스트 끝으로)
        moveCaretToEnd(textArea);
    }


    private void moveCaretToEnd(TextArea textArea) {
        textArea.positionCaret(textArea.getText().length());  // 커서를 텍스트 끝으로 이동
    }


    //폰트선택창 띄우기
    @FXML
    public void groupFindFont(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("fontSelect"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("폰트 파일", "*.fnt"),
                new FileChooser.ExtensionFilter("모든 파일", "*.*")
        );

        // 클릭된 버튼에서 그룹 번호와 버튼 번호 추출
        Button clickedBtn = (Button) event.getSource();

        String TextAreaId = (String) clickedBtn.getUserData();
        TextArea fontPath = (TextArea) fontSettingAnchorPane.lookup("#" + TextAreaId);

        // TextArea에서 가져온 경로가 유효한지 확인
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

        // 선택된 폰트 경로를 TextArea에 설정
        if (selectedFont != null) {
            fontPath.setText(selectedFont.getAbsolutePath());
            String target = (String) clickedBtn.getUserData();
            configService.setProperty(target, selectedFont.getAbsolutePath());
        }

        moveCaretToEnd(fontPath);
        updateFontSize();
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
        //첫번째 그룹
        fontGroup1Path[0] = fontGroup1fontPath1.getText();
        fontType[0] = "영어";
        if (!fontGroup1fontSelected2.getValue().equals(bundle.getString("notUsed"))){
            fontGroup1Path[1] = fontGroup1fontPath2.getText();
            fontType[1] = fontGroup1fontSelected2.getValue();
        }
        if (!fontGroup1fontSelected3.getValue().equals(bundle.getString("notUsed"))){
            fontGroup1Path[2] = fontGroup1fontPath3.getText();
            fontType[2] = fontGroup1fontSelected3.getValue();
        }


        //두번째 그룹
        if (fontGroup2ChkBox.isSelected()) {
            fontGroup2Path = new String[3];
            if (!fontGroup2fontSelected1.getValue().equals(bundle.getString("notUsed"))){
                fontGroup2Path[0] = fontGroup2fontPath1.getText();
                fontType[3] = fontGroup2fontSelected1.getValue();
            }
            if (!fontGroup2fontSelected2.getValue().equals(bundle.getString("notUsed"))){
                fontGroup2Path[1] = fontGroup2fontPath2.getText();
                fontType[4] = fontGroup2fontSelected2.getValue();
            }
            if (!fontGroup2fontSelected3.getValue().equals(bundle.getString("notUsed"))){
                fontGroup2Path[2] = fontGroup2fontPath3.getText();
                fontType[5] = fontGroup2fontSelected3.getValue();
            }

        }

        //세번째 그룹
        if (fontGroup3ChkBox.isSelected()){
            fontGroup3Path = new String[3];
            if (!fontGroup3fontSelected1.getValue().equals(bundle.getString("notUsed"))){
                fontGroup3Path[0] = fontGroup3fontPath1.getText();
                fontType[6] = fontGroup3fontSelected1.getValue();
            }
            if (!fontGroup3fontSelected2.getValue().equals(bundle.getString("notUsed"))){
                fontGroup3Path[1] = fontGroup3fontPath2.getText();
                fontType[7] = fontGroup3fontSelected2.getValue();
            }
            if (!fontGroup3fontSelected3.getValue().equals(bundle.getString("notUsed"))){
                fontGroup3Path[2] = fontGroup3fontPath3.getText();
                fontType[8] = fontGroup3fontSelected3.getValue();
            }

        }

        //네번째 그룹
        if (fontGroup4ChkBox.isSelected()){
            fontGroup4Path = new String[3];
            if (!fontGroup4fontSelected1.getValue().equals(bundle.getString("notUsed"))){
                fontGroup4Path[0] = fontGroup4fontPath1.getText();
                fontType[9] = fontGroup4fontSelected1.getValue();
            }
            if (!fontGroup3fontSelected2.getValue().equals(bundle.getString("notUsed"))){
                fontGroup4Path[1] = fontGroup4fontPath2.getText();
                fontType[10] = fontGroup4fontSelected2.getValue();
            }
            if (!fontGroup4fontSelected3.getValue().equals(bundle.getString("notUsed"))){
                fontGroup4Path[2] = fontGroup4fontPath3.getText();
                fontType[11] = fontGroup4fontSelected3.getValue();
            }
        }

        Task<Void> fontSend = fontService.sendFont(fontGroup1Path, fontGroup2Path, fontGroup3Path, fontGroup4Path, fontType, fontProgressBar, fontProgressLabel);

        fontSend.setOnRunning(e -> {
            // Task가 시작될 때 로딩 애니메이션 표시
            fontProgressBar.setVisible(true);
            fontProgressLabel.setVisible(true);
        });

        fontSend.setOnSucceeded(e -> {
            // Task가 성공적으로 끝났을 때 로딩 애니메이션 숨김
            fontProgressBar.setVisible(false);
            fontProgressLabel.setVisible(false);
        });

        fontSend.setOnFailed(e -> {
            // Task가 실패했을 때 로딩 애니메이션 숨김
            fontProgressBar.setVisible(false);
            fontProgressLabel.setVisible(false);
        });

        fontSend.setOnCancelled(e -> {
            // Task가 취소됐을 때 로딩 애니메이션 숨김
            fontProgressBar.setVisible(false);
            fontProgressLabel.setVisible(false);
        });

        new Thread(fontSend).start();

    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }



    private void updateFontSize() {
        long totalFileSize = 0; // 총 파일 크기
        System.out.println("Updating font size...");

        try {
            // 1번 폰트 그룹
            totalFileSize += getFileSize(fontGroup1fontPath1.getText());
            if (fontGroup1fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup1fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                String size = extractTwoCharsAroundX(fontGroup1fontPath2.getText(), 'x');
                System.out.println("Font Group 1 Size: " + size);
                long sizeInt = parseSize(size);
                totalFileSize += calculateFontSize(sizeInt, fontGroup1fontSelected2.getSelectionModel().getSelectedItem());
            }
            if (fontGroup1fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup1fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup1fontPath3.getText());
            }

            // 2번 폰트 그룹
            if (fontGroup2fontSelected1.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup2fontSelected1.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup2fontPath1.getText());
            }
            if (fontGroup2fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup2fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                String size = extractTwoCharsAroundX(fontGroup2fontPath2.getText(), 'x');
                System.out.println("Font Group 2 Size: " + size);
                long sizeInt = parseSize(size);
                totalFileSize += calculateFontSize(sizeInt, fontGroup2fontSelected2.getSelectionModel().getSelectedItem());
            }
            if (fontGroup2fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup2fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup2fontPath3.getText());
            }

            // 3번 폰트 그룹
            if (fontGroup3fontSelected1.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup3fontSelected1.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup3fontPath1.getText());
            }
            if (fontGroup3fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup3fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                String size = extractTwoCharsAroundX(fontGroup3fontPath2.getText(), 'x');
                System.out.println("Font Group 3 Size: " + size);
                long sizeInt = parseSize(size);
                totalFileSize += calculateFontSize(sizeInt, fontGroup3fontSelected2.getSelectionModel().getSelectedItem());
            }
            if (fontGroup3fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup3fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup3fontPath3.getText());
            }

            // 4번 폰트 그룹
            if (fontGroup4fontSelected1.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup4fontSelected1.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup4fontPath1.getText());
            }
            if (fontGroup4fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup4fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                String size = extractTwoCharsAroundX(fontGroup4fontPath2.getText(), 'x');
                System.out.println("Font Group 4 Size: " + size);
                long sizeInt = parseSize(size);
                totalFileSize += calculateFontSize(sizeInt, fontGroup4fontSelected2.getSelectionModel().getSelectedItem());
            }
            if (fontGroup4fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup4fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup4fontPath3.getText());
            }

            fontCapacity.setText(totalFileSize + "/3145727 Byte");
            System.out.println("Total File Size: " + totalFileSize);

        } catch (Exception e) {
            System.err.println("Error while updating font size: " + e.getMessage());
        }
    }


    private long getFileSize(String filePath){
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.length()-16;
        }
        return 0;
    }

    public String extractTwoCharsAroundX(String input, char target) {
        if (input == null || input.isEmpty()) {
            return "Invalid Position";
        }

        int index = input.indexOf(target);
        if (index < 2 || index > input.length() - 3) {
            return "Invalid Position";
        }

        return input.substring(index - 2, index + 3);
    }

    private long parseSize(String size) {
        if ("Invalid Position".equals(size)) {
            return 0; // 잘못된 입력 처리
        }

        try {
            String[] dimensions = size.split("x");
            return Long.parseLong(dimensions[0]) * Long.parseLong(dimensions[1]) / 16;
        } catch (Exception e) {
            System.err.println("Error parsing size: " + size);
            return 0;
        }
    }

    private long calculateFontSize(long sizeInt, String fontType) {
        switch (fontType) {
            case "유니코드 한국어":
                return sizeInt * 11172L;
            case "유니코드 일본어":
                return sizeInt * 192L;
            case "유니코드 중국어":
                return sizeInt * 20992L;
            case "한글 조합형":
                return sizeInt * 360L;
            case "유니코드 전체":
                return sizeInt; // 전체 크기 반환
            default:
                return 0; // 알 수 없는 타입 처리
        }
    }




}