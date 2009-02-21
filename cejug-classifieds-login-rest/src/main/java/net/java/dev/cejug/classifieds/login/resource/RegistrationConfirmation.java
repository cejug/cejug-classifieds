package net.java.dev.cejug.classifieds.login.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.cejug.classifieds.login.entity.facade.client.UserFacadeLocal;

public class RegistrationConfirmation extends HttpServlet {
	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	@EJB
	private UserFacadeLocal local;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Little trick: http://www.petefreitag.com/item/381.cfm
		String agent = request.getHeader("User-Agent");
		if (agent != null && agent.toUpperCase(Locale.US).contains("MOZILLA")) {
			response.setContentType("text/xml");
		} else {
			response.setContentType("application/atom+xml");
		}

		PrintWriter out = response.getWriter();
		out.print("@EJB UserFacadeLocal ref = " + local);
	}

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		super.doPost(req, resp);
	}
}
