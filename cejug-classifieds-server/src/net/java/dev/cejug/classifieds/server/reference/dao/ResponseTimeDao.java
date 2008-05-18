package net.java.dev.cejug.classifieds.server.reference.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import net.java.dev.cejug.classifieds.server.dao.ClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

public class ResponseTimeDao implements
		ClassifiedsServerDao<OperationTimestamp> {

	private EntityManagerFactory factory = null;

	public ResponseTimeDao() {
		// factory = Persistence.createEntityManagerFactory("classifieds");
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
	public void update(OperationTimestamp source) throws Exception {
		try {

			EntityManager manager = factory.createEntityManager();
			OperationTimestampEntity entity = new OperationTimestampEntity(
					source);
			System.out.println("ENTITY = " + entity);
			System.out.println("MANAGER = " + manager);
			EntityTransaction transaction = manager.getTransaction();
			System.out.println("EntityTransaction = " + transaction);
			transaction.begin();
			manager.persist(entity);
			transaction.commit();
			System.out.println("COMITOU !");
			// TODO: log...

		} catch (Exception e) {
			System.out.println("FACTORY________ " + factory);
			// TODO: log...
			e.printStackTrace();
		}
	}
}
