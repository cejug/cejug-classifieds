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

import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementAdapterLocal;
import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementOperationsLocal;
import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
@Stateless
public class AdvertisementOperations implements AdvertisementOperationsLocal {

	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;

	@EJB
	AdvertisementAdapterLocal advAdapter;
	
	/**
	 * Persistence façade of Advertisement Type entities.
	 * 
	 * @EJB private transient AdvertisementTypeFacadeLocal advTypeFacade;
	 */

	@EJB
	private transient CustomerFacadeLocal customerFacade;

	// @EJB private transient CategoryFacadeLocal categoryFacade;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			AdvertisementOperations.class.getName(), "i18n/log");

	public AdvertisementCollection loadAdvertisementOperation(
			final AdvertisementCollectionFilter filter) {

		// TODO: load advertisements from timetable.... filtering with periods,
		// etc..

		try {
			AdvertisementCollection collection = new AdvertisementCollection();
			List<AdvertisementEntity> entities = advFacade.readAll();
			for (AdvertisementEntity entity : entities) {
				collection.getAdvertisement().add(advAdapter.toSoap(entity));
			}
			return collection;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus publishOperation(final Advertisement advertisement,
			final PublishingHeader header) {

		// TODO: to implement the real code.
		try {
			// TODO: re-think a factory to reuse adapters...
			/*
			 * // loading customer Map<String, String> params = new
			 * HashMap<String, String>(); params.clear(); params.put("d",
			 * header.getCustomerDomain()); params.put("l",
			 * header.getCustomerLogin());
			 */
			CustomerEntity customer = customerFacade.findOrCreate(header
					.getCustomerDomainId(), header.getCustomerLogin());
			AdvertisementEntity entity = advAdapter.toEntity(advertisement);
			entity.setCustomer(customer);
			advFacade.create(entity);

			ServiceStatus status = new ServiceStatus();
			status.setDescription("OK");
			status.setStatusCode(202);

			status.setTimestamp(GregorianCalendar.getInstance());

			return status;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}
}
