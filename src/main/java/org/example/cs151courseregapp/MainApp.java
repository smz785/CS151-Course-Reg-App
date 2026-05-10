package org.example.cs151courseregapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    private static Stage primaryStage;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 440;
    private static final String VIEW_PATH = "view/";

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        Scene scene = loadScene("main-view.fxml");

        stage.setTitle("Course Registration App");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(String fxmlFile) throws IOException {
        primaryStage.setScene(loadScene(fxmlFile));
    }

    private static Scene loadScene(String fxmlFile) throws IOException {
        URL fxmlUrl = MainApp.class.getResource(VIEW_PATH + fxmlFile);

        if (fxmlUrl == null) {
            throw new IOException("FXML file not found: " + VIEW_PATH + fxmlFile);
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        return new Scene(loader.load(), WIDTH, HEIGHT);
    }

    public static void main(String[] args) {
        launch(args);
    }
}