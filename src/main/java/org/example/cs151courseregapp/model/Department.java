package org.example.cs151courseregapp.model;

public enum Department {
    COMPUTER_SCIENCE("Computer Science"),
    MATHEMATICS("Mathematics"),
    ENGINEERING("Engineering"),
    BUSINESS("Business"),
    PHYSICS("Physics");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}