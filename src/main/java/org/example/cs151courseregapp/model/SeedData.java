package org.example.cs151courseregapp.model;

import java.time.LocalTime;
import java.util.Set;

public class SeedData {

    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) {
            return;
        }

        UniversityData data = UniversityData.getInstance();

        Professor p1 = new Professor("P001", "Dr. Smith", "Computer Science");
        Professor p2 = new Professor("P002", "Dr. Johnson", "Mathematics");

        Course c1 = new Course(
                "CS151",
                "Object-Oriented Design",
                3,
                "Covers object-oriented programming, design principles, UML, and design patterns."
        );

        Course c2 = new Course(
                "MATH161A",
                "Applied Statistics",
                3,
                "Introduction to probability, statistics, and data analysis."
        );

        TimeSlot t1 = new TimeSlot(
                Set.of(Days.MON, Days.WED),
                LocalTime.of(10, 30),
                LocalTime.of(11, 45)
        );

        TimeSlot t2 = new TimeSlot(
                Set.of(Days.TUE, Days.THU),
                LocalTime.of(13, 30),
                LocalTime.of(14, 45)
        );

        Section s1 = new InPersonSection(
                Buildings.MH,
                "225",
                "SEC001",
                c1,
                p1,
                t1,
                35
        );

        Section s2 = new OnlineSection(
                "Zoom",
                "https://sjsu.zoom.us/example",
                "SEC002",
                c2,
                p2,
                t2,
                40
        );

        p1.assignSection(s1);
        p2.assignSection(s2);

        data.addProfessor(p1);
        data.addProfessor(p2);

        data.addCourse(c1);
        data.addCourse(c2);

        data.addSection(s1);
        data.addSection(s2);

        initialized = true;
    }
}