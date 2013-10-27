package com.realtech.coursehateoas.course;

import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.service.CourseService;
import com.realtech.coursehateoas.course.web.CourseController;
import com.realtech.coursehateoas.course.web.CourseResourceAssembler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.isA;
import static org.hamcrest.core.Is.is;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class CourseControllerTest {

    private static final Date FAKE_DATE = new Date();

    @InjectMocks
    private CourseController controller;
    @Mock
    private CourseService serviceMock;
    @Mock
    private View mockView;
    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        controller = new CourseController();
        ReflectionTestUtils.setField(controller, "courseResourceAssembler" , new CourseResourceAssembler());
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controller).setSingleView(mockView).build();
    }

    @Test
    public void shouldLoadAllCourses() throws Exception {
        Course course = getTestCourse();
        Iterable<Course> courses = Arrays.asList(course);
        when(serviceMock.getCourses()).thenReturn(courses);

        this.mockMvc.perform(get("/courses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("add-form")))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].links[0].rel", is("self")))
                .andExpect(jsonPath("$.content[0].links[1].rel", is("update-form")))
                .andExpect(jsonPath("$.content[0].links[2].rel", is("cancel-action")))
                .andExpect(jsonPath("$.content[0].title", is("Test Course")));

        verify(serviceMock).getCourses();
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void shouldLoadACourse() throws Exception {
        Course course = getTestCourse();
        when(serviceMock.getCourse(100L)).thenReturn(course);

        this.mockMvc.perform(get("/courses/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("update-form")))
                .andExpect(jsonPath("$.links[2].rel", is("cancel-action")))
                .andExpect(jsonPath("$.title", is("Test Course")));

        verify(serviceMock).getCourse(100L);
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void shouldLoadACourseAddForm() throws Exception {

        this.mockMvc.perform(get("/courses/form")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("courses")))
                .andExpect(jsonPath("$.links[2].rel", is("create-action")))
                .andExpect(jsonPath("$.title", is("")));

        verifyZeroInteractions(serviceMock);
    }

    @Test
    public void shouldCreateACourse() throws Exception {
        when(serviceMock.createCourse(isA(Course.class))).thenReturn(getTestCourse());

        mockMvc.perform(post("/courses/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("        {" +
                        "            \"title\": \"Test Course\"," +
                        "            \"description\": \"Test Course Description\"," +
                        "            \"instructor\": \"Test Instructor\"," +
                        "            \"startDate\": \""+FAKE_DATE.getTime()+"\"," +
                        "            \"workload\": \"Test work load\"" +
                        "        }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("update-form")))
                .andExpect(jsonPath("$.links[2].rel", is("cancel-action")))
                .andExpect(jsonPath("$.title", is("Test Course")));

        verify(serviceMock).createCourse(isA(Course.class));
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void shouldLoadACourseUpdateForm() throws Exception {

        Course course = getTestCourse();
        when(serviceMock.getCourse(100L)).thenReturn(course);

        this.mockMvc.perform(get("/courses/100/update-form")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("courses")))
                .andExpect(jsonPath("$.links[2].rel", is("update-action")))
                .andExpect(jsonPath("$.title", is("Test Course")));

        verify(serviceMock).getCourse(100L);
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void shouldUpdateACourse() throws Exception {
        Course updatedCourse = getTestCourse();
        updatedCourse.setTitle("New Title");
        when(serviceMock.updateCourse(isA(Course.class))).thenReturn(updatedCourse);

        mockMvc.perform(put("/courses/100/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("        {" +
                        "            \"title\": \"New Title\"," +
                        "            \"description\": \"Test Course Description\"," +
                        "            \"instructor\": \"Test Instructor\"," +
                        "            \"startDate\": \""+FAKE_DATE.getTime()+"\"," +
                        "            \"workload\": \"Test work load\"" +
                        "        }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[1].rel", is("update-form")))
                .andExpect(jsonPath("$.links[2].rel", is("cancel-action")))
                .andExpect(jsonPath("$.title", is("New Title")));

        verify(serviceMock).updateCourse(isA(Course.class));
        verifyNoMoreInteractions(serviceMock);
    }

    @Test
    public void shouldDeleteACourse() throws Exception {
        doNothing().when(serviceMock).deleteCourse(100L);

        mockMvc.perform(delete("/courses/100/cancel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(serviceMock).deleteCourse(100L);
        verifyNoMoreInteractions(serviceMock);
    }

    // Helper Method.

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
}
