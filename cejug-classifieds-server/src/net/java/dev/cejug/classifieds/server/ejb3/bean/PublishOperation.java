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
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.PublishOperationLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementTypeEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Stateless
public class PublishOperation implements PublishOperationLocal {

	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;
	/**
	 * Persistence façade of Advertisement Type entities.
	 */
	@EJB
	private transient AdvertisementTypeFacadeLocal advTypeFacade;

	/**
	 * Persistence façade of Customer entities.
	 */
	@EJB
	private transient CustomerFacadeLocal customerFacade;

	/**
	 * Persistence façade of Category entities.
	 */
	@EJB
	private transient CategoryFacadeLocal categoryFacade;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			PublishOperation.class.getName(), "i18n/log");

	@Override
	public ServiceStatus publishOperation(final Advertisement advertisement,
			final PublishingHeader header) {

		// TODO: to implement the real code.
		try {
			/*
			 * // loading customer Map<String, String> params = new
			 * HashMap<String, String>(); params.clear(); params.put("d",
			 * header.getCustomerDomain()); params.put("l",
			 * header.getCustomerLogin());
			 */
			CustomerEntity customer = customerFacade.findOrCreate(header
					.getCustomerDomainId(), header.getCustomerLogin());

			// validating advertisement PIN
			AdvertisementEntity entity = new AdvertisementEntity();
			// entity.setKeywords(advertisement.getKeywords()); // TODO
			entity.setText(advertisement.getFullText());

			entity.setCustomer(customer);

			AdvertisementTypeEntity type = advTypeFacade.read(
					AdvertisementTypeEntity.class, advertisement.getTypeId());
			entity.setType(type);

			entity.setSummary(advertisement.getShortDescription());
			entity.setTitle(advertisement.getHeadline());

			CategoryEntity category = categoryFacade.read(CategoryEntity.class,
					advertisement.getCategoryId());
			entity.setCategory(category);

			Calendar start = Calendar.getInstance();
			Calendar finish = Calendar.getInstance();
			finish.add(Calendar.HOUR, 3);
			entity.setStart(start);
			entity.setFinish(finish);
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
