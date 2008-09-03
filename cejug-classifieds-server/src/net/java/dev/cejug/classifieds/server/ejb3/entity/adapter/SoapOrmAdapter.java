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
package net.java.dev.cejug.classifieds.server.ejb3.entity.adapter;

public abstract class SoapOrmAdapter<SoapType, EntityType> {

	/**
	 * Do-nothing constructor for the derived classes.
	 */
	protected SoapOrmAdapter() {
	}

	/**
	 * Copies the attribute values from an Entity to an object that can be
	 * serialized in SOAP messages.
	 * 
	 * @param type
	 *            the JPA entity.
	 * @return a soap object containing the values of the entity.
	 */
	public abstract SoapType toSoap(EntityType type)
			throws IllegalStateException, IllegalArgumentException;

	/**
	 * Copies the attribute values from an object that can be serialized in SOAP
	 * messages into an Entity object.
	 * 
	 * @param type
	 *            a soap object containing the values of the entity.
	 * @return the JPA entity.
	 */
	public abstract EntityType toEntity(SoapType type)
			throws IllegalStateException, IllegalArgumentException;
}
