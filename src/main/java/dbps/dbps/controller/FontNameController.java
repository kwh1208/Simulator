package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.FontNameService;
import dbps.dbps.service.HexMsgTransceiver;
import dbps.dbps.service.LogService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.*;

public class FontNameController {
    @FXML
    public TextField group2font2;
    @FXML
    public TextField group2font1;
    @FXML
    public TextField group2font3;
    @FXML
    public TextField group1font3;
    @FXML
    public TextField group1font1;
    @FXML
    public TextField group1font2;
    @FXML
    public Pane group2Pane;
    @FXML
    public Pane group1Pane;
    @FXML
    public RadioButton readRadio;
    @FXML
    public RadioButton sendRadio;

    public AnchorPane fontNameAP;
    public ProgressIndicator progressIndicator;

    ToggleGroup group;

    AsciiMsgTransceiver asciiMsgTransceiver;
    HexMsgTransceiver hexMsgTransceiver;
    LogService logService;
    FontNameService fontNameService;

    @FXML
    public void initialize() {
        fontNameAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/fontName.css").toExternalForm());
        group = new ToggleGroup();
        readRadio.setToggleGroup(group);
        sendRadio.setToggleGroup(group);
        fontNameService = FontNameService.getInstance();
        fontNameService.setGroup1font1(group1font1);
        fontNameService.setGroup1font2(group1font2);
        fontNameService.setGroup1font3(group1font3);
        fontNameService.setGroup2font1(group2font1);
        fontNameService.setGroup2font2(group2font2);
        fontNameService.setGroup2font3(group2font3);

        readRadio.setSelected(true);
        logService = LogService.getLogService();

        sendRadio.setSelected(false);

        group1Pane.getChildren().forEach(node -> node.setDisable(true));
        group2Pane.getChildren().forEach(node -> node.setDisable(true));

        sendRadio.setOnAction(event->{
            group1Pane.getChildren().forEach(node -> node.setDisable(false));
            group2Pane.getChildren().forEach(node -> node.setDisable(false));
        });

        readRadio.setOnAction(event->{
            group2Pane.getChildren().forEach(node -> node.setDisable(true));
            group1Pane.getChildren().forEach(node -> node.setDisable(true));
        });

        limitLength(group2font1);
        limitLength(group2font2);
        limitLength(group2font3);
        limitLength(group1font1);
        limitLength(group1font2);
        limitLength(group1font3);

        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
        hexMsgTransceiver = HexMsgTransceiver.getInstance();
    }

