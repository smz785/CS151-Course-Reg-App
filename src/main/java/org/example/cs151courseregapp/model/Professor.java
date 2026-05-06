package org.example.cs151courseregapp.model;

import java.util.ArrayList;
import java.util.List;

public class Professor {
    private String professorId;
    private String name;
    private String department;
    private List<Section> assignedSections;

    public Professor(String professorId, String name, String department) {
        this.professorId = professorId;
        this.name = name;
        this.department = department;
        this.assignedSections = new ArrayList<>();
    }

    public void assignSection(Section section) {
        if (section != null && !assignedSections.contains(section)) {
            assignedSections.add(section);
        }
    }

    public void removeSection(Section section) {
        assignedSections.remove(section);
    }

    public List<Section> getAssignedSections() {
        return assignedSections;
    }

    public String getProfessorId() {
        return professorId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}