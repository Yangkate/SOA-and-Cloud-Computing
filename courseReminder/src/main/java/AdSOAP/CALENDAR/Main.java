package AdSOAP.CALENDAR;

import java.util.EnumSet;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {

	public static void main(String[] args) throws Exception {

		Logger.getGlobal().addHandler(new Handler() {

			@Override
			public void close() throws SecurityException {

			}

			@Override
			public void flush() {

			}

			@Override
			public void publish(LogRecord arg0) {
				// send SMS
				System.out.println(arg0.getMessage());
			}

		});

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(login.class, "/login");
		handler.addServletWithMapping(register.class, "/register");
		handler.addServletWithMapping(logout.class, "/LogOutServlet");

		handler.addServletWithMapping(userLocationReader.class, "/latlng");
		handler.addFilterWithMapping(LoginFilter.class, "/latlng",
				EnumSet.<DispatcherType> of(DispatcherType.REQUEST));

		
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("src/main/resources");
		resourceHandler.setDirectoriesListed(true);
		
		HashSessionManager sessionManager = new HashSessionManager();
		SessionHandler sessionsHandler = new SessionHandler(sessionManager);

		HandlerList hl = new HandlerList();

		hl.addHandler(resourceHandler);
		hl.addHandler(sessionsHandler);
		hl.addHandler(handler);

		Integer port = 8080;
		if (System.getenv("PORT") != null) {
			port = Integer.valueOf(System.getenv("PORT"));
		}
		Server server = new Server(port);
		server.setHandler(hl);

		server.start();
		// this dumps a lot of debug output to the console.
		server.dumpStdErr();
		server.join();
	}

}
