package dbps.dbps;

import com.fazecast.jSerialComm.SerialPort;
import java.io.*;

import static dbps.dbps.Constants.*;

public class tmp_firmware {

    public class CRC16 {
        // CRC 테이블
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
    }

    SerialPort port;
    private byte[] firmwareData;
    private int packetSize = 1024;
    private int totalPackets;
    private OutputStream outputStream;

    // 시리얼 포트 열기 및 설정 (추가해야 할 부분)
    public void openSerialPort() throws IOException {
        // 시리얼 포트 설정
        port = SerialPort.getCommPort("COM1");
        port.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 3000, 3000);
        port.openPort();
        outputStream = new BufferedOutputStream(port.getOutputStream());
    }

    public void closeSerialPort() throws IOException {
        if (outputStream != null) {
            outputStream.close();
        }
        port.closePort();
    }

    // 데이터를 패킷 단위로 전송하는 메소드
    public static boolean sendFontData() {
        boolean connect = false;
        int totBlockCnt = 0;
        int[][] fontBlockCnt = new int[4][3];
        int totPacketCnt = 0;
        int packetCnt = 0;
        String fileName = "";
        int dataSize = 1024;
        FileInputStream file = null;
        byte[] nData = null;

        int[] fontKindBlockCnt = new int[8];
        int iCodeIndex = 0;
        int iFontCount = 1;

        // Assuming cbFont2, cbFont3, cbFont4 are checkboxes (or similar logic)
//        if (cbFont2.isChecked()) iFontCount++;
//        if (cbFont3.isChecked()) iFontCount++;
//        if (cbFont4.isChecked()) iFontCount++;
//
//        try {
//            int topSize = 0x7f;
//            for (int j = 0; j < iFontCount; j++) {
//                for (int i = 0; i < 3; i++) {
//                    String comboBoxValue = findComponent("cbFontKind" + (j + 1) + (i + 1));
//                    iCodeIndex = findCodeIndex(comboBoxValue);
//                    if (iCodeIndex == 6) {
//                        String editValue = findComponent("eFont" + (j + 1) + (i + 1));
//                        fileName = editValue;
//                        File fileObj = new File(fileName);
//                        if (fileObj.exists()) {
//                            file = new FileInputStream(fileName);
//                            int fileSize = file.available() - 16;
//                            fileSize = fileSize / 32;
//                            if (fileSize > topSize) {
//                                topSize = fileSize;
//                                fontKindAddr[1][6] = 0xe000 + fileSize;
//                            }
//                            if (file != null) {
//                                file.close();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Processing font block counts
//        for (int i = 1; i < 8; i++) {
//            if (i == 1) {
//                fontKindBlockCnt[i] = ((fontKindAddr[1][i] - fontKindAddr[0][i]) + 1) * 16;
//            } else {
//                fontKindBlockCnt[i] = ((fontKindAddr[1][i] - fontKindAddr[0][i]) + 1) * 32;
//            }
//            if (fontKindBlockCnt[i] % dataSize == 0) {
//                fontKindBlockCnt[i] = fontKindBlockCnt[i] / dataSize;
//            } else {
//                fontKindBlockCnt[i] = fontKindBlockCnt[i] / dataSize + 1;
//            }
//        }

        // Remaining logic: handling file transmission, packet processing, error handling
        // ...

        return connect;
    }

    public static void main(String[] args) {
        tmp_firmware transfer = new tmp_firmware();
        try {
            transfer.openSerialPort(); // 실제 시리얼 포트를 열어야 합니다.
            transfer.sendFontData();  // 데이터 전송
            transfer.closeSerialPort(); // 시리얼 포트 닫기
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//41.24초