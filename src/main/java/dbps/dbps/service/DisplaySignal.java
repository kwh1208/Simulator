package dbps.dbps.service;

import java.util.HashMap;
import java.util.Map;


//표출신호별 전송 프로토콜 저장
public class DisplaySignal {
    public static Map<String, String> SignalMap_ASC = new HashMap<>();
    public static Map<String, String> SignalMap_HEX = new HashMap<>();

    private static DisplaySignal instance = null;

    public static DisplaySignal getInstance() {
        if (instance == null) {
            instance = new DisplaySignal();
        }
        return instance;
    }

    private DisplaySignal() {
        initialize_ASCii();
        initialize_HEX();
    }

    public void initialize_ASCii(){
        SignalMap_ASC.put("32D-P16D1S11", "052B111");
        SignalMap_ASC.put("16D-P16D1S10-1", "052A110");
        SignalMap_ASC.put("16D-P16D1S11", "052A111");
        SignalMap_ASC.put("16D-P16D1S21", "052A121");
        SignalMap_ASC.put("16D-P16D1S31", "052A131");
        SignalMap_ASC.put("16D-P16D1S41", "052A141");
        SignalMap_ASC.put("08D-P16D2S10-1^9", "0528210");
        SignalMap_ASC.put("08D-P16D2S11-1^9", "0528211");
        SignalMap_ASC.put("08D-P16D2S21", "0528221");
        SignalMap_ASC.put("08D-P16D2S23-1^2", "0528223");
        SignalMap_ASC.put("08D-P16D2S31-9^1", "0528231");
        SignalMap_ASC.put("08D-P16D2S81", "0528281");
        SignalMap_ASC.put("08D-P32D1S11", "0528111");
        SignalMap_ASC.put("08D-P32D1S12", "0528112");
        SignalMap_ASC.put("08D-P32D1S22-9<1", "0528122");
        SignalMap_ASC.put("08D-P32D1S31", "0528131");
        SignalMap_ASC.put("08D-P32D1S32", "0528132");
        SignalMap_ASC.put("08D-P64D1S21", "0528121");
        SignalMap_ASC.put("08D-P64D1S31", "0528131");
        SignalMap_ASC.put("08D-P64D1S41-11", "0528141");
        SignalMap_ASC.put("08D-P64D1S61", "0528161");
        SignalMap_ASC.put("08D-P64D1S71", "0528171");
        SignalMap_ASC.put("08D-P64D1S91-11", "0528191");
        SignalMap_ASC.put("08D-P128D1S42", "0528142");
        SignalMap_ASC.put("08D-P128D1S51", "0528151");
        SignalMap_ASC.put("04D-P16D4S11-1^5^9^13", "0524411");
        SignalMap_ASC.put("04D-P16D4S21-1^5^9^13", "0524421");
        SignalMap_ASC.put("04D-P32D2S11", "0524211");
        SignalMap_ASC.put("04D-P32D2S21", "0524221");
        SignalMap_ASC.put("04D-P32D2S31-2<1^4<3", "0524231");
        SignalMap_ASC.put("04D-P32D2S41", "0524241");
        SignalMap_ASC.put("04D-P32D2S51", "0524251");
        SignalMap_ASC.put("04D-P32D2S61", "0524261");
        SignalMap_ASC.put("04D-P32D2S71", "0524271");
        SignalMap_ASC.put("04D-P64D1S11", "0524111");
        SignalMap_ASC.put("04D-D2-SA1-A4E8A8E8A4", "05242:1");
        SignalMap_ASC.put("04D-D2-SA1-A4E8A8E8", "05242:2");
        SignalMap_ASC.put("02D-P64D2S21", "0522221");
        SignalMap_ASC.put("02D-P64D2S31", "0522231");
        SignalMap_ASC.put("02D-P64D2S41", "0522241");
        SignalMap_ASC.put("02D-P128D2S21", "0522121");
        SignalMap_ASC.put("02D-P256D2S41", "0522241");
        SignalMap_ASC.put("01D-P64D4S21-4^8^12^16", "0521421");
        SignalMap_ASC.put("01D-P128D2S21", "0521221");
        SignalMap_ASC.put("01D-P128D2S31", "0521231");
    }

