/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
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
package net.java.dev.cejug.classifieds.adapter;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.CategoryAdapterLocal;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
@Stateless
public class CategoryAdapter implements CategoryAdapterLocal {

        /** {@inheritDoc} */
	public CategoryEntity toEntity(AdvertisementCategory advCategory)
			throws IllegalStateException, IllegalArgumentException {

		CategoryEntity entity = new CategoryEntity();
		entity.setId(advCategory.getEntityId());
		entity.setDescription(advCategory.getDescription());
		entity.setName(advCategory.getName());

		AdvertisementCategory parent = advCategory.getAdvertisementCategory();
		if (parent != null) {
			entity.setParent(toEntity(parent));
		}
		return entity;
	}

	/** {@inheritDoc} */
	public AdvertisementCategory toSoap(CategoryEntity entity)
			throws IllegalStateException, IllegalArgumentException {

		AdvertisementCategory soapCategory = new AdvertisementCategory();
		soapCategory.setEntityId(entity.getId());
		soapCategory.setDescription(entity.getDescription());
		soapCategory.setName(entity.getName());
		soapCategory.setAvailable(entity.getAvailable());

		if (entity.getParent() != null) {
			soapCategory.setAdvertisementCategory(toSoap(entity.getParent()));
		}
		return soapCategory;
	}
}
