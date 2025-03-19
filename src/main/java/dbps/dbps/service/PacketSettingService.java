package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

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

    @Setter
    @Getter
    public  TextField oriAscFirst;
    @Setter
    @Getter
    public  TextField oriAscSecond;
    @Setter
    @Getter
    public  TextField oriHexFirst;
    @Setter
    @Getter
    public  TextField oriHexSecond;
    @Setter
    @Getter
    public  TextField oriTimeOut;
}
