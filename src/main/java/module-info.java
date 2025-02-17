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
    requires org.eclipse.paho.client.mqttv3;

    // ✅ 명시적으로 패키지 exports
    exports dbps.dbps;
    exports dbps.dbps.controller;
    exports dbps.dbps.service;
    exports dbps.dbps.service.connectManager;

    // ✅ JavaFX FXML에서 Controller 로딩을 허용
    opens dbps.dbps.controller to javafx.fxml;
}
