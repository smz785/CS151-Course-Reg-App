package org.example.cs151courseregapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class StudentController {

    @FXML
    private ListView<String> availableCourses;

    @FXML
    private ListView<String> registeredCourses;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        // sample available courses
        availableCourses.getItems().add("CS 46B - Intro to Data Structtures");
        availableCourses.getItems().add("CS 151 - Object-Oriented Design");
        availableCourses.getItems().add("MATH 42 - Discrete Math");

        messageLabel.setText("Select a course to register.");
    }

    @FXML
    private void registerCourse() {
        String selectedCourse = availableCourses.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            messageLabel.setText("Please select a course first.");
            return;
        }

        if (!registeredCourses.getItems().contains(selectedCourse)) {
            registeredCourses.getItems().add(selectedCourse);
            messageLabel.setText("Registered for " + selectedCourse);
        } else {
            messageLabel.setText("Already registered for this course.");
        }
    }

    @FXML
    private void dropCourse() {
        String selectedCourse = registeredCourses.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            messageLabel.setText("Please select a course to drop.");
            return;
        }

        registeredCourses.getItems().remove(selectedCourse);
        messageLabel.setText("Dropped " + selectedCourse);
    }
}
