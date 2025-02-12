package dbps.dbps.controller;

import dbps.dbps.service.*;
import dbps.dbps.service.connectManager.TCPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class FontSettingController {
    public Label fontProgressLabel;
    public ProgressBar fontProgressBar;
    public Label fontCapacity;
    public ComboBox<String> fontGroup1fontSelected1;
    ResourceBundle bundle = ResourceManager.getInstance().getBundle();

    ConfigService configService;
    FontService fontService = FontService.getInstance();
    TCPManager tcpManager;
    LogService logService;
    @FXML
    public ComboBox<String> fontGroup2fontSelected1;
    @FXML
    public TextArea fontGroup2fontPath1;
    @FXML
    public ComboBox<String> fontGroup2fontSelected2;
    @FXML
    public TextArea fontGroup2fontPath2;
    @FXML
    public ComboBox<String> fontGroup2fontSelected3;
    @FXML
    public TextArea fontGroup2fontPath3;
    @FXML
    public ComboBox<String> fontGroup3fontSelected1;
    @FXML
    public TextArea fontGroup3fontPath1;
    @FXML
    public ComboBox<String> fontGroup3fontSelected2;
    @FXML
    public TextArea fontGroup3fontPath2;
    @FXML
    public ComboBox<String> fontGroup3fontSelected3;
    @FXML
    public TextArea fontGroup3fontPath3;
    @FXML
    public TextArea fontGroup4fontPath1;
    @FXML
    public TextArea fontGroup4fontPath2;
    @FXML
    public TextArea fontGroup4fontPath3;
    @FXML
    public ComboBox<String> fontGroup4fontSelected1;
    @FXML
    public ComboBox<String> fontGroup4fontSelected2;
    @FXML
    public ComboBox<String> fontGroup4fontSelected3;
    @FXML
    ComboBox<String> fontGroup1fontSelected3;
    @FXML
    TextArea fontGroup1fontPath3;
    @FXML
    TextArea fontGroup1fontPath2;
    @FXML
    ComboBox<String> fontGroup1fontSelected2;
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
    Stage progressStage;
    ProgressBar progressBar;
    Label progressLabel;

    AsciiMsgTransceiver asciiMsgTransceiver;
    HexMsgTransceiver hexMsgTransceiver;
    //초기화(하위 폰트그룹이랑 그룹화)
    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        tcpManager = TCPManager.getManager();
        logService = LogService.getLogService();







        if (Boolean.parseBoolean(configService.getProperty("fontGroup2selected"))){
            fontGroup2ChkBox.setSelected(true);
            ableAllNodesInPane((Pane) fontGroup2ChkBox.getParent());
        }
        else {
            fontGroup2ChkBox.setSelected(false);
            disableAllNodesInPane((Pane) fontGroup2ChkBox.getParent());
        }

        if (fontGroup2ChkBox.isSelected()&&Boolean.parseBoolean(configService.getProperty("fontGroup3selected"))){
            fontGroup3ChkBox.setSelected(true);
            ableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
        }
        else {
            fontGroup3ChkBox.setSelected(false);
            disableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
        }

        if (fontGroup3ChkBox.isSelected()&&Boolean.parseBoolean(configService.getProperty("fontGroup4selected"))){
            fontGroup4ChkBox.setSelected(true);
            ableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
        }
        else {
            fontGroup4ChkBox.setSelected(false);
            disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
        }





        fontGroup2ChkBox.setDisable(false);
        fontGroup3ChkBox.setDisable(false);
        fontGroup4ChkBox.setDisable(false);

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
                fontGroup3ChkBox.setDisable(false);
                fontGroup4ChkBox.setDisable(false);
            }
            updateFontSize();
            configService.setProperty("fontGroup2selected", String.valueOf(fontGroup2ChkBox.isSelected()));
        });

        fontGroup3ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!fontGroup2ChkBox.isSelected()){
                fontGroup3ChkBox.setSelected(false);
                return;
            }
            if (newValue) {
                ableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
                fontGroup4ChkBox.setDisable(false);
            } else {
                fontGroup4ChkBox.setSelected(false);
                disableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
                disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
                fontGroup2ChkBox.setDisable(false);
                fontGroup3ChkBox.setDisable(false);
                fontGroup4ChkBox.setDisable(false);
            }
            updateFontSize();
            configService.setProperty("fontGroup3selected", String.valueOf(fontGroup3ChkBox.isSelected()));
        });

        fontGroup4ChkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!fontGroup3ChkBox.isSelected()){
                fontGroup4ChkBox.setSelected(false);
                return;
            }
            if (newValue) {
                ableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
            } else {
                disableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
                fontGroup2ChkBox.setDisable(false);
                fontGroup3ChkBox.setDisable(false);
                fontGroup4ChkBox.setDisable(false);
            }
            updateFontSize();
            configService.setProperty("fontGroup4selected", String.valueOf(fontGroup4ChkBox.isSelected()));
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

        fontGroup1fontSelected2.setValue(configService.getProperty("fontGroup1FontType2"));
        fontGroup1fontSelected3.setValue(configService.getProperty("fontGroup1FontType3"));
        fontGroup2fontSelected1.setValue(configService.getProperty("fontGroup2FontType1"));
        fontGroup2fontSelected2.setValue(configService.getProperty("fontGroup2FontType2"));
        fontGroup2fontSelected3.setValue(configService.getProperty("fontGroup2FontType3"));
        fontGroup3fontSelected1.setValue(configService.getProperty("fontGroup3FontType1"));
        fontGroup3fontSelected2.setValue(configService.getProperty("fontGroup3FontType2"));
        fontGroup3fontSelected3.setValue(configService.getProperty("fontGroup3FontType3"));
        fontGroup4fontSelected1.setValue(configService.getProperty("fontGroup4FontType1"));
        fontGroup4fontSelected2.setValue(configService.getProperty("fontGroup4FontType2"));
        fontGroup4fontSelected3.setValue(configService.getProperty("fontGroup4FontType3"));

        progressStage = new Stage();
        progressStage.initModality(Modality.APPLICATION_MODAL); // 부모 창을 블로킹
        progressStage.setTitle("폰트 전송");

        progressBar = new ProgressBar(0);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.setVisible(true);
        progressBar.setPrefWidth(250);

        progressLabel = new Label("폰트 전송 준비 중...");

        Button cancelButton = new Button("취소");
        Region spacer = new Region(); // 중간에 공간을 추가
        HBox buttonBox = new HBox(spacer, cancelButton);
        HBox.setHgrow(spacer, Priority.ALWAYS); // spacer가 가능한 공간을 채우도록 설정
        buttonBox.setSpacing(10); // 버튼과 spacer 사이 여백 설정

        cancelButton.setOnAction(e -> {
            if (fontSendTask != null) {
                fontSendTask.cancel();
                progressBar.setProgress(0);
                closeWindowAfterDelay(progressStage, 1000);
            }
        });

        VBox vbox = new VBox(15, progressLabel, progressBar, buttonBox);
        vbox.setStyle("-fx-padding: 20px;");

        Scene progressScene = new Scene(vbox, 300, 150);
        progressStage.setScene(progressScene);
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

    private Task<Void> fontSendTask;  // ✅ Task를 전역 변수로 선언


    public void send() throws InterruptedException {
        String[] fontGroup1Path = new String[3];
        String[] fontType = new String[12];
        String[] fontGroup2Path = null;
        String[] fontGroup3Path = null;
        String[] fontGroup4Path = null;

        //첫번째 그룹
        if (chkFont(fontGroup1fontPath1.getText(), "영어")){
            logService.warningLog("폰트그룹 1 영어 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
            return;
        }
        fontGroup1Path[0] = fontGroup1fontPath1.getText();
        configService.setProperty("fontGroup1FontPath1", fontGroup1Path[0]);
        fontType[0] = "영어";

        if (chkFont(fontGroup1fontPath2.getText(), fontGroup1fontSelected2.getValue())){
            logService.warningLog("폰트그룹 1 "+fontGroup1fontSelected2.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
            return;
        }
        if (!fontGroup1fontSelected2.getValue().equals(bundle.getString("notUsed"))){
            fontGroup1Path[1] = fontGroup1fontPath2.getText();
            fontType[1] = fontGroup1fontSelected2.getValue();
            configService.setProperty("fontGroup1FontPath2", fontGroup1Path[1]);
        }

        if (chkFont(fontGroup1fontPath3.getText(), fontGroup1fontSelected3.getValue())){
            logService.warningLog("폰트그룹 1 "+fontGroup1fontSelected3.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
            return;
        }
        if (!fontGroup1fontSelected3.getValue().equals(bundle.getString("notUsed"))){
            fontGroup1Path[2] = fontGroup1fontPath3.getText();
            fontType[2] = fontGroup1fontSelected3.getValue();
            configService.setProperty("fontGroup1FontPath3", fontGroup1Path[2]);
        }


        //두번째 그룹

        if (fontGroup2ChkBox.isSelected()) {
            fontGroup2Path = new String[3];
            if (chkFont(fontGroup2fontPath1.getText(), fontGroup2fontSelected1.getValue())){
                logService.warningLog("폰트그룹 2 "+fontGroup2fontSelected1.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup2fontSelected1.getValue().equals(bundle.getString("notUsed"))){
                fontGroup2Path[0] = fontGroup2fontPath1.getText();
                fontType[3] = fontGroup2fontSelected1.getValue();
                configService.setProperty("fontGroup2FontPath1", fontGroup2Path[0]);
            }

            if (chkFont(fontGroup2fontPath2.getText(), fontGroup2fontSelected2.getValue())){
                logService.warningLog("폰트그룹 2 "+fontGroup2fontSelected2.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup2fontSelected2.getValue().equals(bundle.getString("notUsed"))){
                fontGroup2Path[1] = fontGroup2fontPath2.getText();
                fontType[4] = fontGroup2fontSelected2.getValue();
                configService.setProperty("fontGroup2FontPath2", fontGroup2Path[1]);
            }

            if (chkFont(fontGroup2fontPath3.getText(), fontGroup2fontSelected3.getValue())){
                logService.warningLog("폰트그룹 2 "+fontGroup2fontSelected3.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup2fontSelected3.getValue().equals(bundle.getString("notUsed"))){
                fontGroup2Path[2] = fontGroup2fontPath3.getText();
                fontType[5] = fontGroup2fontSelected3.getValue();
                configService.setProperty("fontGroup2FontPath3", fontGroup2Path[2]);
            }
        }

        //세번째 그룹
        if (fontGroup3ChkBox.isSelected()){
            fontGroup3Path = new String[3];

            if (chkFont(fontGroup3fontPath1.getText(), fontGroup3fontSelected1.getValue())){
                logService.warningLog("폰트그룹 3 "+fontGroup3fontSelected1.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup3fontSelected1.getValue().equals(bundle.getString("notUsed"))){
                fontGroup3Path[0] = fontGroup3fontPath1.getText();
                fontType[6] = fontGroup3fontSelected1.getValue();
                configService.setProperty("fontGroup3FontPath1", fontGroup3Path[1]);
            }

            if (chkFont(fontGroup3fontPath2.getText(), fontGroup3fontSelected2.getValue())){
                logService.warningLog("폰트그룹 3 "+fontGroup3fontSelected2.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup3fontSelected2.getValue().equals(bundle.getString("notUsed"))){
                fontGroup3Path[1] = fontGroup3fontPath2.getText();
                fontType[7] = fontGroup3fontSelected2.getValue();
                configService.setProperty("fontGroup3FontPath2", fontGroup3Path[1]);
            }

            if (chkFont(fontGroup3fontPath3.getText(), fontGroup3fontSelected3.getValue())){
                logService.warningLog("폰트그룹 3 "+fontGroup3fontSelected3.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup3fontSelected3.getValue().equals(bundle.getString("notUsed"))){
                fontGroup3Path[2] = fontGroup3fontPath3.getText();
                fontType[8] = fontGroup3fontSelected3.getValue();
                configService.setProperty("fontGroup3FontPath3", fontGroup3Path[2]);
            }

        }

        //네번째 그룹
        if (fontGroup4ChkBox.isSelected()){
            fontGroup4Path = new String[3];

            if (chkFont(fontGroup4fontPath1.getText(), fontGroup4fontSelected1.getValue())){
                logService.warningLog("폰트그룹 4 "+fontGroup4fontSelected1.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup4fontSelected1.getValue().equals(bundle.getString("notUsed"))){
                fontGroup4Path[0] = fontGroup4fontPath1.getText();
                fontType[9] = fontGroup4fontSelected1.getValue();
                configService.setProperty("fontGroup4FontPath1", fontGroup4Path[0]);
            }

            if (chkFont(fontGroup4fontPath2.getText(), fontGroup4fontSelected2.getValue())){
                logService.warningLog("폰트그룹 4 "+fontGroup4fontSelected2.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup3fontSelected2.getValue().equals(bundle.getString("notUsed"))){
                fontGroup4Path[1] = fontGroup4fontPath2.getText();
                fontType[10] = fontGroup4fontSelected2.getValue();
                configService.setProperty("fontGroup4FontPath2", fontGroup4Path[1]);
            }

            if (chkFont(fontGroup4fontPath3.getText(), fontGroup4fontSelected3.getValue())){
                logService.warningLog("폰트그룹 4 "+fontGroup4fontSelected3.getValue()+" 폰트의 폰트종류와 폰트파일이 맞지 않습니다.");
                return;
            }
            if (!fontGroup4fontSelected3.getValue().equals(bundle.getString("notUsed"))){
                fontGroup4Path[2] = fontGroup4fontPath3.getText();
                fontType[11] = fontGroup4fontSelected3.getValue();
                configService.setProperty("fontGroup4FontPath3", fontGroup4Path[2]);
            }
        }

        // ✅ 진행 상태를 표시할 새 창 만들기
        Platform.runLater(()->{
            progressStage.show();
        });

        // ✅ 폰트 전송 Task 실행
        fontSendTask = fontService.sendFont(fontGroup1Path, fontGroup2Path, fontGroup3Path, fontGroup4Path, fontType, progressBar, progressLabel);

        fontSendTask.setOnRunning(e -> {
            progressLabel.setText("폰트 전송 준비 중...");
            progressBar.setProgress(-1); // 진행 중 상태
        });

        fontSendTask.setOnSucceeded(e -> {
            progressLabel.setText("폰트 전송 완료!");
            progressBar.setProgress(1.0);
            closeWindowAfterDelay(progressStage, 1000); // 1초 후 창 닫기
        });

        new Thread(fontSendTask).start();
    }

    private boolean chkFont(String fileName, String fontType){
        if (fontType==null||fontType.equals("사용안함")) return false;

        if (!fileName.contains("fnt")){
            return true;
        }


        if (fontType.contains("영어")){
            return !fileName.contains("ENG");
        }
        if (fontType.contains("사용자폰트")){
            return !fileName.contains("USER");
        }
        if (fontType.contains("조합형")){
            if ((fileName.toLowerCase()).contains("uni")){
                return true;
            }
            return !fileName.contains("KOR");
        }
        if (fontType.contains("유니코드")){
            return !(fileName.toLowerCase()).contains("uni");
        }

        return false;
    }

    // ✅ 일정 시간이 지나면 진행 상태 창 닫기
    private void closeWindowAfterDelay(Stage stage, int delayMillis) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMillis);
                Platform.runLater(stage::close);
            } catch (InterruptedException ignored) {
            }
        }).start();
        tcpManager.disconnect();
    }

    public void close(MouseEvent mouseEvent) {
        if (fontSendTask != null){
            fontSendTask.cancel();
            hexMsgTransceiver.sendMessages("10 02 00 00 02 45 01 10 03 ", commonProgressIndicator);
        }
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void cancel(){
        fontSendTask.cancel();

        hexMsgTransceiver.sendMessages("10 02 00 00 02 45 01 10 03 ", commonProgressIndicator);
    }



    private void updateFontSize() {
        long totalFileSize = 0; // 총 파일 크기

        try {
            // 1번 폰트 그룹
            totalFileSize += getFileSize(fontGroup1fontPath1.getText());
            if (fontGroup1fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup1fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                if (fontGroup1fontSelected2.getValue().equals("한글 조합형")||fontGroup1fontSelected2.getValue().equals("유니코드 전체")){
                    totalFileSize += Files.size(Paths.get(fontGroup1fontPath2.getText()))-16L;
                }
                else{
                String size = extractTwoCharsAroundX(fontGroup1fontPath2.getText(), 'x');
                long sizeInt = parseSize(size);
                totalFileSize += calculateFontSize(sizeInt, fontGroup1fontSelected2.getSelectionModel().getSelectedItem());}
            }
            if (fontGroup1fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup1fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup1fontPath3.getText());
            }

            // 2번 폰트 그룹
            if (fontGroup2ChkBox.isSelected()){
            if (fontGroup2fontSelected1.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup2fontSelected1.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup2fontPath1.getText());
            }
            if (fontGroup2fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup2fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                if (fontGroup2fontSelected2.getValue().equals("한글 조합형")||fontGroup2fontSelected2.getValue().equals("유니코드 전체")){
                    totalFileSize += Files.size(Paths.get(fontGroup2fontPath2.getText()))-16L;
                }
                else {
                    String size = extractTwoCharsAroundX(fontGroup2fontPath2.getText(), 'x');
                    long sizeInt = parseSize(size);
                    totalFileSize += calculateFontSize(sizeInt, fontGroup2fontSelected2.getSelectionModel().getSelectedItem());
                }
            }
            if (fontGroup2fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup2fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup2fontPath3.getText());
            }}

            // 3번 폰트 그룹
            if (fontGroup3ChkBox.isSelected()){
            if (fontGroup3fontSelected1.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup3fontSelected1.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup3fontPath1.getText());
            }
            if (fontGroup3fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup3fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                if (fontGroup3fontSelected2.getValue().equals("한글 조합형")||fontGroup3fontSelected2.getValue().equals("유니코드 전체")){
                    totalFileSize += Files.size(Paths.get(fontGroup3fontPath2.getText()))-16L;
                }
                else {
                    String size = extractTwoCharsAroundX(fontGroup3fontPath2.getText(), 'x');
                    long sizeInt = parseSize(size);
                    totalFileSize += calculateFontSize(sizeInt, fontGroup3fontSelected2.getSelectionModel().getSelectedItem());
                }
            }
            if (fontGroup3fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup3fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup3fontPath3.getText());
            }}

            // 4번 폰트 그룹
            if (fontGroup4ChkBox.isSelected()){
            if (fontGroup4fontSelected1.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup4fontSelected1.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup4fontPath1.getText());
            }
            if (fontGroup4fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup4fontSelected2.getSelectionModel().getSelectedItem().equals("사용안함")) {
                if (fontGroup4fontSelected2.getValue().equals("한글 조합형")||fontGroup4fontSelected2.getValue().equals("유니코드 전체")){
                    totalFileSize += Files.size(Paths.get(fontGroup4fontPath2.getText()))-16L;
                }
                else {
                    String size = extractTwoCharsAroundX(fontGroup4fontPath2.getText(), 'x');
                    long sizeInt = parseSize(size);
                    totalFileSize += calculateFontSize(sizeInt, fontGroup4fontSelected2.getSelectionModel().getSelectedItem());
                }
            }
            if (fontGroup4fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup4fontSelected3.getSelectionModel().getSelectedItem().equals("사용안함")) {
                totalFileSize += getFileSize(fontGroup4fontPath3.getText());
            }}

            fontCapacity.setText(totalFileSize + "/3145727 Byte");

            if (totalFileSize>3145727){
                fontCapacity.setStyle("-fx-text-fill: red");
            }
            else {
                fontCapacity.setStyle("-fx-text-fill: white");
            }

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
            return Long.parseLong(dimensions[0]) * Long.parseLong(dimensions[1]) / 8;
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
            case "유니코드 전체":
                return sizeInt; // 전체 크기 반환
            default:
                return 0; // 알 수 없는 타입 처리
        }
    }




}