package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class SizeOfDisplayBoardController {

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;

    SizeOfDisplayBoardService sizeOfDisplayBoardService;



    @FXML
    public ChoiceBox<String> colorNum;

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
    }

    private void setInitialValues() {
        SIZE_ROW = spinnerForRow.getValue();
        SIZE_COLUMN = spinnerForColumn.getValue();
        BITS_PER_PIXEL = Integer.parseInt(String.valueOf(colorNum.getValue()).substring(0,1));
    }


    public void sendDisplaySize() throws ExecutionException, InterruptedException {
        if (IS_ASCII){
            displaySizeASC();
        }
        else {
            displaySizeHEX();
        }
        setInitialValues();
    }

    private void displaySizeASC() throws ExecutionException, InterruptedException {
        String msg = "![0040";
        if (isRS){
            msg = "!["+convertRS485AddrASCii()+"040";
        }
        msg+=String.format("%02d",spinnerForRow.getValue());
        msg+=String.format("%02d",spinnerForColumn.getValue());
        switch (howToArray.getValue()){
            case "가로형(default)":
                msg+="0";
                break;
            case "1줄 세로형":
                msg+="1";
                break;
            case "2줄 세로형":
                msg+="2";
                break;
            case "가로형 양면":
                msg+="3";
                break;
            case "1줄 세로형 양면":
                msg+="4";
                break;
            case "2줄 가로형":
                msg+="5";
                break;
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
        switch (howToArray.getValue()){
            case "가로형(default)":
                msg+=" 00";
                break;
            case "1줄 세로형":
                msg+=" 01";
                break;
            case "2줄 세로형":
                msg+=" 02";
                break;
            case "가로형 양면":
                msg+=" 03";
                break;
            case "1줄 세로형 양면":
                msg+=" 04";
                break;
            case "2줄 가로형":
                msg+=" 05";
                break;
        }
        msg+=" 00 F1 10 03";

        hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);

    }
}
