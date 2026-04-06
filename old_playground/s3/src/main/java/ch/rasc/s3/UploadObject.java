package ch.rasc.s3;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class UploadObject {
	public static void main(String[] args)
			throws AmazonServiceException, AmazonClientException {
		if (args.length == 4) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			String bucketName = args[2];
			Path file = Paths.get(args[3]);

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
			System.out.println("java -jar s3backup.jar " + UploadObject.class.getName()
					+ " accessKey secretKey bucketName pathToFile");
		}

	}
}
