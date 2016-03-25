Goal

This task has several goals. First, you will have to perform some basic system administration tasks like handling virtual machines, installing software on servers and configuring databases. Then, you will have to write a simple web application in Python which connects to this database and stores its data there. The goal is that you can transfer what you learned to do in earlier Java exercises to Python. Next, you will learn the impact of adding a caching layer to this application and measure the impact of several settings. Finally, you will have to compute the cost of running your application.

Prerequisites

Learn how to create a simple HTTP server using python with the bottle framework. (You can use other frameworks if you really want.)

Learn what a NoSQL database is. A general description can be found from Wikipedia. In this exercise we will be using the Riak NoSQL database. Read the basic information from Riakdoc - concepts.

If you have not been using Linux lately, you can have a look at this cheat sheet. Read trough the Taste of Riak: Python documentation. You will need this later when accessing Riak from your python code. More complete docmentation for the python client from http://basho.github.io/riak-python-client/client.html

For some parts of this task you will be using the server located at oksa3.it.jyu.fi. This machine is reachable over ssh from inside the university network (including KOAS and Kortepohja student village). Students outside the network can reach the machine using VPN or by first connecting to charra.it.jyu.fi using ssh and then connect to oksa3. In the Linux terminal a typical ssh command would look like: ssh username@host, for example ssh miselico@oksa3.it.jyu.fi. On oksa3, all student in this course have an account and X-forwarding is possible. Windows users can use putty or another shell to connect to the server, but will not be able to use X-forwarding and likely not get a smooth experience. Note that the home directory on oksa3 is not related to your normal university home directory. All files are local to oksa3.

Further, you will be using Amazon Web services (AWS). You should obtain your personal password from http://jyu-aws.appspot.com and try to log in to https://jyu-it.signin.aws.amazon.com/console.

Task

This assignment contains a technical part and several reflective questions (see below).

Do NOT copy-paste-execute commands without having a clue what they are doing. Some commands need to be adapted before use.

In this exercise you will set-up a NoSQL database and then write a small python server which uses the database as a back-end. As an example of a NoSQL database we will use the Riak database. For the server we will use a HTTP server implemented using the python programming language. The database will run in a virtual machine provided by Amazon and the web server at the university.

Setting up the virtual machines on oksa3

Before creating the virtual machines, each student needs to have a public/private key pair; the same type as we used for authentication to yousource with git. This key will be used to log-in to the virtual machines. You can create this key pair (on oksa3) as follows:

ssh-keygen -t rsa
This creates a private and a public key in the ~/.ssh/ directory.

Next, a script has been prepared to create the machines. This script is put in everyones home directory and you should read it to see what it does. This script has to be executed only once for each group!

Read the script using:

less createVMs
Execute the script using :

./createVMs
The script takes your group number as a parameter. Please double check before providing it. You might need to enter your password while this script is running since creating VMs requires root (sudo) access. Take note of the hostnames and IP addresses which the script prints, some of it you will need later. Again, this script can only be run once per group!!

Note that now only the student who created the VMs can access them (check that you can access them by logging in to the VM ssh root@192.168.122.*** ). This is because of the fact that only that user has the right key to log into the virtual machines. Next, you need to add the public keys ( .ssh/id_*.pub ) of the other students in your group to both virtual machines so that they also get access. Adding someone’s public key to the a linux machine is done by appending the key to the file located at ~/.ssh/authorized_keys, more concrete /root/.ssh/authorized_keys.

A final thing you should realize is that the virtual machines can communicate with each other and you can connect to the virtual machines from the host machine, i.e. oksa3. It is, however, not possible (except for people familiar with tunneling) to connect to the created virtual machines from your own work station. Instead of using your local browser for testing something, you can use either wget or curl from the command line to fetch content. Interactive browsing is possible with links or lynx. The server has also Firefox installed, which you can us if you have a fast Internet connection and used ssh X-forwarding (connected using ssh -X oksa3.it.jyu.fi).

Setting up the database

Remark: if you want to start working on the HTTP server before you finish the creation of the database, see the hints section.

The database will be located on AWS. Initially the database will consist out of a 2-node cluster of t2.micro machines.

