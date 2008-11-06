package net.java.dev.cejug.classifieds.richfaces.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
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
	
	
	public List<Advertisement> getAdvertisements() {
		AdvertisementCollectionFilter filter = new AdvertisementCollectionFilter();
		
		if(getSelectedCategory() != null && getSelectedCategory().getId() !=null){
			// To take the selectedCategory just do this: getSelectedCategory().getId().toString()
			// We can set a default value for the advertisements show when the user access the first time
			// so this 'if' will be unnecessary :)
			//filter.setCategory(getSelectedCategory().getId().toString());
		}
		
		filter.setCategory("3"); 
		
		return service.loadAdvertisementOperation(filter).getAdvertisement();	}

	/**
	 * 
	 * @return
	 */
	private List<AdvertisementCategory> reloadCategories() {
		registeredCategories = service.readCategoryBundleOperation(
				new BundleRequest()).getAdvCategory();
		return registeredCategories;
	}

	private AdvertisementCategoryWrapper selectedCategory = new AdvertisementCategoryWrapper();

	public AdvertisementCategoryWrapper getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(
			AdvertisementCategoryWrapper selectedCategory) {
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
				list.add(new SelectItem(new AdvertisementCategoryWrapper(cat
						.getEntityId(), cat.getName()), cat.getName()));
			}
		}
		return list;
	}

	private String size;

	public String getSize() {
		return this.size == null ? "" : this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public void processValueChange(ValueChangeEvent ae)
			throws AbortProcessingException {
//		setSize(Integer.toString(getAdvertisements(
//				Long.toString(((AdvertisementCategoryWrapper) ae.getNewValue())
//						.getId())).size()));
		// selectedCategory = (SelectItem) ae.getNewValue();
	}
}
