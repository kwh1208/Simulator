package dbps.dbps.service;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.Setter;

@Setter
public class ASCiiDefaultSettingService {

    public static ASCiiDefaultSettingService instance = null;

    private ASCiiDefaultSettingService() {
    }

    public static ASCiiDefaultSettingService getInstance() {
        if (instance == null) {
            instance = new ASCiiDefaultSettingService();
        }
        return instance;
    }

    ChoiceBox<String> displayControl;
    ChoiceBox<String> displayMethod;
    ChoiceBox<String> charCodes;
    ChoiceBox<String> fontSize;
    ChoiceBox<String> fontGroup;
    ChoiceBox<String> effectIn;
    ChoiceBox<String> inDirection;
    ChoiceBox<String> effectOut;
    ChoiceBox<String> outDirection;
    ChoiceBox<String> sub;
    ChoiceBox<String> effectSpeed;
    ComboBox<String> effectTime;
    ChoiceBox<String> xStart;
    ChoiceBox<String> yStart;
    ChoiceBox<String> xEnd;
    ChoiceBox<String> yEnd;
    ComboBox<String> bgImg;
    ChoiceBox<String> fontColor;
    ChoiceBox<String> fontBgColor;
    TextField defaultSetting;

    //![0033/P0000/D0001/F0003/E0101/S1504/
    public void setProperties(String result){
        result = result.replace("![0033", "![0032");
        defaultSetting.setText(result);
        result = result.replace("![0032/P0000/", "");
        result = result.replaceAll("!]", "");
        String[] split = result.split("/");

        if (split[0].substring(1,3).equals("99")){
            displayControl.setValue("On");
        } else if (split[0].substring(1,3).equals("00")){
            displayControl.setValue("Off");
        } else {
            displayControl.setValue(split[0].substring(1,3));
        }

        if (split[0].substring(3,5).equals("01")){
            displayMethod.setValue("Clear");
        } else if (split[0].substring(3,5).equals("00")){
            displayMethod.setValue("Normal");
        }

        if (split[1].substring(1,3).equals("00")){
            charCodes.setValue("한글 조합형");
        }else {
            charCodes.setValue("UTF16유니코드");
        }

        if (split[1].substring(3,5).equals("01")){
            fontSize.setValue("14");
        } else if (split[1].substring(3,5).equals("03")) {
            fontSize.setValue("16(Standard)");
        } else {
            fontSize.setValue(String.valueOf(((Integer.parseInt(split[1].substring(3,5))+1)*3)));
        }

        setEffectValues(split[2].substring(1, 3), effectIn, inDirection);
        setEffectValues(split[2].substring(3, 5), effectOut, outDirection);

        if (split[3].substring(1,3).equals("05")){
            effectSpeed.setValue("5(가장 빠름)");
        } else if (split[3].substring(1,3).equals("99")){
            effectSpeed.setValue("99(가장 느림)");
        } else {
            effectSpeed.setValue(split[3].substring(1,3));
        }

        if (split[3].substring(3,5).equals("90")){
            effectTime.setValue("2분");
        } else if (split[3].substring(3,5).equals("91")) {
            effectTime.setValue("3분");
        } else if (split[3].substring(3,5).equals("92")) {
            effectTime.setValue("5분");
        } else if (split[3].substring(3,5).equals("93")) {
            effectTime.setValue("10분");
        } else if (split[3].substring(3,5).equals("94")) {
            effectTime.setValue("30분");
        } else if (split[3].substring(3,5).equals("95")) {
            effectTime.setValue("1시간");
        } else if (split[3].substring(3,5).equals("96")) {
            effectTime.setValue("3시간");
        } else if (split[3].substring(3,5).equals("97")) {
            effectTime.setValue("5시간");
        } else if (split[3].substring(3,5).equals("98")) {
            effectTime.setValue("9시간");
        } else {
            if (Integer.parseInt(split[3].substring(1,3))%2==0){
                effectTime.setValue((Integer.parseInt(split[3].substring(1,3))/2)+"초");
            } else effectTime.setValue("7.5초");
        }

        setPos(split[4].substring(1,3), xStart);
        setPos(split[4].substring(3,5), xEnd);
        setPos(split[5].substring(1,3), yStart);
        setPos(split[5].substring(3,5), yEnd);

        if (split[6].substring(1,4).equals("000")){
            bgImg.setValue("사용안함");
        }else {
            bgImg.setValue(split[6].substring(1,4));
        }

        fontColor.setValue(fontColor.getItems().get(Integer.parseInt(split[7].substring(1,2))));
        fontBgColor.setValue(fontBgColor.getItems().get(Integer.parseInt(split[8].substring(1,2))));

        fontGroup.setValue(fontGroup.getItems().get(Integer.parseInt(split[9].substring(1,2))));
    }

    public void setPos(String code, ChoiceBox<String> choiceBox){
        choiceBox.setValue(String.valueOf((Integer.parseInt(code)/4)));
    }

