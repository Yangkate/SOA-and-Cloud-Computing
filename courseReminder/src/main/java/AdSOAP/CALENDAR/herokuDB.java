package AdSOAP.CALENDAR;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class herokuDB {

   	static Connection getConnection() throws SQLException  {
		 String username ="xqurpnkmwpomyk";
		    String password ="KJwvwe-aDs8874v2juhIQvWUQV";
		    String dbUrl = "jdbc:postgresql://ec2-54-227-248-123.compute-1.amazonaws.com:5432/dcgrflqt8ok4el?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		    return DriverManager.getConnection(dbUrl, username, password);
		    }
}


