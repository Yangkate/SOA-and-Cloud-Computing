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

public class CreateCourse extends HttpServlet {

	public CreateCourse() {
		super();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String path = req.getRequestURI();
		String cName, creditString, description, teacherID;
		String id;
		int credit = 0;
		System.out.println(req.getParameterMap());
		if (((teacherID = req.getParameter("teacherID")) != null) && ((cName = req.getParameter("courseName")) != null)
				&& ((creditString = req.getParameter("courseCredit")) != null)
				&& ((description = req.getParameter("courseDescription")) != null)) {
			try {
				credit = Integer.valueOf(creditString);
			} catch (NumberFormatException e) {
				resp.sendError(404, "Error: credit parameter of the course must be a number");
				return;
			}
			try {
				id = new CourseISService().getCourseISPort().createCourse(cName, teacherID, credit, description); // creating
																													// the
																													// course
				resp.sendRedirect("/courseDetails?courseID=" + id); // the
																	// sendRedirect
																	// method
																	// already
																	// sets the
																	// status
																	// code to
																	// 302
																	// ("Found")
			} catch (NoSuchTeacherException_Exception e) {
				resp.sendError(404, "Error: no such teacher");
			}
		} else
			resp.sendError(404, "Error: wrong parameters");
	}
}
