/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 Felipe GaÃºcho
 
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
 originally used by CEJUG - Cearï¿½ Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.test.integration.admin;

import net.java.dev.cejug.classifieds.test.integration.AbstractServiceTestCase;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorQuery;
import net.java.dev.cejug_classifieds.metadata.admin.MonitorResponse;

import org.junit.Assert;
import org.junit.Test;
import  static org.junit.Assert.assertNotNull;;

/**
 * Test the diploma validation operation.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class CheckMonitorIntegrationTest extends AbstractServiceTestCase {
	@Test
	public void checkMonitor() {
		/*
		 * check if the test advertisement comes with the RSS
		 */
		CejugClassifiedsAdmin service = getAdminService()
				.getCejugClassifiedsAdmin();
		MonitorQuery query = new MonitorQuery();
		query.setAlivePeriodsLength(5);
		query.setResponseTimeLength(30);
		query.setQuery("");
		MonitorResponse response = service.checkMonitorOperation(query);
		assertNotNull(response.getServiceName() );
	}
}
