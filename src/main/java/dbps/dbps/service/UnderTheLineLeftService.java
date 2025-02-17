package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Setter;

public class UnderTheLineLeftService {
    private static UnderTheLineLeftService instance = null;

    private UnderTheLineLeftService() {
    }

    public static UnderTheLineLeftService getInstance(){
        if(instance == null) instance = new UnderTheLineLeftService();
        return instance;
    }

    @Setter
    public TextField timeBoard;

    public void setTime(String time){
        System.out.println(time);
        timeBoard.setText(time);
    }

}