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
package net.java.dev.cejug.classifieds.server.reference;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.Channel;
import net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.contract.FeedType;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;

/**
 * Cejug-Classifieds-service:
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
public class ClassifiedsReferenceImplementation implements
		ClassifiedsServiceInterface {
	@Override
	public AtomCollection loadAtomOperation(AtomFilterCollection filter) {
		// String section = filter.getSection(); // should be used to load
		// different sections.

		FeedType feed = new FeedType();
		feed.getAuthorOrCategoryOrContributor();
		AtomCollection response = new AtomCollection();
		response.getAtomCollection().add(feed);
		return response;
	}

	@Override
	public RssCollection loadRssOperation(RssFilterCollection filter) {
		// String section = filter.getSection(); // should be used to load
		// different sections.
		Channel channel = new Channel();
		/*
		 * TRss rss = new TRss(); rss.setVersion(new BigDecimal(1.0d)); TRssItem
		 * item = new TRssItem(); item .getTitleOrDescriptionOrLink() .add( "The
		 * advertisement colelction title - can include Section, etc");
		 * TRssChannel channel = new TRssChannel(); channel.getItem().add(item);
		 * channel.getOtherAttributes(); channel.getAny();
		 * channel.getTitleOrLinkOrDescription();
		 * 
		 * rss.setChannel(channel);
		 */
		RssCollection response = new RssCollection();
		response.getRssCollection().add(channel);
		return response;
	}

	@Override
	public ServiceStatus publishOperation(Advertisement advertisement) {
		ServiceStatus status = new ServiceStatus();
		status.setDescription("OK");
		status.setCode(202);
		DatatypeFactory factory;
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			throw new WebServiceException("operation not yet implemented");
		}
		status.setTimestamp(factory
				.newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar
						.getInstance()));

		return status;
		// throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus reportSpamOperation(SpamReport spam) {
		throw new WebServiceException("operation not yet implemented");
	}
}