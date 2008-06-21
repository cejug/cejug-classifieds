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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@NamedQuery(name = "selectCustomerByLoginAndDomain", query = "SELECT c FROM CustomerEntity c WHERE c.domain.id= :d AND c.login= :l")
public class CustomerEntity extends AbstractEntity {

	@Column(name = "LOGIN", nullable = false)
	private String login;

	@JoinColumn(name = "DOMAIN", nullable = false)
	@ManyToOne
	private DomainEntity domain;

	@OneToMany(mappedBy = "customer")
	private Collection<QuotaEntity> quotas;

	public String getLogin() {

		return login;
	}

	public void setLogin(String login) {

		this.login = login;
	}

	public DomainEntity getDomain() {

		return domain;
	}

	public void setDomain(DomainEntity domain) {

		this.domain = domain;
	}

	public Collection<QuotaEntity> getQuotas() {

		return quotas;
	}

	public void setQuotas(Collection<QuotaEntity> quotas) {

		this.quotas = quotas;
	}

}
