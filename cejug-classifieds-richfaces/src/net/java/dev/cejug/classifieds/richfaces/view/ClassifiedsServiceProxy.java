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
public class ClassifiedsServiceProxy implements ValueChangeListener {

	public ClassifiedsServiceProxy() {
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

	private AdvertisementCategory selectedCategory = new AdvertisementCategory();

	public AdvertisementCategory getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(AdvertisementCategory selectedCategory) {
		this.selectedCategory = selectedCategory;
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
				list.add(new SelectItem(cat, cat.getName()));
			}
		}
		return list;
	}

	@Override
	public void processValueChange(ValueChangeEvent ae)
			throws AbortProcessingException {
		//selectedCategory = (SelectItem) ae.getNewValue();
	}
	
}
