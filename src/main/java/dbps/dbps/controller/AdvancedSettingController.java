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

import static dbps.dbps.Constants.openModal;

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
        openModal("/dbps/dbps/fxmls/fontSetting.fxml", "폰트 설정", mouseEvent);
    }

    //표출신호 창 열기
    @FXML
    public void transferSignalSetting(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/displaySignalSetting.fxml"));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle("표출신호 설정");
        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);
        modalStage.setOnHidden(event -> {
            if (DisplaySignalSettingController.timeline!=null){
                DisplaySignalSettingController.timeline.stop();
                DisplaySignalSettingController.timeline = null;
            }
        });

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.setOnShown(event -> {
            // 부모 창 위치와 크기 가져오기
            double parentX = parentStage.getX();
            double parentY = parentStage.getY();
            double parentWidth = parentStage.getWidth();
            double parentHeight = parentStage.getHeight();

            // 모달 창 크기 계산
            double modalWidth = modalStage.getWidth();
            double modalHeight = modalStage.getHeight();

            // 위치 계산
            double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙
            double modalY = parentY;

            // 위치 설정
            modalStage.setX(modalX);
            modalStage.setY(modalY);
        });

        modalStage.showAndWait();
    }
    //보드기능 설정 창 열기
    @FXML
    public void boardSetting(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/boardSettings.fxml", "보드 기능 설정", mouseEvent);
    }

    //펌웨어 모달창 열기
    @FXML
    public void firmwareInfo(MouseEvent mouseEvent) throws IOException {
        openModal("/dbps/dbps/fxmls/firmwareUpgrade.fxml", "펌웨어 정보", mouseEvent);
    }



    /**
     * @param fxmlPath open 모달창 fxml 경로
     * @param title 모달창 이름
     * 모달창 open
     */


}
