package com.realtech.coursehateoas.course.domain.model;


public enum CourseStatus {
    NEW("New"),
    APPROVED("Approved"),
    PUBLISHED("Published"),
    BLOCK("Blocked"),
    CANCEL("Cancelled");

    private final String description;

    private CourseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
