package net.java.dev.cejug.classifieds.model.dao;

import net.java.dev.cejug.classifieds.model.entitys.User;

public class ClassifiedsDaoFactory {

	public ClassifiedsDao<User> getUserDao(String userId) {
		/*
		 * Classes calling this method has no information about the
		 * implementation details, they uses DAO features by contract. Later, if
		 * we want or we need, we can replace or modify the implementation
		 * without loosing the contract.
		 */
		return new UserDao<User>();
	}

}
