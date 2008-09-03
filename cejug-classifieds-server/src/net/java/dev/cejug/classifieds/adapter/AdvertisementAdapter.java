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
package net.java.dev.cejug.classifieds.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.ejb.EJB;

import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.AdvertisementKeywordEntity;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.CategoryFacadeLocal;
import net.java.dev.cejug.classifieds.entity.facade.CustomerFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Period;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: $ ($Date: 2008-08-24 11:22:52 +0200 (Sun, 24 Aug 2008) $)
 */
public class AdvertisementAdapter extends
		SoapOrmAdapter<Advertisement, AdvertisementEntity> {
	@EJB
	private transient CategoryFacadeLocal categoryFacade;

	@EJB
	private transient CustomerFacadeLocal customerFacade;

	@EJB
	private transient AdvertisementTypeFacadeLocal advTypeFacade;

	@Override
	public AdvertisementEntity toEntity(Advertisement soap)
			throws IllegalStateException, IllegalArgumentException {
		AdvertisementEntity entity = new AdvertisementEntity();
		entity.setCategory(categoryFacade.read(soap.getCategoryId()));
		entity.setCustomer(customerFacade.findOrCreate(soap.getCustomer()
				.getDomainId(), soap.getCustomer().getLogin()));
		Period period = soap.getPublishingPeriod();
		entity.setFinish(period.getFinish());
		entity.setStart(period.getStart());
		entity.setId(soap.getEntityId());
		// TODO: split the string representation
		entity.setKeywords(splitKeywords(soap.getKeywords()));
		switch (soap.getStatus()) {
		case 1:
			entity.setState(AdvertisementEntity.AdvertisementStatus.ARCHIVE);
			break;
		case 2:
			entity.setState(AdvertisementEntity.AdvertisementStatus.CANCELED);
			break;
		case 3:
			entity.setState(AdvertisementEntity.AdvertisementStatus.ONLINE);
			break;
		}
		entity.setSummary(soap.getSummary());
		entity.setText(soap.getText());
		entity.setTitle(soap.getHeadline());
		entity.setType(advTypeFacade.read(soap.getTypeId()));
		return entity;
	}

	@Override
	public Advertisement toSoap(AdvertisementEntity entity)
			throws IllegalStateException, IllegalArgumentException {
		Advertisement adv = new Advertisement();
		CategoryEntity category = entity.getCategory();
		if (category != null) {
			adv.setCategoryId(category.getId());
		}
		CustomerAdapter adapter = new CustomerAdapter();
		adv.setCustomer(adapter.toSoap(entity.getCustomer()));
		adv.setEntityId(entity.getId());
		adv.setHeadline(entity.getTitle());
		adv.setKeywords(mergeKeywords(entity.getKeywords()));
		// TODO: adv.setLocale(entity.get)
		Period period = new Period();
		period.setStart(entity.getStart());
		period.setFinish(entity.getFinish());
		adv.setPublishingPeriod(period);
		// TODO: adv.setStatus(entity.getState());
		adv.setSummary(entity.getSummary());
		adv.setText(entity.getText());
		adv.setTypeId(entity.getType().getId());

		// TODO Auto-generated method stub
		return null;
	}

	private String mergeKeywords(Collection<AdvertisementKeywordEntity> keywords) {
		String keyword = "";
		for (AdvertisementKeywordEntity key : keywords) {
			keyword += key;
			keyword += ";";
		}
		return keyword;
	}

	private Collection<AdvertisementKeywordEntity> splitKeywords(String keywords) {
		Collection<AdvertisementKeywordEntity> collection = new ArrayList<AdvertisementKeywordEntity>();
		StringTokenizer tokenizer = new StringTokenizer(keywords, ";,", false);
		while (tokenizer.hasMoreTokens()) {
			collection
					.add(new AdvertisementKeywordEntity(tokenizer.nextToken()));
		}
		return collection;
	}

}
