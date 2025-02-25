package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ConfigService;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DefaultChangeController {
    public TextField clientIPTF;
    public TextField clientPortTF;
    public TextField clientSubnetMaskTF;
    public TextField clientGatewayTF;
    public AnchorPane defaultChangeAP;
    ConfigService configService;


    public void initialize() {
        configService = ConfigService.getInstance();

        defaultChangeAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/additionalFunction.css").toExternalForm());

        clientIPTF.setText(configService.getProperty("dbNetIP"));
        clientPortTF.setText(configService.getProperty("dbNetPort"));
        clientSubnetMaskTF.setText(configService.getProperty("dbNetSubnet"));
        clientGatewayTF.setText(configService.getProperty("dbNetGateway"));
    }


    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void confirm(MouseEvent mouseEvent) {
        configService.setProperty("dbNetIP", clientIPTF.getText());
        configService.setProperty("dbNetPort", clientPortTF.getText());
        configService.setProperty("dbNetGateway", clientGatewayTF.getText());
        configService.setProperty("dbNetSubnet", clientSubnetMaskTF.getText());

        configService.reloadConfigProperties();

        close(mouseEvent);
    }
}