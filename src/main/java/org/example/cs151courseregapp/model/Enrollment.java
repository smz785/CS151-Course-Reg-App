package org.example.cs151courseregapp.model;

public class Enrollment {
    private String enrollmentId;
    private Student student;
    private Section section;
    private EnrollmentStatus status;

    public Enrollment(String enrollmentId, Student student, Section section) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.section = section;
        this.status = EnrollmentStatus.ACTIVE;
    }

    public void activate() {
        this.status = EnrollmentStatus.ACTIVE;
    }

    public void drop() {
        this.status = EnrollmentStatus.DROPPED;
    }

    public boolean isActive() {
        return status == EnrollmentStatus.ACTIVE;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public Section getSection() {
        return section;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }
}