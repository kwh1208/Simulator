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

    public void set(String Id, String password) {
        String msg = "++SET++![BT SETT 31  ";
        byte[] IdBytes = Id.getBytes(Charset.forName("MS949"));
        byte[] realId = new byte[20];
        byte[] passwordBytes = password.getBytes(Charset.forName("MS949"));
        byte[] realPassword = new byte[20];

        byte spaceByte = " ".getBytes(Charset.forName("MS949"))[0];

        System.arraycopy(IdBytes, 0, realId, 0, Math.min(IdBytes.length, realId.length));
        if (IdBytes.length < realId.length) {
            Arrays.fill(realId, IdBytes.length, realId.length, spaceByte);
        }

        // passwordBytes를 realPassword에 복사하고 남는 부분을 공백으로 채움
        System.arraycopy(passwordBytes, 0, realPassword, 0, Math.min(passwordBytes.length, realPassword.length));
        if (passwordBytes.length < realPassword.length) {
            Arrays.fill(realPassword, passwordBytes.length, realPassword.length, spaceByte);
        }

        msg += new String(realId, Charset.forName("MS949")) + "  " + new String(realPassword, Charset.forName("MS949")) + "!]";

        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    public void begin(String password){
        String msg = "++SET++![BT " + password + " BEGIN!]";
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }

    public void end(String password) {
        String msg = "++SET++![BT " + password + " END!]";
        asciiMsgTransceiver.sendMessages(msg, false, progressIndicator);
    }
}