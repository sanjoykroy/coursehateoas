package com.realtech.coursehateoas.course.annotation;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Controller
@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
public @interface BoundaryController {
}
