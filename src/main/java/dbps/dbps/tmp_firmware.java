package dbps.dbps;

import com.fazecast.jSerialComm.SerialPort;
import java.io.*;
import java.util.Arrays;

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
    private byte[] fontData1;
    private byte[] fontData2;
    private int packetSize = 1024;
    private int totalPackets;
    private OutputStream outputStream;
    FontData fontData = new FontData(); // 객체 생성 시 모든 값은 기본적으로 0으로 초기화됩니다.

    // 시리얼 포트 열기 및 설정 (추가해야 할 부분)
    public void openSerialPort() throws IOException {
        // 시리얼 포트 설정
        port = SerialPort.getCommPort("COM3");
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
    public void sendFontData() throws IOException, InterruptedException {
        InputStream font = getClass().getResourceAsStream("/dbps/dbps/ENG 08x16-DABIT(표준).fnt");
        fontData1 = font.readAllBytes();

        font = getClass().getResourceAsStream("/dbps/dbps/KOR 16x16-DABIT(표준).fnt");
//        fontData2 = font.readAllBytes();
        fontData1 = Arrays.copyOf(fontData1, fontData1.length - 16);
//        fontData2 = Arrays.copyOf(fontData2, fontData2.length - 16);

//        byte[] combinedFontData = new byte[fontData1.length + fontData2.length];

//        System.arraycopy(fontData1, 0, combinedFontData, 0, fontData1.length);

        // 두 번째 배열을 첫 번째 배열 뒤에 복사
//        System.arraycopy(fontData2, 0, combinedFontData, fontData1.length, fontData2.length);


        int packetSize = 1024;
        int totalPackets = (fontData1.length + packetSize - 1) / packetSize;

        String portName = "COM3";
        SerialPort port = SerialPort.getCommPort(portName);
        port.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 3000, 3000);
        port.openPort();
        OutputStream outputStream = new BufferedOutputStream(port.getOutputStream());
        InputStream inputStream = new BufferedInputStream(port.getInputStream());

        outputStream.write("![00210!]".getBytes());
        outputStream.flush();

        for (int i = 0; i < totalPackets; i++) {
            int currentPacketSize;
            if (i == totalPackets - 1) {
                if (fontData1.length % packetSize==0){
                    currentPacketSize = packetSize;
                }
                else currentPacketSize = fontData1.length % packetSize;
            } else {
                currentPacketSize = packetSize;
            }
            byte[] packet = new byte[currentPacketSize+17];

//            10 02 00 04 0A 98
            packet[0] = 0X10;
            packet[1] = 0X02;
            packet[2] = 0X00;
            packet[3] = 0X04;
            packet[4] = 0X0A;
            packet[5] = (byte) 0X98;

            packet[6] = (byte) totalPackets;
            packet[7] = (byte) (totalPackets >> 8);
            packet[8] = (byte) i;
            packet[9] = (byte) (i>>8);
            packet[10] = (byte) totalPackets;
            packet[11] = (byte) (totalPackets >> 8);
            packet[12] = (byte) i;
            packet[13] = (byte) (i>>8);
            packet[14] = 0X01;


            System.out.println("currentPacketSize = " + currentPacketSize);
            if (i == totalPackets - 1){
                System.arraycopy(fontData1, i*packetSize+16, packet, 15, currentPacketSize-16);
            }
            else{
                System.arraycopy(fontData1, i*packetSize+16, packet, 15, currentPacketSize);
            }




            packet[packet.length-2] = 0x10;
            packet[packet.length-1] = 0x03;

            outputStream.write(packet);
            outputStream.flush();

            System.out.print("send : [");
            for (int j = 0; j < packet.length; j++) {
                System.out.printf("%02X ", packet[j]);
            }
            System.out.println("]");

            byte[] buffer = new byte[1024];
            int totalBytesRead = 0;

            // 데이터 수신을 기다리는 최대 시간 (예: 1000 밀리초)
            long timeout = RESPONSE_LATENCY * 1000;
            long startTime = System.currentTimeMillis();

            // 반복적으로 읽어 남아있는 데이터를 모두 수신
            while (System.currentTimeMillis() - startTime < timeout) {
                if (inputStream.available() > 0) {
                    int bytesRead = inputStream.read(buffer, totalBytesRead, buffer.length - totalBytesRead);
                    if (bytesRead > 0) {
                        totalBytesRead += bytesRead;
                        // 타임아웃 시간 갱신 (데이터 수신이 있으면 타이머 리셋)
                        startTime = System.currentTimeMillis();

                        if (dataReceivedIsCompleteHex(buffer, totalBytesRead)) {
                            break; // 데이터를 다 받았으면 루프 종료
                        }
                    }
                } else {
                    // 짧은 대기 시간 후 다시 읽기 시도 (바쁜 대기 방지)
                    Thread.sleep(50);
                }
            }

            Thread.sleep(300);
        }




        Thread.sleep(1000);

        // 폰트 개수 설정
//        fontData.fontCnt = 1; //폰트 그룹의 개수
//
//// 시작 주소와 끝 주소 설정
//        if(j == 0 && iCodeIndex == 1)
//        {
//            FontData.FontSData[j].FontAddr[i].EndAddr[0] = 0xff;
//            FontData.FontSData[j].FontAddr[i].EndAddr[1] = 0x00;
//            //FontKindBlockCnt[iCodeIndex] = 4096 / DataSize;
//            //FontBlockCnt[j][i] = FontKindBlockCnt[iCodeIndex];
//        }
//        else
//        {
//            fontData.fontSData[j].fontAddr[i].startAddr[0] = uSize[0];
//            fontData.fontSData[j].fontAddr[i].startAddr[1] = uSize[1];
//            fontData.fontSData[j].fontAddr[i].endAddr[0] = uSize[0];
//            fontData.fontSData[j].fontAddr[i].endAddr[1] = uSize[1];
//        }
//// 폭과 높이 설정
//        if (ComboBox.getSelectedIndex() == 2) {
//            fontData.fontSData[j].fontAddr[i].width = 32;
//            fontData.fontSData[j].fontAddr[i].height = 32;
//        } else {
//            fontData.fontSData[j].fontAddr[i].width = 16;
//            fontData.fontSData[j].fontAddr[i].height = 16;
//        }
//
//// 폰트 블록 위치 설정
//        fontData.fontSData[j].fontAddr[i].fontPos[0] = uSize[0];
//        fontData.fontSData[j].fontAddr[i].fontPos[1] = uSize[1];
//
//// 폰트 데이터의 총 바이트 수 설정
//        fontData.fontSData[j].fontByteCnt[0] = uSize[0];
//        fontData.fontSData[j].fontByteCnt[1] = uSize[1];
//        fontData.fontSData[j].fontByteCnt[2] = uSize[2];
//        fontData.fontSData[j].fontByteCnt[3] = uSize[3];
//
//// 데이터 전송 전 패킷에 데이터 복사
//        System.arraycopy(fontData, 0, sendFrameData, 0, tempSize);  // tempSize는 데이터의 크기



        byte[] bytes = hexStringToByteArray("10 02 00 00 3A 4D 01 00 10 00 00 00 00 00 00 FF 00 08 10 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 10 03");
        outputStream.write(bytes);
        outputStream.flush();

        Thread.sleep(1000);


        outputStream.write("![00211!]".getBytes());
        outputStream.flush();

    }

    private boolean dataReceivedIsCompleteHex(byte[] buffer, int length) {
        return length >= 2 && buffer[length - 2] == (byte) 0x10 && buffer[length - 1] == (byte) 0x03;
    }

    // FontData 클래스 정의
    class FontData {
        int fontCnt;
        FontSData[] fontSData = new FontSData[4];

        // FontSData 클래스 정의
        class FontSData {
            FontAddr[] fontAddr = new FontAddr[3];
            byte[] fontByteCnt = new byte[4];

            // FontAddr 클래스 정의
            class FontAddr {
                byte[] startAddr = new byte[2];
                byte[] endAddr = new byte[2];
                int width;
                int height;
                byte[] fontPos = new byte[2];
            }

            FontSData() {
                for (int i = 0; i < fontAddr.length; i++) {
                    fontAddr[i] = new FontAddr();
                }
            }
        }

        FontData() {
            for (int i = 0; i < fontSData.length; i++) {
                fontSData[i] = new FontSData();
            }
        }
    }

    // 초기화 부분 (ZeroMemory 대체)

    public static void main(String[] args) {
        tmp_firmware transfer = new tmp_firmware();
        try {
            transfer.sendFontData();  // 데이터 전송
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
//41.24초