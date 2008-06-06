package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date day;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PeriodState state;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public PeriodState getState() {

		return state;
	}

	public void setState(PeriodState state) {

		this.state = state;
	}

	public Date getDay() {
		return new Date(day.getTime());
	}

	public void setDay(Date day) {
		this.day = new Date(day.getTime());
	}
}
