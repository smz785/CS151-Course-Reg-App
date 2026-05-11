package org.example.cs151courseregapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.cs151courseregapp.MainApp;
import org.example.cs151courseregapp.model.*;
import org.example.cs151courseregapp.service.RegistrationResult;
import org.example.cs151courseregapp.service.RegistrationService;
import org.example.cs151courseregapp.service.ScheduleConflictChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private final UniversityData universityData = UniversityData.getInstance();

    private final RegistrationService registrationService = new RegistrationService(
            universityData,
            new ScheduleConflictChecker()
    );

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
    private Button dropSelectedButton;

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
                    return;
                }

                Enrollment enrollment = findCurrentEnrollmentForSection(section);
                String status = enrollment == null ? "Unknown" : enrollment.getStatusName().name();

                setText(
                        section.getCourse().getCourseCode()
                                + " - "
                                + section.getSectionId()
                                + " | "
                                + status
                                + " | "
                                + section.getTimeSlot().getDisplayText()
                );
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
            return new SimpleStringProperty(professor == null ? "Unassigned" : professor.getName());
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

        setupActionColumn();
    }

    private void setupActionColumn() {
        actionColumn.setCellValueFactory(data -> new SimpleStringProperty(""));

        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button actionButton = new Button();

            {
                actionButton.setMaxWidth(Double.MAX_VALUE);

                actionButton.setOnAction(event -> {
                    Section section = getTableView().getItems().get(getIndex());
                    registerOrWaitlist(section);
                });
            }

            @Override
            protected void updateItem(String ignored, boolean empty) {
                super.updateItem(ignored, empty);

                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                    return;
                }

                Section section = getTableView().getItems().get(getIndex());

                if (currentStudent == null) {
                    actionButton.setText("Load Student");
                    actionButton.setDisable(true);
                } else if (currentStudent.hasCurrentEnrollmentIn(section)) {
                    actionButton.setText("Added");
                    actionButton.setDisable(true);
                } else if (section.hasAvailableSeat()) {
                    actionButton.setText("Register");
                    actionButton.setDisable(false);
                } else {
                    actionButton.setText("Waitlist");
                    actionButton.setDisable(false);
                }

                setGraphic(actionButton);
            }
        });
    }

    private void registerOrWaitlist(Section section) {
        if (currentStudent == null) {
            showMessage("Load a student before registering.");
            return;
        }

        RegistrationResult result = registrationService.regStudent(currentStudent, section);

        switch (result) {
            case REGISTERED -> showMessage(
                    "Registered for "
                            + section.getCourse().getCourseCode()
                            + " section "
                            + section.getSectionId()
                            + "."
            );

            case WAITLISTED -> showMessage(
                    "Section is full. Added to waitlist for "
                            + section.getCourse().getCourseCode()
                            + " section "
                            + section.getSectionId()
                            + "."
            );

            case DUPLICATE_ENROLLMENT -> showMessage(
                    "You are already registered or waitlisted for this section."
            );

            case SCHEDULE_CONFLICT -> showMessage(
                    "Cannot register: this section conflicts with your current schedule."
            );

            case INVALID_INPUT -> showMessage(
                    "Cannot register: invalid student or section."
            );
        }

        refreshStudentPage();
    }

    @FXML
    private void dropSelectedClass() {
        if (currentStudent == null) {
            showMessage("Load a student before dropping a class.");
            return;
        }

        Section selectedSection = classesListView.getSelectionModel().getSelectedItem();

        if (selectedSection == null) {
            showMessage("Select a class from My Classes first.");
            return;
        }

        boolean dropped = registrationService.dropStudent(currentStudent, selectedSection);

        if (dropped) {
            showMessage(
                    "Dropped "
                            + selectedSection.getCourse().getCourseCode()
                            + " section "
                            + selectedSection.getSectionId()
                            + "."
            );
        } else {
            showMessage("Could not drop the selected class.");
        }

        refreshStudentPage();
    }

    private void refreshStudentPage() {
        if (currentStudent == null) {
            classesListView.getItems().clear();
            coursesTable.getItems().clear();
            return;
        }

        classesListView.getItems().setAll(getCurrentStudentActiveOrWaitlistedSections());
        coursesTable.getItems().setAll(getAvailableSectionsForCurrentStudent());
        coursesTable.refresh();
    }

    private List<Section> getCurrentStudentActiveOrWaitlistedSections() {
        List<Section> sections = new ArrayList<>();

        for (Enrollment enrollment : currentStudent.getEnrollments()) {
            if (enrollment.isActive() || enrollment.isWaitlisted()) {
                sections.add(enrollment.getSection());
            }
        }

        return sections;
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

    private Enrollment findCurrentEnrollmentForSection(Section section) {
        if (currentStudent == null || section == null) {
            return null;
        }

        for (Enrollment enrollment : currentStudent.getEnrollments()) {
            if ((enrollment.isActive() || enrollment.isWaitlisted())
                    && enrollment.getSection().equals(section)) {
                return enrollment;
            }
        }

        return null;
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