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
 originally used by CEJUG - Cear� Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.test.integration.business;

import java.util.Calendar;
import java.util.GregorianCalendar;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.business.RssCollection;
import net.java.dev.cejug_classifieds.metadata.business.RssFilterCollection;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Test the diploma validation operation.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class LoadRssFunctionalTest {

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
	public void testLoadRssOperation() {
		/*
		 * check if the test advertisement comes with the RSS
		 */
		CejugClassifiedsBusiness service = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		RssFilterCollection filterCollection = new RssFilterCollection();
		SyndicationFilter filter = new SyndicationFilter();

		// retrieve the advertisement RSS since yesterday to today.
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar yesterday = new GregorianCalendar();
		yesterday.roll(Calendar.DAY_OF_YEAR, false);

		filter.setDateInitial(yesterday);
		filter.setDateFinal(today);

		filterCollection.getFilterCollection().add(filter);

		RssCollection collection = service.loadRssOperation(filterCollection);

		assertTrue("RSS empty", collection.getRssCollection().size() > 0);
	}

	/*
	@Test
	public void testLoadRssOperationFail() {
	}*/
}
