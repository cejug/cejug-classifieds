package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "login")
	private String login;

	@ManyToOne
	@JoinColumn(name = "domain_id")
	private DomainEntity domain;

	@OneToMany
	private Collection<QuotaEntity> quotas;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Collection<QuotaEntity> getQuotas() {
		return quotas;
	}

	public void setQuotas(Collection<QuotaEntity> quotas) {
		this.quotas = quotas;
	}

	public DomainEntity getDomain() {
		return domain;
	}

	public void setDomain(DomainEntity domain) {
		this.domain = domain;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}