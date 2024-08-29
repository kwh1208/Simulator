package dbps.dbps.controller;


import dbps.dbps.Simulator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import static dbps.dbps.service.LogService.logService;


public class LogController {

    @FXML
    public VBox logVBoX;

    @FXML
    private TextFlow logArea;

    @FXML
    private Button clearBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize(){
        logService.setInitial(logArea, scrollPane);

        Image image = new Image(getClass().getResourceAsStream("/dbps/dbps/images/휴지통.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(17.5);
        imageView.setPreserveRatio(true);  // 비율 유지
        imageView.setSmooth(true);  // 스무딩 활성화

        clearBtn.setGraphic(imageView);

        logVBoX.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/log.css").toExternalForm());
        logArea.getChildren().add(new Text("\n"));
    }

    public void clearLog(){
        logService.clearLog(logArea);
    }

    /**
     * 기능 관련(click, input)등
     */

    @FXML
    public void clearBtnClicked(){
        clearLog();
    }

    /**
     * 효과 관련된 메소드(mouse in/out)등
     */

    @FXML
    public void clearBtnEntered(){
        clearBtn.setStyle("-fx-background-color: #1C1F26; -fx-border-color: black; -fx-border-width: 1px");
    }

    @FXML
    public void clearBtnExited(){
        clearBtn.setStyle("-fx-background-color: #1C1F26");
    }
}