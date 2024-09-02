package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ASCiiMsgService;
import dbps.dbps.service.HexMsgService;
import dbps.dbps.service.LogService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;

import static dbps.dbps.Constants.isAscii;

public class SizeOfDisplayBoardController {

    private LogService logService;

    ASCiiMsgService asciiMsgService = ASCiiMsgService.getInstance();

    HexMsgService hexMsgService = HexMsgService.getInstance();

    @FXML
    public ChoiceBox<String> colorNum;

    @FXML
    public CheckBox arrayChk;

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
        logService = LogService.getLogService();
        dpPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/sizeOfDisplayBoard.css").toExternalForm());

        SpinnerValueFactory<Integer> valueFactoryForRow = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 6);
        SpinnerValueFactory<Integer> valueFactoryForColumn = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 2);

        spinnerForRow.setValueFactory(valueFactoryForRow);
        spinnerForColumn.setValueFactory(valueFactoryForColumn);

        spinnerForColumn.setEditable(true);
        spinnerForRow.setEditable(true);


        arrayChk.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                howToArray.setDisable(false);
            }else{
                howToArray.setDisable(true);
            }
        });
    }


    public void sendDisplaySize() {
        if (isAscii){
            displaySizeASC();
        }
        else {
            displaySizeHEX();
        }
    }

    private void displaySizeASC() {
        String msg = "![0040";

        msg+=String.format("%02d",spinnerForColumn.getValue());
        msg+=String.format("%02d",spinnerForRow.getValue());
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
        String receiveMsg = asciiMsgService.sendMessages(msg);

        if (!receiveMsg.equals(msg)){
            String column = receiveMsg.substring(5, 7);
            String row = receiveMsg.substring(7, 9);

            logService.warningLog("디스플레이 사이즈 설정 실패. 가능한 디스플레이 사이즈는 "+column+"단, "+row+"열 입니다.");
        }
    }

    private void displaySizeHEX() {
        String msg = "10 02 00 00 05 40";

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
        msg += String.format(" %02d",spinnerForColumn.getValue());
        msg += String.format(" %02d",spinnerForRow.getValue());
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
        msg+=" 10 03";

        String receiveMsg = hexMsgService.sendMessages(msg);

        String[] splitMsg = receiveMsg.split(" ");

        if (!splitMsg[6].equals("00")){
            String columnHex = splitMsg[7];
            String rowHex = splitMsg[8];

            int column = Integer.parseInt(columnHex, 16);
            int row = Integer.parseInt(rowHex, 16);

            logService.warningLog("디스플레이 사이즈 설정 실패. 가능한 디스플레이 사이즈는 "+column+"단, "+row+"열 입니다.");
        }
    }
}
