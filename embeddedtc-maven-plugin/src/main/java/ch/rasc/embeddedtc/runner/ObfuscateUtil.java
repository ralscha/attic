/**
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.embeddedtc.runner;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

public class ObfuscateUtil {

	public static final String OBFUSCATE = "OBF";

	public static final String ENCODE = "ENC";

	public static String encrypt(String plainText, String password) throws Exception {

		byte[] salt = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password.toCharArray()));
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
		pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(salt, 20));
		return ENCODE + DatatypeConverter.printBase64Binary(salt) + DatatypeConverter
				.printBase64Binary(pbeCipher.doFinal(plainText.getBytes()));
	}

	public static String decrypt(String encryptedText, String password) {
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFactory
					.generateSecret(new PBEKeySpec(password.toCharArray()));
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

			String enc = encryptedText.substring(ENCODE.length() + 12);
			byte[] salt = DatatypeConverter.parseBase64Binary(
					encryptedText.substring(ENCODE.length(), ENCODE.length() + 12));

			pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(salt, 20));
			return new String(
					pbeCipher.doFinal(DatatypeConverter.parseBase64Binary(enc)));
		}
		catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException
				| NoSuchPaddingException | InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * The Jetty obfuscator.
	 *
	 * @param plainText the plain text
	 * @return the obfuscated text
	 */
	public static String obfuscate(String plainText) {
		StringBuilder buf = new StringBuilder();
		byte[] b = plainText.getBytes();

		buf.append(OBFUSCATE);
		for (int i = 0; i < b.length; i++) {
			byte b1 = b[i];
			byte b2 = b[plainText.length() - (i + 1)];
			int i1 = 127 + b1 + b2;
			int i2 = 127 + b1 - b2;
			int i0 = i1 * 256 + i2;
			String x = Integer.toString(i0, 36);

			switch (x.length()) {
			case 1:
				buf.append('0');
				buf.append('0');
				buf.append('0');
				buf.append(x);
				break;
			case 2:
				buf.append('0');
				buf.append('0');
				buf.append(x);
				break;
			case 3:
				buf.append('0');
				buf.append(x);
				break;
			default:
				buf.append(x);
				break;
			}
		}
		return buf.toString();

	}

	public static String deobfuscate(String obfuscatedText) {
		String s = obfuscatedText;
		if (s.startsWith(OBFUSCATE)) {
			s = s.substring(OBFUSCATE.length());
		}

		byte[] b = new byte[s.length() / 2];
		int l = 0;
		for (int i = 0; i < s.length(); i += 4) {
			String x = s.substring(i, i + 4);
			int i0 = Integer.parseInt(x, 36);
			int i1 = i0 / 256;
			int i2 = i0 % 256;
			b[l++] = (byte) ((i1 + i2 - 254) / 2);
		}

		return new String(b, 0, l);
	}

	public static void obfuscate(ObfuscateOptions obfuscateOptions) throws Exception {
		if (obfuscateOptions.password != null) {
			System.err.println(encrypt(obfuscateOptions.plainText.get(0),
					obfuscateOptions.password));
		}
		else {
			System.err.println(obfuscate(obfuscateOptions.plainText.get(0)));
		}
	}

	public static String toPlaintext(String encryptedText, String password) {
		if (encryptedText == null) {
			return null;
		}

		if (encryptedText.startsWith(OBFUSCATE)) {
			return deobfuscate(encryptedText);
		}
		else if (encryptedText.startsWith(ENCODE) && password != null) {
			return decrypt(encryptedText, password);
		}

		return encryptedText;
	}

	@Parameters(commandDescription = "Obfuscates a plaintext password")
	static class ObfuscateOptions {
		@Parameter(required = true, arity = 1, description = "plaintextPassword")
		List<String> plainText;

		@Parameter(names = { "-p", "--password" }, description = "The password")
		String password = null;
	}
}
