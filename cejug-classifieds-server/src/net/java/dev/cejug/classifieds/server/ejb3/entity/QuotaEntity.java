package net.java.dev.cejug.classifieds.server.ejb3.entity;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	@JoinColumn(name = "type")
	private AdvertisementTypeEntity type;

	@Column(nullable = false)
	private Integer amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AdvertisementTypeEntity getType() {
		return type;
	}

	public void setType(AdvertisementTypeEntity type) {
		this.type = type;
	}

	public Integer getAvailable() {
		return amount;
	}

	public void setAvailable(Integer available) {
		this.amount = available;
	}
}