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

import javax.ejb.EJB;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.DomainFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.common.Quota;

/**
 * @author $Author:felipegaucho $
 * @version $Rev:504 $ ($Date:2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
@Entity
@Table(name = "QUOTA")
public class QuotaEntity extends Quota {
	private final static long serialVersionUID = -6026937020915831338L;
	@Transient
	@EJB
	private transient AdvertisementTypeFacadeLocal advTypeFacade;

	@Transient
	@EJB
	private transient CustomerFacadeLocal customerFacade;

	@Transient
	@EJB
	private transient DomainFacadeLocal domainFacade;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public long getId() {
		return entityId;
	}

	@OneToOne
	@JoinColumn(name = "ADVERTISEMENT_TYPE_ID")
	public AdvertisementTypeEntity getType() {
		return advTypeFacade.read(AdvertisementTypeEntity.class,
				getAdvertisementTypeId());

	}

	public Integer getAvailable() {

		return amount;
	}

	/**
	 * @return the customer
	 */
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	public CustomerEntity getCustomer() {
		return customerFacade.findOrCreate(super.domainId, super.customerLogin);
	}

	/**
	 * @return the domain
	 */
	@ManyToOne
	@JoinColumn(name = "DOMAIN_ID", nullable = false)
	public DomainEntity getDomain() {
		return domainFacade.read(DomainEntity.class, super.domainId);
	}

	@Column(name = "AMOUNT", nullable = false)
	public int getAmount() {
		return amount;
	}
}
