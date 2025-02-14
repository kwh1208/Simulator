module DBPS.main {
    requires com.fasterxml.jackson.databind;
    requires com.fazecast.jSerialComm;
    requires java.datatransfer;
    requires javafx.base;
    requires javafx.controls;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires org.fxmisc.richtext;
    requires org.slf4j;

    exports dbps.dbps;
    opens dbps.dbps.controller to javafx.fxml;
}