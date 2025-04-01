package dbps.dbps.service;

import dbps.dbps.service.connectManager.TCPManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static dbps.dbps.Constants.*;

/**
 * 애플리케이션 설정을 관리하는 서비스
 */
public class ConfigService extends AbstractSingleton<ConfigService> {
    // 싱글톤 인스턴스
    private static volatile ConfigService instance;
    
    // 상수 정의
    private static final String CONFIG_DIR = "config";
    private static final String CONFIG_FILENAME = "config.properties";
    private static final String DEFAULT_COMMENT = "Default configuration";
    
    // 속성 키 정의
    private static final String PROP_IS_ASCII = "IS_ASCII";
    private static final String PROP_CONNECT_TYPE = "connectType";
    private static final String PROP_SERIAL_SPEED = "serialSpeed";
    private static final String PROP_OPEN_PORT_NAME = "openPortName";
    
    // 기본 속성 값 정의
    private static final String DEFAULT_SERIAL_PORT = "COM1";
    private static final String DEFAULT_TCP_ADDR = "192.168.0.10";
    private static final int DEFAULT_TCP_PORT = 5000;
    
    // 멤버 변수
    private final Properties properties;
    public final Properties displayProperties;
    public static String configFilePath;

    /**
     * 생성자 - 설정 파일 로드 및 초기화
     */
    private ConfigService() {
        configFilePath = System.getProperty("user.dir") + File.separator + CONFIG_DIR + File.separator + CONFIG_FILENAME;
        properties = new Properties();
        displayProperties = new Properties();
        
        // 기본 초기화
        DisplaySignal.getInstance().initialize_ASCii();
        createFileIfNotExists(configFilePath);
        loadProperties();
        
        // 상수 초기화
        initializeConstants();
    }
    
    /**
     * 싱글톤 인스턴스를 반환하는 메서드 (Double-checked locking 구현)
     * @return 싱글톤 인스턴스
     */
    public static ConfigService getInstance() {
        if (instance == null) {
            synchronized (ConfigService.class) {
                if (instance == null) {
                    instance = new ConfigService();
                }
            }
        }
        return instance;
    }
    
    /**
     * AbstractSingleton에서 요구하는 createInstance 메서드 구현
     */
    @Override
    protected ConfigService createInstance() {
        return instance; // 이미 생성된 인스턴스 반환
    }
    
    /**
     * 전역 상수 초기화
     */
    private void initializeConstants() {
        // 설정 값을 읽어 상수 초기화
        IS_ASCII = Boolean.parseBoolean(getProperty(PROP_IS_ASCII));
        CONNECT_TYPE = getProperty(PROP_CONNECT_TYPE);
        OPEN_PORT_NAME = getProperty(PROP_OPEN_PORT_NAME);
        SERIAL_BAUDRATE = parseInt(getProperty(PROP_SERIAL_SPEED), 115200);
        RS485_ADDR_NUM = parseInt(getProperty("RS485_ADDR_NUM"), 0);
        TCP_IP = getProperty("clientTCPAddr");
        TCP_PORT = parseInt(getProperty("clientTCPPort"), DEFAULT_TCP_PORT);
        UDP_IP = getProperty("UDPAddr");
        UDP_PORT = parseInt(getProperty("UDPPort"), 5109);
        SIZE_ROW = parseInt(getProperty("displayRowSize"), 32);
        SIZE_COLUMN = parseInt(getProperty("displayColumnSize"), 64);
        
        // 비트 퍼 픽셀 처리
        String bitsPerPixelStr = getProperty("bitsPerPixel");
        BITS_PER_PIXEL = (bitsPerPixelStr != null && !bitsPerPixelStr.isEmpty()) ? 
                          bitsPerPixelStr.charAt(0) - '0' : 1;
        
        howToArrange = getProperty("howToArrange");
        isRS = Boolean.parseBoolean(getProperty("isRS"));
        serverTCPPort = parseInt(getProperty("serverTCPPort"), DEFAULT_TCP_PORT);

        // TCP 매니저 설정
        TCPManager.getManager().setIP(TCP_IP);
        TCPManager.getManager().setPORT(TCP_PORT);
    }

