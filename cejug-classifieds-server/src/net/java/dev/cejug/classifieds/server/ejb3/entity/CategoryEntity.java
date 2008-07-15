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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Entity
@Table(name = "CATEGORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
public class CategoryEntity extends AbstractEntity {

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false)
	private String descripton;

	@Column(name = "AVAILABLE", nullable = false)
	private Boolean available = Boolean.TRUE;

	@ManyToOne
	@JoinColumn(name = "PARENT_ID", nullable = true)
	private CategoryEntity parent;

	@OneToMany(mappedBy = "parent")
	private Collection<CategoryEntity> subCategories;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescripton() {

		return descripton;
	}

	public void setDescripton(String descripton) {

		this.descripton = descripton;
	}

	/**
	 * @return the parent
	 */
	public CategoryEntity getParent() {

		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(CategoryEntity parent) {

		this.parent = parent;
	}

	/**
	 * @return the subCategories
	 */
	public Collection<CategoryEntity> getSubCategories() {

		return subCategories;
	}

	/**
	 * @param subCategories
	 *            the subCategories to set
	 */
	public void setSubCategories(Collection<CategoryEntity> subCategories) {

		this.subCategories = subCategories;
	}

	/**
	 * @return the available
	 */
	public Boolean getAvailable() {

		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(Boolean available) {

		this.available = available;
	}
}
