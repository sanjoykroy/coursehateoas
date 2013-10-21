package com.realtech.coursehateoas.api.resources;

import org.springframework.hateoas.ResourceSupport;

public class IndexResource extends ResourceSupport {

    private String message = "Welcome to Real Tech courses API!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
