/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
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
package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.LoadAtomOperationLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;

import org.w3._2005.atom.EntryType;
import org.w3._2005.atom.FeedType;
import org.w3._2005.atom.IdType;
import org.w3._2005.atom.PersonType;
import org.w3._2005.atom.UriType;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Stateless
public class LoadAtomOperation implements LoadAtomOperationLocal {
	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			LoadAtomOperation.class.getName(), "i18n/log");

	public FeedType loadAtomOperation(SyndicationFilter filter) {
		try {
			FeedType atomFeed = new FeedType();
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "title"),
					"Cars @ Cejug Classifieds");
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "subtitle"),
					"All cars");
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "link"),
					"http://cejug-classifieds-server/atom&section=cars");
			atomFeed.getOtherAttributes().put(
					new QName("http://www.w3.org/2005/Atom", "updated"),
					Calendar.getInstance().toString());

			PersonType author = new PersonType();
			author.getNameOrUriOrEmail().add("Cejug-Classifieds");
			author.getNameOrUriOrEmail().add(
					"dev@cejug-classifieds.dev.java.net");
			UriType uri = new UriType();
			uri.setValue("http://cejug-classifieds-server/atom&section=cars");
			author.getNameOrUriOrEmail().add(uri);
			atomFeed.getAuthorOrCategoryOrContributor().add(author);

			IdType feedId = new IdType();
			feedId.setValue("urn:uuid:60a76c80-d399-11d9-b91C-0003939e0af6");
			atomFeed.getAuthorOrCategoryOrContributor().add(feedId);

			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advFacade
					.readAll(AdvertisementEntity.class);

			for (AdvertisementEntity adv : result) {

				EntryType entry = new EntryType();

				entry.getOtherAttributes().put(
						new QName("http://www.w3.org/2005/Atom", "title"),
						adv.getHeadline());
				entry.getOtherAttributes().put(
						new QName("http://www.w3.org/2005/Atom", "link"),
						"http://cejug-classifieds-server/atom&id="
								+ adv.getEntityId());
				atomFeed.getOtherAttributes().put(
						new QName("http://www.w3.org/2005/Atom", "updated"),
						Calendar.getInstance().toString());
				atomFeed.getAuthorOrCategoryOrContributor().add(entry);
			}
			return atomFeed;
		} catch (Exception e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}
}
