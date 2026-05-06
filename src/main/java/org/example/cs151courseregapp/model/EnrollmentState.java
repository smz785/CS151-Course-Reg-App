package org.example.cs151courseregapp.model;

/**
 * This class implements State Design Pattern
 * Single responsibility Principle
 * Open/Closed principle
 * Encapsulation
 */
public interface EnrollmentState {
    void activate(Enrollment enrollment);
    void drop(Enrollment enrollment);
    EnrollmentStatus getStatusName();
    boolean isActive();
    boolean countInCapacity();
}
