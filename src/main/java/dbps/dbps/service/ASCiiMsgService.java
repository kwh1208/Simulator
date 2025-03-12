package dbps.dbps.service;


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

    //메세지 불러오기
    public List<String> loadMessages() {
        List<String> messages = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            String value = configService.getProperty("ASCMsg" + i);
            messages.add(value);
        }

        return messages;
    }
}
