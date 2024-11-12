package dbps.dbps.controller;

import dbps.dbps.Simulator;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AdvancedSettingController {


    @FXML
    Pane ASAP;

    @FXML
    public void initialize(){
        ASAP.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/advancedSetting.css").toExternalForm());
    }


    // 폰트 설정 모달창
    @FXML
    public void fontSetting(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/fontSetting.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("폰트 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }
    //표출신호 창
    public void transferSignalSetting(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/displaySignalSetting.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("표출 신호 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }
    //보드기능 설정 창
    public void boardSetting(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/boardSettings.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("폰트 설정");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }

    //펌웨어 모달창
    public void firmwareInfo(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/firmwareUpgrade.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("펌웨어 정보");

        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.showAndWait();
    }
}
