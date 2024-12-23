package dbps.dbps.service;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;


public class ASCiiMsgService {

    private static ASCiiMsgService instance = null;

    ConfigService configService;

    private ASCiiMsgService() {
        configService = ConfigService.getInstance();
    }

    public static ASCiiMsgService getInstance() {
        if (instance == null) {
            instance = new ASCiiMsgService();
        }
        return instance;
    }

    //메세지 txt 파일에 저장
    public void saveMessages(List<String> msgList){
        for (int i = 0; i < msgList.size(); i++) {
            configService.setProperty("ASCMsg"+(i+1), msgList.get(i));
        }
    }

    //메세지 초기화 확인
    public void resetMsg() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("메세지 초기화");
        alert.setHeaderText("메세지를 초기화하시겠습니까?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            for (int i = 1; i < 10; i++) {
                if (i==1){
                    configService.setProperty("ASCMsg"+i, "![000Hello world!]");
                } else if (i==2) {
                    configService.setProperty("ASCMsg"+i, "![000/C1Hello /C2World!]");
                } else if (i == 3) {
                    configService.setProperty("ASCMsg"+i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
                }
                else configService.setProperty("ASCMsg"+i, "");
            }
        }
    }

    /**
     * 리팩토링
     */

    //메세지 불러오기
    public List<String> loadMessages() {
        List<String> messages = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            String value = configService.getProperty("ASCMsg" + i);
            messages.add(value);
        }

        return messages;
    }
}
