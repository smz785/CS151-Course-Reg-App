package org.example.cs151courseregapp.model;

import java.time.LocalTime;

public class TimeSlot {
    private String days;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(String days, LocalTime startTime, LocalTime endTime) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(TimeSlot other) {
        if (other == null) {
            return false;
        }

        boolean sameDay = false;

        for (char day : days.toCharArray()) {
            if (other.days.indexOf(day) >= 0) {
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

    public boolean containsDay(String day) {
        return day != null && days.contains(day);
    }

    public String getDisplayText() {
        return days + " " + startTime + " - " + endTime;
    }

    public String getDays() {
        return days;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}