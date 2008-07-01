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
package net.java.dev.cejug.classifieds.server;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.ClassifiedsAdminRemote;
import net.java.dev.cejug.classifieds.server.generated.contract.AddQuotaInfo;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementTypeCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.CancelQuotaInfo;
import net.java.dev.cejug.classifieds.server.generated.contract.CategoryCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsAdmin;
import net.java.dev.cejug.classifieds.server.generated.contract.CreateAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.generated.contract.CreateCategoryParam;
import net.java.dev.cejug.classifieds.server.generated.contract.CreateDomainParam;
import net.java.dev.cejug.classifieds.server.generated.contract.DeleteCategoryParam;
import net.java.dev.cejug.classifieds.server.generated.contract.DeleteDomainParam;
import net.java.dev.cejug.classifieds.server.generated.contract.DomainCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug.classifieds.server.generated.contract.ReadCategoryBundleParam;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.UpdateAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.generated.contract.UpdateCategoryParam;
import net.java.dev.cejug.classifieds.server.generated.contract.UpdateDomainParam;

/**
 * Cejug-Classifieds-Service delegates its behaviour to an underneath
 * implementation. You can configure the implementation type in the system
 * properties file. If you don't inform the qualified name of the service
 * implementation, the reference implementation will be used.
 * 
 * @see <a href=
 *      'http://java.sun.com/blueprints/corej2eepatterns/Patterns/BusinessDelega
 *      t e . h t m l ' > C o r e J2EE Patterns - Business Delegate</a>
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@WebService(endpointInterface = "net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsAdmin")
public class ClassifiedsAdminDelegate implements CejugClassifiedsAdmin {
	/*
	 * http://weblogs.java.net/blog/ramapulavarthi/archive/2007/12/extend_your_web
	 * .html
	 */
	@Resource
	ClassifiedsAdminRemote implementation;

	/** The publisher logger. */
	private Logger logger = Logger.getLogger(CejugClassifiedsAdmin.class
			.getName(), "i18n/log");

	public ClassifiedsAdminDelegate() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			implementation = (ClassifiedsAdminRemote) ic
					.lookup(ClassifiedsAdminRemote.class.getName());
		} catch (NamingException e) {
			logger.severe(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MonitorResponse checkMonitorOperation(MonitorQuery monitor) {
		try {
			// TODO: logging....
			return implementation.checkMonitorOperation(monitor);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus addQuotaOperation(AddQuotaInfo addQuotaRequest) {
		try {
			// TODO: logging....
			return implementation.addQuotaOperation(addQuotaRequest);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus cancelQuotaOperation(CancelQuotaInfo cancelQuotaRequest) {
		try {
			// TODO: logging....
			return implementation.cancelQuotaOperation(cancelQuotaRequest);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus deleteAdvertisementTypeOperation(int id) {
		try {
			// TODO: logging....
			return implementation.deleteAdvertisementTypeOperation(id);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus createAdvertisementTypeOperation(
			CreateAdvertisementTypeParam newAdvType) {
		try {
			// TODO: logging....
			return implementation.createAdvertisementTypeOperation(newAdvType);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus createCategoryOperation(CreateCategoryParam newCategory) {
		try {
			// TODO: logging....
			return implementation.createCategoryOperation(newCategory);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus createDomainOperation(CreateDomainParam newDomain) {
		try {
			// TODO: logging....
			return implementation.createDomainOperation(newDomain);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus deleteCategoryOperation(
			DeleteCategoryParam obsoleteCategory) {
		try {
			// TODO: logging....
			return implementation.deleteCategoryOperation(obsoleteCategory);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus deleteDomainOperation(DeleteDomainParam obsoleteDomain) {
		try {
			// TODO: logging....
			return implementation.deleteDomainOperation(obsoleteDomain);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(
			ReadAdvertisementTypeBundleParam getAdvertisementTypes) {
		try {
			// TODO: logging....
			return implementation
					.readAdvertisementTypeBundleOperation(getAdvertisementTypes);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public CategoryCollection readCategoryBundleOperation(
			ReadCategoryBundleParam getCategories) {
		try {
			// TODO: logging....
			return implementation.readCategoryBundleOperation(getCategories);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public DomainCollection readDomainBundleOperation() {
		try {
			// TODO: logging....
			return implementation.readDomainBundleOperation();
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus updateAdvertisementTypeOperation(
			UpdateAdvertisementTypeParam partialAdvType) {
		try {
			// TODO: logging....
			return implementation
					.updateAdvertisementTypeOperation(partialAdvType);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus updateCategoryOperation(
			UpdateCategoryParam partialCategory) {
		try {
			// TODO: logging....
			return implementation.updateCategoryOperation(partialCategory);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus updateDomainOperation(UpdateDomainParam partialDomain) {
		try {
			// TODO: logging....
			return implementation.updateDomainOperation(partialDomain);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}
}
