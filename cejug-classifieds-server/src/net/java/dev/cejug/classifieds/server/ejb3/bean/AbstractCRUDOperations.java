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
import java.util.List;
import java.util.logging.Logger;

import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.EntityFacade;
import net.java.dev.cejug_classifieds.metadata.common.AbstractEntity;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
public abstract class AbstractCRUDOperations<E extends AbstractEntity, T extends AbstractEntity> {

	/**
	 * The default logger.
	 */
	private Logger logger = null;

	/** The type of the entities. */
	private final Class<E> ec;

	AbstractCRUDOperations(Class<E> ec) {
		this.ec = ec;
		logger = Logger.getLogger("AbstractCRUDOperations<" + ec.getName()
				+ ">", "i18n/log");
	}

	/**
	 * The generic facade cannot be instantiated from generic types, so the
	 * subclasses should provide the persistence facade to the crud
	 * implementation.
	 * 
	 * @return an entity facade for persistence of types <em>E</em>
	 */
	abstract EntityFacade<E> getEntityFacade();

	@SuppressWarnings("unchecked")
	public T create(final T type) {
		try {
			// TODO: review validation...
			getEntityFacade().create((E) type);
			return type;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public ServiceStatus delete(final Long id) {

		ServiceStatus status = new ServiceStatus();
		try {
			// TODO Check if the domain is being used, before deleting it
			getEntityFacade().delete(ec, id);

			status.setStatusCode(200);
			status.setDescription("1 domain deleted");

		} catch (Exception e) {
			logger.severe(e.getMessage());
			status.setStatusCode(500);
			status.setDescription(e.getMessage());
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	public List<T> readBundleOperation(BundleRequest bundleRequest) {
		// TODO: use the bundle request parameters as query filter.

		List<T> domainCollection = new ArrayList<T>();

		try {
			List<E> entities = getEntityFacade().readAll(ec);
			if (entities != null) {
				for (E domainEntity : entities) {
					domainCollection.add((T) domainEntity);
				}
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
		return domainCollection;
	}

	@SuppressWarnings("unchecked")
	public ServiceStatus update(final T type) {
		ServiceStatus status = new ServiceStatus();
		try {
			getEntityFacade().update((E) type);
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
