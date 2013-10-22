package com.realtech.coursehateoas.api;


import org.springframework.http.MediaType;

public class ApplicationProtocol {

    public static final String MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

    public static final String COURSES_REL = "courses";

    public static final String FORM_REL = "add-form";

    public static final String CREATE_ACTION_REL = "create-action";

    public static final String UPDATE_ACTION_REL = "update-action";

    public static final String CANCEL_ACTION_REL = "cancel-action";

}
