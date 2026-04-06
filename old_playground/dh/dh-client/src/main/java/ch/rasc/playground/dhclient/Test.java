package ch.rasc.playground.dhclient;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Test {
	public static byte[] iv = new SecureRandom().generateSeed(16);

	public static void main(String[] args)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// removeCryptographyRestrictions();

		String plainText = "Look mah, I'm a message!";
		System.out.println("Original plaintext message: " + plainText);

		// Initialize two key pairs
		KeyPair keyPairA = generateDHKeys();
		KeyPair keyPairB = generateDHKeys();

		byte[] pubEnc = keyPairA.getPublic().getEncoded();
		KeyFactory keyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(pubEnc);
		DHPublicKey pubKey = (DHPublicKey) keyFac.generatePublic(pubX509);
		DHParameterSpec spec = pubKey.getParams();
		System.out.println(((javax.crypto.interfaces.DHPublicKey) keyPairA.getPublic())
				.getParams().getP());
		System.out.println(spec.getP());

		// Create two AES secret keys to encrypt/decrypt the message
		SecretKey secretKeyA = generateSharedSecret(keyPairA.getPrivate(),
				keyPairB.getPublic());
		SecretKey secretKeyB = generateSharedSecret(keyPairB.getPrivate(),
				keyPairA.getPublic());

		// Encrypt the message using 'secretKeyA'
		String cipherText = encryptString(secretKeyA, plainText);
		System.out.println("Encrypted cipher text: " + cipherText);

		// Decrypt the message using 'secretKeyB'
		String decryptedPlainText = decryptString(secretKeyB, cipherText);
		System.out.println("Decrypted cipher text: " + decryptedPlainText);
	}

	public static KeyPair generateDHKeys() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator
					.getInstance("DiffieHellman");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			DHParameterSpec params = ((javax.crypto.interfaces.DHPublicKey) keyPair
					.getPublic()).getParams();
			BigInteger p = params.getP();
			BigInteger g = params.getG();
			int l = params.getL();
			System.out.println(p);
			System.out.println(g);
			System.out.println(l);

			return keyPair;
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SecretKey generateSharedSecret(PrivateKey privateKey,
			PublicKey publicKey) {
		try {
			KeyAgreement keyAgreement = KeyAgreement.getInstance("DiffieHellman");
			keyAgreement.init(privateKey);
			keyAgreement.doPhase(publicKey, true);

			// SecretKey key = keyAgreement.generateSecret("AES");
			byte[] sharedSecret = keyAgreement.generateSecret();
			SecretKey key = new SecretKeySpec(sharedSecret, 0, 16, "AES");

			return key;
		}
		catch (InvalidKeyException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encryptString(SecretKey key, String plainText) {
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] plainTextBytes = plainText.getBytes("UTF-8");
			byte[] cipherText;

			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			cipherText = new byte[cipher.getOutputSize(plainTextBytes.length)];
			int encryptLength = cipher.update(plainTextBytes, 0, plainTextBytes.length,
					cipherText, 0);
			encryptLength += cipher.doFinal(cipherText, encryptLength);

			return bytesToHex(cipherText);
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | UnsupportedEncodingException
				| ShortBufferException | IllegalBlockSizeException
				| BadPaddingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptString(SecretKey key, String cipherText) {
		try {
			Key decryptionKey = new SecretKeySpec(key.getEncoded(), key.getAlgorithm());
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] cipherTextBytes = hexToBytes(cipherText);
			byte[] plainText;

			cipher.init(Cipher.DECRYPT_MODE, decryptionKey, ivSpec);
			plainText = new byte[cipher.getOutputSize(cipherTextBytes.length)];
			int decryptLength = cipher.update(cipherTextBytes, 0, cipherTextBytes.length,
					plainText, 0);
			decryptLength += cipher.doFinal(plainText, decryptLength);

			return new String(plainText, "UTF-8");
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException | ShortBufferException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String bytesToHex(byte[] data, int length) {
		String digits = "0123456789ABCDEF";
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i != length; i++) {
			int v = data[i] & 0xff;

			buffer.append(digits.charAt(v >> 4));
			buffer.append(digits.charAt(v & 0xf));
		}

		return buffer.toString();
	}

	public static String bytesToHex(byte[] data) {
		return bytesToHex(data, data.length);
	}

	public static byte[] hexToBytes(String string) {
		int length = string.length();
		byte[] data = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
					+ Character.digit(string.charAt(i + 1), 16));
		}
		return data;
	}

	// private static void removeCryptographyRestrictions() {
	// if (!isRestrictedCryptography()) {
	// System.out.println("not needed");
	// return;
	// }
	// try {
	// /*
	// * Do the following, but with reflection to bypass access checks:
	// *
	// * JceSecurity.isRestricted = false;
	// * JceSecurity.defaultPolicy.perms.clear();
	// * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
	// */
	// final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
	// final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
	// final Class<?> cryptoAllPermission =
	// Class.forName("javax.crypto.CryptoAllPermission");
	//
	// final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
	// isRestrictedField.setAccessible(true);
	// isRestrictedField.set(null, false);
	//
	// final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
	// defaultPolicyField.setAccessible(true);
	// final PermissionCollection defaultPolicy = (PermissionCollection)
	// defaultPolicyField.get(null);
	//
	// final Field perms = cryptoPermissions.getDeclaredField("perms");
	// perms.setAccessible(true);
	// ((Map<?, ?>) perms.get(defaultPolicy)).clear();
	//
	// final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
	// instance.setAccessible(true);
	// defaultPolicy.add((Permission) instance.get(null));
	//
	// System.out.println("Success");
	// } catch (final Exception e) {
	// System.out.println(e);
	// }
	// }
	//
	// private static boolean isRestrictedCryptography() {
	// // This simply matches the Oracle JRE, but not OpenJDK.
	// return
	// "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
	// }
}