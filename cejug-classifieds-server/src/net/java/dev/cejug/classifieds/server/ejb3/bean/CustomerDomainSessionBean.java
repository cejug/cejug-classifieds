package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.generated.contract.Domain;

@Stateless
public class CustomerDomainSessionBean implements CustomerDomainRemote {
	// this injects the default entity manager factory

	@SuppressWarnings("unused")
	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public Domain create() throws Exception {
		DomainEntity newDomain = new DomainEntity();
		newDomain.setName(Math.random() * 1000 + "."
				+ System.currentTimeMillis());
		manager.persist(newDomain);
		Domain domain = new Domain();
		domain.setName(newDomain.getName());
		return domain;
	}

	@Override
	public void delete(Domain entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Domain> get(String query, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Domain> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Domain entity) throws Exception {
		// TODO Auto-generated method stub

	}

}