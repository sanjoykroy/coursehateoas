package com.realtech.coursehateoas.course.web;

import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.infrastructure.persistence.CourseRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private CourseRepository repository;
    @Autowired
    private CourseResourceAssembler courseResourceAssembler;

    @RequestMapping(method = RequestMethod.GET)
    HttpEntity<Resources<Resource<Course>>> showCourses() {
        LOGGER.info("Loading all courses");
        Collection<Resource<Course>> courseResourceCollection = new ArrayList<Resource<Course>>();
        for (Course course : repository.findAll()) {
            Resource<Course> courseResource = courseResourceAssembler.toResource(course);
            courseResourceCollection.add(courseResource);
        }

        Resources<Resource<Course>> courseResources = new Resources<Resource<Course>>(courseResourceCollection);
        courseResources.add(linkTo(methodOn(CourseController.class).showCourses()).withSelfRel());
        return new ResponseEntity<Resources<Resource<Course>>>(courseResources, HttpStatus.OK);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/{id}")
    HttpEntity<Resource<Course>> showSingleCourse(@PathVariable Long id) {
        LOGGER.info("Loading a single course based on id [{}]", id);
        Resource<Course> courseResource = courseResourceAssembler.toResource(repository.findOne(id));
        return new ResponseEntity<Resource<Course>>(courseResource, HttpStatus.OK);
    }
}
