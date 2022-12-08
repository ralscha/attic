package ch.rasc.sqrl.tree;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * RandomTree produces random nuts
 */
public class RandomTree implements Tree {

	private final int byteSize;
	private final SecureRandom random;

	/**
	 * RandomTree takes a bytesize between 8 and 20 Shorter nuts are preferred; but if you
	 * think your deployment would require more bits to be unique you can create larger
	 * ones
	 */
	public RandomTree(int byteSize) {

		if (byteSize < 8 || byteSize > 20) {
			throw new IllegalArgumentException("byteSize must be between 8 and 20 bytes");
		}

		this.random = new SecureRandom();
		this.byteSize = byteSize;
	}

	@Override
	public String nut() {
		byte[] randomBytes = new byte[this.byteSize];
		this.random.nextBytes(randomBytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	}

}
