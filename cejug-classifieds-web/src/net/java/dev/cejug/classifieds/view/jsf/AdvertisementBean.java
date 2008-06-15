package net.java.dev.cejug.classifieds.view.jsf;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.java.dev.cejug.classifieds.model.service.AdvertisementService;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementHeader;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsBusiness;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug.classifieds.server.generated.contract.Customer;
import net.java.dev.cejug.classifieds.server.generated.contract.Locale;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;

/**
 * This class is responsible for the operations related to advertisement.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class AdvertisementBean {
	/**
	 * The actual advertisement this bean is editing or publishing.
	 */
	private Advertisement advertisement = new Advertisement();
	/**
	 * The advertisement service to communicate with the server
	 */
	private AdvertisementService adsService;

	/**
	 * Publish the advertisement.
	 * 
	 * @param actionEvent
	 *            the event generated by component
	 */
	public void publish(ActionEvent actionEvent) {
		try {
			/**
			 * TODO: the domain should be provided by the client to the server,
			 * and it should be previously registered in the cejug-classifieds
			 * server. Only known domain can publish advertisements.
			 */

			String domain = "www.cejug.org";
			Advertisement advertisement = new Advertisement();
			Customer customer = new Customer();
			customer.setDomain(domain);
			customer.setLogin("fgaucho");

			advertisement.setAdvertiser(customer);
			// Publishing period
			DatatypeFactory factory;
			factory = DatatypeFactory.newInstance();
			Calendar today = GregorianCalendar.getInstance();
			advertisement.setPublishingStart(factory
					.newXMLGregorianCalendar((GregorianCalendar) today));
			Calendar fiveDaysLater = GregorianCalendar.getInstance();
			fiveDaysLater.roll(Calendar.DAY_OF_YEAR, 5);
			advertisement
					.setPublishingFinish(factory
							.newXMLGregorianCalendar((GregorianCalendar) fiveDaysLater));
			// Advertisement contents
			advertisement.setHeadline("JAXWSUnleashed");
			advertisement
					.setShortDescription("JAXWS Unleashed book for only $15,-");
			advertisement
					.setFullText("This is a test advertisement.. several lines here.");

			advertisement.setSectionId(1);
			Locale locale = new Locale();
			locale.setLanguage("pt");
			locale.setCountry("BR");
			advertisement.setLocale(locale);
			advertisement.setKeywords("J2EE,JAXWS");
			advertisement.setStatus(1);

			AdvertisementHeader header = new AdvertisementHeader();
			header.setCustomerDomain(domain);
			header.setCustomerLogin("fgaucho");

			/**
			 * TODO: EXAMPLE of how to invoke the Cejug-Classifieds-Server
			 * operations.
			 */

			CejugClassifiedsBusiness classifiedsBusinessService = new CejugClassifiedsServiceBusiness()
					.getCejugClassifiedsBusiness();

			ServiceStatus status = classifiedsBusinessService.publishOperation(
					advertisement, header);

			// adsService.publish(getAdvertisement());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(status.getDescription()
							+ " <<< response from service :)"));
			// "Your advertisement was published. Can you see it?"));
		} catch (DatatypeConfigurationException e) {
			// TODO Logging, throws customer error message.....
			e.printStackTrace();
		} catch (Exception runntimeError) {
			// TODO Logging, throws customer error message.....
			runntimeError.printStackTrace();
		}
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public AdvertisementService getAdsService() {
		return adsService;
	}

	public void setAdsService(AdvertisementService adsService) {
		this.adsService = adsService;
	}

}
