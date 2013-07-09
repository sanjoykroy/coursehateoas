COURSE-HATEOAS
============

<p>This is an experiment project. In this project I have used Spring HATEOAS.</p>

<p>I have used one resource: Course. The below operations will be performed on this resource:</p>

<ol>
<li>createCourse - POST operation to create a course</li>
<li>showCourses - GET operation to retrieve all courses</li>
<li>showSingleCourse - GET operation to retrieve a single course</li>
<li>getCourse - GET operation to retrieve a course</li>
<li>updateCourse - PUT operation to update a course</li>
<li>cancelCourse - DELETE operation to cancel a course</li>
</ol>

<p>This project uses:</p>

<ol>
<li>Spring Data</li>
<li>Spring Hateoas</li>
<li>Spring MVC</li>
<li>H2</li>
<li>Liquibase</li>
<li>TestNG</li>
<li>Mocktio</li>
<li>Spring Test MVC</li>
<li>Hamcrest</li>
</ol>

To run the application
======================

<p>From the command line with Maven:</p>

<pre><code>$ cd coursehateoas-webapp
$ mvn clean install -Pjetty jetty:run
</code></pre>

To see the application in action
================================

<p> http://localhost:8080/coursehateoas/courses </p>