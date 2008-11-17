/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.richfaces.view;

import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Controller(value = "securityBean")
@Scope("request")
public class SecurityBean {
	private String login;

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	private String password;

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCustomer() {
		javax.faces.context.ExternalContext context = FacesContext
				.getCurrentInstance().getExternalContext();
		return context.isUserInRole("customer")
				|| context.isUserInRole("admin");
	}

	// here comes the customer login methods .......
	public void doLogin() {
		/*
		 * try { hashPassword(password); } catch (NoSuchAlgorithmException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 */

	}
	/*
	 * private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
	 * '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	 * 
	 * private String hashPassword(String password) throws
	 * NoSuchAlgorithmException { MessageDigest md =
	 * MessageDigest.getInstance("MD5"); md.reset();
	 * 
	 * byte[] bytes = md.digest(password.getBytes()); StringBuilder sb = new
	 * StringBuilder(2 bytes.length); for (int i = 0; i < bytes.length; i++) {
	 * int low = (int) (bytes[i] & 0x0f); int high = (int) ((bytes[i] & 0xf0) >>
	 * 4); sb.append(HEXADECIMAL[high]); sb.append(HEXADECIMAL[low]); } return
	 * sb.toString(); }
	 */
}