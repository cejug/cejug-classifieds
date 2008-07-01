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

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementTypeEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.QuotaEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.interceptor.TimerInterceptor;
import net.java.dev.cejug.classifieds.server.generated.contract.AddQuotaInfo;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementCategory;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementType;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementTypeCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.CancelQuotaInfo;
import net.java.dev.cejug.classifieds.server.generated.contract.CategoryCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.Domain;
import net.java.dev.cejug.classifieds.server.generated.contract.DomainCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.Quota;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;

/**
 * //
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 * @see <a * href=
 *      "http://java.sun.com/developer/technicalArticles/ebeans/ejb_30/#entity"
 *      >Writing * Performant EJB Beans in the Java EE 5 Platform (EJB 3.0)
 *      Using * Annotations< /a>
 */
@Interceptors(TimerInterceptor.class)
@Stateless
public class ClassifiedsAdminSessionBean implements ClassifiedsAdminRemote {

	@EJB
	private DomainFacadeLocal domainFacade;

	@EJB
	private CustomerFacadeLocal customerFacade;

	@EJB
	private AdvertisementTypeFacadeLocal advTypeFacade;

	private final DatatypeFactory factory;

	@EJB
	private CategoryFacadeLocal categoryFacade;

	/**
	 * the global log manager, used to al low third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(
			ClassifiedsAdminSessionBean.class.getName(), "i18n/log");

	public ClassifiedsAdminSessionBean() {

		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public MonitorResponse checkMonitorOperation(MonitorQuery monitor) {

		// TODO: implement the real database call and response assembly.
		MonitorResponse response = new MonitorResponse();
		response.setServiceName("Cejug-Classifieds");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.roll(Calendar.DAY_OF_MONTH, -3);
		response.setOnlineSince(factory.newXMLGregorianCalendar(calendar));
		return response;
	}

	@Override
	public ServiceStatus addQuotaOperation(AddQuotaInfo addQuotaRequest) {

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
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status
					.setDescription("1 {0} quota added to customer {1} of domain {2}");
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
	public ServiceStatus cancelQuotaOperation(CancelQuotaInfo cancelQuotaRequest) {
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus createAdvertisementTypeOperation(
			AdvertisementType advertisementType) {

		try {
			AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();
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
	public ServiceStatus createCategoryOperation(AdvertisementCategory category) {
		CategoryEntity entity = new CategoryEntity();
		entity.setDescripton(category.getDescription());
		entity.setName(category.getName());
		try {
			categoryFacade.create(entity);

			// TODO: create a generic status response in the super class...
			ServiceStatus status = new ServiceStatus();
			status.setStatusCode(200);
			status.setDescription("1 advertisement type added");
			return status;
		} catch (Exception e) {
			// TODO log....
			e.printStackTrace();
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus createDomainOperation(Domain domain) {
		try {
			// TODO: review validation...
			DomainEntity entity = new DomainEntity();
			entity.setDomainName(domain.getDomain());
			entity.setSharedQuota(false);
			entity.setBrand(domain.getBrand());
			domainFacade.create(entity);
		} catch (Exception e) {
			// TODO Logging....
			throw new WebServiceException(e);
		}

		return new ServiceStatus();
	}

	@Override
	public CategoryCollection readAllCategoriesOperation() {
		CategoryCollection categoryCollection = new CategoryCollection();
		System.out.println("LLLLL - "
				+ categoryCollection.getAdvertisementCategory());
		try {
			List<CategoryEntity> categories = categoryFacade
					.readAll(CategoryEntity.class);
			if (categories != null) {
				for (CategoryEntity category : categories) {
					AdvertisementCategory cat = new AdvertisementCategory();
					cat.setDescription(category.getDescripton());
					cat.setName(category.getName());
					categoryCollection.getAdvertisementCategory().add(cat);
				}
			}
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
		System.out.println("SIZE: "
				+ categoryCollection.getAdvertisementCategory().size());
		return categoryCollection;
	}

	@Override
	public DomainCollection readAllDomainsOperation() {
		// TODO Auto-generated method stub
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus updateAdvertisementTypeOperation(
			AdvertisementType advertisementType) {
		// TODO Auto-generated method stub
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus updateCategoryOperation(AdvertisementCategory category) {
		// TODO Auto-generated method stub
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus updateDomainOperation(Domain domain) {
		// TODO Auto-generated method stub
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus deleteAdvertisementTypeOperation(int id) {
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus deleteCategoryOperation(int id) {
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus deleteDomainOperation(int id) {
		throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public AdvertisementTypeCollection readAllAdvertisementTypesOperation() {
		throw new WebServiceException("operation not yet implemented");
	}
}
