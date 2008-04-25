package net.java.dev.cejug.classifieds.server.dao;

import java.util.List;

public interface ClassifiedsServerDao<T> {
	void delete(T entity) throws Exception;

	void update(T entity) throws Exception;

	T create() throws Exception;

	List<T> getAll(int limit) throws Exception;

	List<T> get(String query, int limit) throws Exception;
}
