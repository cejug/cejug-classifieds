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
package net.java.dev.cejug.classifieds.test.integration.admin;

import java.util.List;

import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the advertisement type maintenance CRUD operations through the following
 * steps:
 * <ul>
 * <li><strong>@Before</strong> the tests, we store the number of already
 * available advertisement types. After all tests we check this number again, to
 * be sure our tests didn't changed the state of the database.
 * <ol>
 * <li>CREATE a new Advertisement Type, named
 * <em>FunctionalTest + System.currentTimeMillis()</em></li>
 * <li>READ the bundle of available advertisement types and check if our newly
 * created adv type is there. At this moment, we read the ID of the test adv
 * type.</li>
 * <li>UPDATE the test adv type, modifying its name or other attribute.</li>
 * <li>READ the adv type by ID and check if the updated data is correct.</li>
 * <li>DELETE the test created adv type.</li>
 * </ol>
 * </li>
 * <li><strong>@After</strong> the tests, we and check the number of remained
 * advertisement types in the server side to be sure the state of the database
 * haven't changed.</li>
 * </ul>
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 249 $ ($Date: 2008-06-08 13:29:07 +0200 (Sun, 08 Jun 2008) $)
 */
public class AdvertisementTypeMaintenanceFunctionalTest {
	private CejugClassifiedsAdmin admin = null;
	private AdvertisementType advType = null;
	private int availableAdvTypesBeforeTests = -1;

	/**
	 * We first store the number of already available advertisement types. After
	 * all tests, we check this number again, to be sure our tests didn't
	 * changed the state of the database.
	 * 
	 * @throws Exception
	 *             Generic exception, thrown by connection failure or read
	 *             bundle advertisement types errors.
	 */
	@Before
	public void setUp() throws Exception {
		admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
		availableAdvTypesBeforeTests = countAvailableAdvTypesOnDatabase();
	}

	/**
	 * Shared count advertisement types method, to be sure the same counting
	 * mechanism is used before and after the tests. It loads from the server a
	 * list of available advertisement types and returns its size.
	 * 
	 * @return the number of advertisement types stored in the database.
	 */
	private int countAvailableAdvTypesOnDatabase() {
		List<AdvertisementType> advertisementTypes = admin
				.readAdvertisementTypeBundleOperation(
						new ReadAdvertisementTypeBundleParam())
				.getAdvertisementType();
		return advertisementTypes.size();
	}

	/**
	 * Check if the number of available advertisement types remains the same
	 * after the tests. A successful test shouldn't modify the original state of
	 * the database, otherwise we never know what to expect in the next test ;)
	 * The server database is supposed to be reseted before a complete test run.
	 * 
	 * @throws Exception
	 *             Generic exception, thrown by connection failure or read
	 *             bundle advertisement types errors.
	 */
	@After
	public void tearDown() throws Exception {
		Assert.assertEquals(availableAdvTypesBeforeTests,
				countAvailableAdvTypesOnDatabase());
	}

	@Test
	public void crudCategory() {
		// CREATE
		advType = new AdvertisementType();
		advType.setName("FunctionalTest" + System.currentTimeMillis());
		advType.setDescription("Functional ADV Type Test.");
		advType.setMaxAttachmentSize(987L);
		advType.setMaxTextLength(5642L);
		CreateAdvertisementTypeParam createParam = new CreateAdvertisementTypeParam();
		createParam.setAdvertisementType(advType);
		ServiceStatus status = admin
				.createAdvertisementTypeOperation(createParam);
		Assert.assertEquals(status.getStatusCode(), 200);

		// READ
		ReadAdvertisementTypeBundleParam readParam = new ReadAdvertisementTypeBundleParam();
		List<AdvertisementType> availableTypes = admin
				.readAdvertisementTypeBundleOperation(readParam)
				.getAdvertisementType();
		// We created a adv type on the setup method, so we assume there is at
		// least 1 adv type.
		Assert.assertFalse(availableTypes.isEmpty());

		for (AdvertisementType advertisementType : availableTypes) {
			if (advertisementType.getName().equals(advType.getName())) {
				// The just created adv type has no ID, so we need to lookup for
				// its name in the received list in order to know its ID.
				advType = advertisementType;
				break;
			}
		}

		// UPDATE
		String newName = "NewName" + System.currentTimeMillis();
		advType.setName(newName);
		UpdateAdvertisementTypeParam updateParam = new UpdateAdvertisementTypeParam();
		updateParam.setAdvertisementType(advType);
		admin.updateAdvertisementTypeOperation(updateParam);
		List<AdvertisementType> updatedAdvTypes = admin
				.readAdvertisementTypeBundleOperation(
						new ReadAdvertisementTypeBundleParam())
				.getAdvertisementType();

		boolean updateOk = false;
		for (AdvertisementType advertisementType : updatedAdvTypes) {
			if (advertisementType.getId() == advType.getId()) {
				// Check if the received adv type has the newly create name.
				Assert.assertEquals(advertisementType.getName(), newName);
				updateOk = true;
				break;
			}
		}
		Assert.assertTrue(updateOk);

		// DELETE
		// remove or inactive the test advertisement type
		DeleteAdvertisementTypeParam deleteParam = new DeleteAdvertisementTypeParam();
		deleteParam.setPrimaryKey(advType.getId());
		ServiceStatus deleteStatus = admin
				.deleteAdvertisementTypeOperation(deleteParam.getPrimaryKey());
		Assert.assertEquals(deleteStatus.getStatusCode(), 200);
	}
}
