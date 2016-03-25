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
