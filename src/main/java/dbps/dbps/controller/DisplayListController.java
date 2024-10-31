package dbps.dbps.controller;

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

public class DisplayListController {
    public TextField displaySignal;
    public ListView<String> moduleList;
    public TextField search;
    public AnchorPane displayListAP;
    HashMap<String, String> displayModuleMap = new HashMap<>();
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

        displayListAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/displayList.css").toExternalForm());

        moduleList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displaySignal.setText(displayModuleMap.get(newValue));
        });
    }

    private void inputData() {
        displayModuleMap.put("DABIT_P4_2R4C_8S_6000cd","08D-P32D1S31");
        displayModuleMap.put("04N008P2H1200A1","08D-P16D2S21");
        displayModuleMap.put("DABIT_P3_1R4C_16S_900cd","16D-P16D1S21");
        displayModuleMap.put("HS-P3-192-CFH-1601","16D-P16D1S41");
        displayModuleMap.put("DABIT_P8_1R2C_4S_6500cd","04D-P32D2S61");
        displayModuleMap.put("ECO_T10_1R2C_4S_8000cd","04D-P32D2S61");
        displayModuleMap.put("L800-32X16-4S-V3.0(L800)","04D-P32D2S61");
        displayModuleMap.put("户外P8-4 16*32-V4.1","04D-P32D2S61");
        displayModuleMap.put("YS-P10-320x160-4S-2735-V2","04D-P32D2S61");
        displayModuleMap.put("VL300-T110-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL250F111-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL160T110-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL200T110-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL240T110-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL300F110-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("VL320T210-1","01D-P64D4S21-4^8^12^16");
        displayModuleMap.put("DABIT_P3_4R4C_16S_900cd","32D-P16D1S11");
        displayModuleMap.put("P2.5-2121-64X64-32S-8H-M1.1(BRG)","32D-P16D1S11");
        displayModuleMap.put("DABIT_P10_1R2C_4S_4500cd","04D-P32D2S71");
        displayModuleMap.put("DABIT_P3_2R6C_16S_2700cd","16D-P16D1S11");
        displayModuleMap.put("DABIT_P4_2R2C_16S_900cd","16D-P16D1S11");
        displayModuleMap.put("DABIT_P4_2R2C_16S_2200cd","16D-P16D1S11");
        displayModuleMap.put("DABIT_P10_1R2C_4S_4500cd(BGR)","04D-P32D2S51");
        displayModuleMap.put("P3-1415-16S-64X64-S31","16D-P16D1S31");
        displayModuleMap.put("P3-1415-16S-64X64-S3.1(실외용)","16D-P16D1S31");
        displayModuleMap.put("OKR P10 3535 32X16-4S-V2.0","04D-P32D2S41");
        displayModuleMap.put("VS064T110-0(CE3.00)","16D-P16D1S10-1");
        displayModuleMap.put("VS040T110-0","16D-P16D1S10-1");
        displayModuleMap.put("VS048T110-0","16D-P16D1S10-1");
        displayModuleMap.put("VS064T110-0","16D-P16D1S10-1");
        displayModuleMap.put("VS096T110-0","16D-P16D1S10-1");
        displayModuleMap.put("VS128T110-0","16D-P16D1S10-1");
        displayModuleMap.put("DABIT_P6_2R2C_8S_6500cd(BGR)","08D-P64D1S21");
        displayModuleMap.put("GMSFCM4_240_111 GP22LED_81210","04D-P16D4S11-1^5^9^13");
        displayModuleMap.put("DABIT_P6_2R2C_8S_6500cd","08D-P64D1S61");
        displayModuleMap.put("P6-1921-32x32-8S-GC-M1","08D-P64D1S61");
    }

    public void confirm(MouseEvent mouseEvent) {

    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
