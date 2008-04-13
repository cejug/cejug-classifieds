package net.java.dev.cejug.utils.config;

import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.java.dev.cejug.classifieds.server.config.CejugClassifiedsServerConfigValidationHandler;

class ConfigXmlReaderImpl<T> implements ConfigXmlReader<T> {
	private Unmarshaller.Listener listener;

	ConfigXmlReaderImpl(Unmarshaller.Listener listener) {
		this.listener = listener;
	}

	/**
	 * Load a project configuration from a XML input stream.
	 * 
	 * @param inputStream
	 * @param schemaLocation
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JAXBElement<T> read(InputStreamReader inputStream, String context,
			URL schemaLocation) throws Exception {
		// TODO: thread safe
		JAXBContext jc = JAXBContext.newInstance(context, this.getClass()
				.getClassLoader());

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller
				.setEventHandler(new CejugClassifiedsServerConfigValidationHandler());
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
