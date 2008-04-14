package net.java.dev.cejug.utils.config;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;

public class XmlStreamFactory<T> {
	/** @return new JaxbCurriculumReader(). */
	public ConfigXmlReader<T> getReader(Unmarshaller.Listener listener,
			ValidationEventHandler handler) {
		return new ConfigXmlReaderImpl<T>(listener, handler);
	}

	/** @return new JaxbCurriculumWriter(). */
	public ConfigXmlWriter<T> getWriter(ValidationEventHandler handler) {
		return new ConfigXmlWriterImpl<T>(handler);
	}
}
