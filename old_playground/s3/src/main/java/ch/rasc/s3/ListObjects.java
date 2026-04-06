package ch.rasc.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class ListObjects {

	public static void main(String[] args) {

		if (args.length == 3) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			System.out.printf("%-30s %10s %s\n", "Key", "Size", "StorageClass");

			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
					.withBucketName(args[2]);
			ObjectListing objectListing;

			do {
				objectListing = client.listObjects(listObjectsRequest);
				for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
					System.out.printf("%-30s %10d %s\n", summary.getKey(),
							summary.getSize(), summary.getStorageClass());
				}

				listObjectsRequest.setMarker(objectListing.getNextMarker());
			}
			while (objectListing.isTruncated());

		}
		else {
			System.out.println("java -jar s3backup.jar " + ListObjects.class.getName()
					+ " accessKey secretKey bucketName");
		}
	}

}
