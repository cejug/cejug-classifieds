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
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementType;
import net.java.dev.cejug.classifieds.server.generated.contract.CancelQuotaInfo;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsAdmin;
import net.java.dev.cejug.classifieds.server.generated.contract.Domain;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;

/**
 * Cejug-Classifieds-Service delegates its behaviour to an underneath
 * implementation. You can configure the implementation type in the system
 * properties file. If you don't inform the qualified name of the service
 * implementation, the reference implementation will be used.
 * 
 * @see <a
 *      href='http://java.sun.com/blueprints/corej2eepatterns/Patterns/BusinessDelegate.html'>Core
 *      J2EE Patterns - Business Delegate</a>
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@WebService(endpointInterface = "net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsAdmin")
public class ClassifiedsAdminDelegate implements CejugClassifiedsAdmin {
	/*
	 * http://weblogs.java.net/blog/ramapulavarthi/archive/2007/12/extend_your_web.html
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
	public ServiceStatus requestDomainOperation(Domain domain) {
		try {
			// TODO: logging....
			return implementation.requestDomainOperation(domain);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public ServiceStatus addQuotaOperation(AddQuotaInfo addQuotaRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus cancelQuotaOperation(CancelQuotaInfo cancelQuotaRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus requestAdvertisementTypeOperation(
			AdvertisementType advType) {
		try {
			// TODO: logging....
			return implementation.requestAdvertisementTypeOperation(advType);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}
}
