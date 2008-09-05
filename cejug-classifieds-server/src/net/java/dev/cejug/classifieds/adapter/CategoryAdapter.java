package net.java.dev.cejug.classifieds.adapter;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.CategoryAdapterLocal;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

@Stateless
public class CategoryAdapter extends
		AbstractSoapOrmAdapter<AdvertisementCategory, CategoryEntity> 
          implements CategoryAdapterLocal {

	public CategoryEntity toEntity(AdvertisementCategory advCategory)
			throws IllegalStateException, IllegalArgumentException {

		CategoryEntity category = new CategoryEntity();
		category.setId(advCategory.getEntityId());
		category.setDescription(advCategory.getDescription());
		category.setName(advCategory.getName());

		AdvertisementCategory parent = advCategory.getAdvertisementCategory();
		if (parent != null) {
			category.setParent(toEntity(parent));
		}
		return category;
	}

	public AdvertisementCategory toSoap(CategoryEntity entity)
			throws IllegalStateException, IllegalArgumentException {

		AdvertisementCategory cat = new AdvertisementCategory();
		cat.setEntityId(entity.getId());
		cat.setDescription(entity.getDescription());
		cat.setName(entity.getName());
		cat.setAvailable(entity.getAvailable());

		if (entity.getParent() != null) {
			cat.setAdvertisementCategory(toSoap(entity.getParent()));
		}
		return cat;
	}
}
