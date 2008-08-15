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
package net.java.dev.cejug.utils.config;

import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
class ConfigXmlReaderImpl<T> implements ConfigXmlReader<T> {
	private Unmarshaller.Listener listener;
	private ValidationEventHandler handler;

	ConfigXmlReaderImpl() {
		this(null, null);
	}

	ConfigXmlReaderImpl(Unmarshaller.Listener listener,
			ValidationEventHandler handler) {
		this.listener = listener;
		this.handler = handler;
	}

	/**
	 * Load a project configuration from a XML input stream.
	 * 
	 * @param inputStream
	 * @param schemaLocation
	 * @return
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JAXBElement<T> read(InputStreamReader inputStream, String context,
			URL schemaLocation) throws JAXBException, SAXException {
		// TODO: thread safe
		JAXBContext jc = JAXBContext.newInstance(context, this.getClass()
				.getClassLoader());

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		if (handler != null) {
			unmarshaller.setEventHandler(handler);
		}
		if (listener != null) {
			unmarshaller.setListener(listener);
		}
		SchemaFactory sf = SchemaFactory
				.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(schemaLocation);
		unmarshaller.setSchema(schema);

		return (JAXBElement<T>) unmarshaller.unmarshal(inputStream);
	}
}
