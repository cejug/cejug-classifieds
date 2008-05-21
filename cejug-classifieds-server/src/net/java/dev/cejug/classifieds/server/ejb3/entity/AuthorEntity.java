package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

//@IdClass(AuthorComposedId.class)
@Entity
@Table(name = "author")
public class AuthorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "login")
	private String login;

	@PrimaryKeyJoinColumn
	private DomainEntity domain;

	@OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
	private Collection<QuotaEntity> quotas;

	@OneToMany(mappedBy = "author", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Collection<AdvertisementEntity> advertisements;

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

	public Collection<AdvertisementEntity> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Collection<AdvertisementEntity> advertisements) {
		this.advertisements = advertisements;
	}

}