package com.realtech.coursehateoas.course.infrastructure.persistence;


import com.realtech.coursehateoas.course.domain.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
