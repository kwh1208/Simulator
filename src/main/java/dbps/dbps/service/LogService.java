package dbps.dbps.service;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogService {
    //싱글톤 관리
    private static LogService logService = null;
    public ScrollPane scrollPane;
    public InlineCssTextArea logTextArea;
    private String logFilePath;

    private LogService() {
        String currentDir = System.getProperty("user.dir");

        // 로그 디렉토리 경로 설정
        String logDirPath = currentDir + File.separator + "log";
        Path logDir = Paths.get(logDirPath);

        // 로그 디렉토리가 없으면 생성
        if (Files.notExists(logDir)) {
            try {
                Files.createDirectories(logDir); // 디렉토리 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 현재 날짜로 로그 파일 이름 설정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        // 로그 파일 경로 설정
        this.logFilePath = logDirPath + File.separator + "log_" + currentDate + ".txt";

        // 로그 파일이 없으면 생성
        Path logFilePath = Paths.get(this.logFilePath);
        if (Files.notExists(logFilePath)) {
            try {
                Files.createFile(logFilePath); // 로그 파일 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static LogService getLogService(){
        if (logService == null){
            logService = new LogService();
        }
        return logService;
    }

    public void setInitial(ScrollPane scrollPane, InlineCssTextArea logTextArea){
        this.scrollPane = scrollPane;
        this.logTextArea = logTextArea;
    }

    //일반 로그 업데이트
    public void updateInfoLog(String additionalLog) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String formattedTime = formatter.format(currentDate);
        String logMessage = "[" + formattedTime + "] " + additionalLog;

        // 텍스트 추가 및 스타일 적용
        Platform.runLater(()->{
            logTextArea.append(logMessage + "\n", "-fx-fill: white; -fx-font-size:16px");
            scrollToBottom();
        });

        writeLogToFile(logMessage);
    }

    // 경고 로그 추가
    public void warningLog(String additionalLog) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String formattedTime = formatter.format(currentDate);
        String logMessage = "[" + formattedTime + "] [WARNING!] " + additionalLog+"\n";

        // 텍스트 추가 및 스타일 적용
        Platform.runLater(()->{
            int start = logTextArea.getLength();
            logTextArea.appendText(logMessage);
            logTextArea.setStyle(start, start+logMessage.length(),"-fx-fill: #FFA500; -fx-font-weight: bold;-fx-padding: 0 5px 5px 5px;");
            scrollToBottom();
        });

        writeLogToFile(logMessage);
    }

    // 에러 로그 추가
    public void errorLog(String additionalLog) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String formattedTime = formatter.format(currentDate);
        String logMessage = "[" + formattedTime + "] [ERROR!] " + additionalLog + "\n" + "\u200B";

        // 텍스트 추가 및 스타일 적용
        Platform.runLater(() -> {
            // 텍스트 추가 및 스타일 적용
            int start = logTextArea.getLength();
            logTextArea.appendText(logMessage);
            logTextArea.setStyle(start, logTextArea.getLength(), "-fx-fill: red; -fx-font-weight: bold;-fx-padding: 0 5px 5px 5px;");
            scrollToBottom();
        });

        writeLogToFile(logMessage);
    }

    private void writeLogToFile(String logMessage) {
        try (FileWriter writer = new FileWriter(logFilePath, true)) {
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 로그 클리어
    public void clearLog() {
        logTextArea.clear();
    }

    // 스크롤을 맨 아래로 설정
    private void scrollToBottom() {
        logTextArea.moveTo(logTextArea.getLength());
        logTextArea.requestFollowCaret();
    }

}