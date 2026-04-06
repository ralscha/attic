package ch.rasc.playground.dhserver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StreamUtils;

import ch.rasc.playground.util.CryptoUtil;

public class SendServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PrivateKey dsaPrivateKey = null;

	@Override
	public void init() throws ServletException {
		// read dsa certificate
		try {
			String dsaPrivateKeyString = StreamUtils.copyToString(
					getClass().getResourceAsStream("/private_key"),
					StandardCharsets.UTF_8);
			byte[] dsaPrivateKeyPkcs8 = CryptoUtil.hexToBytes(dsaPrivateKeyString);
			this.dsaPrivateKey = CryptoUtil
					.convertPKCS8ToDSAPrivateKey(dsaPrivateKeyPkcs8);
		}
		catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String plainMessage = "The quick brown fox jumps over the lazy dog";

		try {
			// Convert incoming key into a public dh key
			byte[] clientDHPublicKeyX509 = StreamUtils
					.copyToByteArray(request.getInputStream());
			PublicKey clientDHPublicKey = CryptoUtil
					.convertX509ToDHPublicKey(clientDHPublicKeyX509);

			// Create server DH key pair, compute shared secret and dervive AES key
			KeyPair serverDHKeyPair = CryptoUtil.generateDHKeyPair();
			SecretKey aesSecretKey = CryptoUtil.generateAESSecretKey(
					serverDHKeyPair.getPrivate(), clientDHPublicKey);

			// Encrypt message
			byte[] iv = new SecureRandom().generateSeed(16);
			byte[] encryptedMessage = CryptoUtil.encrypt(iv, aesSecretKey,
					plainMessage.getBytes());

			// Create signature
			Signature dsaSignature = Signature.getInstance("SHA256withDSA");
			dsaSignature.initSign(this.dsaPrivateKey);
			dsaSignature.update(plainMessage.getBytes());
			byte[] signature = new byte[50];
			int actualBytes = dsaSignature.sign(signature, 1, 49);
			signature[0] = (byte) actualBytes;

			// Send iv, public dh server key, signature and encrypted message to client
			byte[] serverDHPublicKey = serverDHKeyPair.getPublic().getEncoded();

			@SuppressWarnings("resource")
			ServletOutputStream os = response.getOutputStream();
			os.write(iv);
			os.write(serverDHPublicKey);
			os.write(signature);
			os.write(encryptedMessage);
			os.flush();

		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException
				| IllegalStateException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException
				| InvalidAlgorithmParameterException | SignatureException e) {
			throw new ServletException(e);
		}

	}

}
