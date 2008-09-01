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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

/**
 * A domain is company or a group of people. The domain should be registered in
 * the Cejug-Classifieds system, and a domain has a unique domain name.
 * 
 * @author $Author:felipegaucho $
 * @version $Rev $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
@Entity
@Table(name = "DOMAIN")
public class DomainEntity extends Domain {
	/**
	 * 
	 */
	private final static long serialVersionUID = -6026937020915831338L;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return entityId;
	}

	@Column(name = "SHARED_COTA", nullable = false)
	public boolean isSharedQuota() {
		return sharedQuota;
	}

	// @OneToMany(mappedBy = "domain")
	// private Collection<QuotaEntity> quotas;

	@Column(name = "BRAND", nullable = false)
	public String getBrand() {
		return brand;
	}

	@Column(name = "URI", nullable = false, unique = true)
	public String getUri() {
		return uri;
	}

	/**
	 * @return the categories
	 */
	@ManyToMany
	@JoinTable(name = "DOMAIN_CATEGORY", joinColumns = @JoinColumn(name = "DOMAIN_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID"))
	public List<AdvertisementCategory> getAdvertisementCategory() {
		return super.getAdvertisementCategory();
	}
}
