package net.java.dev.cejug.classifieds.server.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import net.java.dev.cejug.classifieds.server.generated.config.ClassifiedsServerConfig;
import net.java.dev.cejug.classifieds.server.generated.contract.ClassifiedsServiceInterface;
import net.java.dev.cejug.utils.config.ConfigXmlReader;
import net.java.dev.cejug.utils.config.XmlStreamFactory;

public class ConfigWrapper {
	public static final String DEFAULT_CONFIG = "config.xml";
	public static final String DEFAULT_CONFIG_SCHEMA = "https://cejug-classifieds.dev.java.net/ws/schema/config/config.xsd";
	public static final String DEFAULT_CONFIG_CONTENT_TYPE = "UTF-8";
	public static final String DEFAULT_JAXB_CONTEXT = ClassifiedsServiceInterface.class
			.getPackage().getName();

	private ClassifiedsServerConfig config = null;

	private static ConfigWrapper instance = null;

	public static ConfigWrapper getInstance() throws Exception {
		if (instance == null) {
			instance = new ConfigWrapper();
			instance.config = ConfigWrapper.load();
		}
		return instance;
	}

	private ConfigWrapper() {
	}

	@SuppressWarnings("unchecked")
	private static ClassifiedsServerConfig load() throws Exception {
		XmlStreamFactory<ClassifiedsServerConfig> factory = new XmlStreamFactory<ClassifiedsServerConfig>();
		ConfigXmlReader<ClassifiedsServerConfig> reader = factory
				.getReader(new CejugClassifiedsServerConfigUnmarshallerListener());
		ClassLoader loader = reader.getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream(DEFAULT_CONFIG);
		InputStreamReader streamReader = new InputStreamReader(stream, Charset
				.forName(DEFAULT_CONFIG_CONTENT_TYPE));
		return reader.read(streamReader, DEFAULT_JAXB_CONTEXT,
				new URL(DEFAULT_CONFIG_SCHEMA)).getValue();
	}

	public Logger getLogger() {
		config.getInjection().getLogger();
		return null;
	}
}
