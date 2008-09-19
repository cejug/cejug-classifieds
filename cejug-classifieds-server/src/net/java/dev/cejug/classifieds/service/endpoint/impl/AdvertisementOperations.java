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

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.adapter.SoapOrmAdapter;
import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementAdapterLocal;
import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.CustomerAdapterLocal;
import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Stateless
public class AdvertisementOperations extends AbstractCrudImpl<AdvertisementEntity, Advertisement> implements
AdvertisementOperationsLocal {

	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;

        @EJB
        private transient CustomerFacadeLocal customerFacade;

        @EJB
	private transient AdvertisementAdapterLocal adapter;
        
        @EJB
        CustomerAdapterLocal customerAdapter;

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
				collection.getAdvertisement().add(adapter.toSoap(entity));
			}
			return collection;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public Advertisement publishOperation(final Advertisement advertisement,
			final PublishingHeader header) {
		try {
		        CustomerEntity customer = customerFacade.findOrCreate(header.getCustomerDomainId(), header.getCustomerLogin());
			advertisement.setCustomer(customerAdapter.toSoap(customer));
			return super.create(advertisement);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

        @Override
        protected EntityFacade<AdvertisementEntity> getFacade() {
                return advFacade;
        }

        @Override
        protected SoapOrmAdapter<Advertisement, AdvertisementEntity> getAdapter() {
                return adapter;
        }
}
