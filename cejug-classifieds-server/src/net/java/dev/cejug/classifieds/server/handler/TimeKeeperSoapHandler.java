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
package net.java.dev.cejug.classifieds.server.handler;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

/**
 * Keeps the diference time between the input and output of a message.
 * 
 * @deprecated This class is here for reference, asap it will be replaced by a
 *             useful one. It was created in teh first moment of the project,
 *             but it does not make sense anymore since we have interceptors
 *             now.
 */
public class TimeKeeperSoapHandler implements SOAPHandler<SOAPMessageContext> {
	public static final String KEY = "timestamp";
	public static final String OPERATION_KEY = "operation";
	private Timer timer = null;

	/**
	 * the global log manager, used to allow third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(TimeKeeperSoapHandler.class
			.getName(), "i18n/log");

	/**
	 * Each operation has its own queue.
	 */
	private ConcurrentLinkedQueue<OperationTimestamp> stamps = new ConcurrentLinkedQueue<OperationTimestamp>();
	private Map<String, GregorianCalendar> time = Collections
			.synchronizedMap(new HashMap<String, GregorianCalendar>());

	private final DatatypeFactory factory;

	public TimeKeeperSoapHandler() {
		try {
			factory = DatatypeFactory.newInstance();
			timer = new Timer(true);
			timer.schedule(TimestampQueueWorker.getInstance(stamps), 5000L,
					5000L);
		} catch (DatatypeConfigurationException e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleMessage(SOAPMessageContext smc) {
		Boolean outboundProperty = (Boolean) smc
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (outboundProperty) {
			addTimestamp(smc, true);
		} else {
			String key = "key" + System.currentTimeMillis();
			time.put(key, new GregorianCalendar());
			smc.put(TimeKeeperSoapHandler.KEY, key);
			smc.setScope(TimeKeeperSoapHandler.KEY,
					MessageContext.Scope.APPLICATION);
		}
		return true;
	}

	private void addTimestamp(SOAPMessageContext smc, boolean status) {
		// logger.info("AAAAAAAAAAAA 1");
		GregorianCalendar start = time.get((String) smc
				.get(TimeKeeperSoapHandler.KEY));
		OperationTimestamp timestamp = new OperationTimestamp();
		timestamp.setClientId("fake");
		timestamp.setStart(factory.newXMLGregorianCalendar(start));
		timestamp.setFinish(factory
				.newXMLGregorianCalendar(new GregorianCalendar()));
		timestamp.setOperationName((String) smc
				.get(MessageContext.WSDL_OPERATION));
		timestamp.setStatus(status);
		stamps.add(timestamp);
	}

	public boolean handleFault(SOAPMessageContext smc) {
		addTimestamp(smc, false);
		return true;
	}

	// nothing to clean up
	public void close(MessageContext messageContext) {
	}

}
