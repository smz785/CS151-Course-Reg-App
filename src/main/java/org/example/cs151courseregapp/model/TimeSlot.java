package org.example.cs151courseregapp.model;

import java.time.LocalTime;
import java.util.Set;

public class TimeSlot {
    private Set<Days> days;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(Set<Days> days, LocalTime startTime, LocalTime endTime) {
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Days cannot be empty.");
        }

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end time cannot be null.");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.days = Set.copyOf(days);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(TimeSlot other) {
        if (other == null) {
            return false;
        }

        boolean sameDay = false;

        for (Days day : days) {
            if (other.days.contains(day)) {
                sameDay = true;
                break;
            }
        }

        if (!sameDay) {
            return false;
        }

        return startTime.isBefore(other.endTime)
                && endTime.isAfter(other.startTime);
    }

    public String getDisplayText() {
        return days + " " + startTime + " - " + endTime;
    }

    public Set<Days> getDays() {
        return days;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}