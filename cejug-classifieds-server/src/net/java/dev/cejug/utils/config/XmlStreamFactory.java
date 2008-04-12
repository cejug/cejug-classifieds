package net.java.dev.cejug.utils.config;

import javax.xml.bind.Unmarshaller;

public class XmlStreamFactory<T> {
	/** @return new JaxbCurriculumReader(). */
	public ConfigXmlReader<T> getReader(Unmarshaller.Listener listener) {
		return new ConfigXmlReaderImpl<T>(listener);
	}

	/** @return new JaxbCurriculumWriter(). */
	public ConfigXmlWriter<T> getWriter() {
		return new ConfigXmlWriterImpl<T>();
	}
}
