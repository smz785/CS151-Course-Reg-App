package org.example.cs151courseregapp.model;

public class OnlineSection extends Section{
    private String platform;
    private String meetingLink;

    public OnlineSection(String platform, String meetingLink, String sectionId, Course course, Professor professor,
                         TimeSlot timeSlot, int seatCapacity){
        super(sectionId,course, professor, timeSlot, seatCapacity);
        this.platform = platform;
        this.meetingLink = meetingLink;

    }

    public String getPlatform(){
        return platform;
    }

    public String getMeetingLink(){
        return meetingLink;
    }

    @Override
    public String getSectionType(){
        return "Online";
    }

    @Override
    public String getLocation(){
        return platform + " - " + meetingLink;
    }


}
