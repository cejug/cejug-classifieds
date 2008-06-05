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