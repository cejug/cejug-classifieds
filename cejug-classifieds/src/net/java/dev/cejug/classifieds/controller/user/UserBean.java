package net.java.dev.cejug.classifieds.controller.user;

import javax.faces.event.ActionListener;

import net.java.dev.cejug.classifieds.model.entitys.User;
import net.java.dev.cejug.classifieds.model.facade.user.FacUser;
import net.java.dev.cejug.classifieds.model.facade.user.FacUserBean;

/**
 * Managed Bean for User.
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * @since 02/01/2008
 */
public class UserBean {

	User user = new User();
	
	
	public void createUser(ActionListener actionListener) {
		FacUser facUser = new FacUserBean();
		facUser.create(this.getUser());
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
