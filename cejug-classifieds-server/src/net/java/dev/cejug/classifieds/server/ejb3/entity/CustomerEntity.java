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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.java.dev.cejug_classifieds.metadata.common.Customer;

/**
 * A person that can write and/or read the entries in the classifieds system. A
 * customer should be affiliated to a unique domain. A customer should not be
 * registered in the Cejug-Classifieds system, the customers should be managed
 * by the domain. The only customer information manipulated by the
 * Cejug-Classifieds is the customer login, only to identify the customer and
 * manage the quotas associated to each customer.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Entity
@Table(name = "CUSTOMER", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"LOGIN", "DOMAIN" }) })
@NamedQuery(name = CustomerEntity.QUERIES.SELECT_BY_DOMAIN, query = "SELECT c FROM CustomerEntity c WHERE c.domain.id= :domain AND c.login= :login")
public class CustomerEntity extends Customer {
	private final static long serialVersionUID = -6026937020915831338L;

	public static final class QUERIES {
		/**
		 * Parameters:
		 * <ul>
		 * <li><code>CustomerEntity.QUERIES.PARAM_LOGIN</code>: the customer
		 * login.</li>
		 * <li><code>CustomerEntity.QUERIES.PARAM_DOMAIN</code>: the domain of
		 * the customer</li>
		 * </ul>
		 */
		public static final String SELECT_BY_DOMAIN = "selectByLoginAndDomain";
		/** {@value} */
		public static final String PARAM_LOGIN = "login";
		/** {@value} */
		public static final String PARAM_DOMAIN = "domain";
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return entityId;
	}

	@Column(name = "LOGIN", nullable = false)
	public String getLogin() {
		return login;
	}

	@JoinColumn(name = "DOMAIN", nullable = false)
	@ManyToOne
	public DomainEntity getDomain() {
		// get Domain from ID
		return null;
	}

	/*
	 * @OneToMany(mappedBy = "customer") public Collection<QuotaEntity>
	 * getQuotas() { return quotas; }
	 */
}