    public void initialize_HEX(){
        SignalMap_HEX.put("32D-P16D1S11", "00 07 49 00 20 01 11");
        SignalMap_HEX.put("16D-P16D1S10-1", "00 07 49 00 10 01 01");
        SignalMap_HEX.put("16D-P16D1S11", "00 07 49 00 10 01 11");
        SignalMap_HEX.put("16D-P16D1S21", "00 07 49 00 10 01 21");
        SignalMap_HEX.put("16D-P16D1S31", "00 07 49 00 10 01 31");
        SignalMap_HEX.put("16D-P16D1S41", "00 07 49 00 10 01 01");
        SignalMap_HEX.put("08D-P16D2S10-1^9", "00 07 49 00 08 02 10");
        SignalMap_HEX.put("08D-P16D2S11-1^9", "00 07 49 00 08 02 11");
        SignalMap_HEX.put("08D-P16D2S21", "00 07 49 00 08 02 21");
        SignalMap_HEX.put("08D-P16D2S23-1^2", "00 07 49 00 08 02 23");
        SignalMap_HEX.put("08D-P16D2S31-9^1", "00 07 49 00 08 02 31");
        SignalMap_HEX.put("08D-P16D2S81", "00 07 49 00 08 02 81");
        SignalMap_HEX.put("08D-P32D1S11", "00 07 49 00 08 01 11");
        SignalMap_HEX.put("08D-P32D1S12", "00 07 49 00 08 01 12");
        SignalMap_HEX.put("08D-P32D1S22-9<1", "00 07 49 00 08 01 22");
        SignalMap_HEX.put("08D-P32D1S31", "00 07 49 00 08 01 31");
        SignalMap_HEX.put("08D-P32D1S32", "00 07 49 00 08 01 32");
        SignalMap_HEX.put("08D-P64D1S21", "00 07 49 00 08 01 21");
        SignalMap_HEX.put("08D-P64D1S31", "00 07 49 00 08 01 31");
        SignalMap_HEX.put("08D-P64D1S41 - 11", "00 07 49 00 08 01 41");
        SignalMap_HEX.put("08D-P64D1S61", "00 07 49 00 08 01 61");
        SignalMap_HEX.put("08D-P64D1S91-11", "00 07 49 00 08 01 91");
        SignalMap_HEX.put("08D-P128D1S42", "00 07 49 00 08 01 42");
        SignalMap_HEX.put("08D-P128D1S51", "00 07 49 00 08 01 51");
        SignalMap_HEX.put("04D-P16D4S11-1^5^9^13", "00 07 49 00 04 04 11");
        SignalMap_HEX.put("04D-P16D4S21-1^5^9^13", "00 07 49 00 04 04 21");
        SignalMap_HEX.put("04D-P32D2S11", "00 07 49 00 04 02 11");
        SignalMap_HEX.put("04D-P32D2S21", "00 07 49 00 04 02 21");
        SignalMap_ASC.put("04D-P32D2S31-2<1^4<3", "00 07 49 00 04 02 31");
        SignalMap_HEX.put("04D-P32D2S41", "00 07 49 00 04 02 41");
        SignalMap_HEX.put("04D-P32D2S51", "00 07 49 00 04 02 51");
        SignalMap_HEX.put("04D-P32D2S61", "00 07 49 00 04 02 61");
        SignalMap_HEX.put("04D-P32D2S71", "00 07 49 00 04 02 71");
        SignalMap_HEX.put("04D-P64D1S11", "00 07 49 00 04 01 11");
        SignalMap_HEX.put("04D-D2-SA1-A4E8A8E8A4", "00 07 49 00 04 02 A1");
        SignalMap_HEX.put("04D-D2-SA1-A4E8A8E8", "00 07 49 00 04 02 A2");
        SignalMap_HEX.put("02D-P64D2S21", "00 07 49 00 02 02 21");
        SignalMap_HEX.put("02D-P64D2S31", "00 07 49 00 02 02 31");
        SignalMap_HEX.put("02D-P64D2S41", "00 07 49 00 02 02 41");
        SignalMap_HEX.put("02D-P128D2S21", "00 07 49 00 02 01 21");
        SignalMap_HEX.put("02D-P256D2S41", "00 07 49 00 02 02 41");
        SignalMap_HEX.put("01D-P64D4S21-4^8^12^16", "00 07 49 00 01 04 21");
        SignalMap_HEX.put("01D-P128D2S21", "00 07 49 00 01 02 21");
        SignalMap_HEX.put("01D-P128D2S31", "00 07 49 00 01 02 31");
    }
}
