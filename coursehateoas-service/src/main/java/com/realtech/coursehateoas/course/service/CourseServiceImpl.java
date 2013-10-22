package com.realtech.coursehateoas.course.service;

import com.realtech.coursehateoas.course.annotation.ControllerService;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.infrastructure.persistence.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ControllerService
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public Iterable<Course> getCourses() {
        return repository.findAll();
    }

    @Override
    public Course getCourse(Long id) throws CourseNotFoundException {
        Course course = repository.findOne(id);
        if(course == null){
            throw new CourseNotFoundException("Course is not found.");
        } else {
            return course;
        }
    }

    @Override
    public Course createCourse(Course aCourse) {
        return repository.save(aCourse);
    }

    @Override
    public Course updateCourse(Long id, Course aCourse) throws CourseNotFoundException {
        Course existedCourse = this.getCourse(id);
        return repository.save(populateExistedCourseWithNewData(existedCourse, aCourse));
    }

    @Override
    public Course deleteCourse(Long id) throws CourseNotFoundException {
        Course existedCourse = this.getCourse(id);
        repository.delete(existedCourse);
        return existedCourse;
    }

    private Course populateExistedCourseWithNewData(Course existedCourse, Course aCourse) {
        existedCourse.setTitle(aCourse.getTitle());
        existedCourse.setDescription(aCourse.getDescription());
        existedCourse.setUpdateDate(aCourse.getUpdateDate());
        existedCourse.setInstructor(aCourse.getInstructor());
        existedCourse.setStartDate(aCourse.getStartDate());
        existedCourse.setTotalPlace(aCourse.getTotalPlace());
        existedCourse.setWorkload(aCourse.getWorkload());
        return existedCourse;
    }
}
