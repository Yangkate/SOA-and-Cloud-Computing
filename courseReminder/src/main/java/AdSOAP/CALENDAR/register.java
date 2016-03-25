package AdSOAP.CALENDAR;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class register extends HttpServlet {
	public register() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String name = req.getParameter("name");
		String originalPassword = req.getParameter("password");
		String url = req.getParameter("url");
		String zone = req.getParameter("zone");
		Connection connection = null;
		Logger.getGlobal().finest("registering" + name);
		try {
			connection = herokuDB.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Error(e);
		}

		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword,
				BCrypt.gensalt(17));
		String insertTableSQL = "INSERT INTO login"
				+ "(name,password,url,zone) VALUES" + "(?,?,?,?)";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(insertTableSQL);
			stmt.setString(1, name);
			stmt.setString(2, generatedSecuredPasswordHash);
			stmt.setString(3, url);
			stmt.setString(4, zone);

			stmt.execute();
			connection.close();
			stmt.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			try {
				stmt.execute();
			} catch (SQLException e) {
				Logger.getGlobal().severe("A problem occured" + e.toString());
				resp.sendRedirect("error.html");
			}
		}

		resp.sendRedirect("login.html");

	}

}
