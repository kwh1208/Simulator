package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardInfoReadService {
    private static BoardInfoReadService instance = null;

    private BoardInfoReadService() {
    }

    public static BoardInfoReadService getInstance() {
        if (instance == null) {
            instance = new BoardInfoReadService();
        }
        return instance;
    }

    public TextField brightness;
    public TextField horizontal;
    public TextField vertical;
    public TextField array;
    public TextField firmware;
    public TextField cpu;
}