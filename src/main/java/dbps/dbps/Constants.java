package dbps.dbps;

import dbps.dbps.service.ConfigService;
import dbps.dbps.service.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Constants {
    //현재 연결 방법(serial, tcp, udp, RS485, WiFi, Bluetooth)
    public static String CONNECT_TYPE = "none";
    ConfigService configService;

    public static byte[] CONNECT_START = {
            (byte) 0x10, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x0B,
            (byte) 0x6A, (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33,
            (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38,
            (byte) 0x39, (byte) 0x10, (byte) 0x03
    };
    @FXML
    public void initialize() {
        configService = ConfigService.getInstance();
    }

    public static int RESPONSE_LATENCY = 3;

    public static boolean IS_ASCII = false;

    public static int serverTCPPort = 5000;

    public static String hostIP;

    public static boolean ascUTF16 = false;

    public static int SERIAL_BAUDRATE = 115200;

    public static String OPEN_PORT_NAME = null;

    public static int RS485_ADDR_NUM = 0;

    public static boolean isRS = false;

    public static String TCP_IP = "";

    public static int TCP_PORT = 0;

    public static String UDP_IP = "";

    public static int UDP_PORT = 0;

    public static boolean isBT = false;

    public static int SIZE_ROW = 0;
    public static int SIZE_COLUMN = 0;
    public static int BITS_PER_PIXEL = 0;
    public static String howToArrange = "가로형";
    public static String uploadFirmwarePath = "";

    private static final int[] wCRCTable = {
            0X0000, 0XC0C1, 0XC181, 0X0140, 0XC301, 0X03C0, 0X0280, 0XC241,
            0XC601, 0X06C0, 0X0780, 0XC741, 0X0500, 0XC5C1, 0XC481, 0X0440,
            0XCC01, 0X0CC0, 0X0D80, 0XCD41, 0X0F00, 0XCFC1, 0XCE81, 0X0E40,
            0X0A00, 0XCAC1, 0XCB81, 0X0B40, 0XC901, 0X09C0, 0X0880, 0XC841,
            0XD801, 0X18C0, 0X1980, 0XD941, 0X1B00, 0XDBC1, 0XDA81, 0X1A40,
            0X1E00, 0XDEC1, 0XDF81, 0X1F40, 0XDD01, 0X1DC0, 0X1C80, 0XDC41,
            0X1400, 0XD4C1, 0XD581, 0X1540, 0XD701, 0X17C0, 0X1680, 0XD641,
            0XD201, 0X12C0, 0X1380, 0XD341, 0X1100, 0XD1C1, 0XD081, 0X1040,
            0XF001, 0X30C0, 0X3180, 0XF141, 0X3300, 0XF3C1, 0XF281, 0X3240,
            0X3600, 0XF6C1, 0XF781, 0X3740, 0XF501, 0X35C0, 0X3480, 0XF441,
            0X3C00, 0XFCC1, 0XFD81, 0X3D40, 0XFF01, 0X3FC0, 0X3E80, 0XFE41,
            0XFA01, 0X3AC0, 0X3B80, 0XFB41, 0X3900, 0XF9C1, 0XF881, 0X3840,
            0X2800, 0XE8C1, 0XE981, 0X2940, 0XEB01, 0X2BC0, 0X2A80, 0XEA41,
            0XEE01, 0X2EC0, 0X2F80, 0XEF41, 0X2D00, 0XEDC1, 0XEC81, 0X2C40,
            0XE401, 0X24C0, 0X2580, 0XE541, 0X2700, 0XE7C1, 0XE681, 0X2640,
            0X2200, 0XE2C1, 0XE381, 0X2340, 0XE101, 0X21C0, 0X2080, 0XE041,
            0XA001, 0X60C0, 0X6180, 0XA141, 0X6300, 0XA3C1, 0XA281, 0X6240,
            0X6600, 0XA6C1, 0XA781, 0X6740, 0XA501, 0X65C0, 0X6480, 0XA441,
            0X6C00, 0XACC1, 0XAD81, 0X6D40, 0XAF01, 0X6FC0, 0X6E80, 0XAE41,
            0XAA01, 0X6AC0, 0X6B80, 0XAB41, 0X6900, 0XA9C1, 0XA881, 0X6840,
            0X7800, 0XB8C1, 0XB981, 0X7940, 0XBB01, 0X7BC0, 0X7A80, 0XBA41,
            0XBE01, 0X7EC0, 0X7F80, 0XBF41, 0X7D00, 0XBDC1, 0XBC81, 0X7C40,
            0XB401, 0X74C0, 0X7580, 0XB541, 0X7700, 0XB7C1, 0XB681, 0X7640,
            0X7200, 0XB2C1, 0XB381, 0X7340, 0XB101, 0X71C0, 0X7080, 0XB041,
            0X5000, 0X90C1, 0X9181, 0X5140, 0X9301, 0X53C0, 0X5280, 0X9241,
            0X9601, 0X56C0, 0X5780, 0X9741, 0X5500, 0X95C1, 0X9481, 0X5440,
            0X9C01, 0X5CC0, 0X5D80, 0X9D41, 0X5F00, 0X9FC1, 0X9E81, 0X5E40,
            0X5A00, 0X9AC1, 0X9B81, 0X5B40, 0X9901, 0X59C0, 0X5880, 0X9841,
            0X8801, 0X48C0, 0X4980, 0X8941, 0X4B00, 0X8BC1, 0X8A81, 0X4A40,
            0X4E00, 0X8EC1, 0X8F81, 0X4F40, 0X8D01, 0X4DC0, 0X4C80, 0X8C41,
            0X4400, 0X84C1, 0X8581, 0X4540, 0X8701, 0X47C0, 0X4680, 0X8641,
            0X8201, 0X42C0, 0X4380, 0X8341, 0X4100, 0X81C1, 0X8081, 0X4040
    };

    // CRC 계산 함수
    public static int calcCRC(byte[] buf1, byte[] buf2, int length1, int startIdx, int length2) {
        int crc = 0x0000;  // CRC 초기값

        // buf1에 대한 CRC 계산
        for (int i = 0; i < length1; i++) {
            int byteVal = buf1[i] & 0xFF;
            int tableIndex = (crc ^ byteVal) & 0xFF;
            crc = (crc >> 8) ^ wCRCTable[tableIndex];
        }

        // buf2 (firmwareData)의 startIdx부터 length2 크기만큼에 대한 CRC 계산
        for (int i = 0; i < length2; i++) {
            int byteVal = buf2[startIdx + i] & 0xFF;
            int tableIndex = (crc ^ byteVal) & 0xFF;
            crc = (crc >> 8) ^ wCRCTable[tableIndex];
        }

        return crc & 0xFFFF;
    }



    public static byte[] hexStringToByteArray(String hex) {
        String[] hexValues = hex.split(" ");
        byte[] byteArray = new byte[hexValues.length];

        for (int i = 0; i < hexValues.length; i++) {
            byteArray[i] = (byte) Integer.parseInt(hexValues[i], 16);
        }

        return byteArray;
    }

    public static String bytesToHex(byte[] bytes, int length) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            hexString.append(String.format("%02X ", bytes[i]));
        }

        return hexString.toString();
    }

    public static String convertRS485AddrASCii(){
        String[] arr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V"};

        return arr[RS485_ADDR_NUM];
    }

    public static boolean dataReceivedIsComplete(byte[] buffer, int length) {
        String data = new String(buffer, 0, length);
        if (data.contains("RX") && data.contains("![") && data.contains("!]")) {
            int indexTX = data.indexOf("TX");
            int indexStart = data.indexOf("![", indexTX); // "TX" 이후 검색
            int indexEnd = data.indexOf("!]", indexStart); // "![ 이후 검색

            // 순서가 올바른지 확인
            return indexTX != -1 && indexStart != -1 && indexEnd != -1 && indexTX < indexStart && indexStart < indexEnd;
        }

        if (data.contains("BT DIBD")&&data.contains("!]")){
            return true;
        }

        return length > 0 && buffer[length - 1] == (byte) ']' && buffer[length - 2] == (byte) '!';
    }
    public static boolean dataReceivedIsCompleteHex(byte[] buffer, int length) {
        String data = bytesToHex(buffer, length);
        if (data.contains("54 58 28") && data.contains("31 30 20 30 32") && data.contains("31 30 20 30 33")) {
            if (!data.startsWith("52 58 28")) {
                return false;
            }
            int indexTX = data.indexOf("54 58 28");
            int indexStart = data.indexOf("31 30 20 30 32", indexTX); // "TX" 이후 검색
            int indexEnd = data.indexOf("31 30 20 30 33", indexStart); // "10 02" 이후 검색
            // 순서가 올바른지 확인
            return indexTX != -1 && indexStart != -1 && indexEnd != -1 && indexTX < indexStart && indexStart < indexEnd;
        }

        return length > 0 && buffer[length - 1] == 0x03 && buffer[length - 2] == (byte) 0x10;
    }


    public static void openModal(String fxmlPath, String title, MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Simulator.class.getResource(fxmlPath));
        fxmlLoader.setResources(ResourceManager.getInstance().getBundle());
        Parent root = fxmlLoader.load();

        Stage modalStage = new Stage();
        modalStage.setTitle(title);
        modalStage.getIcons().add(new Image(Simulator.class.getResourceAsStream("/icon.jpg")));
        modalStage.initModality(Modality.APPLICATION_MODAL);

        Stage parentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        modalStage.initOwner(parentStage);

        Scene scene = new Scene(root);
        modalStage.setScene(scene);
        modalStage.setResizable(false);

        modalStage.setOnShown(event -> {
            // 부모 창 위치와 크기 가져오기
            double parentX = parentStage.getX();
            double parentY = parentStage.getY();
            double parentWidth = parentStage.getWidth();
            double parentHeight = parentStage.getHeight();

            // 모달 창 크기 계산
            double modalWidth = modalStage.getWidth();
            double modalHeight = modalStage.getHeight();

            // 위치 계산
            double modalX = parentX + (parentWidth / 2) - (modalWidth / 2); // 가로 중앙
            double modalY = parentY;

            // 위치 설정
            modalStage.setX(modalX);
            modalStage.setY(modalY);
        });

        modalStage.showAndWait();
    }
}
