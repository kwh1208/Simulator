package dbps.dbps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import dbps.dbps.service.connectManager.MQTTManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dbps.dbps.Constants.*;
import static dbps.dbps.Constants.SIZE_COLUMN;

public class MqttSettingController {

    public AnchorPane mqttSettingAP;
    public ChoiceBox<String> protocolFormat;
    public TextField CPUField;
    public TextField timeField;
    public TextField moduleField;
    public TextField LEDField;
    public TextField fontField;
    public TextField firmwareField;
    public CheckBox returnField;
    public ChoiceBox<String> displayBright;
    public ChoiceBox<String> pageMsgCnt;
    public ChoiceBox<String> msgInitialize;
    ResourceBundle bundle;
    MainService mainService;
    ConfigService configService;

    @FXML
    public ChoiceBox<String> howToArray;

    @FXML
    private Spinner<Integer> spinnerForRow;

    @FXML
    private Spinner<Integer> spinnerForColumn;

    MQTTManager mqttManager;
    SizeOfDisplayBoardService sizeOfDisplayBoardService;

    @FXML
    public void initialize() {
        mqttSettingAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/mqttSetting.css").toExternalForm());
        mqttManager=MQTTManager.getInstance();

        bundle= ResourceManager.getInstance().getBundle();
        configService = ConfigService.getInstance();
        mainService = MainService.getInstance();

        protocolFormat.getItems().addAll(
                bundle.getString("ASCiiProtocol"),
                bundle.getString("HexProtocol"),
                bundle.getString("mqtt")
        );

        if (IS_MQTT){
            protocolFormat.setValue(bundle.getString("mqtt"));
        }
        else if (IS_ASCII){
            protocolFormat.setValue(bundle.getString("ASCiiProtocol"));
        } else {
            protocolFormat.setValue(bundle.getString("HexProtocol"));
        }

        //드롭다운 감지해서 탭 변경
        protocolFormat.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(bundle.getString("mqtt"))){
                mainService.showMQTTTab();
                IS_MQTT=true;
            } else if (newValue.equals(bundle.getString("ASCiiProtocol"))) {
                mainService.showASCiiMsgTab();
                IS_ASCII = true;
                IS_MQTT=false;
                configService.setProperty("IS_ASCII", "true");
            } else if (newValue.equals(bundle.getString("HexProtocol"))) {
                mainService.showHEXMsgTab();
                IS_ASCII = false;
                IS_MQTT=false;
                configService.setProperty("IS_ASCII", "false");
            }
            mainService.changeSetTab();
        });

        SpinnerValueFactory<Integer> valueFactoryForRow = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, SIZE_ROW);
        SpinnerValueFactory<Integer> valueFactoryForColumn = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, SIZE_COLUMN);

        spinnerForRow.setValueFactory(valueFactoryForRow);
        spinnerForColumn.setValueFactory(valueFactoryForColumn);

        howToArray.valueProperty().addListener((observable, oldValue, newValue) -> {});

        spinnerForColumn.setEditable(true);
        spinnerForRow.setEditable(true);
        mqttManager = MQTTManager.getInstance();

        spinnerForRow.valueProperty().addListener((obs, oldValue, newValue) -> {
            SIZE_ROW = newValue;
        });

        spinnerForColumn.valueProperty().addListener((obs, oldValue, newValue) -> {
            SIZE_COLUMN = newValue;
        });

        setInitialValues();

        sizeOfDisplayBoardService = SizeOfDisplayBoardService.getInstance();
        sizeOfDisplayBoardService.setHowToArray(howToArray);
        sizeOfDisplayBoardService.setSpinnerForRow(spinnerForRow);
        sizeOfDisplayBoardService.setSpinnerForColumn(spinnerForColumn);
    }

    private void setInitialValues() {
        SIZE_ROW = spinnerForRow.getValue();
        SIZE_COLUMN = spinnerForColumn.getValue();
    }


    public void getInfo() throws JsonProcessingException {
        mqttManager.publishGet(Map.of("2.RTE058.2.4", 1));
    }


    public void synchronizeTime() throws JsonProcessingException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                (LocalDateTime.now().getDayOfWeek().getValue() % 7) +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

        // MOID 데이터 생성
        Map<String, Object> moidMap = new HashMap<>();
        moidMap.put("2.RTE058.4.1", timestamp);

        // MQTT 메시지 발행
        mqttManager.publishSet(moidMap);
    }

    public void sendDisplayOn() throws JsonProcessingException {
        mqttManager.publishSet(new HashMap<>(Map.of("2.RTE058.4.4", 1)));
    }


    public void sendDisplayOff() throws JsonProcessingException {
        mqttManager.publishSet(new HashMap<>(Map.of("2.RTE058.4.4", 0)));
    }

    public void sendDisplaySize() throws JsonProcessingException {
        List<Integer> moid = new ArrayList<>();
        moid.add(spinnerForColumn.getValue());
        moid.add(spinnerForRow.getValue());
        if ((howToArray.getValue()).contains("양면")){
            moid.add(1);
        } else {
            moid.add(0);
        }
        //가로, 세로, 2줄가로, 2줄 세로
        if ((howToArray.getValue()).contains("2줄 가로")){
            moid.add(2);
        } else if((howToArray.getValue()).contains("2줄 세로")){
            moid.add(3);
        } else if ((howToArray.getValue()).contains("세로")) {
            moid.add(1);
        } else{
            moid.add(0);
        }

        mqttManager.publishSet(new HashMap<>(Map.of("2.RTE058.4.3", moid)));
    }

    public void sendDisplayBright() throws JsonProcessingException {
        int bright = switch (displayBright.getValue()){
            case "100%(기본)"-> 99;
            case "75%"-> 75;
            case "50%"->50;
            case "25%"->25;
            case "5%"->5;
            default -> 0;
        };
        mqttManager.publishSet(new HashMap<>(Map.of("2.RTE058.4.2", bright)));
    }

    public void sendMsgInitialize() throws JsonProcessingException {
        mqttManager.publishSet(new HashMap<>(Map.of("2.RTE058.4.6", new ArrayList<>(List.of(99, 1)))));
    }

    public void sendPageCnt() throws JsonProcessingException {
        mqttManager.publishSet(new HashMap<>(Map.of("2.RTE058.4.6", new ArrayList<>(List.of(Integer.parseInt(pageMsgCnt.getValue().replaceAll("[^0-9]", "")), 0)))));
    }

    public void openMqtt(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/mqtt.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("Mqtt 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }
}
