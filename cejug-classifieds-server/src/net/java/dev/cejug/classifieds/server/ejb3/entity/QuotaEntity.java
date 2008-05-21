package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quota")
public class QuotaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(mappedBy = "quota", cascade = CascadeType.PERSIST)
	@JoinColumn(name = "advertisement_type")
	private AdvertisementTypeEntity type;

	@SuppressWarnings("unused")
	@JoinColumn(name = "id")
	private AuthorEntity author;

	@Column(nullable = false)
	private Integer available;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdvertisementTypeEntity getType() {
		return type;
	}

	public void setType(AdvertisementTypeEntity type) {
		this.type = type;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}
}