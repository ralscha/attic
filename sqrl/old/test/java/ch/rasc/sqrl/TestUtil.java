package ch.rasc.sqrl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.KeyPairGenerator;

public class TestUtil {
	public static FakeIdentity newFakeIdentity() {
		KeyPairGenerator kpg = new KeyPairGenerator();
		KeyPair idkPair = kpg.generateKeyPair();
		KeyPair vukPair = kpg.generateKeyPair();
		KeyPair sukPair = kpg.generateKeyPair();
		return new FakeIdentity(idkPair, vukPair, sukPair);
	}

	public static byte[] sign(EdDSAPrivateKey idkPrivate, String str) {

		try {
			final Signature signature = new EdDSAEngine(
					MessageDigest.getInstance("SHA-512"));
			signature.initSign(idkPrivate);
			signature.update(str.getBytes(StandardCharsets.UTF_8));
			return signature.sign();
		}
		catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			e.printStackTrace();
			return null;
		}

	}

}
