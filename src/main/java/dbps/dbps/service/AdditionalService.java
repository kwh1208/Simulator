package dbps.dbps.service;

import javafx.scene.control.Spinner;
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

    public Spinner<Integer> spinnerForBefore;
    public Spinner<Integer> spinnerForAfter;

    public void changeUI(String result){
        String[] split = result.split(" ");
        spinnerForBefore.getValueFactory().setValue(Integer.valueOf(split[0]));
        spinnerForAfter.getValueFactory().setValue(Integer.valueOf(split[1]));
    }
}
