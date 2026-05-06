package org.example.cs151courseregapp.service;

import org.example.cs151courseregapp.model.*;

public class InPersonSectionFactory extends SectionFactory {
    private Buildings building;
    private String roomNumber;

    public InPersonSectionFactory(Buildings building, String roomNumber){
        if(building==null){
            throw new IllegalArgumentException("building is null or empty");
        }
        if(roomNumber==null|| roomNumber.trim().isEmpty()){
            throw new IllegalArgumentException("roomNumber is null or empty");
        }

        this.building = building;
        this.roomNumber = roomNumber;

    }


    @Override
    public Section createSection(String sectionId, Course course, Professor professor, TimeSlot timeSlot, int seatCapacity) {
        validateCommonFields(sectionId, course, professor, timeSlot, seatCapacity);

        Section section = new InPersonSection(
                building, roomNumber, sectionId, course, professor, timeSlot,
                seatCapacity
        );

        professor.assignSection(section);
        return section;
    }
}