package net.java.dev.cejug.classifieds.login.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.cejug.classifieds.login.entity.facade.client.RegistrationConstants;
import net.java.dev.cejug.classifieds.login.entity.facade.client.URLDeobfuscator;
import net.java.dev.cejug.classifieds.login.entity.facade.client.UserFacadeLocal;

public class RegistrationConfirmation extends HttpServlet {
	/** <code>serialVersionUID = {@value}</code>. */
	private final static long serialVersionUID = -6026937020915831338L;

	@EJB
	private UserFacadeLocal local;

	@EJB
	private URLDeobfuscator deobfuscator;

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String key = request
				.getParameter(RegistrationConstants.CONFIRMATION_KEY.value());
		if (key == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"invalid key - goodbye hacker...");
		} else {
			try {
				Map<String, String> parameters = deobfuscator
						.extractParameters(key);
				PrintWriter out = response.getWriter();
				Set<Map.Entry<String, String>> keys = parameters.entrySet();

				for (Iterator<Map.Entry<String, String>> it = keys.iterator(); it
						.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					out.print("<strong>");
					out.print(entry.getKey());
					out.print("</strong> : ");
					out.print(entry.getValue());
					out.print("<br/>");
				}
				out.print("<hr/>");
				out
						.print("<p>TODO:<ol><li>To update the account to status ACTIVE</li><li>To redirect to the classifieds page or to a help page with instructions to the new customer</li><li>other ideas</li></ol></p>");
				out.print("<br/><p>@EJB UserFacadeLocal ref = " + local
						+ "</p>");

			} catch (GeneralSecurityException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e
						.getMessage());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
		super.doPost(req, resp);
	}
}
