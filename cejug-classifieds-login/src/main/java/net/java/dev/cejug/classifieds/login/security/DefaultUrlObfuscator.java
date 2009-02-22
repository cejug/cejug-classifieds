/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008-2009 CEJUG - Ceará Java Users Group
 
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
package net.java.dev.cejug.classifieds.login.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import net.java.dev.cejug.classifieds.login.entity.facade.client.URLDeobfuscator;

/**
 * Default URL obfuscator applies a a simple shift plus the MD5 cypher o the
 * plain information.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev$ ($Date: 2009-02-14 12:28:21 +0100 (Sat, 14 Feb 2009) $)
 */
public class DefaultUrlObfuscator implements URLObfuscator, URLDeobfuscator {
	private transient final DESedeStringEncrypter encrypter;

	public DefaultUrlObfuscator(String encryptionKey)
			throws GeneralSecurityException {
		if (encryptionKey == null) {
			throw new IllegalArgumentException("encryption key was null");
		} else if (encryptionKey.trim().length() < 24) {
			throw new IllegalArgumentException(
					"encryption key was less than 24 characters");
		}
		encrypter = new DESedeStringEncrypter(encryptionKey);
	}

	@Override
	public URL createObfuscatedUrl(String login, String email, String baseUrl)
			throws MalformedURLException, GeneralSecurityException,
			UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("email=");
		buffer.append(email);
		buffer.append("&login=");
		buffer.append(login);
		return new URL(baseUrl + encrypter.encrypt(buffer.toString()));
	}

	@Override
	public String extractParameters(String obfuscated)
			throws GeneralSecurityException, IOException {

		return encrypter.decrypt(obfuscated);
	}
}
