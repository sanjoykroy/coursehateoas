package com.realtech.coursehateoas.course.service;


import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;


public interface CourseService {

    Iterable<Course> getCourses();

    Course getCourse(Long id) throws CourseNotFoundException;

    Course createCourse(Course aCourse);

    Course updateCourse(Long id, Course aCourse) throws CourseNotFoundException;

    Course deleteCourse(Long id) throws CourseNotFoundException;
}
