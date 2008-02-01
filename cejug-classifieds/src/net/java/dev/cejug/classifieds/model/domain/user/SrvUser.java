package net.java.dev.cejug.classifieds.model.domain.user;

import net.java.dev.cejug.classifieds.model.entitys.User;

/**
 * Interface to represent the contract of User
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * @since 02/01/2008
 */
public interface SrvUser  {

	
	public User createUser(User user);
	
}
