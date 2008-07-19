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
package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.CategoryEntity;

/**
 * @author $Author: rodrigolopes $
 * @version $Rev: 305 $ ($Date: 2008-06-17 19:03:29 +0200 (Tue, 17 Jun 2008) $)
 * @see CRUDEntityFacade
 */
@Stateless
public class CategoryFacade extends CRUDEntityFacade<CategoryEntity> implements
		CategoryFacadeLocal {
	/**
	 * @see <a * href=
	 *      "http://weblogs.java.net/blog/maxpoon/archive/2007/06/extending_the_n_3.html"
	 *      >Extending * the NetBeans Tutorial JSF-JPA-Hibernate Application,
	 *      Part 3 - * Enabling JMX Monitoring on Hibernate v3 and Ehcache 1.3.0
	 *      on * SimpleJpaHibernateApp< /a>
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public int countAdvertisements(CategoryEntity category) {
		Query query = manager
				.createQuery(
						"select COUNT(a) from AdvertisementEntity a WHERE a.category = :cat")
				.setParameter("cat", category);
		try {
			return ((Long) query.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;

		}
	}
}
