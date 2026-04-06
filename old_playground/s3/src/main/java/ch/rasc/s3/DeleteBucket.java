package ch.rasc.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class DeleteBucket {

	public static void main(String[] args) {

		if (args.length == 3) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			String bucketName = args[2];

			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
					.withBucketName(bucketName);
			ObjectListing objectListing;

			do {
				objectListing = client.listObjects(listObjectsRequest);
				for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
					client.deleteObject(bucketName, summary.getKey());
				}

				listObjectsRequest.setMarker(objectListing.getNextMarker());
			}
			while (objectListing.isTruncated());

			client.deleteBucket(bucketName);
			System.out.println("Bucket deleted: " + args[2]);
		}
		else {
			System.out.println("java -jar s3backup.jar " + DeleteBucket.class.getName()
					+ " accessKey secretKey bucketName");
		}
	}

}
