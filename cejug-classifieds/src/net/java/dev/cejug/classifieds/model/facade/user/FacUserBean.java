package net.java.dev.cejug.classifieds.model.facade.user;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;

import net.java.dev.cejug.classifieds.model.domain.user.SrvUser;
import net.java.dev.cejug.classifieds.model.domain.user.SrvUserBean;
import net.java.dev.cejug.classifieds.model.entitys.User;

/**
*
* Implementation of Façade for User.
* 
* @author Rafael Carneiro [rafaelcarneirob@gmail.com]
* @since 02/01/2008
*/
public class FacUserBean implements FacUser {

	SrvUser srvUser = new SrvUserBean();
	
	
	@Override
	public User create(User entity) {
		return srvUser.createUser(entity);
	}

	@Override
	public User delete(User entity) {
		// TODO Auto-generated method stub
		throw new NotYetImplementedException("");
	}

	@Override
	public User find(User entity) {
		// TODO Auto-generated method stub
		throw new NotYetImplementedException("");
	}

	@Override
	public List<User> list(User entity) {
		// TODO Auto-generated method stub
		throw new NotYetImplementedException("");
	}

	@Override
	public User update(User entity) {
		// TODO Auto-generated method stub
		throw new NotYetImplementedException("");
	}

	
}
