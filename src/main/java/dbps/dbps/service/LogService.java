package dbps.dbps.service;

import javafx.scene.control.ScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LogService {
    //싱글톤 관리
    private static LogService logService = null;
    public ScrollPane scrollPane;
    public InlineCssTextArea logTextArea;

    private LogService() {
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
        logTextArea.append(logMessage + "\n", "-fx-fill: white; -fx-padding: 0 0 5px 0;");
        scrollToBottom();
    }

    // 경고 로그 추가
    public void warningLog(String additionalLog) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String formattedTime = formatter.format(currentDate);
        String logMessage = "[" + formattedTime + "] [WARNING!] " + additionalLog+"\n";

        // 텍스트 추가 및 스타일 적용
        int start = logTextArea.getLength();
        logTextArea.appendText(logMessage);
        logTextArea.setStyle(start, start+logMessage.length(),"-fx-fill: #FFA500; -fx-font-weight: bold;");

        scrollToBottom();
    }

    // 에러 로그 추가
    public void errorLog(String additionalLog) {
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String formattedTime = formatter.format(currentDate);
        String logMessage = "[" + formattedTime + "] [ERROR!] " + additionalLog + "\n" + "\u200B";

        // 텍스트 추가 및 스타일 적용
        int start = logTextArea.getLength();
        logTextArea.appendText(logMessage);
        logTextArea.setStyle(start, logTextArea.getLength(), "-fx-fill: red; -fx-font-weight: bold;");
        scrollToBottom();
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