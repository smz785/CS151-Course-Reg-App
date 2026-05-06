package org.example.cs151courseregapp.model;

import java.util.ArrayList;
import java.util.List;

public class UniversityData {
    private List<Student> students;
    private List<Professor> professors;
    private List<Course> courses;
    private List<Section> sections;
    private List<Enrollment> enrollments;

    public UniversityData() {
        this.students = new ArrayList<>();
        this.professors = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.sections = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    public void addProfessor(Professor professor) {
        if (professor != null) {
            professors.add(professor);
        }
    }

    public void addCourse(Course course) {
        if (course != null) {
            courses.add(course);
        }
    }

    public void addSection(Section section) {
        if (section != null) {
            sections.add(section);
        }
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) {
            enrollments.add(enrollment);
        }
    }

    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public Professor findProfessorById(String professorId) {
        for (Professor professor : professors) {
            if (professor.getProfessorId().equals(professorId)) {
                return professor;
            }
        }
        return null;
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public Section findSectionById(String sectionId) {
        for (Section section : sections) {
            if (section.getSectionId().equals(sectionId)) {
                return section;
            }
        }
        return null;
    }

    public List<Section> getAllSections() {
        return new ArrayList<>(sections);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Professor> getAllProfessors() {
        return new ArrayList<>(professors);
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
}