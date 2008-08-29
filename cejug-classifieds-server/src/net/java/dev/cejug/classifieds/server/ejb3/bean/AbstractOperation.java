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

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

/**
 * S - source object (with the members values to be copied)
 * T - target object (with blank members) 
 */
public abstract class AbstractOperation {
/*
	protected AdvertisementCategory fillAdvertisementCategory(
			final CategoryEntity entity) {
		AdvertisementCategory cat = new AdvertisementCategory();
		cat.setId(entity.getId());
		cat.setDescription(entity.getDescription());
		cat.setName(entity.getName());
		cat.setAvailable(entity.getAvailable());
		return cat;
	}

	protected CategoryEntity fillCategoryEntity(
			final AdvertisementCategory advCategory) {
		CategoryEntity category = new CategoryEntity();
		category.setId(advCategory.getId());
		category.setDescription(advCategory.getDescription());
		category.setName(advCategory.getName());
		return category;
	}
*/
	protected Domain fillDomain(final DomainEntity entity) {
		Domain domain = new Domain();
		domain.setDomain(entity.getDomainName());
		domain.setSharedQuota(entity.getSharedQuota());
		domain.setBrand(entity.getBrand());
		domain.setId(entity.getId());
		return domain;
	}

	protected DomainEntity fillDomainEntity(final Domain domain) {
		DomainEntity entity = new DomainEntity();
		entity.setDomainName(domain.getDomain());
		entity.setSharedQuota(domain.isSharedQuota());
		entity.setBrand(domain.getBrand());
		entity.setId(domain.getId());
		return entity;
	}
}
