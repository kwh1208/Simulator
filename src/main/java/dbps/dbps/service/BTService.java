package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BTService {

    static BTService btService;

    private BTService() {
    }

    public static BTService getInstance() {
        if (btService == null) {
            btService = new BTService();
        }
        return btService;
    }

    public TextField ble_id;
    public TextField ble_password;
}
