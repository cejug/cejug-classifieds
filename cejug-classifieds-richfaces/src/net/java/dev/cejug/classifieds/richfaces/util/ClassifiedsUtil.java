/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008-2009 CEJUG - Ceará Java Users Group
 
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
package net.java.dev.cejug.classifieds.richfaces.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Message provider: it loads the resource bundle of the project and write
 * messages in the rendering components.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2009-01-25 18:45:55 +0100 (Sun, 25 Jan 2009) $)
 */
public final class ClassifiedsUtil {
	/**
	 * 
	 * @param messageKey
	 * @param typeMessage
	 * @param exception
	 */
	public void addLocalizedMessage(Severity typeMessage, Exception exception,
			String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = ResourceBundle.getBundle("messages",
				facesContext.getViewRoot().getLocale());
		String message = bundle.getString(key);

		facesContext.addMessage(null, new FacesMessage(typeMessage, message,
				null));

		if (exception != null) {
			facesContext.getExternalContext().log(message, exception);
		}
	}

	public static void addMessage(String messageText, Severity typeMessage,
			Exception exception) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		facesContext.addMessage(null, new FacesMessage(typeMessage,
				messageText, null));

		if (exception != null) {
			facesContext.getExternalContext().log(messageText, exception);
		}
	}
}
