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

        // Professors
        Professor p1 = new Professor("P001", "Dr. Smith", Department.COMPUTER_SCIENCE);
        Professor p2 = new Professor("P002", "Dr. Johnson", Department.MATHEMATICS);
        Professor p3 = new Professor("P003", "Dr. Lee", Department.ENGINEERING);
        Professor p4 = new Professor("P004", "Dr. Patel", Department.BUSINESS);
        Professor p5 = new Professor("P005", "Dr. Chen", Department.PHYSICS);

        // Courses
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

        Course c3 = new Course(
                "CS146",
                "Data Structures and Algorithms",
                3,
                "Covers lists, trees, graphs, hashing, sorting, and algorithm analysis."
        );

        Course c4 = new Course(
                "BUS100W",
                "Business Communication",
                3,
                "Develops professional writing, presentation, and communication skills."
        );

        Course c5 = new Course(
                "PHYS50",
                "General Physics",
                4,
                "Covers mechanics, motion, forces, energy, and waves."
        );

        Course c6 = new Course(
                "ENGR100",
                "Engineering Design",
                3,
                "Introduces engineering problem solving, design process, and teamwork."
        );

        // Time slots
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

        TimeSlot t3 = new TimeSlot(
                Set.of(Days.MON, Days.WED),
                LocalTime.of(12, 0),
                LocalTime.of(13, 15)
        );

        TimeSlot t4 = new TimeSlot(
                Set.of(Days.FRI),
                LocalTime.of(9, 0),
                LocalTime.of(11, 45)
        );

        TimeSlot t5 = new TimeSlot(
                Set.of(Days.TUE, Days.THU),
                LocalTime.of(10, 30),
                LocalTime.of(11, 45)
        );

        // This intentionally overlaps with t1 for conflict demo.
        TimeSlot t6 = new TimeSlot(
                Set.of(Days.MON, Days.WED),
                LocalTime.of(11, 0),
                LocalTime.of(12, 15)
        );

        // Sections
        Section s1 = new InPersonSection(
                Buildings.MH,
                "225",
                "SEC001",
                c1,
                p1,
                t1,
                2
        );

        Section s2 = new OnlineSection(
                "Zoom",
                "https://sjsu.zoom.us/cs151-online",
                "SEC002",
                c1,
                p1,
                t2,
                40
        );

        Section s3 = new InPersonSection(
                Buildings.MH,
                "321",
                "SEC003",
                c2,
                p2,
                t3,
                35
        );

        Section s4 = new OnlineSection(
                "Canvas",
                "https://sjsu.instructure.com/math161a",
                "SEC004",
                c2,
                p2,
                t4,
                25
        );

        Section s5 = new InPersonSection(
                Buildings.MH,
                "422",
                "SEC005",
                c3,
                p1,
                t5,
                30
        );

        Section s6 = new OnlineSection(
                "Zoom",
                "https://sjsu.zoom.us/cs146-online",
                "SEC006",
                c3,
                p1,
                t6,
                20
        );

        Section s7 = new InPersonSection(
                Buildings.MH,
                "110",
                "SEC007",
                c4,
                p4,
                t2,
                30
        );

        Section s8 = new InPersonSection(
                Buildings.MH,
                "150",
                "SEC008",
                c5,
                p5,
                t4,
                20
        );

        Section s9 = new OnlineSection(
                "Zoom",
                "https://sjsu.zoom.us/engr100",
                "SEC009",
                c6,
                p3,
                t3,
                25
        );

        // Students
        Student std1 = new Student(
                "STD001",
                "Aisha Khan",
                "Computer Science",
                Year.FRESHMAN
        );

        Student std2 = new Student(
                "STD002",
                "Daniel Kim",
                "Data Science",
                Year.JUNIOR
        );

        Student std3 = new Student(
                "STD003",
                "Maria Garcia",
                "Software Engineering",
                Year.SOPHOMORE
        );

        Student std4 = new Student(
                "STD004",
                "James Wilson",
                "Business",
                Year.SENIOR
        );

        Student std5 = new Student(
                "STD005",
                "Sophia Nguyen",
                "Physics",
                Year.JUNIOR
        );

        Student std6 = new Student(
                "STD006",
                "Omar Ali",
                "Computer Science",
                Year.SENIOR
        );

        // Add students
        data.addStudent(std1);
        data.addStudent(std2);
        data.addStudent(std3);
        data.addStudent(std4);
        data.addStudent(std5);
        data.addStudent(std6);

        // Demo enrollments
        // s1 has capacity 2. std1 and std2 fill it. std3 is waitlisted.
        Enrollment e1 = new Enrollment("E001", std1, s1);
        Enrollment e2 = new Enrollment("E002", std2, s1);

        Enrollment e3 = new Enrollment(
                "E003",
                std3,
                s1,
                new WaitlistedEnrollmentState()
        );

        // std1 also has another active course, so Drop demo is visible.
        Enrollment e4 = new Enrollment("E004", std1, s3);

        // std4 has a business course.
        Enrollment e5 = new Enrollment("E005", std4, s7);

        // std5 has physics.
        Enrollment e6 = new Enrollment("E006", std5, s8);

        // Add enrollments to sections
        s1.addEnrollment(e1);
        s1.addEnrollment(e2);
        s1.addEnrollment(e3);
        s3.addEnrollment(e4);
        s7.addEnrollment(e5);
        s8.addEnrollment(e6);

        // Add enrollments to students
        std1.addEnrollment(e1);
        std2.addEnrollment(e2);
        std3.addEnrollment(e3);
        std1.addEnrollment(e4);
        std4.addEnrollment(e5);
        std5.addEnrollment(e6);

        // Add enrollments to university data
        data.addEnrollment(e1);
        data.addEnrollment(e2);
        data.addEnrollment(e3);
        data.addEnrollment(e4);
        data.addEnrollment(e5);
        data.addEnrollment(e6);

        // Assign sections to professors
        p1.assignSection(s1);
        p1.assignSection(s2);
        p1.assignSection(s5);
        p1.assignSection(s6);

        p2.assignSection(s3);
        p2.assignSection(s4);

        p3.assignSection(s9);
        p4.assignSection(s7);
        p5.assignSection(s8);

        // Add professors
        data.addProfessor(p1);
        data.addProfessor(p2);
        data.addProfessor(p3);
        data.addProfessor(p4);
        data.addProfessor(p5);

        // Add courses
        data.addCourse(c1);
        data.addCourse(c2);
        data.addCourse(c3);
        data.addCourse(c4);
        data.addCourse(c5);
        data.addCourse(c6);

        // Add sections
        data.addSection(s1);
        data.addSection(s2);
        data.addSection(s3);
        data.addSection(s4);
        data.addSection(s5);
        data.addSection(s6);
        data.addSection(s7);
        data.addSection(s8);
        data.addSection(s9);

        initialized = true;
    }
}