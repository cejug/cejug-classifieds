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
package net.java.dev.cejug.classifieds.service.endpoint;

import java.util.Collections;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementTypeOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.CategoryOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.CheckMonitorOperationLocal;
import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsAdminLocal;
import net.java.dev.cejug.classifieds.business.interfaces.ClassifiedsAdminRemote;
import net.java.dev.cejug.classifieds.business.interfaces.DomainOperationsLocal;
import net.java.dev.cejug.classifieds.service.endpoint.impl.CategoryOperations;
import net.java.dev.cejug.classifieds.service.interceptor.TimerInterceptor;
import net.java.dev.cejug_classifieds.metadata.admin.AddQuotaInfo;
import net.java.dev.cejug_classifieds.metadata.admin.CancelQuotaInfo;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateDomainParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.CreateCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.CustomerCollection;
import net.java.dev.cejug_classifieds.metadata.common.DeleteCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.DomainCollection;
import net.java.dev.cejug_classifieds.metadata.common.ReadCustomerBundleParam;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;
import net.java.dev.cejug_classifieds.metadata.common.UpdateCustomerParam;

/**
 * <strong>Decorator Pattern</strong>: this class is used to expose the
 * Administrative web-service while keeps logging, response time tracing and
 * other surrounding features. No busines code should be placed here, you find
 * all implementations in the package <code>.impl</code>.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 577 $ ($Date: 2008-09-03 18:57:21 +0200 (Wed, 03 Sep 2008) $)
 * @see <a * href=
 *      "http://java.sun.com/developer/technicalArticles/ebeans/ejb_30/#entity">
 *      * Writing Performant EJB Beans in the Java EE 5 Platform (EJB 3.0) Using
 *      * Annotations< /a>
 */
