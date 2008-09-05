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
package net.java.dev.cejug.classifieds.business.interfaces;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.adapter.SoapOrmAdapter;
import net.java.dev.cejug.classifieds.entity.CustomerEntity;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

@Local
public interface CustomerAdapterLocal extends SoapOrmAdapter<Customer, CustomerEntity> {

}
