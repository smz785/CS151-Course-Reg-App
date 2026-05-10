package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentTest {

    @Test
    void testActiveEnrollmentState() {
        Student student = new Student("S1", "Test", "CS", Year.SENIOR);
        Section section = createSection();

        Enrollment enrollment = new Enrollment(
                "E1",
                student,
                section,
                new ActiveEnrollmentState()
        );

        assertTrue(enrollment.isActive());
        assertFalse(enrollment.isWaitlisted());
        assertTrue(enrollment.countInCapacity());
        assertEquals(EnrollmentStatus.ACTIVE, enrollment.getStatusName());
    }

    @Test
    void testWaitlistedEnrollmentState() {
        Student student = new Student("S1", "Test", "CS", Year.SENIOR);
        Section section = createSection();

        Enrollment enrollment = new Enrollment(
                "E1",
                student,
                section,
                new WaitlistedEnrollmentState()
        );

        assertFalse(enrollment.isActive());
        assertTrue(enrollment.isWaitlisted());
        assertFalse(enrollment.countInCapacity());
        assertEquals(EnrollmentStatus.WAITLISTED, enrollment.getStatusName());
    }

    @Test
    void testDroppedEnrollmentState() {
        Student student = new Student("S1", "Test", "CS", Year.SENIOR);
        Section section = createSection();

        Enrollment enrollment = new Enrollment(
                "E1",
                student,
                section,
                new ActiveEnrollmentState()
        );

        enrollment.drop();

        assertFalse(enrollment.isActive());
        assertFalse(enrollment.isWaitlisted());
        assertFalse(enrollment.countInCapacity());
        assertEquals(EnrollmentStatus.DROPPED, enrollment.getStatusName());
    }

    private Section createSection() {
        return new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                30
        );
    }
}