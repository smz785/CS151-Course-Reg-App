package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.example.cs151courseregapp.service.ScheduleConflictChecker;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleConflictCheckerTest {

    @Test
    void testSectionsConflictWhenTimesOverlapOnSameDay() {
        ScheduleConflictChecker checker = new ScheduleConflictChecker();

        Section sectionOne = createSection(
                "SEC1",
                new TimeSlot(Set.of(Days.MON), LocalTime.of(9, 0), LocalTime.of(10, 15))
        );

        Section sectionTwo = createSection(
                "SEC2",
                new TimeSlot(Set.of(Days.MON), LocalTime.of(10, 0), LocalTime.of(11, 15))
        );

        assertTrue(checker.sectionsConflict(sectionOne, sectionTwo));
    }

    @Test
    void testSectionsDoNotConflictOnDifferentDays() {
        ScheduleConflictChecker checker = new ScheduleConflictChecker();

        Section sectionOne = createSection(
                "SEC1",
                new TimeSlot(Set.of(Days.MON), LocalTime.of(9, 0), LocalTime.of(10, 15))
        );

        Section sectionTwo = createSection(
                "SEC2",
                new TimeSlot(Set.of(Days.TUE), LocalTime.of(9, 30), LocalTime.of(10, 45))
        );

        assertFalse(checker.sectionsConflict(sectionOne, sectionTwo));
    }

    private Section createSection(String sectionId, TimeSlot timeSlot) {
        return new InPersonSection(
                null,
                "Room 101",
                sectionId,
                new Course("C1", "Course", 3, "Test Description"),
                null,
                timeSlot,
                30
        );
    }
}