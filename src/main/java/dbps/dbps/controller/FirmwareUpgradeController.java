package dbps.dbps.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FirmwareUpgradeController {

    @FXML
    public TextField firmwareInformation;

    @FXML
    public TextField firmwareFileInformation;

    @FXML
    public TextField fileLocation;

    @FXML
    public AnchorPane firmwareUpgradeAP;

    @FXML
    public void initialize() {
        firmwareUpgradeAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/firmwareUpgrade.css").toExternalForm());
    }


    public void read(MouseEvent mouseEvent) {
        //msg maker통해서 정보읽어오는 신호 보내기
    }

    public void open(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("펌웨어 파일 선택");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("펌웨어 파일", "*.bin"),
                new FileChooser.ExtensionFilter("모든 파일", "*.*")
        );
        Stage stage = (Stage) (((Node) mouseEvent.getSource()).getScene().getWindow());
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            fileLocation.setText(selectedFile.getAbsolutePath());
        }

        //파일 정보읽어서 firmwareFileInformation에 업데이트
    }

    public void send(MouseEvent mouseEvent) {

    }

    public void close(MouseEvent mouseEvent) {
        ((Stage)(((Node) mouseEvent.getSource()).getScene().getWindow())).close();
    }
}
