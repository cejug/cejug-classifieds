/*
 * @(#) $CVSHeader:  $
 *
 * Copyright (C) 2008 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Netcetera AG, Switzerland.  The program(s) may be used and/or copied
 * only with the written permission of Netcetera AG or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 *
 * @(#) $Id: codetemplates.xml,v 1.5 2004/06/29 12:49:49 hagger Exp $
 */
package net.java.dev.cejug.classifieds.server.ejb3.bean;

import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

public abstract class AbstractOperation {

	protected CategoryEntity fillCategoryEntity(
			final AdvertisementCategory advCategory) {
		CategoryEntity category = new CategoryEntity();
		category.setId(advCategory.getId());
		category.setDescripton(advCategory.getDescription());
		category.setName(advCategory.getName());
		return category;
	}

	protected AdvertisementCategory fillAdvertisementCategory(
			final CategoryEntity entity) {
		AdvertisementCategory cat = new AdvertisementCategory();
		cat.setId(entity.getId());
		cat.setDescription(entity.getDescripton());
		cat.setName(entity.getName());
		cat.setAvailable(entity.getAvailable());
		return cat;
	}
}
