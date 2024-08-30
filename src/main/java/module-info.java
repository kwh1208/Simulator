module dbps.dbps {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires org.slf4j;
    requires static lombok;
    requires jdk.jshell;
    requires java.desktop;
    requires com.sun.jna;

    opens dbps.dbps to javafx.fxml;
    opens dbps.dbps.controller to javafx.fxml;
    exports dbps.dbps;
    exports dbps.dbps.controller;
}