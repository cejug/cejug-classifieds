package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;

@Stateless
public class OperationtimeKeeper {

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	public OperationTimestampEntity create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(OperationTimestampEntity entity) throws Exception {
		// TODO Auto-generated method stub

	}

	public List<OperationTimestampEntity> get(String query, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(OperationTimestampEntity entity) throws Exception {
		manager.persist(entity);
		// TODO Auto-generated method stub

	}

	public OperationTimestampEntity get(Map<String, String> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public OperationTimestampEntity create(OperationTimestampEntity entity)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OperationTimestampEntity> get(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OperationTimestampEntity> get(Map<String, String> params,
			int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