First, you need to log-in to AWS and and select EC2 and change the availability zone (next to your user name) to US West(Oregon) (from now on (since that availability zone already has too many machines) use US West (N. California)). Click Launch instance.
For the operating system, go to Community AMIs and select the Ubuntu operating system. Now you should see and select the ubuntu/images/hvm-ssd/ubuntu-trusty-14.04-amd64-server-20150325 option.
The default instance type is t2.micro, which is what you should choose.
You can leave all the settings as default and launch the server.
You will be asked to select an existing key pair or create a new one. At this step you should create a new one with name groupXX the first time you make a server and reuse this key when making the next server(s).
By default, the server will only allow access trough port 22 (ssh). Later, when you want to connect to your database you will have to add the needed ports to the automatically created security group. Now you should be able to log in to both machines. You cannot continue before you can do this.

Log in to the machine using ssh (for example using ssh -i groupXX ubuntu@ec2-52-30-198-194.eu-west-1.compute.amazonaws.com) and follow the following steps to install the Riak database. This has to be done on both machines. (Installing should be fairly fast; do not attempt to gain time by installing once an making a copy of the VM. This will backfire.)

The easiest way to install Riak is to follow the instruction from https://packagecloud.io/basho/riak/packages/debian/wheezy/riak_2.1.1-1_amd64.deb
Now, riak is installed, but not yet configured. Following the instructions from  http://docs.basho.com/riak/latest/ops/building/basic-cluster-setup/ (section Configure the First Node and Add a Second Node to Your Cluster) you should be able to set-up the two node cluster.
Make sure your database is working by putting some data into it and fetching it back. To do this you will need to install the curl tool. You can install curl by executing apt-get install curl. Look at the Test the cluster section of Five Minute Install on how you can do a quick test to see whether it works. Also test whether you can connect in the same fashion to the database from the virtual machines created on oksa3 in the previous part.
Now, you have a NoSQL database set-up and you are ready to continue making the http server.

Making the HTTP server

Now, we will make an HTTP server which is providing a minimal phone book service. The HTTP server must be running on the second VM which was created. To login to the machine, you can use the following command: ssh root@192.168.122.2XX.

First, you should make sure that you can connect from the http server machine to the database machine by executing similar commands as the ones from step 5 in the previous section.

To make the HTTP server, which must be written in Python, you should use bottle.

At http://192.168.122.2XX/add/ you must serve an HTML form in which the user can enter a name and phone number. Submitting the form to the server is a POST action to http://192.168.122.2XX/add/handle/. The data entered in the form must be stored in the Riak database cluster, old values should be overwritten. A previously entered phone number is retrieved by visiting http://192.168.122.2XX/get/<name>. The response must be JSON, as follows:

    {"name" : "John Doe", "number" : "+358408053254"}
For now, the database must be queried for retrieval of the phone number.

Remember to change the hostname from localhost to 192.168.122.2XX or 0.0.0.0 in the code when starting the server. Otherwise, the server will not be accessible from outside the virtual machine.

In your python code, you will use a library provided by the riak developers to connect to the database. You will need to install a few dependencies before installing the Riak Python libraries. All of then can be installed with apt-get (See Tips section).

python-pip
protobuf-compiler
python-protobuf
build-essential
python-dev
libffi-dev
libssl-dev
Check from this page how to install the Riak libraries and connect to your Riak server from Python code. You just installed pip, you can now use it for installation of the Python packages. While reading the tutorial, keep in mind that your database is not on localhost.

Trying to optimize things - caching

Several things can be done in order to improve the way your application works. Some of the techniques in this section do not really make a difference for this exercise. Again, the goal is to get familiar with things in order to use them when needed. Collect the results of these experiments.

Adding data

Add 10000 randomly created users and numbers to your database. Do this in such a way that this is repeatable (if you use a random number generator, seed it). It might be faster to execute this script on one of the database machines.

Measuring impact

To test the impact of changes, you should measure the throughput of your application using JMeter on the server. First, download the latest stable version of the software to your account on the server, for example using wget http://the.url Then extract the archive using tar --extract --verbose --file archive.tar.gz or unzip archive.zip.