@Interceptors(TimerInterceptor.class)
@Stateless
@WebService(endpointInterface = "net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin", serviceName = "CejugClassifiedsServiceAdmin", portName = "CejugClassifiedsAdmin", targetNamespace = "http://cejug-classifieds.dev.java.net/admin")
public class AdminEndpointDecorator implements ClassifiedsAdminRemote,
		ClassifiedsAdminLocal {

	private static final String NOT_IMPLEMENTED = "operation not yet implemented";

	@EJB
	private CheckMonitorOperationLocal checkMonitorImpl;

	@EJB
	private CategoryOperationsLocal crudCategory;

	@EJB
	private AdvertisementTypeOperationsLocal crudAdvType;

	@EJB
	DomainOperationsLocal crudDomain;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private final static Logger logger = Logger.getLogger(
			AdminEndpointDecorator.class.getName(), "i18n/log");

	public MonitorResponse checkMonitorOperation(final MonitorQuery monitor) {
		try {
			return checkMonitorImpl.checkMonitorOperation(monitor);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus addQuotaOperation(final AddQuotaInfo addQuotaRequest) {
		/*
		 * TODO: review completely this code.
		 * 
		 * ServiceStatus status = new ServiceStatus(); try { Quota
		 * requestedQuota = addQuotaRequest.getQuota(); CustomerEntity customer
		 * = customerFacade.findOrCreate( requestedQuota.getDomainId(),
		 * requestedQuota .getCustomerLogin()); // Collection<QuotaEntity>
		 * customerQuotas = customer.getQuotas(); Collection<QuotaEntity>
		 * customerQuotas = new ArrayList<QuotaEntity>();
		 * 
		 * CrudImpl<AdvertisementTypeEntity, AdvertisementType> crud = new
		 * CrudImpl<AdvertisementTypeEntity, AdvertisementType>( new
		 * AdvertisementTypeAdapter(), new AdvertisementTypeFacade());
		 * AdvertisementTypeEntity type = crud.read(requestedQuota
		 * .getAdvertisementTypeId());
		 * 
		 * boolean newQuota = true; for (Iterator<QuotaEntity> iterator =
		 * customerQuotas.iterator(); iterator .hasNext();) { QuotaEntity entity
		 * = iterator.next(); if (entity.getType().equals(type)) {
		 * entity.setAmount(entity.getAvailable() + 1); newQuota = false; } } if
		 * (newQuota) { QuotaEntity quota = new QuotaEntity();
		 * quota.setAdvertisementTypeId(type.getId()); quota.setAmount(1);
		 * quota.setCustomerLogin(customer.getLogin());
		 * quota.setDomainId(customer.getDomainId()); customerQuotas.add(quota);
		 * } customerFacade.update(customer); status.setStatusCode(200); status
		 * .setDescription("1 {0} quota added to customer {1} of domain {2}");
		 * logger.finest(
		 * "TODO: fix logging... 1 {0} quota added to customer {1} of domain {2}"
		 * ); } catch (Exception e) { logger.severe(e.getMessage());
		 * status.setStatusCode(500); status.setDescription(e.getMessage()); }
		 * return status;
		 */
		return null;
	}

	public ServiceStatus cancelQuotaOperation(final CancelQuotaInfo request) {
		// TODO Auto-generated method stub
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public AdvertisementType createAdvertisementTypeOperation(
			final CreateAdvertisementTypeParam newAdvType) {
		try {
			return crudAdvType.create(newAdvType.getAdvertisementType());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public AdvertisementCategory createCategoryOperation(
			final CreateCategoryParam param) {
		try {
			return crudCategory.create(param.getAdvertisementCategory());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public Domain createDomainOperation(final CreateDomainParam param) {
		try {
		  System.out.println("crudDomaincrudDomaincrudDomaincrudDomaincrudDomain-> " + crudDomain);
			return crudDomain.create(param.getDomain());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}

	public ServiceStatus deleteCategoryOperation(
			final DeleteCategoryParam obsoleteCategory) {
		try {
			return crudCategory.delete(obsoleteCategory.getPrimaryKey());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus deleteDomainOperation(
			final DeleteDomainParam obsoleteDomain) {
		return crudDomain.delete(obsoleteDomain.getPrimaryKey());
	}

	public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(
			final BundleRequest bundleRequest) {
		try {
			AdvertisementTypeCollection collection = new AdvertisementTypeCollection();
			Collections.copy(collection.getAdvertisementType(), crudAdvType
					.readBundleOperation(bundleRequest));
			return collection;

		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public DomainCollection readDomainBundleOperation(
			BundleRequest bundleRequest) {
		try {
			DomainCollection collection = new DomainCollection();
			Collections.copy(crudDomain.readBundleOperation(bundleRequest),
					collection.getDomain());
			return collection;

		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus updateAdvertisementTypeOperation(
			final UpdateAdvertisementTypeParam partialAdvType) {
		try {
			return crudAdvType.update(partialAdvType.getAdvertisementType());

		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus updateCategoryOperation(final UpdateCategoryParam param) {
		try {
			CategoryOperationsLocal crudCategory = new CategoryOperations();
			return crudCategory.update(param.getAdvertisementCategory());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus updateDomainOperation(
			final UpdateDomainParam partialDomain) {
		try {
			return crudDomain.update(partialDomain.getDomain());
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus deleteAdvertisementTypeOperation(final long id) {
		try {
			return crudAdvType.delete(id);

		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus createCustomerOperation(
			final CreateCustomerParam newCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public ServiceStatus deleteCustomerOperation(
			final DeleteCustomerParam obsoleteCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public CustomerCollection readCustomerBundleOperation(
			final ReadCustomerBundleParam getCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public ServiceStatus updateCustomerOperation(
			final UpdateCustomerParam partialCustomer) {
		// TODO
		throw new WebServiceException(NOT_IMPLEMENTED);
	}

	public CategoryCollection readCategoryBundleOperation(
			BundleRequest bundleRequest) {
		CategoryOperationsLocal crudCategory = new CategoryOperations();
		CategoryCollection collection = new CategoryCollection();
		Collections.copy(collection.getAdvertisementCategory(), crudCategory
				.readBundleOperation(bundleRequest));
		return collection;
	}
}
