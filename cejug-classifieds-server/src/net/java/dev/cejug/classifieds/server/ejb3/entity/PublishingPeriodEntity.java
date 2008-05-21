package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "publishing_period")
public class PublishingPeriodEntity {
	public enum PeriodState {
		NEW, OPEN, CLOSED, CANCELED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date start;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date finish;

	@Column(nullable = false)
	private TimeZone timezone;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PeriodState state;

	@SuppressWarnings("unused")
	@PrimaryKeyJoinColumn
	private AdvertisementEntity advertisement;

	public Integer getId() {
		return id;
	}

	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

	public AdvertisementEntity getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementEntity advertisement) {
		this.advertisement = advertisement;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public PeriodState getState() {
		return state;
	}

	public void setState(PeriodState state) {
		this.state = state;
	}
}