package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.connectManager.BTManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class blueToothController {

    public TextField ble_id;
    public TextField ble_password;
    public AnchorPane bluetoothAP;
    BTManager btManager;
    @FXML
    public void initialize(){
        btManager = BTManager.getInstance();

        bluetoothAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/bluetooth.css").toExternalForm());
    }

    public void search(MouseEvent mouseEvent) {
        btManager.search();
    }

    public void set(MouseEvent mouseEvent){
        //++SET++![BT SETT  31  name  password!]
        btManager.set(ble_id.getText(), ble_password.getText());
    }

    public void begin(MouseEvent mouseEvent) {
        //++SET++![BT password             BEGIN!]
        btManager.begin(ble_password.getText());
    }

    public void end(MouseEvent mouseEvent) {
        //++SET++![BT password             END!]
        btManager.end(ble_password.getText());
    }


    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}