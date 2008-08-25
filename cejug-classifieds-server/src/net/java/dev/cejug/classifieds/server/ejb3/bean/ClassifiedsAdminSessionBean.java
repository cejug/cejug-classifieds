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

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.AdvertisementTypeOperationsLocal;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.CategoryOperationsLocal;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.CheckMonitorOperationLocal;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminLocal;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.ClassifiedsAdminRemote;
import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.DomainOperationsLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementTypeEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.QuotaEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.interceptor.TimerInterceptor;
import net.java.dev.cejug_classifieds.metadata.admin.AddQuotaInfo;
import net.java.dev.cejug_classifieds.metadata.admin.CancelQuotaInfo;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;
import net.java.dev.cejug_classifieds.metadata.admin.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateDomainParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.CategoryCollection;
import net.java.dev.cejug_classifieds.metadata.common.CreateCustomerParam;
import net.java.dev.cejug_classifieds.metadata.common.CustomerCollection;
import net.java.dev.cejug_classifieds.metadata.common.DeleteCustomerParam;
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
	  private transient AdvertisementTypeFacadeLocal advTypeFacade;

	@EJB
	private transient CustomerFacadeLocal customerFacade;

	@EJB
	private transient AdvertisementTypeOperationsLocal crudAdvType;

        @EJB
        private transient CategoryOperationsLocal crudCategory;
	       
        @EJB
        private transient DomainOperationsLocal crudDomain;
        
	@EJB
	private transient CheckMonitorOperationLocal checkMonitorImpl;

	/**
	 * the global log manager, used to al low third party services to override
	 * the defult logger.
	 */
	private final static Logger logger = Logger.getLogger(
			ClassifiedsAdminSessionBean.class.getName(), "i18n/log");

	@Override
	public MonitorResponse checkMonitorOperation(final MonitorQuery monitor) {
	  return checkMonitorImpl.checkMonitorOperation(monitor);
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
			logger.severe(e.getMessage());
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
	  return crudAdvType.createAdvertisementTypeOperation(newAdvType);
	}

	@Override
	public ServiceStatus createCategoryOperation(
			final CreateCategoryParam newCategory) {
	  return crudCategory.createCategoryOperation(newCategory);
	}

	@Override
	public ServiceStatus createDomainOperation(final CreateDomainParam newDomain) {
	  return crudDomain.createDomainOperation(newDomain);
	}

	@Override
	public ServiceStatus deleteCategoryOperation(
			final DeleteCategoryParam obsoleteCategory) {
	  return crudCategory.deleteCategoryOperation(obsoleteCategory);
	}

	@Override
	public ServiceStatus deleteDomainOperation(
			final DeleteDomainParam obsoleteDomain) {
	  return crudDomain.deleteDomainOperation(obsoleteDomain);
	}

	@Override
	public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(
			final ReadAdvertisementTypeBundleParam getAdvertisementTypes) {
	  return crudAdvType.readAdvertisementTypeBundleOperation(getAdvertisementTypes);
	}

	@Override
	public DomainCollection readDomainBundleOperation() {
	  return crudDomain.readDomainBundleOperation();
	}

	@Override
	public ServiceStatus updateAdvertisementTypeOperation(
			final UpdateAdvertisementTypeParam partialAdvType) {
	  return crudAdvType.updateAdvertisementTypeOperation(partialAdvType);
	}

	@Override
	public ServiceStatus updateCategoryOperation(
			final UpdateCategoryParam partialCategory) {
	  return crudCategory.updateCategoryOperation(partialCategory);
	}

	@Override
	public ServiceStatus updateDomainOperation(
			final UpdateDomainParam partialDomain) {
	  return crudDomain.updateDomainOperation(partialDomain);
	}

	@Override
	public ServiceStatus deleteAdvertisementTypeOperation(final int id) {
	  return crudAdvType.deleteAdvertisementTypeOperation(id);
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

        @Override
        public CategoryCollection readCategoryBundleOperation(BundleRequest bundleRequest) {
          return crudCategory.readCategoryBundleOperation(bundleRequest);
        }
}
