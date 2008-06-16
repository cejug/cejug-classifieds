package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AbstractEntity;

/**
 * Base class used to be extended by the entity facades.
 * 
 * @author $Author: rodrigolopes $
 * @version $Rev: 1 $ ($Date: 2008-06-08 13:29:07 +0200 (dom, 08 jun 2008) $)
 */
public class EntityFacade<T extends AbstractEntity> {

	@PersistenceContext(unitName = "classifieds")
	public EntityManager manager;

	public void create(T entity) throws Exception {
		manager.persist(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> entityClass) throws Exception {

		Query query = manager.createQuery("select distinct e from "
				+ entityClass.getName() + " e");
		return query.getResultList();
	}

	public T findById(Class<T> entityClass, Serializable primaryKey)
			throws Exception {

		return manager.find(entityClass, primaryKey);
	}

	public void delete(T entity) throws Exception {

		manager.remove(entity);
	}

	public void removeAll(List<T> entities) throws Exception {

		for (T t : entities) {
			manager.remove(t);
		}
		manager.flush();
	}

	public T update(T entity) throws Exception {

		return manager.merge(entity);
	}

	public T merge(T entity) throws Exception {

		return manager.merge(entity);
	}

	/**
	 * Set the manager.
	 * 
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(EntityManager manager) {

		this.manager = manager;
	}
}
