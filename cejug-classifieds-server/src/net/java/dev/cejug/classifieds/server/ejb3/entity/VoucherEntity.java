package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "voucher")
// @SequenceGenerator(name = "VoucherSequence", sequenceName = "ADV_SEQ", initialValue = 100, allocationSize = 10)
public class VoucherEntity {
	public enum VoucherState {
		NEW, USED, CANCELED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	// , generator="VoucherSequence")
	private Integer id;

	@Column(nullable = false)
	private String pin;

	@JoinColumn(name = "advertisement", nullable = true)
	@OneToOne
	private AdvertisementEntity advertisement;

	@JoinColumn(name = "domain", nullable = false)
	@ManyToOne
	private DomainEntity domain;

	@JoinColumn(name = "customer", nullable = true)
	@ManyToOne
	private CustomerEntity customer;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private VoucherState state;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public AdvertisementEntity getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementEntity advertisement) {
		this.advertisement = advertisement;
	}

	public DomainEntity getDomain() {
		return domain;
	}

	public void setDomain(DomainEntity domain) {
		this.domain = domain;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public VoucherState getState() {
		return state;
	}

	public void setState(VoucherState state) {
		this.state = state;
	}

}