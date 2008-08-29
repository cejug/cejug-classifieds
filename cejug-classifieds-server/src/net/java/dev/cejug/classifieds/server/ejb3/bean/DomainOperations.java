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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.DomainOperationsLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug.utils.AttributesCopier;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateDomainParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.DomainCollection;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
@Stateless
public class DomainOperations extends AbstractOperation implements
		DomainOperationsLocal {

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			DomainOperations.class.getName(), "i18n/log");

	@EJB
	private transient DomainFacadeLocal domainFacade;

	@Override
	public Domain createDomainOperation(final CreateDomainParam newDomain) {
		try {
			// TODO: review validation...
			Domain domain = newDomain.getDomain();
			DomainEntity entity = fillDomainEntity(domain);
			Collection<CategoryEntity> categories = new ArrayList<CategoryEntity>();
			if (domain.getAdvertisementCategory() != null) {
				AttributesCopier<AdvertisementCategory, CategoryEntity> categoryToEntity = new AttributesCopier<AdvertisementCategory, CategoryEntity>();
				for (AdvertisementCategory category : domain
						.getAdvertisementCategory()) {
					CategoryEntity categoryEntity = new CategoryEntity();
					categoryToEntity.copyValuesByAttributeNames(category,
							categoryEntity);
					categories.add(categoryEntity);
				}
			}
			entity.setCategories(categories);
			domainFacade.create(entity);
			return fillDomain(entity);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}

	@Override
	public ServiceStatus deleteDomainOperation(
			final DeleteDomainParam obsoleteDomain) {

		ServiceStatus status = new ServiceStatus();
		try {
			// TODO Check if the domain is being used, before deleting it
			domainFacade.delete(DomainEntity.class, obsoleteDomain
					.getPrimaryKey());

			status.setStatusCode(200);
			status.setDescription("1 domain deleted");

		} catch (Exception e) {
			logger.severe(e.getMessage());
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
		}
		return status;
	}

	@Override
	public DomainCollection readDomainBundleOperation() {

		// TODO: use the bundle request parameters as query filter.

		DomainCollection domainCollection = new DomainCollection();

		try {
			List<DomainEntity> domains = domainFacade
					.readAll(DomainEntity.class);
			if (domains != null) {
				for (DomainEntity domainEntity : domains) {
					Domain domain = new Domain();
					domain.setId(domainEntity.getId());
					domain.setBrand(domainEntity.getBrand());
					domain.setDomain(domainEntity.getDomainName());
					domain.setSharedQuota(domainEntity.getSharedQuota());

					if (domainEntity.getCategories() != null) {
						for (CategoryEntity entity : domainEntity
								.getCategories()) {
							AttributesCopier<CategoryEntity, AdvertisementCategory> entityToCategory = new AttributesCopier<CategoryEntity, AdvertisementCategory>();
							AdvertisementCategory category = new AdvertisementCategory();
							entityToCategory.copyValuesByAttributeNames(entity,
									category);
							domain.getAdvertisementCategory().add(category);
						}
					}

					domainCollection.getDomain().add(domain);
				}
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
		return domainCollection;
	}

	@Override
	public ServiceStatus updateDomainOperation(
			final UpdateDomainParam partialDomain) {
		ServiceStatus status = new ServiceStatus();
		try {
			DomainEntity domainEntity = new DomainEntity();
			Domain domain = partialDomain.getDomain();
			domainEntity.setId(domain.getId());
			domainEntity.setDomainName(domain.getDomain());
			domainEntity.setBrand(domain.getBrand());
			domainEntity.setSharedQuota(domain.isSharedQuota());

			Collection<CategoryEntity> categories = new ArrayList<CategoryEntity>();
			if (domain.getAdvertisementCategory() != null) {
				AttributesCopier<AdvertisementCategory, CategoryEntity> categoryToEntity = new AttributesCopier<AdvertisementCategory, CategoryEntity>();
				for (AdvertisementCategory category : domain
						.getAdvertisementCategory()) {
					CategoryEntity categoryEntity = new CategoryEntity();
					categoryToEntity.copyValuesByAttributeNames(category,
							categoryEntity);
					categories.add(categoryEntity);
				}
			}
			domainEntity.setCategories(categories);

			domainFacade.update(domainEntity);

			status.setStatusCode(200);
			status.setDescription("1 domain updated");

		} catch (Exception e) {
			logger.severe(e.getMessage());
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
		}
		return status;
	}

}
