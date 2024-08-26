package dbps.dbps.controller;

import dbps.dbps.Simulator;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MessageSettingController {

    @FXML
    public ChoiceBox<String> msgInitialize;

    @FXML
    public ChoiceBox<String> pageMsgCnt;

    @FXML
    public Pane msPane;

    @FXML
    public void initialize() {
        msPane.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/messageSetting.css").toExternalForm());

        pageMsgCnt.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedCount = Integer.parseInt(newValue.replace("개", ""));
            msgInitialize.getItems().clear();
            msgInitialize.getItems().add("전체");

            for (int i = 1; i < selectedCount; i++) {
                msgInitialize.getItems().add(i+"개");
            }

            msgInitialize.setValue("전체");
        });
    }

    public void sendMsgInitialize(MouseEvent mouseEvent) {
    }

    public void sendPageCnt(MouseEvent mouseEvent) {

    }
}
