package org.example.cs151courseregapp.model;
import org.example.cs151courseregapp.model.Section;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private String major;
    private Year year;
    private List<Enrollment> enrollments;

    public Student(String studentId, String name, String major, Year year) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.year = year;
        this.enrollments = new ArrayList<>();
    }

    public boolean addEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollments.contains(enrollment)) {
            return false;
        }

        if (enrollment.getStudent() != this) {
            return false;
        }

        enrollments.add(enrollment);
        return true;
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

    public boolean hasCurrentEnrollmentIn(Section section) {
        for (Enrollment enrollment : enrollments) {
            if ((enrollment.isActive() || enrollment.isWaitlisted())
                    && enrollment.getSection().equals(section)) {
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

    public Year getYear() {
        return year;
    }

    public List<Enrollment> getEnrollments() {
        return new ArrayList<>(enrollments);
    }




}