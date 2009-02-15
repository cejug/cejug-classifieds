package net.java.dev.cejug.classifieds.login.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProduceMime;

@Path("confirm")
public class RegistrationConfirmation {
	// and implement the following GET method

	@GET
	@ProduceMime("text/html")
	public String getGreeting() {
		return "<p>Welcome to Cejug-Classifieds, you will rceive more information by email...</p><p style=\"color:red;\">NOT YET IMPLEMENTED .. coming soon......</p>";
	}
}
