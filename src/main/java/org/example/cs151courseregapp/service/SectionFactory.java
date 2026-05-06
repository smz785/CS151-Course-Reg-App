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
public class SectionFactory {

    public Section createSection(String sectionId,
                                 Course course,
                                 Professor professor,
                                 TimeSlot timeSlot,
                                 int seatCapacity) {
        if (sectionId == null || sectionId.isEmpty()) {
            throw new IllegalArgumentException("Section ID cannot be empty.");
        }

        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (professor == null) {
            throw new IllegalArgumentException("Professor cannot be null.");
        }

        if (timeSlot == null) {
            throw new IllegalArgumentException("Time slot cannot be null.");
        }

        if (seatCapacity <= 0) {
            throw new IllegalArgumentException("Seat capacity must be positive.");
        }

        Section section = new Section(sectionId, course, professor, timeSlot, seatCapacity);
        professor.assignSection(section);

        return section;
    }
}