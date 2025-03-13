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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private Stage stage;

    @Override
    public void init() throws Exception {
        super.init();
        ConfigService.getInstance();
        resourceManager = ResourceManager.getInstance();

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
        this.stage = stage;

        Stage splashStage = new Stage();
        splashStage.initStyle(StageStyle.UNDECORATED);
        showSplashScreen(splashStage);

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
                stage.setTitle("DBPS V1.2.0");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.jpg")));
                stage.show();

                splashStage.close();
            });
        });

        loadTask.setOnFailed(e -> {
            // 로드 실패 시 에러 처리 로직
            loadTask.getException().printStackTrace();
        });

        // 별도의 스레드에서 백그라운드 작업 시작
        new Thread(loadTask).start();
    }

    private void showSplashScreen(Stage splashStage) {
        // 스플래시 이미지를 불러옵니다.
        Image splashImage = new Image(getClass().getResourceAsStream("/dbps/dbps/images/logo.jpg"));
        ImageView splashImageView = new ImageView(splashImage);
        splashImageView.setPreserveRatio(true);
        splashImageView.setFitWidth(530);

        // 중앙에 위치하도록 StackPane 사용
        StackPane splashRoot = new StackPane(splashImageView);
        Scene splashScene = new Scene(splashRoot, 538, 348);

        splashStage.setScene(splashScene);
        splashStage.show();

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        // Stage의 가로, 세로 크기를 고려해 중앙에 배치
        splashStage.setX((bounds.getWidth() - splashStage.getWidth()) / 2);
        splashStage.setY((bounds.getHeight() - splashStage.getHeight()) / 2);
    }


    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.subpixeltext", "false");
        System.setProperty("prism.text", "t2k");


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