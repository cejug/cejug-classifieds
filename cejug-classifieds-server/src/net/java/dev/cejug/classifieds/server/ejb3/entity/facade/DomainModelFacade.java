package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;
import java.util.Map;

public interface DomainModelFacade<T> {
	void delete(T entity) throws Exception;

	void update(T entity) throws Exception;

	T create() throws Exception;

	T create(T entity) throws Exception;

	List<T> get(int limit) throws Exception;

	List<T> get(String query, int limit) throws Exception;

	T get(Map<String, String> params) throws Exception;

	List<T> get(Map<String, String> params, int limit) throws Exception;
}
