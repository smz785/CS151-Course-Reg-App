package org.example.cs151courseregapp.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.cs151courseregapp.model.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class AdminController {

    private final UniversityData universityData = UniversityData.getInstance();

    @FXML
    private ListView<Section> classesListView;

    @FXML
    private Label instructorNameLabel;

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
    private TableColumn<Enrollment, String> enrolledActionColumn;

    @FXML
    private TableView<Enrollment> waitlistedStudentsTable;

    @FXML
    private TableColumn<Enrollment, String> waitlistedNameColumn;

    @FXML
    private TableColumn<Enrollment, String> waitlistedIdColumn;

    @FXML
    private TableColumn<Enrollment, String> waitlistedActionColumn;

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

        enrolledActionColumn.setCellValueFactory(data ->
                new SimpleStringProperty("Active")
        );
    }

    private void setupWaitlistedTable() {
        waitlistedNameColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getName())
        );

        waitlistedIdColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStudent().getStudentId())
        );


        waitlistedActionColumn.setCellValueFactory(data ->
                new SimpleStringProperty("Double-click to enroll")
        );

        waitlistedStudentsTable.setRowFactory(table -> {
            TableRow<Enrollment> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    enrollWaitlistedStudent(row.getItem());
                }
            });

            return row;
        });
    }

    private void showSectionInfo(Section section) {
        if (section == null) {
            return;
        }

        Professor professor = section.getProfessor();

        if (professor == null) {
            instructorNameLabel.setText("Unassigned");
        } else {
            instructorNameLabel.setText(professor.getName());
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

    @FXML
    private void assignUnassignInstructor() {
        Section selectedSection = classesListView.getSelectionModel().getSelectedItem();

        if (selectedSection == null) {
            showMessage("Please select a class first.");
            return;
        }

        if (selectedSection.getProfessor() != null) {
            selectedSection.setProfessor(null);
            showSectionInfo(selectedSection);
            showMessage("Instructor unassigned.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Assign Instructor");
        dialog.setHeaderText("Enter professor ID:");
        dialog.setContentText("Professor ID:");

        dialog.showAndWait().ifPresent(professorId -> {
            Professor professor = universityData.findProfessorById(professorId.trim());

            if (professor == null) {
                showMessage("Professor not found.");
                return;
            }

            selectedSection.setProfessor(professor);
            showSectionInfo(selectedSection);
            showMessage("Instructor assigned.");
        });
    }

    @FXML
    private void changeClassTime() {
        Section selectedSection = classesListView.getSelectionModel().getSelectedItem();

        if (selectedSection == null) {
            showMessage("Please select a class first.");
            return;
        }

        Dialog<Set<Days>> daysDialog = new Dialog<>();
        daysDialog.setTitle("Change Class Days");
        daysDialog.setHeaderText("Select class days:");

        VBox dayOptions = new VBox(8);

        for (Days day : Days.values()) {
            CheckBox checkBox = new CheckBox(day.name());
            checkBox.setUserData(day);
            dayOptions.getChildren().add(checkBox);
        }

        daysDialog.getDialogPane().setContent(dayOptions);
        daysDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        daysDialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                Set<Days> selectedDays = new HashSet<>();

                for (Node node : dayOptions.getChildren()) {
                    CheckBox checkBox = (CheckBox) node;

                    if (checkBox.isSelected()) {
                        selectedDays.add((Days) checkBox.getUserData());
                    }
                }

                return selectedDays;
            }

            return null;
        });

        daysDialog.showAndWait().ifPresent(days -> {
            if (days.isEmpty()) {
                showMessage("Please select at least one day.");
                return;
            }

            TextInputDialog startDialog = new TextInputDialog();
            startDialog.setTitle("Start Time");
            startDialog.setHeaderText("Enter start time:");
            startDialog.setContentText("Example: 09:00");

            startDialog.showAndWait().ifPresent(startText -> {
                TextInputDialog endDialog = new TextInputDialog();
                endDialog.setTitle("End Time");
                endDialog.setHeaderText("Enter end time:");
                endDialog.setContentText("Example: 10:15");

                endDialog.showAndWait().ifPresent(endText -> {
                    try {
                        LocalTime startTime = LocalTime.parse(startText.trim());
                        LocalTime endTime = LocalTime.parse(endText.trim());

                        TimeSlot newTimeSlot = new TimeSlot(days, startTime, endTime);
                        selectedSection.setTimeSlot(newTimeSlot);

                        showSectionInfo(selectedSection);
                        showMessage("Class time updated.");

                    } catch (Exception e) {
                        showMessage("Invalid time format. Use HH:MM, like 09:00.");
                    }
                });
            });
        });
    }

    private void enrollWaitlistedStudent(Enrollment enrollment) {
        Section section = enrollment.getSection();

        if (!section.hasAvailableSeat()) {
            showMessage("No available seats in this section.");
            return;
        }

        enrollment.activate();

        refreshEnrollmentTables(section);
        showSectionInfo(section);
        showMessage("Waitlisted student enrolled.");
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin Action");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBackToMain() throws java.io.IOException {
        javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/org/example/cs151courseregapp/view/main-view.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 600, 400);
        javafx.stage.Stage stage = (javafx.stage.Stage) classesListView.getScene().getWindow();
        stage.setScene(scene);
    }
}
