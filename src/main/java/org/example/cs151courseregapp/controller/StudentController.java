package org.example.cs151courseregapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.cs151courseregapp.model.*;

public class StudentController {

    private final UniversityData universityData = UniversityData.getInstance();

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
    public void initialize() {
        setupClassesListView();
        setupCoursesTable();

        classesListView.getItems().setAll(universityData.getAllSections());
        coursesTable.getItems().setAll(universityData.getAllSections());
    }

    private void setupClassesListView() {
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

            if (section.hasAvailableSeat()) {
                return new SimpleStringProperty("Register");
            }

            return new SimpleStringProperty("Waitlist");
        });
    }
}
