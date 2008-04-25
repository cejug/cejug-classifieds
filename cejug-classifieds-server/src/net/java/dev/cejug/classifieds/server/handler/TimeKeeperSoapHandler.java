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
			timer.schedule(TimestampQueueWorker.getInstance(stamps), 34L, 34L);
		} catch (DatatypeConfigurationException e) {
			// TODO: log
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
			addTimestamp(smc);
		} else {
			String key = "key" + System.currentTimeMillis();
			time.put(key, new GregorianCalendar());
			smc.put(TimeKeeperSoapHandler.KEY, key);
			smc.setScope(TimeKeeperSoapHandler.KEY,
					MessageContext.Scope.APPLICATION);
		}
		return true;
	}

	private void addTimestamp(SOAPMessageContext smc) {
		GregorianCalendar start = time.get((GregorianCalendar) smc
				.get(TimeKeeperSoapHandler.KEY));
		OperationTimestamp timestamp = new OperationTimestamp();
		timestamp.setClientId("fake");
		timestamp.setStart(factory.newXMLGregorianCalendar(start));
		timestamp.setFinish(factory
				.newXMLGregorianCalendar(new GregorianCalendar()));
		timestamp.setOperationName((String) smc
				.get(TimeKeeperSoapHandler.OPERATION_KEY));
		timestamp.setStatus(true);
		stamps.add(timestamp);
	}

	public boolean handleFault(SOAPMessageContext smc) {
		addTimestamp(smc);
		return true;
	}

	// nothing to clean up
	public void close(MessageContext messageContext) {
	}

}