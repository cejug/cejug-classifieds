package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.OperationTimeKeeperLocal;
import net.java.dev.cejug.classifieds.server.generated.contract.Domain;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.handler.TimeKeeperSoapHandler;

/**
 * //
 * 
 * @WebService(name = "CejugClassifiedsAdmin", targetNamespace =
 *                  "http://cejug-classifieds.dev.java.net/admin")
 * @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
 * 
 * @author Felipe
 * 
 */
@Stateless
public class ClassifiedsAdminSessionBean implements ClassifiedsAdminRemote {
	@EJB
	OperationTimeKeeperLocal timeKeeperFacade;

	@EJB
	DomainFacadeLocal domainFacade;

	private final DatatypeFactory factory;

	/**
	 * the global log manager, used to allow third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(TimeKeeperSoapHandler.class
			.getName(), "i18n/log");

	public ClassifiedsAdminSessionBean() {
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public MonitorResponse checkMonitorOperation(MonitorQuery monitor) {
		// TODO: implement the real database call and response assembly.
		MonitorResponse response = new MonitorResponse();
		response.setServiceName("Cejug-Classifieds");
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.roll(Calendar.DAY_OF_MONTH, -3);
		response.setOnlineSince(factory.newXMLGregorianCalendar(calendar));
		return response;
	}

	/*
	 * Intercepter method within the bean (the bean is the aspect)
	 */
	@AroundInvoke
	public Object timerLog(InvocationContext ctx) throws Exception {
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
				stamp.setClientId("TODO: get client ID");
				timeKeeperFacade.update(stamp);
			} catch (Exception error) {
				throw new WebServiceException(error);
			}
		}
	}

	@Override
	public ServiceStatus requestDomainOperation(Domain domain) {
		try {
			// TODO: review
			// TODO: validation...
			DomainEntity entity = new DomainEntity();
			entity.setDomain(domain.getDomain());
			entity.setSharedQuota(false);
			entity.setTimezone(TimeZone.getTimeZone(domain.getTimezone()));
			entity.setBrand(domain.getBrand());
			domainFacade.update(entity);
		} catch (Exception e) {
			// TODO Logging....
			throw new WebServiceException(e);
		}

		return null;
	}
}