package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "author")
@IdClass(AuthorComposedId.class)
public class AuthorEntity {
	@Id
	@Column(name = "partner")
	private int partnerId;
	@Id
	@Column(name = "login")
	private String login;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private Collection<QuotaEntity> quotas;

	@ManyToOne
	@JoinTable(name = "partner", joinColumns = {
			@JoinColumn(name = "id", referencedColumnName = "partner"),
			@JoinColumn(name = "login", referencedColumnName = "login") })
	private PartnerEntity partner;

	@OneToMany(mappedBy = "author", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Collection<AdvertisementEntity> advertisements;

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public PartnerEntity getPartner() {
		return partner;
	}

	public void setPartner(PartnerEntity partner) {
		this.partner = partner;
	}

	public Collection<AdvertisementEntity> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Collection<AdvertisementEntity> advertisements) {
		this.advertisements = advertisements;
	}
}