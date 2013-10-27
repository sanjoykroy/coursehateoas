package com.realtech.coursehateoas.course.service;

import com.realtech.coursehateoas.course.annotation.ControllerService;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.infrastructure.persistence.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
    public Course updateCourse(Course aCourse) throws CourseNotFoundException {
        Course existedCourse = this.getCourse(aCourse.getId());
        if(existedCourse == null){
            throw new CourseNotFoundException("Course is not found.");
        } else {
            return repository.saveAndFlush(populateExistedCourseWithNewData(existedCourse, aCourse));
        }
    }

    @Override
    public void deleteCourse(Long id) throws CourseNotFoundException {
        Course existedCourse = this.getCourse(id);
        if(existedCourse == null){
            throw new CourseNotFoundException("Course is not found.");
        } else {
            repository.delete(existedCourse);
        }
    }

    private Course populateExistedCourseWithNewData(Course existedCourse, Course aCourse) {
        existedCourse.setTitle(aCourse.getTitle());
        existedCourse.setDescription(aCourse.getDescription());
        existedCourse.setUpdateDate(new Date());
        existedCourse.setInstructor(aCourse.getInstructor());
        existedCourse.setStartDate(aCourse.getStartDate());
        existedCourse.setWorkload(aCourse.getWorkload());
        return existedCourse;
    }
}
