package net.java.dev.cejug.classifieds.server.config;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.bind.Unmarshaller.Listener;

import net.java.dev.cejug.classifieds.server.generated.i18n.ConfigUnmarshalI18N;

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
	 * This is used to mark the first error occurrence. Just before the first
	 * error we print a big warning message notifying the user about mallformed
	 * configuration data. After that, all other errors appears as simples
	 * SEVERE log entry.
	 */
	private boolean firstError = false;

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

	/**
	 * This wrapper method is used to allow a more elegant error notification
	 * with a big header.
	 */
	private void severe(String key, String[] params) {
		if (!firstError) {
			logger.log(Level.SEVERE, ConfigUnmarshalI18N.CONFIG_ERRORS_HEADER
					.value(), new String[0]);
			firstError = true;
		}
		logger.log(Level.SEVERE, key, params);
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
	}
}