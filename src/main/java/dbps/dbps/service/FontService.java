package dbps.dbps.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class FontService {
    private static FontService instance = null;
    private final LogService logService;
    HexMsgTransceiver hexMsgTransceiver;
    //사용안함, 영어, 유니코드 완성, 유니코드 일본어, 유니코드 중국어, 한글조합형, 사용자 폰트, 유니코드 전체
    private int[][] fontKindAddr = {{0, 0, 0xac00, 0x3040, 0x4e00, 0x8861, 0xe000, 0}, {0, 0x7f, 0xd7a3, 0x30ff, 0x9fff, 0xd3bd, 0xe07f, 0xd7a3}};

    private FontService(){
        logService = LogService.getLogService();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    public static FontService getInstance(){
        if(instance == null){
            instance = new FontService();
        }
        return instance;
    }
    
    public void sendFont(String[] fontGroup1, String[] fontGroup2, String[] fontGroup3, String[] fontGroup4, String[] fontSize, String[] fontType) throws InterruptedException {
        int packetSize = 1024;
        int totalPackets = 0;
        byte[] combinedData = null;

        StringBuilder finalPacket = new StringBuilder("4D ");
        
        int groupNum = 1;
        if (fontGroup2 != null){
            groupNum++;
        }
        if (fontGroup3 != null){
            groupNum++;
        }
        if (fontGroup4 != null){
            groupNum++;
        }
        
        finalPacket.append("0").append(groupNum).append(" ");

        hexMsgTransceiver.sendMessages("10 02 00 00 02 45 00 10 03");

        for (int i = 0; i < groupNum; i++) {
            //몇번째 그룹인지 체크
            String[] now;
            if(i==0){
                now = fontGroup1;
            } else if (i == 1) {
                now = fontGroup2;
            } else if (i == 2) {
                now = fontGroup3;
            } else {
                now = fontGroup4;
            }
            StringBuilder groupPacket = new StringBuilder();
            byte[] groupCombinedData = null;
            int groupPacketCnt = 0;
            for (int j = 0; j < 3; j++) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                if (now[j]!=null){
                    byte[] fontData;
                    try {
                        InputStream fontFile = new FileInputStream(now[j]);
                        fontData = fontFile.readAllBytes();

                        fontData = Arrays.copyOf(fontData, fontData.length - 16);
                        //pos start end w h 순서로 추가
                        //pos
                        groupPacketCnt+=Math.ceil(fontData.length/1024.0);
                        ByteBuffer tmp = ByteBuffer.allocate(2);
                        tmp.order(ByteOrder.LITTLE_ENDIAN);
                        tmp.putShort((short)groupPacketCnt);
                        totalPackets+=groupPacketCnt;

                        byte[] tmpArray = tmp.array();
                        for (int k = 0; k < 2; k++) {
                            groupPacket.append(String.format("%02X ", tmpArray[k]));
                        }
                        //start
                        if (j==0&&i==0){
                            groupPacket.append("00 00 ");
                        }
                        else {
                            tmp.clear();
                            //사용안함, 영어, 유니코드 완성, 유니코드 일본어, 유니코드 중국어, 한글조합형, 사용자 폰트, 유니코드 전체
                            if (fontType[3*i+j].equals("영어(ASCII)")){
                                tmp.putInt(fontKindAddr[0][1]);
                            } else if (fontType[3*i+j].equals("유니코드 완성형")) {
                                tmp.putInt(fontKindAddr[0][2]);
                            } else if (fontType[3*i+j].equals("유니코드 일본어")) {
                                tmp.putInt(fontKindAddr[0][3]);
                            } else if (fontType[3*i+j].equals("유니코드 중국어")) {
                                tmp.putInt(fontKindAddr[0][4]);
                            } else if (fontType[3*i+j].equals("한글조합형")) {
                                tmp.putInt(fontKindAddr[0][5]);
                            } else if (fontType[3 * i + j].equals("사용자폰트")) {
                                tmp.putInt(fontKindAddr[0][6]);
                            } else if (fontType[3*i+j].equals("유니코드 전체")) {
                                tmp.putInt(fontKindAddr[0][7]);
                            } else{
                                tmp.putInt(fontKindAddr[0][0]);
                            }
                            for (int k = 0; k < 2; k++) {
                                groupPacket.append(String.format("%02X ", tmpArray[k]));
                            }
                        }

                        //end
                        if (j==0&&i==0){
                            groupPacket.append("FF 00 ");
                        }
                        else {
                            tmp.clear();
                            if (fontType[3*i+j].equals("영어(ASCII)")){
                                tmp.putInt(fontKindAddr[1][1]);
                            } else if (fontType[3*i+j].equals("유니코드 완성형")) {
                                tmp.putInt(fontKindAddr[1][2]);
                            } else if (fontType[3*i+j].equals("유니코드 일본어")) {
                                tmp.putInt(fontKindAddr[1][3]);
                            } else if (fontType[3*i+j].equals("유니코드 중국어")) {
                                tmp.putInt(fontKindAddr[1][4]);
                            } else if (fontType[3*i+j].equals("한글조합형")) {
                                tmp.putInt(fontKindAddr[1][5]);
                            } else if (fontType[3 * i + j].equals("사용자폰트")) {
                                tmp.putInt(fontKindAddr[1][6]);
                            } else if (fontType[3*i+j].equals("유니코드 전체")) {
                                tmp.putInt(fontKindAddr[1][7]);
                            } else{
                                tmp.putInt(fontKindAddr[1][0]);
                            }
                            for (int k = 0; k < 2; k++) {
                                groupPacket.append(String.format("%02X ", tmpArray[k]));
                            }
                        }

                        //w&h
                        //사이즈따라서 groupPacket에 추가
                        String size = extractTwoCharsAroundX(now[j], 'x');
                        String width = size.substring(0, 2);
                        String height = size.substring(3, 5);
                        groupPacket.append(String.format("%02X ", Integer.parseInt(width)));
                        groupPacket.append(String.format("%02X ", Integer.parseInt(height)));

                        outputStream.write(fontData);
                        outputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                combinedData = outputStream.toByteArray();
            }

            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(groupPacketCnt*1024);
            byte[] byteArray = buffer.array();

            for (int j = 0; j < byteArray.length; j++) {
                finalPacket.append(String.format("%02X ", byteArray[j]));
            }
            finalPacket.append(groupPacket);
        }

        for (int i = 0; i < totalPackets; i++) {
            int currentPacketSize;
            if (i == totalPackets - 1) {
                if (combinedData.length % packetSize==0){
                    currentPacketSize = packetSize;
                }
                else currentPacketSize = combinedData.length % packetSize;
            } else {
                currentPacketSize = packetSize;
            }

            byte[] sendPacket = new byte[currentPacketSize+17];

            sendPacket[0] = 0X10;
            sendPacket[1] = 0X02;
            sendPacket[2] = 0X00;
            sendPacket[3] = 0X04;
            sendPacket[4] = 0X0A;
            sendPacket[5] = (byte) 0X98;

            sendPacket[6] = (byte) totalPackets;
            sendPacket[7] = (byte) (totalPackets >> 8);
            sendPacket[8] = (byte) i;
            sendPacket[9] = (byte) (i>>8);
            sendPacket[10] = (byte) totalPackets;
            sendPacket[11] = (byte) (totalPackets >> 8);
            sendPacket[12] = (byte) i;
            sendPacket[13] = (byte) (i>>8);
            sendPacket[14] = 0X01;

            if (i == totalPackets - 1){
                System.arraycopy(combinedData, i*packetSize+16, sendPacket, 15, currentPacketSize-16);
            }
            else{
                System.out.println("i = " + i);
                System.out.println("currentPacketSize = " + currentPacketSize);
                System.out.println("sendPacket.length = " + sendPacket.length);
                System.out.println("combinedData = " + combinedData.length);
                System.arraycopy(combinedData, i*packetSize+16, sendPacket, 15, currentPacketSize);
            }

            sendPacket[sendPacket.length-2] = 0x10;
            sendPacket[sendPacket.length-1] = 0x03;

            System.out.print("send : [");
            for (int j = 0; j < sendPacket.length; j++) {
                System.out.printf("%02X ", sendPacket[j]);
            }
            System.out.println("]");
            hexMsgTransceiver.sendByteMessages(sendPacket);
            Thread.sleep(100);
        }

        //앞뒤로 붙이는거 추가
        hexMsgTransceiver.sendMessages(finalPacket.toString());
        System.out.println(finalPacket);



        hexMsgTransceiver.sendMessages("10 02 00 00 02 45 01 10 03");
    }

    public static String extractTwoCharsAroundX(String input, char target) {
        int index = input.indexOf(target); // 'x'의 인덱스를 찾기
        if (index == -1 || index < 2 || index > input.length() - 3) {
            return "Invalid Position"; // 'x'가 없거나 앞뒤에 두 글자가 없는 경우 처리
        }

        // 'x' 앞뒤 두 글자씩 추출
        return input.substring(index - 2, index + 3);
    }

}
