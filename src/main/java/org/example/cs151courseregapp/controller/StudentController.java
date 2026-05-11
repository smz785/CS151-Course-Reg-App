package org.example.cs151courseregapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.cs151courseregapp.MainApp;
import org.example.cs151courseregapp.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private final UniversityData universityData = UniversityData.getInstance();

    private Student currentStudent;

    @FXML
    private ListView<Section> classesListView;

    @FXML
    private TableView<Section> coursesTable;

    @FXML
    private TableColumn<Section, String> courseColumn;

    @FXML
    private TableColumn<Section, String> idColumn;

    @FXML
    private TableColumn<Section, String> instructorColumn;

    @FXML
    private TableColumn<Section, String> typeColumn;

    @FXML
    private TableColumn<Section, String> locationColumn;

    @FXML
    private TableColumn<Section, String> roomColumn;

    @FXML
    private TableColumn<Section, String> timeslotColumn;

    @FXML
    private TableColumn<Section, String> capacityColumn;

    @FXML
    private TableColumn<Section, String> actionColumn;

    @FXML
    private TextField studentIdField;

    @FXML
    public void initialize() {
        setupClassesListView();
        setupCoursesTable();

        studentIdField.setText("STD001");
        loadStudentById();
    }

    @FXML
    private void loadStudentById() {
        String studentId = studentIdField.getText();

        if (studentId == null || studentId.trim().isEmpty()) {
            showMessage("Enter a student ID.");
            return;
        }

        Student student = universityData.findStudentById(studentId.trim());

        if (student == null) {
            showMessage("Student not found: " + studentId);
            classesListView.getItems().clear();
            coursesTable.getItems().clear();
            currentStudent = null;
            return;
        }

        currentStudent = student;
        refreshStudentPage();
    }

    private void setupClassesListView() {
        classesListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Section section, boolean empty) {
                super.updateItem(section, empty);

                if (empty || section == null) {
                    setText(null);
                } else {
                    setText(
                            section.getCourse().getCourseCode()
                                    + " - "
                                    + section.getSectionId()
                                    + " | "
                                    + section.getTimeSlot().getDisplayText()
                    );
                }
            }
        });
    }

    private void setupCoursesTable() {
        courseColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCourse().getCourseCode())
        );

        idColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getSectionId())
        );

        instructorColumn.setCellValueFactory(data -> {
            Professor professor = data.getValue().getProfessor();

            if (professor == null) {
                return new SimpleStringProperty("Unassigned");
            }

            return new SimpleStringProperty(professor.getName());
        });

        typeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getSectionType())
        );

        locationColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLocation())
        );

        roomColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLocation())
        );

        timeslotColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTimeSlot().getDisplayText())
        );

        capacityColumn.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getEnrollmentCount()
                                + " / "
                                + data.getValue().getSeatCapacity()
                )
        );

        actionColumn.setCellValueFactory(data -> {
            Section section = data.getValue();

            if (currentStudent == null) {
                return new SimpleStringProperty("Load Student");
            }

            if (currentStudent.hasCurrentEnrollmentIn(section)) {
                return new SimpleStringProperty("Already Added");
            }

            if (section.hasAvailableSeat()) {
                return new SimpleStringProperty("Register");
            }

            return new SimpleStringProperty("Waitlist");
        });
    }

    private void refreshStudentPage() {
        if (currentStudent == null) {
            classesListView.getItems().clear();
            coursesTable.getItems().clear();
            return;
        }

        classesListView.getItems().setAll(currentStudent.getEnrolledSections());
        coursesTable.getItems().setAll(getAvailableSectionsForCurrentStudent());
        coursesTable.refresh();
    }

    private List<Section> getAvailableSectionsForCurrentStudent() {
        List<Section> availableSections = new ArrayList<>();

        for (Section section : universityData.getAllSections()) {
            if (!currentStudent.hasCurrentEnrollmentIn(section)) {
                availableSections.add(section);
            }
        }

        return availableSections;
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Page");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBackToMain() throws IOException {
        MainApp.switchScene("main-view.fxml");
    }
}