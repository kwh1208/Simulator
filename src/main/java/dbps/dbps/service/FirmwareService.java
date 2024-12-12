package dbps.dbps.service;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.InputStream;

import static dbps.dbps.Constants.*;
import static dbps.dbps.Constants.calcCRC;

public class FirmwareService {

    private static FirmwareService instance = null;
    HexMsgTransceiver hexMsgTransceiver;

    private FirmwareService() {
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    public static FirmwareService getFirmwareService() {
        if (instance == null) {
            instance = new FirmwareService();
        }
        return instance;
    }

    @Setter
    public TextArea firmwareInformation;

    public void setFirmware(String firmware) {
        firmwareInformation.setText(firmware);
    }

    public Task<Void> firmwareUpload(ProgressBar progressBar, Label progressLabel) {
        return new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            try {
                // 리소스 파일에서 펌웨어 데이터를 읽어옴
                InputStream firmwareStream = new FileInputStream(uploadFirmwarePath);

                if (firmwareStream == null) {
                    return null;
                }

                // 펌웨어 데이터를 읽어 바이트 배열로 저장
                byte[] firmwareData = firmwareStream.readAllBytes();
                firmwareStream.close();  // 스트림 닫기

                // 패킷 개수 계산
                int packetSize = 1024;
                int totalPackets = (firmwareData.length + packetSize - 1) / packetSize;                //600, 500
//            firmwareData[512] = (byte) totalPackets;
//            firmwareData[513] = (byte) (totalPackets >> 8);
                //502
                if (!uploadFirmwarePath.contains("502")){
                    firmwareData[512] = (byte) totalPackets;
                    firmwareData[513] = (byte) (totalPackets >> 8);
                }
                // 앞부분에 추가할 고정된 4바이트 값 (CRC 계산에만 사용)
//            //{0x00, 0x44, 0x07, (byte) 0x9F};
//            //{주소, isize 상하위데이터, 명령코드}
                String msg = "10 02 00 00 02 6F F1 10 03";
                if (isRS){
                    msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 02 6F F1 10 03";
                }
                hexMsgTransceiver.sendByteMessagesNoLog(hexStringToByteArray(msg));

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
                    if (isRS){
                        headerData[0] = (byte)RS485_ADDR_NUM;
                    }

                    // CRC 계산
                    int crc = calcCRC(headerData, firmwareData, headerData.length, i * packetSize, currentPacketSize);

                    // 패킷 데이터 크기 (시작/종료 시퀀스 + 헤더 + 펌웨어 데이터 + CRC + 종료 시퀀스)
                    byte[] packet = new byte[2 + headerData.length + currentPacketSize + 4];

                    // 10 02 (시작 시퀀스)
                    packet[0] = 0x10;
                    packet[1] = 0x02;
                    // 헤더 복사
                    System.arraycopy(headerData, 0, packet, 2, headerData.length);

                    // 펌웨어 데이터 복사
                    System.arraycopy(firmwareData, i * packetSize, packet, 2 + headerData.length, currentPacketSize);

                    // CRC 추가
                    packet[2 + headerData.length + currentPacketSize] = (byte) (crc & 0xFF);
                    packet[2 + headerData.length + currentPacketSize + 1] = (byte) ((crc >> 8) & 0xFF);

                    // 10 03 (종료 시퀀스)
                    packet[2 + headerData.length + currentPacketSize + 2] = 0x10;
                    packet[2 + headerData.length + currentPacketSize + 3] = 0x03;

                    hexMsgTransceiver.sendByteMessagesNoLog(packet);

                    int finalI = i;
                    Platform.runLater(() -> {
                        // ProgressBar 업데이트 (0 ~ 1.0 범위)
                        progressBar.setProgress((double) finalI / totalPackets);

                        // Label 업데이트 (i/totalPackets)
                        progressLabel.setText((finalI + 1) + "/" + totalPackets);
                    });

                    Thread.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Thread.sleep(500);
            //포트닫고열기
            if(CONNECT_TYPE.equals("serial")){

            }
            return null;
        }
    };
}
}
