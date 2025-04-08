package dbps.dbps;

import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.Locale;

import static dbps.dbps.Constants.OPEN_PORT_NAME;

public class Simulator extends Application {
    @Getter
    private static Simulator instance = null;
    private static ResourceManager resourceManager;
    SerialPortManager serialPortManager;
    ServerTCPManager serverTCPManager;
    TCPManager tcpManager;
    UDPManager udpManager;

    @Override
    public void init() throws Exception {
        super.init();
        ConfigService.getInstance();
        resourceManager = ResourceManager.getInstance();
        Font.loadFont(getClass().getResourceAsStream("/NanumGothic.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/NanumGothicBold.ttf"), 12);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }


    @Override
    public void start(Stage stage) throws IOException {
        instance = this;  // 인스턴스를 저장

        // 백그라운드에서 FXML 로드
        Task<Parent> loadTask = new Task<>() {
            @Override
            protected Parent call() throws Exception {
                FXMLLoader loader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/main.fxml"));
                loader.setResources(resourceManager.getBundle());
                return loader.load();
            }
        };

        loadTask.setOnSucceeded(e -> {
            Parent root = loadTask.getValue();
            Scene scene = new Scene(root, 530, 800);
            scene.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/main.css").toExternalForm());

            // UI 업데이트는 JavaFX Application Thread에서 수행
            Platform.runLater(() -> {
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("DBPS V1.3.1");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.jpg")));
                stage.show();
            });
        });

        loadTask.setOnFailed(e -> {
            // 로드 실패 시 에러 처리 로직
            loadTask.getException().printStackTrace();
        });

        // 별도의 스레드에서 백그라운드 작업 시작
        new Thread(loadTask).start();
    }


    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.subpixeltext", "false");
//        System.setProperty("prism.text", "t2k");


        Locale.setDefault(Locale.KOREAN);

        launch();
    }
    @Override
    public void stop() throws Exception {
        super.stop();

        try {
            serialPortManager = SerialPortManager.getManager();
            if (serialPortManager != null) {
                serialPortManager.closePort(OPEN_PORT_NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            serverTCPManager = ServerTCPManager.getInstance();
            if (serverTCPManager != null) {
                serverTCPManager.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tcpManager = TCPManager.getManager();
            if (tcpManager != null) {
                tcpManager.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            udpManager = UDPManager.getUDPManager();
            if (udpManager != null) {
                udpManager.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}