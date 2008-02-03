package net.java.dev.cejug.classifieds.controller.user;

import net.java.dev.cejug.classifieds.model.entitys.User;

/**
 * Managed Bean for User.
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * @since 02/01/2008
 */
public class UserBean {

	User user = new User();
	
	String test;
	
	
	public String execute() {
		
		return "test";
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getTest() {
		return test;
	}


	public void setTest(String test) {
		this.test = test;
	}
	
	
	
	
}
