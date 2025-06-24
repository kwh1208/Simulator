package dbps.dbps.service;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import lombok.Setter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static dbps.dbps.Constants.*;
import static dbps.dbps.Constants.calcCRC;

public class FirmwareService {

    private static FirmwareService instance = null;
    HexMsgTransceiver hexMsgTransceiver;
    LogService logService;
    ResourceBundle bundle;

    private FirmwareService() {
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
        logService = LogService.getLogService();
        bundle = ResourceManager.getInstance().getBundle();
    }

    public static FirmwareService getFirmwareService() {
        if (instance == null) {
            instance = new FirmwareService();
        }
        return instance;
    }

    @Setter
    public static TextArea firmwareInformation;

    public void setFirmware(String firmware) {
        String replace = firmware.replace("!]", "");
        if (firmwareInformation==null){
            return;
        }
        firmwareInformation.setText(replace);
    }

    public Task<Void> firmwareUpload(ProgressBar progressBar, Label progressLabel) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                cancel = false;
                String msg = "10 02 00 00 02 45 00 10 03";
                if (isRS){
                    msg = "10 02 "+RS485_ADDR_NUM+" 00 02 45 00 10 03";
                }
                hexMsgTransceiver.sendByteMessagesShortLog(hexStringToByteArray(msg));
                try {
                    // BufferedInputStream을 사용하여 파일 읽기 성능 향상
                    try (BufferedInputStream firmwareStream = new BufferedInputStream(new FileInputStream(uploadFirmwarePath))) {
                        if (firmwareStream == null) {
                            return null;
                        }

                        // 펌웨어 데이터를 읽어 바이트 배열로 저장
                        byte[] firmwareData = firmwareStream.readAllBytes();

                        // 패킷 개수 계산
                        int packetSize = 1024;
                        int totalPackets = (firmwareData.length + packetSize - 1) / packetSize;

                        if (!uploadFirmwarePath.contains("502")) {
                            firmwareData[512] = (byte) totalPackets;
                            firmwareData[513] = (byte) (totalPackets >> 8);
                        }

                        // 시작 메시지 전송
                        msg = "10 02 00 00 02 6F F1 10 03";
                        if (isRS) {
                            msg = "10 02 " + String.format("%02X ", RS485_ADDR_NUM) + "00 02 6F F1 10 03";
                        }
                        hexMsgTransceiver.sendByteMessagesShortLog(hexStringToByteArray(msg));

                        // 모든 패킷을 미리 구성
                        List<byte[]> allPackets = new ArrayList<>(totalPackets);
                        
                        for (int i = 0; i < totalPackets; i++) {
                            int currentPacketSize = (i == totalPackets - 1)
                                    ? firmwareData.length % packetSize
                                    : packetSize;

                            byte[] headerData = {
                                    0x00,
                                    (byte) (((currentPacketSize + 7) >> 8) | (0x40)),
                                    (byte) ((currentPacketSize + 7) & 0xFF),
                                    (byte) 0X9F,
                                    (byte) totalPackets,
                                    (byte) (totalPackets >> 8),
                                    (byte) i,
                                    (byte) (i >> 8)
                            };
                            
                            if (isRS) {
                                headerData[0] = (byte) RS485_ADDR_NUM;
                            }

                            // CRC 계산
                            int crc = calcCRC(headerData, firmwareData, headerData.length, i * packetSize, currentPacketSize);

                            // 패킷 데이터 크기 (시작/종료 시퀀스 + 헤더 + 펌웨어 데이터 + CRC + 종료 시퀀스)
                            byte[] packet = new byte[2 + headerData.length + currentPacketSize + 4];

                            // 패킷 구성
                            packet[0] = 0x10;  // 시작 시퀀스
                            packet[1] = 0x02;
                            
                            // 헤더 복사
                            System.arraycopy(headerData, 0, packet, 2, headerData.length);

                            // 펌웨어 데이터 복사
                            System.arraycopy(firmwareData, i * packetSize, packet, 2 + headerData.length, currentPacketSize);

                            // CRC 추가
                            packet[2 + headerData.length + currentPacketSize] = (byte) (crc & 0xFF);
                            packet[2 + headerData.length + currentPacketSize + 1] = (byte) ((crc >> 8) & 0xFF);

                            // 종료 시퀀스
                            packet[2 + headerData.length + currentPacketSize + 2] = 0x10;
                            packet[2 + headerData.length + currentPacketSize + 3] = 0x03;

                            allPackets.add(packet);
                        }

                        // 패킷 전송
                        for (int i = 0; i < allPackets.size(); i++) {
                            byte[] packet = allPackets.get(i);
                            
                            boolean success = false;
                            int retryCount = 0;
                            
                            // 재시도 로직 최적화
                            while (!success && retryCount < 3) {
                                try {
                                    hexMsgTransceiver.sendByteMessagesShortLog(packet);
                                    success = true;
                                } catch (IOException e){
                                    return null;
                                }
                                catch (Exception e) {
                                    retryCount++;
                                    if (retryCount >= 3) {
                                        logService.errorLog("재시도 3회 실패했습니다. 연결상태를 확인해주세요.");
                                    }
                                    // 지수 백오프로 재시도 대기 시간 증가
                                    Thread.sleep(300 * retryCount);
                                }
                            }

                            // UI 업데이트
                            int currentProgress = i;
                            Platform.runLater(() -> {
                                progressBar.setProgress((double) currentProgress / totalPackets);
                                progressLabel.setText((int)(((double)(currentProgress + 1)/totalPackets)*100) +"%");
                            });
                        }
                    }
                } catch (Exception e) {
                    msg = "10 02 00 00 02 45 01 10 03";
                    if (isRS){
                        msg = "10 02 "+RS485_ADDR_NUM+" 00 02 45 01 10 03";
                    }
                    Thread.sleep(500);
                    hexMsgTransceiver.sendByteMessagesShortLog(hexStringToByteArray(msg));
                    return null;
                }

                Thread.sleep(500);

                logService.updateInfoLog(bundle.getString("completeFirmwareUpload"));

                return null;
            }
        };
    }
}