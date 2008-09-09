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

import com.codeplex.rss2schema.Guid;
import com.codeplex.rss2schema.Image;
import com.codeplex.rss2schema.ObjectFactory;
import com.codeplex.rss2schema.Rss;
import com.codeplex.rss2schema.RssChannel;
import com.codeplex.rss2schema.RssItem;
import com.codeplex.rss2schema.Source;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 * @see <a href=
 *      'http://www.rssboard.org/rss-specification'>http://www.rssboard.org/rss-specifi
 *      c a t i o n < / a >
 */
@Stateless
public class LoadRssOperation implements LoadRssOperationLocal {
	/**
	 * GMT date format, used to print the XML dates in GMT format: {@value} .
	 */
	private static final String GMT_DATE_PATTERN = "EEE MMM dd HH:mm:ss z yyyy";

	public static final SimpleDateFormat rfc822DateFormats[] = new SimpleDateFormat[]{
      new SimpleDateFormat("EEE, d MMM yy HH:mm:ss z"),
      new SimpleDateFormat("EEE, d MMM yy HH:mm z"),
      new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z"),
      new SimpleDateFormat("EEE, d MMM yyyy HH:mm z"), new SimpleDateFormat("d MMM yy HH:mm z"),
      new SimpleDateFormat("d MMM yy HH:mm:ss z"), new SimpleDateFormat("d MMM yyyy HH:mm z"),
      new SimpleDateFormat("d MMM yyyy HH:mm:ss z"),}; 

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
	 *    &lt;RssChannel&gt;
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
	 *       &lt;RssItem&gt;
	 * 
	 *          &lt;title&gt;Star City&lt;/title&gt;
	 *          &lt;link&gt;http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp&lt;/link&gt;
	 *          &lt;description&gt;How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href=&quot;http://howe.iki.rssi.ru/GCTC/gctc_e.htm&quot;&gt;Star City&lt;/a&gt;.&lt;/description&gt;
	 *          &lt;pubDate&gt;Tue, 03 Jun 2003 09:39:21 GMT&lt;/pubDate&gt;
	 *          &lt;guid&gt;http://liftoff.msfc.nasa.gov/2003/06/03.html#RssItem573&lt;/guid&gt;
	 * 
	 *       &lt;/RssItem&gt;
	 *    &lt;/RssChannel&gt;
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
			RssChannel channel = new RssChannel();
			ObjectFactory factory = new ObjectFactory();
			List<Object> channelAttributes = channel.getTitleOrLinkOrDescription(); 
			channelAttributes.add(factory.createRssChannelTitle("TODO: include the section name: " + filter.getCategoryId()));
			channelAttributes.add(factory.createRssChannelCategory(factory.createCategory()));
			channelAttributes.add(factory.createRssChannelCopyright("2008 @ CEJUG Classifieds"));
			channelAttributes.add(factory.createRssChannelLink("http://localhost:8080/cejug-classifieds-server/rss"));
			channelAttributes.add(factory.createRssChannelDescription("TODO: include the section description: getSection().getDescription();"));
			channelAttributes.add(factory.createRssChannelDocs("http://www.codeplex.com/rss2schema"));
			channelAttributes.add(factory.createRssChannelGenerator("Cejug-Classifieds"));
			channelAttributes.add(factory.createRssChannelWebMaster("dev@cejug-classifieds.dev.java.net"));
			channelAttributes.add(factory.createRssChannelManagingEditor("users@cejug-classifieds.dev.java.net"));
			
			Image image = factory.createImage();
			image.setDescription("Cejug-Classifieds");
			image.setLink("https://cejug-classifieds.dev.java.net/");
			image.setHeight(150);
			image.setWidth(150);
			image.setTitle("Cejug-Classifieds");
			image.setUrl("https://cejug-classifieds.dev.java.net/images/logo.jpg");
			
			channelAttributes.add(image);


			DateFormat gmt = new SimpleDateFormat(GMT_DATE_PATTERN, Locale
					.getDefault());
			gmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			for (AdvertisementEntity adv : result) {
				RssItem item = new RssItem();
				List<Object> itemAttributes = item.getTitleOrDescriptionOrLink();
				itemAttributes.add(factory.createRssItemAuthor("dev@cejug-classifieds.dev.java.net ("
                                    + adv.getCustomer().getLogin() + ")"));
				itemAttributes.add(factory.createRssItemTitle(adv.getTitle()));
				Source s = factory.createSource();
				s.setValue(adv.getText());
				itemAttributes.add(factory.createRssItemDescription(adv.getSummary()));
				itemAttributes.add(factory.createRssItemComments("https://cejug-classifieds.dev.java.net/"));
				// itemAttributes.add(factory.createRssItemPubDate(gmt.format(adv.getStart())));

				Guid guid = new Guid();
				guid.setIsPermaLink(Boolean.FALSE);
				guid
						.setValue("http://localhost:8080/cejug-classifieds-server/rss#"
								+ adv.getId());
				itemAttributes.add(factory.createRssItemGuid(guid));
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
@interface MeuTipo {
	Class<? extends MessageElement> getType();
}