module dbps.dbps {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fazecast.jSerialComm;
    requires static lombok;
    requires java.desktop;
    requires org.fxmisc.richtext;

    opens dbps.dbps to javafx.fxml;
    opens dbps.dbps.controller to javafx.fxml;
    exports dbps.dbps;
    exports dbps.dbps.controller;
}