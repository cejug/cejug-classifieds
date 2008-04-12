package net.java.dev.cejug.utils.config;

import java.io.InputStreamReader;

import javax.xml.bind.JAXBElement;

public interface ConfigXmlReader<T> {
	public JAXBElement<T> read(InputStreamReader inputStreamReader,
			String context, String schemaLocation) throws Exception;
}