    public void setEffectValues(String code, ChoiceBox<String> choiceBox1, ChoiceBox<String> choiceBox2) {
        switch (code) {
            case "00":
                choiceBox1.setValue("효과없음");
                choiceBox2.setValue("");
                break;
            case "01":
                choiceBox1.setValue("정지효과");
                choiceBox2.setValue("방향없음");
                break;
            case "02":
                choiceBox1.setValue("정지효과");
                choiceBox2.setValue("밝아지기");
                break;
            case "03":
                choiceBox1.setValue("정지효과");
                choiceBox2.setValue("어두워지기");
                break;
            case "04":
                choiceBox1.setValue("정지효과");
                choiceBox2.setValue("수평 반사");
                break;
            case "05":
                choiceBox1.setValue("정지효과");
                choiceBox2.setValue("수직 반사");
                break;
            case "06":
                choiceBox1.setValue("이동하기");
                choiceBox2.setValue("왼쪽");
                break;
            case "07":
                choiceBox1.setValue("이동하기");
                choiceBox2.setValue("오른쪽");
                break;
            case "08":
                choiceBox1.setValue("이동하기");
                choiceBox2.setValue("위");
                break;
            case "09":
                choiceBox1.setValue("이동하기");
                choiceBox2.setValue("아래");
                break;
            case "12":
                choiceBox1.setValue("닦아내기");
                choiceBox2.setValue("왼쪽");
                break;
            case "13":
                choiceBox1.setValue("닦아내기");
                choiceBox2.setValue("오른쪽");
                break;
            case "14":
                choiceBox1.setValue("닦아내기");
                choiceBox2.setValue("위");
                break;
            case "15":
                choiceBox1.setValue("닦아내기");
                choiceBox2.setValue("아래");
                break;
            case "18":
                choiceBox1.setValue("블라인드");
                choiceBox2.setValue("왼쪽");
                break;
            case "19":
                choiceBox1.setValue("블라인드");
                choiceBox2.setValue("오른쪽");
                break;
            case "20":
                choiceBox1.setValue("블라인드");
                choiceBox2.setValue("위");
                break;
            case "21":
                choiceBox1.setValue("블라인드");
                choiceBox2.setValue("아래");
                break;
            case "24":
                choiceBox1.setValue("커튼효과");
                choiceBox2.setValue("수평밖으로");
                break;
            case "25":
                choiceBox1.setValue("커튼효과");
                choiceBox2.setValue("수평안으로");
                break;
            case "26":
                choiceBox1.setValue("커튼효과");
                choiceBox2.setValue("수직밖으로");
                break;
            case "27":
                choiceBox1.setValue("커튼효과");
                choiceBox2.setValue("수직안으로");
                break;
            case "35":
                choiceBox1.setValue("확대효과");
                choiceBox2.setValue("왼쪽");
                break;
            case "36":
                choiceBox1.setValue("확대효과");
                choiceBox2.setValue("오른쪽");
                break;
            case "37":
                choiceBox1.setValue("확대효과");
                choiceBox2.setValue("위쪽");
                break;
            case "38":
                choiceBox1.setValue("확대효과");
                choiceBox2.setValue("아래쪽");
                break;
            case "39":
                choiceBox1.setValue("확대효과");
                choiceBox2.setValue("오른쪽아래로");
                break;
            case "40":
                choiceBox1.setValue("회전효과");
                choiceBox2.setValue("시계반대1");
                break;
            case "41":
                choiceBox1.setValue("회전효과");
                choiceBox2.setValue("시계방향1");
                break;
            case "42":
                choiceBox1.setValue("회전효과");
                choiceBox2.setValue("시계반대2");
                break;
            case "43":
                choiceBox1.setValue("회전효과");
                choiceBox2.setValue("시계방향2");
                break;
            case "44":
                choiceBox1.setValue("배경깜빡이기");
                choiceBox2.setValue("빨간색");
                break;
            case "45":
                choiceBox1.setValue("배경깜빡이기");
                choiceBox2.setValue("초록색");
                break;
            case "46":
                choiceBox1.setValue("배경깜빡이기");
                choiceBox2.setValue("파란색");
                break;
            case "47":
                choiceBox1.setValue("배경깜빡이기");
                choiceBox2.setValue("흰색");
                break;
            case "48":
                choiceBox1.setValue("배경깜빡이기");
                choiceBox2.setValue("전체(순차적)");
                break;
            case "49":
                choiceBox1.setValue("색상깜빡이기");
                choiceBox2.setValue("빨간색");
                break;
            case "50":
                choiceBox1.setValue("색상깜빡이기");
                choiceBox2.setValue("초록색");
                break;
            case "51":
                choiceBox1.setValue("색상깜빡이기");
                choiceBox2.setValue("파란색");
                break;
            case "52":
                choiceBox1.setValue("색상깜빡이기");
                choiceBox2.setValue("흰색");
                break;
            case "53":
                choiceBox1.setValue("색상깜빡이기");
                choiceBox2.setValue("전체(순차적)");
                break;
            case "55":
                choiceBox1.setValue("색상깜빡이기");
                choiceBox2.setValue("전체(동시에)");
                break;
            case "122":
                choiceBox1.setValue("전체 효과");
                choiceBox2.setValue("");
                break;
            case "54":
                choiceBox1.setValue("3D 효과");
                choiceBox2.setValue("왼쪽");
                break;
            default:
                choiceBox1.setValue("알 수 없음");
                choiceBox2.setValue("");
                break;
        }
    }
}
