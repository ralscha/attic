package ch.rasc.s3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;

public class EncryptDownloadObject {
	public static void main(String[] args) throws AmazonServiceException,
			AmazonClientException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (args.length == 6) {
			String password = args[2];

			String bucketName = args[3];
			String objectKey = args[4];
			Path restorePath = Paths.get(args[5]);

			SecretKey mySymmetricKey = EncryptUploadObject.createSecretKey(password,
					bucketName, objectKey);

			EncryptionMaterials materials = new EncryptionMaterials(mySymmetricKey);

			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3EncryptionClient client = new AmazonS3EncryptionClient(credentials,
					materials);

			TransferManager tm = new TransferManager(client);
			Download download = tm.download(bucketName, objectKey, restorePath.toFile());
			try {
				download.waitForCompletion();
				tm.shutdownNow();
			}
			catch (InterruptedException | AmazonClientException e) {
				System.out.println(
						"Unable to download file, download was aborted: " + objectKey);
				e.printStackTrace();
			}

		}
		else {
			System.out.println("java -jar s3backup.jar "
					+ EncryptDownloadObject.class.getName()
					+ " accessKey secretKey password bucketName objectKey pathToFile");
		}

	}

}
