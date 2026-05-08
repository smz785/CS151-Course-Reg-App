module org.example.cs151courseregapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;



    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.cs151courseregapp to javafx.fxml;
    opens org.example.cs151courseregapp.controller to javafx.fxml;
    exports org.example.cs151courseregapp;
    exports org.example.cs151courseregapp.controller;
    exports org.example.cs151courseregapp.model;
    exports org.example.cs151courseregapp.service;
}