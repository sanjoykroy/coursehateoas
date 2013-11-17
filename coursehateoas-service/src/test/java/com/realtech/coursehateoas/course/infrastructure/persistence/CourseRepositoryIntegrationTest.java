package com.realtech.coursehateoas.course.infrastructure.persistence;


import com.realtech.coursehateoas.AbstractCourseHateoasRepositoryTest;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.domain.model.CourseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testng.annotations.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.Assert.assertTrue;

public class CourseRepositoryIntegrationTest extends AbstractCourseHateoasRepositoryTest {

    @Autowired
    private CourseRepository repository;

    @Test
    public void shouldSaveACourse() {
        Course savedCourse = createCourse("course1", "description1");
        assertThat(savedCourse.getId(), is(notNullValue()));
    }

    @Test
    public void shouldFindAllCourses() {
        repository.save(createCourse("course1", "description1"));

        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Page<Course> result = repository.findAll(new PageRequest(0, 10, sort));

        assertThat(result, is(notNullValue()));
        assertTrue(result.iterator().hasNext());
    }

    @Test
    public void shouldFindById() {
        Course course = repository.save(createCourse("course1", "description1"));

        Course savedCourse = repository.findOne(course.getId());

        assertThat(savedCourse.getId(), is(notNullValue()));
        assertThat(savedCourse.getTitle(), is("course1"));
        assertThat(savedCourse.getDescription(), is("description1"));
        assertThat(savedCourse.getCourseStatus(), is(CourseStatus.NEW));
    }

    // Helper Methods

    private void createSomeCourses(){
        createCourse("course1", "description1");
        createCourse("course2", "description2");
    }

    private Course createCourse(String title, String description){
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setCreateDate(new Date());
        course.setUpdateDate(new Date());
        course.setStartDate(new Date());
        course.setInstructor("testInstructor");
        course.setEnabled(true);
        course.setCourseStatus(CourseStatus.NEW);
        course.setWorkload("10 hours");
        return repository.save(course);
    }
}
