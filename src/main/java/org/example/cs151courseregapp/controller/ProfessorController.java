package org.example.cs151courseregapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.cs151courseregapp.model.*;

public class ProfessorController {

    private final UniversityData universityData = UniversityData.getInstance();

    @FXML
    private ListView<Section> classesListView;

    @FXML
    private Label classTypeLabel;

    @FXML
    private Label buildingPlatformLabel;

    @FXML
    private Label roomMeetingLinkLabel;

    @FXML
    private Label timeslotLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private TableView<Enrollment> enrolledStudentsTable;

    @FXML
    private TableColumn<Enrollment, String> enrolledNameColumn;

    @FXML
    private TableColumn<Enrollment, String> enrolledIdColumn;

    @FXML
    private TableView<Enrollment> waitlistedStudentsTable;

    @FXML
    private TableColumn<Enrollment, String> waitlistedNameColumn;

    @FXML
    private TableColumn<Enrollment, String> waitlistedIdColumn;

    @FXML
    private TableColumn<Enrollment, String> waitlistPositionColumn;

    @FXML
    public void initialize() {
        classesListView.getItems().setAll(universityData.getAllSections());

        classesListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Section section, boolean empty) {
                super.updateItem(section, empty);

                if (empty || section == null) {
                    setText(null);
                } else {
                    setText(section.getCourse().getCourseCode() + " - " + section.getSectionId());
                }
            }
        });

        classesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSection, newSection) -> showSectionInfo(newSection)
        );

        setupEnrolledTable();
        setupWaitlistedTable();

        if (!classesListView.getItems().isEmpty()) {
            classesListView.getSelectionModel().selectFirst();
        }
    }

    private void setupEnrolledTable() {
        enrolledNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getName())
        );

        enrolledIdColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getStudentId())
        );
    }

    private void setupWaitlistedTable() {
        waitlistedNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getName())
        );

        waitlistedIdColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getStudentId())
        );

        waitlistPositionColumn.setCellValueFactory(data -> {
            int position = waitlistedStudentsTable.getItems().indexOf(data.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(position));
        });
    }

    private void showSectionInfo(Section section) {
        if (section == null) {
            return;
        }

        classTypeLabel.setText(section.getSectionType());
        buildingPlatformLabel.setText(section.getLocation());
        roomMeetingLinkLabel.setText(section.getLocation());
        timeslotLabel.setText(section.getTimeSlot().getDisplayText());
        capacityLabel.setText(section.getEnrollmentCount() + " / " + section.getSeatCapacity());

        refreshEnrollmentTables(section);
    }

    private void refreshEnrollmentTables(Section section) {
        enrolledStudentsTable.getItems().clear();
        waitlistedStudentsTable.getItems().clear();

        for (Enrollment enrollment : section.getEnrollments()) {
            if (enrollment.isWaitlisted()) {
                waitlistedStudentsTable.getItems().add(enrollment);
            } else if (enrollment.isActive()) {
                enrolledStudentsTable.getItems().add(enrollment);
            }
        }
    }
}
