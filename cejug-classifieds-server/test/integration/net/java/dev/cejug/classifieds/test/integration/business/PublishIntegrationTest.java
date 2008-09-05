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
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;

import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Locale;
import net.java.dev.cejug_classifieds.metadata.business.Period;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.Customer;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.kernelpanic.dbmonster.DBMonster;
import pl.kernelpanic.dbmonster.DBMonsterContext;
import pl.kernelpanic.dbmonster.DictionaryManager;
import pl.kernelpanic.dbmonster.generator.StringGenerator;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
public class PublishIntegrationTest extends AbstractServiceTestCase {
	private transient final StringGenerator strGen = new StringGenerator();

	@Before
	public void init() {
		try {
			DBMonsterContext ctx = new DBMonsterContext();
			DictionaryManager dictmanager = new DictionaryManager();
			Random random = new java.util.Random();
			dictmanager.setRandom(random);
			ctx.setProperty(DBMonster.DICTIONARY_MANAGER_KEY, dictmanager);
			ctx.setProperty(DBMonster.RANDOM_KEY, random);
			strGen.initialize(ctx);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testPublishOperation() throws DatatypeConfigurationException,
			MalformedURLException {
		try {
			/*
			 * -- TODO: review this code and do the proper test sequence: -
			 * create a new domain, for tests - insert new advertisement - load
			 * the advertisement - delete the domain and all of its
			 * advertisement (cleanup)
			 * 
			 * WARNING: for our first tests, we are creating a new domain on
			 * each test.
			 */
			CejugClassifiedsBusiness service = getBusinessService()
					.getCejugClassifiedsBusiness();

			CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin()
					.getCejugClassifiedsAdmin();

			// TODO: review (it is only a test)
			String domain = "cejug.functional.test.domain"
					+ System.currentTimeMillis();

			Domain newDomain = new Domain();
			newDomain.setUri(domain);
			newDomain.setBrand("CEJUG");
			newDomain.setSharedQuota(true);
			newDomain.setTimezone("America/Fortaleza");
			CreateDomainParam param = new CreateDomainParam();
			param.setDomain(newDomain);
			newDomain = admin.createDomainOperation(param);

			AdvertisementType type = new AdvertisementType();
			type.setDescription("oo");
			type.setMaxAttachmentSize(300);
			type.setName("courtesy");
			type.setMaxTextLength(250);
			CreateAdvertisementTypeParam advParam = new CreateAdvertisementTypeParam();
			advParam.setAdvertisementType(type);
			admin.createAdvertisementTypeOperation(advParam);

			Random random = new Random();

			long[] categoriesIds = new long[10];
			// CREATE
			for (int i = 0; i < categoriesIds.length; i++) {
				AdvertisementCategory category = new AdvertisementCategory();
				category.setName("test.cat." + random.nextInt() + "."
						+ random.nextInt());
				category
						.setDescription("This category was created just for testing, you are free to delete it");
				category.setDescription("Functional Category Test ("
						+ System.currentTimeMillis() + ").");
				CreateCategoryParam catParam = new CreateCategoryParam();
				catParam.setAdvertisementCategory(category);

				AdvertisementCategory newCategory = admin
						.createCategoryOperation(catParam);
				categoriesIds[i] = newCategory.getEntityId();
			}
			// TODO: admin.updateDomain();

			// ServiceStatus status = facade.publishOperation(bundle);

			// connecting the web-service and calling the publish operation
			/*
			 * URL wsdlLocation = new URL(
			 * "http://localhost:8080/cejug-classifieds-server/server?wsdl");
			 * QName serviceName = new QName(
			 * "http://cejug-classifieds.dev.java.net/server",
			 * "CejugClassifiedsService");
			 */

			// include or activate a new advertisement (submit via service or
			// direct
			// into database)
			/*
			 * check if the test advertisement comes with the RSS
			 */

			Advertisement advertisement = new Advertisement();
			Customer customer = new Customer();
			customer.setLogin("fgaucho");
			customer.setDomainId(newDomain.getEntityId());
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
			strGen.setAllowSpaces(true);
			strGen.setMinLength(1);
			strGen.setMaxLength(15);
			advertisement.setHeadline(strGen.generate().toString());
			strGen.setMaxLength(40);
			advertisement.setSummary(strGen.generate().toString());
			strGen.setMaxLength(250);
			advertisement.setText(strGen.generate().toString());
			advertisement.setCategoryId(categoriesIds[(int) (Math.random()
					* categoriesIds.length - 0.00001)]);
			Locale locale = new Locale();
			locale.setLanguage("pt");
			locale.setCountry("BR");
			advertisement.setLocale(locale);

			strGen.setAllowSpaces(false);
			strGen.setMaxLength(20);
			advertisement.setKeywords(strGen.generate().toString() + ", "
					+ strGen.generate().toString());
			advertisement.setStatus(1);

			PublishingHeader header = new PublishingHeader();
			header.setCustomerDomainId(newDomain.getEntityId());
			header.setCustomerLogin("fgaucho");

			ServiceStatus status = service.publishOperation(advertisement,
					header);
			assert status.getDescription().equalsIgnoreCase("OK");
		} catch (Exception ee) {
		        ee.printStackTrace();
			Assert.fail(ee.getMessage());
		}
	}
}
