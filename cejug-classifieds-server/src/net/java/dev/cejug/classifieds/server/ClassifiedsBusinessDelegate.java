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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.ws.WebServiceException;
import net.java.dev.cejug.classifieds.server.ejb3.bean.ClassifiedsBusinessRemote;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementHeader;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsBusiness;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;
import net.java.dev.cejug.classifieds.server.generated.i18n.ClassifiedsServiceDelegateI18N;

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
@javax.jws.WebService(endpointInterface = "net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsBusiness")
public class ClassifiedsBusinessDelegate implements CejugClassifiedsBusiness {

    /*
     * http://weblogs.java.net/blog/ramapulavarthi/archive/2007/12/extend_your_web.html
     */
    @Resource
    ClassifiedsBusinessRemote implementation;

    /** The publisher logger. */
    private Logger logger = Logger.getLogger(CejugClassifiedsBusiness.class.getName(), "i18n/log");

    public ClassifiedsBusinessDelegate() {

        InitialContext ic;
        try {
            ic = new InitialContext();
            implementation = (ClassifiedsBusinessRemote) ic.lookup(ClassifiedsBusinessRemote.class.getName());
            logger.log(Level.INFO, ClassifiedsServiceDelegateI18N.SERVICE_DELEGATE_LOADED.value(), implementation.getClass().getName());
        } catch (NamingException e) {
            logger.log(Level.SEVERE, ClassifiedsServiceDelegateI18N.SERVICE_DELEGATE_FAILED.value(), new Object[] { implementation.getClass().getName(), e.getMessage() });
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
        }
    }

    @Override
    public ServiceStatus publishOperation(Advertisement advertisement, AdvertisementHeader header) {

        try {
            return implementation.publishOperation(advertisement, header);
        } catch (Exception e) {
            // TODO: logging....
            throw new WebServiceException(e);
        }
    }
}
