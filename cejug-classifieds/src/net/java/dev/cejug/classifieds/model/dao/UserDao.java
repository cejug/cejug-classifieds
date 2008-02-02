package net.java.dev.cejug.classifieds.model.dao;

import java.sql.SQLException;

/**
 * Persistence of the User Entity. This class should be package visible to force
 * it loading through the interface and not through it implementation details.
 * The implementation details should never be visible to consumers, that knows
 * only the interface. The interface guarantee the contract-first design. This
 * class can be Hibernate dependent, since it is just the contract
 * implementation.
 * 
 * @author Felipe Gaúcho
 * 
 * @param <User>
 */
class UserDao<User> extends AbstractClassifiedsDao<User> {

	@Override
	public void create(User pojo) throws Exception {
		/*
		 * Never use technology dependent exception like Hibernate
		 * NotYetImplemented. The DAO should be Java compliant and decoupled
		 * from third party implementations. Internally, the class can
		 * manipulate Hibernate specific exceptions, but the contract should be
		 * subscribed only with Java types.
		 */
		throw new SQLException("Not yet implemented.");
	}

	@Override
	public void delete(User pojo) throws Exception {
		/*
		 * Never use technology dependent exception like Hibernate
		 * NotYetImplemented. The DAO should be Java compliant and decoupled
		 * from third party implementations.
		 */
		throw new SQLException("Not yet implemented.");
	}

	@Override
	public void read(User pojo) throws Exception {
		/*
		 * Never use technology dependent exception like Hibernate
		 * NotYetImplemented. The DAO should be Java compliant and decoupled
		 * from third party implementations.
		 */
		throw new SQLException("Not yet implemented.");
	}

	@Override
	public void update(User pojo) throws Exception {
		/*
		 * Never use technology dependent exception like Hibernate
		 * NotYetImplemented. The DAO should be Java compliant and decoupled
		 * from third party implementations.
		 */
		throw new SQLException("Not yet implemented.");
	}
}
