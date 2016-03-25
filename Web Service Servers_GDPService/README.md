Goal

Learn to create SOAP and RESTful web services. In this exercise we will be using libraries available for creation of this type of services. The goal is however that the student also understands what happens behind the scenes. Further, this task includes service composition, where two services are combined into one - transparently for the user of the service. Finally, you need to implement caching and optionally take measures which prevent rate limiting.

Prerequisites

The group should have made the assignment “Web service clients” and “RESTful web services exercise”. All prerequisite material for these exercises is prerequisite for this one as well. Further, the student should get acquainted with the Jersey libraries. You can read some practical info about RESTful servers from http://jersey.java.net/nonav/documentation/latest/getting-started.html. Please try out the examples from that webpage. Also check the section on JAX-RS Application, Resources and Sub-Resources, to figure out how to extract parts of a URL. Information on how to use the Jersey REST client libraries can be found from https://jersey.java.net/documentation/latest/user-guide.html#client (note that you do not necessarily need to use these).

Basic information about implementing SOAP services in Java using JAX-WS can be found from http://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/. (the parts about ruby web services are not relevant for this exercise.)

For the caching part of the exercise, read about google Guava’s CacheBuilder which provides basic in memory (and in process) caching. More information can be found from Guava’s wiki page on caches.

To solve the issues with browsers’ same origin policy, you can read up on CORS or JSONP. JSONP will be mostly usable for groups with an even group number.

For the optional part of this task, if you have no experience with Java thread pools, read http://docs.oracle.com/javase/tutorial/essential/concurrency/pools.html for more information.

Task

Two services need to be consumed and combined transparently for the user of the newly created service. The final set-up looks like this:

an overview of the final set-up

Part 1

The first service you have to create, called GDP Service, is a service which converts a country code to a Gross Domestic Product. You have to create this SOAP service with the help of this library and JAX-WS. The jar file contains a class which tells you the GDP of a country when given a country code. Since this jar file is not available from a maven repository, you will need to install it manually to your local repository following the instruction from here. In the computer labs, maven is installed under /usr/local/apache-maven-3.3.3/. If on the machine you are working no local mvn command is available, you can do the same from inside eclipse by right click on a maven project > Run as > Maven build … and fill the parameters to the launch as shown in the screenshot. An alternative, and perhaps nicer way to solve this problem is demonstrated by Kamil in his repository. This self made service needs to be accessible using WSDL/SOAP.

Part 2

The second service, which will be called localGDP service will receive as a parameter a longitude and latitude. Then it will first make a call to the google geocoding API (more info from here ) to convert the location to a country code. Next, a call to the service you created in Part 1 will give the GDP for the country code, which is also the final outcome of the second service. In summary, the created service returns a GDP for the country at the location specified.

Calling these web services can be done in a similar fashion as in previous exercises. For the geocoding API you can alternatively use the Jersey client libraries. Even groups use XML to communicate with the geocoding API, add groups use JSON.

For the REST service implementation, you must use the Jersey libraries and even groups use JSON to communicate and odd groups use XML. You should implement the REST services using streams (it is certainly disputable that this is the best solution - point is to learn the technique). To use streams, you can return an object of type StreamingOutput from your resource class’ method like this (other methods achieving the same are allowed as well):

    @GET
    public StreamingOutput getIt() {
        StreamingOutput op = new StreamingOutput() {
            public void write(OutputStream out) throws IOException, WebApplicationException {
                //write to the stream
                //*********Flush the outpustream or wrappers you put around it*************
            }
        };
        return op;
    }
You need to use XML or JSON libraries for writing to the stream. Do not just write Strings.

Part 3

Caching is essential in many applications. In this case we could cache the GDP of countries. The GDP library, and hence the first service you created, updates the values of the GDP for countries every 5 minutes. An easy way to implement caching in this situation would be to query the service every five minutes for all known countries (or all countries ever asked for) and then cache these values. To keep the exercise interesting, you should imagine that there would be too many countries to keep all this data stored in memory. To implement the caching, you have to use a Cache created by com.google.common.cache.CacheBuilder from the Google guava library. For the sake of this exercise, you should limit your cache size to 10 and expire entries in the cache after 2.5 minutes. When creating an actual application, its performance is affected greatly by a good choice of cache parameters. However, getting to know these parameters usually requires a lot of monitoring and profiling.

Part 4

Test whether your interfaces work by using ‘manual ‘testing’, i.e. write a main class which connects to the service you have created.

Then make a web interface for your application. This web interface should consist of a single .html file This web interface should show a map (free choice, could be for instance Google maps or you can get inspiration from here). When the user clicks on the map, you should perform a request to the localGDP service and display the result.

You have to add this file to your repository, but also host this file on the university webspace of one of the group members. Add the address where you hosted the file to the readme file. Note that hosting this file on the university webspace will cause same-origin policy issues in your browser, which you should deal with.

Answer the following reflective questions in your readme file.

