package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementPublisherLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.OperationTimeKeeperLocal;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;
import net.java.dev.cejug.classifieds.server.handler.TimeKeeperSoapHandler;

@Stateless
public class ClassifiedsBusinessSessionBean implements
		ClassifiedsBusinessRemote {
	@EJB
	OperationTimeKeeperLocal timeKeeper;

	@EJB
	AdvertisementPublisherLocal publisher;

	/**
	 * the global log manager, used to allow third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(TimeKeeperSoapHandler.class
			.getName(), "i18n/log");

	private final DatatypeFactory factory;

	public ClassifiedsBusinessSessionBean() {
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	@Override
	public AtomCollection loadAtomOperation(AtomFilterCollection filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RssCollection loadRssOperation(RssFilterCollection filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus publishOperation(AdvertisementBundle advertisements) {
		try {
			publisher.update(advertisements);
			ServiceStatus status = new ServiceStatus();
			status.setDescription("OK");
			status.setCode(202);

			status
					.setTimestamp(factory
							.newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar
									.getInstance()));

			return status;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}

	@Override
	public ServiceStatus reportSpamOperation(SpamReport spam) {
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