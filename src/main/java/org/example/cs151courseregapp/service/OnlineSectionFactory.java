package org.example.cs151courseregapp.service;

import org.example.cs151courseregapp.model.*;
import org.example.cs151courseregapp.service.SectionFactory;

public class OnlineSectionFactory extends SectionFactory {
    private String platform;
    private String meetingLink;

    public OnlineSectionFactory(String platform, String meetingLink) {
        if (platform == null || platform.trim().isEmpty()) {
            throw new IllegalArgumentException("Platform cannot be null or empty");
        }

        if (meetingLink == null || meetingLink.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting link cannot be null or empty");
        }

        this.platform = platform;
        this.meetingLink = meetingLink;
    }

    @Override
    public Section createSection(String sectionId,
                                 Course course,
                                 Professor professor,
                                 TimeSlot timeSlot,
                                 int seatCapacity) {
        validateCommonFields(sectionId, course, professor, timeSlot, seatCapacity);

        Section section = new OnlineSection(
                platform,
                meetingLink,
                sectionId,
                course,
                professor,
                timeSlot,
                seatCapacity
        );

        professor.assignSection(section);
        return section;
    }
}