package dbps.dbps.service;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import lombok.Setter;

@Setter
public class HexMsgService {
    public static HexMsgService instance;

    private ComboBox<String> xStart;
    private ComboBox<String> yStart;
    private ComboBox<String> xEnd;
    private ComboBox<String> yEnd;

    public static HexMsgService getInstance() {
        if (instance == null) {
            instance = new HexMsgService();
        }
        return instance;
    }


    public ComboBox<String> pageMsgCnt;


    private HexMsgService() {
    }

    public void setUI(int num){
        pageMsgCnt.getItems().clear();

        for (int i = 1; i <= num; i++) {
            pageMsgCnt.getItems().add(String.valueOf(i));
        }

        pageMsgCnt.setValue("1");
    }

    public void changeXY(int x, int y){
        xStart.getItems().clear();
        yStart.getItems().clear();
        xEnd.getItems().clear();
        yEnd.getItems().clear();

        for (int i = 0; i <= 4*x; i++) {
            xStart.getItems().add(String.valueOf(4*i));
            xEnd.getItems().add(String.valueOf(4*i));
        }
        for (int i = 0; i <= 4*y; i++) {
            yStart.getItems().add(String.valueOf(4*i));
            yEnd.getItems().add(String.valueOf(4*i));
        }

        xStart.setValue("0");
        yStart.setValue("0");
        xEnd.setValue("0");
        yEnd.setValue("0");
    }
}
