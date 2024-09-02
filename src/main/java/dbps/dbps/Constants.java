package dbps.dbps;

public class Constants {
    //현재 연결 방법(serial, tcp, udp, RS485, WiFi, Bluetooth)
    public static String CONNECT_TYPE;

    public static byte[] CONNECT_START = {
            (byte) 0x10, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x0B,
            (byte) 0x6A, (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33,
            (byte) 0x34, (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38,
            (byte) 0x39, (byte) 0x10, (byte) 0x03
    };

    public static int responseLatency = 1000;

    public static boolean isAscii = false;







    public static byte[] hexStringToByteArray(String hex) {
        String[] hexValues = hex.split(" ");
        byte[] byteArray = new byte[hexValues.length];

        for (int i = 0; i < hexValues.length; i++) {
            byteArray[i] = (byte) Integer.parseInt(hexValues[i], 16);
        }

        return byteArray;
    }
}
