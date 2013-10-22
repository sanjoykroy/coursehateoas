package com.realtech.coursehateoas.api.resources;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;


public class CourseForm extends ResourceSupport {

    private String title;
    private String description;
    private String instructor;
    private Date startDate;
    private String workload;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    @Override
    public String toString() {
        return "CourseForm{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", startDate=" + startDate +
                ", workload='" + workload + '\'' +
                '}';
    }
}