How did you design your application?

How can you lower the memory consumption and CPU use of the LocalGDP service? Any ideas on how to test this?

What does the HTTP request which is send to your localGDP service look like, when the client wants to know the GDP for latitude 62.23186 and longitude 25.73656 ? What does the HTTP response look like?

How did you solve the same-origin policy problems?

If you implemented some mechanism to prevent hitting the rate limit, how did you do it?

What should be improved in this exercise if it is given to students in the future?

Optional

An optional challenge is the implementation of basic rate limiting while making calls to the geocoding API. As per the Limits section of the API documentation there are limits on the amount of requests you can perform on the API.

It seems like an easy solution would be to wait some time (say, a second or so) and then try to call the service again. The problem is, however, that also other calls which happen in other threads will hit the limit at the same time. This would result in all threads waiting a certain amount of time, and then all try again which might hit the limit again. People familiar with networking would think in the direction of exponential back-of approaches. However, since all the work is done inside the same VM, there is a more efficient way of solving this problem. You can for instance create a singleThreadExecutor (a thread pool with only one thread) which has the responsibility to execute all calls to the geocoding API. You then submit() Callables to the pool and get the result from the returned Future. Now, when you hit the rate limit, you can wait a fixed amount of time and try again. You can be sure that no other threads are doing the same thing at the same time.

Returning the task

Put the parts you created yourself to your git repository. Add a readme.md file describing your project structure and the choices you made for the REST API (see hint 4 below).

Hints

Most hints from the previous exercises are still valid.

If you want to do JSON object marshalling, you can generate code stubs using http://jsongen.byingtondesign.com/. The generation is not perfect and most likely needs a few manual changes.

The wsimport tool introduced in an earlier exercise can be used to convert the WSDL file from the GDP service into classes.

The parts which should be included in the answer from the service is something which you should decide in your group. Also the way the information is structured is your choice. You can get inspiration from the mediafire API and the google geocoding API. Also look at the information from https://www.ibm.com/developerworks/webservices/library/ws-restful/. Document the choices in the readme file.

Use HTTP features correctly. Return correct response codes and set the MIME type correctly. (You can use @Produces, but this forces the client to set the encoding correctly as well!)

For information about transforming exceptions in responses, see https://jersey.java.net/documentation/latest/representations.html#d0e3586

<h1>README</h1>

<p>Group8 GDP Service</p>

<h2>1) How did you design your application?</h2>

<p>At first we created the SOAP service thats able to get a GDP from a given countrycode. When starting the publisher we can call the service and method getGDP with a given countrycode.
Furthermore we created a REST service that can call some methods to receive a countrycode from a given latitude and longitude by calling the google API. Afterwards it calls the first service in order to get a GDP.
With a class called location we can make sure that the double are within the range of longitude and latitude.</p>

<p>The html-index file shows a standart google map that is movable and clickable. If an action is performed it tries to call our service to get a countrycode and the associated GDP. 
The project consists of two packages. One for the first (SOAP) service and the other one for the REST service.</p>

<h2>2) How can you lower the memory consumption and CPU use of the LocalGDP service? Any ideas on how to test this?</h2>

<p>Because we use a cache, it could be an idea to try different cacheparameters in order to lower the memory consumption. Furthermore, it is useful, not to save more data then necessary and work with streams instead of strings. That causes that if there is a huge amount of data not everything has to be saved in memory.
Source: lecture ;)</p>

<h2>3) What does the HTTP request which is send to your localGDP service look like, when the client wants to know the GDP for latitude 62.23186 and longitude 25.73656 ? What does the HTTP response look like?</h2>

<p>GET /G8-localGDP/gdp/location?lat=62.23186&amp;lng=25.73656 HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,<em>/</em>;q=0.8
Accept-Language: de,en-US;q=0.7,en;q=0.3
Accept-Encoding: gzip, deflate
Connection: keep-alive</p>

<p>HTTP/1.1 200 OK
Date: Mon, 28 Sep 2015 15:55:17 GMT
Content-Type: application/json
Transfer-Encoding: chunked
Server: Jetty(9.2.9.v20150224)</p>

<p>{"country":"FI","GDP":226.96032327351716}</p>

<h2>4) How did you solve the same-origin policy problems?</h2>

<p>Didn't solved it yet. Upload to webspace made some problems and we have no time left. We will upload it tomorrow to Maximlian's webspace and care about the policy problems then.</p>

<h2>5) What should be improved in this exercise if it is given to students in the future?</h2>

<p>I would be a great help if there would be more hints like what to do and how we can find more (really needed) information. The amount of information, new stuff and problems with dependencies etc. make it hard to concentrate on the topic and the background of the exercise. Often there is not enough time to read through all the information completely so it would be more helpful to say for example that there are different ways to implement the soap service and two are shown >here&lt;. I know its a very practical course but up to a point we do stuff thats very hard to understand and to implement in a few days.</p>
