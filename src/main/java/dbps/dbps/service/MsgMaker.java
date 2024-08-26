package dbps.dbps.service;

public class MsgMaker {

    private static MsgMaker instance = null;

    public static MsgMaker getInstance() {

        if (instance == null) {
            instance = new MsgMaker();
        }
        return instance;
    }

    //앞뒤 처리만 하면 되는 메세지
    public String makeMsgASCii(String msg) {
        return "!["+msg+"!]";
    }

    //보내려는 정보만 있는 메세지
    public String makeMsgAsciiWithCon(String msg, String RS485, boolean errorChk, int msgType) {
        String result = "";

        //0~F까지 이후부터는 모름
        result += RS485;

        if (errorChk){
            result+="1";
        }
        else{
            result+="0";
        }

        if(msgType!=2){
            result+=String.valueOf(msgType);
        }
        else {
            return makeMsgASCiiSpecial(msg, msgType);
        }

        result+=makeMsgASCii(msg);

        return result;
    }

    //특수명령
    private String makeMsgASCiiSpecial(String msg, int msgType) {
        String result = "![00";
        return switch (msgType) {
            case 20, 21, 30, 40, 50, 60, 61, 62, 70, 90 -> result + msgType + msg + "!]";
            case 22 ->
                //외부신호출력 -> 재작업 필요
                    result;
            case 31, 41, 42, 81, 830 -> result + msgType + "!]";
            default -> throw new RuntimeException("잘못된 명령어입니다.");
        };
    }

    public String makeMsgHEX(String msg) {
        return "10 02 "+msg+"10 03";
        //rs 485 목적지 따라서 02 뒤에 00~1F
    }

    public String makeMsgHexWithCon(){
        return "";
    }

    private String makeMsgHexSpecial(){
        return "";
    }

}
