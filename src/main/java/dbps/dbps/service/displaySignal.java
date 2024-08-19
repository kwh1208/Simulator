package dbps.dbps.service;

import java.util.HashMap;
import java.util.Map;
//표출신호별 전송 프로토콜 저장
public class displaySignal {
    public static Map<String, String> SignalMap = new HashMap<>();
    public void tmp(){
        SignalMap.put("32D-P16D1S11", "0052B11111");
        SignalMap.put("16D-P16D1S10-1", "0052A11011");
        SignalMap.put("16D-P16D1S11", "0052A11111");
        SignalMap.put("16D-P16D1S21", "0052A12111");
        SignalMap.put("16D-P16D1S31", "0052A13111");
        SignalMap.put("16D-P16D1S41", "0052A14111");
        SignalMap.put("08D-P16D2S10 - 1^9", "0052821012");
        SignalMap.put("08D-P16D2S11 - 1^9", "0052821111");
        SignalMap.put("08D-P16D2S21", "0052822111");
        SignalMap.put("08D-P16D2S23 - 1^2", "0052822311");
        SignalMap.put("08D-P16D2S23 - 9^1", "0052823111");
        SignalMap.put("08D-P16D2S81", "0052828111");
        SignalMap.put("08D-P32D1S11", "0052811111");
        SignalMap.put("08D-P32D1S12", "0052811211");
        SignalMap.put("08D-P32D1S22 - 9 < 1", "0052812211");
        SignalMap.put("08D-P32D1S31", "0052813111");
        SignalMap.put("08D-P32D1S32", "0052813211");
        SignalMap.put("08D-P64D1S21", "0052812111");
        SignalMap.put("08D-P64D1S31", "0052813111");
        SignalMap.put("08D-P64D1S41 - 11", "0052814111");
        SignalMap.put("08D-P64D1S61", "0052816111");
        SignalMap.put("08D-P64D1S91 - 11", "0052819111");
        SignalMap.put("08D-P128D1S42", "0052814211");
        SignalMap.put("08D-P128D1S51", "0052815111");
        SignalMap.put("04D-P16D4S11-1^5^9^13", "0052441111");
        SignalMap.put("04D-P16D4S21-1^5^9^13", "0052442111");
        SignalMap.put("04D-P32D4S11", "0052421111");
        SignalMap.put("04D-P32D4S21", "0052422111");
        SignalMap.put("04D-P32D2S31 - 2 < 1 ^ 4 < 3", "0052423111");
        SignalMap.put("04D-P32D2S41", "0052441111");
        SignalMap.put("04D-P32D2S51", "0052425111");
        SignalMap.put("04D-P32D2S61", "0052426111");
        SignalMap.put("04D-P32D2S71", "0052427111");
        SignalMap.put("04D-P64D1S11", "0052411111");
        SignalMap.put("04D-D2-SA1-A4E8A8E8A4", "005242:113");
        SignalMap.put("04D-D2-SA1-A4E8A8E8", "005242:211");
        SignalMap.put("02D-P128D2S21", "0052212111");
        SignalMap.put("02D-P64D2S21", "0052222111");
        SignalMap.put("02D-P64D2S31", "0052223111");
        SignalMap.put("02D-P64D2S41", "0052224111");
        SignalMap.put("02D-P256D2S21", "0052224114");
        SignalMap.put("01D-P64D4S21-4^8^12^16", "0052142111");
        SignalMap.put("01D-P128D2S21", "0052122111");
        SignalMap.put("01D-P128D2S31", "0052123111");
    }
}
