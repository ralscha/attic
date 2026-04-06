package ch.rasc.s3;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;

public class DownloadObject {
	public static void main(String[] args)
			throws AmazonServiceException, AmazonClientException {
		if (args.length == 5) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			String bucketName = args[2];
			String objectKey = args[3];
			Path file = Paths.get(args[4]);

			TransferManager tm = new TransferManager(client);
			Download download = tm.download(bucketName, objectKey, file.toFile());
			try {
				download.waitForCompletion();
				tm.shutdownNow();
			}
			catch (InterruptedException | AmazonClientException e) {
				System.out.println(
						"Unable to download file, download was aborted: " + file);
				e.printStackTrace();
			}

		}
		else {
			System.out.println("java -jar s3backup.jar " + DownloadObject.class.getName()
					+ " accessKey secretKey bucketName objectKey pathToFile");
		}

	}
}
