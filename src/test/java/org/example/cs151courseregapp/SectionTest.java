package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {

    @Test
    void testSectionCreation() {
        Course course = new Course("C1", "Course", 3, "Test Description");

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                course,
                null,
                null,
                2
        );

        assertEquals("SEC1", section.getSectionId());
        assertEquals(course, section.getCourse());
        assertEquals(2, section.getSeatCapacity());
        assertTrue(section.getEnrollments().isEmpty());
        assertEquals("InPerson", section.getSectionType());
    }

    @Test
    void testSectionBecomesFullAtCapacity() {
        Course course = new Course("C1", "Course", 3, "Test Description");

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                course,
                null,
                null,
                1
        );

        Student student = new Student("S1", "Test", "CS", "Senior");
        Enrollment enrollment = new Enrollment("E1", student, section);

        assertTrue(section.addEnrollment(enrollment));
        assertTrue(section.isFull());
        assertFalse(section.hasAvailableSeat());
    }

    @Test
    void testAddDuplicateEnrollmentFails() {
        Course course = new Course("C1", "Course", 3, "Test Description");

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                course,
                null,
                null,
                2
        );

        Student student = new Student("S1", "Test", "CS", "Senior");
        Enrollment enrollment = new Enrollment("E1", student, section);

        assertTrue(section.addEnrollment(enrollment));
        assertFalse(section.addEnrollment(enrollment)); // duplicate should fail 
    }
}