package net.java.dev.cejug.classifieds.ws.orm;

import java.sql.SQLException;
import java.util.List;

public interface ClassifiedsCrud<T> {
	void delete(T type) throws SQLException;

	void update(T type) throws SQLException;

	T create() throws SQLException;

	void create(T type) throws SQLException;

	List<T> read(int limit) throws SQLException;

	List<T> read(String query, int limit) throws SQLException;
}
