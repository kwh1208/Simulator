package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdditionalService {

    public static AdditionalService instance = null;

    private AdditionalService() {
    }

    public static AdditionalService getInstance() {
        if (instance == null) instance = new AdditionalService();
        return instance;
    }

    public TextField firmwareVer;
    public TextField CPURate;
    public TextField column;
    public TextField bright;
    public TextField row;
    public TextField array;
}
