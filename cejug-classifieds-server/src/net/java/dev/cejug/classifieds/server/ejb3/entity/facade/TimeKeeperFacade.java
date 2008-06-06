package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;

@Stateless
public class TimeKeeperFacade implements TimeKeeperFacadeLocal {
	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public void record(OperationTimestampEntity entity) throws Exception {
		// TODO Auto-generated method stub
	}
}