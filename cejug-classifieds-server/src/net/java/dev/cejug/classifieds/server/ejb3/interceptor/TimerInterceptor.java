/**
 * 
 */
package net.java.dev.cejug.classifieds.server.ejb3.interceptor;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.OperationtimeKeeper;

/**
 * @author rodrigo
 * 
 */
public class TimerInterceptor {

	@EJB
	OperationtimeKeeper timeKeeperFacade;

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
				stamp.setClientId("TODO: get client ID");
				timeKeeperFacade.update(stamp);
			} catch (Exception error) {
				throw new WebServiceException(error);
			}
		}
	}
}
