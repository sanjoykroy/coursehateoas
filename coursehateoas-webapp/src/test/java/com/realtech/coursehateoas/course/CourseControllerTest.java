package com.realtech.coursehateoas.course;


import com.realtech.coursehateoas.api.resources.CourseResource;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.service.CourseService;
import com.realtech.coursehateoas.course.web.CourseController;
import com.realtech.coursehateoas.course.web.CourseResourceAssembler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;
import org.springframework.web.servlet.View;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

public class CourseControllerTest {

    private static final Date FAKE_DATE = new Date();

    @InjectMocks
    private CourseController controller;
    @Mock
    private CourseService serviceMock;
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
        Course course = getTestCourse();
        Iterable<Course> courses = Arrays.asList(course);
        when(serviceMock.getCourses()).thenReturn(courses);
        when(courseResourceAssemblerMock.toResources(courses)).thenReturn(Arrays.asList(new CourseResource()));
        this.mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock).getCourses();
        verify(courseResourceAssemblerMock).toResources(courses);
    }

    @Test(enabled = false)
    public void shouldCreateACourse() throws Exception {
        when(serviceMock.createCourse(isA(Course.class))).thenReturn(isA(Course.class));

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .body(getNewCourseJson().getBytes()))
                .andExpect(status().isCreated());

        verify(serviceMock).createCourse(isA(Course.class));
    }

    @Test
    public void shouldLoadACourse() throws Exception {
        Course course = getTestCourse();
        when(serviceMock.getCourse(100L)).thenReturn(course);
        when(courseResourceAssemblerMock.toResource(course)).thenReturn(new CourseResource());
        this.mockMvc.perform(get("/courses/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(serviceMock).getCourse(100L);
        verify(courseResourceAssemblerMock).toResource(course);
    }

    @Test(enabled = false)
    public void shouldUpdateACourse() throws Exception {
        Course course = getTestCourse();
        Course updatedCourse = getTestCourse();
        updatedCourse.setTotalPlace(25);
        when(serviceMock.updateCourse(100L, course)).thenReturn(updatedCourse);

        mockMvc.perform(put("/courses/100")
                .contentType(MediaType.APPLICATION_JSON)
                .body(getCourseJson().getBytes()))
                .andExpect(status().isOk());

        verify(serviceMock).updateCourse(100L, course);
    }

    @Test(enabled = false)
    public void shouldDeleteACourse() throws Exception {
        Course deletedCourse = getTestCourse();
        when(serviceMock.deleteCourse(100L)).thenReturn(deletedCourse);

        mockMvc.perform(delete("/courses/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(serviceMock).deleteCourse(100L);
    }


    private Course getTestCourse() {
        Course course = new Course();
        course.setId(100L);
        course.setTitle("Test Course");
        course.setDescription("Test Course Description");
        course.setInstructor("Test Instructor");
        course.setCreateDate(FAKE_DATE);
        course.setUpdateDate(FAKE_DATE);
        course.setStartDate(FAKE_DATE);
        course.setTotalPlace(10);
        course.setWorkload("Test work load");
        course.setEnabled(true);
        return course;
    }

    private String getCourseJson() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return "        {" +
                "            \"id\": 100," +
                "            \"title\": \"Test Course\"," +
                "            \"description\": \"Test Course Description\"," +
                "            \"instructor\": \"Test Instructor\"," +
                "            \"totalPlace\": 25," +
                "            \"createDate\": \""+df.format(FAKE_DATE)+"\"," +
                "            \"updateDate\": \""+df.format(FAKE_DATE)+"\"," +
                "            \"startDate\": \""+df.format(FAKE_DATE)+"\"," +
                "            \"workload\": \"5 hours per week\"," +
                "            \"enabled\": true" +
                "        }";
    }

    private String getNewCourseJson() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        return "        {" +
                "            \"title\": \"Test Course\"," +
                "            \"description\": \"Test Course Description\"," +
                "            \"instructor\": \"Test Instructor\"," +
                "            \"totalPlace\": 10," +
                "            \"createDate\": \""+df.format(FAKE_DATE)+"\"," +
                "            \"updateDate\": \""+df.format(FAKE_DATE)+"\"," +
                "            \"startDate\": \""+df.format(FAKE_DATE)+"\"," +
                "            \"workload\": \"Test work load\"," +
                "            \"enabled\": true" +
                "        }";
    }
}
