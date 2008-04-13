package net.java.dev.cejug.classifieds.server.config;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.bind.Unmarshaller.Listener;

/**
 * This class is used to check the values read from the config XML. Despite JAXB
 * validates the XML against it schema, the contents of the properties file
 * should be verified following the expected ones.
 * 
 * @author Felipe Gaúcho
 * 
 */
public class CejugClassifiedsServerConfigUnmarshallerListener extends Listener {
	private Logger logger = null;

	/**
	 * Classifieds-Server config marshalling listener.
	 * 
	 * @param logger
	 *            the logger used to register the listener processing.
	 */
	public CejugClassifiedsServerConfigUnmarshallerListener(Logger logger) {
		this.logger = logger;
	}

	/**
	 * The Default constructor calls the constructor with an instance of the
	 * java.util.logging.Logger.
	 */
	public CejugClassifiedsServerConfigUnmarshallerListener() {
		this(LogManager.getLogManager().getLogger(
				CejugClassifiedsServerConfigUnmarshallerListener.class
						.getName()));
	}

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		/*
		 * if (target instanceof ConfigEmail) { validateEmailData((ConfigEmail)
		 * target); } else if (target instanceof ConfigPdfTemplate) {
		 * validateTemplateData((ConfigPdfTemplate) target); } else if (target
		 * instanceof ConfigSecurity) { validateSecurity((ConfigSecurity)
		 * target); }
		 */
		// logger.log(Level.SEVERE, key, params);
	}
}