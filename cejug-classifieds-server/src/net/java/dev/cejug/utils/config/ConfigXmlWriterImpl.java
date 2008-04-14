package net.java.dev.cejug.utils.config;

import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEventHandler;

class ConfigXmlWriterImpl<T> implements ConfigXmlWriter<T> {
	private ValidationEventHandler handler;

	ConfigXmlWriterImpl(ValidationEventHandler handler) {
		this.handler = handler;
	}

	/**
	 * WARNING: it never store passwords.
	 * 
	 */
	public void write(JAXBElement<T> config, String context, Writer writer,
			String schemaLocation) throws Exception {
		try {
			JAXBContext jc = JAXBContext.newInstance(context, this.getClass()
					.getClassLoader());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, schemaLocation);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setEventHandler(handler);
			m.marshal(config, writer);
		} catch (Exception exception) {
			throw new Exception(exception.getMessage(), exception);
		}
	}
}
