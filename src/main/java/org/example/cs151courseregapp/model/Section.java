package org.example.cs151courseregapp.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Section {
    protected String sectionId;
    protected Course course;
    protected Professor professor;
    protected TimeSlot timeSlot;
    protected int seatCapacity;
    protected List<Enrollment> enrollments;

    public Section(String sectionId, Course course, Professor professor,
                   TimeSlot timeSlot, int seatCapacity) {
        this.sectionId = sectionId;
        this.course = course;
        this.professor = professor;
        this.timeSlot = timeSlot;
        this.seatCapacity = seatCapacity;
        this.enrollments = new ArrayList<>();
    }

    public boolean hasAvailableSeat() {
        return getEnrollmentCount() < seatCapacity;
    }

    public boolean removeEnrollment(Enrollment enrollment) {
        return enrollments.remove(enrollment);
    }

    public int getEnrollmentCount() {
        int count = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.countInCapacity()) {
                count++;
            }
        }

        return count;
    }

    public boolean addEnrollment(Enrollment enrollment) {
        if (enrollment == null || enrollments.contains(enrollment)) {
            return false;
        }

        if(enrollment.getSection() != this){
            return false;
        }

        if (enrollment.countInCapacity() && isFull()) {
            return false;
        }

        enrollments.add(enrollment);
        return true;
    }

    public boolean isFull() {
        return getEnrollmentCount() >= seatCapacity;
    }

    public String getSectionId() {
        return sectionId;
    }

    public Course getCourse() {
        return course;
    }

    public Professor getProfessor() {
        return professor;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public List<Enrollment> getEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public abstract String getLocation();

    public abstract String getSectionType();
}
