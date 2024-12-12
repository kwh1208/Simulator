package dbps.dbps.service;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import lombok.Setter;

public class SizeOfDisplayBoardService {
    private static SizeOfDisplayBoardService instance = null;

    private SizeOfDisplayBoardService() {
    }

    public static SizeOfDisplayBoardService getInstance() {
        if (instance == null) {
            instance = new SizeOfDisplayBoardService();
        }
        return instance;
    }

    @Setter
    public ChoiceBox<String> howToArray;
    @Setter
    private Spinner<Integer> spinnerForRow;
    @Setter
    private Spinner<Integer> spinnerForColumn;

    public void setDisplaySize(int row, int column) {
        spinnerForRow.getValueFactory().setValue(row);
        spinnerForColumn.getValueFactory().setValue(column);
    }
}