package net.java.dev.cejug.classifieds.adapter;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.CustomerAdapterLocal;
import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

@Stateless
public class CustomerAdapter extends AbstractSoapOrmAdapter<Customer, CustomerEntity> 
    implements CustomerAdapterLocal {

	@EJB
	private transient DomainFacadeLocal domainFacade;

	public CustomerEntity toEntity(Customer customer)
			throws IllegalStateException, IllegalArgumentException {
		CustomerEntity entity = new CustomerEntity();
		entity.setDomain(domainFacade.read(customer.getDomainId()));
		entity.setId(customer.getEntityId());
		entity.setLogin(customer.getLogin());
		// entity.setQuotas(type.getEntityId());
		return entity;
	}

	public Customer toSoap(CustomerEntity entity) throws IllegalStateException,
			IllegalArgumentException {
		Customer customer = new Customer();
		customer.setDomainId(entity.getDomain().getId());
		customer.setEntityId(entity.getId());
		customer.setLogin(entity.getLogin());
		return null;
	}
}
