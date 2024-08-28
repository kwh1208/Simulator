package dbps.dbps.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class UnderTheLineRightController {
    @FXML
    public ChoiceBox<String> BGImgSelection;

    @FXML
    public ChoiceBox<String> fillColor;
    public Pane rightPane;


    @FXML
    public void initialize(){
        BGImgSelection.getItems().add("사용안함");
        for (int i = 1; i < 256; i++) {
            BGImgSelection.getItems().add(String.valueOf(i));
        }

//        rightPane.getStylesheets().add(getClass().getResource("/dbps/dbps/css/underTheLineRight.css").toExternalForm());
    }

    public void sendRelaySignal(MouseEvent mouseEvent) {
    }

    public void sendBGImgSelection(MouseEvent mouseEvent) {
    }

    public void sendFillColor(MouseEvent mouseEvent) {

    }
}
