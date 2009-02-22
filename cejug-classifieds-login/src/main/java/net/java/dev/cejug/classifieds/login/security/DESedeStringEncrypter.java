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
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author fgaucho
 * @see <a
 *      href='http://www.devx.com/Java/10MinuteSolution/21385/1763/page/1'>Encry
 *      p t Sensitive Configuration Data with Java</a>
 * 
 */
public class DESedeStringEncrypter {
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private transient KeySpec keySpec;
	private transient SecretKeyFactory keyFactory;
	private transient Cipher cipher;
	private static final String UNICODE_FORMAT = "UTF8";

	/**
	 * 
	 * @param encryptionScheme
	 * @param encryptionKey
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public DESedeStringEncrypter(String encryptionKey)
			throws GeneralSecurityException {
		try {
			byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);
			keySpec = new DESedeKeySpec(keyAsBytes);
			keyFactory = SecretKeyFactory.getInstance(DESEDE_ENCRYPTION_SCHEME);
			cipher = Cipher.getInstance(DESEDE_ENCRYPTION_SCHEME);
		} catch (Exception e) {
			throw new GeneralSecurityException(e);
		}

	}

	/**
	 * 
	 * @param unencryptedString
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeySpecException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String encrypt(String unencryptedString)
			throws GeneralSecurityException, UnsupportedEncodingException {
		if (unencryptedString == null || unencryptedString.length() == 0) {
			throw new IllegalArgumentException(
					"unencrypted string was null or empty");
		}

		SecretKey key = keyFactory.generateSecret(keySpec);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
		byte[] ciphertext = cipher.doFinal(cleartext);
		BASE64Encoder base64encoder = new BASE64Encoder();
		return base64encoder.encode(ciphertext);
	}

	/**
	 * 
	 * @param encryptedString
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String decrypt(String encryptedString)
			throws GeneralSecurityException, IOException {
		if (encryptedString == null || encryptedString.trim().length() <= 0) {
			throw new IllegalArgumentException(
					"encrypted string was null or empty");
		}
		SecretKey key = keyFactory.generateSecret(keySpec);
		cipher.init(Cipher.DECRYPT_MODE, key);

		BASE64Decoder base64decoder = new BASE64Decoder();
		byte[] cleartext = base64decoder.decodeBuffer(encryptedString);
		byte[] ciphertext = cipher.doFinal(cleartext);

		return new String(ciphertext);
	}
}