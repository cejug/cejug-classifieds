package net.java.dev.cejug.classifieds.server.rest;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminLocal;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;

/**
 * A Servlet used to check if the classifieds were installed successfully.
 */
public class Welcome extends HttpServlet {

	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	/**
	 * The reference to the local interface of the Admin Session Bean.
	 */
	@EJB
	private transient ClassifiedsAdminLocal admin;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		MonitorQuery query = new MonitorQuery();
		query.setResponseTimeLength(20);
		query.setAlivePeriodsLength(20);
		MonitorResponse monResponse = admin.checkMonitorOperation(query);
		request.setAttribute("monitorResponse", monResponse);
		request.getRequestDispatcher("/welcome.jsp").forward(request, response);
	}
}
