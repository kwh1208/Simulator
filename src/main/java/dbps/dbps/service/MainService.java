package dbps.dbps.service;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Setter;

import java.io.IOException;

public class MainService {
    @Setter
    private static Tab messageTab = null;


    private static MainService instance;

    private MainService() {
    }

    // 싱글톤 인스턴스를 반환하는 정적 메서드
    public static MainService getInstance() {
        if (instance == null) {
            instance = new MainService();
        }
        return instance;
    }


    public void showASCiiMsgTab(){
        try {
            Node asciiContent = FXMLLoader.load(getClass().getResource("/dbps/dbps/fxmls/ASCiiMessage.fxml"));
            messageTab.setContent(asciiContent);
            messageTab.setText("아스키 프로토콜");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void showHEXMsgTab(){
        try {
            Node hexContent = FXMLLoader.load(getClass().getResource("/dbps/dbps/fxmls/HEXMessage.fxml"));
            messageTab.setContent(hexContent);
            messageTab.setText("헥사 프로토콜");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
