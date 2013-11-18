package com.realtech.coursehateoas.course.service;


import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.exception.IllegalCourseActionException;
import org.springframework.data.domain.Page;


public interface CourseService {

    Iterable<Course> getCourses();

    Page<Course> getPaginatedCourses(int page, int pageSize);

    Course getCourse(Long id) throws CourseNotFoundException;

    Course create(Course aCourse);

    Course update(Course aCourse) throws CourseNotFoundException;

    void delete(Long id) throws CourseNotFoundException;

    Course approve(Long id) throws CourseNotFoundException, IllegalCourseActionException;

    Course publish(Long id) throws CourseNotFoundException, IllegalCourseActionException;
}
