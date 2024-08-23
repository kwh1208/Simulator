package dbps.dbps.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

import static dbps.dbps.service.LogService.logService;


public class LogController {

    @FXML
    private TextFlow logArea;

    @FXML
    private Button clearBtn;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize(){
        logService.setInitial(logArea, scrollPane);

        if (logArea != null) {
            System.out.println("logArea initialized successfully.");
        } else {
            System.out.println("logArea is null.");
        }

        Image image = new Image(getClass().getResourceAsStream("/dbps/dbps/images/휴지통.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(23);
        imageView.setFitHeight(23);
        clearBtn.setGraphic(imageView);
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
        clearBtn.setStyle("-fx-background-color: #1E3A5F; -fx-border-color: black; -fx-border-width: 1px");
    }

    @FXML
    public void clearBtnExited(){
        clearBtn.setStyle("-fx-background-color: #1E3A5F");
    }
}