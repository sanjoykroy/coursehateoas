package com.realtech.coursehateoas.course.service;


import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.infrastructure.persistence.CourseRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CourseServiceImplTest {

    private static final Long VALID_COURSE_ID = 100L;
    private static final Long INVALID_COURSE_ID = -999L;

    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseRepository repositoryMock;

    @BeforeMethod
    public void setUp(){
        courseService = new CourseServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllCourses() throws Exception {
        when(repositoryMock.findAll(Mockito.any(PageRequest.class))).thenReturn(getFakeCourses());

        Iterable<Course> actual = courseService.getCourses();

        assertThat(actual, is(notNullValue()));
        verify(repositoryMock).findAll();
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void shouldReturnACourse() throws Exception {
        when(repositoryMock.findOne(VALID_COURSE_ID)).thenReturn(getAFakeCourse());

        Course actual = courseService.getCourse(VALID_COURSE_ID);

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(VALID_COURSE_ID));
        verify(repositoryMock).findOne(VALID_COURSE_ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void shouldThrowCourseNotFoundExceptionWhenCourseIsNotAvailable() throws Exception {
        when(repositoryMock.findOne(INVALID_COURSE_ID)).thenReturn(null);

        courseService.getCourse(INVALID_COURSE_ID);

        verify(repositoryMock).findOne(INVALID_COURSE_ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void shouldCreateACourse() throws Exception {
        Course fakeCourse = getAFakeCourse();
        when(repositoryMock.save(fakeCourse)).thenReturn(fakeCourse);

        Course actual = courseService.createCourse(fakeCourse);

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(VALID_COURSE_ID));
        verify(repositoryMock).save(fakeCourse);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void shouldThrowCourseNotFoundExceptionWhenUpdatingCourseIsNotAvailable() throws Exception {
        when(repositoryMock.findOne(INVALID_COURSE_ID)).thenReturn(null);

        courseService.updateCourse(getAFakeCourse());

        verify(repositoryMock).findOne(INVALID_COURSE_ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void shouldUpdateACourse() throws Exception {
        Course fakeCourse = getAFakeCourse();
        when(repositoryMock.findOne(fakeCourse.getId())).thenReturn(fakeCourse);
        when(repositoryMock.saveAndFlush(fakeCourse)).thenReturn(fakeCourse);

        Course actual = courseService.updateCourse(fakeCourse);

        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(VALID_COURSE_ID));
        verify(repositoryMock).findOne(VALID_COURSE_ID);
        verify(repositoryMock).saveAndFlush(fakeCourse);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void shouldThrowCourseNotFoundExceptionWhenDeletingCourseIsNotAvailable() throws Exception {
        when(repositoryMock.findOne(INVALID_COURSE_ID)).thenReturn(null);

        courseService.deleteCourse(INVALID_COURSE_ID);

        verify(repositoryMock).delete(INVALID_COURSE_ID);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void shouldDeleteACourse() throws Exception {
        Course fakeCourse = getAFakeCourse();
        when(repositoryMock.findOne(VALID_COURSE_ID)).thenReturn(fakeCourse);

        courseService.deleteCourse(VALID_COURSE_ID);

        verify(repositoryMock).findOne(VALID_COURSE_ID);
        verify(repositoryMock).delete(fakeCourse);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void shouldReturnPaginatedCourses() throws Exception {
        when(repositoryMock.findAll(Mockito.any(PageRequest.class))).thenReturn(getPaginatedFakeCourses());

        Page<Course> actual = courseService.getPaginatedCourses(1, 1);

        assertThat(actual, is(notNullValue()));
        verify(repositoryMock).findAll(Mockito.any(PageRequest.class));
        verifyNoMoreInteractions(repositoryMock);
    }

    // Helper

    private Page<Course> getFakeCourses(){
        return new PageImpl<Course>(Arrays.asList(getAFakeCourse()));
    }

    private Page<Course> getPaginatedFakeCourses(){
        return new PageImpl<Course>(Arrays.asList(getAFakeCourse()));
    }

    private Course getAFakeCourse(){
        Course course = new Course();
        course.setId(VALID_COURSE_ID);
        return course;
    }
}
