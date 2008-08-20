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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.namespace.QName;
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
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;
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

import org.w3._2005.atom.EntryType;
import org.w3._2005.atom.FeedType;
import org.w3._2005.atom.IdType;
import org.w3._2005.atom.PersonType;
import org.w3._2005.atom.UriType;

import uk.co.thearchitect.schemas.rss_2_0.Rss;
import uk.co.thearchitect.schemas.rss_2_0.TRssChannel;
import uk.co.thearchitect.schemas.rss_2_0.TRssItem;

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

	/**
	 * @return <a href=
	 *         "http://en.wikipedia.org/wiki/Atom_(standard)#Example_of_an_Atom_1.0_Feed"
	 *         >Example:</a>
	 * 
	 *         <pre>
	 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
	 * &lt;feed xmlns=&quot;http://www.w3.org/2005/Atom&quot;&gt;
	 *  
	 *  &lt;title&gt;Example Feed&lt;/title&gt;
	 *  &lt;subtitle&gt;A subtitle.&lt;/subtitle&gt;
	 *  &lt;link href=&quot;http://example.org/feed/&quot; rel=&quot;self&quot;/&gt;
	 *  &lt;link href=&quot;http://example.org/&quot;/&gt;
	 *  &lt;updated&gt;2003-12-13T18:30:02Z&lt;/updated&gt;
	 *  &lt;author&gt;
	 *    &lt;name&gt;John Doe&lt;/name&gt;
	 *    &lt;email&gt;johndoe@example.com&lt;/email&gt;
	 *  &lt;/author&gt;
	 *  &lt;id&gt;urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6&lt;/id&gt;
	 *  
	 *  &lt;entry&gt;
	 *    &lt;title&gt;Atom-Powered Robots Run Amok&lt;/title&gt;
	 *    &lt;link href=&quot;http://example.org/2003/12/13/atom03&quot;/&gt;
	 *    &lt;id&gt;urn:uuid:1225c695-cfb8-4ebb-aaaa-80da344efa6a&lt;/id&gt;
	 *    &lt;updated&gt;2003-12-13T18:30:02Z&lt;/updated&gt;
	 *    &lt;summary&gt;Some text.&lt;/summary&gt;
	 *  &lt;/entry&gt;
	 *  
	 * &lt;/feed&gt;
	 * </pre>
	 */
	@Override
	public FeedType loadAtomOperation(SyndicationFilter filter) {
		try {
			FeedType atomFeed = new FeedType();
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "title"),
					"Cars @ Cejug Classifieds");
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "subtitle"),
					"All cars");
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "link"),
					"http://cejug-classifieds-server/atom&section=cars");
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "updated"),
					Calendar.getInstance().toString());

			PersonType author = new PersonType();
			author.getNameOrUriOrEmail().add("Cejug-Classifieds");
			author.getNameOrUriOrEmail().add(
					"dev@cejug-classifieds.dev.java.net");
			UriType uri = new UriType();
			uri.setValue("http://cejug-classifieds-server/atom&section=cars");
			author.getNameOrUriOrEmail().add(uri);
			atomFeed.getAuthorOrCategoryOrContributor().add(author);

			IdType feedId = new IdType();
			feedId.setValue("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6");
			atomFeed.getAuthorOrCategoryOrContributor().add(feedId);

			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advFacade
					.readAll(AdvertisementEntity.class);

			for (AdvertisementEntity adv : result) {

				EntryType entry = new EntryType();

				entry.getOtherAttributes().put(
						new QName("http://www.w3.org/2005/Atom", "title"),
						adv.getTitle());
				entry.getOtherAttributes().put(
						new QName("http://www.w3.org/2005/Atom", "link"),
						"http://cejug-classifieds-server/atom&id="
								+ adv.getId());
				atomFeed.getOtherAttributes().put(
						new QName("http://www.w3.org/2005/Atom", "updated"),
						Calendar.getInstance().toString());
				atomFeed.getAuthorOrCategoryOrContributor().add(entry);
			}
			return atomFeed;
		} catch (Exception e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	/**
	 * <pre>
	 * &lt;?xml version=&quot;1.0&quot;?&gt;
	 * &lt;rss version=&quot;2.0&quot;&gt;
	 *    &lt;channel&gt;
	 *       &lt;title&gt;Liftoff News&lt;/title&gt;
	 *       &lt;link&gt;http://liftoff.msfc.nasa.gov/&lt;/link&gt;
	 *       &lt;description&gt;Liftoff to Space Exploration.&lt;/description&gt;
	 *       &lt;language&gt;en-us&lt;/language&gt;
	 *       &lt;pubDate&gt;Tue, 10 Jun 2003 04:00:00 GMT&lt;/pubDate&gt;
	 * 
	 *       &lt;lastBuildDate&gt;Tue, 10 Jun 2003 09:41:01 GMT&lt;/lastBuildDate&gt;
	 *       &lt;docs&gt;http://blogs.law.harvard.edu/tech/rss&lt;/docs&gt;
	 *       &lt;generator&gt;Weblog Editor 2.0&lt;/generator&gt;
	 *       &lt;managingEditor&gt;editor@example.com&lt;/managingEditor&gt;
	 *       &lt;webMaster&gt;webmaster@example.com&lt;/webMaster&gt;
	 *       &lt;item&gt;
	 * 
	 *          &lt;title&gt;Star City&lt;/title&gt;
	 *          &lt;link&gt;http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp&lt;/link&gt;
	 *          &lt;description&gt;How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href=&quot;http://howe.iki.rssi.ru/GCTC/gctc_e.htm&quot;&gt;Star City&lt;/a&gt;.&lt;/description&gt;
	 *          &lt;pubDate&gt;Tue, 03 Jun 2003 09:39:21 GMT&lt;/pubDate&gt;
	 *          &lt;guid&gt;http://liftoff.msfc.nasa.gov/2003/06/03.html#item573&lt;/guid&gt;
	 * 
	 *       &lt;/item&gt;
	 *    &lt;/channel&gt;
	 * &lt;/rss&gt;
	 * </pre>
	 */
	@Override
	public Rss loadRssOperation(SyndicationFilter filter) {
		try {
			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advFacade
					.readAll(AdvertisementEntity.class);

			Rss rssFeed = new Rss();
                        rssFeed.getOtherAttributes().put(
                            new QName("", "version"), "2.0");
			TRssChannel channel = new TRssChannel();
                        /*rssFeed.getOtherAttributes().put(
                                        new QName("http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                        "link"),
                                        "http://cejug-classifieds-server/atom&section=cars");
                        rssFeed
                                        .getOtherAttributes()
                                        .put(
                                                        new QName(
                                                                        "http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                                        "description"),
                                                        "TODO: add description to sections");
                        rssFeed.getOtherAttributes().put(
                                        new QName("http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                        "pubDate"), Calendar.getInstance().toString());
                        rssFeed
                                        .getOtherAttributes()
                                        .put(
                                                        new QName(
                                                                        "http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                                        "lastBuildDate"),
                                                        Calendar.getInstance().toString());
                        rssFeed.getOtherAttributes().put(
                                        new QName("http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                        "docs"), "TODO: docs reference");
                        rssFeed.getOtherAttributes().put(
                                        new QName("http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                        "managingEditor"),
                                        "dev@cejug-classifieds.dev.java.net");
                        rssFeed.getOtherAttributes().put(
                                        new QName("http://www.thearchitect.co.uk/schemas/rss-2_0",
                                                        "webMaster"), "dev@cejug-classifieds.dev.java.net");
*/
			
			for (AdvertisementEntity adv : result) {
				TRssItem item = new TRssItem();
				item
						.getOtherAttributes()
						.put(
								new QName(
										"http://www.thearchitect.co.uk/schemas/rss-2_0",
										"title"), adv.getTitle());
				channel.getTitleOrLinkOrDescription().add(item);
			}
			rssFeed.setChannel(channel);
			return rssFeed;
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
