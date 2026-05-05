package org.example.cs151courseregapp.model;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private String major;
    private String year;
    private List<Enrollment> enrollments;

    public Student(String studentId, String name, String major, String year) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.year = year;
        this.enrollments = new ArrayList<>();
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null && !enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    public List<Section> getEnrolledSections() {
        List<Section> sections = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.isActive()) {
                sections.add(enrollment.getSection());
            }
        }

        return sections;
    }

    public boolean isEnrolledIn(Section section) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.isActive() && enrollment.getSection().equals(section)) {
                return true;
            }
        }
        return false;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getYear() {
        return year;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
}