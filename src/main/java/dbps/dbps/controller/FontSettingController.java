package dbps.dbps.controller;

import dbps.dbps.Constants;
import dbps.dbps.service.*;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;
import static dbps.dbps.Constants.hexStringToByteArray;

public class FontSettingController {
    public Label fontProgressLabel;
    public ProgressBar fontProgressBar;
    public Label fontCapacity;
    public ComboBox<ComboItem> fontGroup1fontSelected1;
    ResourceBundle bundle = ResourceManager.getInstance().getBundle();

    ConfigService configService;
    FontService fontService = FontService.getInstance();
    TCPManager tcpManager;
    UDPManager udpManager;
    ServerTCPManager serverTCPManager;
    SerialPortManager serialPortmanager;
    LogService logService;
    @FXML
    public ComboBox<ComboItem> fontGroup2fontSelected1;
    @FXML
    public TextArea fontGroup2fontPath1;
    @FXML
    public ComboBox<ComboItem> fontGroup2fontSelected2;
    @FXML
    public TextArea fontGroup2fontPath2;
    @FXML
    public ComboBox<ComboItem> fontGroup2fontSelected3;
    @FXML
    public TextArea fontGroup2fontPath3;
    @FXML
    public ComboBox<ComboItem> fontGroup3fontSelected1;
    @FXML
    public TextArea fontGroup3fontPath1;
    @FXML
    public ComboBox<ComboItem> fontGroup3fontSelected2;
    @FXML
    public TextArea fontGroup3fontPath2;
    @FXML
    public ComboBox<ComboItem> fontGroup3fontSelected3;
    @FXML
    public TextArea fontGroup3fontPath3;
    @FXML
    public TextArea fontGroup4fontPath1;
    @FXML
    public TextArea fontGroup4fontPath2;
    @FXML
    public TextArea fontGroup4fontPath3;
    @FXML
    public ComboBox<ComboItem> fontGroup4fontSelected1;
    @FXML
    public ComboBox<ComboItem> fontGroup4fontSelected2;
    @FXML
    public ComboBox<ComboItem> fontGroup4fontSelected3;
    @FXML
    ComboBox<ComboItem> fontGroup1fontSelected3;
    @FXML
    TextArea fontGroup1fontPath3;
    @FXML
    TextArea fontGroup1fontPath2;
    @FXML
    ComboBox<ComboItem> fontGroup1fontSelected2;
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
    String defaultPath = System.getProperty("user.dir") + File.separator + "Font" + File.separator;

    //초기화(하위 폰트그룹이랑 그룹화)
    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        tcpManager = TCPManager.getManager();
        udpManager = UDPManager.getUDPManager();
        serverTCPManager = ServerTCPManager.getInstance();
        serialPortmanager = SerialPortManager.getManager();
        logService = LogService.getLogService();


        if (Boolean.parseBoolean(configService.getProperty("fontGroup2selected"))) {
            fontGroup2ChkBox.setSelected(true);
            ableAllNodesInPane((Pane) fontGroup2ChkBox.getParent());
        } else {
            fontGroup2ChkBox.setSelected(false);
            disableAllNodesInPane((Pane) fontGroup2ChkBox.getParent());
        }

        if (fontGroup2ChkBox.isSelected() && Boolean.parseBoolean(configService.getProperty("fontGroup3selected"))) {
            fontGroup3ChkBox.setSelected(true);
            ableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
        } else {
            fontGroup3ChkBox.setSelected(false);
            disableAllNodesInPane((Pane) fontGroup3ChkBox.getParent());
        }

