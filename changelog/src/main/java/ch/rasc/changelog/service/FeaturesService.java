package ch.rasc.changelog.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;

@Service
public class FeaturesService {

	@Autowired
	private Environment environment;

	private Cipher cipher;

	@PostConstruct
	private void init() throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, DecoderException {
		String key = this.environment.getProperty("aes.key");
		SecretKeySpec skeySpec = new SecretKeySpec(Hex.decodeHex(key.toCharArray()),
				"AES");

		this.cipher = Cipher.getInstance("AES");
		this.cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	}

	@ExtDirectMethod
	@PreAuthorize("isAuthenticated()")
	public String feature(Set<String> features)
			throws IllegalBlockSizeException, BadPaddingException {
		byte[] encrypted = this.cipher.doFinal(Joiner.on(";").join(features).getBytes());
		return String.valueOf(Hex.encodeHex(encrypted)).toUpperCase();
	}

}
