package net.java.dev.cejug.classifieds.test.integration;

import java.util.Random;
import java.util.TimeZone;

import net.java.dev.cejug_classifieds.admin.CejugClassifiedsAdmin;
import net.java.dev.cejug_classifieds.admin.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.CreateDomainParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteCategoryParam;
import net.java.dev.cejug_classifieds.metadata.admin.DeleteDomainParam;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.Domain;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

public class AdminClientMock {
	private Random random = new Random();
	private CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin()
			.getCejugClassifiedsAdmin();

	public CejugClassifiedsAdmin getAdmin() {
		return admin;
	}

	public ServiceStatus deleteDomain(Domain domain) {
		// DELETE
		// remove or inactive the test advertisement
		DeleteDomainParam deleteParam = new DeleteDomainParam();
		deleteParam.setPrimaryKey(domain.getEntityId());
		ServiceStatus deleteStatus = admin.deleteDomainOperation(deleteParam);
		return deleteStatus;
	}

	public Domain createDomain() {
		// CREATE
		// TODO: review (it is only a test)
		Domain domain = new Domain();
		String name = "test." + random.nextInt() + "." + random.nextInt();
		domain.setUri(name);
		domain.setBrand("Functional Domain");
		domain.setSharedQuota(false);
		domain.setTimezone(TimeZone.getDefault().getDisplayName());
		CreateDomainParam createParam = new CreateDomainParam();
		createParam.setDomain(domain);
		domain = admin.createDomainOperation(createParam);
		return domain;
	}

	public AdvertisementType createAdvType() {
		AdvertisementType type = new AdvertisementType();
		type.setDescription("oo");
		type.setMaxAttachmentSize(300);
		type.setName("courtesy");
		type.setMaxTextLength(250);
		CreateAdvertisementTypeParam advParam = new CreateAdvertisementTypeParam();
		advParam.setAdvertisementType(type);
		return admin.createAdvertisementTypeOperation(advParam);
	}

	public AdvertisementCategory createCategory() {
		AdvertisementCategory category = new AdvertisementCategory();
		category.setName("test.cat." + random.nextInt() + "."
				+ random.nextInt());
		category
				.setDescription("This category was created just for testing, feel free to delete it");
		CreateCategoryParam catParam = new CreateCategoryParam();
		catParam.setAdvertisementCategory(category);
		return admin.createCategoryOperation(catParam);
	}

	public void tryToDeleteAdvType(AdvertisementType type) {
		if (type != null) {
			try {
				admin.deleteAdvertisementTypeOperation(type.getEntityId());
			} catch (Exception error) {
				System.out.println("Impossible to delete AdvertisementType #"
						+ type.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}

	public void tryToDeleteDomain(Domain domain) {
		if (domain != null) {
			try {
				DeleteDomainParam param = new DeleteDomainParam();
				param.setPrimaryKey(domain.getEntityId());
				admin.deleteDomainOperation(param);
			} catch (Exception error) {
				System.out.println("Impossible to delete AdvertisementType #"
						+ domain.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}

	public void tryToDeleteCategory(AdvertisementCategory category) {
		if (category != null) {
			try {
				DeleteCategoryParam param = new DeleteCategoryParam();
				param.setPrimaryKey(category.getEntityId());
				admin.deleteCategoryOperation(param);
			} catch (Exception error) {
				System.out.println("Impossible to delete AdvertisementType #"
						+ category.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}

	public void tryToDeleteAdvertisement(Advertisement adv) {
		if (adv != null) {
			try {
				admin.deleteAdvertisementTypeOperation(adv.getEntityId());
			} catch (Exception error) {
				System.out.println("Impossible to delete AdvertisementType #"
						+ adv.getEntityId());
				// ignore errors.. it is just a trial.
			}
		}
	}
}
