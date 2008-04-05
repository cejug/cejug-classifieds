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
package net.java.dev.cejug.classifieds.test.functional;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.java.dev.cejug.classifieds.server.generated.CejugClassifiedsService;
import net.java.dev.cejug.classifieds.server.generated.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.SyndicationFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
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
		ClassifiedsServiceInterface service = new CejugClassifiedsService()
				.getClassifiedsServiceInterface();
		System.out.println(service);
		RssFilterCollection filterCollection = new RssFilterCollection();
		SyndicationFilter filter = new SyndicationFilter();

		// retrieve the advertisement RSS since yesterday to today.
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar yesterday = new GregorianCalendar();
		yesterday.roll(Calendar.DAY_OF_YEAR, false);

		try {
			filter.setDateInitial(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(yesterday));
			filter.setDateFinal(DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(today));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filterCollection.getFilterCollection().add(filter);

		RssCollection collection = service.loadRssOperation(filterCollection);
		System.out.println(1);
		assert collection.getRssCollection().size() > 0;
	}

	@Test
	public void testLoadRssOperationFail() {
	}
}
