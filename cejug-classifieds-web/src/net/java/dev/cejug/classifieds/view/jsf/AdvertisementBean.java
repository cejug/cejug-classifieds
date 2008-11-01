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
package net.java.dev.cejug.classifieds.view.jsf;

import java.util.List;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for the operations related to advertisement. The
 * instances of this class will be managed by the Spring Container.
 * 
 * @author $Author$
 * @author Tarso Bessa
 * @version $Rev$ ($Date$)
 */
@Component
@Scope(value = "request")
public class AdvertisementBean {
	/**
	 * The variable name used to store the ads list int the
	 * <code>PageFlowScope</code>.
	 */
	private static final String ADS_LIST = "ADS_LIST";

	/**
	 * The category name used for searching advertisements.
	 */
	private String categoryName;

	/**
	 * The contract used to comunicate with the server. The implementation is
	 * injected by Spring Container.
	 */
	@Autowired
	private CejugClassifiedsBusiness classifiedsBusiness;

	private Advertisement selectedAd;

	/**
	 * Returns a list with all advertisements from the server.
	 * 
	 * @return
	 */
	public List<Advertisement> getAdvertisements() {
		return (List<Advertisement>) RequestContextUtils.get(ADS_LIST);
	}

	/**
	 * Searchs the advertisements associated with a category named
	 * <code>name</code>.
	 * 
	 * @param name
	 *            category name for searching
	 */
	public void searchByCategory() {

		AdvertisementCollectionFilter acf = new AdvertisementCollectionFilter();
		acf.setCategory(categoryName);
		/*
		 * the ads list needs to be persistent between requests and it is added
		 * in PageFlowScope.
		 */
		RequestContextUtils
				.put(ADS_LIST, classifiedsBusiness.loadAdvertisementOperation(
						new AdvertisementCollectionFilter()).getAdvertisement());

	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Advertisement getSelectedAd() {
		return selectedAd;
	}

	public void setSelectedAd(Advertisement selectedAd) {
		this.selectedAd = selectedAd;
	}

}