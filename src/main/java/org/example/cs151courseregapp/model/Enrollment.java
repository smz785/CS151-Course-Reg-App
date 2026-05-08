package org.example.cs151courseregapp.model;

public class Enrollment {
    private String enrollmentId;
    private Student student;
    private Section section;
    private EnrollmentState state;

    public Enrollment(String enrollmentId, Student student, Section section) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.section = section;
        this.state = new ActiveEnrollmentState();
    }

    public Enrollment(String enrollmentId, Student student, Section section, EnrollmentState initialState) {
        if (enrollmentId == null || enrollmentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Enrollment ID cannot be null or empty");
        }

        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (section == null) {
            throw new IllegalArgumentException("Section cannot be null");
        }

        if (initialState == null) {
            throw new IllegalArgumentException("Initial enrollment state cannot be null");
        }

        this.enrollmentId = enrollmentId;
        this.student = student;
        this.section = section;
        this.state = initialState;
    }

    public void activate() {
        state.activate(this);
    }

    public void drop() {
        state.drop(this);
    }

    public boolean isActive() {
        return state.isActive();
    }

    public boolean countInCapacity() {
        return state.countInCapacity();
    }

    public EnrollmentStatus getStatusName(){
        return state.getStatusName();
    }

    public void setState(EnrollmentState state){
        if(state == null){
            throw new IllegalArgumentException("Enrollment state can't be null");
        }
        this.state = state;
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

    public boolean isWaitlisted(){return getStatusName() == EnrollmentStatus.WAITLISTED;}

}