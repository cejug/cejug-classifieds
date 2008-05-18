package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.generated.contract.OperationTimestamp;

@Stateless
public class ResponseTimeBean implements ResponseTime {
	// this injects the default entity manager factory

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	public ResponseTimeBean() {
	}

	@Override
	public OperationTimestamp create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(OperationTimestamp type) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OperationTimestamp> get(String query, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OperationTimestamp> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This is just a test.... INTERCEPTORS is a good reason to think about EJB3 :)
	 * 
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object TimerLog(InvocationContext ctx) throws Exception {
		String beanClassName = ctx.getClass().getName();
		String businessMethodName = ctx.getMethod().getName();
		String target = beanClassName + "." + businessMethodName;
		long startTime = System.currentTimeMillis();
		System.out.println("Invoking " + target);
		try {
			return ctx.proceed();
		} finally {
			System.out.println("Exiting " + target);
			long totalTime = System.currentTimeMillis() - startTime;
			System.out.println("Business method " + businessMethodName + " in "
					+ beanClassName + "takes " + totalTime + "ms to execute");
		}

	}

	@Override
	public void update(OperationTimestamp source) throws Exception {
		try {
			OperationTimestampEntity entity = new OperationTimestampEntity();
			entity.setOperationName(source.getOperationName());

			entity.setStart(source.getStart().toGregorianCalendar().getTime());
			entity
					.setFinish(source.getFinish().toGregorianCalendar()
							.getTime());
			entity.setStatus(source.isStatus());
			entity.setClientId(source.getClientId());
			entity.setFault(source.getFault());

			manager.persist(entity);

			System.out.println("COMITOU !");
			// TODO: log...

		} catch (Exception e) {
			// TODO: log...
			e.printStackTrace();
		}
	}
}
