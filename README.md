COURSE-HATEOAS
============

<p>This is an experiment project on REST Level 3</p>

<p>In this project I have used one resource: Course. A course administrator can : </p>

<ol>
<li>Create a course</li>
<li>View a course</li>
<li>Update a course</li>
<li>Approve a course</li>
<li>Publish a course</li>
<li>Block a course</li>
<li>Cancel a course</li>
<li>Copy a course</li>
</ol>

<p>A student can:</p>

<ol>
<li>Register a course</li>
<li>Unregister a course</li>
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

<pre><code>
$ cd coursehateoas
$ mvn clean install
$ cd coursehateoas-webapp
$ mvn -Pjetty jetty:run
</code></pre>

The below URL is the entry point for this service
=================================================

<p> http://localhost:8080/coursehateoas/</p>