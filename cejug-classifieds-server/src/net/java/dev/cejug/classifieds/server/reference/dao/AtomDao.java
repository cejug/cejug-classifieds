package net.java.dev.cejug.classifieds.server.reference.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.java.dev.cejug.classifieds.server.dao.ClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

public class AtomDao implements
		ClassifiedsServerDao<OperationTimestamp> {

	private EntityManagerFactory factory = null;

	public AtomDao() {
		factory = Persistence.createEntityManagerFactory("classifieds");
	}

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
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		transaction.commit();
	}
}
