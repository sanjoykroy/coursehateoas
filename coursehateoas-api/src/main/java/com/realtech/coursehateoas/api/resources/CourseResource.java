package com.realtech.coursehateoas.api.resources;


import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

public class CourseResource extends ResourceSupport {

    private Long courseId;
    private String title;
    private String description;
    private String instructor;
    private Date startDate;
    private String workload;
    private Date createdDate;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseResource)) return false;
        if (!super.equals(o)) return false;

        CourseResource that = (CourseResource) o;

        if (courseId != null ? !courseId.equals(that.courseId) : that.courseId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CourseResource{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", startDate=" + startDate +
                ", workload='" + workload + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
