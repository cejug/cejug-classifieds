package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<AdvertisementEntity> get(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdvertisementEntity> get(String query, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdvertisementEntity get(Map<String, String> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvertisementEntity> get(Map<String, String> params, int limit)
			throws Exception {
		Query query = manager.createNamedQuery("selectAdvertisementByFilter");
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		List<AdvertisementEntity> result = query.getResultList();
		return result;
	}

}