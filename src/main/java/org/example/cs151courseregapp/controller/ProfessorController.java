package org.example.cs151courseregapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ProfessorController {

    @FXML
    private ListView<String> courseList;

    @FXML
    private ListView<String> studentList;

    @FXML
    private TextField gradeField;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        courseList.getItems().add("CS 46B - Intro to Data Structures");
        courseList.getItems().add("CS 151 - Object-Oriented Design");
        courseList.getItems().add("MATH 42 - Discrete Math");
    }

    @FXML
    private void viewStudents() {
        studentList.getItems().clear();

        String selectedCourse = courseList.getSelectionModel().getSelectedItem();

        if (selectedCourse == null) {
            messageLabel.setText("Please select a course first.");
            return;
        }

        if (selectedCourse.contains("CS 151")) {
            studentList.getItems().add("Alice Johnson");
            studentList.getItems().add("Brian Lee");
            studentList.getItems().add("Sara Patel");
        } else if (selectedCourse.contains("CS151")) {
            studentList.getItems().add("Daniel Kim");
            studentList.getItems().add("Maya Singh");
            studentList.getItems().add("Jordan Smith");
        } else if (selectedCourse.contains("MATH101")) {
            studentList.getItems().add("Emma Wilson");
            studentList.getItems().add("Noah Brown");
        }

        messageLabel.setText("Students loaded for " + selectedCourse);
    }

    @FXML
    private void submitGrade() {
        String selectedStudent = studentList.getSelectionModel().getSelectedItem();
        String grade = gradeField.getText();

        if (selectedStudent == null) {
            messageLabel.setText("Please select a student first.");
            return;
        }

        if (grade == null || grade.trim().isEmpty()) {
            messageLabel.setText("Please enter a grade.");
            return;
        }

        messageLabel.setText("Grade " + grade.trim() + " submitted for " + selectedStudent);
        gradeField.clear();
    }
}

