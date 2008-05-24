package net.java.dev.cejug.classifieds.server.handler;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsBusiness;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;
import net.java.dev.cejug.classifieds.server.generated.i18n.TimestampQueueWorkerI18N;

/**
 * This class consumes the timestamp of the operation calls.
 * 
 * 
 */
public class TimestampQueueWorker extends TimerTask {
	private ConcurrentLinkedQueue<OperationTimestamp> queue;

	// @EJB(name =
	// "net.java.dev.cejug.classifieds.server.ejb3.bean.ResponseTime")
	// private ResponseTime dao;

	private Logger logger = Logger.getLogger(CejugClassifiedsBusiness.class
			.getName(), "i18n/log");

	private static TimestampQueueWorker instance;

	private TimestampQueueWorker(ConcurrentLinkedQueue<OperationTimestamp> queue) {
		this.queue = queue;
	}

	public static TimestampQueueWorker getInstance(
			ConcurrentLinkedQueue<OperationTimestamp> queue) {
		if (instance == null) {
			instance = new TimestampQueueWorker(queue);
		} else if (queue != instance.getQueue()) {
			throw new WebServiceException(
					TimestampQueueWorkerI18N.DUPLICATE_KEY_ERROR.value());
		}
		return instance;

	}

	@Override
	public void run() {
		/*
		 * OperationTimestamp stamp = queue.poll(); if (stamp != null) { try {
		 * InitialContext ic = new InitialContext(); ResponseTime dao =
		 * (ResponseTime) ic.lookup(ResponseTime.class .getName());
		 * dao.update(stamp); } catch (Exception e) { logger.log(Level.SEVERE,
		 * TimestampQueueWorkerI18N.DB_UPDATE_ERROR.value(), e .getMessage());
		 * e.printStackTrace(); // time keeper does not shutdown the service. } }
		 */
	}

	public ConcurrentLinkedQueue<OperationTimestamp> getQueue() {
		return queue;
	}
}
