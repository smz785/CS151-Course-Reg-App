package org.example.cs151courseregapp;

import org.example.cs151courseregapp.model.*;
import org.example.cs151courseregapp.service.InPersonSectionFactory;
import org.example.cs151courseregapp.service.OnlineSectionFactory;
import org.example.cs151courseregapp.service.SectionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SectionFactoryTest {

    private TimeSlot timeSlot() {
        return new TimeSlot(
                Set.of(Days.MON, Days.WED),
                LocalTime.of(10, 30),
                LocalTime.of(11, 45)
        );
    }

    private Course course() {
        return new Course(
                "CS151",
                "Object-Oriented Design",
                3,
                "OO principles, UML, and design patterns."
        );
    }

    private Professor professor() {
        return new Professor(
                "P001",
                "Dr. Smith",
                Department.COMPUTER_SCIENCE
        );
    }

    @Test
    void testInPersonFactoryCreatesSectionAndAssignsProfessor() {
        Professor professor = professor();

        SectionFactory factory = new InPersonSectionFactory(
                Buildings.MH,
                "225"
        );

        Section section = factory.createSection(
                "SEC001",
                course(),
                professor,
                timeSlot(),
                35
        );

        assertNotNull(section);
        assertTrue(section instanceof InPersonSection);
        assertEquals("SEC001", section.getSectionId());
        assertEquals("InPerson", section.getSectionType());
        assertEquals("MH - 225", section.getLocation());
        assertEquals(35, section.getSeatCapacity());

        assertTrue(professor.getAssignedSections().contains(section));
    }

    @Test
    void testOnlineFactoryCreatesSectionAndAssignsProfessor() {
        Professor professor = professor();

        SectionFactory factory = new OnlineSectionFactory(
                "Zoom",
                "https://sjsu.zoom.us/test"
        );

        Section section = factory.createSection(
                "SEC002",
                course(),
                professor,
                timeSlot(),
                40
        );

        assertNotNull(section);
        assertTrue(section instanceof OnlineSection);
        assertEquals("SEC002", section.getSectionId());
        assertEquals("Online", section.getSectionType());
        assertEquals("Zoom - https://sjsu.zoom.us/test", section.getLocation());
        assertEquals(40, section.getSeatCapacity());

        assertTrue(professor.getAssignedSections().contains(section));
    }

    @Test
    void testFactoryRejectsNullCourse() {
        SectionFactory factory = new InPersonSectionFactory(
                Buildings.MH,
                "225"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.createSection(
                        "SEC001",
                        null,
                        professor(),
                        timeSlot(),
                        35
                )
        );
    }

    @Test
    void testFactoryRejectsBlankSectionId() {
        SectionFactory factory = new InPersonSectionFactory(
                Buildings.MH,
                "225"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.createSection(
                        "   ",
                        course(),
                        professor(),
                        timeSlot(),
                        35
                )
        );
    }

    @Test
    void testFactoryRejectsNullProfessor() {
        SectionFactory factory = new InPersonSectionFactory(
                Buildings.MH,
                "225"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.createSection(
                        "SEC001",
                        course(),
                        null,
                        timeSlot(),
                        35
                )
        );
    }

    @Test
    void testFactoryRejectsNullTimeSlot() {
        SectionFactory factory = new InPersonSectionFactory(
                Buildings.MH,
                "225"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.createSection(
                        "SEC001",
                        course(),
                        professor(),
                        null,
                        35
                )
        );
    }

    @Test
    void testFactoryRejectsInvalidSeatCapacity() {
        SectionFactory factory = new InPersonSectionFactory(
                Buildings.MH,
                "225"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> factory.createSection(
                        "SEC001",
                        course(),
                        professor(),
                        timeSlot(),
                        0
                )
        );
    }

    @Test
    void testInPersonFactoryRejectsNullBuilding() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new InPersonSectionFactory(null, "225")
        );
    }

    @Test
    void testInPersonFactoryRejectsBlankRoomNumber() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new InPersonSectionFactory(Buildings.MH, "   ")
        );
    }

    @Test
    void testOnlineFactoryRejectsBlankPlatform() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new OnlineSectionFactory("   ", "https://sjsu.zoom.us/test")
        );
    }

    @Test
    void testOnlineFactoryRejectsBlankMeetingLink() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new OnlineSectionFactory("Zoom", "   ")
        );
    }
}