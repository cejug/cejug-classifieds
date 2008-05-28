package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "domain")
@NamedQuery(name = "selectDomainByname", query = "SELECT d FROM DomainEntity d WHERE d.name= :domain")
public class DomainEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Boolean sharedQuota;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private TimeZone timezone;

	public String getDomain() {
		return name;
	}

	public void setDomain(String domain) {
		this.name = domain;
	}

	public Boolean getSharedQuota() {
		return sharedQuota;
	}

	public void setSharedQuota(Boolean sharedQuota) {
		this.sharedQuota = sharedQuota;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

}