package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;

public class AdvertisementCategoryConverter implements Converter, Serializable {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		try {
			String [] values = value.split(":");
 			AdvertisementCategory ac = new AdvertisementCategory();
			ac.setEntityId(Long.parseLong(values[0]));
			ac.setName(values[1]);
			return ac;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value instanceof AdvertisementCategory) {
			AdvertisementCategory ac = (AdvertisementCategory) value;
			return ac.getEntityId() + ":" + ac.getName();
		}
		return null;
	}

}
