package dbps.dbps.controller;

import dbps.dbps.service.connectManager.BTManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class blueToothController {

    public TextField ble_id;
    public TextField ble_password;
    BTManager btManager;
    @FXML
    public void initialize(){
        btManager = BTManager.getInstance();
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
}
