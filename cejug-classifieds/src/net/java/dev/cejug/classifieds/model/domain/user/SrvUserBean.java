package net.java.dev.cejug.classifieds.model.domain.user;

import net.java.dev.cejug.classifieds.model.entitys.User;
import net.java.dev.cejug.classifieds.model.persist.user.UserDAO;

/**
 * Class to represent the implementation of User
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * @since 02/01/2008
 */
public class SrvUserBean implements SrvUser {

	
	
	@Override
	public User createUser(User user) {
		
		//TODO Modify this to use a repository
		
		UserDAO userDAO = new UserDAO();
		
		userDAO.createUser(user);
		
		return user;
	}

}
