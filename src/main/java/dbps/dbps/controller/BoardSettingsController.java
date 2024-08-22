package dbps.dbps.controller;

import dbps.dbps.Simulator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static dbps.dbps.controller.SettingController.communicationSettingWindow;

public class BoardSettingsController {
    @FXML
    public RadioButton settingRadio;

    @FXML
    public RadioButton readRadio;

    @FXML
    public Pane boardDisable;

    ToggleGroup group = new ToggleGroup();
    @FXML
    public void initialize() {
        settingRadio.setToggleGroup(group);
        readRadio.setToggleGroup(group);
        readRadio.setSelected(true);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (group.getSelectedToggle() == settingRadio) {
                //3~8까지 활성화
                boardDisable.getChildren().forEach(node -> {
                    node.setDisable(false);
                });
            } else {
                //3~8까지 비활성화
                boardDisable.getChildren().forEach(node -> {
                    node.setDisable(true);
                });
            }
        });
    }

    @FXML
    public void openCommunicationSetting() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/communicationSetting.fxml"));
        AnchorPane root = fxmlLoader.load();
        communicationSettingWindow = new Stage();
        communicationSettingWindow.setTitle("통신 설정");

        communicationSettingWindow.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(root, 291, 600);
        communicationSettingWindow.setScene(scene);
        communicationSettingWindow.setResizable(false);

        communicationSettingWindow.show();
    }
    @FXML
    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void Transfer(MouseEvent mouseEvent) {
        //전송
    }
}
