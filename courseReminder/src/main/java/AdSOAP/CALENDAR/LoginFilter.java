package AdSOAP.CALENDAR;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginFilter implements  Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r = (HttpServletRequest)req;
		HttpSession session = r.getSession();
		if (session.isNew() || session.getAttribute(login.USERNAME) == null){
			//forward to login
	    	//((HttpServletResponse)resp).sendRedirect("login.html");
			((HttpServletResponse)resp).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not authenticated");
			
		} else {
			//Object username = session.getAttribute(login.USERNAME);
			//forward the chain
			chain.doFilter(req, resp);
		}
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
