package net.java.dev.cejug.classifieds.entity.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import net.java.dev.cejug.classifieds.entity.AbstractEntity;

/**
 * CRUD operations shared by the Entity Facades.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 * @see <a *
 *      href="http://en.wikipedia.org/wiki/Create,_read,_update_and_delete">
 *      Create, * read, update and delete (CRUD)< /a>
 */
@Stateless
public class CRUDEntityFacade<T extends AbstractEntity<?>> implements
		EntityFacade<T> {
	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private final static Logger logger = Logger.getLogger(
			CRUDEntityFacade.class.getName(), "i18n/log");

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public CRUDEntityFacade() {
		entityClass = (Class<T>) ((java.lang.reflect.ParameterizedType) this
				.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * The entity manager is injected by the container JEE 5+.
	 */
	@PersistenceContext
	protected transient EntityManager manager;

	/**
	 * {@inheritDoc}
	 */
	public void create(final T entity) throws EntityExistsException,
			IllegalStateException, IllegalArgumentException,
			TransactionRequiredException {
		manager.persist(entity);
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> readAll() throws IllegalStateException,
			IllegalArgumentException {
		Query query;
		query = manager.createQuery("select e from "
				+ entityClass.getSimpleName() + " e");
		logger.finest("readAll: " + query.toString());
		return doQuery(query);
	}

	/**
	 * {@inheritDoc}
	 */
	public T read(final Serializable primaryKey) throws IllegalStateException,
			IllegalArgumentException {
		return manager.find(entityClass, primaryKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException,
			PersistenceException {
		manager.remove(entity);
		manager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
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
	public void update(final T entity) throws IllegalStateException,
			IllegalArgumentException, TransactionRequiredException {
		manager.merge(entity);
		manager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<T> doQuery(Query query) throws IllegalStateException,
			IllegalArgumentException {
		List<T> response = query.getResultList();
		if (response == null) {
			response = new ArrayList<T>();
		}
		return response;
	}
}
