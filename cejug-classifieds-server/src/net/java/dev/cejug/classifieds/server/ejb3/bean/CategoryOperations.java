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
package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.CategoryOperationsLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
@Stateless
public class CategoryOperations extends AbstractOperation implements
		CategoryOperationsLocal {

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			CategoryOperations.class.getName(), "i18n/log");

	@EJB
	private transient CategoryFacadeLocal categoryFacade;

	@Override
	public AdvertisementCategory createCategoryOperation(
			final CreateCategoryParam newCategory) {

		AdvertisementCategory advCategory = newCategory
				.getAdvertisementCategory();
		CategoryEntity category = fillCategoryEntity(advCategory);
		AdvertisementCategory parent = advCategory.getAdvertisementCategory();
		if (parent != null) {
			category.setParent(fillCategoryEntity(parent));
		}

		try {
			categoryFacade.create(category);
			return fillAdvertisementCategory(category);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus deleteCategoryOperation(
			final DeleteCategoryParam obsoleteCategory) {

		ServiceStatus status = new ServiceStatus();
		try {
			// TODO Check if the category is being used, before deleting it
			categoryFacade.delete(CategoryEntity.class, Integer
					.valueOf(obsoleteCategory.getPrimaryKey()));

			status.setStatusCode(200);
			status.setDescription("1 category deleted");

		} catch (Exception e) {
			logger.severe(e.getMessage());
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
		}
		return status;
	}

	@Override
	public CategoryCollection readCategoryBundleOperation(
			final BundleRequest bundleRequest) {
		CategoryCollection categorySet;
		categorySet = new CategoryCollection();
		try {
			List<CategoryEntity> categories = categoryFacade
					.readAll(CategoryEntity.class);
			if (categories != null) {
				for (CategoryEntity category : categories) {
					categorySet.getAdvertisementCategory().add(
							fillAdvertisementCategory(category));
				}
			}
			logger.finest(categorySet.getAdvertisementCategory().size()
					+ "categories returned successfully");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
		return categorySet;
	}

	@Override
	public ServiceStatus updateCategoryOperation(
			final UpdateCategoryParam partialCategory) {

		ServiceStatus status = new ServiceStatus();
		try {
			AdvertisementCategory advCategory = partialCategory
					.getAdvertisementCategory();
			CategoryEntity category = fillCategoryEntity(advCategory);
			AdvertisementCategory parent = advCategory
					.getAdvertisementCategory();
			if (parent != null) {
				category.setParent(fillCategoryEntity(parent));
			}
			categoryFacade.update(category);

			status.setStatusCode(200);
			status.setDescription("1 category updated");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
		}
		return status;
	}
}
