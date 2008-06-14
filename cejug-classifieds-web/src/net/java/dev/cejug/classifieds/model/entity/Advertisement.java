package net.java.dev.cejug.classifieds.model.entity;
/**
 * This is the entity class for the advertisements.
 * @author Tarso Bessa
 *
 */
public class Advertisement {
	private Integer id;
	private String title;
	private String summary;
	private String text;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
