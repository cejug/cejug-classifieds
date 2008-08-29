/*
 * @(#) $CVSHeader:  $
 *
 * Copyright (C) 2008 by Netcetera AG.
 * All rights reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Netcetera AG, Switzerland.  The program(s) may be used and/or copied
 * only with the written permission of Netcetera AG or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the program(s) have been supplied.
 *
 * @(#) $Id: codetemplates.xml,v 1.5 2004/06/29 12:49:49 hagger Exp $
 */
package net.java.dev.cejug.classifieds.test.integration;

import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;

/**
 * 
 */
public abstract class AbstractServiceTestCase {

	private static CejugClassifiedsServiceAdmin adminService = null;
	private static CejugClassifiedsServiceBusiness businessService = null;

	public synchronized static final CejugClassifiedsServiceBusiness getBusinessService() {
		if (businessService == null) {
			businessService = new CejugClassifiedsServiceBusiness();
		}
		return businessService;
	}

	public synchronized static final CejugClassifiedsServiceAdmin getAdminService() {
		if (adminService == null) {
			adminService = new CejugClassifiedsServiceAdmin();
		}
		return adminService;
	}
}
