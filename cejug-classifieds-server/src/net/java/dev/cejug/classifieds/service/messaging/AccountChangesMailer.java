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
package net.java.dev.cejug.classifieds.service.messaging;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

/**
 * TODO: review, document and implement the final code.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Stateless
public class AccountChangesMailer implements AccountChangesMailerLocal {

	@Resource(mappedName = "AccountChangesTopicConnectionFactory")
	private TopicConnectionFactory statusMessageTopicCF;

	@Resource(mappedName = "AccountChangesTopic")
	private Topic statusTopic;

	public String sendAccountChangesSummary() {
		String from = "cejug.classifieds@gmail.com";
		String to = "fgaucho@gmail.com";
		String content = "Your order has been processed "
				+ "If you have questions"
				+ " call EJB3 Application with order id # " + "1234567890";

		try {
			System.out.println("Before status TopicCF connection");
			Connection connection = statusMessageTopicCF.createConnection();
			System.out.println("Created connection");
			connection.start();
			System.out.println("statted connection");
			System.out.println("Starting Topic Session");
			Session topicSession = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			MessageProducer publisher = topicSession
					.createProducer(statusTopic);
			System.out.println("created producer");
			MapMessage message = topicSession.createMapMessage();
			message.setStringProperty("from", from);
			message.setStringProperty("to", to);
			message.setStringProperty("subject", "Status of your wine order");
			message.setStringProperty("content", content);
			System.out.println("before send");
			publisher.send(message);
			System.out.println("after send");
		} catch (JMSException e) {
			e.printStackTrace();
		}

		return "Created a MapMessage and sent it to StatusTopic";
	}
}
