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

        CourseResource resource = new CourseResource();
        resource.setCourseId(course.getId());
        resource.setTitle(course.getTitle());
        resource.setDescription(course.getDescription());
        resource.setInstructor(course.getInstructor());
        resource.setStartDate(course.getStartDate());
        resource.setWorkload(course.getWorkload());
        resource.setCreatedDate(course.getCreateDate());
        resource.setStatus(course.getCourseStatus().name());

        Link selfLink = linkTo(methodOn(CourseController.class).showCourse(course.getId())).withSelfRel();
        Link updateLink = linkTo(methodOn(CourseController.class).getUpdateForm(course.getId())).withRel(ApplicationProtocol.UPDATE_FORM_REL);
        Link cancelLink = linkTo(methodOn(CourseController.class).cancelCourse(course.getId())).withRel(ApplicationProtocol.CANCEL_ACTION_REL);
        resource.add(selfLink);
        resource.add(updateLink);
        resource.add(cancelLink);
        return resource;
    }
}

