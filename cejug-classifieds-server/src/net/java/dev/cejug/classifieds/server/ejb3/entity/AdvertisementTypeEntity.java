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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Entity
@Table(name = "ADVERTISEMENT_TYPE")
public class AdvertisementTypeEntity extends AdvertisementType implements
		Comparable<AdvertisementTypeEntity> {
	private final static long serialVersionUID = -6026937020915831338L;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long getId() {
		return entityId;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	@Column(name = "DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}

	@Column(name = "TEXT_LENGTH", nullable = false)
	public long getMaxTextLength() {
		return maxTextLength;
	}

	@Column(name = "MAX_ATTACHMENT_SIZE", nullable = false)
	public long getMaxAttachmentSize() {
		return maxAttachmentSize;
	}

	@Override
	public int compareTo(final AdvertisementTypeEntity other) {
		long thisId;
		thisId = getEntityId();
		long otherId;
		otherId = other.getId();
		return Long.valueOf(thisId - otherId).intValue();

	}

	@Override
	public boolean equals(final Object obj) {

		return (obj instanceof AdvertisementTypeEntity)
				&& compareTo((AdvertisementTypeEntity) obj) == 0;
	}

	@Override
	public int hashCode() {
		long hashCode = getId();
		return Long.valueOf(hashCode).intValue();
	}
}
