package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.OperationtimeKeeper;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;

@Stateless
public class ClassifiedsAdminSessionBean implements ClassifiedsAdminRemote {
	@EJB
	OperationtimeKeeper timeKeeper;

	@Override
	public MonitorResponse checkMonitorOperation(MonitorQuery monitor) {

		// TODO Auto-generated method stub
		return null;
	}

	// interceptor method within the bean (the bean is the aspect)
	@AroundInvoke
	public Object TimerLog(InvocationContext ctx) throws Exception {
		// TODO: include timezone...
		Date start = new Date();
		try {
			return ctx.proceed();
		} finally {
			try {
				OperationTimestampEntity stamp = new OperationTimestampEntity();
				stamp.setOperationName(ctx.getMethod().getName());
				stamp.setStart(start);
				stamp.setFinish(new Date());
				stamp.setStatus(true);
				stamp.setClientId("kk");
				timeKeeper.update(stamp);
			} catch (Exception error) {

			}
		}
	}
}