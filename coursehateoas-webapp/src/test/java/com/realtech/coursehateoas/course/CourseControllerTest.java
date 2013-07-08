package com.realtech.coursehateoas.course;


import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.infrastructure.persistence.CourseRepository;
import com.realtech.coursehateoas.course.web.CourseController;
import com.realtech.coursehateoas.course.web.CourseResourceAssembler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.web.servlet.View;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

public class CourseControllerTest {

    @InjectMocks
    private CourseController controller;
    @Mock
    private CourseRepository repositoryMock;
    @Mock
    private CourseResourceAssembler courseResourceAssemblerMock;
    @Mock
    private View mockView;
    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        controller = new CourseController();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controller).setSingleView(mockView).build();
    }

    @Test
    public void shouldLoadAllCourseResources() throws Exception {
        Course course =  getTestCourse();
        Page<Course> courses = new PageImpl<Course>(Arrays.asList(course));
        when(repositoryMock.findAll()).thenReturn(courses);
        when(courseResourceAssemblerMock.toResource(course)).thenReturn(new Resource<Course>(course));
        this.mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(repositoryMock).findAll();
        verify(courseResourceAssemblerMock).toResource(course);
    }

    private Course getTestCourse(){
        Course course = new Course();
        course.setId(100L);
        course.setTitle("Test Course");
        course.setDescription("Test Course Description");
        course.setInstructor("Test Instructor");
        course.setCreateDate(new Date());
        course.setUpdateDate(new Date());
        course.setStartDate(new Date());
        course.setTotalPlace(10);
        course.setWorkload("Test work load");
        course.setEnabled(true);
        return course;
    }

}
