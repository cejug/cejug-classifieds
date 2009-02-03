/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2009 CEJUG - Ceará Java Users Group
 
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

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The goal of this bean is to send emails with the status of an operation or a
 * customer resource (like account activation notification). It depends on a
 * Mail API resource provided by the Java EE container.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2009-02-01 17:26:25 +0100 (Sun, 01 Feb 2009) $)
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "NotificationQueue")
public class NotificationMailerBean implements MessageListener {
	/**
	 * JavaMail resource is injected by the container and have everything we
	 * need to send/receive emails.
	 * 
	 * @see <a href='http://docs.sun.com/app/docs/doc/820-4335/ablkr?a=view'>
	 *      Glassfish instructions about Configuring JavaMail Resources</a>
	 */
	@Resource(name = "mail/classifieds")
	private transient Session javaMailSession;

	/**
	 * TOTALLY WRONG - but somehow I couldn't make GMail to work on
	 * Glassfish..... it copies the username from the JavaMail Session to a
	 * class variable in order to create an authenticator. The authenticator
	 * itself is not supposed to be used, the container should handle that...
	 * 
	 */
	private transient String pipedUsername;
	/**
	 * 
	 */
	private transient String pipedPassword;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private final static Logger logger = Logger
			.getLogger(NotificationMailerBean.class.getName());

	/**
	 * Each time a Topic arrives in the Notification queue, this listener will
	 * send an email to the addressee. The prime purpose of this bean is to
	 * notify new customer about the link they should click in order to activate
	 * their accounts, but in the future it can be expanded for other uses.
	 * 
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			if (message instanceof MapMessage) {
				MapMessage registration = (MapMessage) message;
				String from = "cejug.classifieds@gmail.com";
				String to;
				to = registration.getStringProperty(RegistrationConstants.EMAIL
						.value());
				// TODO: all this message details should be outside this class,
				// passed as parameters......
				String subject = "Cejug-Classifieds registration confirmation";
				String content = "Welcome to Cejug-Classifieds, \n\n"
						+ "in order to confirm you requested a registration in our classifieds"
						+ "system, please click on the link below:\n\nhttp://link.to.be.sent...\n\n"
						+ "WARNING: this is an incomplete feature of Cejug-Classifieds. IT IS INVALID for the moment.. just a test..\n\n"
						+ "the data you typed in the registration form was:\n"
						+ "\nname: "
						+ registration
								.getStringProperty(RegistrationConstants.NAME
										.value())
						+ "\nemail: "
						+ registration
								.getStringProperty(RegistrationConstants.EMAIL
										.value())
						+ "\nlogin: "
						+ registration
								.getStringProperty(RegistrationConstants.LOGIN
										.value())
						+ "\tpassword: "
						+ registration
								.getStringProperty(RegistrationConstants.PASSWORD
										.value())
						+ "\n\nBe welcome to visit our project and help us to code all pending features (like the registration form). Questions? please send to\n\n\tdev@cejug-classifieds.dev.java.net";

				Properties sessionProps = javaMailSession.getProperties();
				pipedUsername = sessionProps.getProperty("mail.user");
				pipedPassword = sessionProps.getProperty("mail.smtp.password");

				Session session = Session.getDefaultInstance(sessionProps,
						new Authenticator() {
							public PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(
										pipedUsername, pipedPassword);
							}
						});
				javax.mail.Message msg = new MimeMessage(session);
				// javax.mail.Message msg = new MimeMessage(javaMailSession);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = { new InternetAddress(to) };
				msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
				msg.setSubject(subject);
				msg.setSentDate(new java.util.Date());
				msg.setContent(content, "text/plain");
				Transport.send(msg);
				logger.info("MDB: EMAIL tent to " + to);
			} else {
				logger.warning("Invalid message " + message.getClass());
			}
		} catch (JMSException e) {
			logger.severe("Invalid message " + e.getClass());
		} catch (AddressException e) {
			logger.severe("Invalid message " + e.getClass());
		} catch (MessagingException e) {
			logger.severe("Invalid message " + e.getClass());
		}
	}
}
