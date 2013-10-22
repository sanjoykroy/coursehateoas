package com.realtech.coursehateoas.course.web;

import com.realtech.coursehateoas.api.resources.CourseResource;
import com.realtech.coursehateoas.api.resources.CourseResourceCollection;
import com.realtech.coursehateoas.course.annotation.BoundaryController;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.service.CourseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@BoundaryController
@RequestMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseResourceAssembler courseResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<CourseResourceCollection> showCourses() {
        LOGGER.info("Loading all courses");

        Iterable<Course> courses = courseService.getCourses();
        List<CourseResource> courseResources;
        if(courses != null){
            courseResources = courseResourceAssembler.toResources(courses);
        } else {
            courseResources = courseResourceAssembler.toResources(new ArrayList<Course>());
        }

        CourseResourceCollection courseResourceCollection = new CourseResourceCollection(courseResources);
        courseResourceCollection.add(linkTo(methodOn(CourseController.class).showCourses()).withSelfRel());
        return new ResponseEntity<CourseResourceCollection>(courseResourceCollection, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    HttpEntity<CourseResource> createCourse(@RequestBody Course course) {
        LOGGER.info("Creating a course - [{}]", course);
        Course newCourse = courseService.createCourse(course);
        CourseResource courseResource = courseResourceAssembler.toResource(newCourse);
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    HttpEntity<CourseResource> showCourse(@PathVariable Long id) {
        LOGGER.info("Loading a course based on id [{}]", id);
        CourseResource courseResource = courseResourceAssembler.toResource(courseService.getCourse(id));
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    HttpEntity<CourseResource> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        LOGGER.info("Updating a course - Course Id [{}]", id);
        Course updatedCourse = courseService.updateCourse(id, course);
        CourseResource courseResource = courseResourceAssembler.toResource(updatedCourse);
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    HttpEntity<CourseResource> cancelCourse(@PathVariable Long id) {
        LOGGER.info("Deleting a course - Course Id [{}]", id);
        Course deletedCourse = courseService.deleteCourse(id);
        CourseResource courseResource = courseResourceAssembler.toResource(deletedCourse);
        return new ResponseEntity<CourseResource>(courseResource, HttpStatus.OK);
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
