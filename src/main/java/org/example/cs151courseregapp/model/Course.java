package org.example.cs151courseregapp.model;

public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private String description;

    public Course(String courseCode, String title, int credits, String description) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getDescription() {
        return description;
    }
}