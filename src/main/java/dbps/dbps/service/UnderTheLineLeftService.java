package dbps.dbps.service;

import javafx.scene.control.TextField;
import lombok.Setter;

@Setter
public class UnderTheLineLeftService {
    private static UnderTheLineLeftService instance = null;

    private UnderTheLineLeftService() {
    }

    public static UnderTheLineLeftService getInstance(){
        if(instance == null) instance = new UnderTheLineLeftService();
        return instance;
    }

    public TextField timeBoard;

    public void setTime(String time){
        timeBoard.setText(time);
    }

}