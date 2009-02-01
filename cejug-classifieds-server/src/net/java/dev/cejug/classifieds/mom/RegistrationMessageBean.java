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
package net.java.dev.cejug.classifieds.mom;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
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
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "RegistrationQueue")
public class RegistrationMessageBean implements MessageListener {

	@Resource(mappedName = "NotificationQueueConnectionFactory")
	private transient TopicConnectionFactory notificationQueueConnectionFactory;

	@Resource(mappedName = "NotificationQueue")
	private transient Queue notificationQueue;

	/**
	 * Mailer bean logger.
	 */
	private final static Logger logger = Logger.getLogger(
			RegistrationMessageBean.class.getName(), "i18n/log");

	@Override
	public void onMessage(Message registration) {
		try {

			if (registration instanceof MapMessage) {

				// createNewCustomer();
				notifyCustomer(registration);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "onMessage error", ex);
		}
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
}
