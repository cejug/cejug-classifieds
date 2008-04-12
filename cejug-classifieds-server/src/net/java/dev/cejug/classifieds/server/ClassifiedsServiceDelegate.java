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

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.server.generated.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.SpamReport;

/**
 * Cejug-Classifieds-Service delegates its behaviour to an underneath
 * implementation. You can configure the implementation type in the system
 * properties file. If you don't inform the qualified name of the service
 * implementation, the reference implementation will be used.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
@javax.jws.WebService(endpointInterface = "net.java.dev.cejug.classifieds.server.generated.ClassifiedsServiceInterface")
@Stateless
public class ClassifiedsServiceDelegate implements ClassifiedsServiceInterface {
	/**
	 * The service contract realization uses an injected implementation to
	 * delegate the operation calls.
	 */
	private ClassifiedsServiceInterface implementation = null;

	public ClassifiedsServiceDelegate() {
		this.implementation = ClassifiedsServiceLocator
				.getServiceImplementation();

		// TODO: logging exceptions / service name loaded......
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
	public ServiceStatus publishOperation(Advertisement advertisement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus reportSpamOperation(SpamReport spam) {
		// TODO Auto-generated method stub
		return null;
	}

}
