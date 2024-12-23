package dbps.dbps.service;

import javafx.scene.control.ChoiceBox;
import lombok.Setter;

@Setter
public class HexMsgService {
    public static HexMsgService instance;

    public static HexMsgService getInstance() {
        if (instance == null) {
            instance = new HexMsgService();
        }
        return instance;
    }


    public ChoiceBox<String> pageMsgCnt;


    private HexMsgService() {
    }

    public void setUI(int num){
        pageMsgCnt.getItems().clear();

        for (int i = 1; i <= num; i++) {
            pageMsgCnt.getItems().add(String.valueOf(i));
        }

        pageMsgCnt.setValue("1");
    }
}
