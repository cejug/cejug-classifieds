package net.java.dev.cejug.utils.config;

import java.io.Writer;

import javax.xml.bind.JAXBElement;

public interface ConfigXmlWriter<T> {
	/**
	 * Thread safe config writer.
	 * 
	 * @param config
	 * @param context
	 * @param streamWriter
	 * @throws Exception
	 */
	public void write(JAXBElement<T> config, String context,
			Writer streamWriter, String schemaLocation) throws Exception;
}
