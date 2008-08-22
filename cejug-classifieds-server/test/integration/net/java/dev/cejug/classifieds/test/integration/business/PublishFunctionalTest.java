/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe Gaúcho
 
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
package net.java.dev.cejug.classifieds.test.integration.business;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;

import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Locale;
import net.java.dev.cejug_classifieds.metadata.business.Period;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.Customer;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class PublishFunctionalTest {
	private transient CejugClassifiedsBusiness business;
	private transient Domain newDomain;

	@Before
	public void setUp() throws Exception {
		/*
		 * -- TODO: review this code and do the proper test sequence: - create a
		 * new domain, for tests - insert new advertisement - load the
		 * advertisement - delete the domain and all of its advertisement
		 * (cleanup)
		 * 
		 * WARNING: for our first tests, we are creating a new domain on each
		 * test.
		 */
		business = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();

		CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin()
				.getCejugClassifiedsAdmin();

		// TODO: review (it is only a test)
		String domain = "cejug.functional.test.domain"
				+ System.currentTimeMillis();
		newDomain = new Domain();
		newDomain.setDomain(domain);
		newDomain.setBrand("CEJUG");
		newDomain.setSharedQuota(true);
		newDomain.setTimezone("America/Fortaleza");
		CreateDomainParam param = new CreateDomainParam();
		param.setDomain(newDomain);
		admin.createDomainOperation(param);

		AdvertisementType type = new AdvertisementType();
		type.setDescription("oo");
		type.setMaxAttachmentSize(300);
		type.setName("courtesy");
		type.setMaxTextLength(250);
		CreateAdvertisementTypeParam advParam = new CreateAdvertisementTypeParam();
		advParam.setAdvertisementType(type);
		admin.createAdvertisementTypeOperation(advParam);

		// TODO: admin.updateDomain();

		// ServiceStatus status = facade.publishOperation(bundle);

		// connecting the web-service and calling the publish operation
		/*
		 * URL wsdlLocation = new URL(
		 * "http://localhost:8080/cejug-classifieds-server/server?wsdl"); QName
		 * serviceName = new QName(
		 * "http://cejug-classifieds.dev.java.net/server",
		 * "CejugClassifiedsService");
		 */

		// include or activate a new advertisement (submit via service or direct
		// into database)
	}

	@After
	public void tearDown() throws Exception {
		// remove or inactive the test advertisement
	}

	@Test
	public void testPublishOperation() throws DatatypeConfigurationException,
			MalformedURLException {

		try {
			/*
			 * check if the test advertisement comes with the RSS
			 */

			Advertisement advertisement = new Advertisement();
			Customer customer = new Customer();
			customer.setDomain("cejug.functional.test.domain"
					+ System.currentTimeMillis());
			customer.setLogin("fgaucho");
			customer.setDomain(newDomain.getDomain());
			advertisement.setCustomer(customer);

			// Publishing period
			Calendar today = GregorianCalendar.getInstance();
			Period period = new Period();
			period.setStart(today);

			Calendar fiveDaysLater = GregorianCalendar.getInstance();
			fiveDaysLater.roll(Calendar.DAY_OF_YEAR, 5);
			period.setFinish(fiveDaysLater);
			// Advertisement contents
			advertisement.setPublishingPeriod(period);
			advertisement.setHeadline("JAXWSUnleashed");
			advertisement
					.setShortDescription("JAXWS Unleashed book for only $15,-");
			advertisement
					.setFullText("This is a test advertisement.. several lines here.");

			advertisement.setCategoryId(1);
			Locale locale = new Locale();
			locale.setLanguage("pt");
			locale.setCountry("BR");
			advertisement.setLocale(locale);
			advertisement.setKeywords("J2EE,JAXWS");
			advertisement.setStatus(1);

			PublishingHeader header = new PublishingHeader();
			header.setCustomerDomainId(2);
			header.setCustomerLogin("fgaucho");

			ServiceStatus status = business.publishOperation(advertisement,
					header);
			assert status.getDescription().equalsIgnoreCase("OK");
		} catch (Exception ee) {
			ee.printStackTrace();
			Assert.fail(ee.getMessage());
		}
	}

	@Test
	public void testPublishOperationFail() {
		// TODO: simulates an invalid publishing request and check if it fails.
	}
}
