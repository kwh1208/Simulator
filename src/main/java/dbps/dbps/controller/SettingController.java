package dbps.dbps.controller;


import dbps.dbps.Simulator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingController {

    @FXML
    public void initialize(){
    }

    public static Stage communicationSettingWindow;


    public void communicationSettingClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/communicationSetting.fxml"));
            AnchorPane root = fxmlLoader.load();
            communicationSettingWindow = new Stage();
            communicationSettingWindow.setTitle("통신 설정");

            communicationSettingWindow.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(root, 291, 600);
            communicationSettingWindow.setScene(scene);
            communicationSettingWindow.setResizable(false);

            communicationSettingWindow.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
