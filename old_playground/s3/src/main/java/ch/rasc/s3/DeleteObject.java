package ch.rasc.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

public class DeleteObject {

	public static void main(String[] args) {

		if (args.length == 4) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			client.deleteObject(args[2], args[3]);
			System.out.println("Object deleted: " + args[2] + "/" + args[3]);
		}
		else {
			System.out.println("java -jar s3backup.jar " + DeleteObject.class.getName()
					+ " accessKey secretKey bucketName objectKey");
		}
	}

}
