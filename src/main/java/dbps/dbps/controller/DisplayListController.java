package dbps.dbps.controller;

import dbps.dbps.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class DisplayListController {
    public TextField displaySignal;
    public ListView<String> moduleList;
    public TextField search;
    public AnchorPane displayListAP;
    public static String SELECTED_SIGNAL = null;
    HashMap<String, String> displayModuleMap = new LinkedHashMap<>();
    ObservableList<String> dataList;
    FilteredList<String> filteredData;
    @FXML
    public void initialize() {
        inputData();
        dataList = FXCollections.observableArrayList(displayModuleMap.keySet());
        filteredData = new FilteredList<>(dataList, s -> true);
        moduleList.setItems(filteredData);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                // 검색어가 없으면 모든 항목을 표시
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // 검색어와 항목이 일치하는지 검사 (대소문자 구분 없이)
                String lowerCaseFilter = newValue.toLowerCase();
                return item.toLowerCase().contains(lowerCaseFilter);
            });
        });

        moduleList.setOnMouseClicked(event -> handleDoubleClick(event, moduleList));

        ObservableList<String> items = FXCollections.observableArrayList(displayModuleMap.keySet());
        moduleList.setItems(items);

        displayListAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/displayList.css").toExternalForm());

        moduleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displaySignal.setText(displayModuleMap.get(newValue));
        });

        displayListAP.setOnKeyPressed(new Constants.EscapeKeyEventHandler());
    }

    private void handleDoubleClick(MouseEvent event, ListView<String> listView) {
        if (event.getClickCount() == 2) { // 더블클릭 감지
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                confirm(event);
            }
        }
    }

    private void inputData() {
        displayModuleMap.put("04N008P2H1200A1", "08D-P16D2S21");
        displayModuleMap.put("DABIT_P10_1R2C_4S_4500cd", "04D-P32D2S71");
        displayModuleMap.put("DABIT_P10_1R2C_4S_4500cd(BGR)", "04D-P32D2S51");
        displayModuleMap.put("DABIT_P3_1R4C_16S_900cd", "16D-P16D1S21");
        displayModuleMap.put("DABIT_P3_2R6C_16S_2700cd", "16D-P16D1S11");
        displayModuleMap.put("DABIT_P3_4R4C_16S_900cd", "32D-P16D1S11");
        displayModuleMap.put("DABIT_P4_2R2C_16S_2200cd", "16D-P16D1S11");
        displayModuleMap.put("DABIT_P4_2R2C_16S_900cd", "16D-P16D1S11");
        displayModuleMap.put("DABIT_P4_2R4C_8S_6000cd", "08D-P32D1S31");
        displayModuleMap.put("DABIT_P6_2R2C_8S_6500cd", "08D-P64D1S61");
        displayModuleMap.put("DABIT_P6_2R2C_8S_6500cd(BGR)", "08D-P64D1S21");
        displayModuleMap.put("DABIT_P8_1R2C_4S_6500cd", "04D-P32D2S61");
        displayModuleMap.put("ECO_T10_1R2C_4S_8000cd", "04D-P32D2S61");
        displayModuleMap.put("GMSFCM4_240_111 GP22LED_81210", "04D-P16D4S11-1^5^9^13");
        displayModuleMap.put("HS-64W1-CFN-0801", "08D-P64D1S71");
        displayModuleMap.put("HS-P3-192-CFH-1601", "16D-P16D1S41");
        displayModuleMap.put("L800-32X16-4S-V3.0(L800)", "04D-P32D2S61");
        displayModuleMap.put("OKR P10 3535 32X16-4S-V2.0", "04D-P32D2S41");
        displayModuleMap.put("P2.5-2121-64X64-32S-8H-M1.1(BRG)", "32D-P16D1S11");
        displayModuleMap.put("P3-1415-16S-64X64-S31", "16D-P16D1S31");
        displayModuleMap.put("P3-1415-16S-64X64-S3.1(실외용)", "16D-P16D1S31");
        displayModuleMap.put("P6-1921-32x32-8S-GC-M1", "08D-P64D1S61");
        displayModuleMap.put("VL160T110-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL200T110-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL240T110-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL250F111-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL300-T110-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL300F110-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL320T210-1", "01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VS040T110-0", "16D-P16D1S10-1");
        displayModuleMap.put("VS048T110-0", "16D-P16D1S10-1");
        displayModuleMap.put("VS064T110-0", "16D-P16D1S10-1");
        displayModuleMap.put("VS064T110-0(CE3.00)", "16D-P16D1S10-1");
        displayModuleMap.put("VS096T110-0", "16D-P16D1S10-1");
        displayModuleMap.put("VS128T110-0", "16D-P16D1S10-1");
        displayModuleMap.put("YS-P10-320x160-4S-2735-V2", "04D-P32D2S61");
        displayModuleMap.put("户外P8-4 16*32-V4.1", "04D-P32D2S61");
        displayModuleMap.put("CM80-D43N-B", "04D-P32D2S31-2<1^4<3");
        displayModuleMap.put("SEOUL JUNKWANG 70614 3COLOR_1/4DUTY DRIVER", "04D-P32D2S31-2<1^4<3");
        displayModuleMap.put("GMS3CD8-91209", "08D-P16D2S23-1^2");
        displayModuleMap.put("SD-83N11(8D)", "08D-P16D2S23-1^2");
        displayModuleMap.put("CM8D-D83N-A", "08D-P16D2S23-1^2");
        displayModuleMap.put("GMSFCD4-80102", "04D-P16D4S11-1^5^9^13");
        displayModuleMap.put("CM80-M8FN-320B-2014.11.05", "04D-P16D4S11-1^5^9^13");
    }

    public void confirm(MouseEvent mouseEvent) {
        SELECTED_SIGNAL = displaySignal.getText();

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
