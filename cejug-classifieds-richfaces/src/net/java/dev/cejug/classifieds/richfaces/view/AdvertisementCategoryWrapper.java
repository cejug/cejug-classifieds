package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.Serializable;

public class AdvertisementCategoryWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	public AdvertisementCategoryWrapper() {

	}

	public AdvertisementCategoryWrapper(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public AdvertisementCategoryWrapper(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AdvertisementCategoryWrapper other = (AdvertisementCategoryWrapper) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
