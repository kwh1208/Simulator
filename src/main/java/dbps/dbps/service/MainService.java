package dbps.dbps.service;


import dbps.dbps.Simulator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.IS_MQTT;

public class MainService {
    @Setter
    private static Tab messageTab;
    @Setter
    private static Tab settingTab;

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

    public void showMQTTTab() {
        try {
            // 캐시에 아스키 탭 내용이 있는지 확인
            if (!cachedContent.containsKey("mqtt")) {
                // 캐시가 없으면 로드하여 저장
                FXMLLoader loader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/mqttMsg.fxml"));
                ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                loader.setResources(bundle);
                Node asciiContent = loader.load();
                cachedContent.put("mqtt", asciiContent);  // 캐싱
            }
            // 캐시된 UI 노드를 사용
            messageTab.setContent(cachedContent.get("mqtt"));
            messageTab.setText(ResourceManager.getInstance().getBundle().getString("mqtt"));
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

    public void changeSetTab(){
        try {
            if (IS_MQTT){
                if (!cachedContent.containsKey("mqttSet")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbps/dbps/fxmls/mqttSetting.fxml"));
                    ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                    loader.setResources(bundle);
                    Node hexContent = loader.load();
                    cachedContent.put("mqttSet", hexContent);  // 캐싱
                }
                settingTab.setContent(cachedContent.get("mqttSet"));
            } else {
                if (!cachedContent.containsKey("set")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dbps/dbps/fxmls/setting.fxml"));
                    ResourceBundle bundle = ResourceManager.getInstance().getBundle();
                    loader.setResources(bundle);
                    Node hexContent = loader.load();
                    cachedContent.put("set", hexContent);  // 캐싱
                }
                settingTab.setContent(cachedContent.get("set"));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
