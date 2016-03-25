package fi.jyu.it.ties456.week38.Main;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CourseDetails extends HttpServlet {

	public CourseDetails() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
				if ((req.getParameter("courseID")) != null)	//always fails
					resp.sendError(501, "Error: courseDetails service is not implemented");
				else 
					resp.sendError(501, "Error: courseID parameter required");
				}
	
		}