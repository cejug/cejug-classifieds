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
package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class AdvertisementCategoryConverter implements Converter, Serializable {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public AdvertisementCategoryWrapper getAsObject(FacesContext arg0,
			UIComponent arg1, String value) {
		try {
			String[] values = value.split(":");
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
