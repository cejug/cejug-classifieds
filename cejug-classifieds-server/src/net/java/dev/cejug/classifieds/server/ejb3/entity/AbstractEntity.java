/*
 * Entity.java Created on 19 de Setembro de 2000, 10:15
 */
package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Entity Class.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;

	/**
	 * @return the id
	 */
	public Integer getId() {

		return id;
	}
}
