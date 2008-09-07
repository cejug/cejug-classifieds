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
package net.java.dev.cejug.classifieds.service.endpoint.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.adapter.SoapOrmAdapter;
import net.java.dev.cejug.classifieds.business.interfaces.CategoryAdapterLocal;
import net.java.dev.cejug.classifieds.business.interfaces.CategoryOperationsLocal;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
@Stateless
public class CategoryOperations extends
		CrudImpl<CategoryEntity, AdvertisementCategory> implements
		CategoryOperationsLocal {

	@EJB
	CategoryFacadeLocal facade;

	@EJB
	CategoryAdapterLocal adapter;

	@Override
	protected SoapOrmAdapter<AdvertisementCategory, CategoryEntity> getAdapter() {
		return adapter;
	}

	@Override
	protected EntityFacade<CategoryEntity> getFacade() {
		return facade;
	}

	@Override
	public List<AdvertisementCategory> readBundleOperation(
			BundleRequest bundleRequest) {

		return super.readBundleOperation(bundleRequest);
	}
}
