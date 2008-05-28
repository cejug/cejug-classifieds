package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;

@Stateless
public class CustomerFacade implements CustomerFacadeLocal {

	@Override
	public CustomerEntity create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(CustomerEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CustomerEntity> get(String query, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerEntity get(Map<String, String> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerEntity> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(CustomerEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}

}