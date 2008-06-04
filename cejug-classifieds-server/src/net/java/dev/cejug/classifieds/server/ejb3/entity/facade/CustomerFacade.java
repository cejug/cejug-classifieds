package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.QuotaEntity;

@Stateless
public class CustomerFacade implements CustomerFacadeLocal {
	@SuppressWarnings("unused")
	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@EJB
	DomainFacade domainFacade;

	@Override
	public CustomerEntity findOrCreate(String domainName, String login)
			throws Exception {
		Query query = manager
				.createNamedQuery("selectCustomerByLoginAndDomain");
		query.setParameter("domain", domainName);
		query.setParameter("login", login);

		try {
			return (CustomerEntity) query.getSingleResult();
		} catch (NoResultException error) {
			/*
			 * TODO: if the customer does not exist, insert a new customer
			 * automatically. It should also add the default "welcome quotas",
			 * to be defined by an external rule from database. The below code
			 * is just a test.
			 */

			// loading domain
			DomainEntity domain = domainFacade.get(domainName);

			// insert a new customer
			CustomerEntity customer = new CustomerEntity();
			customer.setDomain(domain);
			customer.setLogin(login);
			customer.setQuotas(new ArrayList<QuotaEntity>());
			manager.persist(customer);
			return customer;
		}
	}

	@Override
	public void delete(CustomerEntity entity) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(CustomerEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}
}