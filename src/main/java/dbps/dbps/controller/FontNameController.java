package dbps.dbps.controller;

import dbps.dbps.service.AsciiMsgTransceiver;
import dbps.dbps.service.HexMsgTransceiver;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

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

    ToggleGroup group;

    AsciiMsgTransceiver asciiMsgTransceiver;
    HexMsgTransceiver hexMsgTransceiver;

    @FXML
    public void initialize() {
        fontNameAP.getStylesheets().add(getClass().getResource("/dbps/dbps/css/fontName.css").toExternalForm());
        group = new ToggleGroup();
        readRadio.setToggleGroup(group);
        sendRadio.setToggleGroup(group);

        readRadio.setSelected(true);

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
                byteLength = newText.getBytes("EUC-KR").length;
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


    public void send() throws UnsupportedEncodingException {
        //읽기
        //00960
        //10 02 00 00 03 48 01 32 10 03
        if (IS_ASCII){
            if (readRadio.isSelected()) {
                String msg = "![00960!]";
                if (isRS){
                    msg = "!["+convertRS485AddrASCii()+"0960!]";
                }
                String result = asciiMsgTransceiver.sendMessages(msg, false);

                result = result.substring(8, result.length()-2);
                String[] fontNames = getFontName(result.getBytes(Charset.forName("EUC-KR")));

                group1font1.setText(fontNames[0]);
                group1font2.setText(fontNames[1]);
                group1font3.setText(fontNames[2]);
                group2font1.setText(fontNames[3]);
                group2font2.setText(fontNames[4]);
                group2font3.setText(fontNames[5]);
            }
            else {
                byte[] sendMsg = new byte[216 + 10];
                sendMsg[0] = "!".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[1] = "[".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[2] = "0".getBytes(Charset.forName("EUC-KR"))[0];
                if (isRS){
                    sendMsg[2] = (convertRS485AddrASCii().getBytes("EUC-KR"))[0];
                }
                sendMsg[3] = "0".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[4] = "9".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[5] = "5".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[6] = " ".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[7] = "2".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[224] = "!".getBytes(Charset.forName("EUC-KR"))[0];
                sendMsg[225] = "]".getBytes(Charset.forName("EUC-KR"))[0];
                int idx = 8;
                byte[] tmp2 = new byte[36];
                byte[] tmp1 = group1font1.getText().getBytes("EUC-KR");
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font2.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font3.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font1.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font2.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font3.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);

                asciiMsgTransceiver.sendMessages(new String(sendMsg, "EUC-KR"), false);
            }
        }
        else {
            if (readRadio.isSelected()) {
                String msg = "10 02 00 00 03 48 01 32 10 03";
                if (isRS){
                    msg = "10 02 "+String.format("02X ", RS485_ADDR_NUM)+"00 03 48 01 32 10 03";
                }
                String[] tmp = hexMsgTransceiver.sendMessages(msg).split(" ");
                StringBuilder result = new StringBuilder();
                for (int i = 7; i <= 224; i++) {
                    result.append(tmp[i]).append(" ");
                }

                String[] fontNames = getFontName(hexStringToByteArray(result.toString()));

                group1font1.setText(fontNames[0]);
                group1font2.setText(fontNames[1]);
                group1font3.setText(fontNames[2]);
                group2font1.setText(fontNames[3]);
                group2font2.setText(fontNames[4]);
                group2font3.setText(fontNames[5]);
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
                byte[] tmp1 = group1font1.getText().getBytes("EUC-KR");
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font2.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group1font3.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font1.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font2.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);
                idx+=36;
                tmp1 = group2font3.getText().getBytes("EUC-KR");
                tmp2 = new byte[36];
                System.arraycopy(tmp1, 0, tmp2, 0, tmp1.length);
                System.arraycopy(tmp2, 0, sendMsg, idx, 36);

                hexMsgTransceiver.sendByteMessages(sendMsg);
            }
        }

        //쓰기
        //0095
        //10 02 00 00 DB 48 00 32


        //36바이트까지 가능.
    }

    private static String[] getFontName(byte[] tmp) throws UnsupportedEncodingException {
        String[] fontName = new String[6];
        for (int i = 0; i < 6; i++) {
            byte[] chunk = new byte[36]; // 36바이트 크기의 새로운 배열 생성

            // 원본 바이트 배열에서 36바이트씩 잘라서 새 배열에 복사
            System.arraycopy(tmp, i*36, chunk, 0, 36);

            // 각 바이트 배열을 문자열로 변환 후 출력
            fontName[i] = new String(chunk, "EUC-KR");
        }
        return fontName;
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
