package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;

@Stateless
public class DomainFacade implements DomainFacadeLocal {
	@SuppressWarnings("unused")
	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public void delete(DomainEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(DomainEntity entity) throws Exception {
		// TODO: merge here..
		manager.persist(entity);
	}

	@Override
	public DomainEntity create() throws Exception {
		throw new IllegalAccessException("Unable to create empty domain.");
	}

	@Override
	public List<DomainEntity> get(String query, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DomainEntity get(Map<String, String> params) throws Exception {
		Query query = manager.createNamedQuery("selectDomainByName");
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return (DomainEntity) query.getSingleResult();
	}

	@Override
	public DomainEntity create(DomainEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DomainEntity> get(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DomainEntity> get(Map<String, String> params, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}