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

import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author $Author: mar nufelipegaucho $
 * @version $Rev: 355 $ ($Date: 2007-12-12 21:30:02 +0100 (Wed, 12 Dec 2007) $)
 */
@Entity
@Table(name = "timetable")
public class TimetableEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	/**
	 * This assumes a scheduled process (quartz?) that will update the status of
	 * the time slots.
	 */
	public enum AllocationStatus {
		ONLINE, ARCHIVE, CANCELED
	}

	@JoinColumn(name = "advertisement", nullable = true)
	@OneToOne
	private AdvertisementEntity advertisement;

	@JoinColumn(name = "domainName", nullable = false)
	@ManyToOne
	private DomainEntity domain;

	@JoinColumn(name = "customer", nullable = true)
	@ManyToOne
	private CustomerEntity customer;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar start;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar finish;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AllocationStatus state;

	@JoinColumn(name = "publishingperiod", nullable = false)
	@ManyToOne
	private PublishingPeriodEntity publishingPeriod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getFinish() {
		return finish;
	}

	public void setFinish(Calendar finish) {
		this.finish = finish;
	}

	public AllocationStatus getState() {
		return state;
	}

	public void setState(AllocationStatus state) {
		this.state = state;
	}

	public PublishingPeriodEntity getPublishingPeriod() {
		return publishingPeriod;
	}

	public void setPublishingPeriod(PublishingPeriodEntity publishingPeriod) {
		this.publishingPeriod = publishingPeriod;
	}
}