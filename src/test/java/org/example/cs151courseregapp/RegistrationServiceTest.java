package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.example.cs151courseregapp.service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {

    @Test
    void testRegisterStudentSuccessfully() {
        UniversityData universityData = new UniversityData();
        ScheduleConflictChecker conflictChecker = new ScheduleConflictChecker();
        RegistrationService service = new RegistrationService(universityData, conflictChecker);

        Student student = new Student("S1", "Test", "CS", Year.SENIOR);

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                30
        );

        boolean result = service.registerStudent(student, section);

        assertTrue(result);
        assertEquals(1, student.getEnrollments().size());
        assertEquals(1, section.getEnrollments().size());
        assertEquals(1, universityData.getAllEnrollments().size());
        assertTrue(student.getEnrollments().get(0).isActive());
    }

    @Test
    void testCannotRegisterNullStudent() {
        UniversityData universityData = new UniversityData();
        ScheduleConflictChecker conflictChecker = new ScheduleConflictChecker();
        RegistrationService service = new RegistrationService(universityData, conflictChecker);

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                30
        );

        assertFalse(service.registerStudent(null, section));
    }

    @Test
    void testRegisterStudentWaitlistedWhenFull() {
        UniversityData universityData = new UniversityData();
        ScheduleConflictChecker conflictChecker = new ScheduleConflictChecker();
        RegistrationService service = new RegistrationService(universityData, conflictChecker);

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                1
        );

        Student firstStudent = new Student("S1", "Test One", "CS", Year.SENIOR);
        Student secondStudent = new Student("S2", "Test Two", "CS", Year.SENIOR);

        assertTrue(service.registerStudent(firstStudent, section));
        assertTrue(service.registerStudent(secondStudent, section));

        Enrollment secondEnrollment = secondStudent.getEnrollments().get(0);

        assertTrue(secondEnrollment.isWaitlisted());
    }
}