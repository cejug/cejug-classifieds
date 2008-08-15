/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsBusinessLocal;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsBusinessRemote;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementTypeEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.interceptor.TimerInterceptor;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.AtomCollection;
import net.java.dev.cejug_classifieds.metadata.business.AtomFilterCollection;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.business.RssCollection;
import net.java.dev.cejug_classifieds.metadata.business.RssFilterCollection;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.CreateCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.Customer;
import net.java.dev.cejug_classifieds.metadata.common.CustomerCollection;
import net.java.dev.cejug_classifieds.metadata.common.DeleteCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.ReadCustomerBundleParam;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;
import net.java.dev.cejug_classifieds.metadata.common.UpdateCustomerParam;

import org.w3._2005.atom.FeedType;
import org.w3._2005.atom.TextType;

import edu.harvard.law.blogs.rss20.Channel;
import edu.harvard.law.blogs.rss20.Item;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Interceptors(TimerInterceptor.class)
@Stateless
@WebService(endpointInterface = "net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness", serviceName = "CejugClassifiedsServiceBusiness", portName = "CejugClassifiedsBusiness", targetNamespace = "http://cejug-classifieds.dev.java.net/business")
public class ClassifiedsBusinessSessionBean implements
		ClassifiedsBusinessLocal, ClassifiedsBusinessRemote {

	private static final String NOT_IMPLEMENTED = "operation not yet implemented";

	@EJB
	private transient AdvertisementFacadeLocal advFacade;

	@EJB
	private transient AdvertisementTypeFacadeLocal advTypeFacade;

	@EJB
	private transient CustomerFacadeLocal customerFacade;
	@EJB
	private transient CategoryFacadeLocal categoryFacade;
	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			ClassifiedsBusinessSessionBean.class.getName(), "i18n/log");

	@Override
	public AtomCollection loadAtomOperation(final AtomFilterCollection filter) {
		try {
			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advFacade
					.readAll(AdvertisementEntity.class);

			List<FeedType> atomCollection = new ArrayList<FeedType>();
			for (AdvertisementEntity adv : result) {
				FeedType feed = new FeedType();
				TextType title = new TextType();
				title.setType(adv.getTitle());
				// feed.getAuthorOrCategoryOrContributor().add(title);

				Item item = new Item();
				item.setAuthor(adv.getCustomer().getLogin());
				item.setAuthor("INCOMPLETE DATA SET");
				item.setDescription(adv.getSummary());
				item.setPubDate(adv.getStart());
				atomCollection.add(feed);
			}

			AtomCollection atoms = new AtomCollection();
			// atoms.getAtomCollection().add(atomCollection);
			return atoms;
		} catch (Exception e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public RssCollection loadRssOperation(final RssFilterCollection filter) {

		try {
			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advFacade
					.readAll(AdvertisementEntity.class);

			Channel channel = new Channel();
			for (AdvertisementEntity adv : result) {
				Item item = new Item();
				// item.setAuthor(adv.getVoucher().getCustomer().getLogin());
				item.setAuthor("INCOMPLETE DATA SET");
				item.setTitle(adv.getTitle());
				item.setDescription(adv.getSummary());
				// item.setPubDate(adv.getPublishingPeriod().iterator().next().
				// getDay());
				channel.getItem().add(item);
			}
			RssCollection col = new RssCollection();
			col.getRssCollection().add(channel);
			return col;
		} catch (Exception e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus publishOperation(final Advertisement advertisement,
			final PublishingHeader header) {

		// TODO: to implement the real code.
		try {
			/*
			 * // loading customer Map<String, String> params = new
			 * HashMap<String, String>(); params.clear(); params.put("d",
			 * header.getCustomerDomain()); params.put("l",
			 * header.getCustomerLogin());
			 */
			CustomerEntity customer = customerFacade.findOrCreate(header
					.getCustomerDomainId(), header.getCustomerLogin());

			// validating advertisement PIN
			AdvertisementEntity entity = new AdvertisementEntity();
			// entity.setKeywords(advertisement.getKeywords()); // TODO
			entity.setText(advertisement.getFullText());

			entity.setCustomer(customer);

			AdvertisementTypeEntity type = advTypeFacade.read(
					AdvertisementTypeEntity.class, advertisement.getTypeId());
			entity.setType(type);

			entity.setSummary(advertisement.getShortDescription());
			entity.setTitle(advertisement.getHeadline());

			CategoryEntity category = categoryFacade.read(CategoryEntity.class,
					advertisement.getCategoryId());
			entity.setCategory(category);

			Calendar start = Calendar.getInstance();
			Calendar finish = Calendar.getInstance();
			finish.add(Calendar.HOUR, 3);
			entity.setStart(start);
			entity.setFinish(finish);
			advFacade.create(entity);

			ServiceStatus status = new ServiceStatus();
			status.setDescription("OK");
			status.setStatusCode(202);

			status.setTimestamp(GregorianCalendar.getInstance());

			return status;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}

	@Override
	public AdvertisementCollection loadAdvertisementOperation(
			final AdvertisementCollectionFilter filter) {

		// TODO: load advertisements from timetable.... filtering with periods,
		// etc..

		try {
			AdvertisementCollection collection = new AdvertisementCollection();
			List<AdvertisementEntity> entities = advFacade
					.readAll(AdvertisementEntity.class);
			for (AdvertisementEntity entity : entities) {
				Advertisement adv = new Advertisement();
				adv.setId(entity.getId());
				Customer newCustomer = new Customer();
				newCustomer.setDomain("www.cejug.org");
				newCustomer.setLogin("test");
				adv.setCustomer(newCustomer);
				adv.setFullText(entity.getText());
				adv
						.setKeywords(Arrays.toString(entity.getKeywords()
								.toArray()));
				adv.setShortDescription(entity.getSummary());
				adv.setHeadline(entity.getTitle());
				collection.getAdvertisement().add(adv);
			}
			return collection;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public CategoryCollection readCategoryBundleOperation(
			final BundleRequest bundleRequest) {
		CategoryCollection categorySet;
		categorySet = new CategoryCollection();
		try {
			List<CategoryEntity> categories = categoryFacade
					.readAll(CategoryEntity.class);
			if (categories != null) {
				for (CategoryEntity category : categories) {
					AdvertisementCategory cat = new AdvertisementCategory();
					cat.setDescription(category.getDescripton());
					cat.setName(category.getName());
					int available = categoryFacade
							.countAdvertisements(category);
					cat.setAvailable(available);
					categorySet.getAdvertisementCategory().add(cat);
				}
			}
		} catch (Exception e) {
			// TODO log.......
			// e.printStackTrace();
			throw new WebServiceException(e);
		}
		return categorySet;
	}

	@Override
	public ServiceStatus reportSpamOperation(final int advId) {
		// ServiceStatus status = new ServiceStatus();
		// status.setDescription("OK");
		// status.setStatusCode(200);
		// return status;
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public ServiceStatus createCustomerOperation(
			final CreateCustomerParam newCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public ServiceStatus deleteCustomerOperation(
			final DeleteCustomerParam obsoleteCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public CustomerCollection readCustomerBundleOperation(
			final ReadCustomerBundleParam getCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public ServiceStatus updateCustomerOperation(
			final UpdateCustomerParam partialCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}
}
