package dbps.dbps.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigService {
    private static ConfigService instance;
    private Properties properties;
    private String configFilePath;

    private ConfigService() {
        this.configFilePath = configFilePath;
        properties = new Properties();
        loadProperties();
    }
    public static ConfigService getInstance() {
        if (instance != null) {
            instance = new ConfigService();
        }
        return instance;
    }

    private void loadProperties() {
        try (FileReader reader = new FileReader(configFilePath)) {
            properties.load(reader);
            System.out.println("Configuration loaded from " + configFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("Configuration file not found, using default values.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // 변수 값을 파일에서 읽어옴
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void saveProperties() {
        try (FileWriter writer = new FileWriter(configFilePath)) {
            properties.store(writer, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
