package dbps.dbps.controller;


import dbps.dbps.Simulator;
import dbps.dbps.service.LogService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.InlineCssTextArea;


public class LogController {

    LogService logService;

    @FXML
    public VBox logVBoX;

    @FXML
    private Button clearBtn;


    private InlineCssTextArea logTextArea;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize(){
        Image image = new Image(getClass().getResourceAsStream("/dbps/dbps/images/휴지통.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(17.5);
        imageView.setPreserveRatio(true);  // 비율 유지
        imageView.setSmooth(true);  // 스무딩 활성화

        logTextArea = new InlineCssTextArea();
        logTextArea.setWrapText(true);
        logTextArea.setEditable(false);
        logTextArea.setStyle("-fx-background-color: #1C1F26; -fx-text-fill: white; -fx-font-size: 14px;");
        logTextArea.setMinWidth(696);
        logTextArea.setMinHeight(127);
        scrollPane.setContent(logTextArea);


        clearBtn.setGraphic(imageView);

        logVBoX.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/log.css").toExternalForm());

        logService = LogService.getLogService();
        logService.setInitial(scrollPane, logTextArea);
    }

    public void clearLog(){
        logService.clearLog();
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