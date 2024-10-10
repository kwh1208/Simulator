package dbps.dbps;

import dbps.dbps.service.ConfigService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Simulator extends Application {
    @Override
    public void init() throws Exception {
        super.init();
        ConfigService.getInstance();
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource("/dbps/dbps/fxmls/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 800);

        scene.getStylesheets().add(Simulator.class.getResource("/dbps/dbps/css/main.css").toExternalForm());
        stage.setTitle("DABIT Protocol Simulator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}