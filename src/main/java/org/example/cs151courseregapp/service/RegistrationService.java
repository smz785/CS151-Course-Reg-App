package org.example.cs151courseregapp.service;

import org.example.cs151courseregapp.model.ActiveEnrollmentState;
import org.example.cs151courseregapp.model.Enrollment;
import org.example.cs151courseregapp.model.Section;
import org.example.cs151courseregapp.model.Student;
import org.example.cs151courseregapp.model.UniversityData;
import org.example.cs151courseregapp.model.WaitlistedEnrollmentState;

import java.util.UUID;

public class RegistrationService {
    private UniversityData universityData;
    private ScheduleConflictChecker conflictChecker;

    public RegistrationService(UniversityData universityData,
                               ScheduleConflictChecker conflictChecker) {
        if (universityData == null) {
            throw new IllegalArgumentException("University data cannot be null");
        }

        if (conflictChecker == null) {
            throw new IllegalArgumentException("Conflict checker cannot be null");
        }

        this.universityData = universityData;
        this.conflictChecker = conflictChecker;
    }

    public boolean registerStudent(Student student, Section section) {
        if (!canAttemptRegistration(student, section)) {
            return false;
        }

        Enrollment enrollment;

        if (section.hasAvailableSeat()) {
            enrollment = new Enrollment(
                    UUID.randomUUID().toString(),
                    student,
                    section,
                    new ActiveEnrollmentState()
            );
        } else {
            enrollment = new Enrollment(
                    UUID.randomUUID().toString(),
                    student,
                    section,
                    new WaitlistedEnrollmentState()
            );
        }

        student.addEnrollment(enrollment);
        section.addEnrollment(enrollment);
        universityData.addEnrollment(enrollment);

        return true;
    }

    public boolean dropStudent(Student student, Section section) {
        if (student == null || section == null) {
            return false;
        }

        for (Enrollment enrollment : student.getEnrollments()) {
            if ((enrollment.isActive() || enrollment.isWaitlisted())
                    && enrollment.getSection().equals(section)) {
                enrollment.drop();
                promoteWaitlistedStudent(section);
                return true;
            }
        }

        return false;
    }

    public boolean canAttemptRegistration(Student student, Section section) {
        if (student == null || section == null) {
            return false;
        }

        if (student.hasCurrentEnrollmentIn(section)) {
            return false;
        }

        if (conflictChecker.hasConflict(student, section)) {
            return false;
        }

        return true;
    }

    private void promoteWaitlistedStudent(Section section) {
        if (section == null || section.isFull()) {
            return;
        }

        for (Enrollment enrollment : section.getEnrollments()) {
            if (enrollment.isWaitlisted()) {
                enrollment.activate();
                return;
            }
        }
    }

    public RegistrationResult regStudent(Student student, Section section) {
        if (student == null || section == null) {
            return RegistrationResult.INVALID_INPUT;
        }

        if (student.hasCurrentEnrollmentIn(section)) {
            return RegistrationResult.DUPLICATE_ENROLLMENT;
        }

        if (conflictChecker.hasConflict(student, section)) {
            return RegistrationResult.SCHEDULE_CONFLICT;
        }

        Enrollment enrollment;

        if (section.hasAvailableSeat()) {
            enrollment = new Enrollment(
                    UUID.randomUUID().toString(),
                    student,
                    section,
                    new ActiveEnrollmentState()
            );

            student.addEnrollment(enrollment);
            section.addEnrollment(enrollment);
            universityData.addEnrollment(enrollment);

            return RegistrationResult.REGISTERED;
        }

        enrollment = new Enrollment(
                UUID.randomUUID().toString(),
                student,
                section,
                new WaitlistedEnrollmentState()
        );

        student.addEnrollment(enrollment);
        section.addEnrollment(enrollment);
        universityData.addEnrollment(enrollment);

        return RegistrationResult.WAITLISTED;
    }
}