Now, in order to execute JMeter, you have to realize that on the server you cannot use the graphical user interface. Instead, you will make the test plan (.jmx file) on your own machine, using the GUI and execute it on the server (on the host machine, not inside a virtual machine). Check from the documentation how to execute the plan. (Last year some students have succesfully used these instructions http://blog.ionelmc.ro/2012/02/16/how-to-run-jmeter-over-ssh-tunnel/ )

Slow down the conncetion

To make the testing more realistic, you should simulate a slow network link using netem on the virtual machine.

Create a fixed set of 500 requests

Instead of repeating the same request over and over again, you should make a fixed list of about 100 add and 400 get requests in random order. This list should be generated such that some contacts are more popular than others (probability distribution, anyone?). Put this a CSV file which you use as an input to JMeter. In this way JMeter will not spend its time computing random calls and your test becomes repeatable.

Baseline

Perform the 500 requests with your current implementation. In further steps you will be able to compare and decide whether the change had any effect.

Using protocol buffers instead of HTTP

As you might have read, using HTTP for the client is 2-3 times slower than the protocol buffer API. Currently, your client most likely uses HTTP. Changing it to use protocol buffers is straightforward (because we pre-installed the needed dependencies) and only requires you to change the code for creating the client from riak.RiakClient(host="...", port=8098)to something like riak.RiakClient(host="...", port=8087, protocol='pbc'); or similar. Measure the speed difference between HTTP and protocol buffers.

Caching contacts using memcached

Some contacts will be more popular than others. Imagine that in the scope of this exercise, it makes sense to cache frequently used contacts for later use. memcached is one caching solution which is also used in production environments.

Install memcached on the second virtual machine on oksa3 and make use of the caching facilities from your Python web application. (You can install and use the packaged memcached version by running sudo apt-get install memcached). The memcache deamon runs automatically as a separate process after installation. However, it will be bound to 127.0.0.1 by default and hence it will not be accessible from your second virtual machine. To change this to 0.0.0.0 you need to edit the configuration file at /etc/memcached.conf and restart the memchache deamon service memcached restart.

Then you use a client library to connect to it, note that some memcached clients, like e.g. pylibmc, will require you to install libmemcached-dev. The first option from the list (pylibmc) can be installed using pip install pylibmc. If you are using another client, make sure it supports time outs.

Now, you should use this cache to store frequently used contacts. However, you should use a time-out of 10 seconds (i.e., after 10 seconds the contact should disappear from memcache).

Measure how using the cache affects the performance. From now on you have to empty the memcache before each test.

Using a faster server framework

Currently, you are most likely using a debug / development setting of the python web framework (i.e. bottle). Use a production ready server, like cherrypy, instead. Also, turn of debug mode. Measure.

When using a multi-threaded server combined with memcache, you will have to make a small change to the way you use pylibmc. The pylibmc is not threadsafe and hence weird errors might start happening when calling it from multiple threads. To solve this, you can use a (fifo-like) connection pool as described on http://sendapatch.se/projects/pylibmc/pooling.html#fifo-like-pooling.

Load balancing your database

You have set up two nodes for the database. The Riak library supports using one or more connections. Measure whether it makes any difference whether you use one or both database servers.

Reflective questions

Provide an answer to the following questions in a readme file in your repo

How would you implement a page which shows a list of contacts?
Why would you ever use a set-up with virtual machines in a real (production) environment? Or would you not?
Which of the optimizations made sense, which ones not?
Compute the daily cost of your amazon usage including
Cost of running the VMs
Cost of the network traffic
Cost of the storage
What should be improved in this exercise if it is given to students in the future?



README
=======


### How would you implement a page which shows a list of contacts?

* Create an own database for it.

### Why would you ever use a set-up with virtual machines in a real (production) environment? Or would you not?

* We would not. VM's use a lot of space (for example, on oksa3, there could be only 2000 VM's), which is why in real environments they use super computers that go straight into the hardware.
  VM's are fast, though.

### Which of the optimizations made sense, which ones not?    

* With the cache:     summary = 500 in 662s = 0.8/s   Avg: 1317   Min: 791    Max: 1734   Err: 4 (0.80%)
* Without the cache:  summary = 500 in 668s = 0.7/s   Avg: 1329   Min: 1104   Max: 1501   Err: 1 (0.20%)

* Here we can see the one with the cache was a bit more efficient.

### Compute the daily cost of your amazon usage including      
                       
* Cost of running the VMs:     0.732 $


* We could not log into the AWS and do the chmod (public key) thing on neither our own comps nor the demo room's...
