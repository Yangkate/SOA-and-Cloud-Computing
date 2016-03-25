Goal

The goal of this exercise is to get acquainted with how application deployment can be done on cloud infrastructure. In this exercise we will use tools made available by the platform providers. Most providers create tools for deployment to their systems. Further we will try to measure the performance of the deployed application.

Prerequisites

The first thing you need in order to complete this exercise is some nerves and the will to try out things (This is the real world). In the exercise, we will be deploying last weeks servers to Heroku. For doing so, you will have to install maven and the heroku toolbelt. After this is done, you can try the Heroku Java tutorial.

For measuring performance we will be using JMeter, most information needed for this exercise can be found from Building a Web Test Plan.

Task

The task is to deploy the localGDP server which was created in last week’s exercise to cloud service providers. The platform we use in this exercise is provided by Heroku.

Clean up / prepare last weeks code

Complete the code you created last week and refactor so that methods and packages have a normal name. You must also split the code in two separate projects: one for the WSDL/SOAP GDP service and one for the LocalGDP service. In the WSDL/SOAP application you need to add the dependency to GDP.jar as a local maven repository, which is placed inside the git repository. Instruction for doing this can be found from Adding Unmanaged Dependencies to a Maven Project. The LocalGDP project should not have a dependency on GDP.jar.

Deploying the SOAP/WSDL GDP Service to Heroku

Start by deploying the SOAP/WSDL application on Heroku. On Heroku, you can run any Java application which connects in time (within 60 seconds) to the port which Heroku specifies. In particular, you can keep the code for the WSDL/SOAP service form last week more or less as it was in most groups. However, a few small code changes should be made:

You cannot hardcode the port you run the server on. Instead you need to read the PORT environment variable. In Java, this is can be done as follows:

 Integer port = Integer.valueOf(System.getenv("PORT"));
Starting the server on http://localhost... will not work. You have to use http://0.0.0.0... instead *.

Besides these small changes, you have to add a file called Procfile and a file called system.properties as described in the Heroku Java tutorial.

Deploying the LocalGDP service to Heroku

Deploying this application can be done in essentially the same way as the other service.

Note: also here you will have to replace localhost with 0.0.0.0 and get the port number as an environment variable. Further you have to regenerate the service classes using wsimport, such that the other deployed service is used.

Finally, you cannot use System.in.read() to wait for a keypress to stop the server (heroku closes the standard input, so you will read -1 when reading from stdin). Instead, you do not have to stop the server manually and can just wait forever using Thread.currentThread().wait();.

If you want you can also use the tools which Heroku provides to deploy war packages directly.

Update your html page on users.jyu.fi

Update your html page on users.jyu.fi to use the service deployed in Heroku.
JMeter

With JMeter you can emulate many requests to your service and hence create a high load on your server. Try to come up with a reasonable way to measure the performance. Use this technique to measure the performance of your server running locally vs. the one on Heroku. Also look at how different settings for your cache affect the performance. Note that the GDP.jar implementation has a one second delay when answering to calls. Document how you measured the performance, add the measurements and describe how the results compare.

It is recommended to perform these measurements in the Linux class.
