package ch.rasc.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectMetadataRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class RestoreJobStatus {
	public static void main(String[] args)
			throws AmazonServiceException, AmazonClientException {
		if (args.length == 4) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			String bucketName = args[2];
			String objectKey = args[3];

			GetObjectMetadataRequest request = new GetObjectMetadataRequest(bucketName,
					objectKey);
			ObjectMetadata response = client.getObjectMetadata(request);
			Boolean restoreFlag = response.getOngoingRestore();
			System.out.format("Restoration status: %s.\n",
					restoreFlag == Boolean.TRUE ? "in progress" : "finished");

		}
		else {
			System.out
					.println("java -jar s3backup.jar " + RestoreJobStatus.class.getName()
							+ " accessKey secretKey bucketName objectKey");
		}

	}
}
