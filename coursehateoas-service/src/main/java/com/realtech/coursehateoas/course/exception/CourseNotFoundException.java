package com.realtech.coursehateoas.course.exception;


public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException() {
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}
