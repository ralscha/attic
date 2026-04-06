package ch.rasc.s3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration.Transition;
import com.amazonaws.services.s3.model.StorageClass;

public class CreateBucketWithLifecycle {

	public static void main(String[] args) {

		if (args.length == 5) {
			AWSCredentials credentials = new BasicAWSCredentials(args[0], args[1]);
			AmazonS3Client client = new AmazonS3Client(credentials);

			String bucketName = args[2];
			Bucket bucket = client.createBucket(bucketName);
			System.out.println("Bucket created: " + bucket);

			int transferDays = Integer.parseInt(args[3]);
			int expirationDays = Integer.parseInt(args[4]);

			Transition transToArchive = new Transition().withDays(transferDays)
					.withStorageClass(StorageClass.Glacier);
			BucketLifecycleConfiguration.Rule ruleArchiveAndExpire = new BucketLifecycleConfiguration.Rule()
					.withId("Archive and delete rule").withPrefix("")
					.withTransitions(Collections.singletonList(transToArchive))
					.withExpirationInDays(expirationDays)
					.withStatus(BucketLifecycleConfiguration.ENABLED.toString());

			List<BucketLifecycleConfiguration.Rule> rules = new ArrayList<>();
			rules.add(ruleArchiveAndExpire);
			BucketLifecycleConfiguration configuration = new BucketLifecycleConfiguration()
					.withRules(rules);

			client.setBucketLifecycleConfiguration(bucketName, configuration);

		}
		else {
			System.out.println("java -jar s3backup.jar "
					+ CreateBucketWithLifecycle.class.getName()
					+ " accessKey secretKey bucketName transferToGlacierInDays expirationInDays");
		}
	}
}
