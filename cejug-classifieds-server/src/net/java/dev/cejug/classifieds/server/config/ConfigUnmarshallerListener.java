package net.java.dev.cejug.classifieds.server.config;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.xml.bind.Unmarshaller.Listener;

import net.java.dev.cejug.classifieds.server.generated.config.Injection;

/**
 * This class is used to check the values read from the config XML. Despite JAXB
 * validates the XML against it schema, the contents of the properties file
 * should be verified following the expected ones.
 * 
 * @author Felipe Gaúcho
 * 
 */
public class ConfigUnmarshallerListener extends Listener {
	private Logger logger = null;

	/**
	 * Classifieds-Server config marshalling listener.
	 * 
	 * @param logger
	 *            the logger used to register the listener processing.
	 */
	public ConfigUnmarshallerListener(Logger logger) {
		this.logger = logger;
	}

	/**
	 * The Default constructor calls the constructor with an instance of the
	 * java.util.logging.Logger.
	 */
	public ConfigUnmarshallerListener() {
		this(LogManager.getLogManager().getLogger(
				ConfigUnmarshallerListener.class.getName()));
	}

	@Override
	public void afterUnmarshal(Object target, Object parent) {
		if (target instanceof Injection) {
			validateServiceImplementation((Injection) target);
		}
		// logger.log(Level.SEVERE, key, params);
	}

	/**
	 * If the name of the service implementation was not injected, we assume to
	 * use the reference implementation class.
	 * 
	 * @param injection
	 */
	private void validateServiceImplementation(Injection injection) {
		String implementation = injection.getServiceImplementation();
		// TODO: under review (design changed)
		/*
		 * if (implementation == null) { injection
		 * .setServiceImplementation(ClassifiedsReferenceImplementation.class
		 * .getName()); } else { try { Class<?> type =
		 * Class.forName(implementation.trim(), false,
		 * this.getClass().getClassLoader()); type.newInstance(); } catch
		 * (ClassNotFoundException e) { logger.severe(String.format( "The
		 * service implementation cannot be found: {0}", implementation)); throw
		 * new WebServiceException(e); } catch (InstantiationException e) {
		 * logger .severe(String .format( "The service implementation cannot be
		 * instantiated: {0}", e.getMessage())); throw new
		 * WebServiceException(e); } catch (IllegalAccessException e) { logger
		 * .severe(String .format( "Problems trying to instantiate the service
		 * implementation: {0}", e.getMessage())); throw new
		 * WebServiceException(e); } }
		 */
	}
}