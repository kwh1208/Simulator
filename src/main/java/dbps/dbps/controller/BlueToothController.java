package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.connectManager.BTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.concurrent.ExecutionException;

public class BlueToothController {

    public TextField ble_id;
    public TextField ble_password;
    public AnchorPane bluetoothAP;
    public ProgressIndicator progressIndicator;
    BTManager btManager;
    @FXML
    public void initialize(){
        btManager = BTManager.getInstance(progressIndicator);

        bluetoothAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/bluetooth.css").toExternalForm());
    }
    //블루투스 검색
    public void search() {
        //++SET++![BT SEARCHING DIBD!]
        btManager.search();
    }

    //블루투스 이름 및 비밀번호 설정
    public void set( ){
        //++SET++![BT SETT  31  name  password!]
        btManager.set(ble_id.getText(), ble_password.getText());
    }

    //블루투스 통신 시작
    public void begin( ) throws ExecutionException, InterruptedException {
        //++SET++![BT password             BEGIN!]
        btManager.begin(ble_password.getText());
    }

    //블루투스 통신 종료
    public void end( ) {
        //++SET++![BT password             END!]
        btManager.end(ble_password.getText());
    }

    //창종료
    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}