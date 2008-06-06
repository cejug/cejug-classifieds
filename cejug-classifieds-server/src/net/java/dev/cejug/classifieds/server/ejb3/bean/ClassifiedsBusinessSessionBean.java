package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.PublishingPeriodEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.PublishingPeriodEntity.PeriodState;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.interceptor.TimerInterceptor;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementHeader;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.Channel;
import net.java.dev.cejug.classifieds.server.generated.contract.EntryType;
import net.java.dev.cejug.classifieds.server.generated.contract.FeedType;
import net.java.dev.cejug.classifieds.server.generated.contract.Item;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;
import net.java.dev.cejug.classifieds.server.generated.contract.TextType;
import net.java.dev.cejug.classifieds.server.handler.TimeKeeperSoapHandler;

/**
 * @WebService(name = "CejugClassifiedsBusiness", targetNamespace =
 *                  "http://cejug-classifieds.dev.java.net/business")
 * @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
 */

@Interceptors(TimerInterceptor.class)
@Stateless
public class ClassifiedsBusinessSessionBean implements
		ClassifiedsBusinessRemote {

	@EJB
	private AdvertisementFacadeLocal advertisementFacade;

	// @EJB	private CustomerFacadeLocal customerFacade;

	/**
	 * the global log manager, used to allow third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(ClassifiedsBusinessSessionBean.class
			.getName(), "i18n/log");

	private final DatatypeFactory factory;

	public ClassifiedsBusinessSessionBean() {

		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public AtomCollection loadAtomOperation(AtomFilterCollection filter) {

		try {
			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advertisementFacade
					.getLatest(50);

			List<FeedType> atomCollection = new ArrayList<FeedType>();
			for (AdvertisementEntity adv : result) {
				FeedType feed = new FeedType();
				EntryType entry = new EntryType();
				TextType title = new TextType();
				title.setType(adv.getTitle());
				feed.getAuthorOrCategoryOrContributor().add(title);

				Item item = new Item();
				// item.setAuthor(adv.getVoucher().getCustomer().getLogin());
				item.setAuthor("INCOMPLETE DATA SET");
				item.setDescription(adv.getSummary());
				// item.setPubDate(adv.getPublishingPeriod().iterator().next().getDay());
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
	public RssCollection loadRssOperation(RssFilterCollection filter) {

		try {
			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advertisementFacade
					.getLatest(50);

			Channel channel = new Channel();
			for (AdvertisementEntity adv : result) {
				Item item = new Item();
				// item.setAuthor(adv.getVoucher().getCustomer().getLogin());
				item.setAuthor("INCOMPLETE DATA SET");
				item.setTitle(adv.getTitle());
				item.setDescription(adv.getSummary());
				// item.setPubDate(adv.getPublishingPeriod().iterator().next().getDay());
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
	public ServiceStatus reportSpamOperation(SpamReport spam) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus publishOperation(Advertisement advertisement,
			AdvertisementHeader header) {

		try {
			/*
			 * // loading customer Map<String, String> params = new HashMap<String,
			 * String>(); params.clear(); params.put("d",
			 * header.getCustomerDomain()); params.put("l",
			 * header.getCustomerLogin()); CustomerEntity customer =
			 * customerFacade.get(params);
			 */

			// validating advertisement PIN
			AdvertisementEntity entity = new AdvertisementEntity();
			entity.setKeywords(advertisement.getKeywords());
			entity.setText(advertisement.getFullText());

			// entity.setVoucher(voucher); // TODO: de onde vem o vouche??
			entity.setSummary(advertisement.getShortDescription());
			entity.setTitle(advertisement.getHeadline());
			PublishingPeriodEntity period = new PublishingPeriodEntity();
			period.setDay(new Date(advertisement.getPublishingStart()
					.getMillisecond()));
			period.setState(PeriodState.NEW);

			Collection<PublishingPeriodEntity> c = new ArrayList<PublishingPeriodEntity>();
			c.add(period);
			// entity.setPublishingPeriod(c);
			advertisementFacade.create(entity);

			ServiceStatus status = new ServiceStatus();
			status.setDescription("OK");
			status.setCode(202);

			status
					.setTimestamp(factory
							.newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar
									.getInstance()));

			return status;
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}
}
