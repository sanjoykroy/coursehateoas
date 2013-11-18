package com.realtech.coursehateoas.course.domain.model;

import org.testng.annotations.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


public class CourseTest {

    @Test
    public void shouldConstructACourse() throws Exception {
        Course aFakeCourse = getCourse("test", "testDescription");

        assertThat(aFakeCourse, is(notNullValue()));
        assertThat(aFakeCourse.getTitle(), is("test"));
        assertThat(aFakeCourse.getDescription(), is("testDescription"));
        assertThat(aFakeCourse.getCreateDate(), is(notNullValue()));
        assertThat(aFakeCourse.getUpdateDate(), is(notNullValue()));
        assertThat(aFakeCourse.getStartDate(), is(notNullValue()));
        assertThat(aFakeCourse.getInstructor(), is("testInstructor"));
        assertThat(aFakeCourse.isEnabled(), is(true));
        assertThat(aFakeCourse.getCourseStatus(), is(CourseStatus.NEW));
        assertThat(aFakeCourse.getWorkload(), is("10 hours"));
    }

    @Test
    public void shouldReturnTrueIfCourseIsApprovable() throws Exception{
        Course aFakeCourse = getCourse("test", "testDescription");
        assertThat(aFakeCourse.isApprovable(), is(true));
    }

    @Test
    public void shouldReturnFalseIfCourseIsNotApprovable() throws Exception{
        Course aFakeCourse = getCourse("test", "testDescription");
        aFakeCourse.setCourseStatus(CourseStatus.APPROVED);
        assertThat(aFakeCourse.isApprovable(), is(false));
    }

    @Test
    public void shouldReturnTrueIfCourseIsPublishable() throws Exception{
        Course aFakeCourse = getCourse("test", "testDescription");
        aFakeCourse.setCourseStatus(CourseStatus.APPROVED);
        assertThat(aFakeCourse.isPublishable(), is(true));
    }

    @Test
    public void shouldReturnFalseIfCourseIsNotPublishable() throws Exception{
        Course aFakeCourse = getCourse("test", "testDescription");
        aFakeCourse.setCourseStatus(CourseStatus.NEW);
        assertThat(aFakeCourse.isPublishable(), is(false));
    }

    private Course getCourse(String title, String description){
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
        return course;
    }
}
