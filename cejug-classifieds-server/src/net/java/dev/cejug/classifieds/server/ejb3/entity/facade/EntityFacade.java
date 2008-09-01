package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug_classifieds.metadata.common.AbstractEntity;

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
	 * @param entityClass
	 *            The entity type.
	 * @return A collection of objects of type 'entityClass'
	 * @throws IllegalStateException
	 *             if called for a Java Persistence query language UPDATE or
	 *             DELETE statement. See:
	 *             http://java.sun.com/j2se/1.5/docs/api/java
	 *             /lang/IllegalStateException.html
	 * @throws IllegalArgumentException
	 *             if query string is not valid.
	 */
	List<T> readAll(Class<T> entityClass) throws IllegalStateException,
			IllegalArgumentException;

	/**
	 * TODO: missed comments.
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return an object of type T
	 * @throws Exception
	 */
	T read(Class<T> entityClass, Serializable primaryKey)
			throws IllegalStateException, IllegalArgumentException;

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

	void delete(Class<T> entityClass, Serializable primaryKey)
			throws IllegalStateException, IllegalArgumentException,
			TransactionRequiredException, PersistenceException;

	void deleteAll(List<T> entities) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException;

	/**
	 * <font color='gray'>CR</font><strong>U</strong><font color='gray'>D</font>
	 * operation - merges the entity with the database one.
	 * 
	 * @param entity
	 *            The entity to be merged in the database.
	 * @return the updated entity, with latest data.
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
