package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;

@Stateless
@NamedQuery(name = "selectDomainByName", query = "SELECT d FROM DomainEntity d WHERE d.domain= :domain")
public class DomainFacade {
	@SuppressWarnings("unused")
	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	public void delete(DomainEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}

	public void update(DomainEntity entity) throws Exception {
		// TODO: merge here..
		manager.persist(entity);
	}

	public DomainEntity create() throws Exception {
		throw new IllegalAccessException("Unable to create empty domain.");
	}

	public List<DomainEntity> get(String query, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public DomainEntity get(Map<String, String> params) throws Exception {
		Query query = manager.createNamedQuery("selectDomainByName");
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		return (DomainEntity) query.getSingleResult();
	}

	public DomainEntity create(DomainEntity entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DomainEntity> get(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<DomainEntity> get(Map<String, String> params, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}