        if (fontGroup3ChkBox.isSelected() && Boolean.parseBoolean(configService.getProperty("fontGroup4selected"))) {
            fontGroup4ChkBox.setSelected(true);
            ableAllNodesInPane((Pane) fontGroup4ChkBox.getParent());
        } else {
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
            if (!fontGroup2ChkBox.isSelected()) {
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
            if (!fontGroup3ChkBox.isSelected()) {
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

        fontGroup1fontPath1.setText(configService.getProperty("fontGroup1FontPath1") != null
                ? defaultPath + configService.getProperty("fontGroup1FontPath1")
                : defaultPath);
        fontGroup1fontPath2.setText(configService.getProperty("fontGroup1FontPath2") != null
                ? defaultPath + configService.getProperty("fontGroup1FontPath2")
                : defaultPath);
        fontGroup1fontPath3.setText(configService.getProperty("fontGroup1FontPath3") != null
                ? defaultPath + configService.getProperty("fontGroup1FontPath3")
                : defaultPath);

        fontGroup2fontPath1.setText(configService.getProperty("fontGroup2FontPath1") != null
                ? defaultPath + configService.getProperty("fontGroup2FontPath1")
                : defaultPath);
        fontGroup2fontPath2.setText(configService.getProperty("fontGroup2FontPath2") != null
                ? defaultPath + configService.getProperty("fontGroup2FontPath2")
                : defaultPath);
        fontGroup2fontPath3.setText(configService.getProperty("fontGroup2FontPath3") != null
                ? defaultPath + configService.getProperty("fontGroup2FontPath3")
                : defaultPath);

        fontGroup3fontPath1.setText(configService.getProperty("fontGroup3FontPath1") != null
                ? defaultPath + configService.getProperty("fontGroup3FontPath1")
                : defaultPath);
        fontGroup3fontPath2.setText(configService.getProperty("fontGroup3FontPath2") != null
                ? defaultPath + configService.getProperty("fontGroup3FontPath2")
                : defaultPath);
        fontGroup3fontPath3.setText(configService.getProperty("fontGroup3FontPath3") != null
                ? defaultPath + configService.getProperty("fontGroup3FontPath3")
                : defaultPath);

        fontGroup4fontPath1.setText(configService.getProperty("fontGroup4FontPath1") != null
                ? defaultPath + configService.getProperty("fontGroup4FontPath1")
                : defaultPath);
        fontGroup4fontPath2.setText(configService.getProperty("fontGroup4FontPath2") != null
                ? defaultPath + configService.getProperty("fontGroup4FontPath2")
                : defaultPath);
        fontGroup4fontPath3.setText(configService.getProperty("fontGroup4FontPath3") != null
                ? defaultPath + configService.getProperty("fontGroup4FontPath3")
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
        fontGroup1fontSelected1.setValue(new ComboItem("english", bundle.getString("english")));

        fontGroup1fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup1FontType2", newValue.key());
            updateFontSize();
        });
        fontGroup1fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup1FontType3", newValue.key());
            updateFontSize();
        });
        fontGroup2fontSelected1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup2FontType1", newValue.key());
            updateFontSize();
        });
        fontGroup2fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup2FontType2", newValue.key());
            updateFontSize();
        });
        fontGroup2fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup2FontType3", newValue.key());
            updateFontSize();
        });
        fontGroup3fontSelected1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup3FontType1", newValue.key());
            updateFontSize();
        });
        fontGroup3fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup3FontType2", newValue.key());
            updateFontSize();
        });
        fontGroup3fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup3FontType3", newValue.key());
            updateFontSize();
        });
        fontGroup4fontSelected1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup4FontType1", newValue.key());
            updateFontSize();
        });
        fontGroup4fontSelected2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup4FontType2", newValue.key());
            updateFontSize();
        });
        fontGroup4fontSelected3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            configService.setProperty("fontGroup4FontType3", newValue.key());
            updateFontSize();
        });

        addItem();

        fontGroup1fontSelected2.setValue(new ComboItem(configService.getProperty("fontGroup1FontType2"), bundle.getString(configService.getProperty("fontGroup1FontType2"))));
        fontGroup1fontSelected3.setValue(new ComboItem(configService.getProperty("fontGroup1FontType3"), bundle.getString(configService.getProperty("fontGroup1FontType3"))));
        fontGroup2fontSelected1.setValue(new ComboItem(configService.getProperty("fontGroup2FontType1"), bundle.getString(configService.getProperty("fontGroup2FontType1"))));
        fontGroup2fontSelected2.setValue(new ComboItem(configService.getProperty("fontGroup2FontType2"), bundle.getString(configService.getProperty("fontGroup2FontType2"))));
        fontGroup2fontSelected3.setValue(new ComboItem(configService.getProperty("fontGroup2FontType3"), bundle.getString(configService.getProperty("fontGroup2FontType3"))));
        fontGroup3fontSelected1.setValue(new ComboItem(configService.getProperty("fontGroup3FontType1"), bundle.getString(configService.getProperty("fontGroup3FontType1"))));
        fontGroup3fontSelected2.setValue(new ComboItem(configService.getProperty("fontGroup3FontType2"), bundle.getString(configService.getProperty("fontGroup3FontType2"))));
        fontGroup3fontSelected3.setValue(new ComboItem(configService.getProperty("fontGroup3FontType3"), bundle.getString(configService.getProperty("fontGroup3FontType3"))));
        fontGroup4fontSelected1.setValue(new ComboItem(configService.getProperty("fontGroup4FontType1"), bundle.getString(configService.getProperty("fontGroup4FontType1"))));
        fontGroup4fontSelected2.setValue(new ComboItem(configService.getProperty("fontGroup4FontType2"), bundle.getString(configService.getProperty("fontGroup4FontType2"))));
        fontGroup4fontSelected3.setValue(new ComboItem(configService.getProperty("fontGroup4FontType3"), bundle.getString(configService.getProperty("fontGroup4FontType3"))));

        progressStage = new Stage();
        progressStage.initModality(Modality.APPLICATION_MODAL); // 부모 창을 블로킹
        progressStage.setTitle("폰트 전송");

        progressBar = new ProgressBar(0);
        progressBar.setStyle("-fx-accent: green;");
        progressBar.setVisible(true);
        progressBar.setPrefWidth(250);

        progressLabel = new Label("폰트 전송 준비 중...");
        progressLabel.setStyle("-fx-font-family: Gulim;");

        Button cancelButton = new Button("취소");
        Region spacer = new Region(); // 중간에 공간을 추가
        HBox buttonBox = new HBox(spacer, cancelButton);
        HBox.setHgrow(spacer, Priority.ALWAYS); // spacer가 가능한 공간을 채우도록 설정
        buttonBox.setSpacing(10); // 버튼과 spacer 사이 여백 설정

        cancelButton.setOnAction(e -> {
            if (fontSendTask != null) {
                fontSendTask.cancel();
                progressBar.setProgress(0);
                closeWindowAfterDelay(progressStage);
            }
        });


        VBox vbox = new VBox(15, progressLabel, progressBar, buttonBox);

        Scene progressScene = new Scene(vbox, 300, 150);
        progressStage.setScene(progressScene);

        progressStage.setOnCloseRequest(e -> {
            if (fontSendTask != null) {
                fontSendTask.cancel();
                progressBar.setProgress(0);
                closeWindowAfterDelay(progressStage);
            }
        });

        vbox.setStyle("-fx-padding: 20px; ");
        cancelButton.setStyle(
                "-fx-border-radius: 10;" +
                        "-fx-padding: 5 10 5 10;" +
                        "-fx-background-radius: 10;"
        );
        cancelButton.setOnMousePressed(e -> {
            cancelButton.setStyle(
                    "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;"
            );
        });

        cancelButton.setOnMouseEntered(e -> {
            cancelButton.setStyle(
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: hand;" // 🔹 손가락 커서로 변경
            );
        });

