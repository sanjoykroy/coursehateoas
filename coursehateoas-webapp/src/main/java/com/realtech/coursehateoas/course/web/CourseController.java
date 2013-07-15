package com.realtech.coursehateoas.course.web;

import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@ExposesResourceFor(Course.class)
@RequestMapping(value = "/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseResourceAssembler courseResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    HttpEntity<Resources<Resource<Course>>> showCourses() {
        LOGGER.info("Loading all courses");
        Collection<Resource<Course>> courseResourceCollection = new ArrayList<Resource<Course>>();
        for (Course course : courseService.getCourses()) {
            Resource<Course> courseResource = courseResourceAssembler.toResource(course);
            courseResourceCollection.add(courseResource);
        }

        Resources<Resource<Course>> courseResources = new Resources<Resource<Course>>(courseResourceCollection);
        courseResources.add(linkTo(methodOn(CourseController.class).showCourses()).withSelfRel());
        return new ResponseEntity<Resources<Resource<Course>>>(courseResources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    HttpEntity<Resource<Course>> createCourse(@RequestBody Course course) {
        LOGGER.info("Creating a course - [{}]", course);
        Course newCourse = courseService.createCourse(course);
        Resource<Course> courseResource = courseResourceAssembler.toResource(newCourse);
        return new ResponseEntity<Resource<Course>>(courseResource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    HttpEntity<Resource<Course>> showCourse(@PathVariable Long id) {
        LOGGER.info("Loading a course based on id [{}]", id);
        Resource<Course> courseResource = courseResourceAssembler.toResource(courseService.getCourse(id));
        return new ResponseEntity<Resource<Course>>(courseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    HttpEntity<Resource<Course>> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        LOGGER.info("Updating a course - Course Id [{}]", id);
        Course updatedCourse = courseService.updateCourse(id, course);
        Resource<Course> courseResource = courseResourceAssembler.toResource(updatedCourse);
        return new ResponseEntity<Resource<Course>>(courseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    HttpEntity<Resource<Course>> cancelCourse(@PathVariable Long id) {
        LOGGER.info("Deleting a course - Course Id [{}]", id);
        Course deletedCourse = courseService.deleteCourse(id);
        Resource<Course> courseResource = courseResourceAssembler.toResource(deletedCourse);
        return new ResponseEntity<Resource<Course>>(courseResource, HttpStatus.OK);
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
