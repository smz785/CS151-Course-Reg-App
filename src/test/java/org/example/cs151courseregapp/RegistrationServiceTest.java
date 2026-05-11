package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.example.cs151courseregapp.service.RegistrationResult;
import org.example.cs151courseregapp.service.RegistrationService;
import org.example.cs151courseregapp.service.ScheduleConflictChecker;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {

    private TimeSlot timeSlot(Days day, int startHour, int endHour) {
        return new TimeSlot(
                Set.of(day),
                LocalTime.of(startHour, 0),
                LocalTime.of(endHour, 0)
        );
    }

    private Course course(String code) {
        return new Course(code, "Test Course", 3, "Test Description");
    }

    private Professor professor(String id) {
        return new Professor(id, "Professor " + id, Department.COMPUTER_SCIENCE);
    }

    private Section inPersonSection(String sectionId, TimeSlot timeSlot, int capacity) {
        return new InPersonSection(
                Buildings.MH,
                "225",
                sectionId,
                course("CS151"),
                professor("P-" + sectionId),
                timeSlot,
                capacity
        );
    }

    private RegistrationService service(UniversityData universityData) {
        return new RegistrationService(
                universityData,
                new ScheduleConflictChecker()
        );
    }

    @Test
    void testRegisterStudentSuccessfullyReturnsRegistered() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Student student = new Student("S1", "Student One", "CS", Year.SENIOR);
        Section section = inPersonSection("SEC1", timeSlot(Days.MON, 10, 11), 30);

        RegistrationResult result = service.regStudent(student, section);

        assertEquals(RegistrationResult.REGISTERED, result);
        assertEquals(1, student.getEnrollments().size());
        assertEquals(1, section.getEnrollments().size());
        assertEquals(1, universityData.getAllEnrollments().size());
        assertTrue(student.getEnrollments().get(0).isActive());
        assertEquals(1, section.getEnrollmentCount());
    }

    @Test
    void testDuplicateRegistrationIsRejected() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Student student = new Student("S1", "Student One", "CS", Year.SENIOR);
        Section section = inPersonSection("SEC1", timeSlot(Days.MON, 10, 11), 30);

        assertEquals(RegistrationResult.REGISTERED, service.regStudent(student, section));
        assertEquals(RegistrationResult.DUPLICATE_ENROLLMENT, service.regStudent(student, section));

        assertEquals(1, student.getEnrollments().size());
        assertEquals(1, section.getEnrollments().size());
        assertEquals(1, universityData.getAllEnrollments().size());
    }

    @Test
    void testScheduleConflictIsRejected() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Student student = new Student("S1", "Student One", "CS", Year.SENIOR);

        Section firstSection = inPersonSection(
                "SEC1",
                timeSlot(Days.MON, 10, 12),
                30
        );

        Section conflictingSection = inPersonSection(
                "SEC2",
                timeSlot(Days.MON, 11, 13),
                30
        );

        assertEquals(RegistrationResult.REGISTERED, service.regStudent(student, firstSection));
        assertEquals(RegistrationResult.SCHEDULE_CONFLICT, service.regStudent(student, conflictingSection));

        assertEquals(1, student.getEnrollments().size());
        assertEquals(0, conflictingSection.getEnrollments().size());
    }

    @Test
    void testFullSectionCreatesWaitlistedEnrollment() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Section section = inPersonSection("SEC1", timeSlot(Days.MON, 10, 11), 1);

        Student firstStudent = new Student("S1", "Student One", "CS", Year.SENIOR);
        Student secondStudent = new Student("S2", "Student Two", "CS", Year.JUNIOR);

        assertEquals(RegistrationResult.REGISTERED, service.regStudent(firstStudent, section));
        assertEquals(RegistrationResult.WAITLISTED, service.regStudent(secondStudent, section));

        Enrollment firstEnrollment = firstStudent.getEnrollments().get(0);
        Enrollment secondEnrollment = secondStudent.getEnrollments().get(0);

        assertTrue(firstEnrollment.isActive());
        assertTrue(secondEnrollment.isWaitlisted());

        assertEquals(2, section.getEnrollments().size());
        assertEquals(1, section.getEnrollmentCount());
    }

    @Test
    void testDropActiveStudentPromotesWaitlistedStudent() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Section section = inPersonSection("SEC1", timeSlot(Days.MON, 10, 11), 1);

        Student firstStudent = new Student("S1", "Student One", "CS", Year.SENIOR);
        Student secondStudent = new Student("S2", "Student Two", "CS", Year.JUNIOR);

        assertEquals(RegistrationResult.REGISTERED, service.regStudent(firstStudent, section));
        assertEquals(RegistrationResult.WAITLISTED, service.regStudent(secondStudent, section));

        Enrollment firstEnrollment = firstStudent.getEnrollments().get(0);
        Enrollment secondEnrollment = secondStudent.getEnrollments().get(0);

        assertTrue(firstEnrollment.isActive());
        assertTrue(secondEnrollment.isWaitlisted());

        assertTrue(service.dropStudent(firstStudent, section));

        assertEquals(EnrollmentStatus.DROPPED, firstEnrollment.getStatusName());
        assertTrue(secondEnrollment.isActive());
        assertEquals(1, section.getEnrollmentCount());
    }

    @Test
    void testInvalidInputReturnsInvalidInput() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Student student = new Student("S1", "Student One", "CS", Year.SENIOR);
        Section section = inPersonSection("SEC1", timeSlot(Days.MON, 10, 11), 30);

        assertEquals(RegistrationResult.INVALID_INPUT, service.regStudent(null, section));
        assertEquals(RegistrationResult.INVALID_INPUT, service.regStudent(student, null));
    }

    @Test
    void testDropFailsForNullInputs() {
        UniversityData universityData = new UniversityData();
        RegistrationService service = service(universityData);

        Student student = new Student("S1", "Student One", "CS", Year.SENIOR);
        Section section = inPersonSection("SEC1", timeSlot(Days.MON, 10, 11), 30);

        assertFalse(service.dropStudent(null, section));
        assertFalse(service.dropStudent(student, null));
    }
}