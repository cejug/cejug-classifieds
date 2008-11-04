package net.java.dev.cejug.classifieds.richfaces.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "helloClassifiedsBean")
@Scope("request")
public class HelloClassifiedsBean implements ValueChangeListener {

	public HelloClassifiedsBean() {
		service = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		reloadCategories();
	}

	private CejugClassifiedsBusiness service = null;

	private List<AdvertisementCategory> registeredCategories;

	public List<AdvertisementCategory> getRegisteredCategories() {
		return registeredCategories;
	}

	/**
	 * 
	 * @return
	 */
	private List<AdvertisementCategory> reloadCategories() {
		registeredCategories = service.readCategoryBundleOperation(
				new BundleRequest()).getAdvCategory();
		return registeredCategories;
	}

	private String selectedCategory = "";

	public String getSelectedCategory() {
		return selectedCategory;
	}

	public String getHello() {
		return "Hello Classifieds Richfaces :D";
	}

	public List<SelectItem> getCategories() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		// TODO: this should be cached somehow..
		registeredCategories = reloadCategories();
		if (registeredCategories != null) {
			for (AdvertisementCategory cat : registeredCategories) {
				SelectItem item1 = new SelectItem(cat);
				item1.setLabel(cat.getName());
				list.add(item1);
			}
		}
		return list;
	}

	@Override
	public void processValueChange(ValueChangeEvent ae)
			throws AbortProcessingException {
		selectedCategory = "WOW";// ((SelectItem) ae.getNewValue());
	}

}
