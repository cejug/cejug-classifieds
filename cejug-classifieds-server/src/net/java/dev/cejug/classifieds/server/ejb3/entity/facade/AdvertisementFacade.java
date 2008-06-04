package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;

@Stateless
public class AdvertisementFacade implements AdvertisementFacadeLocal {
	// this injects the default entity manager factory

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public void update(AdvertisementEntity entity) throws Exception {
		manager.merge(entity);
	}

	@Override
	public AdvertisementEntity create(AdvertisementEntity entity)
			throws Exception {
		manager.persist(entity);
		return entity;
	}

	@Override
	public void delete(AdvertisementEntity entity) throws Exception {
		manager.remove(entity);
	}

	@Override
	public AdvertisementEntity create() throws Exception {
		throw new UnsupportedOperationException(
				"blank advertisements cannot be created.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvertisementEntity> getLatest(int limit) throws Exception {
		Query query = manager.createNamedQuery("selectAdvertisementByFilter");
		List<AdvertisementEntity> result = query.getResultList();
		return result;
	}

}