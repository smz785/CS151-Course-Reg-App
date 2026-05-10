package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityDataTest {

    @Test
    void testAddAndFindStudent() {
        UniversityData data = new UniversityData();

        Student student = new Student("S1", "Test", "CS", Year.SENIOR);
        data.addStudent(student);

        Student found = data.findStudentById("S1");

        assertNotNull(found);
        assertEquals("S1", found.getStudentId());
    }

    @Test
    void testAddAndFindCourse() {
        UniversityData data = new UniversityData();

        Course course = new Course("C1", "Course", 3, "Test Description");
        data.addCourse(course);

        Course found = data.findCourseByCode("C1");

        assertNotNull(found);
        assertEquals("C1", found.getCourseCode());
    }

    @Test
    void testAddAndFindSection() {
        UniversityData data = new UniversityData();

        Section section = new InPersonSection(
                null,
                "Room 101",
                "SEC1",
                new Course("C1", "Course", 3, "Test Description"),
                null,
                null,
                30
        );

        data.addSection(section);

        Section found = data.findSectionById("SEC1");

        assertNotNull(found);
        assertEquals("SEC1", found.getSectionId());
    }

    @Test
    void testAddEnrollment() {
        UniversityData data = new UniversityData();

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

        Enrollment enrollment = new Enrollment("E1", student, section);

        data.addEnrollment(enrollment);

        assertEquals(1, data.getAllEnrollments().size());
    }
}