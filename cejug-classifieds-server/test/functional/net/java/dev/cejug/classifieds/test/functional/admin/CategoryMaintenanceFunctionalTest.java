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
package net.java.dev.cejug.classifieds.test.functional.admin;

import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminRemote;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.ReadCategoryBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the category maintenance CRUD operations through the following steps:
 * <ul>
 * <li><strong>@Before</strong> the tests, we store the number of already
 * available categories. After all tests we check this number again, to be sure
 * our tests didn't changed the state of the database.
 * <ol>
 * <li>CREATE a new Category, named
 * <em>FunctionalTest + System.currentTimeMillis()</em></li>
 * <li>READ the bundle of available categories and check if our newly created
 * category is there. At this moment, we read the ID of the test category.</li>
 * <li>UPDATE the test category, modifying its name or other attribute.</li>
 * <li>READ the category by ID and check if the updated data is correct.</li>
 * <li>DELETE the test created category.</li>
 * </ol>
 * </li>
 * <li><strong>@After</strong> the tests, we and check the number of remained
 * categories in the server side to be sure the state of the database haven't
 * changed.</li>
 * </ul>
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 249 $ ($Date: 2008-06-08 13:29:07 +0200 (Sun, 08 Jun 2008) $)
 */
public class CategoryMaintenanceFunctionalTest {

	/**
	 * We first store the number of already available categories. After all
	 * tests, we check this number again, to be sure our tests didn't changed
	 * the state of the database.
	 * 
	 * @throws Exception
	 *             Generic exception, thrown by connection failure or read
	 *             bundle categories errors.
	 */
	@Before
	public void setUp() throws Exception {
		// database pre-setup
	}

	/**
	 * Check if the number of available categories remains the same after the
	 * tests. A successful test shouldn't modify the original state of the
	 * database, otherwise we never know what to expect in the next test ;) The
	 * server database is supposed to be reseted before a complete test run.
	 * 
	 * @throws Exception
	 *             Generic exception, thrown by connection failure or read
	 *             bundle categories errors.
	 */
	@After
	public void tearDown() throws Exception {
		// database pre-setup
	}

	/**
	 * Shared count categories method, to be sure the same counting mechanism is
	 * used before and after the tests. It loads from the server a list of
	 * available categories and returns its size.
	 * 
	 * @return the number of categories stored in the database.
	 */
	private int countAvailableCategoriesOnDatabase(CejugClassifiedsAdmin admin) {
		List<AdvertisementCategory> categories = admin
				.readCategoryBundleOperation(new ReadCategoryBundleParam())
				.getAdvertisementCategory();
		return categories.size();

	}

	@Test
	public void testingSoapWebService() {
		try {
			CejugClassifiedsAdmin adminEndpoint = new CejugClassifiedsServiceAdmin()
					.getCejugClassifiedsAdmin();
			int availableCategoriesBeforeTests = countAvailableCategoriesOnDatabase(adminEndpoint);
			crudCategory(adminEndpoint);
			Assert.assertEquals(availableCategoriesBeforeTests,
					countAvailableCategoriesOnDatabase(adminEndpoint));

		} catch (Exception n) {
			n.printStackTrace();
			Assert.fail(n.getMessage());
		}
	}

	@Test
	public void testingRemoteEjb() {
		try {
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"com.sun.enterprise.naming.SerialInitContextFactory");
			props.setProperty("java.naming.factory.url.pkgs",
					"com.sun.enterprise.naming");
			props
					.setProperty("java.naming.factory.state",
							"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
			InitialContext ctx = new InitialContext(props);
			CejugClassifiedsAdmin adminSessionBean = (ClassifiedsAdminRemote) ctx
					.lookup("net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminRemote");
			int availableCategoriesBeforeTests = countAvailableCategoriesOnDatabase(adminSessionBean);
			crudCategory(adminSessionBean);
			Assert.assertEquals(availableCategoriesBeforeTests,
					countAvailableCategoriesOnDatabase(adminSessionBean));
		} catch (NamingException n) {
			n.printStackTrace();
			Assert.fail(n.getMessage());
		}
	}

	private void crudCategory(CejugClassifiedsAdmin admin) {
		// CREATE
		AdvertisementCategory category = new AdvertisementCategory();
		category.setName("FunctionalTest" + System.currentTimeMillis());
		category
				.setDescription("This category was created just for testing, you are free to delete it");
		category.setDescription("Functional Category Test.");
		CreateCategoryParam catParam = new CreateCategoryParam();
		catParam.setAdvertisementCategory(category);
		ServiceStatus status = admin.createCategoryOperation(catParam);
		Assert.assertEquals(status.getStatusCode(), 200);

		// READ
		ReadCategoryBundleParam param = new ReadCategoryBundleParam();
		List<AdvertisementCategory> categories = admin
				.readCategoryBundleOperation(param).getAdvertisementCategory();
		// We created a category on the setup method, so we assume there is at
		// least 1 category.
		Assert.assertFalse(categories.isEmpty());

		for (AdvertisementCategory advertisementCategory : categories) {
			if (advertisementCategory.getName().equals(category.getName())) {
				// The just created category has no ID, so we need to lookup for
				// its name in the received list in order to know its ID.
				category = advertisementCategory;
				break;
			}
		}

		// UPDATE
		String newName = "NewName" + System.currentTimeMillis();
		category.setName(newName);
		UpdateCategoryParam updateParam = new UpdateCategoryParam();
		updateParam.setAdvertisementCategory(category);
		admin.updateCategoryOperation(updateParam);
		List<AdvertisementCategory> updatedCategories = admin
				.readCategoryBundleOperation(param).getAdvertisementCategory();

		boolean updateOk = false;
		for (AdvertisementCategory advertisementCategory : updatedCategories) {
			if (advertisementCategory.getId().equals(category.getId())) {
				// Check if the received category has the newly create name.
				Assert.assertEquals(advertisementCategory.getName(), newName);
				updateOk = true;
				break;
			}
		}
		Assert.assertTrue(updateOk);

		// DELETE
		// remove or inactive the test advertisement
		DeleteCategoryParam deleteParam = new DeleteCategoryParam();
		deleteParam.setPrimaryKey(category.getId());
		ServiceStatus deleteStatus = admin.deleteCategoryOperation(deleteParam);
		Assert.assertEquals(deleteStatus.getStatusCode(), 200);
	}
}
