package dbps.dbps.service;

import dbps.dbps.controller.DabitNetController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

@Getter
@Setter
public class DabitNetService {
    private static DabitNetService instance = null;

    private DabitNetService(){
    }

    public static DabitNetService getInstance(){
        if(instance == null){
            instance = new DabitNetService();
        }
        return instance;
    }

    Map<String, DabitNetController.DB300IPPort> db300InfoList;
    ListView<String> dbList;
    Button searchBtn;

    public void updateUI(String message){
            BufferedReader reader = new BufferedReader(new StringReader(message));
            String line;
            int idx = 0;
            DabitNetController.DB300IPPort readDB300IPPort = null;
            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                line = line.trim();
                if (line.equals("DIBD")) {
                    idx = 1;
                    if (readDB300IPPort != null) {
                        db300InfoList.put(readDB300IPPort.getMacAddress(), readDB300IPPort);
                    }
                    readDB300IPPort = new DabitNetController.DB300IPPort();
                    continue;
                }
                switch (idx) {
                    case 1:
                        readDB300IPPort.setMacAddress(line);
                        idx++;
                        break;
                    case 2:
                        readDB300IPPort.setClientIP(line);
                        idx++;
                        break;
                    case 3:
                        readDB300IPPort.setClientSubnetMask(line);
                        idx++;
                        break;
                    case 4:
                        readDB300IPPort.setClientGateway(line);
                        idx++;
                        break;
                    case 5:
                        readDB300IPPort.setClientPort(line);
                        idx++;
                        break;
                    case 6:
                        readDB300IPPort.setIpStatic(line.equals("30"));
                        idx++;
                        break;
                    case 7:
                        readDB300IPPort.setServerIP(line);
                        idx++;
                        break;
                    case 8:
                        readDB300IPPort.setServerPort(line);
                        idx++;
                        break;
                    case 9:
                        readDB300IPPort.setServerMode(line.equals("30"));
                        idx++;
                        break;
                    case 10:
                        readDB300IPPort.setName(line);
                        idx++;
                        break;
                    case 11:
                        readDB300IPPort.setHeartbeat(line);
                        idx++;
                        break;
                    case 12:
                        idx++;
                        break;
                    case 13:
                        if (line.contains("TX")) {
                            break;
                        }
                        readDB300IPPort.setWifiSSID(line);
                        idx++;
                        break;
                    case 14:
                        readDB300IPPort.setWifiPW(line);
                        idx++;
                        break;
                    case 15:
                        if (line.contains("30")){
                            readDB300IPPort.setStation(true);
                        }
                        else if (line.contains("31")){
                            readDB300IPPort.setStation(false);
                        }
                        idx++;
                        break;
                    default:
                        break;
                }
            }
            db300InfoList.put(readDB300IPPort.getMacAddress(), readDB300IPPort);
            for (Map.Entry<String, DabitNetController.DB300IPPort> db300Infos : db300InfoList.entrySet()) {
                DabitNetController.DB300IPPort db300IPPort = db300Infos.getValue();
                if (!dbList.getItems().contains(db300IPPort.getMacAddress())) {
                    dbList.getItems().add(db300IPPort.getMacAddress());
                    if ((dbList.getItems()).size()==1){
                        Platform.runLater(()->{
                            dbList.getSelectionModel().select(0);
                        });
                    }
                }
            }
    }
}
