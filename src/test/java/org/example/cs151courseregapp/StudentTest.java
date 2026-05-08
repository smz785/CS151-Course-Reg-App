package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    void testStudentCreation() {
        Student student = new Student("S1", "Test", "CS", "Senior");

        assertEquals("S1", student.getStudentId());
        assertEquals("Test", student.getName());
        assertEquals("CS", student.getMajor());
        assertEquals("Senior", student.getYear());
        assertTrue(student.getEnrollments().isEmpty());
    }

    @Test
    void testAddEnrollment() {
        Student student = new Student("S1", "Test", "CS", "Senior");

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test description"),
                null,
                null,
                30
        );

        Enrollment enrollment = new Enrollment("E1", student, section);

        student.addEnrollment(enrollment);

        assertEquals(1, student.getEnrollments().size());
    }

    @Test
    void testRemoveEnrollment() {
        Student student = new Student("S1", "Test", "CS", "Senior");

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                30
        );

        Enrollment enrollment = new Enrollment("E1", student, section);

        student.addEnrollment(enrollment);
        student.removeEnrollment(enrollment);

        assertTrue(student.getEnrollments().isEmpty());
    }

    @Test
    void testIsEnrolledInReturnsTrueForActiveEnrollment() {
        Student student = new Student("S1", "Test", "CS", "Senior");

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                30
        );

        Enrollment enrollment = new Enrollment("E1", student, section);

        student.addEnrollment(enrollment);

        assertTrue(student.isEnrolledIn(section));
    }

    @Test
    void testAddNullEnrollmentDoesNotChangeList() {
        Student student = new Student("S1", "Test", "CS", "Senior");

        student.addEnrollment(null);

        assertTrue(student.getEnrollments().isEmpty());
    }
}