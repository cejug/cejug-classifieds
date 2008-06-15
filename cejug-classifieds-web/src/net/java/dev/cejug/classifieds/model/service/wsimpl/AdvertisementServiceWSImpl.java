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
package net.java.dev.cejug.classifieds.model.service.wsimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.java.dev.cejug.classifieds.model.service.AdvertisementService;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;

/**
 * This implementation is responsible for the communication with the
 * web-services stubs of the classifieds server.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class AdvertisementServiceWSImpl implements AdvertisementService {
	/**
	 * This field is for test purpose only.
	 */
	private List<Advertisement> ads = new ArrayList<Advertisement>();

	@Override
	public Advertisement publish(Advertisement advertisement) {
		// TODO: ID should be included in the Advertisement element.
		// advertisement.setId((int) (Math.random() * 1000000));
		ads.add(advertisement);
		return advertisement;
	}

	@Override
	public List<Advertisement> getAll() {
		return Collections.unmodifiableList(ads);
	}

}
