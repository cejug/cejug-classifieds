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
 * @deprecated This class is here for reference, asap it will be replaced by a
 *             useful one. It was created in teh first moment of the project,
 *             but it does not make sense anymore since we have interceptors
 *             now.
 * 
 */
public class TimestampQueueWorker extends TimerTask {
	private ConcurrentLinkedQueue<OperationTimestamp> queue;

	// @EJB(name =
	// "net.java.dev.cejug.classifieds.server.ejb3.bean.ResponseTime")
	// private ResponseTime dao;

	private static Logger logger = Logger.getLogger(
			CejugClassifiedsBusiness.class.getName(), "i18n/log");

	private static TimestampQueueWorker instance;

	private TimestampQueueWorker(ConcurrentLinkedQueue<OperationTimestamp> queue) {
		this.queue = queue;
	}

	public static TimestampQueueWorker getInstance(
			ConcurrentLinkedQueue<OperationTimestamp> queue) {
		if (instance == null) {
			instance = new TimestampQueueWorker(queue);
		} else if (queue != instance.getQueue()) {
			logger.severe(TimestampQueueWorkerI18N.DUPLICATE_KEY_ERROR.value());
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
