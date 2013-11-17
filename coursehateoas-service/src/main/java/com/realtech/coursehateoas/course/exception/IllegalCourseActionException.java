package com.realtech.coursehateoas.course.exception;

public class IllegalCourseActionException extends RuntimeException {
    public IllegalCourseActionException() {
    }

    public IllegalCourseActionException(String message) {
        super(message);
    }
}
