package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static dbps.dbps.Constants.*;
import static dbps.dbps.service.SettingService.commonProgressIndicator;

public class UnderTheLineRightController {
    @FXML
    public ComboBox<String> BGImgSelection;

    @FXML
    public VBox rightVbox;

    AsciiMsgTransceiver asciiMsgTransceiver;

    HexMsgTransceiver hexMsgTransceiver;


    @FXML
    public void initialize(){
        BGImgSelection.getItems().add("사용안함");
        for (int i = 1; i < 256; i++) {
            BGImgSelection.getItems().add(String.valueOf(i));
        }

        BGImgSelection.setVisibleRowCount(10);

        rightVbox.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineRight.css").toExternalForm());

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();

        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }


    public void sendBGImgSelection(MouseEvent mouseEvent) {
        String value = BGImgSelection.getValue();
        if (IS_ASCII){
            String result = "";
            if (value.equals("사용안함"))
            {
                result = "000";
            }
            else result = String.format("%03d", Integer.parseInt(value));
            {
                String msg = "![0020"+result+"!]";
                if (isRS){
                    msg = "!["+convertRS485AddrASCii()+"020"+result+"!]";
                }
                asciiMsgTransceiver.sendMessages(msg,false, commonProgressIndicator);
            }
        }
        else {
            int result = 0;
            if (!value.equals("사용안함")){
                result = Integer.parseInt(value);
            }
            String msg = "10 02 00 00 02 4F "+String.format("%02X ", result)+"10 03";
            if (isRS){
                msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 02 4F "+String.format("%02X ", result)+"10 03";
            }
            hexMsgTransceiver.sendMessages(msg, commonProgressIndicator);
        }
    }


    public void openAdditionalFunction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/additionalFunctions.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("추가 기능");
        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    public void docs() {
        try {
            Desktop.getDesktop().browse(new URI("https://publish.obsidian.md/dabitdocs"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void AS() {
        try {
            Desktop.getDesktop().browse(new URI("https://forms.gle/kuZM2CbKDnicmRp3A"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void ASPhoto() {
        try {
            Desktop.getDesktop().browse(new URI("https://forms.gle/zkt5ALsQKKZhbQnx9"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
