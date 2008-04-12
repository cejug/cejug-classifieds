package net.java.dev.cejug.classifieds.server.config;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class CejugClassifiedsServerConfigValidationHandler implements
		ValidationEventHandler {

	public boolean handleEvent(ValidationEvent arg0) {
		System.out.println(": " + arg0.getMessage());
		return false;
	}

}
