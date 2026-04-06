package ch.rasc.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

public class RestoreObject {
	public static void main(String[] args)
			throws AmazonServiceException, AmazonClientException {
		if (args.length == 4) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			String bucketName = args[2];
			String objectKey = args[3];
			int expirationInDays = 1;
			client.restoreObject(bucketName, objectKey, expirationInDays);

		}
		else {
			System.out.println("java -jar s3backup.jar " + RestoreObject.class.getName()
					+ " accessKey secretKey bucketName objectKey");
		}

	}
}
