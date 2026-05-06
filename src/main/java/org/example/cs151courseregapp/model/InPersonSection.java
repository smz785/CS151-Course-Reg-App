package org.example.cs151courseregapp.model;

public class InPersonSection extends Section{
    private Buildings building;
    private String roomNumber;

    public InPersonSection(Buildings building, String roomNumber, String sectionId, Course course, Professor professor,
                           TimeSlot timeSlot, int seatCapacity){
        super(sectionId, course, professor, timeSlot, seatCapacity);
        this.building = building;
        this.roomNumber = roomNumber;

    }

    public Buildings getBuilding(){
        return building;
    }

    public String getRoomNumber(){
        return roomNumber;
    }

    @Override
    public String getSectionType(){
        return "InPerson";
    }

    @Override
    public String getLocation(){
        return building + " - " + roomNumber;
    }
}
