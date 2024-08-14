package dbps.dbps.controller;

import dbps.dbps.Simulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdvancedSettingController {




    public void fontSetting(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("fontSetting.fxml"));
        Parent root = fxmlLoader.load();


        Stage modalStage = new Stage();
        modalStage.setTitle("폰트 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false); // 필요에 따라 크기 조절 가능 설정

        // 모달 창 표시
        modalStage.showAndWait();


    }

    public void transferSignalSetting(MouseEvent mouseEvent) {
    }

    public void boardSetting(MouseEvent mouseEvent) {
    }

    public void firmwareInfo(MouseEvent mouseEvent) {

    }
}
