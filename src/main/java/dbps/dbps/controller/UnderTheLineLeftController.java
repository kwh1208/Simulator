package dbps.dbps.controller;


import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.TCPManager;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class UnderTheLineLeftController {


    SerialPortManager serialPortManager;
    TCPManager tcpManager;

    ResourceBundle bundle;


    @FXML
    public Pane leftPane;



    @FXML
    public void initialize() {
        leftPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineLeft.css").toExternalForm());
        bundle= ResourceManager.getInstance().getBundle();


        serialPortManager = SerialPortManager.getManager();

        tcpManager = TCPManager.getManager();
    }


}