// 🔹 마우스를 벗어나면 원래 스타일로 복구
        cancelButton.setOnMouseExited(e -> {
            cancelButton.setStyle(
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;" +
                            "-fx-cursor: default;" // 기본 커서로 변경
            );
        });

// 버튼에서 손을 뗄 때 원래 스타일로 복구
        cancelButton.setOnMouseReleased(e -> {
            cancelButton.setStyle(
                            "-fx-border-radius: 10;" +
                            "-fx-padding: 5 10 5 10;" +
                            "-fx-background-radius: 10;"
            );
        });
        progressLabel.setStyle(
                        "-fx-padding: 5; " +
                        "-fx-background-radius: 5; " +
                        "-fx-border-radius: 5;"
        );

        Platform.runLater(() -> {
            Stage parentStage = (Stage) fontSettingAnchorPane.getScene().getWindow();

            double parentX = parentStage.getX();
            double parentY = parentStage.getY();
            double parentWidth = parentStage.getWidth();
            double parentHeight = parentStage.getHeight();

            // 진행 창 위치 설정 (세로는 부모와 동일, 가로는 절반 위치)
            progressStage.setX(parentX + parentWidth / 2 - 150); // 300px 창 기준 중앙 정렬
            progressStage.setY(parentY + (parentHeight / 2) - 75); // 150px 창 기준 중앙 정렬
        });

        fontSettingAnchorPane.setOnKeyPressed(new Constants.EscapeKeyEventHandler());
    }

    private void addItem() {
        fontGroup1fontSelected1.getItems().addAll(
                new ComboItem("english", bundle.getString("english")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup1fontSelected2.getItems().addAll(
                new ComboItem("combination", bundle.getString("combination")),
                new ComboItem("UNI-KR", bundle.getString("UNI-KR")),
                new ComboItem("UNI-JP", bundle.getString("UNI-JP")),
                new ComboItem("UNI-CN", bundle.getString("UNI-CN")),
                new ComboItem("UNI-all", bundle.getString("UNI-all")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup1fontSelected3.getItems().addAll(
                new ComboItem("userFont", bundle.getString("userFont")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup2fontSelected1.getItems().addAll(
                new ComboItem("english", bundle.getString("english")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup2fontSelected2.getItems().addAll(
                new ComboItem("combination", bundle.getString("combination")),
                new ComboItem("UNI-KR", bundle.getString("UNI-KR")),
                new ComboItem("UNI-JP", bundle.getString("UNI-JP")),
                new ComboItem("UNI-CN", bundle.getString("UNI-CN")),
                new ComboItem("UNI-all", bundle.getString("UNI-all")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup2fontSelected3.getItems().addAll(
                new ComboItem("userFont", bundle.getString("userFont")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup3fontSelected1.getItems().addAll(
                new ComboItem("english", bundle.getString("english")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup3fontSelected2.getItems().addAll(
                new ComboItem("combination", bundle.getString("combination")),
                new ComboItem("UNI-KR", bundle.getString("UNI-KR")),
                new ComboItem("UNI-JP", bundle.getString("UNI-JP")),
                new ComboItem("UNI-CN", bundle.getString("UNI-CN")),
                new ComboItem("UNI-all", bundle.getString("UNI-all")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup3fontSelected3.getItems().addAll(
                new ComboItem("userFont", bundle.getString("userFont")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup4fontSelected1.getItems().addAll(
                new ComboItem("english", bundle.getString("english")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup4fontSelected2.getItems().addAll(
                new ComboItem("combination", bundle.getString("combination")),
                new ComboItem("UNI-KR", bundle.getString("UNI-KR")),
                new ComboItem("UNI-JP", bundle.getString("UNI-JP")),
                new ComboItem("UNI-CN", bundle.getString("UNI-CN")),
                new ComboItem("UNI-all", bundle.getString("UNI-all")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
        );

        fontGroup4fontSelected3.getItems().addAll(
                new ComboItem("userFont", bundle.getString("userFont")),
                new ComboItem("notUsed", bundle.getString("notUsed"))
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
        textArea.positionCaret(textArea.getText().length());
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

        if (!selectedFont.exists()) {
            logService.errorLog("해당 파일을 찾을 수 없습니다.");
        }
        // 선택된 폰트 경로를 TextArea에 설정
        else {
            fontPath.setText(selectedFont.getAbsolutePath());
            String target = (String) clickedBtn.getUserData();
            configService.setProperty(target, selectedFont.getAbsolutePath());
        }

        Platform.runLater(() -> moveCaretToEnd(fontPath));
        updateFontSize();
    }

    //체크박스 클릭시 폰트설정 비활성화/활성화
    private void disableAllNodesInPane(Pane pane) {
        for (Node node : pane.getChildren()) {
            node.setDisable(true);

            if (node instanceof Pane) {
                disableAllNodesInPane((Pane) node);
            }
        }
    }

    private void ableAllNodesInPane(Pane pane) {
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
        if (chkFont(fontGroup1fontPath1.getText(), "영어")) {
            logService.warningLog(bundle.getString("fontGroup1") + bundle.getString("fontMismatch"));
            return;
        }
        fontGroup1Path[0] = fontGroup1fontPath1.getText();
        configService.setProperty("fontGroup1FontPath1", Paths.get(fontGroup1Path[0]).getFileName().toString());
        fontType[0] = fontGroup1fontSelected1.getValue().key();

        if (chkFont(fontGroup1fontPath2.getText(), fontGroup1fontSelected2.getValue().displayText())) {
            logService.warningLog(bundle.getString("fontGroup1") + bundle.getString("fontMismatch"));
            return;
        }
        if (!fontGroup1fontSelected2.getValue().displayText().equals(bundle.getString("notUsed"))) {
            fontGroup1Path[1] = fontGroup1fontPath2.getText();
            fontType[1] = fontGroup1fontSelected2.getValue().key();
            configService.setProperty("fontGroup1FontPath2", Paths.get(fontGroup1Path[1]).getFileName().toString());
        }

        if (chkFont(fontGroup1fontPath3.getText(), fontGroup1fontSelected3.getValue().displayText())) {
            logService.warningLog(bundle.getString("fontGroup1") + bundle.getString("fontMismatch"));
            return;
        }
        if (!fontGroup1fontSelected3.getValue().displayText().equals(bundle.getString("notUsed"))) {
            fontGroup1Path[2] = fontGroup1fontPath3.getText();
            fontType[2] = fontGroup1fontSelected3.getValue().key();
            configService.setProperty("fontGroup1FontPath3", Paths.get(fontGroup1Path[2]).getFileName().toString());
        }

// 두번째 그룹
        if (fontGroup2ChkBox.isSelected()) {
            fontGroup2Path = new String[3];

            if (chkFont(fontGroup2fontPath1.getText(), fontGroup2fontSelected1.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup2") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup2fontSelected1.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup2Path[0] = fontGroup2fontPath1.getText();
                fontType[3] = fontGroup2fontSelected1.getValue().key();
                configService.setProperty("fontGroup2FontPath1", Paths.get(fontGroup2Path[0]).getFileName().toString());
            }

            if (chkFont(fontGroup2fontPath2.getText(), fontGroup2fontSelected2.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup2") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup2fontSelected2.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup2Path[1] = fontGroup2fontPath2.getText();
                fontType[4] = fontGroup2fontSelected2.getValue().key();
                configService.setProperty("fontGroup2FontPath2", Paths.get(fontGroup2Path[1]).getFileName().toString());
            }

            if (chkFont(fontGroup2fontPath3.getText(), fontGroup2fontSelected3.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup2") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup2fontSelected3.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup2Path[2] = fontGroup2fontPath3.getText();
                fontType[5] = fontGroup2fontSelected3.getValue().key();
                configService.setProperty("fontGroup2FontPath3", Paths.get(fontGroup2Path[2]).getFileName().toString());
            }
        }

// 세번째 그룹
        if (fontGroup3ChkBox.isSelected()) {
            fontGroup3Path = new String[3];

            if (chkFont(fontGroup3fontPath1.getText(), fontGroup3fontSelected1.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup3") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup3fontSelected1.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup3Path[0] = fontGroup3fontPath1.getText();
                fontType[6] = fontGroup3fontSelected1.getValue().key();
                configService.setProperty("fontGroup3FontPath1", Paths.get(fontGroup3Path[0]).getFileName().toString());
            }

            if (chkFont(fontGroup3fontPath2.getText(), fontGroup3fontSelected2.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup3") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup3fontSelected2.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup3Path[1] = fontGroup3fontPath2.getText();
                fontType[7] = fontGroup3fontSelected2.getValue().key();
                configService.setProperty("fontGroup3FontPath2", Paths.get(fontGroup3Path[1]).getFileName().toString());
            }

            if (chkFont(fontGroup3fontPath3.getText(), fontGroup3fontSelected3.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup3") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup3fontSelected3.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup3Path[2] = fontGroup3fontPath3.getText();
                fontType[8] = fontGroup3fontSelected3.getValue().key();
                configService.setProperty("fontGroup3FontPath3", Paths.get(fontGroup3Path[2]).getFileName().toString());
            }
        }

// 네번째 그룹
        if (fontGroup4ChkBox.isSelected()) {
            fontGroup4Path = new String[3];

            if (chkFont(fontGroup4fontPath1.getText(), fontGroup4fontSelected1.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup4") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup4fontSelected1.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup4Path[0] = fontGroup4fontPath1.getText();
                fontType[9] = fontGroup4fontSelected1.getValue().key();
                configService.setProperty("fontGroup4FontPath1", Paths.get(fontGroup4Path[0]).getFileName().toString());
            }

            if (chkFont(fontGroup4fontPath2.getText(), fontGroup4fontSelected2.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup4") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup4fontSelected2.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup4Path[1] = fontGroup4fontPath2.getText();
                fontType[10] = fontGroup4fontSelected2.getValue().key();
                configService.setProperty("fontGroup4FontPath2", Paths.get(fontGroup4Path[1]).getFileName().toString());
            }

            if (chkFont(fontGroup4fontPath3.getText(), fontGroup4fontSelected3.getValue().displayText())) {
                logService.warningLog(bundle.getString("fontGroup4") + bundle.getString("fontMismatch"));
                return;
            }
            if (!fontGroup4fontSelected3.getValue().displayText().equals(bundle.getString("notUsed"))) {
                fontGroup4Path[2] = fontGroup4fontPath3.getText();
                fontType[11] = fontGroup4fontSelected3.getValue().key();
                configService.setProperty("fontGroup4FontPath3", Paths.get(fontGroup4Path[2]).getFileName().toString());
            }
        }

        // ✅ 진행 상태를 표시할 새 창 만들기
        Platform.runLater(() -> {
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
            closeWindowAfterDelay(progressStage); // 1초 후 창 닫기
        });

        new Thread(fontSendTask).start();
    }

    private boolean chkFont(String fileName, String fontType) {
        if (fontType == null || fontType.equals("사용안함")) return false;

        if (!fileName.contains("fnt")) {
            return true;
        }


        if (fontType.equals(bundle.getString("english"))) {
            return !fileName.contains("ENG");
        }
        if (fontType.equals(bundle.getString("userFont"))) {
            return !fileName.contains("USER");
        }
        if (fontType.equals(bundle.getString("CombinationType"))) {
            if ((fileName.toLowerCase()).contains("uni")) {
                return true;
            }
            return !fileName.contains("KOR");
        }
        if (fontType.equals(bundle.getString("UNI-all")) || fontType.equals(bundle.getString("UNI-KR")) || fontType.equals(bundle.getString("UNI-JP")) || fontType.equals(bundle.getString("UNI-CN"))) {
            return !(fileName.toLowerCase()).contains("uni");
        }

        return false;
    }

    // ✅ 일정 시간이 지나면 진행 상태 창 닫기
    private void closeWindowAfterDelay(Stage stage) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String msg = "10 02 00 00 02 45 01 10 03";
        if (isRS) {
            msg = "10 02 " + RS485_ADDR_NUM + " 00 02 45 01 10 03";
        }
        try {
            hexMsgTransceiver.sendByteMessagesNoLog(hexStringToByteArray(msg));
        }finally {
            new Thread(() -> {
                try {
                    Thread.sleep(300);
                    Platform.runLater(stage::close);
                } catch (InterruptedException ignored) {
                }
            }).start();
            end();
        }
    }

    public void close(MouseEvent mouseEvent) {
        if (fontSendTask != null) {
            fontSendTask.cancel();
        }
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
        end();
    }

    private void end() {
        tcpManager.disconnectNoLog();
        serverTCPManager.disconnectNoLog();
        udpManager.disconnectNoLog();
        serialPortmanager.closePortNoLog(OPEN_PORT_NAME);
    }

    private void updateFontSize() {
        long totalFileSize = 0; // 총 파일 크기

        try {
            // 1번 폰트 그룹
            totalFileSize += getFileSize(fontGroup1fontPath1.getText());
            if (fontGroup1fontSelected2.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup1fontSelected2.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                if (fontGroup1fontSelected2.getValue().displayText().equals(bundle.getString("CombinationType")) || fontGroup1fontSelected2.getValue().displayText().equals("UNI-ALL")) {
                    totalFileSize += Files.size(Paths.get(fontGroup1fontPath2.getText())) - 16L;
                } else {
                    String size = extractTwoCharsAroundX(fontGroup1fontPath2.getText(), 'x');
                    long sizeInt = parseSize(size);
                    totalFileSize += calculateFontSize(sizeInt, fontGroup1fontSelected2.getSelectionModel().getSelectedItem().displayText());
                }
            }
            if (fontGroup1fontSelected3.getSelectionModel().getSelectedItem() != null &&
                    !fontGroup1fontSelected3.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                totalFileSize += getFileSize(fontGroup1fontPath3.getText());
            }

            // 2번 폰트 그룹
            if (fontGroup2ChkBox.isSelected()) {
                if (fontGroup2fontSelected1.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup2fontSelected1.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    totalFileSize += getFileSize(fontGroup2fontPath1.getText());
                }
                if (fontGroup2fontSelected2.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup2fontSelected2.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    if (fontGroup2fontSelected2.getValue().displayText().equals(bundle.getString("CombinationType")) || fontGroup2fontSelected2.getValue().displayText().equals("UNI-ALL")) {
                        totalFileSize += Files.size(Paths.get(fontGroup2fontPath2.getText())) - 16L;
                    } else {
                        String size = extractTwoCharsAroundX(fontGroup2fontPath2.getText(), 'x');
                        long sizeInt = parseSize(size);
                        totalFileSize += calculateFontSize(sizeInt, fontGroup2fontSelected2.getSelectionModel().getSelectedItem().displayText());
                    }
                }
                if (fontGroup2fontSelected3.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup2fontSelected3.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    totalFileSize += getFileSize(fontGroup2fontPath3.getText());
                }
            }

            // 3번 폰트 그룹
            if (fontGroup3ChkBox.isSelected()) {
                if (fontGroup3fontSelected1.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup3fontSelected1.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    totalFileSize += getFileSize(fontGroup3fontPath1.getText());
                }
                if (fontGroup3fontSelected2.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup3fontSelected2.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    if (fontGroup3fontSelected2.getValue().displayText().equals(bundle.getString("CombinationType")) || fontGroup3fontSelected2.getValue().displayText().equals("UNI-ALL")) {
                        totalFileSize += Files.size(Paths.get(fontGroup3fontPath2.getText())) - 16L;
                    } else {
                        String size = extractTwoCharsAroundX(fontGroup3fontPath2.getText(), 'x');
                        long sizeInt = parseSize(size);
                        totalFileSize += calculateFontSize(sizeInt, fontGroup3fontSelected2.getSelectionModel().getSelectedItem().displayText());
                    }
                }
                if (fontGroup3fontSelected3.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup3fontSelected3.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    totalFileSize += getFileSize(fontGroup3fontPath3.getText());
                }
            }

            // 4번 폰트 그룹
            if (fontGroup4ChkBox.isSelected()) {
                if (fontGroup4fontSelected1.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup4fontSelected1.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    totalFileSize += getFileSize(fontGroup4fontPath1.getText());
                }
                if (fontGroup4fontSelected2.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup4fontSelected2.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    if (fontGroup4fontSelected2.getValue().displayText().equals(bundle.getString("CombinationType")) || fontGroup4fontSelected2.getValue().displayText().equals("UNI-ALL")) {
                        totalFileSize += Files.size(Paths.get(fontGroup4fontPath2.getText())) - 16L;
                    } else {
                        String size = extractTwoCharsAroundX(fontGroup4fontPath2.getText(), 'x');
                        long sizeInt = parseSize(size);
                        totalFileSize += calculateFontSize(sizeInt, fontGroup4fontSelected2.getSelectionModel().getSelectedItem().displayText());
                    }
                }
                if (fontGroup4fontSelected3.getSelectionModel().getSelectedItem() != null &&
                        !fontGroup4fontSelected3.getSelectionModel().getSelectedItem().displayText().equals(bundle.getString("notUsed"))) {
                    totalFileSize += getFileSize(fontGroup4fontPath3.getText());
                }
            }

            fontCapacity.setText("(" + totalFileSize + "/3145727) Byte");

            if (totalFileSize > 3145727) {
                fontCapacity.setStyle("-fx-text-fill: red");
            } else {
                fontCapacity.setStyle("-fx-text-fill: black");
            }

        } catch (Exception e) {
            System.err.println("Error while updating font size: " + e.getMessage());
        }
    }


    private long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.length() - 16;
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
        if (fontType.equals(bundle.getString("UNI-KR"))) {
            return sizeInt * 11172L;
        } else if (fontType.equals(bundle.getString("UNI-JP"))) {
            return sizeInt * 192L;
        } else if (fontType.equals(bundle.getString("UNI-CN"))) {
            return sizeInt * 20992L;
        } else if (fontType.equals(bundle.getString("UNI-all"))) {
            return sizeInt * 65501L;
        } else {
            return 0;
        }
    }

    public void fontName(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/fontName.fxml", "폰트 이름 설정", mouseEvent);
    }

    public void sendName() throws Exception {
        if (IS_ASCII) {
            byte[] sendMsg = new byte[216 + 10];
            sendMsg[0] = "!".getBytes(Charset.forName("MS949"))[0];
            sendMsg[1] = "[".getBytes(Charset.forName("MS949"))[0];
            sendMsg[2] = "0".getBytes(Charset.forName("MS949"))[0];
            if (isRS) {
                sendMsg[2] = (convertRS485AddrASCii().getBytes("MS949"))[0];
            }
            sendMsg[3] = "0".getBytes(Charset.forName("MS949"))[0];
            sendMsg[4] = "9".getBytes(Charset.forName("MS949"))[0];
            sendMsg[5] = "5".getBytes(Charset.forName("MS949"))[0];
            sendMsg[6] = " ".getBytes(Charset.forName("MS949"))[0];
            sendMsg[7] = "2".getBytes(Charset.forName("MS949"))[0];
            sendMsg[224] = "!".getBytes(Charset.forName("MS949"))[0];
            sendMsg[225] = "]".getBytes(Charset.forName("MS949"))[0];
            int idx = 8;
            byte[] tmp2 = new byte[36];
            byte[] tmp1 = configService.getProperty("fontGroup1FontPath1").getBytes("MS949");
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup1FontPath2").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup1FontPath3").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup2FontPath1").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup2FontPath2").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup2FontPath3").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);

            asciiMsgTransceiver.sendMessages(new String(sendMsg, "MS949"), false, null);
        } else {
            byte[] sendMsg = new byte[216 + 10];
            sendMsg[0] = (byte) 0x10;
            sendMsg[1] = (byte) 0x02;
            sendMsg[2] = (byte) 0x00;
            if (isRS) {
                sendMsg[2] = (byte) RS485_ADDR_NUM;
            }
            sendMsg[3] = (byte) 0x00;
            sendMsg[4] = (byte) 0xDB;
            sendMsg[5] = (byte) 0x48;
            sendMsg[6] = (byte) 0x00;
            sendMsg[7] = (byte) 0x32;
            sendMsg[224] = (byte) 0x10;
            sendMsg[225] = (byte) 0x03;
            int idx = 8;
            byte[] tmp2 = new byte[36];
            byte[] tmp1 = configService.getProperty("fontGroup1FontPath1").getBytes("MS949");
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup1FontPath2").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup1FontPath3").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup2FontPath1").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup2FontPath2").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);
            idx += 36;
            tmp1 = configService.getProperty("fontGroup2FontPath3").getBytes("MS949");
            tmp2 = new byte[36];
            System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
            System.arraycopy(tmp2, 0, sendMsg, idx, 36);

            hexMsgTransceiver.sendByteMessages(sendMsg, null);
        }
    }


}