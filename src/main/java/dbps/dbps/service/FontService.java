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
import java.text.MessageFormat;
import java.util.*;

import static dbps.dbps.Constants.*;

public class FontService {
    private static FontService instance = null;
    HexMsgTransceiver hexMsgTransceiver;
    LogService logService;
    ResourceBundle bundle;
    //사용안함, 영어, 유니코드 한국, 유니코드 일본어, 유니코드 중국어, 한글조합형, 사용자 폰트, 유니코드 전체
    private final int[][] fontKindAddr = {{0, 0, 0xac00, 0x3040, 0x4e00, 0x8861, 0xe000, 0}, {0, 0x7f, 0xd7a3, 0x30ff, 0x9fff, 0xd3bd, 0xe07f, 0xd7a3}};

    private FontService(){
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        logService = LogService.getLogService();
        bundle=ResourceManager.getInstance().getBundle();
    }

    public static FontService getInstance(){
        if(instance == null){
            instance = new FontService();
        }
        return instance;
    }


    
    public Task<Void> sendFont(String[] fontGroup1, String[] fontGroup2, String[] fontGroup3, String[] fontGroup4, String[] fontType, ProgressBar progressBar, Label progressLabel) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String msg = "10 02 00 00 02 45 00 10 03";
                if (isRS){
                    msg = "10 02 "+RS485_ADDR_NUM+" 00 02 45 00 10 03";
                }
                hexMsgTransceiver.sendByteMessages(hexStringToByteArray(msg), null);

                int packetSize = 1024;
                int totalPackets = 0;
                byte[] combinedData;

                // ArrayList 대신 ByteBuffer 사용으로 변경
                ByteBuffer fontDataBuffer = ByteBuffer.allocate(1024 * 1024 * 10); // 초기 크기 할당 (10MB)

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

                Map<Integer, byte[]> groupFontData = new HashMap<>();
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
                finalPacket[0] = 0x10;
                finalPacket[1] = 0x02;
                finalPacket[finalPacket.length-2] = 0x10;
                finalPacket[finalPacket.length-1] = 0x03;

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
                    
                    // 각 그룹별 버퍼 초기화
                    fontDataBuffer.clear();
                    
