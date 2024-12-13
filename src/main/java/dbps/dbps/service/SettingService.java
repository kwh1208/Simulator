package dbps.dbps.service;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;

public class SettingService {
    private static SettingService instance;

    // 공용 ProgressIndicator
    public static ProgressIndicator commonProgressIndicator;

    // private 생성자: 외부에서 직접 인스턴스 생성 불가
    private SettingService(ProgressIndicator progressIndicator) {
        commonProgressIndicator = progressIndicator;
    }

    // 싱글톤 인스턴스 반환
    public static synchronized SettingService getInstance(ProgressIndicator progressIndicator) {
        if (instance == null) {
            instance = new SettingService(progressIndicator);
        }
        return instance;
    }

    // ProgressIndicator 설정
    public static void showProgress() {
        if (commonProgressIndicator != null) {
            Platform.runLater(() -> commonProgressIndicator.setVisible(true));
        }
    }

    public static void hideProgress() {
        if (commonProgressIndicator != null) {
            Platform.runLater(() -> commonProgressIndicator.setVisible(false));
        }
    }
}
