package com.realtech.coursehateoas.course.service;


import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.exception.IllegalCourseActionException;
import org.springframework.data.domain.Page;


public interface CourseService {

    Iterable<Course> getCourses();

    Page<Course> getPaginatedCourses(int page, int pageSize);

    Course getCourse(Long id) throws CourseNotFoundException;

    Course createCourse(Course aCourse);

    Course updateCourse(Course aCourse) throws CourseNotFoundException;

    void deleteCourse(Long id) throws CourseNotFoundException;

    Course approveCourse(Long id) throws CourseNotFoundException, IllegalCourseActionException;
}
