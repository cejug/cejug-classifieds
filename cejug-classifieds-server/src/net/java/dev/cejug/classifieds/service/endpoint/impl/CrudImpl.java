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
import net.java.dev.cejug.classifieds.business.interfaces.CRUDLocal;
import net.java.dev.cejug.classifieds.entity.AbstractEntity;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.MessageElement;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
public abstract class CrudImpl<E extends AbstractEntity<? extends T>, T extends MessageElement>
		implements CRUDLocal<T> {

	protected abstract EntityFacade<E> getFacade();

	protected abstract SoapOrmAdapter<T, E> getAdapter();

	/** {@inheritDoc} */
	public T create(final T type) {
		// TODO: review validation...
		E entity = getAdapter().toEntity(type);
		getFacade().create(entity);
		return getAdapter().toSoap(entity);
	}

	/** {@inheritDoc} */
	public ServiceStatus delete(final long id) {
		ServiceStatus status = new ServiceStatus();
		E entity = getFacade().read(id);
		getFacade().delete(entity);
		// TODO: define the enumeration of the response codes.
		status.setStatusCode(200);
		status.setDescription("TODO: create i18n messages.. 1 domain deleted");
		return status;
	}

	/** {@inheritDoc} */
	public List<T> readBundleOperation(BundleRequest bundleRequest) {
		List<E> entities = getFacade().readAll();
		List<T> bundle = new ArrayList<T>(entities.size());
		SoapOrmAdapter<T, E> adapter = getAdapter();
		for (E entity : entities) {
			bundle.add(adapter.toSoap(entity));
		}
		return bundle;
	}

	/** {@inheritDoc} */
	public ServiceStatus update(final T type) {
		ServiceStatus status = new ServiceStatus();
		E entity = getAdapter().toEntity(type);
		getFacade().update(entity);
		status.setStatusCode(200);
		status.setDescription("1 domain updated");
		return status;
	}

	/** {@inheritDoc} */
	public T read(long id) {
		return getAdapter().toSoap(getFacade().read(id));
	}
}
