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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.transaction.TransactionRequiredException;

import net.java.dev.cejug.classifieds.business.interfaces.CustomerAdapterLocal;
import net.java.dev.cejug.classifieds.business.interfaces.CustomerOperationsLocal;
import net.java.dev.cejug.classifieds.business.interfaces.SoapOrmAdapter;
import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.EntityFacade;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 1133 $ ($Date: 2009-01-26 20:49:07 +0100 (Mon, 26 Jan 2009) $)
 */
@Stateless
public class CustomerOperations extends
		AbstractCrudImpl<CustomerEntity, Customer> implements
		CustomerOperationsLocal {

	/**
	 * The persistence facade for Customer entites.
	 * 
	 * @see CustomerFacadeLocal
	 */
	@EJB
	private transient CustomerFacadeLocal facade;

	/**
	 * The adapter between Customer and CustomerEntity.
	 * 
	 * @see CustomerAdapterLocal
	 */
	@EJB
	private transient CustomerAdapterLocal adapter;

	@Override
	public Customer findOrCreate(long domainId, String login)
			throws EntityExistsException, TransactionRequiredException,
			IllegalStateException, IllegalArgumentException {
		CustomerEntity entity = facade.findOrCreate(domainId, login);
		return adapter.toSoap(entity);
	}

	/**
	 * @see #findOrCreate(long, String)
	 * @return <code>findOrCreate(type.getDomainId(), type.getLogin());</code>
	 * @throws IllegalArgumentException
	 * @throws IllegalStateException
	 * @throws TransactionRequiredException
	 * @throws EntityExistsException
	 */
	@Override
	public Customer create(Customer type) throws EntityExistsException,
			TransactionRequiredException, IllegalStateException,
			IllegalArgumentException {
		return findOrCreate(type.getDomainId(), type.getLogin());
	}

	@Override
	protected SoapOrmAdapter<Customer, CustomerEntity> getAdapter() {
		return adapter;
	}

	@Override
	protected EntityFacade<CustomerEntity> getFacade() {
		return facade;
	}
}
