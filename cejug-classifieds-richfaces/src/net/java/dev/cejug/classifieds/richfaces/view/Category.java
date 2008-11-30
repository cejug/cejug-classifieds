package net.java.dev.cejug.classifieds.richfaces.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
@Controller(value = "categoryBean")
@Scope("request")
public class Category {
	
	public Category() {
		SERVICE = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		reloadCategories();
	}

	private transient final CejugClassifiedsBusiness SERVICE;
	
	private transient List<AdvertisementCategory> registeredCategories;
	
	private List<AdvertisementCategory> reloadCategories() {
		registeredCategories = SERVICE.readCategoryBundleOperation(
				new BundleRequest()).getAdvCategory();
		return registeredCategories;
	}
	
	public List<SelectItem> getCategories() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		// TODO: this should be cached somehow..
		registeredCategories = reloadCategories();
		if (registeredCategories != null) {
			for (AdvertisementCategory cat : registeredCategories) {
				list.add(new SelectItem(new AdvertisementCategoryWrapper(cat
						.getEntityId(), cat.getName()), cat.getName()));
			}
		}
		return list;
	}
}
