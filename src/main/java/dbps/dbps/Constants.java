package dbps.dbps;

public class Constants {
    //현재 연결 방법(serial, tcp, udp, RS485, WiFi, Bluetooth)
    public static String CONNECT_TYPE = "none";

    public static byte[] CONNECT_START = {
            (byte) 0x10, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x0B,
            (byte) 0x6A, (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33,
            (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38,
            (byte) 0x39, (byte) 0x10, (byte) 0x03
    };

    public static int RESPONSE_LATENCY = 3;

    public static boolean IS_ASCII = false;

    public static int SERIAL_BAUDRATE = 115200;

    public static String OPEN_PORT_NAME = null;

    public static int RS485_ADDR_NUM = 0;

    public static String CLIENT_TCP_IP = "";

    public static int CLIENT_TCP_PORT = 0;

    public static String UDP_IP = "";

    public static int UDP_PORT = 0;

    public static int SIZE_ROW = 0;
    public static int SIZE_COLUMN = 0;
    public static int BITS_PER_PIXEL = 0;



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
}
