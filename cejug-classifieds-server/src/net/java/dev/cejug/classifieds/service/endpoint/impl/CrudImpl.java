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

import java.util.ArrayList;
import java.util.List;

import net.java.dev.cejug.classifieds.adapter.SoapOrmAdapter;
import net.java.dev.cejug.classifieds.entity.AbstractEntity;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;
import net.java.dev.cejug.classifieds.service.ejb.interfaces.CRUDLocal;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.MessageElement;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
public class CrudImpl<E extends AbstractEntity<? extends T>, T extends MessageElement>
		implements CRUDLocal<T> {

	/** The adapter between SOAP serializable objects and the entities. */
	private final SoapOrmAdapter<T, E> adapter;

	/** The persistence facade. */
	private final EntityFacade<E> facade;

	/**
	 * 
	 * @param adapter
	 *            the adapter between the entity type <strong>E</strong> and the
	 *            SOAP serializable type <strong>T</strong>.
	 * @param facade
	 */
	public CrudImpl(SoapOrmAdapter<T, E> adapter, EntityFacade<E> facade) {
		this.adapter = adapter;
		this.facade = facade;
	}

	/** {@inheritDoc} */
	@Override
	public T create(final T type) {
		// TODO: review validation...
		facade.create(adapter.toEntity(type));
		return type;
	}

	/** {@inheritDoc} */
	@Override
	public ServiceStatus delete(final long id) {
		ServiceStatus status = new ServiceStatus();
		E entity = facade.read(id);
		facade.delete(entity);
		// TODO: define the enumeration of the response codes.
		status.setStatusCode(200);
		status.setDescription("TODO: create i18n messages.. 1 domain deleted");
		return status;
	}

	/** {@inheritDoc} */
	@Override
	public List<T> readBundleOperation(BundleRequest bundleRequest) {
		// TODO: use the bundle request parameters as query filter.

		List<T> domainCollection = new ArrayList<T>();

		/*
		 * TODO: try { List<E> entities = getEntityFacade().readAll(ec); if
		 * (entities != null) { for (E domainEntity : entities) {
		 * domainCollection.add((T) domainEntity); } } } catch (Exception e) {
		 * logger.severe(e.getMessage()); throw new WebServiceException(e); }
		 */
		return domainCollection;
	}

	/** {@inheritDoc} */
	@Override
	public ServiceStatus update(final T type) {
		ServiceStatus status = new ServiceStatus();
		facade.update(adapter.toEntity(type));
		status.setStatusCode(200);
		status.setDescription("1 domain updated");
		return status;
	}

	/** {@inheritDoc} */
	@Override
	public T read(long id) {
		return adapter.toSoap(facade.read(id));
	}
}