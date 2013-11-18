package com.realtech.coursehateoas.course.domain.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "instructor", nullable = false)
    private String instructor;
    @Column(name = "totalplace", nullable = false)
    private int totalPlace;
    @Column(name = "createdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "updatedate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    @Column(name = "startdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "workload", nullable = false)
    private String workload;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Column(name = "course_status")
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getTotalPlace() {
        return totalPlace;
    }

    public void setTotalPlace(int totalPlace) {
        this.totalPlace = totalPlace;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public boolean isApprovable(){
        return (courseStatus.compareTo(CourseStatus.NEW)==0);
    }

    public boolean isPublishable(){
        return (courseStatus.compareTo(CourseStatus.APPROVED)==0);
    }

    public boolean isBlockable(){
        return (courseStatus.compareTo(CourseStatus.PUBLISHED)==0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (!id.equals(course.id)) return false;
        if (!title.equals(course.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", instructor='" + instructor + '\'' +
                ", totalPlace=" + totalPlace +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", startDate=" + startDate +
                ", workload='" + workload + '\'' +
                ", enabled=" + enabled +
                ", courseStatus=" + courseStatus +
                '}';
    }
}
