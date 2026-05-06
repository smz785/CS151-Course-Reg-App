package org.example.cs151courseregapp.service;

import org.example.cs151courseregapp.model.Enrollment;
import org.example.cs151courseregapp.model.Section;
import org.example.cs151courseregapp.model.Student;
import org.example.cs151courseregapp.model.UniversityData;

import java.util.UUID;

public class RegistrationService {
    private UniversityData universityData;
    private ScheduleConflictChecker conflictChecker;

    public RegistrationService(UniversityData universityData,
                               ScheduleConflictChecker conflictChecker) {
        this.universityData = universityData;
        this.conflictChecker = conflictChecker;
    }

    public boolean registerStudent(Student student, Section section) {
        if (!canRegister(student, section)) {
            return false;
        }

        Enrollment enrollment = new Enrollment(
                UUID.randomUUID().toString(),
                student,
                section
        );

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
            if (enrollment.isActive() && enrollment.getSection().equals(section)) {
                enrollment.drop();
                return true;
            }
        }

        return false;
    }

    public boolean canRegister(Student student, Section section) {
        if (student == null || section == null) {
            return false;
        }

        if (student.isEnrolledIn(section)) {
            return false;
        }

        if (section.isFull()) {
            return false;
        }

        if (conflictChecker.hasConflict(student, section)) {
            return false;
        }

        return true;
    }
}