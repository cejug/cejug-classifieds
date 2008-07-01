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
package net.java.dev.cejug.classifieds.test.functional.admin;

import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementCategory;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsAdmin;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug.classifieds.server.generated.contract.CreateCategoryParam;
import net.java.dev.cejug.classifieds.server.generated.contract.DeleteCategoryParam;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;

import org.junit.After;
import org.junit.Before;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 249 $ ($Date: 2008-06-08 13:29:07 +0200 (Sun, 08 Jun 2008) $)
 */
public class CategoryMaintenanceFunctionalTest {
	private CejugClassifiedsAdmin admin = null;
	private AdvertisementCategory category = new AdvertisementCategory();

	@Before
	public void setUp() throws Exception {
		admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
		category.setName("FunctionalTest" + System.currentTimeMillis());
		category
				.setDescription("This category was created just for testing, you are free to delete it");
		CreateCategoryParam catParam = new CreateCategoryParam();
		catParam.setAdvertisementCategory(category);
		ServiceStatus status = admin.createCategoryOperation(catParam);
		assert status.getStatusCode() == 1;
	}

	@After
	public void tearDown() throws Exception {
		// remove or inactive the test advertisement
		DeleteCategoryParam param = new DeleteCategoryParam();
		param.setPrimaryKey(category.getId());
		admin.deleteCategoryOperation(param);
		admin = null;

	}
}
