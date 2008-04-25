package net.java.dev.cejug.classifieds.server.reference.dao;

import java.util.List;

import net.java.dev.cejug.classifieds.server.dao.AbstractClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

public class ResponseTimeDao extends
		AbstractClassifiedsServerDao<OperationTimestamp> {

	@Override
	public OperationTimestamp create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(OperationTimestamp type) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OperationTimestamp> get(String query, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OperationTimestamp> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(OperationTimestamp type) throws Exception {

		/*
		 * ResponseTimeEntity entity = new ResponseTimeEntity(source);
		 * EntityTransaction transaction = manager.getTransaction();
		 * transaction.begin(); manager.persist(entity); transaction.commit();
		 */
	}
}
