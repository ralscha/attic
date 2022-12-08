package ch.rasc.sqrl.tree;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicLong;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Creates a 64-bit nut based on the GRC spec using a monotonic counter and blowfish
 * cipher
 */
public class GrcTree implements Tree {
	private final AtomicLong counter = new AtomicLong();

	private final SecretKeySpec secretKeySpec;
	private final Cipher cipher;

	/**
	 * Takes an initial counter value (in the case of reboot) and a blowfish key (use a
	 * max key of random 56 bytes)
	 */
	public GrcTree(long counterInit, byte[] blowfishKey) {
		this.counter.set(counterInit);
		this.secretKeySpec = new SecretKeySpec(blowfishKey, "Blowfish");
		try {
			this.cipher = Cipher.getInstance("Blowfish");
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String nut() {
		try {
			long nextValue = this.counter.incrementAndGet();
			this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);

			byte[] encryptedConter = new byte[8];
			encryptedConter[0] = (byte) nextValue;
			encryptedConter[1] = (byte) (nextValue >> 8);
			encryptedConter[2] = (byte) (nextValue >> 16);
			encryptedConter[3] = (byte) (nextValue >> 24);
			encryptedConter[4] = (byte) (nextValue >> 32);
			encryptedConter[5] = (byte) (nextValue >> 40);
			encryptedConter[6] = (byte) (nextValue >> 48);
			encryptedConter[7] = (byte) (nextValue >> 56);

			byte[] encryptedCounter = this.cipher.doFinal(encryptedConter);
			return Base64.getUrlEncoder().withoutPadding()
					.encodeToString(Arrays.copyOf(encryptedCounter, 8));
		}
		catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

}
