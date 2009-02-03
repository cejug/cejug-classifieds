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
package net.java.dev.cejug.classifieds.login.jms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;

import net.java.dev.cejug.classifieds.login.entity.GroupEntity;
import net.java.dev.cejug.classifieds.login.entity.UserEntity;
import net.java.dev.cejug.classifieds.login.entity.facade.GroupFacadeLocal;
import net.java.dev.cejug.classifieds.login.entity.facade.UserFacadeLocal;

/**
 * Registration steps, triggered by a message in the registration queue:
 * <ol>
 * <li>receives a message containing the registration request data from the
 * RegistrationQueue.</li>
 * <li>Creates a new customer record in the database, with group or status
 * <em>pending</em>.</li>
 * <li>Pushes a new message with the confirmation request to the
 * NotificationQueue</li>
 * </ol>
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2009-02-01 15:59:06 +0100 (Sun, 01 Feb 2009) $)
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "RegistrationQueue")
public class RegistrationMessageBean implements MessageListener {

	@Resource(mappedName = "NotificationQueueConnectionFactory")
	private transient TopicConnectionFactory notificationQueueConnectionFactory;

	@Resource(mappedName = "NotificationQueue")
	private transient Queue notificationQueue;

	@EJB
	private transient UserFacadeLocal userFacade;

	@EJB
	private transient GroupFacadeLocal groupFacade;
	/**
	 * Mailer bean logger.
	 */
	private final static Logger logger = Logger
			.getLogger(RegistrationMessageBean.class.getName());

	@Override
	public void onMessage(Message registration) {
		try {
			if (registration instanceof MapMessage) {
				createNewCustomer(registration);
				addUserToCustomerGroup(registration);
				notifyCustomer(registration);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "onMessage error", ex);
		}
	}

	private void addUserToCustomerGroup(Message registration)
			throws JMSException {
		GroupEntity customersGroup = new GroupEntity();
		customersGroup.setGroupId("customer");
		customersGroup.setDescription("new customer");
		customersGroup.setLogin(registration
				.getStringProperty(RegistrationConstants.LOGIN.value()));
		groupFacade.create(customersGroup);
	}

	private UserEntity createNewCustomer(Message registration)
			throws JMSException {
		UserEntity newCustomer = new UserEntity();
		newCustomer.setDomain("domain1");
		newCustomer.setLogin(registration
				.getStringProperty(RegistrationConstants.LOGIN.value()));
		newCustomer.setEmail(registration
				.getStringProperty(RegistrationConstants.EMAIL.value()));
		String passwordMd5;
		try {
			passwordMd5 = hashPassword(registration
					.getStringProperty(RegistrationConstants.PASSWORD.value()));
		} catch (NoSuchAlgorithmException e) {
			throw new JMSException(e.getMessage());
		}

		newCustomer.setPassword(passwordMd5);
		newCustomer.setName(registration
				.getStringProperty(RegistrationConstants.NAME.value()));
		userFacade.create(newCustomer);
		return newCustomer;
	}

	/**
	 * Send an email to the new customer, asking him to confirm the
	 * registration.
	 * 
	 * @param registration
	 *            the original message containing the customer information.
	 * @throws JMSException
	 *             a general messaging exception.
	 */
	private void notifyCustomer(Message registration) throws JMSException {
		Connection connection = null;
		try {
			logger.finest("Sending notification about account changes");
			connection = notificationQueueConnectionFactory.createConnection();
			logger.finest("Connection established with client ID = "
					+ connection.getClientID());
			connection.start();
			Session topicSession = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			MessageProducer publisher = topicSession
					.createProducer(notificationQueue);
			logger.finest("Producer created for topic "
					+ notificationQueue.getQueueName());
			publisher.send(registration);

			logger.finest("message sent.");
		} catch (JMSException e) {
			logger.severe(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				logger.severe(e.getMessage());
			}
		}
	}

	private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String hashPassword(String password)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();

		byte[] bytes = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			int low = (int) (bytes[i] & 0x0f);
			int high = (int) ((bytes[i] & 0xf0) >> 4);
			sb.append(HEXADECIMAL[high]);
			sb.append(HEXADECIMAL[low]);
		}
		return sb.toString();
	}
}
