package net.java.dev.cejug.classifieds.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractClassifiedsServerDao<T> implements
		ClassifiedsServerDao<T> {

	protected EntityManagerFactory factory = null;
	protected EntityManager manager = null;

	public AbstractClassifiedsServerDao() {
		factory = Persistence.createEntityManagerFactory("classifieds_server");
		manager = factory.createEntityManager();
	}
}
