package com.realtech.coursehateoas.course.web;

import com.realtech.coursehateoas.api.ApplicationProtocol;
import com.realtech.coursehateoas.api.resources.CourseForm;
import com.realtech.coursehateoas.api.resources.CourseResource;
import com.realtech.coursehateoas.api.resources.CourseResourceCollection;
import com.realtech.coursehateoas.course.annotation.BoundaryController;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.service.CourseService;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@BoundaryController
@RequestMapping(value = "/courses")
public class CourseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseResourceAssembler courseResourceAssembler;

    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseResourceCollection> showCourses() {
        LOGGER.info("Returning all courses");

        Iterable<Course> courses = courseService.getCourses();
        List<CourseResource> courseResources;
        if(courses != null){
            courseResources = courseResourceAssembler.toResources(courses);
        } else {
            courseResources = courseResourceAssembler.toResources(new ArrayList<Course>());
        }

        CourseResourceCollection courseResourceCollection = new CourseResourceCollection(courseResources);
        courseResourceCollection.add(linkTo(methodOn(CourseController.class).showCourses()).withSelfRel());
        courseResourceCollection.add(linkTo(methodOn(CourseController.class).getCreateForm()).withRel(ApplicationProtocol.FORM_REL));

        return new ResponseEntity<CourseResourceCollection>(courseResourceCollection, HttpStatus.OK);
    }

    @RequestMapping(value = "/form",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseForm> getCreateForm() {
        CourseForm courseForm = new CourseForm();
        courseForm.setTitle("");
        courseForm.setDescription("");
        courseForm.setInstructor("");
        courseForm.setWorkload("");
        courseForm.setStartDate(new DateTime().plusDays(1).toDate());

        Link selfLink = linkTo(methodOn(CourseController.class).getCreateForm()).withSelfRel();
        Link createLink = ControllerLinkBuilder.linkTo(CourseController.class).slash("create").withRel(ApplicationProtocol.CREATE_ACTION_REL);
        Link coursesLink = linkTo(methodOn(CourseController.class).showCourses()).withRel(ApplicationProtocol.COURSES_REL);
        courseForm.add(Arrays.asList(selfLink, coursesLink, createLink));
        return new ResponseEntity<CourseForm>(courseForm, HttpStatus.OK);
    }


    @RequestMapping(value = "/create",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseResource> createCourse(@RequestBody CourseForm form) {
        LOGGER.info("Creating a course - [{}]", form);
        Course course = getCourseInfoFromForm(form);
        Course newCourse = courseService.createCourse(course);
        CourseResource courseResource = courseResourceAssembler.toResource(newCourse);
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseResource> showCourse(@PathVariable Long id) {
        LOGGER.info("Loading a course based on id [{}]", id);
        CourseResource courseResource = courseResourceAssembler.toResource(courseService.getCourse(id));
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/update-form",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseForm> getUpdateForm(@PathVariable Long id) {

        Course courseToBeUpdated = courseService.getCourse(id);

        CourseForm courseForm = new CourseForm();
        courseForm.setTitle(courseToBeUpdated.getTitle());
        courseForm.setDescription(courseToBeUpdated.getDescription());
        courseForm.setInstructor(courseToBeUpdated.getInstructor());
        courseForm.setWorkload(courseToBeUpdated.getWorkload());
        courseForm.setStartDate(courseToBeUpdated.getStartDate());

        Link selfLink = linkTo(methodOn(CourseController.class).showCourse(courseToBeUpdated.getId())).withSelfRel();
        Link coursesLink = linkTo(methodOn(CourseController.class).showCourses()).withRel(ApplicationProtocol.COURSES_REL);
        Link updateLink = ControllerLinkBuilder.linkTo(CourseController.class).slash("/"+courseToBeUpdated.getId()+"/update").withRel(ApplicationProtocol.UPDATE_ACTION_REL);
        courseForm.add(Arrays.asList(selfLink, coursesLink, updateLink));
        return new ResponseEntity<CourseForm>(courseForm, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/update",
                    method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseResource> updateCourse(@PathVariable Long id, @RequestBody CourseForm form) {
        LOGGER.info("Updating a course - Course Id [{}]", id);
        Course course = getCourseInfoFromForm(form);
        course.setId(id);
        Course updatedCourse = courseService.updateCourse(course);
        CourseResource courseResource = courseResourceAssembler.toResource(updatedCourse);
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/cancel",
                    method = RequestMethod.DELETE)
    public ResponseEntity cancelCourse(@PathVariable Long id) {
        LOGGER.info("Deleting a course - Course Id [{}]", id);
        courseService.deleteCourse(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Course getCourseInfoFromForm(CourseForm form){
        Course course = new Course();
        course.setCreateDate(new Date());
        course.setDescription(form.getDescription());
        course.setEnabled(true);
        course.setInstructor(form.getInstructor());
        course.setStartDate(form.getStartDate());
        course.setTitle(form.getTitle());
        course.setUpdateDate(new Date());
        course.setWorkload(form.getWorkload());
        return course;
    }

    @ExceptionHandler
    ResponseEntity handleExceptions(Exception ex) {
        ResponseEntity responseEntity = null;
        if (ex instanceof CourseNotFoundException) {
            responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


}
