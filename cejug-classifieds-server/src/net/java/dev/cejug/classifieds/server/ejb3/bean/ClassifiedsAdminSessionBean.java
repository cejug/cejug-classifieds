package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.interceptor.TimerInterceptor;
import net.java.dev.cejug.classifieds.server.generated.contract.Domain;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.Vouchers;
import net.java.dev.cejug.classifieds.server.generated.contract.VouchersRequest;

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
@Interceptors(TimerInterceptor.class)
@Stateless
public class ClassifiedsAdminSessionBean implements ClassifiedsAdminRemote {

	@EJB
	private DomainFacadeLocal domainFacade;

	private final DatatypeFactory factory;

	/**
	 * the global log manager, used to allow third party services to override
	 * the defult logger.
	 */
	private static Logger logger = Logger.getLogger(ClassifiedsAdminSessionBean.class
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

	@Override
	public ServiceStatus requestDomainOperation(Domain domain) {
		try {
			// TODO: review validation...
			DomainEntity entity = new DomainEntity();
			entity.setDomainName(domain.getDomain());
			entity.setSharedQuota(false);
			entity.setBrand(domain.getBrand());
			domainFacade.update(entity);
		} catch (Exception e) {
			// TODO Logging....
			throw new WebServiceException(e);
		}

		return new ServiceStatus();
	}

	@Override
	public Vouchers requestVoucherOperation(VouchersRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
}
