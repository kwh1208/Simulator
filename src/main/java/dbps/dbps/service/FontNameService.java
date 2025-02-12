package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FontNameService {
    private static FontNameService instance;

    private FontNameService() {
    }

    public static FontNameService getInstance() {
        if (instance == null){
            instance = new FontNameService();
        }
        return instance;
    }

    public TextField group2font2;
    public TextField group2font1;
    public TextField group2font3;
    public TextField group1font3;
    public TextField group1font1;
    public TextField group1font2;
}
