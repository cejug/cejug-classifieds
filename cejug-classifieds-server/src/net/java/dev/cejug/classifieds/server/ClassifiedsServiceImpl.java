/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe Ga�cho
 
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

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.generated.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.ClassifiedsServiceInterface;
import net.java.dev.cejug.classifieds.server.generated.FeedType;
import net.java.dev.cejug.classifieds.server.generated.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.TRss;
import net.java.dev.cejug.classifieds.server.generated.TRssChannel;
import net.java.dev.cejug.classifieds.server.generated.TRssItem;

/**
 * Cejug-Classifieds-service:
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
@javax.jws.WebService(endpointInterface = "net.java.dev.cejug.classifieds.server.generated.ClassifiedsServiceInterface")
@Stateless
public class ClassifiedsServiceImpl implements ClassifiedsServiceInterface {
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
		System.out.println("YEPE");
		TRss rss = new TRss();
		rss.setVersion(new BigDecimal(1.0d));
		TRssItem item = new TRssItem();
		item
				.getTitleOrDescriptionOrLink()
				.add(
						"The advertisement colelction title - can include Section, etc");
		TRssChannel channel = new TRssChannel();
		channel.getItem().add(item);
		channel.getOtherAttributes();
		channel.getAny();
		channel.getTitleOrLinkOrDescription();

		rss.setChannel(channel);

		RssCollection response = new RssCollection();
		response.getRssCollection().add(rss);
		return response;
	}

	@Override
	public ServiceStatus publishOperation(Advertisement advertisement) {
		throw new WebServiceException("operation not yet implemented");
	}
}