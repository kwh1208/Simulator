package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class SizeOfDisplayBoardController {

    public ChoiceBox<String> displayBright;
    public ChoiceBox<String> colorNum;
    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;

    SizeOfDisplayBoardService sizeOfDisplayBoardService;

    ConfigService configService;

    HexMsgService hexMsgService;
    ResourceBundle bundle;

    @FXML
    public ChoiceBox<String> howToArray;

    @FXML
    public Pane dpPane;

    @FXML
    private Spinner<Integer> spinnerForRow;

    @FXML
    private Spinner<Integer> spinnerForColumn;


    @FXML
    public void initialize(){
        configService = ConfigService.getInstance();
        hexMsgService = HexMsgService.getInstance();
        bundle = ResourceManager.getInstance().getBundle();

        dpPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/sizeOfDisplayBoard.css").toExternalForm());

        SpinnerValueFactory<Integer> valueFactoryForRow = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, SIZE_ROW);
        SpinnerValueFactory<Integer> valueFactoryForColumn = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, SIZE_COLUMN);

        spinnerForRow.setValueFactory(valueFactoryForRow);
        spinnerForColumn.setValueFactory(valueFactoryForColumn);

        spinnerForColumn.setEditable(true);
        spinnerForRow.setEditable(true);

        spinnerForRow.valueProperty().addListener((obs, oldValue, newValue) -> {
            SIZE_ROW = newValue;
        });

        spinnerForColumn.valueProperty().addListener((obs, oldValue, newValue) -> {
            SIZE_COLUMN = newValue;
        });

        setInitialValues();

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        sizeOfDisplayBoardService = SizeOfDisplayBoardService.getInstance();
        sizeOfDisplayBoardService.setHowToArray(howToArray);
        sizeOfDisplayBoardService.setSpinnerForRow(spinnerForRow);
        sizeOfDisplayBoardService.setSpinnerForColumn(spinnerForColumn);

        howToArray.getItems().addAll(
                bundle.getString("horizontalDefault"),
                bundle.getString("singleVertical"),
                bundle.getString("doubleVertical"),
                bundle.getString("horizontalTwin"),
                bundle.getString("singleVerticalTwin"),
                bundle.getString("doubleHorizontal"));
        howToArray.setValue(bundle.getString("horizontalDefault"));
    }

    private void setInitialValues() {
        SIZE_ROW = spinnerForRow.getValue();
        SIZE_COLUMN = spinnerForColumn.getValue();
        BITS_PER_PIXEL = Integer.parseInt(String.valueOf(colorNum.getValue()).substring(0,1));
        configService.setProperty("displayRowSize", String.valueOf(SIZE_ROW));
        configService.setProperty("displayColumnSize", String.valueOf(SIZE_COLUMN));
    }


    public void sendDisplaySize() {
        if (IS_ASCII){
            displaySizeASC();
        }
        else {
            displaySizeHEX();
        }
        setInitialValues();

        hexMsgService.changeXY(SIZE_COLUMN,SIZE_ROW);
    }

    private void displaySizeASC() {
        String msg = "![0040";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"040";
        }
        msg+=String.format("%02d",spinnerForRow.getValue());
        msg+=String.format("%02d",spinnerForColumn.getValue());
        if (howToArray.getValue().equals(bundle.getString("horizontalDefault"))) {
            msg += "0";
        } else if (howToArray.getValue().equals(bundle.getString("singleVertical"))) {
            msg += "1";
        } else if (howToArray.getValue().equals(bundle.getString("doubleVertical"))) {
            msg += "2";
        } else if (howToArray.getValue().equals(bundle.getString("horizontalTwin"))) {
            msg += "3";
        } else if (howToArray.getValue().equals(bundle.getString("singleVerticalTwin"))) {
            msg += "4";
        } else if (howToArray.getValue().equals(bundle.getString("doubleHorizontal"))) {
            msg += "5";
        }



        msg+="!]";
        String finalMsg = msg;
        CompletableFuture.supplyAsync(() -> asciiMsgTransceiver.sendMessages(finalMsg, false, commonProgressIndicator)).join();

    }

    private void displaySizeHEX() {
        String msg = "10 02 00 00 07 40";
        if (isRS){
            msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 07 40";

        }
        switch (String.valueOf(colorNum.getValue()).charAt(0)){
            case 50:
                msg+=" 02";
                break;
            case 51:
                msg+=" 03";
                break;
            case 56:
                msg+=" 08";
                break;
        }

        msg += " "+Integer.toHexString(spinnerForRow.getValue());
        msg += " "+Integer.toHexString(spinnerForColumn.getValue());
        if (howToArray.getValue().equals(bundle.getString("horizontalDefault"))) {
            msg += " 00";
        } else if (howToArray.getValue().equals(bundle.getString("singleVertical"))) {
            msg += " 01";
        } else if (howToArray.getValue().equals(bundle.getString("doubleVertical"))) {
            msg += " 02";
        } else if (howToArray.getValue().equals(bundle.getString("horizontalTwin"))) {
            msg += " 03";
        } else if (howToArray.getValue().equals(bundle.getString("singleVerticalTwin"))) {
            msg += " 04";
        } else if (howToArray.getValue().equals(bundle.getString("doubleHorizontal"))) {
            msg += " 05";
        }
        msg+=" 00 F1 10 03";

        hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
    }

}
