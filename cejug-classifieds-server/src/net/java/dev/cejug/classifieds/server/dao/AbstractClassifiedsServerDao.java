package net.java.dev.cejug.classifieds.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractClassifiedsServerDao<T> implements
		ClassifiedsServerDao<T> {
	@PersistenceContext(unitName = "classifieds_server")
	protected EntityManager manager;
}
