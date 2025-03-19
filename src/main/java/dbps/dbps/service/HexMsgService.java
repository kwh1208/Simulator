package dbps.dbps.service;

import javafx.scene.control.ComboBox;
import lombok.Setter;

import java.util.ResourceBundle;

@Setter
public class HexMsgService {
    public static HexMsgService instance;

    private ComboBox<ComboItem> xStart;
    private ComboBox<ComboItem> yStart;
    private ComboBox<ComboItem> xEnd;
    private ComboBox<ComboItem> yEnd;
    ResourceBundle bundle;

    public static HexMsgService getInstance() {
        if (instance == null) {
            instance = new HexMsgService();
        }
        return instance;
    }


    public ComboBox<String> pageMsgCnt;


    private HexMsgService() {
        bundle = ResourceManager.getInstance().getBundle();
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

        for (int i = 0; i <= 4 * x; i++) {
            xStart.getItems().add(new ComboItem(String.valueOf(4 * i),String.valueOf(4 * i)+bundle.getString("pixel")));
            xEnd.getItems().add(new ComboItem(String.valueOf(4 * i),String.valueOf(4 * i)+bundle.getString("pixel")));
        }
        for (int i = 0; i <= 4 * y; i++) {
            yStart.getItems().add(new ComboItem(String.valueOf(4 * i),String.valueOf(4 * i)+bundle.getString("pixel")));
            yEnd.getItems().add(new ComboItem(String.valueOf(4 * i),String.valueOf(4 * i)+bundle.getString("pixel")));
        }

        xStart.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
        yStart.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
        xEnd.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
        yEnd.setValue(new ComboItem("0", 0+bundle.getString("pixel")));
    }
}
