package com.realtech.coursehateoas.course.infrastructure.persistence;


import com.realtech.coursehateoas.course.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
public interface CourseRepository extends JpaRepository<Course, Long> {
}
