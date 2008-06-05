package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "domain")
public class DomainEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true)
	private String domainName;

	@Column(nullable = false)
	private Boolean sharedQuota;

	@Column(nullable = false)
	private String brand;

	@OneToMany
	private Collection<QuotaEntity> quotas;

	public Boolean getSharedQuota() {

		return sharedQuota;
	}

	public void setSharedQuota(Boolean sharedQuota) {

		this.sharedQuota = sharedQuota;
	}

	public String getBrand() {

		return brand;
	}

	public void setBrand(String brand) {

		this.brand = brand;
	}

	public Collection<QuotaEntity> getQuotas() {
		return quotas;
	}

	public void setQuotas(Collection<QuotaEntity> quotas) {
		this.quotas = quotas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

}
