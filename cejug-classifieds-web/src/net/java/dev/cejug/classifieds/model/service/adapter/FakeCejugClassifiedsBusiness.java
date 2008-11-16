package net.java.dev.cejug.classifieds.model.service.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebServiceException;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.Rss;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
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
 * TODO: convert it to a mock class, it may be usefull for local tests... just a
 * fake
 * 
 * @author Tarso Bessa
 * 
 */
public class FakeCejugClassifiedsBusiness implements CejugClassifiedsBusiness {

	@Override
	public ServiceStatus createCustomerOperation(CreateCustomerParam newCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus deleteCustomerOperation(
			DeleteCustomerParam obsoleteCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdvertisementCollection loadAdvertisementOperation(
			AdvertisementCollectionFilter filter) {
		AdvertisementCollection col = new AdvertisementCollection();
		List<Advertisement> ads = new ArrayList<Advertisement>();
		for (int i = 0, count = (int) (Math.random() * 10); i < count; i++) {

			Advertisement ad = new Advertisement();
			ad.setText("Title " + i);
			ad.setSummary("This is the summary text " + i);
			ads.add(ad);
		}
		col.setAdvertisement(ads);
		return col;
	}

	@Override
	public Feed loadAtomOperation(SyndicationFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rss loadRssOperation(SyndicationFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advertisement publishOperation(Advertisement advertisement,
			PublishingHeader header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryCollection readCategoryBundleOperation(
			BundleRequest categoryBundleRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerCollection readCustomerBundleOperation(
			ReadCustomerBundleParam getCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus reportSpamOperation(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus updateCustomerOperation(
			UpdateCustomerParam partialCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(
			BundleRequest advTypeBundleRequest) {
		// TODO Auto-generated method stub
		throw new WebServiceException("NOT_IMPLEMENTED");
	}

}
