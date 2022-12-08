package ch.rasc.sqrl;

import java.security.KeyPair;

import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;

public class FakeIdentity {

	private final EdDSAPrivateKey idkPrivate;
	private final EdDSAPublicKey idk;

	private final EdDSAPrivateKey vukPrivate;
	private final EdDSAPublicKey vuk;

	private final EdDSAPublicKey suk;

	public FakeIdentity(KeyPair idkPair, KeyPair vukPair, KeyPair sukPair) {
		this.idkPrivate = (EdDSAPrivateKey) idkPair.getPrivate();
		this.idk = (EdDSAPublicKey) idkPair.getPublic();
		this.vukPrivate = (EdDSAPrivateKey) vukPair.getPrivate();
		this.vuk = (EdDSAPublicKey) vukPair.getPublic();
		this.suk = (EdDSAPublicKey) sukPair.getPublic();
	}

	public EdDSAPrivateKey getIdkPrivate() {
		return this.idkPrivate;
	}

	public EdDSAPublicKey getIdk() {
		return this.idk;
	}

	public EdDSAPrivateKey getVukPrivate() {
		return this.vukPrivate;
	}

	public EdDSAPublicKey getVuk() {
		return this.vuk;
	}

	public EdDSAPublicKey getSuk() {
		return this.suk;
	}

}
