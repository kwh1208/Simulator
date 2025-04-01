package dbps.dbps.service;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import lombok.Setter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainService {
    @Setter
    private static Tab messageTab;
    @Setter
    private static Tab settingTab;

    private static MainService instance;
    LogService logService = LogService.getLogService();

    private Map<String, Node> cachedContent = new HashMap<>();

    private MainService() {
    }

    // 싱글톤 인스턴스를 반환하는 정적 메서드
    public static MainService getInstance() {
        if (instance == null) {
            instance = new MainService();
        }
        return instance;
    }

    // 헥사 메시지 탭 표시
    public void showHEXMsgTab() {
        try {
            Node hexContent = cachedContent.computeIfAbsent("hex", key -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbps/dbps/fxmls/HEXMessage.fxml"));
                    ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                    loader.setResources(bundle);
                    return loader.load();
                } catch (IOException e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    logService.updateInfoLog(sw.toString());
                    throw new RuntimeException(e);
                }
            });
            messageTab.setContent(hexContent);
            Label label = new Label(ResourceManager.getInstance().getBundle().getString("protocolTransfer"));
            label.setStyle("-fx-alignment: center; -fx-padding: 4px;");
            messageTab.setGraphic(label);
            messageTab.setText("");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // setting 탭을 지연 로드하며 캐싱 처리
    public void changeSetTab() {
        try {
            Node setContent = cachedContent.computeIfAbsent("set", key -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbps/dbps/fxmls/setting.fxml"));
                    ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                    loader.setResources(bundle);
                    return loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            settingTab.setContent(setContent);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
