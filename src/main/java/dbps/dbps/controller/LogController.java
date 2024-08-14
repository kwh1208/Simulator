package dbps.dbps.controller;


import dbps.dbps.service.LogService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import lombok.Getter;


public class LogController {

    @Getter
    private static LogController instance = new LogController();

    public LogController LogController(TextArea logArea) {
        return instance;
    }

    @FXML
    private TextFlow logArea;

    @FXML
    private Button clearBtn;

    private LogService logService = LogService.getInstance();

    @FXML
    public void initialize(){
        instance = this;

        Image image = new Image(getClass().getResourceAsStream("/dbps/dbps/images/휴지통.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(23);
        imageView.setFitHeight(23);
        clearBtn.setGraphic(imageView);


    }

    public void updateLog(String log){
        logService.updateInfoLog(log, logArea);
    }

    public void clearLog(){
        System.out.println("로그 삭제");
        logService.clearLog(logArea);
    }

    public void warningLog(String log){
        logService.warningLog(log, logArea);
    }

    public void errorLog(String log){
        logService.errorLog(log, logArea);
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
        clearBtn.setStyle("-fx-background-color: yellow; -fx-border-color: black; -fx-border-width: 1px");
    }

    @FXML
    public void clearBtnExited(){
        clearBtn.setStyle("-fx-background-color: yellow");
    }
}