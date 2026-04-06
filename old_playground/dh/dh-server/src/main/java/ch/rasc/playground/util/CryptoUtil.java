package ch.rasc.playground.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {

	public static KeyPair generateDHKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman");
		keyPairGenerator.initialize(2048);
		return keyPairGenerator.generateKeyPair();
	}

	public static KeyPair generateDSAKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
		return keyPairGenerator.generateKeyPair();
	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
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

	public static PublicKey convertX509ToDHPublicKey(byte[] encodedPublicKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeyFactory keyFac = KeyFactory.getInstance("DiffieHellman");
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(encodedPublicKey);
		return keyFac.generatePublic(pubX509);
	}

	public static PublicKey convertX509ToDSAPublicKey(byte[] encodedPublicKey)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeyFactory keyFac = KeyFactory.getInstance("DSA");
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(encodedPublicKey);
		return keyFac.generatePublic(pubX509);
	}

	public static PrivateKey convertPKCS8ToDSAPrivateKey(byte[] encodedPrivateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFac = KeyFactory.getInstance("DSA");
		PKCS8EncodedKeySpec pkcs8spec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		return keyFac.generatePrivate(pkcs8spec);
	}

	public static SecretKey generateAESSecretKey(PrivateKey privateKey,
			PublicKey publicKey)
			throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException {
		KeyAgreement keyAgreement = KeyAgreement.getInstance("DiffieHellman");
		keyAgreement.init(privateKey);
		keyAgreement.doPhase(publicKey, true);

		byte[] sharedSecret = keyAgreement.generateSecret();
		return new SecretKeySpec(sharedSecret, 0, 16, "AES");
	}

	public static byte[] encrypt(byte[] iv, SecretKey key, byte[] plainMessage)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException {

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		return cipher.doFinal(plainMessage);
	}

	public static byte[] decrypt(byte[] iv, SecretKey key, byte[] encryptedMessage)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {

		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		return cipher.doFinal(encryptedMessage);
	}

}
