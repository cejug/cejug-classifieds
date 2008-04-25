package net.java.dev.cejug.classifieds.server.handler;

import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;
import net.java.dev.cejug.classifieds.server.generated.i18n.TimestampQueueWorkerI18N;
import net.java.dev.cejug.classifieds.server.reference.dao.ResponseTimeDao;

/**
 * This class consumes the timestamp of the operation calls.
 * 
 * 
 */
public class TimestampQueueWorker extends TimerTask {
	private ConcurrentLinkedQueue<OperationTimestamp> queue;
	private ResponseTimeDao dao = new ResponseTimeDao();
	private Logger logger = Logger.getLogger(ClassifiedsServiceInterface.class
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
		OperationTimestamp stamp = queue.poll();
		if (stamp != null) {
			try {
				dao.update(stamp);
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						TimestampQueueWorkerI18N.DB_UPDATE_ERROR.value(), e
								.getMessage());
				// time keeper does not shutdown the service.
			}
		}
	}

	public ConcurrentLinkedQueue<OperationTimestamp> getQueue() {
		return queue;
	}
}
