package fi.jyu.it.ties456.week38.Main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class Run {
	public static void main(String[] args) throws Exception {
		
		ServletHandler handler = new ServletHandler();
		//add all servlets you want to use to the handler, the second argument is the path
        handler.addServletWithMapping(SearchTeacher.class, "/searchTeacher");
        handler.addServletWithMapping(CourseDetails.class, "/courseDetails");
        handler.addServletWithMapping(CreateCourse.class, "/createCourse");
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/resources");
        resourceHandler.setDirectoriesListed(true);
        HandlerList l = new HandlerList();
        l.addHandler(resourceHandler);        
        l.addHandler(handler);
       
        //Create a new Server, add the handler to it and start
        Server server = new Server(8080);		
		server.setHandler(l);
		server.start();
		//this dumps a lot of debug output to the console. 
		server.dumpStdErr();
		server.join();
	}
}