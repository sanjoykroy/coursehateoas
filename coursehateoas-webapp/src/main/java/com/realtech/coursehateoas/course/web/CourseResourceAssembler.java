package com.realtech.coursehateoas.course.web;


import com.realtech.coursehateoas.api.ApplicationProtocol;
import com.realtech.coursehateoas.api.resources.CourseResource;
import com.realtech.coursehateoas.course.domain.model.Course;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CourseResourceAssembler extends ResourceAssemblerSupport<Course, CourseResource> {

    public CourseResourceAssembler() {
        super(CourseController.class, CourseResource.class);
    }

    @Override
    public CourseResource toResource(Course course) {

        CourseResource courseResource = new CourseResource();
        courseResource.setCourseId(course.getId());
        courseResource.setTitle(course.getTitle());
        courseResource.setDescription(course.getDescription());
        courseResource.setInstructor(course.getInstructor());
        courseResource.setStartDate(course.getStartDate());
        courseResource.setWorkload(course.getWorkload());

        Link selfLink = linkTo(methodOn(CourseController.class).showCourse(course.getId())).withSelfRel();
        Link updateLink = linkTo(methodOn(CourseController.class).getUpdateForm(course.getId())).withRel(ApplicationProtocol.UPDATE_FORM_REL);
        Link cancelLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.CANCEL_ACTION_REL);
        courseResource.add(selfLink);
        courseResource.add(updateLink);
        courseResource.add(cancelLink);
        return courseResource;
    }
}

