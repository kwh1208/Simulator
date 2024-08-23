package dbps.dbps.service;

import java.nio.charset.Charset;
import java.util.Arrays;

public class BtMsgMaker {



    //search
    public byte[] btSearch(){
        String searchMsg = "++SET++![BT SEARCHING DIBD!]";

        return searchMsg.getBytes(Charset.forName("EUC-KR"));
    }


    //set
    public byte[] btSet(String name, String pwd){
        //헥사버전
        String setMsg = "++SET++![BT SETT 31  ";
        String validName = chkLength(name);
        String validPwd = chkLength(pwd);
        setMsg += validName + validPwd + "!]";

        return setMsg.getBytes(Charset.forName("EUC-KR"));
    }
    //begin
    public byte[] btBegin(String pwd) {
        String beginMsg = "++SET++![BT ";
        String validPwd = chkLength(pwd);
        beginMsg += validPwd + " BEGIN!]";
        return beginMsg.getBytes(Charset.forName("EUC-KR"));
    }

    //end
    public byte[] btEnd(String pwd) {
        String endMsg = "++SET++![BT ";
        String validPwd = chkLength(pwd);
        endMsg += validPwd + " END!]";
        return endMsg.getBytes(Charset.forName("EUC-KR"));
    }

    /**
     * 리팩토링용
     */

    private String chkLength(String name) {
        byte[] result = new byte[20];
        Arrays.fill(result, (byte) 0x20);
        int index = 0;
        for (int i = 0; i < name.length(); i++) {
            String tmp = name.substring(i, i + 1);
            byte[] tmpBytes = tmp.getBytes(Charset.forName("EUC-KR"));
            if(index+tmpBytes.length>=20){
                break;
            }
            for (int j = 0; j < tmpBytes.length; j++) {
                result[index] = tmpBytes[j];
                index++;
            }
        }
        return new String(result);
    }
}