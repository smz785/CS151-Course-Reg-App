package org.example.cs151courseregapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AdminController {

    @FXML
    private TextField courseNameField;

    @FXML
    private ListView<String> courseList;

    @FXML
    public void initialize() {
        // sample data
        courseList.getItems().add("CS 46B");
        courseList.getItems().add("CS 151");
        courseList.getItems().add("MATH 42");
    }

    @FXML
    private void addCourse() {
        String courseName = courseNameField.getText();

        if (courseName != null && !courseName.trim().isEmpty()) {
            courseList.getItems().add(courseName.trim());
            courseNameField.clear();
        }
    }

    @FXML
    private void removeCourse() {
        String selectedCourse = courseList.getSelectionModel().getSelectedItem();

        if (selectedCourse != null) {
            courseList.getItems().remove(selectedCourse);
        }
    }
}
