package com.realtech.coursehateoas.course.web;

import com.realtech.coursehateoas.api.ApplicationProtocol;
import com.realtech.coursehateoas.api.resources.CourseForm;
import com.realtech.coursehateoas.api.resources.CourseResource;
import com.realtech.coursehateoas.api.resources.CourseResourceCollection;
import com.realtech.coursehateoas.course.annotation.BoundaryController;
import com.realtech.coursehateoas.course.domain.model.Course;
import com.realtech.coursehateoas.course.exception.CourseNotFoundException;
import com.realtech.coursehateoas.course.service.CourseService;

import com.realtech.coursehateoas.course.support.NavigationLinkBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
                    produces = ApplicationProtocol.MEDIA_TYPE_THIN_JSON)
    public ResponseEntity<CourseResourceCollection> showCourses(
               @RequestParam(value = "page", required = false, defaultValue = "0") int page,
               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        LOGGER.info("Returning all courses");

        Page<Course> courses = courseService.getPaginatedCourses(page, pageSize);

        List<CourseResource> resources;
        if(courses != null){
            resources = courseResourceAssembler.toResources(courses.getContent());
        } else {
            resources = courseResourceAssembler.toResources(new ArrayList<Course>());
        }

        CourseResourceCollection collection = new CourseResourceCollection(resources);
        collection.add(linkTo(methodOn(CourseController.class).getCreateForm()).withRel(ApplicationProtocol.FORM_REL));

        NavigationLinkBuilder.addNavigationLinks(collection, courses);

        return new ResponseEntity<CourseResourceCollection>(collection, HttpStatus.OK);
    }

    @RequestMapping(value = "/form",
                    method = RequestMethod.GET,
                    produces = ApplicationProtocol.MEDIA_TYPE_THIN_JSON)
    public ResponseEntity<CourseForm> getCreateForm() {
        CourseForm form = new CourseForm();
        form.setTitle("");
        form.setDescription("");
        form.setInstructor("");
        form.setWorkload("");
        form.setStartDate(new DateTime().plusDays(1).toDate());

        Link selfLink = linkTo(methodOn(CourseController.class).getCreateForm()).withSelfRel();
        Link createLink = ControllerLinkBuilder.linkTo(CourseController.class).slash("create").withRel(ApplicationProtocol.CREATE_ACTION_REL);
        Link coursesLink = linkTo(CourseController.class).withRel(ApplicationProtocol.COURSES_REL);
        form.add(Arrays.asList(selfLink, coursesLink, createLink));
        return new ResponseEntity<CourseForm>(form, HttpStatus.OK);
    }


    @RequestMapping(value = "/create",
                    method = RequestMethod.POST,
                    produces = ApplicationProtocol.MEDIA_TYPE_THIN_JSON,
                    consumes = ApplicationProtocol.MEDIA_TYPE_THIN_JSON)
    public ResponseEntity<CourseResource> createCourse(@RequestBody CourseForm form) {
        LOGGER.info("Creating a course - [{}]", form);
        Course course = getCourseInfoFromForm(form);
        Course newCourse = courseService.createCourse(course);
        CourseResource resource = courseResourceAssembler.toResource(newCourse);
        Link updateLink = linkTo(methodOn(CourseController.class).getUpdateForm(course.getId())).withRel(ApplicationProtocol.UPDATE_FORM_REL);
        resource.add(updateLink);
        Link cancelLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.CANCEL_ACTION_REL);
        resource.add(cancelLink);
        Link coursesLink = linkTo(CourseController.class).withRel(ApplicationProtocol.COURSES_REL);
        resource.add(coursesLink);
        return new ResponseEntity<CourseResource>(resource, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseResource> showCourse(@PathVariable Long id) {
        LOGGER.info("Loading a course based on id [{}]", id);
        Course course = courseService.getCourse(id);

        CourseResource resource = courseResourceAssembler.toResource(course);

        Link updateLink = linkTo(methodOn(CourseController.class).getUpdateForm(course.getId())).withRel(ApplicationProtocol.UPDATE_FORM_REL);
        resource.add(updateLink);
        Link cancelLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.CANCEL_ACTION_REL);
        resource.add(cancelLink);

        if(course.isApprovable()){
            Link approveLink = linkTo(methodOn(CourseController.class).approveCourse(course.getId())).withRel(ApplicationProtocol.APPROVE_ACTION_REL);
            resource.add(approveLink);
        }
        return new ResponseEntity<CourseResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/update-form",
            method = RequestMethod.GET,
            produces = ApplicationProtocol.MEDIA_TYPE_THIN_JSON)
    public ResponseEntity<CourseForm> getUpdateForm(@PathVariable Long id) {

        Course courseToBeUpdated = courseService.getCourse(id);

        CourseForm form = new CourseForm();
        form.setTitle(courseToBeUpdated.getTitle());
        form.setDescription(courseToBeUpdated.getDescription());
        form.setInstructor(courseToBeUpdated.getInstructor());
        form.setWorkload(courseToBeUpdated.getWorkload());
        form.setStartDate(courseToBeUpdated.getStartDate());

        Link selfLink = linkTo(methodOn(CourseController.class).showCourse(courseToBeUpdated.getId())).withSelfRel();
        Link coursesLink = linkTo(methodOn(CourseController.class).showCourses(1, 10)).withRel(ApplicationProtocol.COURSES_REL);
        Link updateLink = ControllerLinkBuilder.linkTo(CourseController.class).slash("/"+courseToBeUpdated.getId()+"/update").withRel(ApplicationProtocol.UPDATE_ACTION_REL);
        form.add(Arrays.asList(selfLink, coursesLink, updateLink));
        return new ResponseEntity<CourseForm>(form, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/update",
                    method = RequestMethod.PUT,
                    produces = ApplicationProtocol.MEDIA_TYPE_THIN_JSON,
                    consumes = ApplicationProtocol.MEDIA_TYPE_THIN_JSON)
    public ResponseEntity<CourseResource> updateCourse(@PathVariable Long id, @RequestBody CourseForm form) {
        LOGGER.info("Updating a course - Course Id [{}]", id);
        Course course = getCourseInfoFromForm(form);
        course.setId(id);
        Course updatedCourse = courseService.updateCourse(course);
        CourseResource resource = courseResourceAssembler.toResource(updatedCourse);
        Link updateLink = linkTo(methodOn(CourseController.class).getUpdateForm(course.getId())).withRel(ApplicationProtocol.UPDATE_FORM_REL);
        resource.add(updateLink);
        Link cancelLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.CANCEL_ACTION_REL);
        resource.add(cancelLink);
        return new ResponseEntity<CourseResource>(resource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/cancel",
                    method = RequestMethod.DELETE)
    public ResponseEntity cancelCourse(@PathVariable Long id) {
        LOGGER.info("Deleting a course - Course Id [{}]", id);
        courseService.deleteCourse(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/approve",
            method = RequestMethod.PUT)
    public ResponseEntity approveCourse(@PathVariable Long id) {
        LOGGER.info("Approving a course - Course Id [{}]", id);
        Course course = courseService.approveCourse(id);

        CourseResource resource = courseResourceAssembler.toResource(course);

        Link updateLink = linkTo(methodOn(CourseController.class).getUpdateForm(course.getId())).withRel(ApplicationProtocol.UPDATE_FORM_REL);
        resource.add(updateLink);
        Link cancelLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.CANCEL_ACTION_REL);
        resource.add(cancelLink);

//        if(course.isApprovable()){
//            Link approveLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.APPROVE_ACTION_REL);
//            resource.add(approveLink);
//        }
        return new ResponseEntity<CourseResource>(resource, HttpStatus.OK);

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
