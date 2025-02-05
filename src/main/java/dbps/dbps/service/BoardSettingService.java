package dbps.dbps.service;

import javafx.scene.control.ComboBox;
import lombok.Setter;

@Setter
public class BoardSettingService {
    private static BoardSettingService instance = null;

    private BoardSettingService() {
    }

    public static BoardSettingService getInstance() {
        if (instance == null) {
            instance = new BoardSettingService();
        }
        return instance;
    }

    public ComboBox<String> debugMethod;
    public ComboBox<String> BH1_Func;
    public ComboBox<String> J4_func;
    public ComboBox<String> J2_baud;
    public ComboBox<String> J3_baud;
    public ComboBox<String> BH1_baud;

    public void setUI(String result){
        String[] split = result.split(",");
        debugMethod.getSelectionModel().select(Integer.parseInt(split[0]));
        BH1_Func.getSelectionModel().select(Integer.parseInt(split[1]));
        J4_func.getSelectionModel().select(Integer.parseInt(split[2]));
        J2_baud.getSelectionModel().select(Integer.parseInt(split[3]));
        J3_baud.getSelectionModel().select(Integer.parseInt(split[4]));
        BH1_baud.getSelectionModel().select(Integer.parseInt(split[5]));
    }
}
