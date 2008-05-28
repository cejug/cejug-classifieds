package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;

@Stateless
public class OperationtimeKeeper implements OperationTimeKeeperLocal {

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public OperationTimestampEntity create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(OperationTimestampEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OperationTimestampEntity> get(String query, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OperationTimestampEntity> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(OperationTimestampEntity entity) throws Exception {
		manager.persist(entity);
		// TODO Auto-generated method stub

	}

	@Override
	public OperationTimestampEntity get(Map<String, String> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
