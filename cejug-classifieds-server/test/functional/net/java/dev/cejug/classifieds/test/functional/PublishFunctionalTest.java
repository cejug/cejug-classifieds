/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe GaÃºcho
 
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
 originally used by CEJUG - Cearï¿½ Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.test.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;
import net.java.dev.cejug.classifieds.server.generated.contract.Author;
import net.java.dev.cejug.classifieds.server.generated.contract.AuthorType;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsService;
import net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.contract.Locale;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
public class PublishFunctionalTest {

	@Before
	public void setUp() throws Exception {
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
		/*
		 * check if the test advertisement comes with the RSS
		 */

		Advertisement advertisement = new Advertisement();

		// Authorship
		Author author = new Author();
		author.setEmail("fgaucho@gmail.com");
		author.setName("Felipe Gaúcho");
		author.setType(AuthorType.COMMUNITY);
		advertisement.setAdvertiser(author);

		// Publishing period
		DatatypeFactory factory = DatatypeFactory.newInstance();
		Calendar today = GregorianCalendar.getInstance();
		advertisement.setPublishingStart(factory
				.newXMLGregorianCalendar((GregorianCalendar) today));
		Calendar fiveDaysLater = GregorianCalendar.getInstance();
		fiveDaysLater.roll(Calendar.DAY_OF_YEAR, 5);
		advertisement.setPublishingFinish(factory
				.newXMLGregorianCalendar((GregorianCalendar) fiveDaysLater));

		// Advertisement contents
		advertisement.setHeadline("JAXWS Unleashed");
		advertisement
				.setShortDescription("JAXWS Unleashed book for only $15,-");
		advertisement
				.setFullText("This is a test advertisement.. several lines here.");

		advertisement.setSectionId(1);
		Locale locale = new Locale();
		locale.setLanguage("pt");
		locale.setCountry("BR");
		advertisement.setLocale(locale);

		// connecting the web-service and calling the publish operation
		URL wsdlLocation = new URL(
				"http://localhost:8080/cejug-classifieds-server/server?wsdl");
		QName serviceName = new QName(
				"http://cejug-classifieds.dev.java.net/server",
				"CejugClassifiedsService");
		CejugClassifiedsService service = new CejugClassifiedsService(
				wsdlLocation, serviceName);
		ClassifiedsServiceInterface facade = service
				.getClassifiedsServiceInterface();
		AdvertisementBundle bundle = new AdvertisementBundle();

		bundle.getAdvertisements().add(advertisement);
		bundle.setAuthorId("cejug.registered.12");

		ServiceStatus status = facade.publishOperation(bundle);
		assert status.getDescription().equalsIgnoreCase("OK");
	}

	@Test
	public void testPublishOperationFail() {
	}
}
