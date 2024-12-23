package dbps.dbps.service;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainService {
    private static Tab messageTab = null;

    private static MainService instance;

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

    public static void setMessageTab(Tab messageTab) {
        MainService.messageTab = messageTab;
    }

    public void showASCiiMsgTab() {
        try {
            // 캐시에 아스키 탭 내용이 있는지 확인
            if (!cachedContent.containsKey("ascii")) {
                // 캐시가 없으면 로드하여 저장
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbps/dbps/fxmls/ASCiiMessage.fxml"));
                ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                loader.setResources(bundle);
                Node asciiContent = loader.load();
                cachedContent.put("ascii", asciiContent);  // 캐싱
            }
            // 캐시된 UI 노드를 사용
            messageTab.setContent(cachedContent.get("ascii"));
            messageTab.setText(ResourceManager.getInstance().getBundle().getString("ASCiiProtocol"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 헥사 메시지 탭 표시
    public void showHEXMsgTab() {
        try {
            // 캐시에 헥사 탭 내용이 있는지 확인
            if (!cachedContent.containsKey("hex")) {
                // 캐시가 없으면 로드하여 저장
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbps/dbps/fxmls/HEXMessage.fxml"));
                ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                loader.setResources(bundle);
                Node hexContent = loader.load();
                cachedContent.put("hex", hexContent);  // 캐싱
            }
            // 캐시된 UI 노드를 사용
            messageTab.setContent(cachedContent.get("hex"));
            messageTab.setText(ResourceManager.getInstance().getBundle().getString("HexProtocol"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
