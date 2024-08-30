package dbps.dbps.service;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LogService {
    //싱글톤 관리
    private static LogService logService = null;
    public TextFlow logArea;
    public ScrollPane scrollPane;

    private LogService() {
    }

    public static LogService getLogService(){
        if (logService == null){
            logService = new LogService();
        }
        return logService;
    }

    public void setInitial(TextFlow logArea, ScrollPane scrollPane){
        this.logArea = logArea;
        this.scrollPane = scrollPane;
    }

    //일반 로그 업데이트
    public void updateInfoLog(String additionalLog){
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        String formattedTime = formatter.format(currentDate);
        Text updateLog = new Text("[" + formattedTime+"] " + additionalLog+"\n");
        updateLog.setStyle("-fx-fill: white;");
        logArea.getChildren().add(updateLog);

        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }

    public void clearLog(TextFlow logArea){
        logArea.getChildren().clear();
    }


    public void warningLog(String additionalLog){
        updateInfoLog("[Warning!] " + additionalLog);
    }


    public void errorLog(String additionalLog){
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        String formattedTime = formatter.format(currentDate);
        Text errorLog = new Text("[" + formattedTime+"] "+"[ERROR!] " + additionalLog+"\n");
        errorLog.setStyle("-fx-fill: #E88F89;");

        logArea.getChildren().add(errorLog);

        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }

}