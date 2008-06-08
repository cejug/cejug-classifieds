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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Entity
@Table(name = "QUOTA")
public class QuotaEntity extends AbstractEntity {

	@OneToOne
	@JoinColumn(name = "ADVERTISEMENT_TYPE_ID")
	private AdvertisementTypeEntity type;

	@Column(name = "AMOUNT", nullable = false)
	private Integer amount;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private CustomerEntity customer;

	@ManyToOne
	@JoinColumn(name = "DOMAIN_ID", nullable = false)
	private DomainEntity domain;

	public AdvertisementTypeEntity getType() {

		return type;
	}

	public void setType(AdvertisementTypeEntity type) {

		this.type = type;
	}

	public Integer getAvailable() {

		return amount;
	}

	public void setAvailable(Integer available) {

		this.amount = available;
	}

	/**
	 * @return the customer
	 */
	public CustomerEntity getCustomer() {

		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(CustomerEntity customer) {

		this.customer = customer;
	}

	/**
	 * @return the domain
	 */
	public DomainEntity getDomain() {

		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(DomainEntity domain) {

		this.domain = domain;
	}
}
