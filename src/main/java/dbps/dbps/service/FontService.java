package dbps.dbps.service;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

import static dbps.dbps.Constants.*;

public class FontService {
    private static FontService instance = null;
    HexMsgTransceiver hexMsgTransceiver;
    //사용안함, 영어, 유니코드 완성, 유니코드 일본어, 유니코드 중국어, 한글조합형, 사용자 폰트, 유니코드 전체
    private int[][] fontKindAddr = {{0, 0, 0xac00, 0x3040, 0x4e00, 0x8861, 0xe000, 0}, {0, 0x7f, 0xd7a3, 0x30ff, 0x9fff, 0xd3bd, 0xe07f, 0xd7a3}};

    private FontService(){
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    public static FontService getInstance(){
        if(instance == null){
            instance = new FontService();
        }
        return instance;
    }
    
    public Task<Void> sendFont(String[] fontGroup1, String[] fontGroup2, String[] fontGroup3, String[] fontGroup4, String[] fontType, ProgressBar progressBar, Label progressLabel) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                hexMsgTransceiver.sendMessages("10 02 00 00 02 45 00 10 03");
                Thread.sleep(300);

                int packetSize = 1024;
                int totalPackets = 0;
                byte[] combinedData;
                List<Byte> fontPackets = new ArrayList<>();

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

                Map<Integer, List<Byte>> groupFontPackets = new HashMap<>();
                byte[] finalPacket = new byte[28*Math.max(2, groupNum)+9];

                if (groupNum<=2){
                    finalPacket[4] = (byte) 0x3A;
                } else if (groupNum == 3) {
                    finalPacket[4] = (byte) 0x56;
                } else{
                    finalPacket[4] = (byte) 0x72;
                }

                finalPacket[5] = (byte) 0x4D;
                finalPacket[6] = (byte) groupNum;
                int finalIdx = 7;
                int[] groupPackets = new int[groupNum];

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
                    int groupPacketCnt = 0;
                    for (int j = 0; j < 3; j++) {
                        if (now[j]!=null){
                            byte[] fontData;
                            try {
                                InputStream fontFile = new FileInputStream(now[j]);
                                fontData = fontFile.readAllBytes();

                                fontData = Arrays.copyOf(fontData, fontData.length - 16);
                                String size = extractTwoCharsAroundX(now[j], 'x');
                                String width = size.substring(0, 2);
                                String height = size.substring(3, 5);
                                if (fontType[3*i+j].contains("유니코드")){
                                    int fontSize = Integer.parseInt(width) * Integer.parseInt(height) / 8;
                                    if (fontType[3*i+j].contains("완성형")){
                                        int startUnicode = 0xAC00-33;
                                        int endUnicode = 0xD7A3-33;

                                        int startIndex = (startUnicode) * fontSize;  // 유니코드의 시작 위치 계산
                                        int endIndex = (endUnicode + 1) * fontSize;  // 유니코드의 끝 위치 계산 (포함하려면 +1)

                                        // 유니코드 범위 내의 데이터만 복사
                                        fontData = Arrays.copyOfRange(fontData, startIndex, endIndex);
                                    } else if (fontType[3 * i + j].contains("일본어")) {
                                        int startUnicode = 0x3040-33;  // U+3040의 유니코드 값
                                        int endUnicode = 0x30FF-33;    // U+30FF의 유니코드 값

                                        int startIndex = (startUnicode) * fontSize;  // 유니코드의 시작 위치 계산
                                        int endIndex = (endUnicode + 1) * fontSize;  // 유니코드의 끝 위치 계산 (포함하려면 +1)

                                        // 유니코드 범위 내의 데이터만 복사
                                        fontData = Arrays.copyOfRange(fontData, startIndex, endIndex);
                                    } else if (fontType[3 * i + j].contains("중국어")){
                                        int startUnicode = 0x4E00-33;
                                        int endUnicode = 0x9FFF-33;

                                        int startIndex = (startUnicode) * fontSize;
                                        int endIndex = (endUnicode + 1) * fontSize;

                                        // 유니코드 범위 내의 데이터만 복사
                                        fontData = Arrays.copyOfRange(fontData, startIndex, endIndex);
                                    }
                                }
                                System.out.println(Math.ceil(fontData.length/1024.0));
                                //pos start end w h 순서로 추가
                                //pos
                                ByteBuffer tmp = ByteBuffer.allocate(2);
                                tmp.order(ByteOrder.LITTLE_ENDIAN);
                                tmp.putShort((short)groupPacketCnt);
                                groupPacketCnt += (int) Math.ceil(fontData.length/1024.0);
                                groupPackets[i] = groupPacketCnt;

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
                                        tmp.putShort((short)fontKindAddr[0][1]);
                                    } else if (fontType[3*i+j].equals("유니코드 완성형")) {
                                        tmp.putShort((short)fontKindAddr[0][2]);
                                    } else if (fontType[3*i+j].equals("유니코드 일본어")) {
                                        tmp.putShort((short)fontKindAddr[0][3]);
                                    } else if (fontType[3*i+j].equals("유니코드 중국어")) {
                                        tmp.putShort((short)fontKindAddr[0][4]);
                                    } else if (fontType[3*i+j].equals("한글조합형")) {
                                        tmp.putShort((short)fontKindAddr[0][5]);
                                    } else if (fontType[3 * i + j].equals("사용자폰트")) {
                                        tmp.putShort((short)fontKindAddr[0][6]);
                                    } else if (fontType[3*i+j].equals("유니코드 전체")) {
                                        tmp.putShort((short)fontKindAddr[0][7]);
                                    } else{
                                        tmp.putShort((short)fontKindAddr[0][0]);
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
                                        tmp.putShort((short)fontKindAddr[1][1]);
                                    } else if (fontType[3*i+j].equals("유니코드 완성형")) {
                                        tmp.putShort((short)fontKindAddr[1][2]);
                                    } else if (fontType[3*i+j].equals("유니코드 일본어")) {
                                        tmp.putShort((short)fontKindAddr[1][3]);
                                    } else if (fontType[3*i+j].equals("유니코드 중국어")) {
                                        tmp.putShort((short)fontKindAddr[1][4]);
                                    } else if (fontType[3*i+j].equals("한글조합형")) {
                                        tmp.putShort((short)fontKindAddr[1][5]);
                                    } else if (fontType[3 * i + j].equals("사용자폰트")) {
                                        tmp.putShort((short)fontKindAddr[1][6]);
                                    } else if (fontType[3*i+j].equals("유니코드 전체")) {
                                        tmp.putShort((short)fontKindAddr[1][7]);
                                    } else{
                                        tmp.putShort((short)fontKindAddr[1][0]);
                                    }
                                    for (int k = 0; k < 2; k++) {
                                        groupPacket.append(String.format("%02X ", tmpArray[k]));
                                    }
                                }


                                //w&h
                                //사이즈따라서 groupPacket에 추가

                                groupPacket.append(String.format("%02X ", Integer.parseInt(width)));
                                groupPacket.append(String.format("%02X ", Integer.parseInt(height)));

                                for (byte fontDatum : fontData) {
                                    fontPackets.add(fontDatum);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else{
                            for (int k = 0; k < 8; k++) {
                                groupPacket.append(String.format("%02X ", 0));
                            }
                        }

                    }
                    totalPackets+=groupPackets[i];
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    buffer.order(ByteOrder.LITTLE_ENDIAN);
                    buffer.putInt(groupPacketCnt*1024);

                    for (byte b : buffer.array()) {
                        finalPacket[finalIdx++] = b;
                    }
                    groupFontPackets.put(i, new ArrayList<>(fontPackets));
                    fontPackets.clear();

                    byte[] bytes = hexStringToByteArray(String.valueOf(groupPacket));

                    for (byte byteData : bytes) {
                        finalPacket[finalIdx++] = byteData;
                    }
                }
                int sentPacket = 0;
                int index = 0;
                int sendGroupNum = 0;
                //만약에 유니코드면 시작점 지정.
                //송신패킷 생성
                //groupFontPackets
                System.out.println(totalPackets);
                for (int i = 0; i < groupNum; i++) {
                    combinedData = new byte[groupFontPackets.get(i).size()];
                    for (Byte b : groupFontPackets.get(i)) {
                        combinedData[index++] = b;
                    }
                    index = 0;
                    for (int j = 0; j < groupPackets[i]; j++) {
                        byte[] sendPacket = new byte[1041];
                        int currentPacketSize = packetSize;
                        if (j == groupPackets[i] - 1) {
                            if (combinedData.length % packetSize!=0){
                                currentPacketSize = combinedData.length % packetSize;
                            }
                        }
                        sendPacket[0] = 0X10;
                        sendPacket[1] = 0X02;
                        sendPacket[2] = 0X00;
                        if (isRS){
                            sendPacket[2] = (byte) RS485_ADDR_NUM;
                        }
                        sendPacket[3] = 0X04;
                        sendPacket[4] = 0X0A;
                        sendPacket[5] = (byte) 0X98;

                        sendPacket[6] = (byte) totalPackets;
                        sendPacket[7] = (byte) (totalPackets >> 8);
                        sendPacket[8] = (byte) sentPacket;
                        sendPacket[9] = (byte) (sentPacket>>8);
                        sendPacket[10] = (byte) groupPackets[sendGroupNum];
                        sendPacket[11] = (byte) (groupPackets[sendGroupNum] >> 8);
                        sendPacket[12] = (byte) j;
                        sendPacket[13] = (byte) (j>>8);
                        sendPacket[14] = 0X01;
                        sentPacket++;
                        if (j == groupPackets[i] - 1){
                            System.arraycopy(combinedData, j*packetSize+16, sendPacket, 15, currentPacketSize-16);
                        }
                        else{
                            System.arraycopy(combinedData, j*packetSize+16, sendPacket, 15, currentPacketSize);
                        }


                        sendPacket[sendPacket.length-2] = 0x10;
                        sendPacket[sendPacket.length-1] = 0x03;

                        hexMsgTransceiver.sendByteMessagesNoLog(sendPacket);
                        System.out.print("send = [");
                        for (byte b : sendPacket) {
                            System.out.printf("%02X ", b);
                        }
                        System.out.println("]");

                        Thread.sleep(200);

                        int finalI = j;
                        int total = totalPackets;
                        Platform.runLater(() -> {
                            // ProgressBar 업데이트 (0 ~ 1.0 범위)
                            progressBar.setProgress((double) finalI / total);

                            // Label 업데이트 (i/totalPackets)
                            progressLabel.setText((finalI + 1) + "/" + total);
                        });
                    }
                }

                //앞뒤로 붙이는거 추가
                finalPacket[0] = 0x10;
                finalPacket[1] = 0x02;
                finalPacket[finalPacket.length-2] = 0x10;
                finalPacket[finalPacket.length-1] = 0x03;
                hexMsgTransceiver.sendByteMessages(finalPacket);

                Thread.sleep(200);

                hexMsgTransceiver.sendMessages("10 02 00 00 02 45 01 10 03 ");

                return null;
            }
        };
    }

    public String extractTwoCharsAroundX(String input, char target) {
        int index = input.indexOf(target); // 'x'의 인덱스를 찾기
        if (index < 2 || index > input.length() - 3) {
            return "Invalid Position"; // 'x'가 없거나 앞뒤에 두 글자가 없는 경우 처리
        }

        // 'x' 앞뒤 두 글자씩 추출
        return input.substring(index - 2, index + 3);
    }
}