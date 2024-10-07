package dbps.dbps.service.connectManager;

import javafx.concurrent.Task;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static dbps.dbps.Constants.CONNECT_TYPE;
public class BTManager {

    private static BTManager instance = null;
    SerialPortManager serialPortManager;

    public static BTManager getInstance() {
        if (instance == null) {
            instance = new BTManager();
        }
        return instance;
    }

    private BTManager() {
        serialPortManager = SerialPortManager.getManager();
    }

    public void search() {
        serialPortManager.sendMsgAndGetMsg("++SET++![BT SEARCHING DIBD!]");
    }

    public void set(String Id, String password) {
        String msg = "++SET++![BT SETT 31  ";
        byte[] IdBytes = Id.getBytes(Charset.forName("EUC-KR"));
        byte[] realId = new byte[20];
        byte[] passwordBytes = password.getBytes(Charset.forName("EUC-KR"));
        byte[] realPassword = new byte[20];

        byte spaceByte = " ".getBytes(Charset.forName("EUC-KR"))[0];

        System.arraycopy(IdBytes, 0, realId, 0, Math.min(IdBytes.length, realId.length));
        if (IdBytes.length < realId.length) {
            Arrays.fill(realId, IdBytes.length, realId.length, spaceByte);
        }

        // passwordBytes를 realPassword에 복사하고 남는 부분을 공백으로 채움
        System.arraycopy(passwordBytes, 0, realPassword, 0, Math.min(passwordBytes.length, realPassword.length));
        if (passwordBytes.length < realPassword.length) {
            Arrays.fill(realPassword, passwordBytes.length, realPassword.length, spaceByte);
        }

        msg += new String(realId, Charset.forName("EUC-KR")) + "  " + new String(realPassword, Charset.forName("EUC-KR")) + "!]";

        serialPortManager.sendMsgAndGetMsg(msg);
    }

    public void begin(String password) {
        String msg = "++SET++![BT " + password + " BEGIN!]";
        Task<String> sendTask = serialPortManager.sendMsgAndGetMsg(msg);
        Thread taskThread = new Thread(sendTask);
        taskThread.start();
        String receivedMsg = "";
        try {
            receivedMsg = sendTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (receivedMsg.equals("![DIBD BLE OK!]")){
            CONNECT_TYPE = "bluetooth";
        }
    }

    public void end(String password) {
        String msg = "++SET++![BT " + password + " END!]";
        serialPortManager.sendMsgAndGetMsg(msg);
    }


    //++SET++![BT SETT  31  name  password!]
    //++SET++![BT password             BEGIN!]
    //++SET++![BT password             END!]
}