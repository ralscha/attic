package ch.rasc.playground.dhclient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import ch.rasc.playground.util.CryptoUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Client {

	public static final MediaType MEDIA_TYPE_BINARY = MediaType
			.parse("application/octet-stream");

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException,
			InvalidKeySpecException, InvalidKeyException, IllegalStateException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, SignatureException {

		// create dh keypair
		KeyPair clientDHKeyPair = CryptoUtil.generateDHKeyPair();
		byte[] clientDHPublicKeyX509 = clientDHKeyPair.getPublic().getEncoded();

		// post the public dh key to the server
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(MEDIA_TYPE_BINARY, clientDHPublicKeyX509);
		Request request = new Request.Builder().url("http://localhost:3333/").post(body)
				.build();

		try (Response response = client.newCall(request).execute();
				ResponseBody bdy = response.body()) {
			if (bdy != null) {
				byte[] responseBody = bdy.bytes();

				// extract iv, public server dh key and encrypted message
				byte[] iv = Arrays.copyOfRange(responseBody, 0, 16);
				byte[] serverDHPublicKeyX509 = Arrays.copyOfRange(responseBody, 16, 829);
				byte[] signaturePadded = Arrays.copyOfRange(responseBody, 829, 879);
				byte[] signature = Arrays.copyOfRange(signaturePadded, 1,
						signaturePadded[0] + 1);
				byte[] encryptedMessage = Arrays.copyOfRange(responseBody, 879,
						responseBody.length);
				PublicKey serverDHPublicKey = CryptoUtil
						.convertX509ToDHPublicKey(serverDHPublicKeyX509);

				// decrypt message
				SecretKey aesSecretKey = CryptoUtil.generateAESSecretKey(
						clientDHKeyPair.getPrivate(), serverDHPublicKey);
				byte[] plainMessage = CryptoUtil.decrypt(iv, aesSecretKey,
						encryptedMessage);

				System.out.println("ENCRYPTED MESSAGE: " + new String(plainMessage));

				// Verify signature
				StringBuilder out = new StringBuilder();
				InputStreamReader reader = new InputStreamReader(
						Client.class.getResourceAsStream("/public_key"),
						StandardCharsets.UTF_8);
				char[] buffer = new char[1024];
				int bytesRead = -1;
				while ((bytesRead = reader.read(buffer)) != -1) {
					out.append(buffer, 0, bytesRead);
				}
				String dsaPublicKeyString = out.toString();

				byte[] dsaPublicKeyX509 = CryptoUtil.hexToBytes(dsaPublicKeyString);
				PublicKey dsaPublicKey = CryptoUtil
						.convertX509ToDSAPublicKey(dsaPublicKeyX509);

				Signature dsaSignature = Signature.getInstance("SHA256withDSA");
				dsaSignature.initVerify(dsaPublicKey);
				dsaSignature.update(plainMessage);
				if (dsaSignature.verify(signature)) {
					System.out.println("Signature OK");
				}
				else {
					System.out.println("Signature NOT OK");
				}
			}
		}

	}

}
