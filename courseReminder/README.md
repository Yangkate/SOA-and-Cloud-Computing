Goal

The goal of this task is to get to use different APIs of service providers and create an integrated service. The integrated service will run in the premises of a Cloud service provider. You will also have to adapt yourself to the storage and caching options provided.

Prerequisites

You must have understood the tasks related to web service clients and web service servers. Also the skills learned from deploying applications will come in handy. The material which was prerequisite for those exercises is also needed for this exercise. Since you are free to choose the environment and tooling used in this exercise, you will need to search documentation as needed. Check whether the tools and platform you are selecting have associated costs. You can use the budget provided by amazon for this exercise.

When you are working in a pair, you need to use protocol buffers and snappy for communication with one of the services. In that case, read about protocol buffers from https://developers.google.com/protocol-buffers/. There are implementations for many languages available (See ThirdPartyAddOns). Also read about the basic idea of Snappy (former Zippy) from http://code.google.com/p/snappy/. If you are planning to use Java for this task you could use the Java implementation from https://github.com/xerial/snappy-java. Remark : this library does not seem to work on GAE since it uses native code. https://github.com/dain/snappy works on GAE, but does not have streaming capabilities.

If you are really interested in the multiple coordinate systems in use in Finland, you could check http://www.kolumbus.fi/eino.uikkanen/geodocsgb/ficoords.htm.

Task

You have to create a website which helps people to be in time. Concrete, you create a page which displays how much time the user has left before he has to leave to make it to the next event.

A user must be able to log-in to your service. Then he can provide his timezone information and a URL for his calendar information. The provided information must be stored persistently on your server (in some sort of database).

Then, the user can navigate to a page which shows how much time he has left before he should leave to make it to the next event in his calendar. The page also shows information about the first stage of the journey. This beginning of the journey is based on the location of the user as provided by the browser and the current time. The end of the journey is based on the location information attached to the next event and its start time. The user is supposed to use public transportation means, walking and cycling only.

You have the following resources available:

A service providing routing information in Finland
matka.fi provides a RESTful API http://developer.matka.fi/pages/en/home.php
More specifically coordinate based routing
Careful : the coordinate system used is KKJ3 which needs to converted for compatibility with other systems.
KKJ3 is KKJ with zone equal to three. Even if the location in question is not in Zone 3.
One service providing an iCal calendar
This is provided by the users by URL
Be careful using user provided URLs, they tend to try to crash your service.
One example you can use is your own korppi calendar (see iCal on korppi).
A GeoConversion library written in Java to convert between KKJ3 and WSG-84 coordinate systems.
provided by the teacher
There is also a Python version available (found from  https://github.com/HSLdevcom/kalkati2gtfs/blob/master/coordinates.py)
When using other languages, you have to create a small service to do the conversion.
A tool for converting University classrooms to locations
If you work alone: a library. This library also contains example code.
If you work in a pair : as a service (see below).
The browsers GeoLocation javascript API
You can assume that only modern browsers having this feature are used.
Use watchposition so that your site gets updated about changes in the location.
A time source
You should make a decision about this one yourself
Can you trust the browser’s time?
Can you trust the server’s time?
In what time-zone is the server? The user?
User information
an iCal calendar URL
Timezone
You are free to use a platform and tooling of your choice. You have to deploy the service so that it is accessible trough the Internet.

Optional: If the application does not know the coordinates for the location, it can ask the user to specify them (for instance using a map) or you could query a geocoding service provider.

Additions if the work is performed in a group of two persons

You need to add support for having multiple iCal calendars.
The user can specify what his walking/cycling speed is. This value must be taken into account when doing the routing requests.
slow (30m/min)
normal (70m/min)
fast (100m/min)
running (200m/min)
cycling (300m/min)
You need to implement caching of the calendars using memcached or groupcache
Only cache the necessary parts and do not use too old data (you might need to use time stamps).
You can use provided services to make your life easier (GAE, Heroku, and other providers have free memcache options).
You cannot use the library to convert between university classrooms and locations. Instead you need to use the service which is available from http://jyulocation.appspot.com/locate/{location} This is a restful service which does not use xml or json. Opening a URI like http://jyulocation.appspot.com/locate/Ag%20Alfa will result in a binary result. Instead of JSON or XML, the server is using protocol buffers compressed using snappy which are served over http. The protocol buffer schema can be found from location.prot. You need to compile this schema using the protoc tool to communicate with the service. Example code on how to convert data received from this service can be found from Client.java. Also use a caching strategy for this service. The strategy will be different compared to the calendar caching since the information served is static (does not expire).
Instead of only creating a web page which displays the information, you also have to make a browser extension which shows the time of the next departure.
Optional: Allow the user to log in using OpenID. Do not implement this yourself, use existing frameworks and services.

