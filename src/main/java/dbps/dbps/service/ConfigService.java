package dbps.dbps.service;

import dbps.dbps.service.connectManager.TCPManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static dbps.dbps.Constants.*;

public class ConfigService {
    private static ConfigService instance;
    private final Properties properties;
    public final Properties displayProperties;
    public static String configFilePath;
    private final String displayFilePath;
    LogService logService;

    private ConfigService() {
        logService = LogService.getLogService();
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

        TCPManager.getManager().setIP(TCP_IP);
        TCPManager.getManager().setPORT(TCP_PORT);
    }

    public void reloadConfigProperties() {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.clear(); // 기존 값 초기화
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void createFileIfNotExists(String filePath) {
        if (new File(filePath).exists()) {
            return;
        }
        Properties defaultProperties = new Properties();
        if (filePath.equals(displayFilePath)){
            defaultProperties.setProperty("08D-P16D2S21", "04N008P2H1200A1");
            defaultProperties.setProperty("04D-P32D2S71", "DABIT_P10_1R2C_4S_4500cd");
            defaultProperties.setProperty("04D-P32D2S51", "DABIT_P10_1R2C_4S_4500cd(BGR)");
            defaultProperties.setProperty("16D-P16D1S21", "DABIT_P3_1R4C_16S_900cd");
            defaultProperties.setProperty("16D-P16D1S11", "DABIT_P3_2R6C_16S_2700cd\nDABIT_P4_2R2C_16S_2200cd\nDABIT_P4_2R2C_16S_900cd");
            defaultProperties.setProperty("32D-P16D1S11", "DABIT_P3_4R4C_16S_900cd\nP2.5-2121-64X64-32S-8H-M1.1(BRG)");
            defaultProperties.setProperty("08D-P32D1S31", "DABIT_P4_2R4C_8S_6000cd");
            defaultProperties.setProperty("08D-P64D1S61", "DABIT_P6_2R2C_8S_6500cd\nP6-1921-32x32-8S-GC-M1");
            defaultProperties.setProperty("08D-P64D1S21", "DABIT_P6_2R2C_8S_6500cd(BGR)");
            defaultProperties.setProperty("04D-P32D2S61", "DABIT_P8_1R2C_4S_6500cd\nECO_T10_1R2C_4S_8000cd\nL800-32X16-4S-V3.0(L800)\nYS-P10-320x160-4S-2735-V2\n户外P8-4 16*32-V4.1");
            defaultProperties.setProperty("04D-P16D4S11-1^5^9^13", "GMSFCM4_240_111 GP22LED_81210");
            defaultProperties.setProperty("08D-P64D1S71", "HS-64W1-CFN-0801");
            defaultProperties.setProperty("16D-P16D1S41", "HS-P3-192-CFH-1601");
            defaultProperties.setProperty("04D-P32D2S41", "OKR P10 3535 32X16-4S-V2.0");
            defaultProperties.setProperty("16D-P16D1S31", "P3-1415-16S-64X64-S31\nP3-1415-16S-64X64-S3.1(실외용)");
            defaultProperties.setProperty("01D-P64D4S21-4^8^12^16", "VL160T110-1\nVL200T110-1\nVL240T110-1\nVL250F111-1\nVL300-T110-1\nVL300F110-1\nVL320T210-1");
            defaultProperties.setProperty("16D-P16D1S10-1", "VS040T110-0\nVS048T110-0\nVS064T110-0\nVS064T110-0(CE3.00)\nVS096T110-0\nVS128T110-0");
        }
        else {
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
            defaultProperties.setProperty("PROGRAM_LANGUAGE", "한국어");

            defaultProperties.setProperty("dbNetIP", "192.168.0.201");
            defaultProperties.setProperty("dbNetPort", "5000");
            defaultProperties.setProperty("dbNetGateway", "192.168.0.1");
            defaultProperties.setProperty("dbNetSubnet", "255.255.255.0");

            defaultProperties.setProperty("fontGroup1FontPath1", "ENG 08x16-DABIT(표준).fnt");
            defaultProperties.setProperty("fontGroup1FontType1", "영어");
            defaultProperties.setProperty("fontGroup1FontPath2", "KOR 16x16-DABIT(표준).fnt");
            defaultProperties.setProperty("fontGroup1FontType2", "한글조합형");
            defaultProperties.setProperty("fontGroup1FontPath3", "USER 16x16-Special(표준).fnt");
            defaultProperties.setProperty("fontGroup1FontType3", "사용자폰트");

            defaultProperties.setProperty("fontGroup2FontPath1", "ENG 08x16-DABIT(표준).fnt");
            defaultProperties.setProperty("fontGroup2FontType1", "영어");
            defaultProperties.setProperty("fontGroup2FontPath2", "KOR 16x16-DABIT(표준).fnt");
            defaultProperties.setProperty("fontGroup2FontType2", "한글조합형");
            defaultProperties.setProperty("fontGroup2FontPath3", "USER 16x16-Special(표준).fnt");
            defaultProperties.setProperty("fontGroup2FontType3", "사용자폰트");

            defaultProperties.setProperty("fontGroup1selected", "True");
            defaultProperties.setProperty("fontGroup2selected", "True");
            defaultProperties.setProperty("fontGroup3selected", "False");
            defaultProperties.setProperty("fontGroup4selected", "False");

            defaultProperties.setProperty("isHexRealTime", "0");

            for (int i = 0; i <= 10; i++) {//페이지 개수(0은 실시간)
                for (int j =0; j < 3; j++) {//섹션 개수
                    defaultProperties.setProperty("displayControl"+i+j, "On");
                    defaultProperties.setProperty("displayMethod"+i+j, "Clear");
                    defaultProperties.setProperty("charCode"+i+j, "한글 조합형");
                    defaultProperties.setProperty("fontSize"+i+j, "16(Standard)");
                    defaultProperties.setProperty("fontGroup"+i+j, "폰트그룹1");
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

            defaultProperties.setProperty("displayControlDefault", "On");
            defaultProperties.setProperty("displayMethodDefault", "Clear");
            defaultProperties.setProperty("charCodeDefault", "한글 조합형");
            defaultProperties.setProperty("fontSizeDefault", "16(Standard)");
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

            defaultProperties.setProperty("isMQTTRealTime", "0");
        for (int i = 0; i <= 10; i++) {//페이지 개수(0은 실시간)
            for (int j =0; j < 3; j++) {//섹션 개수
                defaultProperties.setProperty("MQTTdisplayControl"+i+j, "On");
                defaultProperties.setProperty("MQTTdisplayMethod"+i+j, "Clear");
                defaultProperties.setProperty("MQTTcharCode"+i+j, "한글 조합형");
                defaultProperties.setProperty("MQTTfontSize"+i+j, "16(Standard)");
                defaultProperties.setProperty("MQTTfontGroup"+i+j, "폰트그룹1");
                defaultProperties.setProperty("MQTTeffectIn"+i+j, "정지효과");
                defaultProperties.setProperty("MQTTeffectInDirection"+i+j, "방향없음");
                defaultProperties.setProperty("MQTTeffectOut"+i+j, "사용안함");
                defaultProperties.setProperty("MQTTeffectOutDirection"+i+j, "사용안함");
                defaultProperties.setProperty("MQTTeffectSpeed"+i+j, "5");
                defaultProperties.setProperty("MQTTeffectTime"+i+j, "2초");
                defaultProperties.setProperty("MQTTxStart"+i+j, "0");
                defaultProperties.setProperty("MQTTxEnd"+i+j, "0");
                defaultProperties.setProperty("MQTTyStart"+i+j, "0");
                defaultProperties.setProperty("MQTTyEnd"+i+j, "0");
                defaultProperties.setProperty("MQTTbgImg"+i+j, "사용안함");
                defaultProperties.setProperty("MQTTtextColor"+i+j, "1");
                defaultProperties.setProperty("MQTTbgColor"+i+j, "0");
                if (i==0) {
                    defaultProperties.setProperty("MQTTtext"+i+j, "realTime 메세지 "+j);
                }
                else {
                    defaultProperties.setProperty("MQTTtext"+i+j, "page "+i+"-section "+j);
                }
            }
        }

        }

        File configFile = new File(filePath);
        // 디렉토리 생성
        File parentDir = configFile.getParentFile();
        if (!parentDir.exists()) parentDir.mkdirs();  // 디렉토리가 없을 경우 생성

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();  // 파일이 없을 경우 생성
                // 기본 설정 값 저장
                try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
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
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFilePath), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
        }
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(displayFilePath), StandardCharsets.UTF_8)) {
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