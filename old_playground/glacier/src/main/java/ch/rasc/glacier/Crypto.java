package ch.rasc.glacier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	public static void writeEncryptedFile(String password, Path input, Path output)
			throws IOException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
			InvalidKeyException, InvalidParameterSpecException {

		byte[] salt = new byte[8];
		SecureRandom rnd = new SecureRandom();
		rnd.nextBytes(salt);

		SecretKey secret = createKey(password, salt);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		AlgorithmParameters params = cipher.getParameters();
		byte[] initVector = params.getParameterSpec(IvParameterSpec.class).getIV();

		byte[] inbuf = new byte[1024];

		try (InputStream is = Files.newInputStream(input, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(output)) {

			os.write(salt);
			os.write(initVector.length);
			os.write(initVector);

			int nread;
			while ((nread = is.read(inbuf)) > 0) {
				os.write(cipher.update(Arrays.copyOf(inbuf, nread)));
			}

			byte[] finalbuf = cipher.doFinal();
			if (finalbuf != null) {
				os.write(finalbuf);
			}
		}

	}

	public static void readEncryptedFile(String password, Path input, Path output)
			throws IllegalBlockSizeException, BadPaddingException, IOException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {

		try (InputStream is = Files.newInputStream(input, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(output)) {

			byte[] salt = new byte[8];
			is.read(salt);
			int len = is.read();
			byte[] initVector = new byte[len];
			is.read(initVector);

			SecretKey secret = createKey(password, salt);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(initVector));

			byte[] inbuf = new byte[1024];

			int nread;
			while ((nread = is.read(inbuf)) > 0) {
				os.write(cipher.update(Arrays.copyOf(inbuf, nread)));
			}

			byte[] finalbuf = cipher.doFinal();
			if (finalbuf != null) {
				os.write(finalbuf);
			}
		}

	}

	private static SecretKey createKey(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		return secret;
	}

	public static void main(String[] args)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
			InvalidParameterSpecException, IOException,
			InvalidAlgorithmParameterException {

		writeEncryptedFile("mypass", Paths.get("input.txt"), Paths.get("encrypted.aes"));
		readEncryptedFile("mypass", Paths.get("encrypted.aes"),
				Paths.get("decrypted.txt"));
	}

}