package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.BoardSettingService;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.convertRS485AddrASCii;
import static dbps.dbps.Constants.isRS;

public class BoardSettingsController {

    private static final String BOARD_SETTING_CSS = "/dbps/dbps/css/boardsetting.css";
    private static final String COMM_SETTING_FXML = "/dbps/dbps/fxmls/communicationSetting.fxml";


    @FXML
    public RadioButton settingRadio;
    @FXML
    public RadioButton readRadio;
    @FXML
    public Pane boardDisable;
    @FXML
    public AnchorPane boardAP;
    @FXML
    public ComboBox<String> debugMethod;
    @FXML
    public ComboBox<String> BH1_Func;
    @FXML
    public ComboBox<String> J4_func;
    @FXML
    public ComboBox<String> J2_baud;
    @FXML
    public ComboBox<String> J3_baud;
    @FXML
    public ComboBox<String> BH1_baud;
    public ProgressIndicator progressIndicator;
    public Label rs_address;

    private AsciiMsgTransceiver asciiMsgTransceiver;
    private ToggleGroup group = new ToggleGroup();
    private BoardSettingService boardSettingService;

    private static final String[] BAUD_RATES = {"9600", "19200", "38400", "57600", "115200", "230400", "460800", "921600"};
    private static final String[] BH1_OPTIONS = {
            "TTL/RS485", "CAN", "12C", "SPI", "8Pin Input(HEX)",
            "8Pin Input(BCD)", "8Pin Input(Number)", "8Pin Input(BIT BGSch)"
    };
    private static final String[] J4_OPTIONS = {
            "Relay Out", "CDS", "DHT22(ONLY NoLAN)", "DS1302",
            "SHT31(DB502)", "2Port Input", "Relay Out & NoRTC", "Relay 4Port"
    };

    @FXML
    public void initialize() {
        // 그룹화 설정
        settingRadio.setToggleGroup(group);
        readRadio.setToggleGroup(group);
        readRadio.setSelected(true);

        // CSS 추가
        boardAP.getStylesheets().add(Simulator.class.getResource(BOARD_SETTING_CSS).toExternalForm());

        // Toggle 변경 리스너
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                toggleDisableBoard(group.getSelectedToggle() == settingRadio)
        );

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        boardSettingService = BoardSettingService.getInstance();

        boardSettingService.setDebugMethod(debugMethod);
        boardSettingService.setBH1_baud(BH1_baud);
        boardSettingService.setBH1_Func(BH1_Func);
        boardSettingService.setJ4_func(J4_func);
        boardSettingService.setJ2_baud(J2_baud);
        boardSettingService.setJ3_baud(J3_baud);
        boardSettingService.setRs_address(rs_address);
    }

    private void toggleDisableBoard(boolean enable) {
        boardDisable.getChildren().forEach(node -> node.setDisable(!enable));
    }

    @FXML
    public void openCommunicationSetting(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource(COMM_SETTING_FXML));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("통신 설정");
        modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.png")));
        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    @FXML
    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void Transfer() throws ExecutionException, InterruptedException {
        if (group.getSelectedToggle().equals(readRadio)) {
            handleReadCommand();
        } else {
            handleSetCommand();
        }
    }

    private void handleReadCommand() throws ExecutionException, InterruptedException {
        asciiMsgTransceiver.sendMessages("![00B30!]", false, progressIndicator);
    }

    private void handleSetCommand() {
        StringBuilder msg = new StringBuilder(isRS ? "![" + convertRS485AddrASCii() + "0B2 " : "![00B2 ");

        msg.append(debugMethod.getValue().equals("disable") ? "0," : debugMethod.getValue().replaceAll("[^0-9]", "") + ",");
        msg.append(getComboBoxIndex(BH1_Func, BH1_OPTIONS)).append(",");
        msg.append(getComboBoxIndex(J4_func, J4_OPTIONS)).append(",");
        msg.append(getComboBoxIndex(J2_baud, BAUD_RATES)).append(",");
        msg.append(getComboBoxIndex(J3_baud, BAUD_RATES)).append(",");
        msg.append(getComboBoxIndex(BH1_baud, BAUD_RATES)).append("!]");

        asciiMsgTransceiver.sendMessages(msg.toString(), false, progressIndicator);
    }

    private String getComboBoxIndex(ComboBox<String> comboBox, String[] options) {
        int index = java.util.Arrays.asList(options).indexOf(comboBox.getValue());
        return index != -1 ? String.valueOf(index) : "0";
    }
}