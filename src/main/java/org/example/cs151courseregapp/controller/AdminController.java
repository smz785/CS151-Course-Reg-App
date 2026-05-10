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
    private ListView<String> waitlistedStudents;

    @FXML
    private ListView<String> enrolledStudents;

    
    @FXML
    private ListView<String> professors;

    @FXML
    private ListView<String> sections;

    @FXML
    private ListView<String> assignedSections;

    
    @FXML
    private TextField scheduleField;

    @FXML
    public void initialize() {
        // Courses
        courseList.getItems().addAll("CS 46B", "CS 151", "MATH 42");

        // Waitlist example
        waitlistedStudents.getItems().addAll("Alice", "Bob", "Charlie");

        // Professors
        professors.getItems().addAll("Prof. Smith", "Prof. Lee");

        // Sections
        sections.getItems().addAll("CS151 - Section 1", "CS151 - Section 2");
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

    
    @FXML
    private void enrollWaitlistedStudent() {
        String student = waitlistedStudents.getSelectionModel().getSelectedItem();

        if (student != null) {
            waitlistedStudents.getItems().remove(student);
            enrolledStudents.getItems().add(student);
        }
    }


    @FXML
    private void assignProfessorToSection() {
        String professor = professors.getSelectionModel().getSelectedItem();
        String section = sections.getSelectionModel().getSelectedItem();

        if (professor != null && section != null) {
            assignedSections.getItems().add(professor + " -> " + section);
        }
    }

  
    @FXML
    private void unassignProfessorFromSection() {
        String selected = assignedSections.getSelectionModel().getSelectedItem();

        if (selected != null) {
            assignedSections.getItems().remove(selected);
        }
    }


    @FXML
    private void changeSectionSchedule() {
        String section = sections.getSelectionModel().getSelectedItem();
        String newSchedule = scheduleField.getText();

        if (section != null && newSchedule != null && !newSchedule.isEmpty()) {
            int index = sections.getItems().indexOf(section);
            sections.getItems().set(index, section + " (" + newSchedule + ")");
            scheduleField.clear();
        }
    }
}
