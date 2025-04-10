package dbps.dbps.service.connectManager;

import dbps.dbps.service.AsciiMsgTransceiver;
import javafx.scene.control.ProgressIndicator;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;
import java.util.Arrays;
@Setter
@Getter
public class BTManager {

    private static BTManager instance = null;
    ProgressIndicator progressIndicator;


    AsciiMsgTransceiver asciiMsgTransceiver;

    public static BTManager getInstance() {
        if (instance == null) {
            instance = new BTManager();
        }
        return instance;
    }

    private BTManager() {
        asciiMsgTransceiver = AsciiMsgTransceiver.getInstance();
    }

    public void search() {
        asciiMsgTransceiver.sendMessages("++SET++![BT SEARCHING DIBD!]", false, progressIndicator);
    }

    public void set(String id, String password) {
        Charset charset = Charset.forName("MS949");
        byte[] idFixed = getFixedBytes(id, 20, charset);
        byte[] pwdFixed = getFixedBytes(password, 20, charset);

        // 시작 부분의 문자열은 고정되어 있다고 가정하고,
        // 최종 메시지도 바이트 배열로 구성한 후 전송하거나,
        // 문자열과 바이트 배열이 혼합되지 않도록 처리한다.
        String header = "++SET++![BT SETT  31  ";
        String footer = "!]";

        // header와 footer는 인코딩된 바이트와 결합
        byte[] headerBytes = header.getBytes(charset);
        byte[] footerBytes = footer.getBytes(charset);

        // 최종 메시지 바이트 배열의 길이 계산
        byte[] message = new byte[headerBytes.length + idFixed.length + 2 /*여기서 사이 공백 등*/ + pwdFixed.length + footerBytes.length];

        int pos = 0;
        System.arraycopy(headerBytes, 0, message, pos, headerBytes.length);
        pos += headerBytes.length;

        System.arraycopy(idFixed, 0, message, pos, idFixed.length);
        pos += idFixed.length;

        // ID와 Password 사이에 2바이트의 공백 추가 (필요 시)
        byte spaceByte = " ".getBytes(charset)[0];
        message[pos++] = spaceByte;
        message[pos++] = spaceByte;

        System.arraycopy(pwdFixed, 0, message, pos, pwdFixed.length);
        pos += pwdFixed.length;

        System.arraycopy(footerBytes, 0, message, pos, footerBytes.length);
        pos += footerBytes.length;


        asciiMsgTransceiver.sendMessages(new String(message), false, null);
    }

    public void begin(String password){
        String msg = "++SET++![BT " + padPassword(password) + " BEGIN!]";
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    public void end(String password) {
        String msg = "++SET++![BT " + padPassword(password) + " END!]";
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }


    public static String padPassword(String password) {
        byte[] passwordBytes = password.getBytes(); // 문자열을 바이트 배열로 변환
        int length = passwordBytes.length;

        if (length > 20) {
            // 20바이트를 초과하면 자름
            return new String(passwordBytes, 0, 20);
        } else if (length < 20) {
            // 20바이트보다 짧으면 공백(0x20)으로 패딩
            byte[] paddedBytes = new byte[20];
            System.arraycopy(passwordBytes, 0, paddedBytes, 0, length);
            for (int i = length; i < 20; i++) {
                paddedBytes[i] = 0x20; // 공백 문자 (ASCII 32)
            }
            return new String(paddedBytes);
        }

        return password; // 이미 20바이트면 그대로 반환
    }

    public byte[] getFixedBytes(String str, int fixedLength, Charset charset) {
        byte[] bytes = str.getBytes(charset);
        // 만약 지정된 길이보다 길다면 멀티바이트 문자가 잘리지 않도록 처리 필요
        if (bytes.length > fixedLength) {
            // 여기서는 단순히 잘라내지만, 실제로는 문자 경계를 고려한 로직이 필요함
            return Arrays.copyOf(bytes, fixedLength);
        } else if (bytes.length < fixedLength) {
            byte[] fixed = new byte[fixedLength];
            System.arraycopy(bytes, 0, fixed, 0, bytes.length);
            // 공백의 바이트 값
            byte spaceByte = " ".getBytes(charset)[0];
            Arrays.fill(fixed, bytes.length, fixedLength, spaceByte);
            return fixed;
        }
        return bytes;
    }
}