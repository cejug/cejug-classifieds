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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author $Author: mar nufelipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
@Entity
@Table(name = "voucher")
public class VoucherEntity {

	public enum VoucherState {
		NEW, ONLINE, USED, CANCELED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	private String pin;

	@JoinColumn(name = "advertisement", nullable = true)
	@OneToOne
	private AdvertisementEntity advertisement;

	@JoinColumn(name = "domain", nullable = false)
	@ManyToOne
	private DomainEntity domain;

	@JoinColumn(name = "customer", nullable = true)
	@ManyToOne
	private CustomerEntity customer;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VoucherState state;

	@JoinColumn(name = "publishingperiod", nullable = false)
	@ManyToOne
	private PublishingPeriodEntity publishingPeriod;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getPin() {

		return pin;
	}

	public void setPin(String pin) {

		this.pin = pin;
	}

	public AdvertisementEntity getAdvertisement() {

		return advertisement;
	}

	public void setAdvertisement(AdvertisementEntity advertisement) {

		this.advertisement = advertisement;
	}

	public DomainEntity getDomain() {

		return domain;
	}

	public void setDomain(DomainEntity domain) {

		this.domain = domain;
	}

	public CustomerEntity getCustomer() {

		return customer;
	}

	public void setCustomer(CustomerEntity customer) {

		this.customer = customer;
	}

	public VoucherState getState() {

		return state;
	}

	public void setState(VoucherState state) {

		this.state = state;
	}

	/**
	 * @return the publishingPeriod
	 */
	public PublishingPeriodEntity getPublishingPeriod() {

		return publishingPeriod;
	}

	/**
	 * @param publishingPeriod
	 *            the publishingPeriod to set
	 */
	public void setPublishingPeriod(PublishingPeriodEntity publishingPeriod) {

		this.publishingPeriod = publishingPeriod;
	}
}
