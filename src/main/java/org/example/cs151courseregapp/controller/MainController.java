package org.example.cs151courseregapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController {

    @FXML
    private void goToAdminView(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/cs151courseregapp/view/admin-view.fxml");
    }

    @FXML
    private void goToProfessorView(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/cs151courseregapp/view/professor-view.fxml");
    }

    @FXML
    private void goToStudentView(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/cs151courseregapp/view/student-view.fxml");
    }

    // 🔹 reusable method to switch scenes
    private void switchScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
