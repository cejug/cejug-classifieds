package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AbstractEntity;

/**
 * CRUD operations shared by the Entity Facades.
 * 
 * @author $Author: rodrigolopes $
 * @version $Rev: 1 $ ($Date: 2008-06-08 13:29:07 +0200 (dom, 08 jun 2008) $)
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Create,_read,_update_and_delete">Create,
 *      read, update and delete (CRUD)</a>
 */
public class CRUDEntityFacade<T extends AbstractEntity> implements
		EntityFacade<T> {

	/**
	 * The entity manager is injected by the container JEE 5+.
	 */
	@PersistenceContext
	protected EntityManager manager;

	/**
	 * <strong>C</strong><font color='gray'>rud</font> operation - inserts a
	 * new entity in the database.
	 * 
	 * @param entity
	 *            The entity to be included in the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	@Override
	public void create(T entity) throws Exception {
		manager.persist(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> readAll(Class<T> entityClass) throws Exception {
		Query query = manager.createQuery("select e from "
				+ entityClass.getSimpleName() + " e");
		return query.getResultList();
	}

	/**
	 * <font color='gray'>c</font><strong>R</strong><font color='gray'>ud</font>
	 * operation - reads an entity from the database.
	 * 
	 * @param entity
	 *            The entity found in the database.
	 * @throws Exception
	 *             if the database does not contains the entity that matches the
	 *             ID provided as parameter.
	 * @param entityClass
	 *            The type of the entity.
	 * @param primaryKey
	 *            The ID of the entity. Cejug-Classifieds supports Integer IDs
	 *            only.
	 */
	@Override
	public T read(Class<T> entityClass, Serializable primaryKey)
			throws Exception {
		return manager.find(entityClass, primaryKey);
	}

	/**
	 * <font color='gray'>cru</font><strong>D</strong> operation - removes an
	 * entity from the database.
	 * 
	 * @param entity
	 *            The entity to be excluded from the database.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	@Override
	public void delete(T entity) throws Exception {
		manager.remove(entity);
	}

	@Override
	public void deleteAll(List<T> entities) throws Exception {
		for (T t : entities) {
			manager.remove(t);
		}
		manager.flush();
	}

	/**
	 * <font color='gray'>cr</font><strong>U</strong><font color='gray'>d</font>
	 * operation - merges the entity with the database one.
	 * 
	 * @param entity
	 *            The entity to be merged in the database.
	 * @return the updated entity, with latest data.
	 * @throws Exception
	 *             database exception, or incompatible entity.
	 */
	@Override
	public T update(T entity) throws Exception {
		return manager.merge(entity);
	}
}
