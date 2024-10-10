package dbps.dbps.service;

import java.io.*;
import java.util.Properties;

import static dbps.dbps.service.DisplaySignal.SignalMap_ASC;
import static dbps.dbps.Constants.*;

public class ConfigService {
    private static ConfigService instance;
    private final Properties properties;
    private final Properties displayProperties;
    private final String configFilePath;
    private final String displayFilePath;

    private ConfigService() {
        configFilePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
        displayFilePath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "display.properties";
        properties = new Properties();
        displayProperties = new Properties();
        createFileIfNotExists(configFilePath, "config");
        createFileIfNotExists(displayFilePath, "display");
        loadProperties();

        IS_ASCII = Boolean.parseBoolean(getProperty("IS_ASCII"));
        CONNECT_TYPE = getProperty("connectType");
        OPEN_PORT_NAME = getProperty("openPortName");
        SERIAL_BAUDRATE = Integer.parseInt(getProperty("serialSpeed"));
        RS485_ADDR_NUM = getProperty("RS485_ADDR_NUM");
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
    }

    private void createFileIfNotExists(String filePath, String fileName) {
        Properties defaultProperties = new Properties();
        if (fileName.equals("config")){
            for (int i = 1; i < 10; i++) {
                if (i==1){
                    defaultProperties.setProperty("ASCMsg"+i, "![000Hello world!]");
                } else if (i==2) {
                    defaultProperties.setProperty("ASCMsg"+i, "![000/P000/C1Hello!]");
                } else if (i == 3) {
                    defaultProperties.setProperty("ASCMsg"+i, "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]");
                }
                defaultProperties.setProperty("ASCMsg"+i, "");
            }

            defaultProperties.setProperty("IS_ASCII", "true");
            defaultProperties.setProperty("connectType", "serial");
            defaultProperties.setProperty("serialSpeed", "115200");
            defaultProperties.setProperty("isRS", "false");
            defaultProperties.setProperty("RS485Num", "00");
            defaultProperties.setProperty("clientTCPAddr", "192.168.0.10");
            defaultProperties.setProperty("clientTCPPort", "5100");
            defaultProperties.setProperty("serverTCPAddr", "192.168.0.10");
            defaultProperties.setProperty("serverTCPPort", "5000");
            defaultProperties.setProperty("UDPPort", "5109");
            defaultProperties.setProperty("UDPAddr", "192.168.0.10");
            defaultProperties.setProperty("RESPONSE_LATENCY", "3");
            defaultProperties.setProperty("latency", "3");
            defaultProperties.setProperty("lastDisplaySignal", "16D-P16D1S11");

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

            for (int i = 0; i <= 10; i++) {//페이지 개수(0은 실시간)
                for (int j = 1; j < 3; j++) {//섹션 개수
                    defaultProperties.setProperty("displayControl"+i+j, "ON");
                    defaultProperties.setProperty("displayMethod"+i+j, "Normal");
                    defaultProperties.setProperty("charCode"+i+j, "KS완성형 한글코드");
                    defaultProperties.setProperty("fontSize"+i+j, "16(Standard)");
                    defaultProperties.setProperty("fontGroup"+i+j, "FontGroup1");
                    defaultProperties.setProperty("effectIn"+i+j, "정지효과");
                    defaultProperties.setProperty("effectInDirection"+i+j, "방향없음");
                    defaultProperties.setProperty("effectOut"+i+j, "사용안함");
                    defaultProperties.setProperty("effectOutDirection"+i+j, "사용안함");
                    defaultProperties.setProperty("effectSpeed"+i+j, "5");
                    defaultProperties.setProperty("effectTime"+i+j, "2");
                    defaultProperties.setProperty("xStart"+i+j, "0");
                    defaultProperties.setProperty("xEnd"+i+j, "0");
                    defaultProperties.setProperty("yStart"+i+j, "0");
                    defaultProperties.setProperty("yEnd"+i+j, "0");
                    defaultProperties.setProperty("bgImg"+i+j, "사용안함");
                    defaultProperties.setProperty("textColor"+i+j, "1");
                    defaultProperties.setProperty("bgColor"+i+j, "2");
                    if (i==0) {
                        defaultProperties.setProperty("text", "realTime 메세지");
                    }
                    else {
                        defaultProperties.setProperty("text", "page "+i+"/section "+j);
                    }
                }
            }

            defaultProperties.setProperty("displayControlDefault", "ON");
            defaultProperties.setProperty("displayMethodDefault", "Normal");
            defaultProperties.setProperty("charCodeDefault", "KS완성형 한글코드");
            defaultProperties.setProperty("fontSizeDefault", "16(Standard)");
            defaultProperties.setProperty("fontGroupDefault", "FontGroup1");
            defaultProperties.setProperty("effectInDefault", "정지효과");
            defaultProperties.setProperty("effectInDirectionDefault", "방향없음");
            defaultProperties.setProperty("effectOutDefault", "사용안함");
            defaultProperties.setProperty("effectOutDirectionDefault", "사용안함");
            defaultProperties.setProperty("effectSpeedDefault", "5");
            defaultProperties.setProperty("effectTimeDefault", "2");
            defaultProperties.setProperty("xStartDefault", "0");
            defaultProperties.setProperty("xEndDefault", "0");
            defaultProperties.setProperty("yStartDefault", "0");
            defaultProperties.setProperty("yEndDefault", "0");
            defaultProperties.setProperty("bgImgDefault", "사용안함");
            defaultProperties.setProperty("textColorDefault", "1");
            defaultProperties.setProperty("bgColorDefault", "2");
            defaultProperties.setProperty("text", "![0032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]");

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
        }
        else if (fileName.equals("display")){
            for (String key : SignalMap_ASC.keySet()) {
                if (key.equals("32D-P16D1S11")){
                    defaultProperties.setProperty(key, "DABIT_P3_4R4C_16S_900cd\nP2.5-2121-64X64-32S-8H-M1.1(BRG)");
                } else if (key.equals("16D-P16D1S21")) {
                    defaultProperties.setProperty(key, "DABIT_P3_1R4C_16S_900cd");
                } else if (key.equals("16D-P16D1S11")) {
                    defaultProperties.setProperty(key, "DABIT_P3_2R6C_16S_2700cd\nDABIT_P4_2R2C_16S_900cd\nDABIT_P4_2R2C_16S_2200cd ");
                } else if (key.equals("08D-P32D1S31")) {
                    defaultProperties.setProperty(key, "DABIT_P4_2R4C_8S_6000cd");
                } else if (key.equals("08D-P16D2S11-1^9")) {
                    defaultProperties.setProperty(key, "DABIT_P6_1R2C_8S_900cd\nDABIT_P6_1R2C_8S_6000cd(GBR)\nVS128T110(GBR)\n04N008P2H1200A1\nVS128F111-8D (GBR)\nVS064F111-8\nVS096F111-8");
                } else if (key.equals("08D-P16D2S31-9^1")) {
                    defaultProperties.setProperty(key, "DABIT_P6_1R2C_8S_6000cd\n08D-P16D2S31-9^1");
                } else if (key.equals("08D-P64D1S21")) {
                    defaultProperties.setProperty(key, "DABIT_P6_2R2C_8S_6500cd(BGR)");
                } else if (key.equals("08D-P64D1S61")) {
                    defaultProperties.setProperty(key, "DABIT_P6_2R2C_8S_6500cd\nP6-1921-32x32-8S-GC-M1");
                } else if (key.equals("04D-P32D2S61")) {
                    defaultProperties.setProperty(key, "DABIT_P8_1R2C_4S_6500cd\nECO_T10_1R2C_4S_8000cd\nL800-32X16-4S-V3.0(L800)\n户外P8-4 16*32-V4.1\nYS-P10-320x160-4S-2735-V2");
                } else if (key.equals("04D-P32D2S51")) {
                    defaultProperties.setProperty(key, "DABIT_P10_1R2C_4S_4500cd(BGR)");
                } else if (key.equals("04D-P32D2S71")) {
                    defaultProperties.setProperty(key, "DABIT_P10_1R2C_4S_4500cd");
                } else if (key.equals("16D-P16D1S31")) {
                    defaultProperties.setProperty(key, "P3-1415-16S-64X64-S31\nP3-1415-16S-64X64-S3.1(실외용)");
                } else if (key.equals("16D-P16D1S10-1")) {
                    defaultProperties.setProperty(key, "VS064T110-0(CE3.00)\nVS040T110-0\nVS048T110-0\nVS064T110-0\nVS096T110-0\nVS128T110-0");
                } else if (key.equals("01D-P64D4S21-4^8^12^16")) {
                    defaultProperties.setProperty(key, "VL300-T110-1\nVL250F111-1\nVL160T110-1\nVL200T110-1\nVL240T110-1\nVL300F110-1\nVL320T210-1");
                } else if (key.equals("04D-P16D4S11-1^5^9^13")) {
                    defaultProperties.setProperty(key, "GMSFCM4_240_111 GP22LED_81210");
                } else if (key.equals("04D-P32D2S41")) {
                    defaultProperties.setProperty(key, "OKR P10 3535 32X16-4S-V2.0");
                } else if (key.equals("16D-P16D1S41")){
                    defaultProperties.setProperty(key, "HS-P3-192-CFH-1601");
                } else if (key.equals("08D-P16D2S21")) {
                    defaultProperties.setProperty(key, "04N008P2H1200A1");
                } else if (key.equals("08D-P16D2S10-1^9")) {
                    defaultProperties.setProperty(key, "VL128TR-08S(단종)\nVS128T110-8(옥외용)\nVL200T110-8\nVL200T210-8(일본)\nVL240T110-8");
                } else defaultProperties.setProperty(key, "");
            }
        }
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
                try (FileWriter writer = new FileWriter(configFile)) {
                    defaultProperties.store(writer, "Default configuration");
                }
                System.out.println("Created default configuration file at: " + filePath);
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
        try (FileWriter writer = new FileWriter(displayFilePath)) {
            displayProperties.store(writer, null);
        } catch (IOException e) {
        }
    }

    private void saveProperties() {
        try (FileWriter writer = new FileWriter(configFilePath)) {
            properties.store(writer, null);
        } catch (IOException e) {
        }
    }
}
