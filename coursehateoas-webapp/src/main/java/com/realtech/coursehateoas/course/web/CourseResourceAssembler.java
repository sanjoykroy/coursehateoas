package com.realtech.coursehateoas.course.web;


import com.realtech.coursehateoas.course.domain.model.Course;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CourseResourceAssembler implements ResourceAssembler<Course, Resource<Course>> {

    private Class<CourseController> controllerClass = CourseController.class;

    @Override
    public Resource<Course> toResource(Course course) {
        Resource<Course> courseResource = new Resource<Course>(course);
        Link selfLink = linkTo(methodOn(controllerClass).showCourse(course.getId())).withSelfRel();
        Link updateLink = linkTo(methodOn(controllerClass).updateCourse(course.getId(), course)).withRel("/update");
        Link cancelLink = linkTo(methodOn(controllerClass).cancelCourse(course.getId())).withRel("/cancel");
        Link coursesLink = linkTo(methodOn(controllerClass).showCourses()).withRel("/courses");
        courseResource.add(selfLink);
        courseResource.add(updateLink);
        courseResource.add(cancelLink);
        courseResource.add(coursesLink);
        return courseResource;
    }
}

