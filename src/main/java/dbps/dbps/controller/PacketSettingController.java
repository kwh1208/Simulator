package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.PacketSettingService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class PacketSettingController {
    public TextField ascFirst;
    public TextField ascSecond;
    public TextField hexFirst;
    public TextField hexSecond;
    public TextField timeOut;
    public AnchorPane psAP;

    @FXML
    public void initialize(){
        psAP.getStylesheets().add(Objects.requireNonNull(Simulator.class.getResource("/dbps/dbps/css/dabitNet.css")).toExternalForm());
    }

    public void setPacket() {
        PacketSettingService packetSettingService = PacketSettingService.getInstance();
        packetSettingService.getOriAscFirst().setText(ascFirst.getText());
        packetSettingService.getOriAscSecond().setText(ascSecond.getText());
        packetSettingService.getOriHexFirst().setText(hexFirst.getText());
        packetSettingService.getOriHexSecond().setText(hexSecond.getText());
        packetSettingService.getOriTimeOut().setText(timeOut.getText());
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
