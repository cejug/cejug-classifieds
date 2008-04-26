package net.java.dev.cejug.classifieds.server.reference.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.dao.ClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

@Stateless
public class OperationTimestampDao implements
		ClassifiedsServerDao<OperationTimestamp> {
	@PersistenceContext(unitName = "classifieds_server")
	EntityManager manager;

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
	public void update(OperationTimestamp entity) throws Exception {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		transaction.commit();
	}
}
