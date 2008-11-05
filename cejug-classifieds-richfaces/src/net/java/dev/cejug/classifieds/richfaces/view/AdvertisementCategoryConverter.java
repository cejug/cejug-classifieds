package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

public class AdvertisementCategoryConverter implements Converter, Serializable {

	@Override
	public AdvertisementCategoryWrapper getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		try {
			String [] values = value.split(":");
 			AdvertisementCategoryWrapper ac = new AdvertisementCategoryWrapper();
			ac.setId(Long.parseLong(values[0]));
			ac.setName(values[1]);
			return ac;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value instanceof AdvertisementCategoryWrapper) {
			AdvertisementCategoryWrapper ac = (AdvertisementCategoryWrapper) value;
			return ac.getId() + ":" + ac.getName();
		}
		return null;
	}

}
