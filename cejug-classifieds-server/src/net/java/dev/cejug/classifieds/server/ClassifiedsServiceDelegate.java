/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe Gaúcho
 
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
package net.java.dev.cejug.classifieds.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;
import net.java.dev.cejug.classifieds.server.generated.i18n.ClassifiedsServiceDelegateI18N;
import net.java.dev.cejug.classifieds.server.handler.TimeKeeperSoapHandler;

/**
 * Cejug-Classifieds-Service delegates its behaviour to an underneath
 * implementation. You can configure the implementation type in the system
 * properties file. If you don't inform the qualified name of the service
 * implementation, the reference implementation will be used.
 * 
 * @see <a
 *      href='http://java.sun.com/blueprints/corej2eepatterns/Patterns/BusinessDelegate.html'>Core
 *      J2EE Patterns - Business Delegate</a>
 * @author $Author: felipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
// @Interceptors( { Interceptor2.class })
@javax.jws.WebService(endpointInterface = "net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface")
@Stateless
public class ClassifiedsServiceDelegate implements ClassifiedsServiceInterface {
	/*
	 * private static final String LOAD_ATOM_OPERATION = "loadAtomOperation";
	 * private static final String LOAD_RSS_OPERATION = "loadRssOperation";
	 * private static final String PUBLISH_OPERATION = "publishOperation";
	 * private static final String REPORT_SPAM_OPERATION =
	 * "reportSpamOperation"; //
	 * http://weblogs.java.net/blog/ramapulavarthi/archive/2007/12/extend_your_web.html
	 */
	@Resource
	WebServiceContext wsContext;

	/** The publisher logger. */
	private Logger logger = Logger.getLogger(ClassifiedsServiceInterface.class
			.getName(), "i18n/log");

	/**
	 * The service contract realization uses an injected implementation to
	 * delegate the operation calls.
	 */
	private ClassifiedsServiceInterface implementation = null;

	public ClassifiedsServiceDelegate() {
		try {
			this.implementation = ClassifiedsServiceLocator
					.getServiceImplementation();
			logger.log(Level.INFO,
					ClassifiedsServiceDelegateI18N.SERVICE_DELEGATE_LOADED
							.value(), implementation.getClass().getName());
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					ClassifiedsServiceDelegateI18N.SERVICE_DELEGATE_FAILED
							.value(),
					new Object[] { implementation.getClass().getName(),
							e.getMessage() });
			throw new WebServiceException(e);
		}
	}

	@Override
	public AtomCollection loadAtomOperation(AtomFilterCollection filter) {
		try {

			return implementation.loadAtomOperation(filter);
		} catch (Exception e) {
			// TODO: logging....
			throw new WebServiceException(e);
		} finally {
			MessageContext msgContext = wsContext.getMessageContext();
			msgContext.put(TimeKeeperSoapHandler.KEY, msgContext
					.get(TimeKeeperSoapHandler.KEY));
			msgContext.put(MessageContext.WSDL_OPERATION, "loadAtom");
		}
	}

	@Override
	public RssCollection loadRssOperation(RssFilterCollection filter) {
		try {
			// TODO: logging....
			return implementation.loadRssOperation(filter);
		} catch (Exception e) {
			// TODO: logging....
			throw new WebServiceException(e);
		} finally {
			MessageContext msgContext = wsContext.getMessageContext();
			msgContext.put(TimeKeeperSoapHandler.KEY, msgContext
					.get(TimeKeeperSoapHandler.KEY));
			msgContext.put(MessageContext.WSDL_OPERATION, "loadRssOperation");
		}
	}

	@Override
	public ServiceStatus publishOperation(Advertisement advertisement) {
		try {
			return implementation.publishOperation(advertisement);
		} catch (Exception e) {
			// TODO: logging....
			throw new WebServiceException(e);
		} finally {
			MessageContext msgContext = wsContext.getMessageContext();
			msgContext.put(TimeKeeperSoapHandler.KEY, msgContext
					.get(TimeKeeperSoapHandler.KEY));
			msgContext.put(MessageContext.WSDL_OPERATION, "publishOperation");
		}
	}

	@Override
	public ServiceStatus reportSpamOperation(SpamReport spam) {
		try {
			// TODO: logging....
			return implementation.reportSpamOperation(spam);
		} catch (Exception e) {
			// TODO: logging....
			throw new WebServiceException(e);
		} finally {
			MessageContext msgContext = wsContext.getMessageContext();
			msgContext.put(TimeKeeperSoapHandler.KEY, msgContext
					.get(TimeKeeperSoapHandler.KEY));

			msgContext
					.put(MessageContext.WSDL_OPERATION, "reportSpamOperation");
		}
	}

	@Override
	public MonitorResponse checkMonitorOperation(MonitorQuery monitor) {
		try {
			// TODO: logging....
			return implementation.checkMonitorOperation(monitor);
		} catch (Exception e) {
			// TODO: logging....
			throw new WebServiceException(e);
		} finally {
			MessageContext msgContext = wsContext.getMessageContext();
			msgContext.put(TimeKeeperSoapHandler.KEY, msgContext
					.get(TimeKeeperSoapHandler.KEY));
			msgContext
					.put(MessageContext.WSDL_OPERATION, "checkMonitorOperation");
		}
	}
}