    /**
     * 설정 파일 다시 로드
     */
    public void reloadConfigProperties() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.clear(); // 기존 값 초기화
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("설정 파일을 로드할 수 없습니다");
        }
    }

    /**
     * 설정 파일이 존재하지 않으면 기본 설정으로 파일 생성
     * 
     * @param filePath 설정 파일 경로
     */
    private void createFileIfNotExists(String filePath) {
        File configFile = new File(filePath);
        
        // 파일이 이미 존재하면 건너뜀
        if (configFile.exists()) {
            return;
        }
        
        // 기본 속성 생성
        Properties defaultProperties = createDefaultProperties();
        
        // 디렉토리 생성
        File parentDir = configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        // 파일 생성 및 저장
        try {
            configFile.createNewFile();
            
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                defaultProperties.store(writer, DEFAULT_COMMENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 기본 속성 생성
     * 
     * @return 기본 속성이 설정된 Properties 객체
     */
    private Properties createDefaultProperties() {
        Properties defaultProperties = new Properties();
        
        // ASCII 메시지 기본값
        for (int i = 1; i <= 10; i++) {
            if (i == 1) {
                defaultProperties.setProperty("ASCMsg" + i, "![000Hello world!]");
            } else if (i == 2) {
                defaultProperties.setProperty("ASCMsg" + i, "![000/C1Hello /C2World!]");
            } else if (i == 3) {
                defaultProperties.setProperty("ASCMsg" + i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
            } else {
                defaultProperties.setProperty("ASCMsg" + i, "");
            }
        }
        
        // 기본 설정 값
        defaultProperties.setProperty(PROP_IS_ASCII, "true");
        defaultProperties.setProperty(PROP_CONNECT_TYPE, "serial");
        defaultProperties.setProperty(PROP_SERIAL_SPEED, "115200");
        defaultProperties.setProperty("RS485_ADDR_NUM", "0");
        defaultProperties.setProperty("serverTCPPort", String.valueOf(DEFAULT_TCP_PORT));
        defaultProperties.setProperty("openPortNum", DEFAULT_SERIAL_PORT);
        defaultProperties.setProperty("isRS", "false");
        defaultProperties.setProperty("clientTCPAddr", DEFAULT_TCP_ADDR);
        defaultProperties.setProperty(PROP_OPEN_PORT_NAME, DEFAULT_SERIAL_PORT);
        defaultProperties.setProperty("clientTCPPort", String.valueOf(DEFAULT_TCP_PORT));
        defaultProperties.setProperty("serverTCPAddr", DEFAULT_TCP_ADDR);
        defaultProperties.setProperty("serverTCPPort", String.valueOf(DEFAULT_TCP_PORT));
        defaultProperties.setProperty("UDPPort", "5109");
        defaultProperties.setProperty("UDPAddr", DEFAULT_TCP_ADDR);
        defaultProperties.setProperty("RESPONSE_LATENCY", "3");
        defaultProperties.setProperty("latency", "3");
        defaultProperties.setProperty("lastDisplaySignal", "16D-P16D1S11");
        defaultProperties.setProperty("PROGRAM_LANGUAGE", "한국어");
        
        // 페이지 메시지 관련 기본값
        defaultProperties.setProperty("pageMsgCnt", "10");
        
        // 디스플레이 관련 기본값
        defaultProperties.setProperty("displayRowSize", "2");
        defaultProperties.setProperty("displayColumnSize", "6");
        defaultProperties.setProperty("bitsPerPixel", "8BPP");
        defaultProperties.setProperty("howToArrange", "가로형");
        
        return defaultProperties;
    }

    /**
     * 속성 로드
     */
    private void loadProperties() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            // 로그 처리
        }
    }

    /**
     * 속성 저장
     * 
     * @param key 속성 키
     * @param value 속성 값
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    /**
     * 속성값 가져오기
     * 
     * @param key 속성 키
     * @return 속성 값
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * 디스플레이 속성 가져오기
     * 
     * @param key 속성 키
     * @return 속성 값
     */
    public String getDisplayProperty(String key) {
        return displayProperties.getProperty(key);
    }
    
    /**
     * 디스플레이 속성 설정
     * 
     * @param key 속성 키
     * @param value 속성 값
     */
    public void setDisplayProperties(String key, String value) {
        displayProperties.setProperty(key, value);
        saveDisplayProperties();
    }

    /**
     * 디스플레이 속성 저장 (향후 구현)
     */
    private void saveDisplayProperties() {
        // 구현 필요
    }

    /**
     * 설정 파일 저장
     */
    private void saveProperties() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.store(writer, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 문자열을 정수로 변환 (실패 시 기본값 사용)
     * 
     * @param value 변환할 문자열
     * @param defaultValue 변환 실패시 사용할 기본값
     * @return 변환된 정수 또는 기본값
     */
    private int parseInt(String value, int defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}