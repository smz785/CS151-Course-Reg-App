package org.example.cs151courseregapp.controller;

import javafx.fxml.FXML;
import org.example.cs151courseregapp.MainApp;

import java.io.IOException;

public class MainController {

    @FXML
    private void goToAdminView() throws IOException {
        MainApp.switchScene("admin-view.fxml");
    }

    @FXML
    private void goToProfessorView() throws IOException {
        MainApp.switchScene("professor-view.fxml");
    }

    @FXML
    private void goToStudentView() throws IOException {
        MainApp.switchScene("student-view.fxml");
    }
}