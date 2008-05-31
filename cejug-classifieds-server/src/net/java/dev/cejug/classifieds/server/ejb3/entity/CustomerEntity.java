package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customer", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"login", "domain" }) })
@NamedQuery(name = "selectCustomerByLoginAndDomain", query = "SELECT c FROM CustomerEntity c WHERE c.domain.domain= :d AND c.login= :l")
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "login", nullable = false)
	private String login;

	@JoinColumn(name = "domain", nullable = false)
	@ManyToOne
	private DomainEntity domain;

	@OneToMany
	private Collection<QuotaEntity> quotas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public DomainEntity getDomain() {
		return domain;
	}

	public void setDomain(DomainEntity domain) {
		this.domain = domain;
	}

	public Collection<QuotaEntity> getQuotas() {
		return quotas;
	}

	public void setQuotas(Collection<QuotaEntity> quotas) {
		this.quotas = quotas;
	}

}