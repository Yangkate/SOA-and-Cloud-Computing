package fi.jyu.it.ties456.week38.Main;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import fi.jyu.it.ties456.week38.services.course.CourseISService;
import fi.jyu.it.ties456.week38.services.course.NoSuchTeacherException_Exception;
import fi.jyu.it.ties456.week38.services.teacher.TeacherRegistry;
import fi.jyu.it.ties456.week38.services.teacher.TeacherRegistryService;

public class SearchTeacher extends HttpServlet {

	public SearchTeacher() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String queryString;
			if((queryString = req.getParameter("queryString")) != null){	//if the parameter exists
				TeacherRegistryService connect = new TeacherRegistryService();
		        TeacherRegistry teacherReg = connect.getTeacherRegistryPort();
				JSONObject result = Main.searchTeacher(teacherReg, queryString);		//getting the JSON objects
				resp.setStatus(200);
				resp.getWriter().write(result.toString());			
				}
			else
				resp.sendError(404, "Error: queryString paramter required");
		}	
}
