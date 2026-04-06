package ch.rasc.playground.dhserver;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

import ch.rasc.playground.util.CryptoUtil;

public class CreateKeyPairForSignature {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyPair dsaKeyPair = CryptoUtil.generateDSAKeyPair();

		byte[] publicKey = dsaKeyPair.getPublic().getEncoded();
		byte[] privateKey = dsaKeyPair.getPrivate().getEncoded();

		System.out.println(dsaKeyPair.getPublic().getFormat());
		System.out.println(dsaKeyPair.getPrivate().getFormat());

		System.out.println("PUBLIC :" + CryptoUtil.bytesToHex(publicKey));
		System.out.println("PRIVATE:" + CryptoUtil.bytesToHex(privateKey));
	}
}
