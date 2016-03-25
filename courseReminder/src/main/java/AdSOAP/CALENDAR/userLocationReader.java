package AdSOAP.CALENDAR;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.fortuna.ical4j.data.ParserException;

import org.xml.sax.SAXException;

import fi.jyu.ties532.advanced.locator.LocationNotFoundException;

@SuppressWarnings("serial")
public class userLocationReader extends HttpServlet{
	
	public userLocationReader() {
		super();
		}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws IOException{
	       
		content i = null;
	     String url = null;
		
		//get the paremeters from req
	      double userLat = Double.parseDouble(req.getParameter("lat"));
	      double userLng = Double.parseDouble(req.getParameter("lng"));
	      
	      
		  //get user name from session
	      HttpSession session=req.getSession();
	      String username= (String)session.getAttribute("username");
	      
		  //connect postgresql from Heroku
	      Connection connection = null;
			try {
				connection = herokuDB.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Statement stmt = null;
			try {
				stmt = connection.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

				ResultSet rs = null;
		     try {
				rs = stmt.executeQuery("SELECT url,zone FROM login WHERE name='"+username+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     try {
				while (rs.next()) {
				url= rs.getString("url");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		try {
			i = eventRemind.getReminder(userLat, userLng,url);// get info 
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (LocationNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
			
			resp.setCharacterEncoding("UTF-8"); 
			
		    
		   	
   	   resp.getWriter().println( "You have " + i.left + " mins Left\n"
						+ "From: " + i.begAdress + "\nTo: " + i.location);
   	         
   }

}
