package dbps.dbps.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
    private static ResourceBundle bundle;
    private static ResourceManager instance;
    public ConfigService configService;

    private ResourceManager() {
        configService = ConfigService.getInstance();
    }


    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    // 번들 초기화 (로케일에 따라 번들 선택)
    public static void initialize(Locale locale) {
        bundle = ResourceBundle.getBundle("dbps.dbps.messages", locale);
    }

    // 현재 번들 반환
    public ResourceBundle getBundle() {
        if (bundle == null) {
            if (configService.getProperty("PROGRAM_LANGUAGE").equals("한국어")){
                initialize(Locale.KOREAN);
            }
            else initialize(Locale.ENGLISH);
        }
        return bundle;
    }
}