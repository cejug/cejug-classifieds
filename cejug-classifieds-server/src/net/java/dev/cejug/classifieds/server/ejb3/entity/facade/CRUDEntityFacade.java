package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AbstractEntity;

/**
 * CRUD operations shared by the Entity Facades.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 * @see <a *
 *      href="http://en.wikipedia.org/wiki/Create,_read,_update_and_delete">
 *      Create, * read, update and delete (CRUD)< /a>
 */
public class CRUDEntityFacade<T extends AbstractEntity> implements
		EntityFacade<T> {

	/**
	 * The entity manager is injected by the container JEE 5+.
	 */
	@PersistenceContext
	protected transient EntityManager manager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(final T entity) throws EntityExistsException,
			IllegalStateException, IllegalArgumentException,
			TransactionRequiredException {
		manager.persist(entity);
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> readAll(final Class<T> entityClass)
			throws IllegalStateException, IllegalArgumentException {
		Query query;
		query = manager.createQuery("select e from "
				+ entityClass.getSimpleName() + " e");
		return doQuery(query);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T read(final Class<T> entityClass, final Serializable primaryKey)
			throws IllegalStateException, IllegalArgumentException {
		return manager.find(entityClass, primaryKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException {
		manager.remove(entity);
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(final Class<T> entityClass, final Serializable primaryKey)
			throws IllegalStateException, IllegalArgumentException,
			TransactionRequiredException, PersistenceException {
		manager.remove(read(entityClass, primaryKey));
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll(final List<T> entities) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException {
		for (T t : entities) {
			manager.remove(t);
		}
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(final T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException {
		manager.merge(entity);
		manager.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> doQuery(Query query) throws IllegalStateException,
			IllegalArgumentException {
		List<T> response = query.getResultList();
		if (response == null) {
			response = new ArrayList<T>();
		}
		return response;
	}
}
