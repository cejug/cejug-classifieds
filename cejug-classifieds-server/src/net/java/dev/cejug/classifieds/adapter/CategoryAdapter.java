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

@Stateless
public class CategoryAdapter extends
		AbstractSoapOrmAdapter<AdvertisementCategory, CategoryEntity> 
          implements CategoryAdapterLocal {

	public CategoryEntity toEntity(AdvertisementCategory advCategory)
			throws IllegalStateException, IllegalArgumentException {

		CategoryEntity category = new CategoryEntity();
		category.setId(advCategory.getEntityId());
		category.setDescription(advCategory.getDescription());
		category.setName(advCategory.getName());

		AdvertisementCategory parent = advCategory.getAdvertisementCategory();
		if (parent != null) {
			category.setParent(toEntity(parent));
		}
		return category;
	}

	public AdvertisementCategory toSoap(CategoryEntity entity)
			throws IllegalStateException, IllegalArgumentException {

		AdvertisementCategory cat = new AdvertisementCategory();
		cat.setEntityId(entity.getId());
		cat.setDescription(entity.getDescription());
		cat.setName(entity.getName());
		cat.setAvailable(entity.getAvailable());

		if (entity.getParent() != null) {
			cat.setAdvertisementCategory(toSoap(entity.getParent()));
		}
		return cat;
	}
}
