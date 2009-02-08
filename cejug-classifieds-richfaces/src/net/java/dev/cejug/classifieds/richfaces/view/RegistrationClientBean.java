/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008-2009 CEJUG - Ceará Java Users Group
 
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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

import net.java.dev.cejug.classifieds.login.entity.facade.client.RegistrationConstants;

/**
 * POJO of the registration form in the GUI.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2009-01-25 18:45:55 +0100 (Sun, 25 Jan 2009) $)
 */
public class RegistrationClientBean {

	/**
	 * TODO: how to get the browser Locale to use in custom messages ??
	 */
	private ResourceBundle i18n = ResourceBundle.getBundle("messages", Locale
			.getDefault());

	/**
	 * default logger.
	 */
	private static final Logger logger = Logger
			.getLogger(RegistrationClientBean.class.getName());

	@Resource(mappedName = "RegistrationQueueConnectionFactory")
	private transient QueueConnectionFactory registrationQueueConnectionFactory;

	@Resource(mappedName = "RegistrationQueue")
	private transient Queue registrationQueue;

	/** new customer's login. */
	private String login;

	/** new customer's password. */
	private String password;

	/** new customer's real name. */
	private String name;

	/** new customer's real email. */
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {

		return login;
	}

	public String getPassword() {

		return password;
	}

	public void setLogin(String login) {

		this.login = login;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	/**
	 * registration flow:
	 * <ol>
	 * <li>validates the data provided by the new customer</li>
	 * <li>check if the username already exists (this we need to create backend
	 * support)</li>
	 * <li>dispatch a message to the registration Queue, containning the form
	 * data.</li>
	 * <li>notify the user that the registration form was submitted and that he
	 * will receive the confirmation and other instructions by email.</li>
	 * </ol>
	 */
	public void register() {
		// TODO: validation of the data, and checking if the user already
		// exists, etc...
		Connection connection = null;
		Session registrationSession = null;
		try {
			connection = registrationQueueConnectionFactory.createConnection();
			registrationSession = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = registrationSession
					.createProducer(registrationQueue);
			MapMessage registrationRequest = registrationSession
					.createMapMessage();

			registrationRequest.setStringProperty(RegistrationConstants.LOGIN
					.value(), login);
			registrationRequest.setStringProperty(
					RegistrationConstants.PASSWORD.value(), password);
			registrationRequest.setStringProperty(RegistrationConstants.NAME
					.value(), name);
			registrationRequest.setStringProperty(RegistrationConstants.EMAIL
					.value(), email);
			registrationRequest.setStringProperty(
					RegistrationConstants.REGISTRATION_MSG.value(),
					MessageFormat.format(i18n
							.getString(RegistrationConstants.REGISTRATION_MSG
									.value()), name));
			registrationRequest.setStringProperty(
					RegistrationConstants.REGISTRATION_SUBJECT.value(),
					i18n.getString(RegistrationConstants.REGISTRATION_SUBJECT
							.value()));
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			publisher.send(registrationRequest);
			logger.finest("Registration request message sent by the customer: "
					+ name);
			// done, now it is time to say friendly words to the customer :)
		} catch (Exception e) {
			logger.severe(e.getMessage());
		} finally {
			if (registrationSession != null) {
				try {
					registrationSession.close();
				} catch (JMSException e) {
					logger.severe(e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}
}