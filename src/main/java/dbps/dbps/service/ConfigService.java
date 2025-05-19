package dbps.dbps.service;

import dbps.dbps.service.connectManager.TCPManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static dbps.dbps.Constants.*;

public class ConfigService extends AbstractSingleton<ConfigService> {
    // 싱글톤 인스턴스
    private static volatile ConfigService instance;
    
    // 멤버 변수
    private final Properties properties;
    public final Properties displayProperties;
    public static String configFilePath;

    /**
     * 생성자 - 설정 파일 로드 및 초기화
     */
    private ConfigService() {
        configFilePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
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
        return instance;
    }
    
    /**
     * 전역 상수 초기화
     */
    private void initializeConstants() {
        try {
            IS_ASCII = Boolean.parseBoolean(getProperty("IS_ASCII"));
            CONNECT_TYPE = getProperty("connectType");
            OPEN_PORT_NAME = getProperty("openPortName");
            SERIAL_BAUDRATE = Integer.parseInt(getProperty("serialSpeed"));
            RS485_ADDR_NUM = Integer.parseInt(getProperty("RS485_ADDR_NUM"));
            TCP_IP = getProperty("clientTCPAddr");
            TCP_PORT = Integer.parseInt(getProperty("clientTCPPort"));
            UDP_IP = getProperty("UDPAddr");
            UDP_PORT = Integer.parseInt(getProperty("UDPPort"));
            SIZE_ROW = Integer.parseInt(getProperty("displayRowSize"));
            SIZE_COLUMN = Integer.parseInt(getProperty("displayColumnSize"));
            
            String bitsPerPixel = getProperty("bitsPerPixel");
            if (bitsPerPixel != null && !bitsPerPixel.isEmpty()) {
                BITS_PER_PIXEL = bitsPerPixel.charAt(0) - '0';
            }
            
            howToArrange = getProperty("howToArrange");
            isRS = Boolean.parseBoolean(getProperty("isRS"));
            serverTCPPort = Integer.parseInt(getProperty("serverTCPPort"));

            // TCP 매니저 설정
            TCPManager.getManager().setIP(TCP_IP);
            TCPManager.getManager().setPORT(TCP_PORT);
        } catch (NumberFormatException e) {
            System.err.println("설정값 변환 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 설정 파일 다시 로드
     */
    public void reloadConfigProperties() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.clear(); // 기존 값 초기화
            properties.load(reader);
            initializeConstants(); // 상수 다시 초기화
        } catch (IOException e) {
            System.err.println("설정 파일 로드 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("설정 파일을 로드할 수 없습니다");
        }
    }

    /**
     * 설정 파일이 존재하지 않으면 기본 설정으로 파일 생성
     * 
     * @param filePath 설정 파일 경로
     */
    private void createFileIfNotExists(String filePath) {
        if (new File(filePath).exists()) {
            return;
        }
        
        Properties defaultProperties = createDefaultProperties();
        
        File configFile = new File(filePath);
        // 디렉토리 생성
        File parentDir = configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // 디렉토리가 없을 경우 생성
        }
        
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();  // 파일이 없을 경우 생성
                // 기본 설정 값 저장
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    defaultProperties.store(writer, "Default configuration");
                }
            } catch (IOException e) {
                System.err.println("설정 파일 생성 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
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

        defaultProperties.setProperty("lastPage", "0");
        defaultProperties.setProperty("lastSection", "0");
        
        // 기본 연결 설정
        defaultProperties.setProperty("IS_ASCII", "true");
        defaultProperties.setProperty("connectType", "serial");
        defaultProperties.setProperty("serialSpeed", "115200");
        defaultProperties.setProperty("RS485_ADDR_NUM", "0");
        defaultProperties.setProperty("serverTCPPort", "5000");
        defaultProperties.setProperty("openPortNum", "COM1");
        defaultProperties.setProperty("isRS", "false");
        defaultProperties.setProperty("clientTCPAddr", "192.168.0.10");
        defaultProperties.setProperty("openPortName", "COM1");
        defaultProperties.setProperty("clientTCPPort", "5100");
        defaultProperties.setProperty("serverTCPAddr", "192.168.0.10");
        defaultProperties.setProperty("serverTCPPort", "5000");
        defaultProperties.setProperty("UDPPort", "5109");
        defaultProperties.setProperty("UDPAddr", "192.168.0.10");
        defaultProperties.setProperty("RESPONSE_LATENCY", "3");
        defaultProperties.setProperty("latency", "3");
        defaultProperties.setProperty("lastDisplaySignal", "16D-P16D1S11");
        defaultProperties.setProperty("PROGRAM_LANGUAGE", "한국어");

        defaultProperties.setProperty("mqtt_IP", "192.168.0.10");
        defaultProperties.setProperty("mqtt_Port", "1883");

        // 네트워크 설정
        defaultProperties.setProperty("dbNetIP", "192.168.0.201");
        defaultProperties.setProperty("dbNetPort", "5000");
        defaultProperties.setProperty("dbNetGateway", "192.168.0.1");
        defaultProperties.setProperty("dbNetSubnet", "255.255.255.0");

        // 폰트 그룹 설정
        defaultProperties.setProperty("fontGroup1FontPath1", "ENG 08x16-DABIT(표준).fnt");
        defaultProperties.setProperty("fontGroup1FontType1", "english");
        defaultProperties.setProperty("fontGroup1FontPath2", "KOR 16x16-DABIT(표준).fnt");
        defaultProperties.setProperty("fontGroup1FontType2", "combination");
        defaultProperties.setProperty("fontGroup1FontPath3", "USER 16x16-Special(표준).fnt");
        defaultProperties.setProperty("fontGroup1FontType3", "userFont");

        defaultProperties.setProperty("fontGroup2FontPath1", "ENG 08x16-DABIT(표준).fnt");
        defaultProperties.setProperty("fontGroup2FontType1", "english");
        defaultProperties.setProperty("fontGroup2FontPath2", "KOR 16x16-DABIT(표준).fnt");
        defaultProperties.setProperty("fontGroup2FontType2", "combination");
        defaultProperties.setProperty("fontGroup2FontPath3", "USER 16x16-Special(표준).fnt");
        defaultProperties.setProperty("fontGroup2FontType3", "userFont");

        defaultProperties.setProperty("fontGroup3FontType1", "english");
        defaultProperties.setProperty("fontGroup3FontType2", "combination");
        defaultProperties.setProperty("fontGroup3FontType3", "userFont");

        defaultProperties.setProperty("fontGroup4FontType1", "english");
        defaultProperties.setProperty("fontGroup4FontType2", "combination");
        defaultProperties.setProperty("fontGroup4FontType3", "userFont");

        defaultProperties.setProperty("fontGroup1selected", "True");
        defaultProperties.setProperty("fontGroup2selected", "True");
        defaultProperties.setProperty("fontGroup3selected", "False");
        defaultProperties.setProperty("fontGroup4selected", "False");

        defaultProperties.setProperty("isHexRealTime", "0");

        // 디스플레이 설정 - 페이지 및 섹션별 설정
        for (int i = 0; i <= 10; i++) { // 페이지 개수(0은 실시간)
            for (int j = 0; j < 3; j++) { // 섹션 개수
                defaultProperties.setProperty("displayControl" + i + j, "On");
                defaultProperties.setProperty("displayMethod" + i + j, "Clear");
                defaultProperties.setProperty("charCode" + i + j, "CombinationType");
                defaultProperties.setProperty("fontSize" + i + j, "16");
                defaultProperties.setProperty("fontGroup" + i + j, "fontGroup1");
                defaultProperties.setProperty("effectIn" + i + j, "staticEffect");
                defaultProperties.setProperty("effectInDirection" + i + j, "noDirection");
                defaultProperties.setProperty("effectOut" + i + j, "staticEffect");
                defaultProperties.setProperty("effectOutDirection" + i + j, "noDirection");
                defaultProperties.setProperty("effectSpeed" + i + j, "5");
                defaultProperties.setProperty("effectTime" + i + j, "2sec");
                defaultProperties.setProperty("xStart" + i + j, "0");
                defaultProperties.setProperty("xEnd" + i + j, "0");
                defaultProperties.setProperty("yStart" + i + j, "0");
                defaultProperties.setProperty("yEnd" + i + j, "0");
                defaultProperties.setProperty("bgImg" + i + j, "notUsed");
                defaultProperties.setProperty("textColor" + i + j, "1");
                defaultProperties.setProperty("bgColor" + i + j, "0");
                defaultProperties.setProperty("textColorASC" + i + j, "red");
                defaultProperties.setProperty("bgColorASC" + i + j, "black");
                if (i == 0) {
                    defaultProperties.setProperty("text" + i + j, "realTime 메시지 " + j);
                } else {
                    defaultProperties.setProperty("text" + i + j, "page " + i + "-section " + j);
                }
            }
        }

        // 디스플레이 기본 설정
        defaultProperties.setProperty("displayControlDefault", "On");
        defaultProperties.setProperty("displaySignal", "32D-P161S11");
        defaultProperties.setProperty("displayMethodDefault", "Clear");
        defaultProperties.setProperty("charCodeDefault", "한글 조합형");
        defaultProperties.setProperty("fontSizeDefault", "16");
        defaultProperties.setProperty("fontGroupDefault", "폰트그룹1");
        defaultProperties.setProperty("effectInDefault", "정지효과");
        defaultProperties.setProperty("effectInDirectionDefault", "방향없음");
        defaultProperties.setProperty("effectOutDefault", "사용안함");
        defaultProperties.setProperty("effectOutDirectionDefault", "사용안함");
        defaultProperties.setProperty("effectSpeedDefault", "5");
        defaultProperties.setProperty("effectTimeDefault", "2초");
        defaultProperties.setProperty("xStartDefault", "0");
        defaultProperties.setProperty("xEndDefault", "0");
        defaultProperties.setProperty("yStartDefault", "0");
        defaultProperties.setProperty("yEndDefault", "0");
        defaultProperties.setProperty("bgImgDefault", "사용안함");
        defaultProperties.setProperty("textColorDefault", "1");
        defaultProperties.setProperty("bgColorDefault", "2");
        defaultProperties.setProperty("settingText", "![0032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]");

        // 기타 설정
        defaultProperties.setProperty("pageMsgCnt", "10");
        defaultProperties.setProperty("pageMsgClear", "전체");
        defaultProperties.setProperty("displayBrightness", "100");
        defaultProperties.setProperty("realTimeMsg", "효과 동시표출");
        defaultProperties.setProperty("displayRowSize", "2");
        defaultProperties.setProperty("displayColumnSize", "6");
        defaultProperties.setProperty("bitsPerPixel", "8BPP");
        defaultProperties.setProperty("howToArrange", "가로형");
        defaultProperties.setProperty("relay1", "None");
        defaultProperties.setProperty("relay2", "None");
        defaultProperties.setProperty("relay3", "None");
        defaultProperties.setProperty("relay4", "None");
        defaultProperties.setProperty("bgImg", "사용안함");
        defaultProperties.setProperty("displayCover", "검은색");
        
        return defaultProperties;
    }

    /**
     * 속성 로드
     */
    private void loadProperties() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("설정 파일 로드 중 오류 발생: " + e.getMessage());
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
            System.err.println("설정 파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}