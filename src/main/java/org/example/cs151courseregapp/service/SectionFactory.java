package org.example.cs151courseregapp.service;

import org.example.cs151courseregapp.model.Course;
import org.example.cs151courseregapp.model.Professor;
import org.example.cs151courseregapp.model.Section;
import org.example.cs151courseregapp.model.TimeSlot;

/**
 * This class implements Factory Method Design Pattern
 * Open/Closed Principle
 * Single Responsibility Principle
 * Dependency Inversion Principle
 */
public abstract class SectionFactory {

    public abstract Section createSection(
            String sectionId,
            Course course,
            Professor professor,
            TimeSlot timeSlot,
            int seatCapacity
    );

    protected void validateCommonFields(String sectionId, Course course, Professor professor, TimeSlot timeSlot, int seatCapacity){
        if(course == null){
            throw new IllegalArgumentException("Course cannot be null");
        }
        if(sectionId == null || sectionId.trim().isEmpty()){
            throw new IllegalArgumentException("Section ID cannot be null or empty");
        }
        if(professor == null){
            throw new IllegalArgumentException("Professor cannot be null");
        }
        if(timeSlot == null){
            throw new IllegalArgumentException("TimeSlot cannot be null");
        }
        if(seatCapacity <= 0){
            throw new IllegalArgumentException("Seat Capacity cannot be less than 0");
        }
    }
}