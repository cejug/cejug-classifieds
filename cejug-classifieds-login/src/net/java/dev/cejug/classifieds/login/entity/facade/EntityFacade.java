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
package net.java.dev.cejug.classifieds.login.entity.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.login.entity.AbstractEntity;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 * 
 * @see CRUDEntityFacade
 * @see <a href=
 *      'http://en.wikipedia.org/wiki/Create%2C_read%2C_update_and_delete'>CRUD @
 *      Wikipedia.</a>
 */
public interface EntityFacade<T extends AbstractEntity> {

	/**
	 * <strong>C</strong><font color='gray'>RUD</font> operation - inserts a new
	 * entity in the database.
	 * 
	 * @param entity
	 *            The entity to be included in the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	void create(T entity) throws EntityExistsException, IllegalStateException,
			IllegalArgumentException, TransactionRequiredException;

	/**
	 * <font color='gray'>C</font><strong>R</strong><font color='gray'>UD</font>
	 * operation - inserts a new entity in the database. Read all records
	 * available on database for a certain type of entity.
	 * 
	 * @return A collection of objects of type 'entityClass'
	 * @throws IllegalStateException
	 *             if called for a Java Persistence query language UPDATE or
	 *             DELETE statement. See:
	 *             http://java.sun.com/j2se/1.5/docs/api/java
	 *             /lang/IllegalStateException.html
	 * @throws IllegalArgumentException
	 *             if query string is not valid.
	 */
	List<T> readAll() throws IllegalStateException, IllegalArgumentException;

	/**
	 * TODO: missed comments.
	 * 
	 * @param primaryKey
	 * @return an object of type T
	 * @throws Exception
	 */
	T read(Serializable primaryKey) throws IllegalStateException,
			IllegalArgumentException;

	/**
	 * <font color='gray'>CRU</font><strong>D</strong> operation - removes an
	 * entity from the database.
	 * 
	 * @param entity
	 *            The entity to be excluded from the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	void delete(T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException;

	void deleteAll(List<T> entities) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException;

	/**
	 * <font color='gray'>CR</font><strong>U</strong><font color='gray'>D</font>
	 * operation - merges the entity with the database one.
	 * 
	 * @param entity
	 *            The entity to be merged in the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	void update(T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException;

	/**
	 * Execute a query against the database and return the result set
	 * (eventually empty).
	 * 
	 * @param query
	 *            the named query.
	 * @return a list of entities (eventually empty).
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	List<T> doQuery(Query query) throws IllegalStateException,
			IllegalArgumentException;
}
