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
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminLocal;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminRemote;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementTypeEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.QuotaEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.ServiceLifeCycleEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.ServiceLifeCycleFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.interceptor.TimerInterceptor;
import net.java.dev.cejug_classifieds.metadata.admin.AddQuotaInfo;
import net.java.dev.cejug_classifieds.metadata.admin.AlivePeriod;
import net.java.dev.cejug_classifieds.metadata.admin.CancelQuotaInfo;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;
import net.java.dev.cejug_classifieds.metadata.admin.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.ReadCategoryBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateDomainParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.CreateCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.CustomerCollection;
import net.java.dev.cejug_classifieds.metadata.common.DeleteCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.DomainCollection;
import net.java.dev.cejug_classifieds.metadata.common.Quota;
import net.java.dev.cejug_classifieds.metadata.common.ReadCustomerBundleParam;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;
import net.java.dev.cejug_classifieds.metadata.common.UpdateCustomerParam;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 * @see <a * href=
 *      "http://java.sun.com/developer/technicalArticles/ebeans/ejb_30/#entity">
 *      * Writing Performant EJB Beans in the Java EE 5 Platform (EJB 3.0) Using
 *      * Annotations< /a>
 */
@Interceptors(TimerInterceptor.class)
@Stateless
@WebService(endpointInterface = "net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin", serviceName = "CejugClassifiedsServiceAdmin", portName = "CejugClassifiedsAdmin", targetNamespace = "http://cejug-classifieds.dev.java.net/admin")
public class ClassifiedsAdminSessionBean implements ClassifiedsAdminRemote,
		ClassifiedsAdminLocal {

	private static final String NOT_IMPLEMENTED = "operation not yet implemented";

	@EJB
	private transient DomainFacadeLocal domainFacade;

	@EJB
	private transient CustomerFacadeLocal customerFacade;

	@EJB
	private transient AdvertisementTypeFacadeLocal advTypeFacade;

	@EJB
	private transient CategoryFacadeLocal categoryFacade;

	@EJB
	private transient ServiceLifeCycleFacadeLocal lifeCycleFacade;

	/**
	 * the global log manager, used to al low third party services to override
	 * the defult logger.
	 */
	private final static Logger logger = Logger.getLogger(
			ClassifiedsAdminSessionBean.class.getName(), "i18n/log");

	@Override
	public MonitorResponse checkMonitorOperation(final MonitorQuery monitor) {

		// TODO: implement the real database call and response assembly.
		MonitorResponse response;
		response = new MonitorResponse();
		response.setServiceName("Cejug-Classifieds");

		try {
			List<ServiceLifeCycleEntity> alivePeriods = lifeCycleFacade
					.readAll(ServiceLifeCycleEntity.class);
			List<AlivePeriod> periods = response.getAlivePeriods();
			for (ServiceLifeCycleEntity lifeCycle : alivePeriods) {
				AlivePeriod period = new AlivePeriod();
				period.setStart(lifeCycle.getStart());
				period.setFinish(lifeCycle.getFinish());
				period.setNote(lifeCycle.getName());
				periods.add(period);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WebServiceException(e);
		}

		return response;
	}

	@Override
	public ServiceStatus addQuotaOperation(final AddQuotaInfo addQuotaRequest) {
		ServiceStatus status = new ServiceStatus();
		try {
			Quota requestedQuota = addQuotaRequest.getQuota();
			CustomerEntity customer = customerFacade.findOrCreate(
					requestedQuota.getDomainId(), requestedQuota
							.getCustomerLogin());
			Collection<QuotaEntity> customerQuotas = customer.getQuotas();

			AdvertisementTypeEntity type = advTypeFacade.read(
					AdvertisementTypeEntity.class, Integer
							.valueOf(requestedQuota.getAdvertisementTypeId()));

			boolean newQuota = true;
			for (Iterator<QuotaEntity> iterator = customerQuotas.iterator(); iterator
					.hasNext();) {
				QuotaEntity entity = iterator.next();
				if (entity.getType().equals(type)) {
					entity.setAvailable(entity.getAvailable() + 1);
					newQuota = false;
				}
			}
			if (newQuota) {
				QuotaEntity quota = new QuotaEntity();
				quota.setType(type);
				quota.setAvailable(1);
				quota.setCustomer(customer);
				quota.setDomain(customer.getDomain());
				customerQuotas.add(quota);
			}
			customerFacade.update(customer);
			status.setStatusCode(200);
			status
					.setDescription("1 {0} quota added to customer {1} of domain {2}");
			logger
					.finest("TODO: fix logging... 1 {0} quota added to customer {1} of domain {2}");
		} catch (Exception e) {
			// TODO: log.............
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
		}
		return status;
	}

	@Override
	public ServiceStatus cancelQuotaOperation(final CancelQuotaInfo request) {
		// TODO Auto-generated method stub
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public ServiceStatus createAdvertisementTypeOperation(
			final CreateAdvertisementTypeParam newAdvType) {

		try {
			AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();
			AdvertisementType advertisementType = newAdvType
					.getAdvertisementType();
			advTypeEntity.setDescription(advertisementType.getDescription());
			advTypeEntity.setMaxAttachmentSize(advertisementType
					.getMaxAttachmentSize());
			advTypeEntity.setName(advertisementType.getName());
			advTypeEntity.setTextLength(advertisementType.getMaxTextLength());

			advTypeFacade.create(advTypeEntity);

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 advertisement type added");
			return status;
		} catch (Exception e) {
			// TODO: log.............
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}

	}

	@Override
	public ServiceStatus createCategoryOperation(
			final CreateCategoryParam newCategory) {

		AdvertisementCategory advertisementCategory = newCategory
				.getAdvertisementCategory();
		CategoryEntity category = fillCategoryEntity(advertisementCategory);
		AdvertisementCategory parent = advertisementCategory
				.getAdvertisementCategory();
		if (parent != null) {
			category.setParent(fillCategoryEntity(parent));
		}

		try {
			categoryFacade.create(category);

			// TODO: create a generic status response in the super class...
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 category added");
			return status;
		} catch (Exception e) {
			// TODO log....
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}

	}

	@Override
	public ServiceStatus createDomainOperation(final CreateDomainParam newDomain) {

		try {
			// TODO: review validation...
			DomainEntity entity = new DomainEntity();
			Domain domain = newDomain.getDomain();
			entity.setDomainName(domain.getDomain());
			entity.setSharedQuota(domain.isSharedQuota());
			entity.setBrand(domain.getBrand());
			Collection<CategoryEntity> categories = new ArrayList<CategoryEntity>();
			if (domain.getAdvertisementCategory() != null) {
				for (AdvertisementCategory category : domain
						.getAdvertisementCategory()) {
					CategoryEntity categoryEntity = fillCategoryEntity(category);
					categories.add(categoryEntity);
				}
			}
			entity.setCategories(categories);
			domainFacade.create(entity);

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 domain created");
			return status;
		} catch (Exception e) {
			// TODO Logging....
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}
	}

	@Override
	public ServiceStatus deleteCategoryOperation(
			final DeleteCategoryParam obsoleteCategory) {

		try {
			// TODO Check if the category is being used, before deleting it
			categoryFacade.delete(CategoryEntity.class, Integer
					.valueOf(obsoleteCategory.getPrimaryKey()));

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 category deleted");
			return status;

		} catch (Exception e) {
			// TODO: log.............
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}
	}

	@Override
	public ServiceStatus deleteDomainOperation(
			final DeleteDomainParam obsoleteDomain) {

		try {
			// TODO Check if the domain is being used, before deleting it
			domainFacade.delete(DomainEntity.class, obsoleteDomain
					.getPrimaryKey());

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 domain deleted");
			return status;

		} catch (Exception e) {
			// TODO: log.............
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}

	}

	@Override
	public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(
			final ReadAdvertisementTypeBundleParam getAdvertisementTypes) {

		// TODO: use the bundle request parameters as query filter.

		AdvertisementTypeCollection advTypeCollection = new AdvertisementTypeCollection();
		try {
			List<AdvertisementTypeEntity> advTypes = advTypeFacade
					.readAll(AdvertisementTypeEntity.class);
			if (advTypes != null) {
				for (AdvertisementTypeEntity advTypeEntity : advTypes) {
					AdvertisementType advType = new AdvertisementType();
					advType.setId(advTypeEntity.getId());
					advType.setDescription(advTypeEntity.getDescription());
					advType.setName(advTypeEntity.getName());
					advType.setMaxAttachmentSize(advTypeEntity
							.getMaxAttachmentSize());
					advType.setMaxTextLength(advTypeEntity.getTextLength());
					advTypeCollection.getAdvertisementType().add(advType);
				}
			}
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
		return advTypeCollection;
	}

	@Override
	public CategoryCollection readCategoryBundleOperation(
			final ReadCategoryBundleParam getCategories) {

		// TODO: use the bundle request parameters as query filter.

		CategoryCollection categoryCollection = new CategoryCollection();
		try {
			List<CategoryEntity> categories = categoryFacade
					.readAll(CategoryEntity.class);
			if (categories != null) {
				for (CategoryEntity category : categories) {
					AdvertisementCategory cat = fillAdvertisementCategory(category);
					if (category.getParent() != null) {
						cat
								.setAdvertisementCategory(fillAdvertisementCategory(category
										.getParent()));
					}
					categoryCollection.getAdvertisementCategory().add(cat);
				}
			}
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
		return categoryCollection;
	}

	private AdvertisementCategory fillAdvertisementCategory(
			final CategoryEntity entity) {

		AdvertisementCategory cat = new AdvertisementCategory();
		cat.setId(entity.getId());
		cat.setDescription(entity.getDescripton());
		cat.setName(entity.getName());
		cat.setAvailable(entity.getAvailable());
		return cat;
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
						for (CategoryEntity categoryEntity : domainEntity
								.getCategories()) {
							AdvertisementCategory category = fillAdvertisementCategory(categoryEntity);
							domain.getAdvertisementCategory().add(category);
						}
					}

					domainCollection.getDomain().add(domain);
				}
			}
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
		return domainCollection;
	}

	@Override
	public ServiceStatus updateAdvertisementTypeOperation(
			final UpdateAdvertisementTypeParam partialAdvType) {

		try {
			AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();
			AdvertisementType advertisementType = partialAdvType
					.getAdvertisementType();
			advTypeEntity.setId(advertisementType.getId());
			advTypeEntity.setDescription(advertisementType.getDescription());
			advTypeEntity.setName(advertisementType.getName());
			advTypeEntity.setMaxAttachmentSize(advertisementType
					.getMaxAttachmentSize());
			advTypeEntity.setTextLength(advertisementType.getMaxTextLength());

			advTypeFacade.update(advTypeEntity);

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 advertisement type updated");
			return status;

		} catch (Exception e) {
			// TODO: log.............
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}
	}

	@Override
	public ServiceStatus updateCategoryOperation(
			final UpdateCategoryParam partialCategory) {

		try {
			AdvertisementCategory advertisementCategory = partialCategory
					.getAdvertisementCategory();
			CategoryEntity category = fillCategoryEntity(advertisementCategory);
			AdvertisementCategory parent = advertisementCategory
					.getAdvertisementCategory();
			if (parent != null) {
				category.setParent(fillCategoryEntity(parent));
			}
			categoryFacade.update(category);

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 category updated");
			return status;

		} catch (Exception e) {
			// TODO: log.............
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}
	}

	private CategoryEntity fillCategoryEntity(
			AdvertisementCategory advertisementCategory) {

		CategoryEntity category = new CategoryEntity();
		category.setId(advertisementCategory.getId());
		category.setDescripton(advertisementCategory.getDescription());
		category.setName(advertisementCategory.getName());
		return category;
	}

	@Override
	public ServiceStatus updateDomainOperation(
			final UpdateDomainParam partialDomain) {

		try {
			DomainEntity domainEntity = new DomainEntity();
			Domain domain = partialDomain.getDomain();
			domainEntity.setId(domain.getId());
			domainEntity.setDomainName(domain.getDomain());
			domainEntity.setBrand(domain.getBrand());
			domainEntity.setSharedQuota(domain.isSharedQuota());

			Collection<CategoryEntity> categories = new ArrayList<CategoryEntity>();
			if (domain.getAdvertisementCategory() != null) {
				for (AdvertisementCategory category : domain
						.getAdvertisementCategory()) {
					CategoryEntity categoryEntity = fillCategoryEntity(category);
					categories.add(categoryEntity);
				}
			}
			domainEntity.setCategories(categories);

			domainFacade.update(domainEntity);

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 domain updated");
			return status;

		} catch (Exception e) {
			// TODO: log.............
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}
	}

	@Override
	public ServiceStatus deleteAdvertisementTypeOperation(final int id) {

		try {
			// TODO Check if the advertisement type is being used, before
			// deleting it
			advTypeFacade.delete(AdvertisementTypeEntity.class, Integer
					.valueOf(id));

			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 advertisement type deleted");
			return status;

		} catch (Exception e) {
			// TODO: log.............
			e.printStackTrace();
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
			return status;
		}
	}

	@Override
	public ServiceStatus createCustomerOperation(
			final CreateCustomerParam newCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public ServiceStatus deleteCustomerOperation(
			final DeleteCustomerParam obsoleteCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public CustomerCollection readCustomerBundleOperation(
			final ReadCustomerBundleParam getCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	@Override
	public ServiceStatus updateCustomerOperation(
			final UpdateCustomerParam partialCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}
}
