package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;

public interface DomainModelFacade<T> {
	void delete(T entity) throws Exception;

	void update(T entity) throws Exception;

	T create() throws Exception;

	List<T> getAll(int limit) throws Exception;

	List<T> get(String query, int limit) throws Exception;
}
