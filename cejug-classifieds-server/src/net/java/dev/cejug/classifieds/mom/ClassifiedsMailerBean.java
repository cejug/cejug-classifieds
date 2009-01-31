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
package net.java.dev.cejug.classifieds.mom;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The goal of this bean is to send emails with the status of an operation or a
 * customer resource (like account activation notification). It depends on a
 * Mail API resource provided by the Java EE container.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "RegistrationQueue")
public class ClassifiedsMailerBean implements MessageListener {
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
	private final static Logger logger = Logger.getLogger(
			ClassifiedsMailerBean.class.getName(), "i18n/log");

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
				System.out.println(javaMailSession.getProperties());
				MapMessage orderMessage = (MapMessage) message;
				String from = orderMessage.getStringProperty("from");
				String to = orderMessage.getStringProperty("to");
				String subject = orderMessage.getStringProperty("subject");
				String content = orderMessage.getStringProperty("content");

				Properties sessionProps = javaMailSession.getProperties();
				pipedUsername = sessionProps.getProperty("mail.user");
				pipedPassword = sessionProps.getProperty("mail.smtp.password");

				javax.mail.Message msg = new MimeMessage(Session
						.getDefaultInstance(sessionProps, new Authenticator() {
							public PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(
										pipedUsername, pipedPassword);
							}
						}));
				// javax.mail.Message msg = new MimeMessage(javaMailSession);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = { new InternetAddress(to) };
				msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
				msg.setSubject(subject);
				msg.setSentDate(new java.util.Date());
				msg.setContent(content, "text/html");
				Transport.send(msg);
				logger.info("MDB: EMAIL tent to " + to);
			} else {
				logger.warning("Invalid message " + message.getClass());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, "onMessage error", ex);
		}
	}
}
