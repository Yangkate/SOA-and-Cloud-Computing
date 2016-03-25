package AdSOAP.CALENDAR;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCrypt;



public class login extends HttpServlet{
	static final String USERNAME = "username";

	public login() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	String name = req.getParameter("name");
        String password = req.getParameter("password");
    	Connection connection = null;
		try {
			connection = herokuDB.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Statement stmt = null;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT name,password FROM login");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Map<String, String> data = new HashMap<String, String>();
    	try {
			while (rs.next()) {
				String username = rs.getString("name");
				String pass = rs.getString("password");
				data.put(username, pass);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Error(e);
		}
    	if (BCrypt.checkpw(password, data.get(name))) {
			//session set username
			try {
			 HttpSession session = req.getSession();
                if (session != null) {
					
					session.setAttribute(USERNAME, name);
					session.setMaxInactiveInterval(60);
				}
			} catch (Throwable t) {
				t.printStackTrace();
				throw new Error(t);
			}
			 resp.sendRedirect("location.html");// right to location,html

		}

		else {
			resp.sendRedirect("login.html");//wrong to login.html

		}
    	
    	
    
    }
}
