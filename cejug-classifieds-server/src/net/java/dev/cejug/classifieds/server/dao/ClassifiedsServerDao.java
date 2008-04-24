package net.java.dev.cejug.classifieds.server.dao;

import java.util.List;

public interface ClassifiedsServerDao<T> {
	void delete(T type) throws Exception;

	void update(T type) throws Exception;

	T create() throws Exception;

	List<T> getAll(int limit) throws Exception;

	List<T> get(String query, int limit) throws Exception;
}
