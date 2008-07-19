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

import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.ReadCategoryBundleParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 249 $ ($Date: 2008-06-08 13:29:07 +0200 (Sun, 08 Jun 2008) $)
 */
public class MaintainCategoriesFunctionalTest {

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
	public void testCategory() {
		CejugClassifiedsAdmin service = new CejugClassifiedsServiceAdmin()
				.getCejugClassifiedsAdmin();
		ReadCategoryBundleParam param = new ReadCategoryBundleParam();

		if (service.readCategoryBundleOperation(param)
				.getAdvertisementCategory().isEmpty()) {
			AdvertisementCategory category = new AdvertisementCategory();
			category.setName("cars");
			category.setDescription("new and used cars.");
			CreateCategoryParam catParam = new CreateCategoryParam();
			catParam.setAdvertisementCategory(category);
			ServiceStatus status = service.createCategoryOperation(catParam);
			assert status.getDescription().equalsIgnoreCase("OK");

			category = new AdvertisementCategory();
			category.setName("jobs");
			category.setDescription("find a new job today.");
			catParam.setAdvertisementCategory(category);
			status = service.createCategoryOperation(catParam);
			assert status.getDescription().equalsIgnoreCase("OK");

			category = new AdvertisementCategory();
			category.setName("gadgets");
			category.setDescription("tech tools & gadgets.");
			catParam.setAdvertisementCategory(category);
			status = service.createCategoryOperation(catParam);
			System.out.println(status.getDescription());
			assert status.getDescription().equalsIgnoreCase("OK");
		}

	}
}
