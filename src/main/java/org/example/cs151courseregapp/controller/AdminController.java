package org.example.cs151courseregapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.cs151courseregapp.model.Course;
import org.example.cs151courseregapp.model.UniversityData;

public class AdminController {

    private final UniversityData universityData = UniversityData.getInstance();

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField creditsField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ListView<String> courseList;

    @FXML
    public void initialize() {
        refreshCourseList();
    }

    private void refreshCourseList() {
        courseList.getItems().clear();

        for (Course course : universityData.getAllCourses()) {
            courseList.getItems().add(course.getCourseCode() + " - " + course.getTitle());
        }
    }

    @FXML
    private void addCourse() {
        String code = courseCodeField.getText();
        String title = titleField.getText();
        String creditsText = creditsField.getText();
        String description = descriptionField.getText();

        if (code != null && title != null && creditsText != null &&
            !code.isEmpty() && !title.isEmpty() && !creditsText.isEmpty()) {

            try {
                int credits = Integer.parseInt(creditsText);

                Course course = new Course(code.trim(), title.trim(), credits, description.trim());
                universityData.addCourse(course);

                refreshCourseList();

                courseCodeField.clear();
                titleField.clear();
                creditsField.clear();
                descriptionField.clear();

            } catch (NumberFormatException e) {
                System.out.println("Credits must be a number.");
            }
        }
    }

    @FXML
    private void removeCourse() {
        String selected = courseList.getSelectionModel().getSelectedItem();

        if (selected != null) {
            courseList.getItems().remove(selected);
        }
    }
}
