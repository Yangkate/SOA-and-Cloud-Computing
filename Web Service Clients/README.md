Goal

To know basics of consuming a web service using JAX-WS and learn the basic use of Servlets.

Prerequisites:

Learn what a WSDL file is. There is no need to know every detail, just the big picture. In practical use you will learn the details which you need. http://en.wikipedia.org/wiki/WSDL
Learn the idea behind the wsimport tool : http://www.mkyong.com/webservices/jax-ws/jax-ws-wsimport-tool-example/
Basically: wsimport converts a WSDL file to Java classes.
Full documentation about the tool from http://jax-ws.java.net/jax-ws-ea3/docs/wsimport.html
To communicate with the WSDL services SOAP messages will be used. Most of this will be hidden to you because of the use of wsimport. However, it might be useful to have an idea what happens behind the scenes. Therefore, if you are interested, you could read http://www.webreference.com/authoring/web_service/index.html. Read the parts about Web Services, SOAP and WSDL. Continue reading about XML if you are not familiar with it.
You can also read some basic information about Java Servlets, but most things should become clear while working on the second part of the task and studying the example.

Task

This assignment contains a programming part and several reflective questions.

In the task you are required to make a simple secretary application which will communicate with web services using the WSDL/SOAP standards.

The WSDL files of the services are located at http://ub1.ad.jyu.fi/teacher?wsdl and http://ub1.ad.jyu.fi/courses?wsdl. You can open these addresses with your browser to take a look at them. Each WSDL file defines only one service. In a more realistic setting there would be multiple services per WSDL descriptor.

The first teacher service defines a service from which you can search for people in the registry. Currently the data in the database is as follows (exactly this data):

ID        	First name        	Surname    	Email    
id1	John	Doe	jd@jd.com
id2	Peter	Caine	pc@pc.com
id3	Mary	Smith	ms@ms.com
The method in the course service defines a way of creating a new course in the system. It returns the ID of the created course. Your task is to make a Java application which is able to communicate with both services.

First application

Your first application must work from the command line and use System.in for input. (This must be a maven project) First, you need to use the wsimport tools to create helper classes which will interact with the published web services. Create the helper classes in the package fi.jyu.it.ties456.week38.services.teacher and fi.jyu.it.ties456.week38.services.course (see -p option of wsimport) It is the easiest to keep the generated classes in your project. (use -keep and -Xnocompile). If you forget to generate the classes directly in the package (using -p), it is not possible to just change the package name at the top of the code file. You will have to regenerate the files.

Then you can write the application in the class fi.jyu.it.ties456.week38.Main, which needs to have a main method. In the application menu there must be three options : “quit”, “search” and “create”.

“quit” quits the application.
“search” asks for a search string, performs the search method on the teachers registry and prints the results. Then the application starts over asking the option from the user.
“create” asks all needed input parameters (course name, teacher id (which should to be from the teacher search), number of credits and a short description). Then the course registration method on the courses web service is called and the application prints the returned course ID. Then the application starts over asking the option from the user.
Do not spend to much time on things like input validation, beautiful design, etc. However, it would be good to have one class containing two methods. One for each action.

Second application

Once this task is done, you will have to make a second application. This will be is an API with the same functionality.

This web application should be created using Java Servlets. Each Servlet is responsible for handling one of the following requests:

path	method	query parameters	response	failures
/searchTeacher	GET	queryString	respnse code 200 and json containing the persons’ information	response code 404 and json containing an error message
/createCourse	POST	none, data should be sent in the POST body	code 302 or 303 (See https://en.wikipedia.org/wiki/Post/Redirect/Get) and redirect to /courseDetails?courseID=theID	response code 404 and a human readbale error message
/courseDetails	GET	courseID	always fails	response code 501 and a message telling that this is not implemented
You can use the following steps to setup this project:

Create a standard maven project (maven-archetype-quickstart)
Add dependencies as show in this example
make sure that you also add a dependency on the project you created for the first application. This way you can reuse the pieces.
Create your Servlets. A servlet is a normal Java class which extends from the javax.servlet.http.HttpServlet class.
In the class you should override the doPost or doGet method depending on the type of requests this Servlet needs to answer to.
In the method, you have to call methods on the request and response objects to get the query parameters, set status codes and write the response itself.
A simple example
To run your servlets, make a class similar to this one
The following reflective questions must be answered by each group and the answers must be placed in the readme.md file in the repository.

Most REST services do not use WSDL and SOAP. What could be reasons for this?
SOAP and WSDL use XML, would it be better if they would use something more modern like JSON?
How did you handle errors and how should it be done? Imagine that your application is running on a production server.
Returning the task

The finished project(s) must be uploaded to the git repository which will be made by the teacher. The repository should contain a short readme.md file which should be formatted using markdown. Also add the answers to the questions to the readme file.

Hints:

Use Eclipse IDE for Java EE Developers from http://www.eclipse.org/downloads/.
Make first the command line app and then the website stuff.
The services will not run anymore after 30 September 2015.
Use tools available on the Internet (e.g. WSDL testers, validators, visualizers etc.).
To get some result from the teachers search service, use search strings that are substrings of strings stored in the database (e.g. “Jo”, “Cai”, “ary”)
Strictly speaking, a Servlet is anything which implements the javax.servlet.Servlet interface. However, in practice you will almost always use HttpServlets.
If you are familiar with Servlets, you can also work with other web containers (Glassfish, Tomcat, …) and use web.xml. However, you have to make sure that all group members understand what is happening.
