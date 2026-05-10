package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SectionTest {

    @Test
    void testInPersonSectionCreation() {
        Course course = new Course("C1", "Course", 3, "Test Description");

        InPersonSection section = new InPersonSection(
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
        assertEquals("Room 101", section.getRoomNumber());
    }

    @Test
    void testOnlineSectionCreation() {
        Course course = new Course("C1", "Course", 3, "Test Description");

        OnlineSection section = new OnlineSection(
                "Zoom",
                "https://zoom.test/class",
                "SEC2",
                course,
                null,
                null,
                30
        );

        assertEquals("SEC2", section.getSectionId());
        assertEquals(course, section.getCourse());
        assertEquals(30, section.getSeatCapacity());
        assertEquals("Online", section.getSectionType());
        assertEquals("Zoom", section.getPlatform());
        assertEquals("https://zoom.test/class", section.getMeetingLink());
        assertEquals("Zoom - https://zoom.test/class", section.getLocation());
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

        Student student = new Student("S1", "Test", "CS", Year.SENIOR);
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

        Student student = new Student("S1", "Test", "CS", Year.SENIOR);
        Enrollment enrollment = new Enrollment("E1", student, section);

        assertTrue(section.addEnrollment(enrollment));
        assertFalse(section.addEnrollment(enrollment));
    }
}