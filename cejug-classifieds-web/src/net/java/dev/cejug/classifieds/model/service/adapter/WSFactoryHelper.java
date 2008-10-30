package net.java.dev.cejug.classifieds.model.service.adapter;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.business.Rss;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.CreateCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.CustomerCollection;
import net.java.dev.cejug_classifieds.metadata.common.DeleteCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.ReadCustomerBundleParam;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;
import net.java.dev.cejug_classifieds.metadata.common.UpdateCustomerParam;

import org.w3._2005.atom.Feed;

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
