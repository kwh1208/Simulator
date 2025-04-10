package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PacketSettingService {

    private static PacketSettingService instance;

    public static PacketSettingService getInstance() {
        if (instance==null){
            instance = new PacketSettingService();
        }
        return instance;
    }

    public PacketSettingService() {
    }

    //다빛넷의 textField
    public TextField oriAscFirst;
    public TextField oriAscSecond;
    public TextField oriHexFirst;
    public TextField oriHexSecond;
    public TextField oriTimeOut;

    //모달창의 textField
    public TextField ascFirst;
    public TextField ascSecond;
    public TextField hexFirst;
    public TextField hexSecond;
    public TextField timeOut;

    public void setUI(){
        ascFirst.setText(oriAscFirst.getText());
        ascSecond.setText(oriAscSecond.getText());
        hexFirst.setText(oriHexFirst.getText());
        hexSecond.setText(oriHexSecond.getText());
        timeOut.setText(oriTimeOut.getText());
    }


    public void changeUI() {
        oriAscFirst.setText(ascFirst.getText());
        oriAscSecond.setText(ascSecond.getText());
        hexFirst.setText(hexFirst.getText());
        hexSecond.setText(hexSecond.getText());
        timeOut.setText(timeOut.getText());
    }
}
