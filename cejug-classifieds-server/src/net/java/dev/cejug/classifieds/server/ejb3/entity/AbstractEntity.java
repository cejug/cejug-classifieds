package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entity type.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@MappedSuperclass
public abstract class AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id of the entity.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
