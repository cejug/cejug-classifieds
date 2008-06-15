/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Cear� Java Users Group
 
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
 originally used by CEJUG - Cear� Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.model.service;

import java.util.List;

import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;

/**
 * Defines a contract for the Advertisements operations.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public interface AdvertisementService {
	/**
	 * Returns a Advertisement object ready to be shown. Send the advertisement
	 * argument to be stored and returns it populated with additional
	 * informations, if necessary.
	 * 
	 * Note: Always use the returned object because there's no garantee between
	 * the argument and the returned object be the same.
	 * 
	 * @param advertisement
	 * @return A advertisement ready to be shown
	 */
	public Advertisement publish(Advertisement advertisement);

	/**
	 * Returns a list containing all the advertisements.
	 * 
	 * Note: this method is temporary.
	 * 
	 * @return
	 */
	public List<Advertisement> getAll();
}
