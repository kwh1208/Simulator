package dbps.dbps.service;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogService {

    static LogService logService = new LogService();


    private LogService(){
    }

    public static LogService getInstance(){
        return logService;
    }


    public void updateInfoLog(String additionalLog, TextFlow logArea){
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        String formattedTime = formatter.format(currentDate);
        logArea.getChildren().add(new Text("\n[" + formattedTime+"] " + additionalLog));
    }

    public void clearLog(TextFlow logArea){
        logArea.getChildren().clear();
    }


    public void warningLog(String additionalLog, TextFlow logArea){
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        String formattedTime = formatter.format(currentDate);

        logArea.getChildren().add(new Text("\n[" + formattedTime+"] "+"[Warning!]" + additionalLog));
    }


    public void errorLog(String additionalLog, TextFlow logArea){
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

        String formattedTime = formatter.format(currentDate);
        Text errorLog = new Text("\n[" + formattedTime+"] "+"[ERROR!]" + additionalLog);
        errorLog.setStyle("-fx-fill: #E88F89;");


        logArea.getChildren().add(errorLog);
    }

}