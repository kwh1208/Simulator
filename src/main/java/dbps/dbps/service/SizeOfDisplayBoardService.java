package dbps.dbps.service;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import lombok.Setter;

import static dbps.dbps.Constants.SIZE_COLUMN;
import static dbps.dbps.Constants.SIZE_ROW;

public class SizeOfDisplayBoardService {
    private static SizeOfDisplayBoardService instance = null;
    HexMsgService hexMsgService = HexMsgService.getInstance();
    ConfigService configService = ConfigService.getInstance();

    private SizeOfDisplayBoardService() {
    }

    public static SizeOfDisplayBoardService getInstance() {
        if (instance == null) {
            instance = new SizeOfDisplayBoardService();
        }
        return instance;
    }

    @Setter
    public ComboBox<String> howToArray;
    @Setter
    private Spinner<Integer> spinnerForRow;
    @Setter
    private Spinner<Integer> spinnerForColumn;

    public void setDisplaySize(int row, int column) {
        spinnerForRow.getValueFactory().setValue(row);
        spinnerForColumn.getValueFactory().setValue(column);

        setInitialValues();

        hexMsgService.changeXY(SIZE_COLUMN,SIZE_ROW);
    }

    private void setInitialValues() {
        SIZE_ROW = spinnerForRow.getValue();
        SIZE_COLUMN = spinnerForColumn.getValue();

        configService.setProperty("displayRowSize", String.valueOf(SIZE_ROW));
        configService.setProperty("displayColumnSize", String.valueOf(SIZE_COLUMN));
    }
}