/**
 * 
 */
package net.java.dev.cejug.classifieds.server.ejb3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.java.dev.cejug_classifieds.metadata.common.AbstractEntity;

/**
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
@Entity
@Table(name = "KEYWORD")
public class AdvertisementKeywordEntity extends AbstractEntity {
	private final static long serialVersionUID = -6026937020915831338L;

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	private int id;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

}
