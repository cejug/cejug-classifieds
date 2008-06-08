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
package net.java.dev.cejug.classifieds.server.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import net.java.dev.cejug.classifieds.server.generated.config.ClassifiedsServerConfig;
import net.java.dev.cejug.utils.config.ConfigXmlReader;
import net.java.dev.cejug.utils.config.XmlStreamFactory;

/**
 * An utility class to load the configuration file. It keeps the latest loaded
 * configuration. The factory facade allows the consumers to get the last loaded
 * configuration or to force the factory to reload the configuration. The
 * loading of the configuration is thread safe.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
public class ConfigLoader {
	public static final String DEFAULT_CONFIG = "config.xml";
	public static final String DEFAULT_CONFIG_SCHEMA = "https://cejug-classifieds.dev.java.net/ws/schema/config/config.xsd";
	public static final String DEFAULT_CONFIG_CONTENT_TYPE = "UTF-8";
	public static final String DEFAULT_JAXB_CONTEXT = ClassifiedsServerConfig.class
			.getPackage().getName();

	private ClassifiedsServerConfig lastLoaded = null;

	private static ConfigLoader instance = new ConfigLoader();

	public static ConfigLoader getInstance() {
		return instance;
	}

	private ConfigLoader() {
	}

	public ClassifiedsServerConfig reload() throws Exception {
		lastLoaded = null;
		return this.load();

	}

	@SuppressWarnings("unchecked")
	public ClassifiedsServerConfig load() throws Exception {
		if (lastLoaded != null) {
			return lastLoaded;
		} else {
			XmlStreamFactory<ClassifiedsServerConfig> factory = new XmlStreamFactory<ClassifiedsServerConfig>();
			ConfigXmlReader<ClassifiedsServerConfig> reader = factory
					.getReader(new ConfigUnmarshallerListener(), null);
			ClassLoader loader = reader.getClass().getClassLoader();
			synchronized (this) {
				InputStream stream = loader.getResourceAsStream(DEFAULT_CONFIG);
				InputStreamReader streamReader = new InputStreamReader(stream,
						Charset.forName(DEFAULT_CONFIG_CONTENT_TYPE));
				lastLoaded = reader.read(streamReader, DEFAULT_JAXB_CONTEXT,
						new URL(DEFAULT_CONFIG_SCHEMA)).getValue();
				return lastLoaded;
			}
		}
	}
}
