package org.example.cs151courseregapp.model;

public class DroppedEnrollmentState implements EnrollmentState
{
    @Override
    public void activate(Enrollment enrollment){
        throw new IllegalStateException("Dropped student cannot be activated");
    }

    @Override
    public void drop(Enrollment enrollment){

    }

    @Override
    public EnrollmentStatus getStatusName(){
        return EnrollmentStatus.DROPPED;
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
