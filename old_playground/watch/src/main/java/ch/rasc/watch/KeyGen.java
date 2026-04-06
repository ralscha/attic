package ch.rasc.watch;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGen {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();

		String key = Base64.getUrlEncoder().encodeToString(raw);
		System.out.printf("My Secret Key: %s\n", key);

	}

}
