package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.dao.ClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

@Stateless
public class ResponseTimeBean implements
		ClassifiedsServerDao<OperationTimestamp> {

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	public ResponseTimeBean() {
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
			OperationTimestampEntity entity = new OperationTimestampEntity();
			entity.setOperationName(source.getOperationName());

			entity.setStart(source.getStart().toGregorianCalendar().getTime());
			entity
					.setFinish(source.getFinish().toGregorianCalendar()
							.getTime());
			entity.setStatus(source.isStatus());
			entity.setClientId(source.getClientId());
			entity.setFault(source.getFault());

			System.out.println("ENTITY = " + entity);
			System.out.println("MANAGER = " + manager);
			// EntityTransaction transaction = manager.getTransaction();
			// System.out.println("EntityTransaction = " + transaction);
			// transaction.begin();
			manager.persist(entity);
			// transaction.commit();
			// System.out.println("COMITOU !");
			// TODO: log...

		} catch (Exception e) {
			System.out.println("ENTITY MANAGER________ " + manager);
			// TODO: log...
			e.printStackTrace();
		}
	}
}
