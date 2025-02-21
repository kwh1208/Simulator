package dbps.dbps;

import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import dbps.dbps.service.connectManager.SerialPortManager;
import dbps.dbps.service.connectManager.ServerTCPManager;
import dbps.dbps.service.connectManager.TCPManager;
import dbps.dbps.service.connectManager.UDPManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

        // 기본 UI 로드
        loadUI();
    }

    public void loadUI() throws IOException {
        // FXML 로드
        FXMLLoader loader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/main.fxml"));
        loader.setResources(resourceManager.getBundle()); // 새로운 번들 주입

        // Scene 설정
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 800);

        // 스타일시트 적용
        scene.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/main.css").toExternalForm());


        // Stage 설정
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("dbProtocolSimulator V1.0.0");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.jpg")));
        stage.show();
    }


    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.subpixeltext", "false");
        System.setProperty("prism.text", "t2k");

        Locale.setDefault(Locale.KOREAN);

        launch();
    }
    @Override
    public void stop() throws Exception{
        super.stop();

        try {
            serialPortManager = SerialPortManager.getManager();
            if (serialPortManager != null) {
                serialPortManager.closePort(OPEN_PORT_NAME);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        try {
            serverTCPManager = ServerTCPManager.getInstance();
            if (serverTCPManager != null) {
                serverTCPManager.disconnect();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        try {
            tcpManager = TCPManager.getManager();
            if (tcpManager != null) {
                tcpManager.disconnect();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        try {
            udpManager = UDPManager.getUDPManager();
            if (udpManager != null) {
                udpManager.disconnect();
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}