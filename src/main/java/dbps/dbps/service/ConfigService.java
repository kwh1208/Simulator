package dbps.dbps.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static dbps.dbps.Constants.*;

public class ConfigService {
    private static ConfigService instance;
    private final Properties properties;
    public final Properties displayProperties;
    private final String configFilePath;
    private final String displayFilePath;

    private ConfigService() {
        configFilePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
        displayFilePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "display.properties";
        properties = new Properties();
        displayProperties = new Properties();
        DisplaySignal.getInstance().initialize_ASCii();
        createFileIfNotExists(configFilePath);
        createFileIfNotExists(displayFilePath);
        loadProperties();

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
        BITS_PER_PIXEL = getProperty("bitsPerPixel").charAt(0)-'0';
        howToArrange = getProperty("howToArrange");
        RESPONSE_LATENCY = Integer.parseInt(getProperty("latency"));
        isRS = Boolean.parseBoolean(getProperty("isRS"));
        serverTCPPort = Integer.parseInt(getProperty("serverTCPPort"));
    }

    private void createFileIfNotExists(String filePath) {
        Properties defaultProperties = new Properties();
            for (int i = 1; i < 10; i++) {
                if (i==1){
                    defaultProperties.setProperty("ASCMsg"+i, "![000Hello world!]");
                } else if (i==2) {
                    defaultProperties.setProperty("ASCMsg"+i, "![000/C1Hello /C2World!]");
                } else if (i == 3) {
                    defaultProperties.setProperty("ASCMsg"+i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
                }
                else defaultProperties.setProperty("ASCMsg"+i, "");
            }

            defaultProperties.setProperty("IS_ASCII", "true");
            defaultProperties.setProperty("connectType", "serial");
            defaultProperties.setProperty("serialSpeed", "115200");
            defaultProperties.setProperty("RS485_ADDR_NUM", "0");
            defaultProperties.setProperty("serverTCPPort", "5000");
            defaultProperties.setProperty("openPortNum", "COM1");
            defaultProperties.setProperty("isRS", "false");
            defaultProperties.setProperty("clientTCPAddr", "192.168.0.10");
            defaultProperties.setProperty("openPortName", "1");
            defaultProperties.setProperty("clientTCPPort", "5100");
            defaultProperties.setProperty("serverTCPAddr", "192.168.0.10");
            defaultProperties.setProperty("serverTCPPort", "5000");
            defaultProperties.setProperty("UDPPort", "5109");
            defaultProperties.setProperty("UDPAddr", "192.168.0.10");
            defaultProperties.setProperty("RESPONSE_LATENCY", "3");
            defaultProperties.setProperty("latency", "3");
            defaultProperties.setProperty("lastDisplaySignal", "16D-P16D1S11");
            defaultProperties.setProperty("PROGRAM_LANGUAGE", "korean");

            defaultProperties.setProperty("fontGroup1Size", "8X16/16X16");
            defaultProperties.setProperty("fontGroup1FontPath1", new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"font");
            defaultProperties.setProperty("fontGroup1FontType1", "영어(ASCII)");
            defaultProperties.setProperty("fontGroup1FontPath2", new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"font");
            defaultProperties.setProperty("fontGroup1FontType2", "한글조합형");
            defaultProperties.setProperty("fontGroup1FontPath3", new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"font");
            defaultProperties.setProperty("fontGroup1FontType3", "사용자폰트");

            defaultProperties.setProperty("fontGroup2Size", "8X16/16X16");
            defaultProperties.setProperty("fontGroup2FontPath1", new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"font");
            defaultProperties.setProperty("fontGroup2FontType1", "영어(ASCII)");
            defaultProperties.setProperty("fontGroup2FontPath2", new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"font");
            defaultProperties.setProperty("fontGroup2FontType2", "한글조합형");
            defaultProperties.setProperty("fontGroup2FontPath3", new File(System.getProperty("user.dir")).getAbsolutePath()+File.separator+"font");
            defaultProperties.setProperty("fontGroup2FontType3", "사용자폰트");

            defaultProperties.setProperty("fontGroup1selected", "True");
            defaultProperties.setProperty("fontGroup2selected", "True");
            defaultProperties.setProperty("fontGroup3selected", "False");
            defaultProperties.setProperty("fontGroup4selected", "False");

            defaultProperties.setProperty("isHexRealTime", "0");

            for (int i = 0; i <= 10; i++) {//페이지 개수(0은 실시간)
                for (int j =0; j < 3; j++) {//섹션 개수
                    defaultProperties.setProperty("displayControl"+i+j, "ON");
                    defaultProperties.setProperty("displayMethod"+i+j, "Normal");
                    defaultProperties.setProperty("charCode"+i+j, "한글 조합형");
                    defaultProperties.setProperty("fontSize"+i+j, "16(Standard)");
                    defaultProperties.setProperty("fontGroup"+i+j, "FontGroup0");
                    defaultProperties.setProperty("effectIn"+i+j, "정지효과");
                    defaultProperties.setProperty("effectInDirection"+i+j, "방향없음");
                    defaultProperties.setProperty("effectOut"+i+j, "사용안함");
                    defaultProperties.setProperty("effectOutDirection"+i+j, "사용안함");
                    defaultProperties.setProperty("effectSpeed"+i+j, "5");
                    defaultProperties.setProperty("effectTime"+i+j, "2초");
                    defaultProperties.setProperty("xStart"+i+j, "0");
                    defaultProperties.setProperty("xEnd"+i+j, "0");
                    defaultProperties.setProperty("yStart"+i+j, "0");
                    defaultProperties.setProperty("yEnd"+i+j, "0");
                    defaultProperties.setProperty("bgImg"+i+j, "사용안함");
                    defaultProperties.setProperty("textColor"+i+j, "1");
                    defaultProperties.setProperty("bgColor"+i+j, "0");
                    if (i==0) {
                        defaultProperties.setProperty("text"+i+j, "realTime 메세지 "+j);
                    }
                    else {
                        defaultProperties.setProperty("text"+i+j, "page "+i+"-section "+j);
                    }
                }
            }

            defaultProperties.setProperty("displayControlDefault", "ON");
            defaultProperties.setProperty("displayMethodDefault", "Normal");
            defaultProperties.setProperty("charCodeDefault", "한글 조합형");
            defaultProperties.setProperty("fontSizeDefault", "16(Standard)");
            defaultProperties.setProperty("fontGroupDefault", "FontGroup0");
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

            defaultProperties.setProperty("pageMsgCnt", "1");
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
        File configFile = new File(filePath);
        // 디렉토리 생성
        File parentDir = configFile.getParentFile();
        if (!parentDir.exists()) parentDir.mkdirs();  // 디렉토리가 없을 경우 생성

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();  // 파일이 없을 경우 생성
                // 기본 설정 값 저장
                try (FileWriter writer = new FileWriter(configFile)) {
                    defaultProperties.store(writer, "Default configuration");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConfigService getInstance() {
        if (instance == null) {
            instance = new ConfigService();
        }
        return instance;
    }

    private void loadProperties() {
        try (FileReader reader = new FileReader(configFilePath)) {
            properties.load(reader);
        } catch (IOException e) {
        }
        try (FileReader reader = new FileReader(displayFilePath)){
            displayProperties.load(reader);
        } catch (IOException e) {

        }
    }

    /**
     * ttl, tcp
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    // 변수 값을 파일에서 읽어옴
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getDisplayProperty(String key) {
        return displayProperties.getProperty(key);
    }
    //데코엘

    public void setDisplayProperties(String key, String value){
        displayProperties.setProperty(key, value);
        saveDisplayProperties();
    }

    private void saveDisplayProperties() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(displayFilePath), StandardCharsets.UTF_8)) {
            displayProperties.store(writer, null);
        } catch (IOException e) {
        }
    }

    private void saveProperties() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.store(writer, null);
        } catch (IOException e) {
        }
    }
}
