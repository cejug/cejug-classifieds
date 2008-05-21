package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "domain")
public class DomainEntity {
	@Column(nullable = false)
	private Boolean sharedQuota;

	@Id
	@Column(nullable = false)
	private String domain;

	@OneToMany(mappedBy = "domain")
	private Collection<AuthorEntity> authors;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Collection<AuthorEntity> getAuthors() {
		return authors;
	}

	public void setAuthors(Collection<AuthorEntity> authors) {
		this.authors = authors;
	}

	public Boolean getSharedQuota() {
		return sharedQuota;
	}

	public void setSharedQuota(Boolean sharedQuota) {
		this.sharedQuota = sharedQuota;
	}

	public String getName() {
		return domain;
	}

	public void setName(String name) {
		this.domain = name;
	}

}