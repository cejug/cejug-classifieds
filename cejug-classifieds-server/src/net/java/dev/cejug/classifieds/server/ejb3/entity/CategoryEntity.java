/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.server.ejb3.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Entity
@Table(name = "CATEGORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
public class CategoryEntity extends AdvertisementCategory {
	private final static long serialVersionUID = -6026937020915831338L;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return entityId;
	}

	@OneToMany(mappedBy = "parent")
	private Collection<CategoryEntity> subCategories;

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}

	/**
	 * @return the parent
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ID", nullable = true)
	public CategoryEntity getParent() {
		// TODO: to check this....
		return (CategoryEntity) super.getAdvertisementCategory();
	}

	/**
	 * @return the subCategories
	 */
	public Collection<CategoryEntity> getSubCategories() {

		return subCategories;
	}

	/**
	 * @return the available
	 */
	@Transient
	public int getAvailable() {
		return available;
	}
}
