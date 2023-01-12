package com.myrestfulprojects.moviehub.model;

public enum Department {
    ACTOR("ACTOR"),
    DIRECTOR("DIRECTOR"),
    STAR("STAR"),
    WRITER("WRITER");
    private String department;
    Department(String department) {
        this.department = department;
    }
    public String getDepartment() {
        return this.department;
    }
}
