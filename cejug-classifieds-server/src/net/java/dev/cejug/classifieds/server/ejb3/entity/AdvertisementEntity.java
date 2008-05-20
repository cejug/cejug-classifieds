package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "advertisement")
public class AdvertisementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String summary;
	@Column(nullable = false)
	private String text;
	@Column(nullable = false)
	private String keywords;

	@OneToOne(mappedBy = "advertisement", cascade = CascadeType.PERSIST)
	@JoinColumn(name = "period")
	private PublishingPeriodEntity publishingPeriod;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "author")
	private AuthorEntity author;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PublishingPeriodEntity getPublishingPeriod() {
		return publishingPeriod;
	}

	public void setPublishingPeriod(PublishingPeriodEntity publishingPeriod) {
		this.publishingPeriod = publishingPeriod;
	}

	public AuthorEntity getPublisher() {
		return author;
	}

	public void setPublisher(AuthorEntity publisher) {
		this.author = publisher;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
