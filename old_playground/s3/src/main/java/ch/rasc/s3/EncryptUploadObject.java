package ch.rasc.s3;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class EncryptUploadObject {
	public static void main(String[] args) throws AmazonServiceException,
			AmazonClientException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (args.length == 5) {
			String password = args[2];

			String bucketName = args[3];
			String fileToUpload = args[4];
			Path file = Paths.get(fileToUpload);

			SecretKey mySymmetricKey = createSecretKey(password, bucketName,
					file.getFileName().toString());

			EncryptionMaterials materials = new EncryptionMaterials(mySymmetricKey);

			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);

			AmazonS3EncryptionClient client = new AmazonS3EncryptionClient(credentials,
					materials);

			TransferManager tm = new TransferManager(client);
			Upload upload = tm.upload(bucketName, file.getFileName().toString(),
					file.toFile());
			try {
				upload.waitForCompletion();
				tm.shutdownNow();
			}
			catch (InterruptedException | AmazonClientException e) {
				System.out.println("Unable to upload file, upload was aborted: " + file);
				e.printStackTrace();
			}

		}
		else {
			System.out.println(
					"java -jar s3backup.jar " + EncryptUploadObject.class.getName()
							+ " accessKey secretKey password bucketName pathToFile");
		}

	}

	public static SecretKey createSecretKey(String password, String bucketName,
			String objectName) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = (bucketName + objectName).getBytes(StandardCharsets.UTF_8);
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 1024, 256);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey sk = factory.generateSecret(keySpec);
		SecretKey mySymmetricKey = new SecretKeySpec(sk.getEncoded(), "AES");
		return mySymmetricKey;
	}
}
