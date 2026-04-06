package ch.rasc.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;

public class CreateBucket {

	public static void main(String[] args) {

		if (args.length == 3) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			Bucket bucket = client.createBucket(args[2]);
			System.out.println("Bucket created: " + bucket);
		}
		else {
			System.out.println("java -jar s3backup.jar " + CreateBucket.class.getName()
					+ " accessKey secretKey bucketName");
		}
	}

}
