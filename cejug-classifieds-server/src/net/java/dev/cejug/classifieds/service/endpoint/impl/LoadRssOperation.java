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
package net.java.dev.cejug.classifieds.service.endpoint.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.LoadRssOperationLocal;
import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.SyndicationFilter;
import net.java.dev.cejug_classifieds.metadata.common.MessageElement;
import net.java.dev.cejug_classifieds.rss.Channel;
import net.java.dev.cejug_classifieds.rss.Item;
import net.java.dev.cejug_classifieds.rss.Rss;
import net.java.dev.cejug_classifieds.rss.TGuid;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 577 $ ($Date: 2008-09-03 18:57:21 +0200 (Wed, 03 Sep 2008) $)
 * @see <a href='http://www.rssboard.org/rss-specification'>http://www.rssboard.org/rss-specification
 *      < / a >
 */
@Stateless
public class LoadRssOperation implements LoadRssOperationLocal {
	/**
	 * GMT date format, used to print the XML dates in GMT format: {@value} .
	 */
	private static final String GMT_DATE_PATTERN = "EEE MMM dd HH:mm:ss z yyyy";

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
			LoadRssOperation.class.getName(), "i18n/log");

	/**
	 * Produces an RSS document with the latest active advertisements in the
	 * database. The response XML document should have the same format as the <a
	 * href='http://cyber.law.harvard.edu/rss/examples/rss2sample.xml'> sample
	 * RSS</a> below:
	 * 
	 * <pre>
	 * &lt;?xml version=&quot;1.0&quot;?&gt;
	 * &lt;rss version=&quot;2.0&quot;&gt;
	 *    &lt;channel&gt;
	 *       &lt;title&gt;Liftoff News&lt;/title&gt;
	 *       &lt;link&gt;http://liftoff.msfc.nasa.gov/&lt;/link&gt;
	 *       &lt;description&gt;Liftoff to Space Exploration.&lt;/description&gt;
	 *       &lt;language&gt;en-us&lt;/language&gt;
	 *       &lt;pubDate&gt;Tue, 10 Jun 2003 04:00:00 GMT&lt;/pubDate&gt;
	 * 
	 *       &lt;lastBuildDate&gt;Tue, 10 Jun 2003 09:41:01 GMT&lt;/lastBuildDate&gt;
	 *       &lt;docs&gt;http://blogs.law.harvard.edu/tech/rss&lt;/docs&gt;
	 *       &lt;generator&gt;Weblog Editor 2.0&lt;/generator&gt;
	 *       &lt;managingEditor&gt;editor@example.com&lt;/managingEditor&gt;
	 *       &lt;webMaster&gt;webmaster@example.com&lt;/webMaster&gt;
	 *       &lt;item&gt;
	 * 
	 *          &lt;title&gt;Star City&lt;/title&gt;
	 *          &lt;link&gt;http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp&lt;/link&gt;
	 *          &lt;description&gt;How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href=&quot;http://howe.iki.rssi.ru/GCTC/gctc_e.htm&quot;&gt;Star City&lt;/a&gt;.&lt;/description&gt;
	 *          &lt;pubDate&gt;Tue, 03 Jun 2003 09:39:21 GMT&lt;/pubDate&gt;
	 *          &lt;guid&gt;http://liftoff.msfc.nasa.gov/2003/06/03.html#item573&lt;/guid&gt;
	 * 
	 *       &lt;/item&gt;
	 *    &lt;/channel&gt;
	 * &lt;/rss&gt;
	 * </pre>
	 * 
	 * @return an XML document containing the advetisements RSS.
	 */
	public Rss loadRssOperation(SyndicationFilter filter) {
		try {

			// TODO: converter filter in a map of parameters...
			List<AdvertisementEntity> result = advFacade.readByCategory(filter
					.getCategoryId());

			Rss rssFeed = new Rss();
			rssFeed.getOtherAttributes().put(new QName("", "version"), "2.0");
			Channel channel = new Channel();
			channel.setTitle("TODO: include the section name: "
					+ filter.getCategoryId());
			channel
					.setLink("http://localhost:8080/cejug-classifieds-server/rss");
			channel
					.setDescription("TODO: include the section description: getSection().getDescription();");

			DateFormat gmt = new SimpleDateFormat(GMT_DATE_PATTERN, Locale
					.getDefault());
			gmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			for (AdvertisementEntity adv : result) {
				Item item = new Item();
				item.setAuthor("dev@cejug-classifieds.dev.java.net ("
						+ adv.getCustomer().getLogin() + ")");
				item.setTitle(adv.getTitle());
				item.setDescription(adv.getText());
				item.setComments(adv.getSummary());
				item.setPubDate(gmt.format(adv.getStart().getTime()));
				TGuid guid = new TGuid();
				guid.setIsPermaLink(Boolean.FALSE);
				guid
						.setValue("http://localhost:8080/cejug-classifieds-server/rss#"
								+ adv.getId());
				item.setGuid(guid);
				channel.getItem().add(item);
			}
			rssFeed.setChannel(channel);
			return rssFeed;
		} catch (Exception e) {
			// TODO: log
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}
}

// @Retention(// a sua retention policy) //opicional
// @Target(CLASS) //opcional, tem um import aqui que eu não lembro... 
@interface MeuTipo{
     Class<? extends MessageElement> getType();
}