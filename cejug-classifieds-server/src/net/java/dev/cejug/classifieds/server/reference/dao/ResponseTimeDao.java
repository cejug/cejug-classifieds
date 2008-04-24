package net.java.dev.cejug.classifieds.server.reference.dao;

import java.util.List;

import javax.persistence.EntityTransaction;

import net.java.dev.cejug.classifieds.server.dao.AbstractClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.generated.contract.ResponseTime;
import net.java.dev.cejug.classifieds.server.reference.dao.pojos.ResponseTimeEntity;

public class ResponseTimeDao extends AbstractClassifiedsServerDao<ResponseTime> {

	public ResponseTimeDao() {
		super();
	}

	@Override
	public ResponseTime create() throws Exception {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(ResponseTime type) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ResponseTime> get(String query, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResponseTime> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ResponseTime source) throws Exception {
		ResponseTimeEntity entity = new ResponseTimeEntity(source);
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		transaction.commit();
	}
}
