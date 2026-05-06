package org.example.cs151courseregapp.service;

import org.example.cs151courseregapp.model.Section;
import org.example.cs151courseregapp.model.Student;

public class ScheduleConflictChecker {

    public boolean hasConflict(Student student, Section newSection) {
        if (student == null || newSection == null) {
            return false;
        }

        for (Section currentSection : student.getEnrolledSections()) {
            if (sectionsConflict(currentSection, newSection)) {
                return true;
            }
        }

        return false;
    }

    public boolean sectionsConflict(Section section1, Section section2) {
        if (section1 == null || section2 == null) {
            return false;
        }

        return section1.getTimeSlot().overlaps(section2.getTimeSlot());
    }
}