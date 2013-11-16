package com.realtech.coursehateoas.course.web;


import com.realtech.coursehateoas.api.ApplicationProtocol;
import com.realtech.coursehateoas.api.resources.IndexResource;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/", produces = ApplicationProtocol.MEDIA_TYPE)
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<IndexResource> getIndex(){
        IndexResource indexResource = new IndexResource();
        Link selfLink = linkTo(methodOn(IndexController.class).getIndex()).withSelfRel();
        Link coursesLink = linkTo(CourseController.class).withRel(ApplicationProtocol.COURSES_REL);
        indexResource.add(selfLink);
        indexResource.add(coursesLink);
        return new ResponseEntity<IndexResource>(indexResource, HttpStatus.OK);
    }
}