                    for (int j = 0; j < 3; j++) {
                        if (now[j]!=null){
                            byte[] fontData;
                            try {
                                // try-with-resources를 사용해 자원 자동 해제
                                try (InputStream fontFile = new FileInputStream(now[j])) {
                                    fontData = fontFile.readAllBytes();
                                }

                                String size = extractTwoCharsAroundX(now[j], 'x');
                                String width = size.substring(0, 2);
                                String height = size.substring(3, 5);

                                Arrays.fill(fontData, 0, 16, (byte)0x00);

                                fontData = Arrays.copyOf(fontData, fontData.length - 16);
                                int fontSize = Integer.parseInt(width) * Integer.parseInt(height) / 8;
                                if (fontType[3*i+j].contains("UNI")){
                                    if (fontType[3*i+j].equals("UNI-KR")){
                                        int startUnicode = 0xAC00-33;
                                        int endUnicode = 0xD7A3;

                                        int startIndex = (startUnicode) * fontSize;
                                        int endIndex = (endUnicode + 1) * fontSize;

                                        // 유니코드 범위 내의 데이터만 복사
                                        fontData = Arrays.copyOfRange(fontData, startIndex, endIndex);
                                    } else if (fontType[3 * i + j].equals("UNI-JP")) {
                                        int startUnicode = 0x3040-33;
                                        int endUnicode = 0x30FF;

                                        int startIndex = (startUnicode) * fontSize;
                                        int endIndex = (endUnicode + 1) * fontSize;

                                        fontData = Arrays.copyOfRange(fontData, startIndex, endIndex);
                                    } else if (fontType[3 * i + j].equals("UNI-CN")){
                                        int startUnicode = 0x4E00-33;
                                        int endUnicode = 0x9FFF;

                                        int startIndex = (startUnicode) * fontSize;
                                        int endIndex = (endUnicode + 1) * fontSize;

                                        fontData = Arrays.copyOfRange(fontData, startIndex, endIndex);
                                    } else {
                                        int endUnicode = 0xd7a3+33;

                                        int startIndex = 0;
                                        int endIndex = (endUnicode + 1) * fontSize;

                                        byte[] trimmedFontData = Arrays.copyOfRange(fontData, startIndex, endIndex);

                                        byte[] dummyData = new byte[33 * fontSize];
                                        Arrays.fill(dummyData, (byte) 0x00);

                                        // 버퍼 크기 확인 및 조정
                                        ByteBuffer newFontData = ByteBuffer.allocate(dummyData.length + trimmedFontData.length);
                                        newFontData.put(dummyData);
                                        newFontData.put(trimmedFontData);

                                        fontData = newFontData.array();
                                    }
                                } else if (fontType[3*i+j].equals("english")){
                                    int endUnicode = 0x7f;

                                    int startIndex = 0;
                                    int endIndex = (endUnicode + 1) * fontSize * 4;

                                    fontData = Arrays.copyOf(fontData, Math.min(fontData.length, endIndex));
                                } else if (fontType[3*i+j].equals("userFont")){
                                    int endUnicode = 0xe07f;

                                    int startIndex = 0;
                                    int endIndex = (endUnicode + 1) * fontSize;

                                    fontData = Arrays.copyOf(fontData, fontData.length - 16 - 1024);
                                }

                                // 1024의 배수로 패딩
                                if (fontData.length % 1024 != 0) {
                                    int newLength = ((fontData.length / 1024) + 1) * 1024;
                                    fontData = Arrays.copyOf(fontData, newLength);
                                }

                                // pos start end w h 순서로 추가
                                // pos
                                ByteBuffer tmp = ByteBuffer.allocate(2);
                                tmp.order(ByteOrder.LITTLE_ENDIAN);
                                tmp.putShort((short)groupPacketCnt);
                                groupPacketCnt += (int) Math.ceil(fontData.length/1024.0);
                                groupPackets[i] = groupPacketCnt;

                                byte[] tmpArray = tmp.array();
                                for (int k = 0; k < 2; k++) {
                                    groupPacket.append(String.format("%02X ", tmpArray[k]));
                                }
                                
                                // start
                                if (j==0&&i==0){
                                    groupPacket.append("00 00 ");
                                }
                                else {
                                    tmp.clear();
                                    if (fontType[3*i+j].equals("english")){
                                        if (!now[0].contains("08")){
                                            tmp.putShort((short)0X0020);
                                        }
                                        else {
                                            tmp.putShort((short)fontKindAddr[0][1]);
                                        }
                                    } else if (fontType[3*i+j].equals("UNI-KR")) {
                                        tmp.putShort((short)fontKindAddr[0][2]);
                                    } else if (fontType[3*i+j].equals("UNI-JP")) {
                                        tmp.putShort((short)fontKindAddr[0][3]);
                                    } else if (fontType[3*i+j].equals("UNI-CN")) {
                                        tmp.putShort((short)fontKindAddr[0][4]);
                                    } else if (fontType[3*i+j].equals("combination")) {
                                        tmp.putShort((short)fontKindAddr[0][5]);
                                    } else if (fontType[3 * i + j].equals("userFont")) {
                                        tmp.putShort((short)fontKindAddr[0][6]);
                                    } else if (fontType[3*i+j].equals("UNI-all")) {
                                        tmp.putShort((short)fontKindAddr[0][7]);
                                    } else{
                                        tmp.putShort((short)fontKindAddr[0][0]);
                                    }
                                    for (int k = 0; k < 2; k++) {
                                        groupPacket.append(String.format("%02X ", tmpArray[k]));
                                    }
                                }

                                // end
                                if (j==0&&i==0){
                                    groupPacket.append("FF 00 ");
                                }
                                else {
                                    tmp.clear();
                                    if (fontType[3*i+j].equals("english")){
                                        tmp.putShort((short)fontKindAddr[1][1]);
                                    } else if (fontType[3*i+j].equals("UNI-KR")) {
                                        tmp.putShort((short)fontKindAddr[1][2]);
                                    } else if (fontType[3*i+j].equals("UNI-JP")) {
                                        tmp.putShort((short)fontKindAddr[1][3]);
                                    } else if (fontType[3*i+j].equals("UNI-CN")) {
                                        tmp.putShort((short)fontKindAddr[1][4]);
                                    } else if (fontType[3*i+j].equals("combination")) {
                                        tmp.putShort((short)fontKindAddr[1][5]);
                                    } else if (fontType[3 * i + j].equals("userFont")) {
                                        tmp.putShort((short)fontKindAddr[1][6]);
                                    } else if (fontType[3*i+j].equals("UNI-all")) {
                                        tmp.putShort((short)fontKindAddr[1][7]);
                                    } else{
                                        tmp.putShort((short)fontKindAddr[1][0]);
                                    }
                                    for (int k = 0; k < 2; k++) {
                                        groupPacket.append(String.format("%02X ", tmpArray[k]));
                                    }
                                }

                                // w&h
                                groupPacket.append(String.format("%02X ", Integer.parseInt(width)));
                                groupPacket.append(String.format("%02X ", Integer.parseInt(height)));

                                // ArrayList 대신 ByteBuffer에 직접 추가
                                fontDataBuffer.put(fontData);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else{
                            for (int k = 0; k < 8; k++) {
                                groupPacket.append(String.format("%02X ", 0));
                            }
                        }
                    }
                    
                    // ByteBuffer에서 바이트 배열로 변환
                    fontDataBuffer.flip();
                    byte[] fontDataArray = new byte[fontDataBuffer.remaining()];
                    fontDataBuffer.get(fontDataArray);
                    
                    groupPackets[i] = (int) Math.ceil(fontDataArray.length/1024.0);
                    totalPackets += groupPackets[i];
                    
                    ByteBuffer buffer = ByteBuffer.allocate(4);
                    buffer.order(ByteOrder.LITTLE_ENDIAN);
                    buffer.putInt(groupPacketCnt*1024);

                    for (byte b : buffer.array()) {
                        finalPacket[finalIdx++] = b;
                    }
                    
                    // HashMap에 바이트 배열 직접 저장
                    groupFontData.put(i, fontDataArray);

                    byte[] bytes = hexStringToByteArray(String.valueOf(groupPacket));

                    for (byte byteData : bytes) {
                        finalPacket[finalIdx++] = byteData;
                    }
                }
                
                int sentPacket = 0;

                // 전송 전에 모든 패킷을 미리 구성하여 메모리에 저장
                List<byte[]> allPacketsToSend = new ArrayList<>();
                
                for (int i = 0; i < groupNum; i++) {
                    combinedData = groupFontData.get(i);
                    
                    for (int j = 0; j < groupPackets[i]; j++) {
                        byte[] sendPacket = new byte[1041];
                        int currentPacketSize = packetSize;
                        if (j == groupPackets[i] - 1 && combinedData.length % packetSize != 0) {
                            currentPacketSize = combinedData.length % packetSize;
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
                        sendPacket[10] = (byte) groupPackets[i];
                        sendPacket[11] = (byte) (groupPackets[i] >> 8);
                        sendPacket[12] = (byte) j;
                        sendPacket[13] = (byte) (j>>8);
                        sendPacket[14] = (byte) (i+1);
                        sentPacket++;
                        
                        if (j == groupPackets[i] - 1){
                            System.arraycopy(combinedData, j*packetSize+16, sendPacket, 15, currentPacketSize-16);
                        }
                        else{
                            System.arraycopy(combinedData, j*packetSize+16, sendPacket, 15, currentPacketSize);
                        }

                        sendPacket[sendPacket.length-2] = 0x10;
                        sendPacket[sendPacket.length-1] = 0x03;
                        
                        allPacketsToSend.add(sendPacket);
                    }
                }
                
                // 실제 패킷 전송
                for (int packetIndex = 0; packetIndex < allPacketsToSend.size(); packetIndex++) {
                    byte[] sendPacket = allPacketsToSend.get(packetIndex);
                    
                    if (isCancelled()){
                        logService.updateInfoLog(bundle.getString("transferCancel"));

                        hexMsgTransceiver.sendByteMessagesNoLog(finalPacket);

                        msg = "10 02 00 00 02 45 01 10 03";
                        if (isRS){
                            msg = "10 02 "+RS485_ADDR_NUM+" 00 02 45 01 10 03";
                        }
                        hexMsgTransceiver.sendByteMessages(hexStringToByteArray(msg), null);
                        return null;
                    }

                    try {
                        hexMsgTransceiver.sendByteMessagesShortLog(sendPacket);
                    } catch (Exception e){
                        if (isCancelled()){
                            logService.updateInfoLog(bundle.getString("transferCancel"));
                            msg = "10 02 00 00 02 45 01 10 03";
                            if (isRS){
                                msg = "10 02 "+RS485_ADDR_NUM+" 00 02 45 01 10 03";
                            }
                            hexMsgTransceiver.sendByteMessages(hexStringToByteArray(msg), null);
                            return null;
                        }
                    }

                    int currentProgress = packetIndex;
                    int totalPacketsCount = allPacketsToSend.size();
                    Platform.runLater(() -> {
                        // UI 업데이트는 한 번의 runLater로 처리
                        progressBar.setProgress((double) currentProgress / totalPacketsCount);
                        progressLabel.setText((int)(((double)(currentProgress + 1)/totalPacketsCount)*100) +"%");
                    });
                }

                Thread.sleep(50);

                hexMsgTransceiver.sendByteMessagesNoLog(finalPacket);

                Thread.sleep(500);

                msg = "10 02 00 00 02 45 01 10 03";
                if (isRS){
                    msg = "10 02 "+RS485_ADDR_NUM+" 00 02 45 01 10 03";
                }
                hexMsgTransceiver.sendByteMessages(hexStringToByteArray(msg), null);

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