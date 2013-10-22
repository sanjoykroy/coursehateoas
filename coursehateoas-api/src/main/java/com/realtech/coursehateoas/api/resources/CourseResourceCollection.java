package com.realtech.coursehateoas.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class CourseResourceCollection extends Resources<CourseResource> {
    protected CourseResourceCollection() {
    }

    public CourseResourceCollection(Iterable<CourseResource> content, Link... links) {
        super(content, links);
    }
}
