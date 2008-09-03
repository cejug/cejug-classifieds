/*
 * @(#) $CVSHeader:  $
 *
 * Copyright (C) 2008 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Netcetera AG, Switzerland.  The program(s) may be used and/or copied
 * only with the written permission of Netcetera AG or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 *
 * @(#) $Id$
 */
package net.java.dev.cejug.classifieds.service.http;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.entity.ServiceLifeCycleEntity;
import net.java.dev.cejug.classifieds.entity.facade.ServiceLifeCycleFacadeLocal;

public class ServletLifeCycle implements ServletContextListener {
	@EJB
	private transient ServiceLifeCycleFacadeLocal lifeObserver;
	private transient long id = -1;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			ServiceLifeCycleEntity lifeCycle = lifeObserver.read(id);
			lifeCycle.setFinish(Calendar.getInstance());
			lifeObserver.update(lifeCycle);
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			ServiceLifeCycleEntity lifeCycle = new ServiceLifeCycleEntity();
			lifeCycle.setName("test");
			lifeCycle.setStart(Calendar.getInstance());
			lifeObserver.create(lifeCycle);
			id = lifeCycle.getId();
		} catch (Exception e) {
			throw new WebServiceException(e);
		}
	}
}
