package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.ConfigService;
import dbps.dbps.service.LogService;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import static dbps.dbps.Constants.*;

public class ASCheckController {
    AsciiMsgTransceiver asciiMsgTransceiver;
    ConfigService configService;
    LogService logService;

    public void initialize() {
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        configService = ConfigService.getInstance();
        logService = LogService.getLogService();
    }

    public void copyController() {
        asciiMsgTransceiver.sendMessages("![0081!]", false, null)
                .thenAccept(result -> {
                    Map<String, Object> infoMap = new LinkedHashMap<>();
                    infoMap.put("IS_ASCII", IS_ASCII);
                    infoMap.put("SIZE_COLUMN", SIZE_COLUMN);
                    infoMap.put("SIZE_ROW", SIZE_ROW);
                    infoMap.put("howToArrange", howToArrange);
                    infoMap.put("BITS_PER_PIXEL", BITS_PER_PIXEL);
                    infoMap.put("displaySignal", configService.getProperty("displaySignal"));
                    infoMap.put("displaySignal-color", configService.getProperty(configService.getProperty("displaySignal") + "-color"));
                    infoMap.put("log", logService.getLast10Lines());

                    StringBuilder sb = new StringBuilder();
                    for (Map.Entry<String, Object> entry : infoMap.entrySet()) {
                        sb.append(entry.getKey())
                                .append(" : ")
                                .append(entry.getValue())
                                .append(System.lineSeparator());
                    }
                    String copyText = sb.toString();

                    Platform.runLater(() -> {
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent content = new ClipboardContent();
                        content.putString(copyText);
                        clipboard.setContent(content);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("복사 완료");
                        alert.setHeaderText(null);
                        alert.setContentText("정보가 클립보드에 복사되었습니다.");
                        alert.show();
                    });
                });
    }

    public void openAS() {
        try {
            Desktop.getDesktop().browse(new URI("https://forms.gle/zkt5ALsQKKZhbQnx9"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
