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

package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;

/**
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 * 
 */
@Entity
@Table(name = "ADVERTISEMENT")
@NamedQuery(name = AdvertisementEntity.QUERIES.SELECT_BY_CATEGORY, query = "SELECT adv FROM AdvertisementEntity adv WHERE adv.category.id= :catId")
public class AdvertisementEntity extends Advertisement {
	private final static long serialVersionUID = -6026937020915831338L;
	@Transient
	private Collection<AdvertisementKeywordEntity> kewordsCollection = null;

	public static final class QUERIES {
		/**
		 * Parameters:
		 * <ul>
		 * <li><code>CustomerEntity.PARAM_CATEGORY_ID</code>: the ID of the
		 * category</li>
		 * </ul>
		 */
		public static final String SELECT_BY_CATEGORY = "selectByCategory";
		/** {@value} */
		public static final String PARAM_CATEGORY_ID = "catId";
	}

	/**
	 * This assumes a scheduled process (quartz?) that will update the status of
	 * the advertisements.
	 */
	public enum AdvertisementStatus {
		ONLINE, ARCHIVE, CANCELED
	}

	@Transient
	@EJB
	private transient AdvertisementTypeFacadeLocal advTypeFacade;

	// @Transient @EJB private transient CategoryFacadeLocal categoryFacade;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long getEntityId() {
		return super.getEntityId();
	}

	@Column(name = "HEADLINE", nullable = false)
	public String getHeadline() {
		return headline;
	}

	@Column(name = "SUMMARY", nullable = false)
	public String getSummary() {
		return summary;
	}

	@Column(name = "TEXT", nullable = false)
	public String getText() {
		return text;
	}

	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	@ManyToOne
	public CustomerEntity getCustomer() {
		return (CustomerEntity) customer;
	}

	@ManyToMany
	@JoinTable(name = "ADVERTISEMENT_KEYWORD", joinColumns = @JoinColumn(name = "ADVERTISEMENT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "KEYWORD_ID", referencedColumnName = "ID"))
	public Collection<AdvertisementKeywordEntity> getKeywordCollection() {
		// Lookup for the keywords, create the collection and then return...
		if (kewordsCollection == null) {
			kewordsCollection = new ArrayList<AdvertisementKeywordEntity>();
			StringTokenizer tokenizer = new StringTokenizer(keywords, ",;",
					false);
			while (tokenizer.hasMoreTokens()) {
				AdvertisementKeywordEntity advKeyword;
				advKeyword = new AdvertisementKeywordEntity();
				advKeyword.setName(tokenizer.nextToken());
				kewordsCollection.add(advKeyword);
			}

		}
		return kewordsCollection;
	}

	/**
	 * @return the type
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ADVERTISEMENT_TYPE_ID")
	public AdvertisementTypeEntity getType() {
		return advTypeFacade.read(AdvertisementTypeEntity.class, typeId);
	}

	@Column(name = "START", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getStart() {
		return super.publishingPeriod.getStart();
	}

	@Column(name = "FINISH", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFinish() {
		return super.publishingPeriod.getFinish();
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public AdvertisementStatus getState() {
		switch (super.status) {
		case 0:
			return AdvertisementStatus.ARCHIVE;
		case 1:
			return AdvertisementStatus.CANCELED;
		case 2:
			return AdvertisementStatus.ONLINE;
		default:
			throw new WebServiceException("status not defined");
		}
	}

	@ManyToOne
	@JoinColumn(name = "CATEGORY")
	public CategoryEntity getCategory() {
		// categoryFacade.read(CategoryEntity.class, super.categoryId);
		return null;
	}
}
