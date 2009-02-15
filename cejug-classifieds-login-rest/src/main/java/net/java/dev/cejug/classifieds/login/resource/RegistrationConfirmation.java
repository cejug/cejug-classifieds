package net.java.dev.cejug.classifieds.login.resource;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.QueryParam;

import net.java.dev.cejug.classifieds.login.entity.facade.UserFacadeLocal;

@Path("confirm")
public class RegistrationConfirmation {
	// @EJB
	// private transient UserFacadeLocal userFacade;

	@GET
	@ProduceMime("text/html")
	public String getGreeting() {
		return "<p>Welcome to Cejug-Classifieds, you will rceive more information by email...</p><p style=\"color:red;\">NOT YET IMPLEMENTED .. coming soon......</p>";
	}

	@GET
	@ProduceMime("text/html")
	public String newCustomer(@QueryParam("login") String login,
			@QueryParam("key") String key) {
		return "<ul><li>login: <strong>" + login
				+ "</strong></li><li>key: <strong>" + key + "</strong></ul>";
	}
}
