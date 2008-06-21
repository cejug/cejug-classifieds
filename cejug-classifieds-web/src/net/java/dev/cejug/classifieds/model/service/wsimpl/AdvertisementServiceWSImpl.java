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

import java.util.Collections;
import java.util.List;

import net.java.dev.cejug.classifieds.model.service.AdvertisementService;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementCategory;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementCollectionFilter;
import net.java.dev.cejug.classifieds.server.generated.contract.CategoryCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsBusiness;
import net.java.dev.cejug.classifieds.server.generated.contract.CejugClassifiedsServiceBusiness;

/**
 * This implementation is responsible for the communication with the
 * web-services stubs of the classifieds server.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class AdvertisementServiceWSImpl implements AdvertisementService {

	/**
	 * TODO: this class is eventually not necessary. The service can be used as
	 * injected bean. Or, to use this class as cache strategy, avoiding to call
	 * the service every time.
	 */
	@Override
	public List<Advertisement> getAll() {
		CejugClassifiedsBusiness classifiedsBusinessService = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		AdvertisementCollectionFilter filter = new AdvertisementCollectionFilter();

		// maximum number of records reduces the amount of serialized
		// objects on the wire, enhancing a bit the performance.
		filter.setMaximumNumberOfRecords(10);
		// TODO: the service interface will provide an enumeration of valid
		// categories, but it is not yet available :(
		filter.setCategory("books");
		AdvertisementCollection advertisements = classifiedsBusinessService
				.loadAdvertisementOperation(filter);
		return Collections.unmodifiableList(advertisements.getAdvertisement());
	}

	@Override
	public List<AdvertisementCategory> getCategories() {
		CejugClassifiedsBusiness classifiedsBusinessService = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		CategoryCollection collection = classifiedsBusinessService
				.loadCategoriesOperation();
		return Collections.unmodifiableList(collection
				.getAdvertisementCategory());

	}
}
