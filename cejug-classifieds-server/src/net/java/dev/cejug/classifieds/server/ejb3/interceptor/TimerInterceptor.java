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
package net.java.dev.cejug.classifieds.server.ejb3.interceptor;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.TimeKeeperFacadeLocal;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class TimerInterceptor {
	@EJB
	private TimeKeeperFacadeLocal timeKeeperFacade;

	/*
	 * Intercepter method within the bean (the bean is the aspect)
	 */
	@AroundInvoke
	public Object timerLog(InvocationContext ctx) throws Exception {
		// TODO: include timezone from config file...
		TimeZone timezone = TimeZone.getDefault();
		Calendar start = Calendar.getInstance(timezone);
		String errorMessage = null;
		try {
			return ctx.proceed();
		} catch (Exception error) {
			errorMessage = error.getMessage();
			throw new WebServiceException(error);
		} finally {
			try {
				OperationTimestampEntity stamp = new OperationTimestampEntity();
				stamp.setOperationName(ctx.getMethod().getName());
				stamp.setDate(start);
				stamp.setResponseTime(Calendar.getInstance(timezone)
						.getTimeInMillis()
						- start.getTimeInMillis());
				stamp.setStatus(true);
				stamp.setClientId("TODO: get client ID -> ");
				if (errorMessage != null) {
					stamp.setStatus(false);
					stamp.setFault(errorMessage);
				}
				timeKeeperFacade.create(stamp);
			} catch (Exception error) {
				throw new WebServiceException(error);
			}
		}
	}
}
