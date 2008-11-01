package net.java.dev.cejug.classifieds.model.service.adapter;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;

/**
 * This is a helper class to abstract the construction of
 * {@link CejugClassifiedsBusiness} WS implementation.
 * 
 * @author Tarso Bessa
 * 
 */
public class WSFactoryHelper {

	/**
	 * Returns a <code>CejugClassifiedsBusiness</code> web service
	 * implementation.
	 * 
	 * @return
	 */
	public static CejugClassifiedsBusiness getWebService() {
		// TODO: the problem seems the classpath, and not the service interface.
		// Don't forgett to turn on the server side (database, server and run
		// the ant task of the server module..)
		return new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
	}

}
