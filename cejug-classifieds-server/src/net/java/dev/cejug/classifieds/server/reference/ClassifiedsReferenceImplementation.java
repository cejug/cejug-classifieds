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
import net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.contract.FeedType;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorQuery;
import net.java.dev.cejug.classifieds.server.generated.contract.MonitorResponse;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;
import net.java.dev.cejug.classifieds.server.generated.contract.SyndicationFilter;
import net.java.dev.cejug.classifieds.server.reference.dao.RssChannelDao;

/**
 * TODO: NOT YET IMPLEMENTED. It is just a mockup code that hould be replaced
 * when the real code becomes available. This fake return values helps client
 * developers to continue to work unsynchronized with the server side
 * development.
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
		RssChannelDao dao = new RssChannelDao();
		RssCollection response = new RssCollection();

		for (SyndicationFilter f : filter.getFilterCollection()) {
			// make SyndicationFilter query
			try {
				response.getRssCollection().addAll(dao.get("", 100));
			} catch (Exception e) {
				// TODO: log
				e.printStackTrace();
			}
		}

		return response;
		/*--
		 * <?xml version="1.0" encoding="UTF-8" ?>
		 <rss version="2.0">

		 <channel>
		 <title>RSS Example</title>
		 <description>This is an example of an RSS feed</description>
		 <link>http://www.domain.com/link.htm</link>
		 <lastBuildDate>Mon, 28 Aug 2006 11:12:55 -0400 </lastBuildDate>
		 <pubDate>Tue, 29 Aug 2006 09:00:00 -0400</pubDate>

		 <item>
		 <title>Item Example</title>
		 <description>This is an example of an Item</description>
		 <link>http://www.domain.com/link.htm</link>
		 <guid isPermaLink="false"> 1102345</guid>
		 <pubDate>Tue, 29 Aug 2006 09:00:00 -0400</pubDate>
		 </item>

		 </channel>
		 </rss>
		 */
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
			throw new WebServiceException(e.getMessage());
		}
		status.setTimestamp(factory
				.newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar
						.getInstance()));

		return status;
		// throw new WebServiceException("operation not yet implemented");
	}

	@Override
	public ServiceStatus reportSpamOperation(SpamReport spam) {
		// throw new WebServiceException("operation not yet implemented");
		/*
		 * javax.xml.soap.Detail detail = null; try { detail =
		 * SOAPFactory.newInstance().createDetail();
		 * detail.addChildElement("MyDetails").addTextNode("failed"); } catch
		 * (SOAPException e) { e.printStackTrace(); } throw new
		 * SOAPFaultException(new QName(
		 * "http://cejug-classifieds.dev.java.net/server/metadata",
		 * "ClassifiedsFault"), "sendSOAPFault method failed",
		 * "http://foo/bar/baz/", detail);
		 */
		ServiceStatus status = new ServiceStatus();
		return status;
	}

	@Override
	public MonitorResponse checkMonitorOperation(MonitorQuery monitor) {
		MonitorResponse response = new MonitorResponse();
		DatatypeFactory factory;
		try {
			factory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			throw new WebServiceException("operation not yet implemented");
		}
		response.setOnlineSince(factory
				.newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar
						.getInstance()));
		response.setServiceName(this.getClass().getName());

		MonitorResponse rt = new MonitorResponse();

		return response;
	}
}