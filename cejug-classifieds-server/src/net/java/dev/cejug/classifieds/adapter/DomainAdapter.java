package net.java.dev.cejug.classifieds.adapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.business.interfaces.DomainAdapterLocal;
import net.java.dev.cejug.classifieds.entity.CategoryEntity;
import net.java.dev.cejug.classifieds.entity.DomainEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.Domain;

@Stateless
public class DomainAdapter extends AbstractSoapOrmAdapter<Domain, DomainEntity> 
        implements DomainAdapterLocal {

	public DomainEntity toEntity(Domain domain) throws IllegalStateException,
			IllegalArgumentException {
		DomainEntity domainEntity = new DomainEntity();
		domainEntity.setDomainName(domain.getUri());
		domainEntity.setSharedQuota(domain.isSharedQuota());
		domainEntity.setBrand(domain.getBrand());
		Collection<CategoryEntity> categories = new ArrayList<CategoryEntity>();
		if (domain.getAdvertisementCategory() != null) {
			CategoryAdapter categoryAdapter = new CategoryAdapter();
			for (AdvertisementCategory category : domain
					.getAdvertisementCategory()) {
				CategoryEntity categoryEntity = categoryAdapter
						.toEntity(category);
				categories.add(categoryEntity);
			}
		}
		domainEntity.setCategories(categories);
		return domainEntity;
	}

	public Domain toSoap(DomainEntity domainEntity)
			throws IllegalStateException, IllegalArgumentException {
		Domain domain = new Domain();
		domain.setEntityId(domainEntity.getId());
		domain.setBrand(domainEntity.getBrand());
		domain.setUri(domainEntity.getDomainName());
		domain.setSharedQuota(domainEntity.getSharedQuota());

		if (domainEntity.getCategories() != null) {
			CategoryAdapter categoryAdapter = new CategoryAdapter();
			for (CategoryEntity categoryEntity : domainEntity.getCategories()) {
				AdvertisementCategory category = categoryAdapter
						.toSoap(categoryEntity);
				domain.getAdvertisementCategory().add(category);
			}
		}
		return domain;
	}
}
