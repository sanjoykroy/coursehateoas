package com.realtech.coursehateoas.course.service;

import com.realtech.coursehateoas.course.annotation.ControllerService;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.domain.model.CourseStatus;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.exception.IllegalCourseActionException;
import com.realtech.coursehateoas.course.infrastructure.persistence.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
        aCourse.setCourseStatus(CourseStatus.NEW);
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

    @Override
    public Page<Course> getPaginatedCourses(int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return repository.findAll(new PageRequest(page, pageSize, sort));
    }

    @Override
    public Course approveCourse(Long id) throws CourseNotFoundException {
        Course course = getCourse(id);
        if(course.isApprovable()){
            course.setCourseStatus(CourseStatus.APPROVED);
            return repository.saveAndFlush(course);
        } else {
            throw new IllegalCourseActionException("Course with id ["+id+"] is not eligible to update.");
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
