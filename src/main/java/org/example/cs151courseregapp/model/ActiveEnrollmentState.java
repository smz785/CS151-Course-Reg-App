package org.example.cs151courseregapp.model;

public class ActiveEnrollmentState implements EnrollmentState
{
    @Override
    public void activate(Enrollment enrollment){

    }

    @Override
    public void drop(Enrollment enrollment){
        enrollment.setState(new DroppedEnrollmentState());
    }

    @Override
    public EnrollmentStatus getStatusName(){
        return EnrollmentStatus.ACTIVE;
    }

    @Override
    public boolean isActive(){
        return true;
    }

    @Override
    public boolean countInCapacity(){
        return false;
    }
}
