package org.example.cs151courseregapp.model;

public class WaitlistedEnrollmentState implements EnrollmentState{

    @Override
    public void activate(Enrollment enrollment) {
        enrollment.setState(new ActiveEnrollmentState());
    }

    @Override
    public void drop(Enrollment enrollment) {
        enrollment.setState(new DroppedEnrollmentState());
    }

    @Override
    public EnrollmentStatus getStatusName(){
        return EnrollmentStatus.WAITLISTED;
    }

    @Override
    public boolean isActive(){
        return false;
    }

    @Override
    public boolean countInCapacity(){
        return false;
    }
}
