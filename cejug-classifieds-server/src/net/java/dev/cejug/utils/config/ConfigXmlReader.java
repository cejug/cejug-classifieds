package net.java.dev.cejug.utils.config;

import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.bind.JAXBElement;

public interface ConfigXmlReader<T> {
	public JAXBElement<T> read(InputStreamReader inputStreamReader,
			String context, URL schemaLocation) throws Exception;
}
