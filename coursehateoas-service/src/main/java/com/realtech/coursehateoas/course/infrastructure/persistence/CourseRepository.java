package com.realtech.coursehateoas.course.infrastructure.persistence;


import com.realtech.coursehateoas.course.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