    private void limitLength(TextField field) {
        field.setTextFormatter(new TextFormatter<>(change -> {
            // 입력된 문자열을 UTF-8로 변환하여 바이트 수 계산
            String newText = change.getControlNewText();
            int byteLength;
            try {
                byteLength = newText.getBytes("MS949").length;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            // 36바이트를 넘지 않으면 변경 허용
            if (byteLength <= 36) {
                return change;
            } else {
                // 36바이트를 넘으면 입력 차단
                return null;
            }
        }));
    }


    public void send() throws UnsupportedEncodingException, ExecutionException, InterruptedException {
        if (IS_ASCII){
            if (readRadio.isSelected()) {
                String msg = "![00960!]";
                if (isRS) {
                    msg = "![" + convertRS485AddrASCii() + "0960!]";
                }

                // 비동기 작업을 위한 Task 생성
                String finalMsg = msg;
                Task<String> readTask = new Task<>() {
                    @Override
                    protected String call() throws Exception {
                        // 메시지 송신 및 응답 대기
                        String response = asciiMsgTransceiver.sendMessages(finalMsg, false, progressIndicator).get();

                        // 응답 데이터 가공
                        return response.substring(8, response.length() - 2);
                    }
                };

                // 작업 성공 시 처리
                readTask.setOnSucceeded(e -> {
                    try {
                        String result = readTask.get();
                        String[] fontNames = getFontName(result.getBytes(Charset.forName("MS949")));

                        // UI 업데이트
                        Platform.runLater(() -> {
                            group1font1.setText(fontNames[0]);
                            group1font2.setText(fontNames[1]);
                            group1font3.setText(fontNames[2]);
                            group2font1.setText(fontNames[3]);
                            group2font2.setText(fontNames[4]);
                            group2font3.setText(fontNames[5]);
                        });
                    } catch (Exception ex) {
                        logService.errorLog("결과 처리 중 오류 발생: " + ex.getMessage());
                    }
                });

                // 작업 실패 시 처리
                readTask.setOnFailed(e -> {
                    logService.errorLog("메시지 송수신 실패: " + readTask.getException().getMessage());
                });

                // 작업 실행
                new Thread(readTask).start();
            }

            else {
                byte[] sendMsg = new byte[216 + 10];
                sendMsg[0] = "!".getBytes(Charset.forName("MS949"))[0];
                sendMsg[1] = "[".getBytes(Charset.forName("MS949"))[0];
                sendMsg[2] = "0".getBytes(Charset.forName("MS949"))[0];
                if (isRS){
                    sendMsg[2] = (convertRS485AddrASCii().getBytes("MS949"))[0];
                }
                sendMsg[3] = "0".getBytes(Charset.forName("MS949"))[0];
                sendMsg[4] = "9".getBytes(Charset.forName("MS949"))[0];
                sendMsg[5] = "5".getBytes(Charset.forName("MS949"))[0];
                sendMsg[6] = " ".getBytes(Charset.forName("MS949"))[0];
                sendMsg[7] = "2".getBytes(Charset.forName("MS949"))[0];
                sendMsg[224] = "!".getBytes(Charset.forName("MS949"))[0];
                sendMsg[225] = "]".getBytes(Charset.forName("MS949"))[0];
                int idx = 8;
                byte[] tmp2 = new byte[36];
                byte[] tmp1 = group1font1.getText().getBytes("MS949");
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font2.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font3.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font1.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font2.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font3.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);

                asciiMsgTransceiver.sendMessages(new String(sendMsg, "MS949"), false, progressIndicator);
            }
        }
        else {
            if (readRadio.isSelected()) {
                String msg = "10 02 00 00 03 48 01 32 10 03";
                if (isRS){
                    msg = "10 02 "+String.format("%02X ", RS485_ADDR_NUM)+"00 03 48 01 32 10 03";
                }
                hexMsgTransceiver.sendMessages(msg, progressIndicator);
            }
            else {
                //10 02 00 00 DB 48 00 32
                byte[] sendMsg = new byte[216 + 10];
                sendMsg[0] = (byte) 0x10;
                sendMsg[1] = (byte) 0x02;
                sendMsg[2] = (byte) 0x00;
                if (isRS){
                    sendMsg[2] = (byte) RS485_ADDR_NUM;
                }
                sendMsg[3] = (byte) 0x00;
                sendMsg[4] = (byte) 0xDB;
                sendMsg[5] = (byte) 0x48;
                sendMsg[6] = (byte) 0x00;
                sendMsg[7] = (byte) 0x32;
                sendMsg[224] = (byte) 0x10;
                sendMsg[225] = (byte) 0x03;
                int idx = 8;
                byte[] tmp2 = new byte[36];
                byte[] tmp1 = group1font1.getText().getBytes("MS949");
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font2.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font3.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font1.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font2.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font3.getText().getBytes("MS949");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);

                hexMsgTransceiver.sendByteMessages(sendMsg, progressIndicator);
            }
        }

        //쓰기
        //0095
        //10 02 00 00 DB 48 00 32


        //36바이트까지 가능.
    }

    public static String[] getFontName(byte[] tmp) throws UnsupportedEncodingException {
        String[] fontName = new String[6];
        for (int i = 0; i < 6; i++) {
            byte[] chunk = new byte[36]; // 36바이트 크기의 새로운 배열 생성

            // 원본 바이트 배열에서 36바이트씩 잘라서 새 배열에 복사
            System.arraycopy(tmp, i*36, chunk, 0, 36);

            // 각 바이트 배열을 문자열로 변환 후 출력
            fontName[i] = new String(chunk, "MS949");
        }
        return fontName;
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
