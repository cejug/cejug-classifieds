package net.java.dev.cejug.classifieds.server.handler;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Keeps the diference time between the input and output of a message.
 * 
 */
public class TimeKeeperSoapHandler implements SOAPHandler<SOAPMessageContext> {
	/**
	 * the global log manager, used to allow third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(TimeKeeperSoapHandler.class
			.getName(), "i18n/log");
	/**
	 * Each operation has its own queue.
	 */
	Map<String, ConcurrentLinkedQueue<Long>> queues = new HashMap<String, ConcurrentLinkedQueue<Long>>();
	Map<TimeStamp, Long> time = new HashMap<TimeStamp, Long>();

	private int queueMaxLength = 10;

	/*
	 * TimeKeeperSoapHandler() { ClassifiedsServerConfig config; try { config =
	 * ConfigLoader.getInstance().load(); Integer customQueueLength =
	 * config.getMonitor() .getTimerQueueLength(); if (customQueueLength ==
	 * null) { queueMaxLength = customQueueLength; } } catch (Exception e) {
	 * logger.log(Level.WARNING,
	 * TimeKeeperSoapHandlerI18N.QUEUE_LENGTH_ERROR.value(), e .getMessage()); } }
	 */

	// change this to redirect output if desired
	private static PrintStream out = System.out;

	public Set<QName> getHeaders() {
		return null;
	}

	public boolean handleMessage(SOAPMessageContext smc) {
		Boolean outboundProperty = (Boolean) smc
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		if (outboundProperty) {
			long stamp = time.get((TimeStamp) smc.get(TimeStamp.KEY));
			long responsetime = System.currentTimeMillis() - stamp;
			System.out.println(smc.get(MessageContext.WSDL_OPERATION)
					+ " respond in " + responsetime + "ms.");

		} else {
			TimeStamp stamp = new TimeStamp();
			time.put(stamp, System.currentTimeMillis());
			smc.put(TimeStamp.KEY, stamp);
			smc.setScope(TimeStamp.KEY, MessageContext.Scope.APPLICATION);
		}
		return true;
	}

	public boolean handleFault(SOAPMessageContext smc) {
		// store timeStamp in Database
		// get ID, include id in the message context
		long start = ((TimeStamp) smc.get(TimeStamp.KEY)).getTime();
		long responsetime = System.currentTimeMillis() - start;
		System.out.println(smc.get(MessageContext.WSDL_OPERATION)
				+ " failed in " + responsetime + "ms.");
		return true;
	}

	// nothing to clean up
	public void close(MessageContext messageContext) {
	}

}
