package dbps.dbps;

import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Simulator extends Application {
    private static Simulator instance = null;
    private static ResourceManager resourceManager;

    private Stage stage;

    public static Simulator getInstance() {
        return instance;
    }

    @Override
    public void init() throws Exception {
        super.init();
        ConfigService.getInstance();
        resourceManager = ResourceManager.getInstance();
    }


    @Override
    public void start(Stage stage) throws IOException {
        instance = this;  // 인스턴스를 저장
        this.stage = stage;

        // 기본 UI 로드
        loadUI();
    }

    public void loadUI() throws IOException {
        FXMLLoader loader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/main.fxml"));
        loader.setResources(resourceManager.getBundle());  // 새로운 번들 주입
        Scene scene = new Scene(loader.load(), 700, 800);

        // 스타일시트 적용
        scene.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/main.css").toExternalForm());

        // Stage에 새 Scene 설정
        stage.setScene(scene);
        stage.setTitle("DBPS");  // 새 타이틀 설정
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}